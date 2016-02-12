package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;
import static info.tepp.parser.earley.Helpers.asList;

import info.tepp.parser.earley.grammars.Expression;
import org.junit.Test;

public class GrammarTest {

    @Test
    public void grammarIsASetOfRules() throws Exception {
        assertThat(Expression.grammar())
            .containsExactly((Object[]) Expression.rules());
    }

    @Test
    public void grammarSizeIsCountOfUniqueRules() throws Exception {
        assertThat(Expression.grammar())
            .hasSize(Expression.rules().length);
    }

    @Test
    public void grammarHasStreamOfSymbols() throws Exception {
        assertThat(asList(Expression.grammar().symbols()))
            .containsExactly((Object[]) Expression.symbols());
    }

    @Test
    public void grammarHasStreamOfRules() throws Exception {
        assertThat(asList(Expression.grammar().rules()))
            .containsExactly((Object[]) Expression.rules());
    }

}
