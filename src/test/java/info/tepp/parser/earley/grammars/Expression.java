package info.tepp.parser.earley.grammars;

import info.tepp.parser.earley.Grammar;
import info.tepp.parser.earley.Production;
import info.tepp.parser.earley.Rule;
import info.tepp.parser.earley.Symbol;

public class Expression {

    private final static Symbol PLUSMINUS = new Symbol("[+-]");
    private final static Symbol MULTDIV = new Symbol("[*/]");
    private final static Symbol RPAR = new Symbol("')'");
    private final static Symbol LPAR = new Symbol("'('");
    private final static Symbol DIGIT = new Symbol("[0-9]");

    private final static Symbol Sum = new Symbol("Sum");
    private final static Symbol Product = new Symbol("Product");
    private final static Symbol Factor = new Symbol("Factor");
    private final static Symbol Number = new Symbol("Number");

    private final Grammar g = new Grammar(
        // Sum = Sum [+-] Product | Product
        new Rule(Sum, new Production(Sum, PLUSMINUS, Product)),
        new Rule(Sum, new Production(Product)),

        // Product = Product [*/] Factor | Factor
        new Rule(Product, new Production(Product, MULTDIV, Factor)),
        new Rule(Product, new Production(Factor)),

        // Factor  = '(' Sum ')' | Number
        new Rule(Factor, new Production(LPAR, Sum, RPAR)),
        new Rule(Factor, new Production(Number)),

        // Number  = [0-9]+
        new Rule(Number, new Production(DIGIT, Number)),
        new Rule(Number, new Production(DIGIT))
    );

    public static Grammar grammar() {
        return new Expression().g;
    }

    public static Rule[] rules() {
        return new Rule[] {
            // Sum = Sum [+-] Product | Product
            new Rule(Sum, new Production(Sum, PLUSMINUS, Product)),
            new Rule(Sum, new Production(Product)),

            // Product = Product [*/] Factor | Factor
            new Rule(Product, new Production(Product, MULTDIV, Factor)),
            new Rule(Product, new Production(Factor)),

            // Factor  = '(' Sum ')' | Number
            new Rule(Factor, new Production(LPAR, Sum, RPAR)),
            new Rule(Factor, new Production(Number)),

            // Number  = [0-9]+
            new Rule(Number, new Production(DIGIT, Number)),
            new Rule(Number, new Production(DIGIT))
        };
    }

    public static Symbol[] symbols() {
        return new Symbol[] {
            // Terminal symbols
            PLUSMINUS, MULTDIV, RPAR, LPAR, DIGIT,

            // Nonterminal symbols
            Sum, Product, Factor, Number
        };
    }
}
