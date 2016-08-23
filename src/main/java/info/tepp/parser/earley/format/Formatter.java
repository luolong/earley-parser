package info.tepp.parser.earley.format;

import info.tepp.parser.earley.Symbol;

public class Formatter {

    public String format(Symbol symbol) {
        return new SymbolFormatter(symbol).toString();
    }

    private static class SymbolFormatter {
        private final Symbol symbol;

        public SymbolFormatter(Symbol symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
