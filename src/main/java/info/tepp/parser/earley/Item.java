package info.tepp.parser.earley;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.StringJoiner;

public class Item {

    private static final String DOT = "•";

    private final Rule rule;
    private final int dot;
    private final int index;

    /**
     * Construct a new item for a rule with specific dot position and starting index;
     */
    public Item(@Nonnull Rule rule, int dot, int index) {
        checkDotPositionRange(dot, rule.getProduction().length());
        checkIndexPositionRange(index);

        this.rule = rule;
        this.dot = dot;
        this.index = index;
    }

    /**
     * Checks if rule item's index position is within valid integer range.
     *
     * In this context this only checks if the index is at negative position.
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

    public int getDot() {
        return dot;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        StringJoiner join = new StringJoiner(" ");
        join.add(rule.getLeft().toString()).add(Rule.ARROW);

        String production = rule.getProduction();
        Iterator<Symbol.Nonterminal> nonterminals = rule.nonTerminals();
        if (dot > 0) {
            join.add(Rule.formatProduction(nonterminals, production.substring(0, dot)));
        }

        join.add(DOT);

        if (dot < production.length()) {
            join.add(Rule.formatProduction(nonterminals, production.substring(dot, production.length())));
        }

        return join.add("[" + index + "]").toString();
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
