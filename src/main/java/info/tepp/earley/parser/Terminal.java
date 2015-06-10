package info.tepp.earley.parser;

/**
 * Created by rolandt on 10.06.15.
 */
public interface Terminal {
    boolean matches(char nextChar);
}
