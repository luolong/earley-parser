package info.tepp.parser.earley;

import org.junit.Test;

import java.util.Arrays;

import static info.tepp.parser.earley.NonterminalTest.*;
import static org.junit.Assert.assertEquals;

public class RuleTest {

    public static final Rule RULE = new Rule(A, B, TerminalTest.a);

    @Test
    public void leftSideOfTheRuleIsANonterminal() throws Exception {
        assertEquals(A, RULE.getLeft());
    }

    @Test
    public void rightSideOfTheRuleIsAListOfSymbols() throws Exception {
        assertEquals(Arrays.asList(B, TerminalTest.a), RULE.getRight());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void RuleProductionSymbolsListIsUnmodifiable() throws Exception {
        RULE.getRight().add(TerminalTest.b);
    }

    @Test
    public void rulesAreEqualByValue() {
        assertEquals(
                new Rule(A, B, TerminalTest.a),
                new Rule(A, B, TerminalTest.a)
        );
    }

    @Test
    public void ruleToStringUsesBnfFormat() {
        assertEquals("A → B \"a\"", RULE.toString());
    }
}