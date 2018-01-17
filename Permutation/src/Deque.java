import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private class Node {

    private Item item;
    private Node nextNode;
    private Node prevNode;

    public Node(final Item itemP, final Node nextNodeP, final Node prevNodeP) {

      this.item = itemP;
      this.nextNode = nextNodeP;
      this.prevNode = prevNodeP;
    }

    public Item getItem() {
      return item;
    }

    public Node getNextNode() {
      return nextNode;
    }

    public Node getPrevNode() {
      return prevNode;
    }

    public void setItem(final Item itemP) {
      this.item = itemP;
    }

    public void setNextNode(final Node nextNodeP) {
      this.nextNode = nextNodeP;
    }

    public void setPrevNode(final Node prevNodeP) {
      this.prevNode = prevNodeP;
    }
  }

  private Node firstNode;
  private Node lastNode;
  private int queueCount;

  public Deque() {

    this.firstNode = null;
    this.lastNode = null;
    this.queueCount = 0;
  }

  /// API Methods

  public boolean isEmpty() {

    return (this.firstNode == null);
  }

  public int size() {

    return this.queueCount;
  }

  public void addFirst(final Item item) {

    if (item == null) {
      throw
          new IllegalArgumentException("Item is null");
    }

    // Setup the new node and it's links
    Node newNode = new Node(item, this.firstNode, null);

    if (this.firstNode != null) {

      this.firstNode.setPrevNode(newNode);
    } else {
      this.lastNode = newNode;
    }

    this.firstNode = newNode;
    this.queueCount++;
  }

  public void addLast(final Item item) {

    if (item == null) {
      throw
          new IllegalArgumentException("Item is null");
    }

    // Setup the new node and it's links
    Node newNode = new Node(item, null, this.lastNode);

    if (this.lastNode != null) {

      this.lastNode.setNextNode(newNode);
    } else {
      this.firstNode = newNode;
    }

    this.lastNode = newNode;
    this.queueCount++;
  }

  public Item removeFirst() {

    if (this.isEmpty()) {
      throw
          new NoSuchElementException("Deque is empty");
    }

    Node oldFirstNode = this.firstNode;
    Node newFirstNode = this.firstNode.getNextNode();

    if (newFirstNode != null) {
      newFirstNode.setPrevNode(null);
    } else {
      this.firstNode = null;
      this.lastNode = null;
    }

    this.firstNode = newFirstNode;
    this.queueCount--;

    Item item = oldFirstNode.getItem();
    oldFirstNode = null;

    return item;
  }

  public Item removeLast() {

    if (this.isEmpty()) {
      throw
          new NoSuchElementException("Deque is empty");
    }

    Node oldLastNode = this.lastNode;
    Node newLastNode = this.lastNode.getPrevNode();

    if (newLastNode != null) {

      newLastNode.setNextNode(null);
    } else {
      this.firstNode = null;
      this.lastNode = null;
    }

    this.lastNode = newLastNode;
    this.queueCount--;

    Item item = oldLastNode.getItem();
    oldLastNode = null;

    return item;
  }

  @Override
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {

    private Node currentNode = firstNode;

    @Override
    public boolean hasNext() {

      return (this.currentNode != null);
    }

    @Override
    public Item next() {

      if (!this.hasNext()) {
        throw
            new NoSuchElementException("No more elements left");
      }

      Item item = this.currentNode.getItem();
      this.currentNode = this.currentNode.getNextNode();

      return item;
    }

    @Override
   public void remove() {

      throw
          new UnsupportedOperationException("Remove unsupported");
    }
  }

  public static void main(final String[] args) {

    Deque<Integer> deque = new Deque<>();
    deque.addFirst(10);
    int item = deque.removeLast();
    StdOut.println(String.format("T[%d]", item));
    deque.addFirst(11);
    item = deque.removeFirst();
    StdOut.println(String.format("T[%d]", item));
    deque.addLast(9);
    deque.addLast(8);
    deque.addFirst(7);
    //deque.addFirst(12);
    //deque.addFirst(13);

    for (int num : deque) {
      StdOut.println(num);
    }
  }
}
