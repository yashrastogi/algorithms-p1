
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return 0;
    }

    // add the item
    public void enqueue(Item item) {

    }

    // remove and return a random item
    public Item dequeue() {
        return null;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return null;
    }

    private class Node<Item> {
        private Node<Item> next;
        private Item item;
    }
    private class LinkedList<Item> implements Iterable<Item> {
        private Node<Item> first = null;

        public void push(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
        }

        public Item pop() {
            Node<Item> oldfirst = first;
            first = first.next;
            return oldfirst.item;
        }

        public Item removeLast() {
            Node<Item> current = first;
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
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<Item>();
            current.next.item = item;
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


}
