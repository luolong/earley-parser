package info.tepp.earley.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class RuleToStringTest {

    private final String expected;
    private final Rule rule;

    @Parameters(name = "{index}: {1}")
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(
                new Object[] {"A → B \"a\"",         new Rule(NonterminalTest.A, /* -> */ NonterminalTest.B, TerminalTest.a)},
                new Object[] {"A → A \"ab\" B",      new Rule(NonterminalTest.A, /* -> */ NonterminalTest.A, TerminalTest.a, TerminalTest.b, NonterminalTest.B)},
                new Object[] {"A → \"a\" A \"b\" B", new Rule(NonterminalTest.A, /* -> */ TerminalTest.a, NonterminalTest.A, TerminalTest.b, NonterminalTest.B)},
                new Object[] {"A → \"a\" A B \"b\"", new Rule(NonterminalTest.A, /* -> */ TerminalTest.a, NonterminalTest.A, NonterminalTest.B, TerminalTest.b)},
                new Object[] {"A → A B \"ab\"",      new Rule(NonterminalTest.A, /* -> */ NonterminalTest.A, NonterminalTest.B, TerminalTest.a, TerminalTest.b)},
                new Object[] {"A → \"ab\" A B",      new Rule(NonterminalTest.A, /* -> */ TerminalTest.a, TerminalTest.b, NonterminalTest.A, NonterminalTest.B)},
                new Object[] {"A → A B",             new Rule(NonterminalTest.A, /* -> */ NonterminalTest.A, NonterminalTest.B)},
                new Object[] {"A → \"ab\"",          new Rule(NonterminalTest.A, /* -> */ TerminalTest.a, TerminalTest.b)}
        );
    }

    public RuleToStringTest(String expected, Rule rule) {
        this.expected = expected;
        this.rule = rule;
    }

    @Test
    public void test() {
        Assert.assertEquals(expected, rule.toString());
    }
}
