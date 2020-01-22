/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int numElem;

    public RandomizedQueue() {
        arr = (Item[]) new Object[10];
        numElem = 0;
    }

    // unit testing code
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<>();
        test.enqueue("Hello1");
        test.enqueue("Hello2");
        test.enqueue("Hello3");
        test.enqueue("Hello4");
        for (String str : test) {
            System.out.println(str);
        }
        System.out.println("-");
        for (String str : test) {
            System.out.println(str);
        }
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (numElem == arr.length) {
            resize(1);
        }
        arr[numElem++] = item;
    }

    private void resize(int op) {
        if (op == 1) {
            Item[] doubleItems = (Item[]) new Object[arr.length * 2];
            int j = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != null) doubleItems[i] = arr[j++];
            }
            arr = doubleItems;
        }
        else if (op == 0) {
            Item[] halfItems = (Item[]) new Object[arr.length / 2];
            int j = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != null) halfItems[i] = arr[j++];
            }
            arr = halfItems;
        }
        else {
            throw new UnsupportedOperationException("no such op: " + op);
        }
    }

    public Item dequeue() {
        if (numElem == 0) throw new NoSuchElementException();
        int randomPos = StdRandom.uniform(numElem);
        // System.out.print("Random: " + randomPos + " Length: "+arr.length);
        Item temp = arr[randomPos];
        // System.out.print(" Temp: " + temp + " NumElem: "+ numElem);
        arr[randomPos] = arr[numElem-- - 1];
        arr[numElem] = null;
        // System.out.print("-"+numElem+" arrRP: " + arr[randomPos]+"   ");
        if (arr.length / 3 == numElem && numElem != 0) {
            resize(0);
        }
        return temp;
    }

    public boolean isEmpty() {
        return numElem == 0;
    }

    public int size() {
        return numElem;
    }

    public Item sample() {
        if (numElem == 0) throw new NoSuchElementException();
        int randomPos = StdRandom.uniform(numElem);
        return arr[randomPos];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int iterNumElem = numElem;
        private int currPos = -1;

        public boolean hasNext() {
            return iterNumElem != 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (currPos == -1) currPos = StdRandom.uniform(numElem);
            iterNumElem--;
            return arr[currPos++ % numElem];
        }
    }
}
