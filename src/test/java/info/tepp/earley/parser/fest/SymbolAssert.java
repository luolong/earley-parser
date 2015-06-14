package info.tepp.earley.parser.fest;

import info.tepp.earley.parser.Symbol;
import org.fest.assertions.api.AbstractComparableAssert;
import org.fest.assertions.api.AbstractUnevenComparableAssert;
import org.fest.assertions.core.ComparableAssert;

public class SymbolAssert extends AbstractComparableAssert<SymbolAssert, Symbol>
        implements ComparableAssert<SymbolAssert, Symbol>{

    public static SymbolAssert assertThat(Symbol symbol) {
        return new SymbolAssert(symbol);
    }

    protected SymbolAssert(Symbol actual) {
        super(actual, SymbolAssert.class);
    }


}
