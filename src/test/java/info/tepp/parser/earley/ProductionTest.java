package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;
import static info.tepp.parser.earley.Helpers.produces;
import static info.tepp.parser.earley.Helpers.symbol;

import org.junit.Test;

public class ProductionTest {

    @Test
    public void productionIsAListOfSymbols() throws Exception {
        assertThat(produces("A", "B", "'c'"))
            .containsExactly(symbol("A"), symbol("B"), symbol("'c'"))
            .inOrder();
    }

    @Test
    public void productionHashCode() throws Exception {
        assertThat(produces("A", "'a'").hashCode())
            .isEqualTo(produces("A", "'a'").hashCode());
    }

    @Test
    public void productionEquals() throws Exception {
        assertThat(produces("A", "'a'"))
            .isEqualTo(produces("A","'a'"));
    }

    @Test
    public void productionToString() throws Exception {
        assertThat(produces("A", "'a'").toString())
            .isEqualTo("A 'a'");
    }
}
