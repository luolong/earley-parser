package info.tepp.parser.earley;

import java.util.Objects;

/**
 * Either {@link info.tepp.parser.earley.Symbol.Terminal terminal}
 * or {@link info.tepp.parser.earley.Symbol.Nonterminal nonterminal}
 * symbol in a rule.
 */
public abstract class Symbol {

    private Symbol() {/* sealed */}

    /**
     * Terminal symbol of a parsing rule.
     */
    public static final class Terminal extends Symbol implements CharSequence {
        public final CharSequence value;

        public Terminal(CharSequence value) {
            this.value = value;
        }

        public CharSequence getValue() {
            return value;
        }

        @Override
        public int length() {
            return value.length();
        }

        @Override
        public char charAt(int index) {
            return value.charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return value.subSequence(start, end);
        }

        @Override
        public String toString() {
            return "\"" + value + "\"";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Terminal terminal = (Terminal) o;
            return Objects.equals(value, terminal.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    /**
     * Non-terminal symbol of a parsing rule.
     */
    public static final class Nonterminal extends Symbol {
        private final String name;

        public Nonterminal(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
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
    }

}
