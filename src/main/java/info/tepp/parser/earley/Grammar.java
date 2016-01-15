package info.tepp.parser.earley;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Grammar is a set of rules.
 */
public class Grammar extends AbstractSet<Rule> implements Set<Rule> {
    public Grammar(Rule ... rules) {
    }

    /**
     * Returns an iterator over rules contained in this grammar.
     */
    @Override
    public Iterator<Rule> iterator() {
        return null;
    }

    /**
     * The count of individual rules that make up this grammar.
     */
    @Override
    public int size() {
        return 0;
    }
}
