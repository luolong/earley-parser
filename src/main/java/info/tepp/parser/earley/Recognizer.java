package info.tepp.parser.earley;

/**
 * Recognizer reads a character input and tries to recognize if it matches the rules of the grammar.
 */
public class Recognizer {
    private final Grammar grammar;

    public Recognizer(Grammar grammar) {
        this.grammar = grammar;
    }

    public Grammar getGrammar() {
        return grammar;
    }
}
