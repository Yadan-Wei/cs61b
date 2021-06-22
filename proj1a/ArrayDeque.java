/** Please be noted add isEmpty method, or the auto grader couldn't
 * find the API to do next step.
 * for method or variable, if we don't want they are accessed out of this class,
 * Please set them to private.
 * ArrayDeque is a double sided queue, it has two pointers, first and last.
 * Each add first will make the first move forward one step while last the opposite.
 * when first reaches to the head of the array, it will move to the end of the array
 * while last moves to the head of the array when it reaches the end.
 */
public class ArrayDeque<T> {
	private int size;
	private int nextFirst;
	private int nextLast;
	private T[] items;

	public ArrayDeque() {
		items = (T[]) new Object[8];
		size = 0;
		nextFirst = (items.length - size) / 2;
		nextLast = nextFirst + 1;
	}

	public int size() {
		return size;
	}

	private boolean isFull() {
		return size == items.length;
	}
    /* add first item and resize if array is full.*/
	public void addFirst(T item) {
		if (isFull()) {
			resize(items.length, 2);
		}
		items[nextFirst] = item;
		nextFirst = minusOne(nextFirst);
		size = size + 1;
	}
    /* add last item and resize if array is full.*/
	public void addLast(T item) {
        if (isFull()) {
            resize(items.length, 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size = size + 1;
	}
    /* remove and return the first item in the queue and check the size*/
	public T removeFirst() {
	    if (size == 0) {
	        return null;
        }
        int first = plusOne(nextFirst);
        T res = items[first];
        nextFirst = first;
        items[first] = null;
        size = size - 1;
        if (checkResize()) {
            resize(items.length, 0.5);
        }
        return res;
	}
	public T removeLast() {
	    if (size == 0) {
	        return null;
        }
	    int last = minusOne(nextLast);
	    T res = items[last];
	    nextLast = last;
	    items[last] = null;
	    size = size - 1;
	    if (checkResize()) {
	        resize(items.length, 0.5);
        }
	    return res;
	}
	/** is this index start from the one before nextFirst, if so, we need
	 * compare the length of items and the first + index.*/
	public T get(int index) {
	    if (index >= size) {
	        return null;
        }
	    int first = plusOne(nextFirst);
	    if (first + index < items.length) {
			return items[first + index];
		}
	    return items[first + index - items.length];
	}
	/**It is impossible to use copy directly approach to resize.
	 * we need |2|3|__|4|5| not |2|3|__|4|5|_|_|*/
    private void resize(int length, double factor) {
    	/* Pay attention to data type.*/
    	int newLength = (int) (length * factor);
    	T[] a = (T[]) new Object[newLength];
		int first = plusOne(nextFirst);
		int leftLength = first + size - length;
		/** from |_|_|_|_|1|2|_|_|_|_| to |_|_|_|1|2| or
		 * from |_|_|3|4|_| to |_|_|_|_|_|_|_|_|3|4|, change nextFirst and nextLast*/
    	if (leftLength <= 0) {
    		System.arraycopy(items, first, a, (int) newLength - size, size);
    		nextFirst = minusOne(newLength - size);
    		nextLast = 0;
		/** from |1|_|_|_|_|_|_|_|_|2| to |1|_|_|_|2|
		 * from |3|4|_|1| to |3|4|_|_|_|_|_|1|, need to change nextFirst. */
		} else {
			System.arraycopy(items, 0, a, 0, leftLength);
			System.arraycopy(items, first, a, first + newLength - length, length - first);
			nextFirst = minusOne(first + newLength - length);
		}
		items = a;
    }

    private boolean checkResize() {
		if (items.length >= 16) {
			return (((double) size) / items.length < 0.25);
		}
		return false;
    }

	private int minusOne(int index) {
		if (index == 0) {
			return items.length - 1;
		}
		return index - 1;
	}

	private int plusOne(int index) {
		if (index == items.length - 1) {
			return 0;
		}
		return index + 1;
	}

	public boolean isEmpty() {
    	return size == 0;
	}

	public void printDeque() {
		for (int i = 0; i < size; i++) {
			System.out.print(get(i) + " ");
		}
		System.out.println();
	}
}
