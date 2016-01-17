package info.tepp.parser.earley;

import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.List;

public class ProductionTest {

    @Test
    public void productionIsAListOfSymbols() throws Exception {
        List<Symbol> production = new Production(new Symbol("A"), new Symbol("B"), new Symbol("c"));
    }
}
