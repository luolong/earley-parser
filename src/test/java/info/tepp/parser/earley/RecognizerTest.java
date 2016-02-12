package info.tepp.parser.earley;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import info.tepp.parser.earley.grammars.Expression;
import org.junit.Test;

public class RecognizerTest {

    @Test
    public void recognizerIsBoundToAGrammar() throws Exception {
        final Grammar g = Expression.grammar();
        final Symbol sum = Expression.Symbols().Sum;
        assertSame(g, g.recognize(sum).getGrammar());
    }
}
