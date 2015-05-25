package info.tepp.parser.earley;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class DottedRule extends Rule {

    public static final String DOT = "•";

    private final int position;

    public DottedRule(Rule rule, int position) {
        super(rule.left, rule.right);
        if (position < 0 || position > right.length) {
            throw new IndexOutOfBoundsException(String.valueOf(position));
        }

        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        Stream<String> stream = Stream.of(left.toString(), ARROW);
        if (position > 0)
            stream = Stream.concat(stream, Arrays.stream(right).limit(position).map(Object::toString));

        stream = Stream.concat(stream, Stream.of(DOT));

        if (position < right.length)
            stream = Stream.concat(stream, Arrays.stream(right).skip(position).map(Object::toString));

        return stream.collect(joining(" "));
    }

    public DottedRule dot() {
        return this;
    }

    public DottedRule next() {
        return new DottedRule(this, position + 1);
    }
}
