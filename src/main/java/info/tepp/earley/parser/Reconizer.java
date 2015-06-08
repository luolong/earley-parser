package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Recognizes an input string as belonging to a grammar.
 */
public class Reconizer {

    private final Grammar grammar;
    private final Nonterminal start;
    private final ArrayList<StateSet> stateSets;

    public Reconizer(Grammar grammar, Nonterminal start) {
        this.grammar = grammar;
        this.start = start;

        stateSets = new ArrayList<>();
        List<Item> items = grammar
                .rules(start)
                .map(rule -> new Item(rule, 0, 0))
                .collect(Collectors.toList());

        stateSets.add(new StateSet(0, items));
    }

    public StateSet getStates(int index) {
        return stateSets.get(index);
    }

    public StateSet getCurentStates() {
        return stateSets.get(0);
    }

    public boolean recognizes(String input) {
        int length = input.length();
        stateSets.ensureCapacity(length);
        int i = 0;
        while (i < stateSets.size()) {
            StateSet next = stateSets.get(i).scan(input,
                    new StateSet.Prediction(grammar),
                    new StateSet.Completion(stateSets));

            if (next.isEmpty()) break;
            stateSets.add(next);

            i += 1;
        }

        return i == input.length() && stateSets.get(i).stream()
                .anyMatch(item -> item.isDone() && item.getStart() == 0 && item.getLeft() == start);

    }
}
