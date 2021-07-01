import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testRemoveLast() {
        StudentArrayDeque<Integer> std = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();
        int i = 0;
        String m = "";
        while (i < 10) {
            int randomNumber = StdRandom.uniform(0, 10);
            std.addFirst(randomNumber);
            sol.addFirst(randomNumber);
            m = m + "\naddFirst(" + randomNumber + ")";
            i = i + 1;
        }
        while (i != 0) {
            Integer expected = sol.removeLast();
            Integer actual = std.removeLast();
            m = m + "\nremoveLast()";
            assertEquals(m, expected, actual);
            i = i - 1;
        }

    }
}



