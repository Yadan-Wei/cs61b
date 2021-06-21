public class LinkedListDeque<T> {
	private class Node {
		private Node prev;
		private T item;
		private Node next;

		Node(Node a, T b, Node c) {
			prev = a;
			item = b;
			next = c;
		}
	}
	 /* The first item (if it exists) is at sentinel.next.*/
	private Node sentinel;
	private int size;

	/* Create an empty deque*/
	public LinkedListDeque() {
		sentinel = new Node(null, (T) "1", null);
		size = 0;
	}

	public LinkedListDeque(T item) {
		sentinel = new Node(null, null, null);
		sentinel.next = new Node(sentinel, item, null);
		sentinel.prev = sentinel.next;
		size = 1;
	}

	public void addFirst(T item) {
		if (size == 0) {
			sentinel.next = new Node(sentinel, item, sentinel);
			sentinel.prev = sentinel.next;
		} else {
			sentinel.next = new Node(sentinel, item, sentinel.next);
			sentinel.next.next.prev = sentinel.next;
		}
		size = size + 1;
	}

	public void addLast(T item) {
		sentinel.prev = new Node(sentinel.prev, item, sentinel);
		sentinel.prev.prev.next = sentinel.prev;
		size = size + 1;

	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public int size() {
		return size;
	}

	public void printDeque() {
		Node p = sentinel;
		while (p.next != sentinel) {
			p = p.next;
			System.out.print(p.item + " ");
		}
		System.out.println();

	}

	public T removeFirst() {
		if (size == 0) {
			return null;
		}
		T res = sentinel.next.item;
		sentinel.next = sentinel.next.next;
		sentinel.next.prev = sentinel;
		size = size - 1;
		return res;

	}

	public T removeLast() {
		if (size == 0) {
			return null;
		}
		T res = sentinel.prev.item;
		sentinel.prev = sentinel.prev.prev;
		sentinel.prev.next = sentinel;
		size = size - 1;
		return res;

	}

	public T get(int index) {
		if (index > size - 1) {
			return null;
		}
		Node p = sentinel;
		while (index != 0) {
			p = p.next;
			index  = index - 1;
		}
		return p.item;
	}

	public T getRecursiveHelper(int index, Node p) {
		if (index == 0) {
			return p.item;
		}
		return getRecursiveHelper(index - 1, p.next);
	}
	public T getRecursive(int index) {
		if (index > size - 1) {
			return null;
		}
		Node p = sentinel.next;
		return getRecursiveHelper(index, p);
	}
}
