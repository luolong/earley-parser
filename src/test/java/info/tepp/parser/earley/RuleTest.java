package info.tepp.parser.earley;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RuleTest {

    public static final Symbol.Nonterminal A = new Symbol.Nonterminal("A");
    public static final Symbol.Terminal b = new Symbol.Terminal("b");

    @Test
    public void toStringReturnsProductionRule() {
        assertEquals("A " + Rule.ARROW + " \"b\" A",
                new Rule(A, /* -> */ b, A).toString());
    }

    public void ruleBuilder() {
        assertEquals(new Rule(A, b, A), Rule.symbol(A).produces(b, A));
    }

}