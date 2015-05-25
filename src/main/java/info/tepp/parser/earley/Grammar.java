package info.tepp.parser.earley;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * Grammar for an Earley parser.
 *
 * <p>For the purposes of Earley parsing, a <i>grammar</i> is
 * just a set of <i>{@link Rule production rules}</i>.</p>
 */
public class Grammar {

    private final Rule[] rules;

    public Grammar(Rule... rules) {
        this.rules = rules;
    }


    public Collection<Rule> getRules() {
        return Collections.unmodifiableCollection(Arrays.asList(rules));
    }

    @Override
    public String toString() {
        return stream(rules)
                .map(Object::toString)
                .collect(joining("\n"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grammar rules1 = (Grammar) o;
        return Arrays.equals(rules, rules1.rules);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rules);
    }

}
