package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Terminal;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TerminalTest {

    public static final Terminal a = new Terminal("a");
    public static final Terminal b = new Terminal("b");

    public void terminalHasTerm() {
        assertEquals("a", a.getTerm());
    }

    @Test
    public void terminalSymbolsEqualByValue() throws Exception {
        assertEquals(new Terminal("a"), new Terminal("a"));
    }

    @Test
    public void terminalToStringReturnsTermInDoubleQuotes() {
        assertEquals("\"a\"", a.toString());
    }
}