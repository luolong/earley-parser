package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Sequence;

import javax.annotation.Nonnull;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * State set of some parse position
 */
public class StateSet extends AbstractSet<Item> implements Set<Item> {
    private final LinkedHashSet<Item> items;
    private final int index;

    private static boolean DEBUG = true;

    public static StateSet of(int index, Item... items) {
        return new StateSet(index, Arrays.asList(items));
    }

    public static StateSet of(int index, Rule... rules) {
        return new StateSet(index, Arrays.stream(rules)
                .map(r -> r.toItem(index))
                .collect(toList()));
    }

    public StateSet(int index, Collection<Item> items) {
        this.index = index;
        this.items = new LinkedHashSet<>(items);
    }

    @Override
    public Stream<Item> stream() {
        return items.stream();
    }

    @Override
    public @Nonnull Iterator<Item> iterator() {
        final Iterator<Item> iterator = items.iterator();
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Item next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove");
            }
        };
    }

    @Override
    public boolean add(Item item) {
        return items.add(item);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends Item> c) {
        return items.addAll(c);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public boolean removeIf(Predicate<? super Item> filter) {
        throw new UnsupportedOperationException("removeIf");
    }

    /**
     * Scans the input string at correct position and returns
     * initial state of the next state set based on the findings
     * of this state set.
     */
    public StateSet scan(String string, Predictor predictor, Completer completer) {
        Queue<Item> queue = getQueue();
        Scanner scanner = scanner(string);

        if (DEBUG) {debug(string, index); debug();}
        StateSet next = new StateSet(index + 1, emptySet());

        Item item;
        while ((item = queue.poll()) != null) {
            queue.addAll(predictor
                    .predict(item).stream()
                            .map(r -> debug(r, index).toItem(index))
                            .collect(toList()));

            next.addAll(scanner.scan(item));

            queue.addAll(debug(completer.complete(item), index));
        }

        return next;
    }

    Scanner scanner(String string) {
        if (string.length() > index) {
            return new Scanner(string.charAt(index));
        }
        return Scanner.EMPTY;
    }

    @Nonnull
    Queue<Item> getQueue() {
        final LinkedList<Item> linkedList = new LinkedList<>(this.items);

        @SuppressWarnings("unchecked")
        Queue<Item> queue = (Queue<Item>) Proxy.newProxyInstance(
                LinkedList.class.getClassLoader(),
                LinkedList.class.getInterfaces(),
                (proxy, method, args) -> {
                    if (method.getName().equals("add") && args.length == 1)
                        return items.add((Item) args[0]) && (boolean) method.invoke(linkedList, args);

                    if (method.getName().equals("addAll") && args.length == 1)
                        return items.addAll((Collection<Item>) args[0]) && (boolean) method.invoke(linkedList, args);

                    return method.invoke(linkedList, args);
                });
        return queue;
    }

    @Override
    public String toString() {
        Iterator<Item> it = iterator();
        if (! it.hasNext())
            return "{@" + index + "}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append('@').append(index).append(':').append(' ');
        for (;;) {
            Object e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

    public static class Predictor {
        private final Grammar grammar;

        public Predictor(Grammar grammar) {
            this.grammar = grammar;
        }

        public List<Prediction> predict(Item item) {
            Symbol symbol = item.getCurrent();
            if (symbol instanceof Nonterminal) {
                Nonterminal nonterminal = (Nonterminal) symbol;
                List<Prediction> predictions = predictions(nonterminal);
                if (grammar.isNullable(nonterminal)) {
                    predictions.add(new NullCompletion(item));
                }
                return predictions;

            }

            return emptyList();
        }

        private List<Prediction> predictions(Nonterminal symbol) {
            // Collect all predictions for this rule
            return grammar
                    .rules(symbol)
                    .map(Prediction::new)
                    .collect(toList());
        }
    }

    public static class Prediction {
        private final Rule rule;
        public Prediction(Rule rule) {
            this.rule = rule;
        }

        public Item toItem(int start) {
            return rule.toItem(start);
        }

        @Override
        public String toString() {
            String s = rule.toItem(0).toString();
            int lp = s.lastIndexOf('(') + 1;
            return s.substring(0,lp) + '?' + s.substring(lp+1);
        }

        @Override
        public int hashCode() {
            return rule.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Prediction
                    && rule.equals(((Prediction) other).rule);
        }
    }

    public static class NullCompletion extends Prediction {
        private final Item item;

        public NullCompletion(Item item) {
            super(item.getRule());
            this.item = item;
        }

        @Override
        public Item toItem(int start) {
            return item.advance();
        }

        @Override
        public String toString() {
            return item.advance().toString().replace(Item.DOT, "\u2218");
        }
    }

    public static class Scanner {
        private static Scanner EMPTY = new Scanner('\0') {
            @Override
            public Set<Item> scan(Item item) {
                return emptySet();
            }
        };

        private final char character;

        Scanner(char character) {
            this.character = character;
        }

        public Set<Item> scan(Item item) {
            Symbol symbol = item.getCurrent();
            if (symbol instanceof Sequence) {
                if (((Sequence) symbol).matches(character)) {
                    return singleton(item.advance());
                }
            }
            return emptySet();
        }
    }

    public static class Completer {
        private final List<StateSet> stateSets;

        public Completer(List<StateSet> stateSets) {
            this.stateSets = stateSets;
        }

        public Set<Item> complete(Item item) {
            if (item.isDone()) {
                return stateSets.get(item.getStart()).stream()
                        .filter(i -> !i.isDone())
                        .filter(i -> i.getCurrent().equals(item.getLeft()))
                        .map(Item::advance)
                        .collect(toSet());
            }
            return emptySet();
        }
    }

    private static final String
            LINE = "--------------------------------------------------------------------------------";
    private static final String
            BLANK = "                                                                               ";

    private static void debug(String input, int index) {
        if (!DEBUG) return;
        int start = Math.max(0, index - 38);
        int end = Math.min(index + 38, input.length());
        int offset = index - start;
        String out = "\n" + input.substring(start, end);
        out += '\n';
        if (offset > 0) out += LINE.substring(0, offset);
        out += '^';
        if (index < end) out += LINE.substring(index+1, end);
        System.out.println(out);
    }

    private static Prediction debug(Prediction prediction, int index) {
        if (DEBUG) {
            int start = Math.max(0, index - 38);
            int offset = index - start;
            String out = "";
            if (offset > 1) out += BLANK.substring(0, offset -1);
            out += prediction.toString();
            if (prediction instanceof NullCompletion) out += " <- nullable completion";
            else out += " <- prediction";
            System.out.println(out);
        }
        return prediction;
    }

    private static Set<Item> debug(Set<Item> complete, int index) {
        if (DEBUG) {
            complete.forEach(item -> {
                int start = Math.max(0, index - 38);
                int offset = index - start;
                String out = "";
                if (offset > 1) out += BLANK.substring(0, offset -1);
                out += item.toString() + " <- completion";
                System.out.println(out);
            });
        }
        return complete;
    }

    private void debug() {
        if (!DEBUG) return;
        forEach(item -> {
            int start = Math.max(0, index - 38);
            int offset = index - start;
            String out = "";
            if (offset > 1) out += BLANK.substring(0, offset -1);
            out += item.toString();
            System.out.println(out);
        });
    }

}
