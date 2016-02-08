package info.tepp.parser.earley;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * Grammar is a set of rules.
 */
public class Grammar extends AbstractSet<Rule> implements Set<Rule> {
    private final Rule[] rules;

    public Grammar(Rule ... rules) {
        this.rules = Stream.of(rules).distinct().toArray(Rule[]::new);
    }

    /**
     * Returns an iterator over rules contained in this grammar.
     */
    @Override
    public Iterator<Rule> iterator() {
        return rules().iterator();
    }

    @Override
    public Spliterator<Rule> spliterator() {
        return Arrays.spliterator(rules);
    }

    /**
     * The count of individual rules that make up this grammar.
     */
    @Override
    public int size() {
        return rules.length;
    }

    /**
     * Returns a sequential {@link Stream stream} of {@link Rule rules} making up this grammar.
     */
    public final Stream<Rule> rules() {
        return stream();
    }

    /**
     * Returns a {@link Stream stream} of all unique symbols used in this grammar.
     */
    public Stream<Symbol> symbols() {
        return rules().flatMap(rule -> Stream.concat(
            Stream.of(rule.getSymbol()),
            rule.getProduction().symbols()
        )).distinct();
    }
}
