package io.github.nopeless.sortedlist;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SortedList<E extends Comparable<E>> {
    private Node<E> head;  // points to the head of the list.
    private Node<E> tail;  // points to the tail of the list.
    private int size; // number of nodes (items) in the list.

    /**
     * Create a new, empty SortedList.
     */
    public SortedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns number of elements in the sorted list
     */
    public int size() {
        return size;
    }

    /**
     * Return the item at a specified index in this SortedList.
     * If index < the halfway point in the list (based on the size), the list should be traversed
     * forwards from the head.  If index > the halfway point, the traversal should start at the tail
     * and proceed in reverse.  For an index exactly halfway, you may start at either end.
     */
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Index out of bounds (" + index + ") for SortedList with size=" + size);

        int delta = 0;

        if (index < (size + 1) / 2) {
            delta = 1;
        } else {
            delta = -1;
            // flip index
            index = size - index - 1;
        }

        var r = _getEnd(delta);

        for (int i = 0; i < index; i++) {
            r = r._move(delta);
        }

        return r.value;
    }

    /**
     * Remove all the items in the SortedList.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Add a new item into this SortedList.  Position will be determined automatically based on sorted order.
     * An item < head or > tail should be added in O(1) time. Other items requiring a traversal may be added
     * in O(n) time.
     * We assume this item does not already exist in the list.
     */
    public void add(E item) {

        var node = new Node<>(item);

        // edge case
        if (size == 0) {
            size++;
            this.head = this.tail = node;
            return;
        }

        // start with size++ so we can return any time
        size++;

        // fast lookup for tail
        if (item.compareTo(tail.value) >= 0) {
            // set links
            node.prev = tail;
            tail.next = node;

            tail = node;
            return;
        }

        for (var h = this.head; h != null; h = h.next) {
            if (item.compareTo(h.value) <= 0) {
                // item -> h
                var prev = h.prev;
                var next = h;

                // set links
                node.next = next;
                node.prev = prev;

                if (prev != null) prev.next = node;
                else head = node;

                // next is not null
                next.prev = node;

                return;
            }
        }
    }

    /**
     * Returns true if this SortedList contains item, false otherwise.
     */
    public boolean contains(E item) {
        for (var h = head; h != null; h = h.next) if (h.value.equals(item)) return true;
        return false;
    }

    /**
     * Remove an item from this SortedList.  If the item occurs multiple times,
     * only one copy will be removed.
     */
    public void remove(E item) {
        for (var h = head; h != null; h = h.next) {
            if (item.compareTo(h.value) == 0) {
                var prev = h.prev;
                var next = h.next;


                if (prev != null) prev.next = next;
                else head = next;
                if (next != null) next.prev = prev;
                else tail = prev;

                size--;

                return;
            }
        }
    }

    private Node<E> _getEnd(int d) {
        if (d != 1 && d != -1) throw new IllegalArgumentException();

        return d == 1 ? head : tail;
    }

    /**
     * Returns internal representation of the data structure
     */
    public String toInternalString() {
        var s = "";

        for (int i = 0; i < 2; i++) {
            int delta = i == 0 ? 1 : -1;

            ArrayList<E> items = new ArrayList<>();

            for (var v = _getEnd(delta); v != null; v = v._move(delta)) {
                items.add(v.value);
            }

            s += "[" + items.stream().map(Object::toString).collect(Collectors.joining(", ")) + "] ";
        }

        return s + "size=" + size;
    }

    /**
     * Return a string representation of this list from the user's perspective.
     * Should look like [item1 item2 item3...]
     */
    public String toString() {
        ArrayList<E> items = new ArrayList<>();

        for (var h = head; h != null; h = h.next) {
            items.add(h.value);
        }

        return "[" + items.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
    }

    /**
     * Internal node
     */
    private static class Node<E> {
        public E value;
        public Node<E> next;
        public Node<E> prev;

        public Node(E value) {
            this.value = value;
        }

        /**
         * Moves front or back depending on delta
         */
        protected Node<E> _move(int delta) {
            if (delta != 1 && delta != -1) throw new IllegalArgumentException();

            return delta == 1 ? this.next : this.prev;
        }
    }
}
