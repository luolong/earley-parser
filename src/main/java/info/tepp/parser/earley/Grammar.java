package info.tepp.parser.earley;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Grammar consists of a set of production rules
 */
public class Grammar {

    private final Set<Rule> rules;

    public static Grammar of(@Nonnull Rule ... rules) {
        TreeSet<Rule> set = new TreeSet<>(Comparator.<Rule>naturalOrder());
        set.addAll(Arrays.asList(rules));
        return new Grammar(set);
    }

    private Grammar(@Nonnull Set<Rule> rules) {
        this.rules = rules;
    }

    public @Nonnull Set<Rule> getRules() {
        return Collections.unmodifiableSet(rules);
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
}
