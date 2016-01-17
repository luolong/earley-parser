package info.tepp.parser.earley;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class RuleTest {

    @Test
    public void lhsSymbolOfTheRule() throws Exception {
        assertEquals(new Symbol("Product"), new Rule(new Symbol("Product"), new Production()).getSymbol());
    }

    @Test @Ignore
    public void rhsProductionOfTheRule() throws Exception {
        assertEquals(new Production(new Symbol("Product"), new Symbol("[*/]"), new Symbol("Factor")),
            new Rule(new Symbol("Product"),
                     new Production(new Symbol("Product"), new Symbol("[*/]"), new Symbol("Factor")))
                .getProduction());
    }
}
