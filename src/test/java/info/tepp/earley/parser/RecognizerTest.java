package info.tepp.earley.parser;

import org.junit.Test;

import java.util.stream.Stream;

import static info.tepp.earley.parser.SimpleArithmetic.Expression;
import static info.tepp.earley.parser.SimpleArithmetic.grammar;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class RecognizerTest {

    private final Reconizer recognizer = grammar.getRecognizer(Expression);

    @Test
    public void initialStateSetContainsAllProductionsForStartingSymbol() throws Exception {
        assertEquals(
                stateSet(0, grammar.rules(Expression)),
                recognizer.getStates(0));
    }

    @Test
    public void initialStateSetIsZero() throws Exception {
        assertSame(recognizer.getStates(0), recognizer.getCurentStates());
    }

    private StateSet stateSet(int index, Stream<Rule> rules) {
        return new StateSet(index, rules.map(r -> r.toItem(index)).collect(toList()));
    }
}
