package info.tepp.parser.earley;

/**
 * Recognizer reads a character input and tries to recognize if it matches the rules of the grammar.
 */
public class Recognizer {
    private final Grammar grammar;
    private final Symbol startSymbol;

    public Recognizer(Grammar grammar, Symbol startSymbol) {
        this.grammar = grammar;
        this.startSymbol = startSymbol;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public Symbol getStartSymbol() {
        return startSymbol;
    }

    public Parse read( CharSequence input ) {
        return new Parse();
    }

}
