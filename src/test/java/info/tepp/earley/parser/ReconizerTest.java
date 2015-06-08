package info.tepp.earley.parser;

import org.junit.Test;

import static info.tepp.earley.parser.SimpleArithmetic.Sum;
import static info.tepp.earley.parser.SimpleArithmetic.grammar;
import static org.junit.Assert.*;

public class ReconizerTest {

    @Test
    public void test() {
        assertTrue(grammar.getRecognizer(Sum).recognizes("1+(2*3-4)"));
    }
}