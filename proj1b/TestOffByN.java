import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByN = new OffByN(5);

    @Test
    public void testequalChars() {
        assertTrue(offByN.equalChars('a','f'));
        assertFalse(offByN.equalChars('f','e'));
        assertTrue(offByN.equalChars('f','a'));
        assertFalse(offByN.equalChars('f','h'));
    }

}
