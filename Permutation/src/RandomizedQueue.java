import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private static final int QUEUE_GROWTH_FACTOR = 2;

  private Item[] queueStorage;
  private int queueCapacity = 1;
  private int queueCount = 0;

  public RandomizedQueue() {

    this.queueStorage = (Item[]) new Object[this.queueCapacity];
  }

  // API Methods

  public boolean isEmpty() {

    return (this.queueCount == 0);
  }

  public int size() {

    return this.queueCount;
  }

  public void enqueue(final Item item) {

    if (item == null) {
      throw
          new IllegalArgumentException("Item is null");
    }

    if ((this.queueCount) >= this.queueCapacity) {
      growQueueStorage();
    }

    this.queueStorage[this.queueCount] = item;
    this.queueCount++;
  }

  public Item dequeue() {

    if (this.isEmpty()) {
      throw
          new NoSuchElementException("Queue is empty");
    }

    if ((this.queueCount / 4) >= this.queueCapacity) {
      shrinkQueueStorage();
    }

    int randomIndex = StdRandom.uniform(0, this.queueCount);
    int lastIndex = (this.queueCount - 1);
    // Pick an item at random and return it.
    // Fill the empty space by the last (last in) item in the queue

    Item item = this.queueStorage[randomIndex];
    Item replacementItem  = this.queueStorage[lastIndex];

    this.queueStorage[randomIndex] = replacementItem;
    this.queueStorage[lastIndex] = null;
    this.queueCount--;

    return item;
  }

  public Item sample() {

    if (this.isEmpty()) {
      throw
          new NoSuchElementException("Queue is empty");
    }

    int randomIndex = StdRandom.uniform(0, this.queueCount);

    Item item = this.queueStorage[randomIndex];

    return item;
  }

  // Queue Management Methods

  private void growQueueStorage() {

    int newCapacity = (this.queueCapacity * QUEUE_GROWTH_FACTOR);

    resizeQueueStorage(newCapacity);
  }

  private void shrinkQueueStorage() {

    int newCapacity =
        Math.floorDiv(this.queueCapacity, QUEUE_GROWTH_FACTOR);

    resizeQueueStorage(newCapacity);
  }

  private void resizeQueueStorage(final int newCapacity) {

    Item[] newQueueStorage = (Item[]) new Object[newCapacity];
    int maxQueueIter =
        (this.queueCapacity > newCapacity)
          ? newCapacity
            : this.queueCapacity;

    for (int queueIter = 0; queueIter < maxQueueIter; queueIter++) {
      newQueueStorage[queueIter] = this.queueStorage[queueIter];
    }

    this.queueStorage = newQueueStorage;
    this.queueCapacity = newCapacity;
  }

  @Override
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    @Override
    public boolean hasNext() {

      return (!isEmpty());
    }

    @Override
    public Item next() {

      if (!this.hasNext()) {
        throw
            new NoSuchElementException("No more elements to iterate");
      }

      Item item = dequeue();

      return item;
    }

    @Override
    public void remove() {

      throw new UnsupportedOperationException("Remove unsupported");
    }
  }

  public static void main(final String[] args) {

    RandomizedQueue<Integer> randomizedQueue
        = new RandomizedQueue<>();

    randomizedQueue.enqueue(10);
    randomizedQueue.enqueue(11);
    randomizedQueue.enqueue(12);

    StdOut.println(String.format("T%d", randomizedQueue.dequeue()));

    randomizedQueue.enqueue(13);
    randomizedQueue.enqueue(14);
    randomizedQueue.enqueue(15);

    StdOut.println(String.format("T%d", randomizedQueue.dequeue()));

    randomizedQueue.enqueue(16);


    for (int val : randomizedQueue) {

      StdOut.println(val);
    }

    StdOut.println(String.format("T%d", randomizedQueue.dequeue()));
  }
}
