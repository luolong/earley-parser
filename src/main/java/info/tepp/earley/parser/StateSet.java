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

        StateSet next = new StateSet(index + 1, emptySet());

        Item item;
        while ((item = queue.poll()) != null) {
            queue.addAll(predictor
                    .predict(item).stream()
                            .map(r -> r.toItem(index))
                            .collect(toList()));

            next.addAll(scanner.scan(item));

            queue.addAll(completer.complete(item));
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
                if (grammar.isNullable((Nonterminal) symbol)) {

                }
                // Collect all predictions for this rule
                return grammar
                        .rules((Nonterminal) symbol)
                        .map(Prediction::new)
                        .collect(toList());
            }

            return emptyList();
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
}
