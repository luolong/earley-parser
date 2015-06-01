package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Terminal;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Predicate;

/**
 * State set of some parse position
 */
public class StateSet extends AbstractSet<Item> implements Set<Item> {
    private final LinkedHashSet<Item> items;
    private final int index;

    public StateSet(int index, List<Item> items) {
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

    void scan(String string, Grammar grammar) {
        Queue<Item> queue = getQueue();
        Set<Item> scans = new HashSet<>();

        Item item;
        while ((item = queue.poll()) != null) {
            queue.addAll(predict(item.getCurrent(), grammar));

            if (matches(string.charAt(index), item.getCurrent()))
                scans.add(item.next());

        }
    }

    boolean matches(char nextChar, Symbol item) {
        if (item instanceof Terminal) {
            ((Terminal) item).matches(nextChar);
        }
        return false;
    }

    List<Item> predict(Symbol nextSymbol, Grammar grammar) {
        ArrayList<Item> predictions = new ArrayList<>();

        if (nextSymbol instanceof Nonterminal) {
            // Collect all predictions for this rule
            grammar.rules((Nonterminal) nextSymbol)
                   .map(rule1 -> rule1.toItem(index))
                   .forEachOrdered(predictions::add);
        }

        predictions.removeAll(items);
        return predictions;
    }

    @NotNull
    Queue<Item> getQueue() {
        return new LinkedList<Item>(items) {
            @Override
            public boolean addAll(Collection<? extends Item> c) {
                HashSet<Item> temp = new HashSet<>(c);
                temp.removeAll(StateSet.this.items);
                StateSet.this.items.addAll(temp);
                return super.addAll(temp);
            }

            @Override
            public boolean add(Item item) {
                return StateSet.this.add(item) && super.add(item);
            }
        };
    }
}
