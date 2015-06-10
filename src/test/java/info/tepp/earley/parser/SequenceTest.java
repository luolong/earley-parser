package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Sequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SequenceTest {

    public static final Sequence a = new Sequence("a");
    public static final Sequence b = new Sequence("b");

    @Test
    public void terminalHasTerm() {
        assertEquals("a", a.getSequence());
    }

    @Test
    public void terminalSymbolsEqualByValue() throws Exception {
        assertEquals(new Sequence("a"), new Sequence("a"));
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
        Sequence abc1 = new Sequence("abc");
        Sequence abc2 = new Sequence("abc");
        assertTrue(abc1.compareTo(abc2) == 0);
    }
}