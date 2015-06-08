package info.tepp.earley.parser;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static info.tepp.earley.parser.SimpleArithmetic.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class CompletionTest {

    Item sum0 = Sum.to(Product).toItem(0);
    Item product0 = Product.to(Product, PLUS_OP, Factor).toItem(0).advance().advance();
    Item product1 = Product.to(Factor).toItem(0);
    Item factor0 = Factor.to(LPAREN, Factor, RPAREN).toItem(0);
    Item factor1 = factor0.advance();
    Item factor2 = factor1.advance();
    Item factor3 = factor2.advance();

    List<StateSet> stateSets = asList(
            StateSet.of(0, sum0, product0, product1, factor0),
            StateSet.of(1, factor1),
            StateSet.of(2, factor2));

    StateSet.Completion completion = new StateSet.Completion(stateSets);

    @Test
    public void itFindsAllPossibleCompletions() {
        assertEquals(asSet(product0.advance(), product1.advance()),
                completion.complete(factor3));
    }

    private Set<Item> asSet(Item... items) {
        return new HashSet<>(asList(items));
    }
}