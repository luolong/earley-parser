package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Nonterminal;
import info.tepp.parser.earley.Symbol.Terminal;
import org.junit.Test;

public class GrammarTest {

    @Test(expected = java.lang.UnsupportedOperationException.class)
    public void rulesAreImmutable() {
        new Grammar()
                .getRules()
                .add(new Rule(new Nonterminal("Grammar"), new Terminal("0")));
    }
}