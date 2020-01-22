/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    private final RandomizedQueue<String> stringList = new RandomizedQueue<>();

    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException();
        int k = Integer.parseInt(args[0]);
        Permutation perm = new Permutation();
        perm.takeInput();
        perm.spitRandom(k);
    }

    private void takeInput() {
        while (!StdIn.isEmpty()) {
            stringList.enqueue(StdIn.readString());
        }
    }

    private void spitRandom(int k) {
        while (k-- != 0) {
            System.out.println(stringList.dequeue());
        }
    }
}
