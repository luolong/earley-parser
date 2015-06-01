package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Terminal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TerminalTest {

    public static final Terminal a = new Terminal("a");
    public static final Terminal b = new Terminal("b");

    @Test
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