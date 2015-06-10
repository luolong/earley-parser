package info.tepp.earley.parser;

import static info.tepp.earley.parser.Symbol.Nonterminal;
import static info.tepp.earley.parser.Symbol.Sequence;

public class SimpleArithmetic {

    static final Nonterminal Sum = new Nonterminal("Sum");
    static final Nonterminal Product = new Nonterminal("Product");
    static final Nonterminal Factor = new Nonterminal("Factor");
    static final Nonterminal Number = new Nonterminal("Number");

    static final Nonterminal Expression = new Nonterminal("Expression");

    static final Sequence PLUS_OP      = new Sequence("+");
    static final Sequence MINUS_OP     = new Sequence("-");
    static final Sequence TIMES_OP     = new Sequence("*");
    static final Sequence DIVISION_OP  = new Sequence("/");
    static final Sequence LPAREN       = new Sequence("(");
    static final Sequence RPAREN       = new Sequence(")");

    static final Sequence ZERO         = new Sequence("0");
    static final Sequence ONE          = new Sequence("1");
    static final Sequence TWO          = new Sequence("2");
    static final Sequence THREE        = new Sequence("3");
    static final Sequence FOUR         = new Sequence("4");
    static final Sequence FIVE         = new Sequence("5");
    static final Sequence SIX          = new Sequence("6");
    static final Sequence SEVEN        = new Sequence("7");
    static final Sequence EIGHT        = new Sequence("8");
    static final Sequence NINE         = new Sequence("9");


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
