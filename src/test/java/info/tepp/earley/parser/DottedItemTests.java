package info.tepp.earley.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;

import static info.tepp.earley.parser.NonterminalTest.A;
import static info.tepp.earley.parser.NonterminalTest.B;
import static info.tepp.earley.parser.SequenceTest.a;
import static info.tepp.earley.parser.SequenceTest.b;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DottedItemTests {

    private final Rule RULE = new Rule(A, /* -> */ a, b, A, B, b, a, B, A);

    private final String expect;
    private final Item item;

    @Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(
                new Object[] {"A → • \"ab\" A B \"ba\" B A (0)"      , 0},
                new Object[] {"A → \"a\" • \"b\" A B \"ba\" B A (0)" , 1},
                new Object[] {"A → \"ab\" • A B \"ba\" B A (0)"      , 2},
                new Object[] {"A → \"ab\" A • B \"ba\" B A (0)"      , 3},
                new Object[] {"A → \"ab\" A B • \"ba\" B A (0)"      , 4},
                new Object[] {"A → \"ab\" A B \"b\" • \"a\" B A (0)" , 5},
                new Object[] {"A → \"ab\" A B \"ba\" • B A (0)"      , 6},
                new Object[] {"A → \"ab\" A B \"ba\" B • A (0)"      , 7},
                new Object[] {"A → \"ab\" A B \"ba\" B A • (0)"      , 8}
        );
    }

    public DottedItemTests(String expect, int dotPosition) {
        this.expect = expect;
        this.item = new Item(RULE, dotPosition, 0);
    }

    @Test
    public void toStringInsertsDotToCorrectPosition(){
        assertEquals(expect, item.toString());
    }

    @Test
    public void currentSymbolReturnsSymbolAtDotPosition() {
        assertEquals(RULE.getSymbolAt(item.getDot()), item.getCurrent());
    }
}
