package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Nonterminal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;

/**
 * Production rule
 */
public class Rule {

    public static final String ARROW = "→";

    protected final Nonterminal left;
    protected final Symbol[] right;

    public Rule(Nonterminal left, Symbol... right) {
        this.left = left;
        this.right = right;
    }

    public Nonterminal getLeft() {
        return left;
    }

    public List<Symbol> getRight() {
        return unmodifiableList(asList(right));
    }

    @Override
    public String toString() {
        return Stream.concat(
                    Stream.of(left, ARROW),
                    Arrays.stream(right))
                .map(Object::toString)
                .collect(joining(" "));
    }

    public static PartialRule symbol(Nonterminal left) {
        return new PartialRule(left);
    }

    public static PartialRule symbol(String left) {
        return new PartialRule(new Nonterminal(left));
    }

    public DottedRule dot() {
        return new DottedRule(this, 0);
    }

    public static final class PartialRule {
        private final Nonterminal left;

        public PartialRule(Nonterminal symbol) {
            this.left = symbol;
        }

        public Rule produces(Symbol ... right) {
            return new Rule(left, right);
        }
    }
}
