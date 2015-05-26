package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Nonterminal;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GrammarTest {

    @Test
    public void grammarIsASetOfRules() throws Exception {
        Grammar grammar = Grammar.of(new Rule(NonterminalTest.A));
        assertEquals(setOf(new Rule(new Nonterminal("A"))), grammar.getRules());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void grammarIsASetOfUnmodifiableRules() throws Exception {
        Grammar.of( new Rule(new Nonterminal("A")) ).getRules().add(
                new Rule(new Nonterminal("A"))
        );
    }

    private Set<Rule> setOf(Rule... rules) {
        return new HashSet<>(Arrays.asList(rules));
    }
}
