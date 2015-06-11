package info.tepp.earley.parser;

import info.tepp.earley.parser.Symbol.Nonterminal;
import info.tepp.earley.parser.Symbol.Terminal;

public class NullableGrammar {

    public static final Nonterminal A = Symbol.named("A");
    public static final Nonterminal B = Symbol.named("B");

    public static final Terminal a = Symbol.sequence("a");
    public static final Terminal b = Symbol.sequence("b");

    public static final Terminal LPAR = Symbol.sequence("(");
    public static final Terminal RPAR = Symbol.sequence(")");

    public static final Grammar grammar = Grammar.of(
            A.to(LPAR, A, RPAR),
            A.to(B),

            B.to(a),
            B.to(b),
            B.toNull()
    );
}
