import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  public static void main(final String[] args) {

    int k = Integer.parseInt(args[0]);

    RandomizedQueue<String> randomizedQueue
        = new RandomizedQueue<>();

    for (String inputStr : StdIn.readAllStrings()) {
      randomizedQueue.enqueue(inputStr);
    }

    while (k-- > 0) {

      StdOut.println(randomizedQueue.dequeue());
    }
  }
}
