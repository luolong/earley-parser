package info.tepp.parser.earley.grammars;

import info.tepp.parser.earley.Grammar;
import info.tepp.parser.earley.Production;
import info.tepp.parser.earley.Rule;
import info.tepp.parser.earley.Symbol;

public class Expression {

    public static Symbols Symbols() {
        return new Symbols();
    }

    public static final class Symbols {
        public final Symbol PLUSMINUS = new Symbol("[+-]");
        public final Symbol MULTDIV = new Symbol("[*/]");
        public final Symbol RPAR = new Symbol("')'");
        public final Symbol LPAR = new Symbol("'('");
        public final Symbol DIGIT = new Symbol("[0-9]");

        public final Symbol Sum = new Symbol("Sum");
        public final Symbol Product = new Symbol("Product");
        public final Symbol Factor = new Symbol("Factor");
        public final Symbol Number = new Symbol("Number");

        public Symbol[] all() {
            return new Symbol[] {
                // Terminal symbols
                PLUSMINUS, MULTDIV, RPAR, LPAR, DIGIT,

                // Nonterminal symbols
                Sum, Product, Factor, Number
            };
        }
    }

    public static Rules Rules() {
        return new Rules();
    }

    public static final class Rules {
        final Symbols s = new Symbols();
        public Rule[] all() {
            return new Rule[] {
                // Sum = Sum [+-] Product | Product
                new Rule(s.Sum, new Production(s.Sum, s.PLUSMINUS, s.Product)),
                new Rule(s.Sum, new Production(s.Product)),

                // Product = Product [*/] Factor | Factor
                new Rule(s.Product, new Production(s.Product, s.MULTDIV, s.Factor)),
                new Rule(s.Product, new Production(s.Factor)),

                // Factor  = '(' Sum ')' | Number
                new Rule(s.Factor, new Production(s.LPAR, s.Sum, s.RPAR)),
                new Rule(s.Factor, new Production(s.Number)),

                // Number  = [0-9]+
                new Rule(s.Number, new Production(s.DIGIT, s.Number)),
                new Rule(s.Number, new Production(s.DIGIT))
            };
        }
    }

    private final Grammar g = new Grammar(new Rules().all());

    public static Grammar grammar() {
        return new Expression().g;
    }

    public static Rule[] rules() {
        return new Rules().all();
    }

    public static Symbol[] symbols() {
        return new Symbols().all();
    }
}
