package info.tepp.earley.parser;

import static info.tepp.earley.parser.Symbol.Nonterminal;
import static info.tepp.earley.parser.Symbol.Terminal;

public class SimpleArithmetic {

    static final Nonterminal Sum = new Nonterminal("Sum");
    static final Nonterminal Product = new Nonterminal("Product");
    static final Nonterminal Factor = new Nonterminal("Factor");
    static final Nonterminal Number = new Nonterminal("Number");

    static final Nonterminal Expression = new Nonterminal("Expression");

    static final Terminal PLUS_OP      = new Terminal("+");
    static final Terminal MINUS_OP     = new Terminal("-");
    static final Terminal TIMES_OP     = new Terminal("*");
    static final Terminal DIVISION_OP  = new Terminal("/");
    static final Terminal LPAREN       = new Terminal("(");
    static final Terminal RPAREN       = new Terminal(")");

    static final Terminal ZERO         = new Terminal("0");
    static final Terminal ONE          = new Terminal("1");
    static final Terminal TWO          = new Terminal("2");
    static final Terminal THREE        = new Terminal("3");
    static final Terminal FOUR         = new Terminal("4");
    static final Terminal FIVE         = new Terminal("5");
    static final Terminal SIX          = new Terminal("6");
    static final Terminal SEVEN        = new Terminal("7");
    static final Terminal EIGHT        = new Terminal("8");
    static final Terminal NINE         = new Terminal("9");


    static final Grammar grammar = Grammar.of(
            Sum.to(Sum, PLUS_OP, Product),
            Sum.to(Sum, MINUS_OP, Product),
            Sum.to(Product),

            Product.to(Product, TIMES_OP, Factor),
            Product.to(Product, DIVISION_OP, Factor),
            Product.to(Factor),

            Factor.to(LPAREN, Sum, RPAREN),
            Factor.to(Number),

            Number.to(ZERO, Number),
            Number.to(ZERO),

            Number.to(ONE, Number),
            Number.to(ONE),

            Number.to(TWO, Number),
            Number.to(TWO),

            Number.to(THREE, Number),
            Number.to(THREE),

            Number.to(FOUR, Number),
            Number.to(FOUR),

            Number.to(FIVE, Number),
            Number.to(FIVE),

            Number.to(SIX, Number),
            Number.to(SIX),

            Number.to(SEVEN, Number),
            Number.to(SEVEN),

            Number.to(EIGHT, Number),
            Number.to(EIGHT),

            Number.to(NINE, Number),
            Number.to(NINE)
    );
}
