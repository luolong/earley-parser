package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Nonterminal;
import info.tepp.parser.earley.Symbol.Terminal;
import org.junit.Test;

import static info.tepp.parser.earley.DottedRule.DOT;
import static info.tepp.parser.earley.Rule.ARROW;
import static org.junit.Assert.assertEquals;

public class DottedRuleTest {

    public static final Nonterminal A = new Nonterminal("A");
    public static final Symbol foo = new Terminal("foo");
    public static final Symbol c = new Nonterminal("c");

    private Rule rule = Rule.symbol(A).produces(foo, c);

    @Test
    public void toStringAtPositionZero() {
        assertEquals("A " + ARROW + " " + DOT + " \"foo\" c",
                     rule.dot().toString());
    }

    @Test
    public void toStringAtPositionOne() {
        assertEquals("A " + ARROW + " \"foo\" " + DOT + " c",
                     rule.dot().next().toString());
    }

    @Test
    public void atPositionOne() {
        assertEquals(1, rule.dot().next().getPosition());
    }

    @Test
    public void toStringAtPositionTwo() {
        assertEquals("A " + ARROW + " \"foo\" c " + DOT,
                rule.dot().next().next().toString());
    }

    @Test
    public void atPositionTwo() {
        assertEquals(2, rule.dot().next().next().getPosition());
    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void indexOutOfBounds() {
        new DottedRule(rule, 3);
    }

    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void indexOutOfBounds1() {
        new DottedRule(rule, -1);
    }

}