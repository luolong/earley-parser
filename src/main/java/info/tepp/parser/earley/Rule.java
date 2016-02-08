package info.tepp.parser.earley;

import java.util.Objects;

/**
 * Single rule of an earley grammar.
 */
public class Rule {
    private final Symbol symbol;
    private final Production production;

    public Rule(Symbol symbol, Production production) {
        this.symbol = symbol;
        this.production = production;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Production getProduction() {
        return production;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(symbol, rule.symbol) &&
            Objects.equals(production, rule.production);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, production);
    }

    @Override
    public String toString() {
        return symbol + " -> " + production;
    }
}
