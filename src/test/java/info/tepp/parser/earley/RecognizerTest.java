package info.tepp.parser.earley;

import info.tepp.parser.earley.grammars.Expression;
import org.junit.Test;

public class RecognizerTest {

    @Test
    public void canGetRecognizerFromGrammar() throws Exception {
        Recognizer recognizer = Expression.grammar().recognize(Expression.Symbols().Sum);
    }

}
