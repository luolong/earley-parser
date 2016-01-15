package info.tepp.parser.earley;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SymbolTest {

    @Test
    public void symbolEquality() throws Exception {
        assertEquals(new Symbol("A"), new Symbol("A"));
    }

    @Test
    public void symbolHashCode() throws Exception {
        assertEquals(new Symbol("A").hashCode(), new Symbol("A").hashCode());
    }

    @Test
    public void symbolToString() throws Exception {
        assertEquals("A", new Symbol("A").toString());
    }

    @Test
    public void symbolHasAName() throws Exception {
        assertEquals("A", new Symbol("A").getName());
    }
}
