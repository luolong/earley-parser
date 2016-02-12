package info.tepp.parser.earley;

import static info.tepp.parser.earley.Helpers.symbol;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SymbolTest {

    @Test
    public void symbolHasAName() throws Exception {
        assertEquals("A", symbol("A").getName());
    }

    @Test
    public void symbolEquality() throws Exception {
        assertEquals(symbol("A"), symbol("A"));
    }

    @Test
    public void symbolHashCode() throws Exception {
        assertEquals(symbol("A").hashCode(), symbol("A").hashCode());
    }

    @Test
    public void symbolToString() throws Exception {
        assertEquals("A", symbol("A").toString());
    }
}
