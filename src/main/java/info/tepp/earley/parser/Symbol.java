package info.tepp.earley.parser;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Objects;

public abstract class Symbol implements Comparable<Symbol> {
    private Symbol() {/* sealed class */}

    public static Nonterminal named(String name) {
        return new Nonterminal(name);
    }

    public static Terminal sequence(String term) {
        return new Sequence(term);
    }
    public static Terminal caseOf(String cases) {
        if (cases.length() == 1)
            return new Sequence(cases);

        return new Sequence(cases);
    }

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

            return super.compareTo(other);
        }

        public Rule to(Symbol... symbols) {
            return new Rule(this, symbols);
        }

        public Rule toNull() {
            return new Rule(this);
        }

    }

    public abstract static class Terminal extends Symbol implements Comparable<Symbol> {
        public abstract boolean matches(char nextChar);
    }

    public static class Sequence extends Terminal implements Comparable<Symbol> {
        private final CharSequence sequence;

        public Sequence(char character) {
            this.sequence = String.valueOf(character);
        }

        public Sequence(@Nonnull CharSequence sequence) {
            this.sequence = sequence;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sequence sequence = (Sequence) o;
            return Objects.equals(this.sequence, sequence.sequence);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sequence);
        }

        @Override
        public String toString() {
            return "\"" + sequence + "\"";
        }

        public CharSequence getSequence() {
            return sequence;
        }

        @Override
        public int compareTo(@Nonnull Symbol other) {
            if (other instanceof Sequence) {
                return Comparator.compare(this.sequence, ((Sequence) other).sequence);
            }

            return 1;
        }

        @Override
        public boolean matches(char nextChar) {
            return sequence.charAt(0) == nextChar;
        }

    }

    private static <T extends Symbol> int orderOf(Class<T> type) {
        if (Nonterminal.class.isAssignableFrom(type)) return 0;
        if (Terminal.class.isAssignableFrom(type)) return 1;
        throw new IllegalArgumentException(type.getName());
    }

    @Override
    public int compareTo(@Nonnull Symbol other) {
        return java.util.Comparator
                .nullsLast(ClassOrder)
                .compare(this, other);
    }

    private static final Comparator<Symbol> ClassOrder =
            (Symbol a, Symbol b) -> orderOf(a.getClass()) - orderOf(b.getClass());

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
