package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toCollection;

public class NullableSymbols {

    public static Set<Nonterminal> findNullableSymbols(Rule... rules) {
        return findNullableSymbols(Arrays.asList(rules));
    }

    public static Set<Nonterminal> findNullableSymbols(Collection<Rule> rules) {
        LinkedHashSet<Nonterminal> nullables = rules.stream()
                .filter(Rule::isEmpty)
                .map(Rule::getLeft)
                .collect(toCollection(LinkedHashSet::new));

        // Return earley, when there is no nullable symbols
        if (nullables.isEmpty()) return nullables;

        Map<Nonterminal, Set<Rule>> lhs = new LinkedHashMap<>();
        Map<Nonterminal, Set<Rule>> rhs = new LinkedHashMap<>();

        for (Rule rule : rules) {
            Nonterminal left = rule.getLeft();
            addRule(lhs, left, rule);
            rule.nonTerminals().forEachOrdered(
                    right -> addRule(rhs, right, rule));
        }

        Deque<Nonterminal> workQueue = new LinkedList<>(nullables);
        Nonterminal workSymbol;
        while ((workSymbol = workQueue.poll()) != null) {
            for (Rule workRule : rhs.getOrDefault(workSymbol, emptySet())) {
                Nonterminal left = workRule.getLeft();
                if (nullables.contains(left)) {
                    continue;
                }

                if (workRule.size() == workRule.nonTerminals()
                        .filter(nullables::contains)
                        .count()) {
                    nullables.add(left);
                    workQueue.add(left);
                }
            }
        }

        return nullables;
    }

    private static void addRule(Map<Nonterminal, Set<Rule>> table, Nonterminal symbol, Rule rule) {
        Set<Rule> set = table.get(symbol);
        if (set == null) {
            set = new LinkedHashSet<>();
            table.put(symbol, set);
        }
        set.add(rule);
    }
}
