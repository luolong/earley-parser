package info.tepp.parser.earley;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A single production rule mapping a non-terminal symbol
 * to a sequence of production symbols
 */
public class Rule {

    private Symbol.Nonterminal left;
    private final Symbol[] right;

    public Rule(Symbol.Nonterminal left, Symbol... right) {
        this.left = left;
        this.right = right;
    }

    public Symbol.Nonterminal getLeft() {
        return left;
    }

    public List<Symbol> getRight() {
        return Arrays.asList(right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule other = (Rule) o;
        return Objects.equals(this.left, other.left) &&
                Arrays.equals(this.right, other.right);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(new Object[] { left, right });
    }

    @Override
    public String toString() {
        return left.toString() + " → " + getRight().stream().map(Object::toString).collect(Collectors.joining(" "));
    }
}
