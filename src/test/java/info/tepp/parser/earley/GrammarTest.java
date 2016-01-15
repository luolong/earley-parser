package info.tepp.parser.earley;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

public class GrammarTest {

    @Test
    public void grammasIsASetOfRules() throws Exception {
        Set<Rule> g = new Grammar();
    }

    @Test
    @Ignore
    public void canCreateAGrammarWithASingleRule() throws Exception {
        assertTrue(new Grammar(new Rule(new Symbol("Product"), new Production()))
                     .contains(new Rule(new Symbol("Product"), new Production())));
    }
}
