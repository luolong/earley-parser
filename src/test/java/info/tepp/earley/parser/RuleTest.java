package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Terminal;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static info.tepp.earley.parser.NonterminalTest.A;
import static info.tepp.earley.parser.NonterminalTest.B;
import static info.tepp.earley.parser.TerminalTest.a;
import static info.tepp.earley.parser.TerminalTest.b;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RuleTest {

    public static final Rule RULE = new Rule(A, B, a);

    @Test
    public void leftSideOfTheRuleIsANonterminal() throws Exception {
        assertEquals(A, RULE.getLeft());
    }

    @Test
    public void rightSideOfTheRuleIsAListOfSymbols() throws Exception {
        assertEquals(Arrays.asList(B, a), RULE.getRight());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void RuleProductionSymbolsListIsUnmodifiable() throws Exception {
        RULE.getRight().add(TerminalTest.b);
    }

    @Test
    public void rulesAreEqualByValue() {
        assertEquals(
            new Rule(A, B, a),
            new Rule(A, B, a)
        );
    }

    @Test
    public void ruleComparison() throws Exception {
        assertEquals(1, new Rule(A, b).compareTo(new Rule(A, a)));
    }

    @Test
    public void ruleComparison2() throws Exception {
        assertEquals(1, new Rule(A, B).compareTo(new Rule(A, a)));
    }

    @Test
    public void symbolAtPositionReturnsSingleTerminalCharacter0() {
        assertEquals(a, new Rule(A, new Terminal("abc")).getSymbolAt(0));
    }

    @Test
    public void symbolAtPositionReturnsSingleTerminalCharacter1() {
        assertEquals(b, new Rule(A, new Terminal("abc")).getSymbolAt(1));
    }

    @Test
    public void symbolAtPositionReturnsNullAtEndPosition() {
        assertNull(new Rule(A, new Terminal("abc")).getSymbolAt(3));
    }

    @Test
    public void symbolAtPositionReturnsNonterminal0() {
        assertEquals(A, new Rule(A, A, B).getSymbolAt(0));
    }

    @Test
    public void symbolAtPositionReturnsNonterminal2() {
        assertEquals(B, new Rule(A, A, a, B).getSymbolAt(2));
    }

    @Test
    public void symbolAtPositionReturnsNullAtEndPosition1() {
        assertNull(new Rule(A, A, a, B).getSymbolAt(3));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void symbolAtPositionReturnsNullAfterEndPosition() {
        new Rule(A, A, a, B).getSymbolAt(4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void symbolAtPositionReturnsNullForNegativePosition() {
        new Rule(A, A, a, B).getSymbolAt(-1);
    }
}