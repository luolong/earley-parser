package info.tepp.parser.earley;

import info.tepp.parser.earley.Symbol.Nonterminal;
import info.tepp.parser.earley.Symbol.Terminal;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * A single production rule mapping a non-terminal symbol
 * to a sequence of production symbols
 */
public class Rule implements Comparable<Rule> {

    public static final String ARROW = "→";
    public static final String OBJECT_REPLACEMENT_CHARACTER = "\uFFFC";

    private final Nonterminal left;
    private final Symbol[] right;

    private final String production;

    public Rule(@Nonnull Nonterminal left, @Nonnull Symbol... right) {
        this.left = left;
        this.right = right;

        this.production = stream(this.right)
                .map((s -> {
                    if (s instanceof Terminal) {
                        return ((Terminal) s).getTerm();
                    } else {
                        return OBJECT_REPLACEMENT_CHARACTER;
                    }
                }))
                .collect(joining());
    }

    public @Nonnull Nonterminal getLeft() {
        return left;
    }

    public @Nonnull List<Symbol> getRight() {
        return Arrays.asList(right);
    }

    public @Nonnull String getProduction() {
        return production;
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
        String formatted = formatProduction(nonTerminals(), production);
        return String.join(" ", left.toString(), ARROW, formatted);
    }

    @Nonnull
    public static String formatProduction(Iterator<Nonterminal> nonterminals, String production) {
        String formatted = "";

        int start = 0,
            index = production.indexOf(OBJECT_REPLACEMENT_CHARACTER);

        while (index > -1) {
            if (formatted.length() > 0) formatted += " ";

            if (index > start) {
                formatted += '"' + production.substring(start, index) + "\" ";
            }

            formatted += nonterminals.next().toString();

            start = index + OBJECT_REPLACEMENT_CHARACTER.length();
            index = production.indexOf(OBJECT_REPLACEMENT_CHARACTER, start);
        }

        if (start < production.length()) {
            if (formatted.length() > 0) formatted += " ";
            formatted += '"' + production.substring(start) + '"';
        }

        return formatted;
    }

    Iterator<Nonterminal> nonTerminals() {
        return stream(right)
                .filter(s -> s instanceof Nonterminal)
                .map(s -> (Nonterminal) s).iterator();
    }

    @Override
    public int compareTo(@Nonnull Rule other) {
        int left = this.left.compareTo(other.left);
        if (left != 0) return left;

        int len1 = right.length;
        int len2 = other.right.length;

        int lim = Math.min(len1, len2);

        for (int k = 0; k < lim; k++) {
            Symbol s1 = this.right[k];
            Symbol s2 = other.right[k];
            int cmp = s1.compareTo(s2);
            if (cmp != 0) {
                return cmp;
            }
        }

        return len1 - len2;
    }
}
