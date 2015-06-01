package info.tepp.earley.parser;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static info.tepp.earley.parser.SimpleArithmetic.*;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class StateSetTest {

    @Test
    public void predictNonterminalAfterInitialDot() throws Exception {
        StateSet states = grammar.getRecognizer(Sum).getStates(0);

        assertEquals(items(0, grammar.rules(Product)),
                states.predict(Sum.to(Product).toItem(0).getCurrent(), grammar));
    }

    @Test
    public void addingNewItemsToQueueAddsThemToStateSet() {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        HashSet<Item> before = new HashSet<>(stateSet);

        Queue<Item> queue = stateSet.getQueue();

        assertTrue(queue.addAll(singletonList(Product.to(Factor).toItem(0))));
        assertNotEquals(before, new HashSet<>(stateSet));
    }

    @Test
    public void addingNewItemToQueueAddsItToStateSet() {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        int sizeBefore = stateSet.size();

        Queue<Item> queue = stateSet.getQueue();

        assertTrue(queue.add(Product.to(Factor).toItem(0)));
        assertEquals(sizeBefore + 1, stateSet.size());
    }

    @Test
    public void doNotAddDuplicateItemsToQueue() {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        int sizeBefore = stateSet.size();

        Queue<Item> queue = stateSet.getQueue();

        assertFalse(
                "Nothing should be added to the queue",
                queue.addAll(singletonList(Sum.to(Product).toItem(0))));

        assertEquals(sizeBefore, stateSet.size());
    }

    @Test
    public void nonterminalDoesNotMatchCharacter() throws Exception {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        assertFalse(stateSet.matches('(', Number));
    }

    @Test
    public void terminalMatchesCharacter() throws Exception {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        assertFalse(stateSet.matches('(', LPAREN));
    }

    @Test
    public void doNotAddDuplicateItemToQueue() {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        int sizeBefore = stateSet.size();

        Queue<Item> queue = stateSet.getQueue();

        assertFalse(
                "Nothing should be added to the queue",
                queue.add(Sum.to(Product).toItem(0)));

        assertEquals(sizeBefore, stateSet.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveAnItem() throws Exception {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        Item item = stateSet.iterator().next();
        stateSet.remove(item);
    }

    @SuppressWarnings("CollectionAddedToSelf")
    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveItems() throws Exception {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        stateSet.removeAll(stateSet);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveItemsWithPredicate() throws Exception {
        StateSet stateSet = grammar.getRecognizer(Sum).getStates(0);
        stateSet.removeIf(i -> true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canNotRemoveItemsWithIterator() throws Exception {
        Iterator<Item> iterator = grammar.getRecognizer(Sum).getStates(0).iterator();
        iterator.next();
        iterator.remove();
    }

    List<Item> items(int start, Stream<Rule> rules) {
        return rules.map(rule -> rule.toItem(start)).collect(toList());
    }
}