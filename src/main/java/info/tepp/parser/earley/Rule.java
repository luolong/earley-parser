package info.tepp.parser.earley;

/**
 * Single rule of an earley grammar.
 */
public class Rule {
    private Symbol symbol;
    private final Production production;

    public Rule(Symbol symbol, Production production) {
        this.symbol = symbol;
        this.production = production;
    }

    public Symbol getLhsSymbol() {
        return symbol;
    }
}
