package info.tepp.earley.parser;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static info.tepp.earley.parser.NonterminalTest.A;
import static info.tepp.earley.parser.TerminalTest.a;
import static info.tepp.earley.parser.TerminalTest.b;
import static org.junit.Assert.assertEquals;

public class GrammarTest {

    private final Grammar grammar = Grammar.of(new Rule(A, a, A, b), new Rule(A, a, b));

    @Test
    public void grammarIsASetOfRules() throws Exception {
        assertEquals(
                setOf(new Rule(A, a, A, b), new Rule(A, a, b)),
                grammar.getRules());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void grammarRulesAreUnmodifiable() throws Exception {
        Grammar.of( new Rule(A) ).getRules().add(
                new Rule(A, /* -> */ a, A, b)
        );
    }

    @Test
    public void twoGrammarsAreEqualIfTheyhaveSameSetOfRules() throws Exception {
        assertEquals(
                Grammar.of(new Rule(A, a, A, b), new Rule(A, a, b)),
                Grammar.of(new Rule(A, a, A, b), new Rule(A, a, b))
        );
    }

    @Test
    public void grammarToStringReturnsAllRulesSeparategByLinebreak() throws Exception {
        assertEquals(String.join("\n",
                        "A → \"a\" A \"b\"",
                        "A → \"ab\""
                ),
                grammar.toString());
    }

    private Set<Rule> setOf(Rule... rules) {
        return new HashSet<>(Arrays.asList(rules));
    }


}
