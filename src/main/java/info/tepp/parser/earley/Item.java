package info.tepp.parser.earley;

import lombok.Value;

@Value
public class Item
{
    Rule rule;
    int position;

    static Item start(Rule rule) {
        return new Item(rule, 0);
    }
}
