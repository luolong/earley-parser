package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class RuleTest {

    @Test
    public void lhsSymbolOfTheRule() throws Exception {
        assertEquals(new Symbol("Product"), new Rule(new Symbol("Product"), new Production()).getSymbol());
    }

    @Test
    public void rhsProductionOfTheRule() throws Exception {
        assertEquals(new Production(new Symbol("Product"), new Symbol("[*/]"), new Symbol("Factor")),
            new Rule(new Symbol("Product"),
                     new Production(new Symbol("Product"), new Symbol("[*/]"), new Symbol("Factor")))
                .getProduction());
    }

    @Test
    public void ruleEquality() throws Exception {
        assertThat(new Rule(new Symbol("Number"), new Production(new Symbol("[0-9]"), new Symbol("Number"))))
        .isEqualTo(new Rule(new Symbol("Number"), new Production(new Symbol("[0-9]"), new Symbol("Number"))));
    }

    @Test
    public void ruleHashCode() throws Exception {
        assertThat(new Rule(new Symbol("Number"), new Production(new Symbol("[0-9]"), new Symbol("Number")))
            .hashCode())
        .isEqualTo(new Rule(new Symbol("Number"), new Production(new Symbol("[0-9]"), new Symbol("Number")))
            .hashCode());
    }

    @Test
    public void ruleToString() throws Exception {
        assertThat(new Rule(new Symbol("Number"), new Production(new Symbol("[0-9]"), new Symbol("Number"))).toString())
        .isEqualTo("Number -> [0-9] Number");
    }
}
