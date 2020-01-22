/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    private final Deque<String> stringList = new Deque<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
        int k = Integer.parseInt(args[0]);
        Permutation perm = new Permutation();
        // System.out.println(1);
        perm.takeInput();
        // System.out.println(2);
        perm.spitRandom(k);
        // System.out.println(3);

    }

    private void takeInput() {
        while (!StdIn.isEmpty()) {
            stringList.addFirst(StdIn.readString());
        }
    }

    private void spitRandom(int k) {
        while (k-- != 0) {
            int randNum = StdRandom.uniform(stringList.size());
            for (String s : stringList) {
                --randNum;
                if (randNum == 0) {
                    System.out.println(s);
                }
            }
        }
    }
}
