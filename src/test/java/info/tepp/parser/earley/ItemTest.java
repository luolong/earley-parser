package info.tepp.parser.earley;

import info.tepp.parser.earley.Item.DotIndexOutOfBoundsException;
import org.junit.Test;

import static info.tepp.parser.earley.RuleTest.RULE;

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


}