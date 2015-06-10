package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static info.tepp.earley.parser.NullableSymbols.findNullableSymbols;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class NullableSymbolsTest {

    static final Nonterminal A = Symbol.named("A");
    static final Nonterminal B = Symbol.named("B");
    static final Symbol.Terminal a = Symbol.term("a");
    static final Symbol.Terminal b = Symbol.term("b");

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

    private <S extends Symbol> Set<S> asSet(S... symbols) {
        return Arrays.stream(symbols).collect(toSet());
    }
}
