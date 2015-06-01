package info.tepp.parser.earley;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

import static info.tepp.parser.earley.NonterminalTest.A;
import static info.tepp.parser.earley.NonterminalTest.B;
import static info.tepp.parser.earley.TerminalTest.a;
import static info.tepp.parser.earley.TerminalTest.b;

@RunWith(Parameterized.class)
public class RuleToStringTest {

    private final String expected;
    private final Rule rule;

    @Parameters(name = "{index}: {1}")
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(
                new Object[] {"A → B \"a\"",         new Rule(A, /* -> */ B, a)},
                new Object[] {"A → A \"ab\" B",      new Rule(A, /* -> */ A, a, b, B)},
                new Object[] {"A → \"a\" A \"b\" B", new Rule(A, /* -> */ a, A, b, B)},
                new Object[] {"A → \"a\" A B \"b\"", new Rule(A, /* -> */ a, A, B, b)},
                new Object[] {"A → A B \"ab\"",      new Rule(A, /* -> */ A, B, a, b)},
                new Object[] {"A → \"ab\" A B",      new Rule(A, /* -> */ a, b, A, B)},
                new Object[] {"A → A B",             new Rule(A, /* -> */ A, B)},
                new Object[] {"A → \"ab\"",          new Rule(A, /* -> */ a, b)}
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
