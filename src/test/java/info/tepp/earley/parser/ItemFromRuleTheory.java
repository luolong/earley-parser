package info.tepp.earley.parser;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class ItemFromRuleTheory {

    public static @DataPoint int START = 0;
    public static @DataPoint int ONE = 1;
    public static @DataPoint int HUNDRED = 100;
    public static @DataPoint int MILLION = 1000000;

    @Theory
    public void Rule_toItem_createsNewItemWithSpecifiedIndex(int index) {
        assumeTrue(index > 0);
        assertEquals(index, RuleTest.RULE.toItem(index).getStart());
    }

    @Theory
    public void Rule_toItem_createsNewItemWithDotPosition0(int index) {
        assumeTrue(index > 0);
        assertEquals(0, RuleTest.RULE.toItem(index).getDot());
    }
}
