package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;

import info.tepp.parser.earley.grammars.Expression;
import org.junit.Test;

public class GrammarTest {

    @Test
    public void grammarIsASetOfRules() throws Exception {
        assertThat(Expression.grammar()).containsExactly(Expression.rules());
    }

    @Test
    public void grammarSizeIsCountOfUniqueRules() throws Exception {
        assertThat(Expression.grammar()).hasSize(Expression.rules().length);
    }
}
