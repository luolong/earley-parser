package info.tepp.earley.parser;

import info.tepp.earley.parser.Item.DotIndexOutOfBoundsException;
import org.junit.Test;

import static info.tepp.earley.parser.NonterminalTest.A;
import static info.tepp.earley.parser.NonterminalTest.B;
import static info.tepp.earley.parser.RuleTest.RULE;
import static info.tepp.earley.parser.TerminalTest.a;
import static info.tepp.earley.parser.TerminalTest.b;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemTest {

    @Test
    public void itemIsARuleWithDotAndStartingIndex() throws Exception {
        new Item(RULE, 0, 0);
    }

    @Test(expected = DotIndexOutOfBoundsException.class)
    public void itemWithNegativeDotPositionIsAnError() {
        new Item(RULE, -1, 0);
    }

    @Test(expected = DotIndexOutOfBoundsException.class)
    public void itemWithDotPositionExceedingRuleProductionLengthIsAnError() {
        new Item(RULE, RULE.getProduction().length()+1, 0);
    }

    @Test(expected = Item.StartIndexNegativeException.class)
    public void itemWithNegativeIndexIsAnError() {
        new Item(RULE, 0, -1);
    }

    @Test
    public void itemsAreEqual() throws Exception {
        assertEquals(
            new Item(new Rule(A, B, a), 0, 0),
            new Item(new Rule(A, B, a), 0, 0)
        );
    }

    @Test
    public void advanceMovesDotRightByOne() throws Exception {
        assertEquals(
                new Item(new Rule(A, B, a), 1, 0),
                new Item(new Rule(A, B, a), 0, 0).advance()
        );
    }

    @Test(expected = DotIndexOutOfBoundsException.class)
    public void canNotAdvanceOverEnd() throws Exception {
        new Item(new Rule(A, B, a), 2, 0).advance();
    }

    @Test
    public void itemIsDoneWhenDotIsAtTheEnd() throws Exception {
        assertTrue(new Item(A.to(a, b), 2, 0).isDone());
    }
}