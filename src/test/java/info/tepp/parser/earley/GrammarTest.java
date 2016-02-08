package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;
import static java.util.stream.Collectors.toList;

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
        assertThat(Expression.grammar().symbols().collect(toList()))
            .containsExactly((Object[]) Expression.symbols());
    }
}
