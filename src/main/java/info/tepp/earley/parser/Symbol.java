package info.tepp.earley.parser;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Objects;

public abstract class Symbol implements Comparable<Symbol> {
    private Symbol() {/* sealed class */}

    public static class Nonterminal extends Symbol implements Comparable<Symbol> {
        private final String name;

        public Nonterminal(@Nonnull String name) {
            this.name = name;
        }

        public @Nonnull String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Nonterminal that = (Nonterminal) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(@Nonnull Symbol other) {
            if (other instanceof Nonterminal) {
                return this.name.compareToIgnoreCase(((Nonterminal) other).name);
            }

            return 1;
        }

        public Rule to(Symbol... symbols) {
            return new Rule(this, symbols);
        }
    }

    public static class Terminal extends Symbol implements Comparable<Symbol> {
        private final CharSequence term;

        public Terminal(char character) {
            this.term = String.valueOf(character);
        }

        public Terminal(@Nonnull CharSequence term) {
            this.term = term;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Terminal terminal = (Terminal) o;
            return Objects.equals(term, terminal.term);
        }

        @Override
        public int hashCode() {
            return Objects.hash(term);
        }

        @Override
        public String toString() {
            return "\"" + term + "\"";
        }

        public CharSequence getTerm() {
            return term;
        }

        @Override
        public int compareTo(@Nonnull Symbol other) {
            if (other instanceof Terminal) {
                return Comparator.compare(this.term, ((Terminal) other).term);
            }

            return -1;
        }

        public boolean matches(char nextChar) {
            return term.charAt(0) == nextChar;
        }

    }

    private static final Comparator<CharSequence> Comparator = (cs1, cs2) -> {
        if (cs1 == cs2) return 0;
        if (cs1 == null) return -1;
        if (cs2 == null) return 1;

        int len1 = cs1.length();
        int len2 = cs2.length();
        int lim = Math.min(len1, len2);

        for (int k = 0; k < lim; k++) {
            char c1 = cs1.charAt(k);
            char c2 = cs2.charAt(k);
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return len1 - len2;
    };
}
