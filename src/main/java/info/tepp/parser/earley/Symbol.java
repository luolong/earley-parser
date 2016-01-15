package info.tepp.parser.earley;

import java.util.Objects;

/**
 * Symbol of an Earley grammar
 */
public class Symbol {
    private final String name;

    public Symbol(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the symbol
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Symbol && Objects.equals(this.name, ((Symbol) obj).name);
    }

    @Override
    public String toString() {
        return name;
    }
}
