package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Nonterminal;
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
}