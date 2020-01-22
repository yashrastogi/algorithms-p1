/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // unit testing code
    private Item[] arr;
    private int numElem;

    public RandomizedQueue() {
        arr = (Item[]) new Object[10];
        numElem = 0;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<>();
        test.enqueue("Hello1");
        test.enqueue("World2");
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
        System.out.println(test.dequeue());
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (numElem == arr.length) {
            resize(1);
        }
        arr[numElem++] = item;
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
        if (arr.length / 3 == numElem) {
            resize(0);
        }
        return temp;
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

    // public Item pop(Item item) {
    //     if (arr.length / 3 == numElem) {
    //         resize(0);
    //     }
    //     return arr[numElem--];
    // }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int currPos = 0;

        public boolean hasNext() {
            return !(currPos >= arr.length);
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return arr[currPos++];
        }
    }
}
