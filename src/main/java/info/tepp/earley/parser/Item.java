package info.tepp.earley.parser;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Item {

    private static final String DOT = "•";

    private final Rule rule;
    private final int dot;
    private final int start;

    /**
     * Construct a new item for a rule with specific dot position and starting index;
     */
    public Item(@Nonnull Rule rule, int dot, int start) {
        checkDotPositionRange(dot, rule.getProduction().length());
        checkIndexPositionRange(start);

        this.rule = rule;
        this.dot = dot;
        this.start = start;
    }

    @Nonnull
    public Symbol.Nonterminal getLeft() {
        return getRule().getLeft();
    }

    @Nonnull
    public List<Symbol> getRight() {
        return getRule().getRight();
    }

    /**
     * Checks if rule item's start position is within valid integer range.
     *
     * In this context this only checks if the start is at negative position.
     */
    private static void checkIndexPositionRange(int index) {
        if (index < 0) throw new ItemIndexNegativeException(index);
    }

    /**
     * Checks if rule itam's dot position is within valid range.
     *
     * Valid position range is {@code [0..rule.production.length]},
     * meaning this must fall within production positions.
     */
    private static void checkDotPositionRange(int dot, int length) {
        if (dot < 0 || dot > length)
            throw new DotIndexOutOfBoundsException(dot);
    }

    /**
     * The rule that this item represents.
     */
    public @Nonnull Rule getRule() {
        return rule;
    }

    /**
     * Position the current position of partial parse of this rule.
     */
    public int getDot() {
        return dot;
    }

    /**
     * The starting position for this item in the input stream.
     */
    public int getStart() {
        return start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item other = (Item) o;
        return Objects.equals(start, other.start) &&
                Objects.equals(rule, other.rule) &&
                Objects.equals(dot, other.dot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, rule, dot);
    }

    @Override
    public String toString() {
        StringJoiner join = new StringJoiner(" ");
        join.add(rule.getLeft().toString()).add(Rule.ARROW);

        String production = rule.getProduction();
        Iterator<Symbol.Nonterminal> nonterminals = rule.nonTerminals().iterator();
        if (dot > 0) {
            join.add(Rule.formatProduction(nonterminals, production.substring(0, dot)));
        }

        join.add(DOT);

        if (dot < production.length()) {
            join.add(Rule.formatProduction(nonterminals, production.substring(dot, production.length())));
        }

        return join.add("(" + start + ")").toString();
    }

    public Symbol getCurrent() {
        return rule.getSymbolAt(dot);
    }

    public Item next() {
        return new Item(rule, dot+1, start);
    }

    /**
     * Thrown when constructing an Item whose dot position falls outlide production sequence.
     */
    public static class DotIndexOutOfBoundsException extends IndexOutOfBoundsException {
        public DotIndexOutOfBoundsException(int index) {
            super(String.valueOf(index));
        }
    }

    public static class ItemIndexNegativeException extends IndexOutOfBoundsException {
        public ItemIndexNegativeException(int index) {
            super(String.valueOf(index));
        }
    }
}
