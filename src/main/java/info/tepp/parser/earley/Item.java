package info.tepp.parser.earley;

public class Item {

    private final DottedRule rule;
    private final int index;

    public Item(DottedRule dottedRule, int index) {
        this.rule = dottedRule;
        this.index = index;
    }

}
