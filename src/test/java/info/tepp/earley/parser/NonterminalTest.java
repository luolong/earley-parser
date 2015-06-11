package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.fest.SymbolAssert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NonterminalTest {

    public static final Nonterminal A = new Nonterminal("A");
    public static final Nonterminal B = new Nonterminal("B");

    @Test
    public void nonterminalHasName() throws Exception {
        assertEquals("A", A.getName());
    }

    @Test
    public void nonterminalEqualsByValue() throws Exception {
        assertEquals(new Nonterminal("A"), new Nonterminal("A"));
    }

    @Test
    public void nonterminalToStringReturnsName() {
        assertEquals("A", A.toString());
    }

    @Test
    public void compareToTerminalSymbolAlwaysSmaller() throws Exception {
        SymbolAssert.assertThat(A).isLessThan(Symbol.sequence("A"));
    }
}