package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Terminal;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * A single production rule mapping a non-terminal symbol
 * to a sequence of production symbols
 */
public class Rule implements Comparable<Rule> {

    public static final String ARROW = "→";
    public static final char OBJECT_REPLACEMENT_CHARACTER = '\uFFFC';

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
                        return String.valueOf(OBJECT_REPLACEMENT_CHARACTER);
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

    /**
     * Returns new Item from this rule at specified starting index with dot position 0
     */
    public Item toItem(int start) {
        return new Item(this, 0, start);
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
        return Arrays.deepHashCode(new Object[]{left, right});
    }

    @Override
    public String toString() {
        String formatted = formatProduction(nonTerminals().iterator(), production);
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

            start = index + 1;
            index = production.indexOf(OBJECT_REPLACEMENT_CHARACTER, start);
        }

        if (start < production.length()) {
            if (formatted.length() > 0) formatted += " ";
            formatted += '"' + production.substring(start) + '"';
        }

        return formatted;
    }

    Stream<Nonterminal> nonTerminals() {
        return stream(right)
                .filter(s -> s instanceof Nonterminal)
                .map(s -> (Nonterminal) s);
    }

    @Override
    public int compareTo(@Nonnull Rule other) {
        return unit(Comparator(this.nonTerminals().iterator(),
                               other.nonTerminals().iterator())
               .compare(this.production, other.production));
    }

    private int unit(int number) {
        if (number < 0) return -1;
        if (number > 0) return 1;
        return 0;
    }

    private static java.util.Comparator<CharSequence> Comparator(Iterator<Nonterminal> n1, Iterator<Nonterminal> n2) {
        return (cs1, cs2) -> {
            if (cs1 == cs2) return 0;
            if (cs1 == null) return -1;
            if (cs2 == null) return 1;

            int len1 = cs1.length();
            int len2 = cs2.length();
            int lim = Math.min(len1, len2);

            for (int k = 0; k < lim; k++) {
                char c1 = cs1.charAt(k);
                char c2 = cs2.charAt(k);

                boolean nb1 = (c1 == OBJECT_REPLACEMENT_CHARACTER);
                boolean nb2 = (c1 == OBJECT_REPLACEMENT_CHARACTER);

                if (nb1 && nb2) {
                    boolean b1 = n1.hasNext();
                    boolean b2 = n2.hasNext();

                    if (b1 && b2) {
                        Nonterminal nt1 = n1.next();
                        Nonterminal nt2 = n2.next();

                        int cmp = nt1.compareTo(nt2);
                        if (cmp != 0) return cmp;
                    }
                    else if (b1) return 1;
                    else if (b2) return -1;
                }

                if (c1 != c2) {
                    return nb1 ? -1 : nb2 ? 1 : c1 - c2;
                }
            }
            return len1 - len2;
        };
    }

    public Symbol getSymbolAt(int index) {
        if (index < 0 || index > production.length())
            throw new IndexOutOfBoundsException(String.valueOf(index));

        if (index < production.length()) {
            char ch = production.charAt(index);
            if (ch == OBJECT_REPLACEMENT_CHARACTER) {
                long count = production.chars().limit(index)
                        .filter(c -> c == OBJECT_REPLACEMENT_CHARACTER)
                        .count();

                return nonTerminals().skip(count)
                        .findFirst().get();
            }
            return new Terminal(ch);
        }

        return null;
    }
}
