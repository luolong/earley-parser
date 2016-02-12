package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;
import static info.tepp.parser.earley.Helpers.produces;
import static info.tepp.parser.earley.Helpers.rule;
import static info.tepp.parser.earley.Helpers.symbol;

import org.junit.Test;

public class RuleTest {

    @Test
    public void gettingLhsSymbolOfTheRule() throws Exception {
        assertThat(rule("A", produces("A", "b", "C")).getSymbol())
            .isEqualTo(symbol("A"));
    }

    @Test
    public void gettingRhsProductionOfTheRule() throws Exception {
        assertThat(rule("A", produces("A", "b", "C")).getProduction())
            .isEqualTo(produces("A", "b", "C"));
    }

    @Test
    public void ruleEquality() throws Exception {
        assertThat(rule("A", produces("A", "b", "C")))
            .isEqualTo(rule("A", produces("A", "b", "C")));
    }

    @Test
    public void ruleHashCode() throws Exception {
        assertThat(rule("A", produces("A", "b", "C")).hashCode())
            .isEqualTo(rule("A", produces("A", "b", "C")).hashCode());
    }

    @Test
    public void ruleToString() throws Exception {
        assertThat(rule("A", produces("A", "b", "C")).toString())
            .isEqualTo("A -> A b C");
    }

}
