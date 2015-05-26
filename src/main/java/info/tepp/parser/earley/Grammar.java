package info.tepp.parser.earley;

import java.util.*;

/**
 * Grammar consists of a set of production rules
 */
public class Grammar {

    private final Set<Rule> rules;

    public static Grammar of(Rule ... rules) {
        List<Rule> list = Arrays.asList(rules);
        return new Grammar(new HashSet<>(list));
    }

    public Grammar(HashSet<Rule> rules) {
        this.rules = rules;
    }

    public Set<Rule> getRules() {
        return Collections.unmodifiableSet(rules);
    }
}
