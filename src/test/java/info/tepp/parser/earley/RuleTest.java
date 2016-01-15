package info.tepp.parser.earley;

import static org.junit.Assert.*;

import org.junit.Test;

public class RuleTest {

    @Test
    public void lhsOfARule() throws Exception {
        assertEquals(new Symbol("Product"), new Rule(new Symbol("Product"), new Production()).getLhsSymbol());
    }
}
