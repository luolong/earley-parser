package info.tepp.earley.parser;

import info.tepp.earley.parser.Item.DotIndexOutOfBoundsException;
import org.junit.Assert;
import org.junit.Test;

import static info.tepp.earley.parser.NonterminalTest.A;
import static info.tepp.earley.parser.NonterminalTest.B;
import static info.tepp.earley.parser.RuleTest.RULE;
import static info.tepp.earley.parser.TerminalTest.a;
import static org.junit.Assert.assertEquals;

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

    @Test(expected = Item.ItemIndexNegativeException.class)
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

}