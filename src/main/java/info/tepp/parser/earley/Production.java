package info.tepp.parser.earley;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Production is a list of symbols on the right hand side of the Earley grammar rule.
 */
public class Production extends AbstractList<Symbol> implements List<Symbol> {
    private final Symbol[] symbols;

    public Production(Symbol ... symbols) {
        this.symbols = symbols;
    }

    /**
     * {@inheritDoc}
     *
     * @param index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Symbol get(int index) {
        return symbols[index];
    }

    @Override
    public int size() {
        return symbols.length;
    }

    @Override
    public String toString() {
        return Stream.of(symbols).map(Symbol::toString).collect(Collectors.joining(" "));
    }
}
