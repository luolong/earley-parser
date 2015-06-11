package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Terminal;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static info.tepp.earley.parser.NullableSymbols.findNullableSymbols;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class NullableSymbolsTest {

    static final Nonterminal A = Symbol.named("A");
    static final Nonterminal B = Symbol.named("B");
    static final Terminal a = Symbol.sequence("a");
    static final Terminal b = Symbol.sequence("b");

    @Test
    public void leftOfEmptyRuleIsNullable() {
        assertEquals(asSet(A),
                findNullableSymbols(A.toNull(), A.to(a)));
    }

    @Test
    public void findsSymbolThatPointsToEmptyRule() {
        assertEquals(asSet(A, B),
                findNullableSymbols(A.toNull(), A.to(a), A.to(B), B.to(A), B.to(b)));
    }

    @SafeVarargs
    private final <S extends Symbol> Set<S> asSet(S... symbols) {
        return Arrays.stream(symbols).collect(toSet());
    }
}
