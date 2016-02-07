package info.tepp.parser.earley;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class ProductionTest {

    @Test
    public void productionIsAListOfSymbols() throws Exception {
        assertThat(new Production(new Symbol("A"), new Symbol("B"), new Symbol("'c'")))
            .containsExactly(new Symbol("A"), new Symbol("B"), new Symbol("'c'"));
    }

    @Test
    public void productionHashCode() throws Exception {
        assertThat(new Production(new Symbol("A"), new Symbol("'a'")).hashCode())
            .isEqualTo(new Production(new Symbol("A"), new Symbol("'a'")).hashCode());
    }

    @Test
    public void productionEquals() throws Exception {
        assertThat(new Production(new Symbol("A"), new Symbol("'a'")))
            .isEqualTo(new Production(new Symbol("A"), new Symbol("'a'")));
    }

    @Test
    public void productionToString() throws Exception {
        assertThat(new Production(new Symbol("A"), new Symbol("'a'")).toString())
            .isEqualTo("A 'a'");
    }
}
