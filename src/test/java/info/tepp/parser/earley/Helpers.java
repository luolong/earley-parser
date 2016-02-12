package info.tepp.parser.earley;

import java.util.stream.Stream;

public final class Helpers {
    private Helpers() {/* sealed constructor */}


    public static Rule rule(String symbolName, Production production) {
        return new Rule(symbol(symbolName), production);
    }

    public static Production produces(String... symbolNames) {
        return new Production(Stream.of(symbolNames).map(Symbol::new).toArray(Symbol[]::new));
    }

    public static Symbol symbol(String name) {
        return new Symbol(name);
    }
}
