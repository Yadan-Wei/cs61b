package synthesizer;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        /* test isEmpty() */
        assertTrue(arb.isEmpty());
        /* test dequeue exception with Junit version 4.12
         @source
        * https://github.com/junit-team/junit4/wiki/Exception-testing */
        try {
            arb.dequeue();
            fail("Expected a RuntimeException to be Thrown");
        } catch (RuntimeException anException) {
            assertThat(anException.getMessage(),org.hamcrest.CoreMatchers.is("Ring buffer underflow"));
        }
        /* test enqueue and dequeue */
        arb.enqueue(33);
        assertEquals(33, (int)arb.dequeue());
        assertTrue(arb.isEmpty());
        arb.enqueue(45);
        arb.enqueue(23);
        /*test peek*/
        assertEquals(45, (int)arb.peek());
        arb.enqueue(22);
        arb.enqueue(67);
        arb.enqueue(25);
        /*test isFull() */
        assertTrue(arb.isFull());
        /*test enqueue exception */
        try {
            arb.enqueue(78);
            fail("Expected a RuntimeException to be Thrown");
        } catch (RuntimeException anException) {
            assertThat(anException.getMessage(),org.hamcrest.CoreMatchers.is("Ring buffer overflow"));
        }

        /*test order of enqueue and iterator*/
        Iterator<Integer> arbIterator = arb.iterator();
        int[] expected = new int[]{45,23,22,67,25};
        int i = 0;
        while (arbIterator.hasNext()) {
            assertEquals(expected[i],(int)arbIterator.next());
            i += 1;
        }
    }



    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}

