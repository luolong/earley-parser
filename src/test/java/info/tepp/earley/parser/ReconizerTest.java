package info.tepp.earley.parser;

import org.junit.Test;

import static info.tepp.earley.parser.NullableGrammar.A;
import static info.tepp.earley.parser.SimpleArithmetic.Sum;
import static info.tepp.earley.parser.SimpleArithmetic.grammar;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ReconizerTest {

    @Test
    public void test() {
        assertTrue(SimpleArithmetic.grammar.getRecognizer(Sum).recognizes("1+(2*3-4)"));
    }

    @Test
    public void nullableRecognizesEmptyInput() throws Exception {
        assertTrue(NullableGrammar.grammar.getRecognizer(A).recognizes(""));
    }
    @Test
    public void recognizesB() throws Exception {
        assertTrue(NullableGrammar.grammar.getRecognizer(A).recognizes("b"));
    }
    @Test
    public void recognizesA() throws Exception {
        assertTrue(NullableGrammar.grammar.getRecognizer(A).recognizes("a"));
    }
    @Test
    public void recognizesBB() throws Exception {
        assertTrue(NullableGrammar.grammar.getRecognizer(A).recognizes("()"));
    }
}