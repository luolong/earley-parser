package info.tepp.parser.earley;

import java.util.Objects;

public abstract class Symbol {
    private Symbol() {/* sealed class */}

    public static class Nonterminal extends Symbol {
        private final String name;

        public Nonterminal(String name) {
            this.name = name;
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

        public String getName() {
            return name;
        }
    }

    public static class Terminal extends Symbol {
        private final CharSequence term;

        public Terminal(CharSequence term) {
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
    }
}
