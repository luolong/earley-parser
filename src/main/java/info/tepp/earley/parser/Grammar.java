package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Stream;

import static info.tepp.earley.parser.NullableSymbols.findNullableSymbols;
import static java.util.stream.Collectors.joining;

/**
 * Grammar consists of a set of production rules
 */
public class Grammar {

    private final Set<Rule> rules;
    private final Set<Nonterminal> nullables;

    public static Grammar of(@Nonnull Rule... rules) {
        TreeSet<Rule> set = new TreeSet<>(Comparator.<Rule>naturalOrder());
        set.addAll(Arrays.asList(rules));
        return new Grammar(set);
    }

    private Grammar(@Nonnull Set<Rule> rules) {
        this.rules = Collections.unmodifiableSet(rules);
        this.nullables = Collections.unmodifiableSet(findNullableSymbols(rules));
    }

    public @Nonnull Set<Rule> getRules() {
        return Collections.unmodifiableSet(rules);
    }

    public Stream<Rule> rules() {
        return rules.stream();
    }

    public Stream<Rule> rules(@Nonnull Nonterminal left) {
        return rules.stream().filter(r -> left.equals(r.getLeft()));
    }

    public boolean isNullable(Nonterminal symbol) {
        return nullables.contains(symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grammar other = (Grammar) o;
        return Objects.equals(rules, other.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rules);
    }

    @Override
    public String toString() {
        return rules.stream()
                .map(Object::toString)
                .collect(joining("\n"));
    }

    public Reconizer getRecognizer(Nonterminal start) {
        return new Reconizer(this, start);
    }

    public Set<Nonterminal> getNullableSymbols() {
        return new HashSet<>();
    }

}
