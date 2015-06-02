package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Terminal;
import org.junit.Test;

import static info.tepp.earley.parser.TerminalTest.a;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        assertTrue(
                "Nonterminal symbol should compare as less than Terminal symbol",
                A.compareTo(new Terminal("A")) < 0);
    }
}