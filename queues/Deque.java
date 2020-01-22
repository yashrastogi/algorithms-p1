/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private final LinkedList<Item> ll;
    private int size = 0;

    // construct an empty deque
    public Deque() {
        ll = new LinkedList<>();
    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> dq = new Deque<String>();
        dq.addFirst("Hello");
        dq.print();
        dq.addFirst("World");
        dq.print();
        dq.addFirst("3rd");
        dq.print();
        dq.addLast("My");
        dq.print();
        System.out.println(dq.isEmpty());
        dq.removeLast();
        dq.print();
        dq.removeFirst();
        dq.print();
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        ll.push(item);
        size++;
    }

    private void print() {
        // Iterator<Item> it = iterator();
        for (Item item : ll) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        ll.setLast(item);
        size++;
    }

    // is the deque empty?
    public boolean isEmpty() {
        Iterator<Item> it = ll.iterator();
        return !it.hasNext();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        return ll.removeLast();
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        return ll.pop();
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return ll.iterator();
    }

    private class LinkedList<Item> implements Iterable<Item> {
        private Node<Item> first = null;

        public Item pop() {
            Node<Item> oldfirst = first;
            first = first.next;
            return oldfirst.item;
        }

        public Item removeLast() {
            Node<Item> current = first;
            if (first == null) {
                return null;
            }
            if (first.next == null) {
                Node<Item> temp = first;
                first = null;
                return temp.item;
            }
            while (current.next.next != null) {
                current = current.next;
            }
            Item item = current.next.item;
            current.next = null;
            return item;
        }

        public Iterator<Item> iterator() {
            return new LinkedListIterator();
        }

        public void setLast(Item item) {
            Node<Item> current = first;
            if (current == null) {
                push(item);
                return;
            }
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<Item>();
            current.next.item = item;
        }

        public void push(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
        }

        public boolean isEmpty() {
            return first == null;
        }

        private class LinkedListIterator implements Iterator<Item> {
            private Node<Item> current = first;

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }

    private class Node<Item> {
        private Node<Item> next;
        private Item item;
    }
}


