package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class RuleTest {

    @Test
    public void lhsSymbolOfTheRule() throws Exception {
        assertThat(newRule().getSymbol()).isEqualTo(createLhsSymbol());
    }

    @Test
    public void rhsProductionOfTheRule() throws Exception {
        assertThat(newRule().getProduction()).isEqualTo(createRhsProduction());
    }

    @Test
    public void ruleEquality() throws Exception {
        assertThat(newRule()).isEqualTo(newRule());
    }

    @Test
    public void ruleHashCode() throws Exception {
        assertThat(newRule().hashCode()).isEqualTo(newRule().hashCode());
    }

    @Test
    public void ruleToString() throws Exception {
        assertThat(new Rule(new Symbol("A"),
                   new Production(new Symbol("A"), new Symbol("b"), new Symbol("C"))).toString())
            .isEqualTo("A -> A b C");
    }


    //
    // Private helper methods
    //

    private Rule newRule() {
        return new Rule(createLhsSymbol(), createRhsProduction());
    }

    private Production createRhsProduction() {
        return new Production(new Symbol("A"), new Symbol("b"), new Symbol("C"));
    }

    private Symbol createLhsSymbol() {
        return new Symbol("A");
    }

}
