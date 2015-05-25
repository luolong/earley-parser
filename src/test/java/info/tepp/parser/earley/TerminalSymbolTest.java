package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Terminal;
import org.junit.Test;

import static org.junit.Assert.*;

public class TerminalSymbolTest {

    private final Terminal A = new Terminal("A");

    @Test
    public void toString_printsDoubleQuotes() throws Exception {
        assertEquals("\"A\"", A.toString());
    }

    @Test
    public void lengthIs1() throws Exception {
        assertEquals(A.getValue().length(), A.length());
    }

    @Test
    public void charAt() throws Exception {
        assertEquals(A.getValue().charAt(0), A.charAt(0));
    }

    @Test
    public void stringContentEquals() throws Exception {
        assertTrue("\"A\".contentEquals(A)", "A".contentEquals(A));
    }

    @Test
    public void sameCodePointsAsInput() throws Exception {
        assertArrayEquals("A".codePoints().toArray(), A.codePoints().toArray());
    }
}