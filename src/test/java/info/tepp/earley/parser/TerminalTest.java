package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Terminal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    @Test
    public void terminalComparesGreaterThanNonterminal() throws Exception {
        assertTrue("Terminal symbol should compare as greater than Nonterminal symbol",
                a.compareTo(new Nonterminal("a")) > 0);
    }

    @Test
    public void terminalSymbolsAreComparedCharByChar() throws Exception {
        Terminal abc1 = new Terminal("abc");
        Terminal abc2 = new Terminal("abc");
        assertTrue(abc1.compareTo(abc2) == 0);
    }
}