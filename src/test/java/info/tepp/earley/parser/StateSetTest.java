package info.tepp.earley.parser;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static info.tepp.earley.parser.SimpleArithmetic.*;
import static info.tepp.earley.parser.SimpleArithmetic.Number;
import static java.util.Collections.emptySet;
import static org.junit.Assert.*;

public class StateSetTest {

    @Test
    public void predicterPredictsNextNonterminalProductions() throws Exception {
        LinkedList<Item> queue = new LinkedList<>();

        StateSet stateSet = new StateSet(0, queue);
        stateSet.predicter(grammar, queue)
                .predict(Sum.to(Product).toItem(0), 0);

        assertEquals(
                asSet(Product.to(Product, TIMES_OP, Factor).toItem(0),
                      Product.to(Product, DIVISION_OP, Factor).toItem(0),
                      Product.to(Factor).toItem(0)),
                asSet(queue));
    }

    @Test
    public void addingSetOfNewItemsToQueueAddsThemToStateSet() {
        StateSet stateSet = new StateSet(0, emptySet());
        Queue<Item> queue = stateSet.getQueue();

        List<Item> items = Arrays.asList(
                Product.to(Product, TIMES_OP, Factor).toItem(0),
                Product.to(Product, DIVISION_OP, Factor).toItem(0));

        assertTrue(queue.addAll(items));
        assertEquals(asSet(items), stateSet);
    }

    @Test
    public void addingSingleNewItemToQueueAddsItToStateSet() {
        StateSet stateSet = new StateSet(0, emptySet());
        int sizeBefore = stateSet.size();

        Queue<Item> queue = stateSet.getQueue();

        assertTrue(queue.add(Product.to(Factor).toItem(0)));
        assertEquals(sizeBefore + 1, stateSet.size());
    }

    @Test
    public void addingSetOfItemsToQueueSkipsDuplicates() {
        StateSet stateSet = StateSet.of(0, Sum.to(Product));
        Queue<Item> queue = stateSet.getQueue();

        assertFalse(
                "Nothing should be added to the queue",
                queue.addAll(asSet(Sum.to(Product).toItem(0))));

        assertEquals(asSet(Sum.to(Product).toItem(0)), stateSet);
    }

    @Test
    public void scannerDoesNotMatchNonterminalSymbol() throws Exception {
        StateSet.Scanner scanner = new StateSet(0, emptySet()).scanner("(1+2)*3");
        assertFalse(scanner.scan(Factor.to(Number).toItem(0)));
    }

    @Test
    public void scannerMatchesTerminalSymbol() throws Exception {
        StateSet.Scanner scanner = new StateSet(0, emptySet()).scanner("(1+2)*3");
        assertTrue(scanner.scan(Factor.to(LPAREN, Sum, RPAREN).toItem(0)));
    }

    @Test
    public void scannerAddsMatchingItemToNextStateSet() throws Exception {
        StateSet.Scanner scanner = new StateSet(0, emptySet()).scanner("(1+2)*3");
        scanner.scan(Factor.to(LPAREN, Sum, RPAREN).toItem(0));

        assertEquals(
                asSet(Factor.to(LPAREN, Sum, RPAREN).toItem(0).advance()),
                scanner.getAdvancedItems());
    }

    @Test
    public void doNotAddDuplicateItemToQueue() {
        StateSet stateSet = StateSet.of(0, Sum.to(Product));
        Queue<Item> queue = stateSet.getQueue();
        int sizeBefore = queue.size();

        assertFalse(
                "Nothing should be added to the queue",
                queue.add(Sum.to(Product).toItem(0)));

        assertEquals(sizeBefore, queue.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveAnItem() throws Exception {
        StateSet stateSet = StateSet.of(0, Sum.to(Product));
        stateSet.remove(stateSet.iterator().next());
    }

    @SuppressWarnings("CollectionAddedToSelf")
    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveItems() throws Exception {
        StateSet stateSet = StateSet.of(0, Sum.to(Product));
        stateSet.removeAll(asSet(Sum.to(Product).toItem(0)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveItemsWithPredicate() throws Exception {
        StateSet stateSet = StateSet.of(0, Sum.to(Product));
        stateSet.removeIf(i -> true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveItemsWithIterator() throws Exception {
        Iterator<Item> iterator = StateSet.of(0, Sum.to(Product)).iterator();
        iterator.next();
        iterator.remove();
    }

    @Test
    public void stateSetPredictsRulesOnTheLeftOfDot() throws Exception {
        Grammar grammar = Grammar.of(
                Sum.to(Sum, PLUS_OP, Product), Sum.to(Sum, MINUS_OP, Product), Sum.to(Product),
                Product.to(Product, TIMES_OP, Factor), Product.to(Product, DIVISION_OP, Factor), Product.to(Factor),
                Factor.to(LPAREN, Sum, RPAREN), Factor.to(Number),
                Number.to(ONE), Number.to(TWO));

        StateSet stateSet = StateSet.of(0,
                Sum.to(Sum, PLUS_OP, Product), Sum.to(Sum, MINUS_OP, Product), Sum.to(Product));

        stateSet.scan("", grammar);

        StateSet expected = StateSet.of(0,
                Sum.to(Sum, PLUS_OP, Product), Sum.to(Sum, MINUS_OP, Product), Sum.to(Product),
                Product.to(Product, TIMES_OP, Factor), Product.to(Product, DIVISION_OP, Factor), Product.to(Factor),
                Factor.to(LPAREN, Sum, RPAREN), Factor.to(Number),
                Number.to(ONE), Number.to(TWO));

        assertEquals(expected, stateSet);
    }

    @Test
    public void stateSetScansRulesStartingWithNextTerminalCharacter() throws Exception {
        Grammar grammar = Grammar.of(
                Sum.to(Sum, PLUS_OP, Product), Sum.to(Sum, MINUS_OP, Product), Sum.to(Product),
                Product.to(Product, TIMES_OP, Factor), Product.to(Product, DIVISION_OP, Factor), Product.to(Factor),
                Factor.to(LPAREN, Sum, RPAREN), Factor.to(Number),
                Number.to(ONE), Number.to(TWO));

        StateSet stateSet = StateSet.of(0,
                Sum.to(Sum, PLUS_OP, Product), Sum.to(Sum, MINUS_OP, Product), Sum.to(Product));

        StateSet nextStateSet = stateSet.scan("1", grammar);

        StateSet expected = StateSet.of(1, Number.to(ONE).toItem(0).advance());

        assertEquals(expected, nextStateSet);
    }

    @SafeVarargs
    private final <T> Set<T> asSet(T... items) {
        return Arrays.stream(items).collect(Collectors.toSet());
    }

    private <T> Set<T> asSet(Collection<T> items) {
        return items.stream().collect(Collectors.toSet());
    }

}