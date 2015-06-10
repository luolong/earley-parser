package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Sequence;
import org.junit.Test;

import java.util.Arrays;

import static info.tepp.earley.parser.NonterminalTest.A;
import static info.tepp.earley.parser.NonterminalTest.B;
import static info.tepp.earley.parser.SequenceTest.a;
import static info.tepp.earley.parser.SequenceTest.b;
import static org.junit.Assert.*;

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
        RULE.getRight().add(SequenceTest.b);
    }

    @Test
    public void rulesAreEqualByValue() {
        assertEquals(
            new Rule(A, B, a),
            new Rule(A, B, a)
        );
    }

    @Test
    public void symbolAtPositionReturnsSingleTerminalCharacter0() {
        assertEquals(a, new Rule(A, new Sequence("abc")).getSymbolAt(0));
    }

    @Test
    public void symbolAtPositionReturnsSingleTerminalCharacter1() {
        assertEquals(b, new Rule(A, new Sequence("abc")).getSymbolAt(1));
    }

    @Test
    public void symbolAtPositionReturnsNullAtEndPosition() {
        assertNull(new Rule(A, new Sequence("abc")).getSymbolAt(3));
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

    @Test
    public void nonterminaRuleCompareToTerminaRule() throws Exception {
        Sequence a = new Sequence("A");
        Nonterminal A = new Nonterminal("A");

        Rule A_to_nonterminal = A.to(a, A, a);
        Rule A_to_terminal = A.to(a, a, A);

        assertTrue(
                String.format("Expecting [%s] < [%s]", A_to_nonterminal, A_to_terminal),
                A_to_nonterminal.compareTo(A_to_terminal) < 0);
    }

    @Test
    public void terminalRuleCompareToNonterminaRule() throws Exception {
        Rule A_to_nonterminal = A.to(new Nonterminal("A"));
        Rule A_to_terminal = A.to(new Sequence("A"));

        assertTrue("",
                A_to_terminal.compareTo(A_to_nonterminal) > 0);
    }

    @Test
    public void rulesCompareSameAsSymbols0() {
        Sequence a = new Sequence("A");
        Nonterminal A = new Nonterminal("A");

        assertEquals("Expecting A<=>\"A\" same as [A→A]<=>[A→\"A\"]",
                     A.compareTo(a), A.to(A).compareTo(A.to(a)));
    }

    @Test
    public void rulesCompareSameAsSymbols1() {
        Sequence a = new Sequence("A");
        Nonterminal A = new Nonterminal("A");

        Rule A_to_a = A.to(a);
        Rule A_to_A = A.to(A);
        assertEquals("Expecting \"A\"<=>A same as [A→\"A\"]<=>[A→A]",
                a.compareTo(A), A_to_a.compareTo(A_to_A));
    }
}