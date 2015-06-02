package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Terminal;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

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
    public StateSet scan(String string, Grammar grammar) {
        Queue<Item> queue = getQueue();

        Predicter predicter = predicter(grammar, queue);
        Scanner scanner = scanner(string);

        Item item;
        while ((item = queue.poll()) != null) {

            predicter.predict(item, index);
            scanner.scan(item);

        }

        return new StateSet(index + 1, scanner.getAdvancedItems());
    }

    Scanner scanner(String string) {
        if (string.length() > index) {
            return new Scanner(string.charAt(index));
        }
        return Scanner.EMPTY;
    }

    @Nonnull
    Predicter predicter(Grammar grammar, Queue<Item> queue) {
        return new Predicter(queue, grammar);
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

    public static class Predicter {
        private final Queue<Item> queue;
        private final Grammar grammar;

        public Predicter(Queue<Item> queue, Grammar grammar) {
            this.queue = queue;
            this.grammar = grammar;
        }

        public boolean predict(Item item, int index) {
            ArrayList<Item> predictions = new ArrayList<>();
            Symbol symbol = item.getCurrent();
            if (symbol instanceof Nonterminal) {
                // Collect all predictions for this rule
                grammar.rules((Nonterminal) symbol)
                       .map(rule1 -> rule1.toItem(index))
                       .forEachOrdered(predictions::add);
            }

            return queue.addAll(predictions);
        }
    }

    public static class Scanner {
        private static Scanner EMPTY = new Scanner('\0') {
            @Override
            public boolean scan(Item item) {
                return false;
            }

            @Override
            public Set<Item> getAdvancedItems() {
                return Collections.emptySet();
            }
        };

        private final char character;
        private final Set<Item> items = new HashSet<>();

        Scanner(char character) {
            this.character = character;
        }

        public boolean scan(Item item) {
            Symbol symbol = item.getCurrent();
            if (symbol instanceof Terminal) {
                if (((Terminal) symbol).matches(character)) {
                    items.add(item.advance());
                    return true;
                }
            }
            return false;
        }

        public Set<Item> getAdvancedItems() {
            return items;
        }
    }
}
