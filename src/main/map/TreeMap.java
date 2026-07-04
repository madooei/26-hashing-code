package map;

import java.util.Iterator;

/**
 * A self-balancing binary search tree implementation of the {@code Map} ADT.
 * Pairs are kept in BST order by key, and the AVL height bound — no node's two
 * subtrees ever differ in height by more than one — keeps the tree short, so
 * {@code get}, {@code containsKey}, {@code put}, and {@code remove} each run in
 * {@code O(log n)}.
 *
 * <p>The balancing engine is carried over unchanged from {@code TreeSet}: the
 * {@code updateHeight}, {@code rebalance}, and rotation helpers only ever read
 * and rewrite a node's {@code left}, {@code right}, and cached {@code height},
 * never its key or value, so storing a pair at each node changes nothing about
 * them.
 *
 * @param <K> the type of keys, which must be orderable against themselves.
 * @param <V> the type of values stored under the keys.
 */
public class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {

  private Node<K, V> root;   // the top of the tree, or null when empty
  private int count;         // how many pairs the map holds

  private static class Node<K, V> {
    K key;
    V value;
    Node<K, V> left;
    Node<K, V> right;
    int height;              // cached height of the subtree rooted here

    Node(K key, V value) {
      this.key = key;
      this.value = value;
      this.height = 0;       // a new node is a leaf
    }
  }

  /**
   * Creates an empty map, whose root is {@code null}.
   */
  public TreeMap() {
    root = null;   // the map starts empty
    count = 0;
  }

  @Override
  public int size() {
    return count;
  }

  /**
   * Returns an iterator over this map's keys in ascending order. This is the
   * walk an enhanced for loop uses, so the loop variable is a key.
   *
   * @return an iterator over this map's keys, sorted.
   */
  @Override
  public Iterator<K> iterator() {
    java.util.List<K> keys = new java.util.ArrayList<>();
    collectKeys(root, keys);
    return keys.iterator();
  }

  // In-order walk that appends each node's key, yielding keys in sorted order.
  private void collectKeys(Node<K, V> node, java.util.List<K> keys) {
    if (node == null) {
      return;
    }
    collectKeys(node.left, keys);
    keys.add(node.key);
    collectKeys(node.right, keys);
  }

  /**
   * Returns this map's pairs in ascending key order, one {@code Entry} per pair.
   *
   * @return an iterable over this map's entries, sorted by key.
   */
  @Override
  public Iterable<Map.Entry<K, V>> entries() {
    java.util.List<Map.Entry<K, V>> pairs = new java.util.ArrayList<>();
    collectEntries(root, pairs);
    return pairs;
  }

  // In-order walk that appends each node's pair, yielding keys in sorted order.
  private void collectEntries(Node<K, V> node, java.util.List<Map.Entry<K, V>> pairs) {
    if (node == null) {
      return;
    }
    collectEntries(node.left, pairs);
    pairs.add(new TreeEntry<>(node.key, node.value));
    collectEntries(node.right, pairs);
  }

  // An immutable snapshot of one node's key and value.
  private static final class TreeEntry<K, V> implements Map.Entry<K, V> {
    private final K key;
    private final V value;

    TreeEntry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public K key() {
      return key;
    }

    @Override
    public V value() {
      return value;
    }
  }

  // Recursive search of the subtree rooted at node: return the matching node,
  // or null if the key is absent.
  private Node<K, V> getNode(Node<K, V> node, K key) {
    if (node == null) {
      return null;
    }
    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      return getNode(node.left, key);
    }
    if (cmp > 0) {
      return getNode(node.right, key);
    }
    return node;   // keys match
  }

  /**
   * Returns the value associated with the given key, or {@code null} if the key
   * is not present.
   *
   * @param key the key to look up.
   * @return the value associated with the key, or {@code null} if it is absent.
   * @throws IllegalArgumentException if the key is null.
   */
  @Override
  public V get(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    Node<K, V> node = getNode(root, key);
    return node == null ? null : node.value;
  }

  /**
   * Reports whether this map contains the given key.
   *
   * @param key the key to look for.
   * @return {@code true} if the key is present, {@code false} otherwise.
   * @throws IllegalArgumentException if the key is null.
   */
  @Override
  public boolean containsKey(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    return getNode(root, key) != null;
  }

  // Insert or overwrite key in the subtree rooted at node, returning that
  // subtree's root. Only the new-node case changes shape, so only it refreshes
  // the cached height and rebalances on the way back up.
  private Node<K, V> put(Node<K, V> node, K key, V value) {
    if (node == null) {
      return new Node<>(key, value);   // key was absent: insert
    }
    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      node.left = put(node.left, key, value);
    } else if (cmp > 0) {
      node.right = put(node.right, key, value);
    } else {
      node.value = value;   // key present: overwrite, no structural change
      return node;
    }
    updateHeight(node);
    return rebalance(node);
  }

  /**
   * Associates the given value with the given key. If the key is already
   * present, its value is replaced.
   *
   * @param key the key to add or update.
   * @param value the value to associate with the key.
   * @return {@code true} if the key was not previously present, {@code false}
   *     if it was.
   * @throws IllegalArgumentException if the key or the value is null.
   */
  @Override
  public boolean put(K key, V value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException();
    }
    boolean isNew = !containsKey(key);
    root = put(root, key, value);   // overwrites if present, inserts if absent
    if (isNew) {
      count++;
    }
    return isNew;
  }

  // Remove key from the subtree rooted at node and return the new root of that
  // subtree, refreshing the cached height and rebalancing on the way up.
  private Node<K, V> remove(Node<K, V> node, K key) {
    if (node == null) {
      return null;
    }
    int cmp = key.compareTo(node.key);
    if (cmp < 0) {
      node.left = remove(node.left, key);
    } else if (cmp > 0) {
      node.right = remove(node.right, key);
    } else {
      if (node.left == null) {
        return node.right;
      }
      if (node.right == null) {
        return node.left;
      }
      Node<K, V> successor = minNode(node.right);
      node.key = successor.key;       // copy both fields of the pair
      node.value = successor.value;
      node.right = remove(node.right, successor.key);
    }
    updateHeight(node);
    return rebalance(node);
  }

  /**
   * Removes the given key and its value from this map if the key is present.
   *
   * @param key the key to remove.
   * @return {@code true} if the key was removed, {@code false} if it was not
   *     present.
   * @throws IllegalArgumentException if the key is null.
   */
  @Override
  public boolean remove(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    if (!containsKey(key)) {
      return false;     // not present; nothing to remove
    }
    root = remove(root, key);
    count--;
    return true;
  }

  // The smallest node in a subtree: keep going left until there is no left child.
  private Node<K, V> minNode(Node<K, V> node) {
    if (node == null) {
      throw new IllegalArgumentException("empty subtree");
    }
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  // The height of the subtree rooted at node: an O(1) read of the cached field.
  // The empty tree has no node to hold a number, so null answers -1.
  private int height(Node<K, V> node) {
    if (node == null) {
      return -1;
    }
    return node.height;
  }

  // How the node leans: positive is left-heavy, negative is right-heavy.
  private int balanceFactor(Node<K, V> node) {
    return height(node.left) - height(node.right);
  }

  // Recompute one node's cached height from its children's cached heights. The
  // children must already be correct, so this is called bottom-up.
  private void updateHeight(Node<K, V> node) {
    node.height = 1 + Math.max(height(node.left), height(node.right));
  }

  // Pick and apply the rotation a node needs, returning the new subtree root.
  private Node<K, V> rebalance(Node<K, V> node) {
    int bf = balanceFactor(node);
    if (bf > 1) { // left-heavy
      if (balanceFactor(node.left) < 0) { // left child leans right
        node.left = leftRotation(node.left); // straighten into left-left
      }
      return rightRotation(node);
    }
    if (bf < -1) { // right-heavy
      if (balanceFactor(node.right) > 0) { // right child leans left
        node.right = rightRotation(node.right); // straighten into right-right
      }
      return leftRotation(node);
    }
    return node; // balanced: nothing to do
  }

  // Lift root's left child above it, returning the new top of this subtree.
  private Node<K, V> rightRotation(Node<K, V> root) {
    Node<K, V> child = root.left;
    root.left = child.right;
    child.right = root;
    updateHeight(root); // the node that moved down, now lower
    updateHeight(child); // the node that moved up, depends on root
    return child;
  }

  // The mirror of rightRotation: lift root's right child above it.
  private Node<K, V> leftRotation(Node<K, V> root) {
    Node<K, V> child = root.right;
    root.right = child.left;
    child.left = root;
    updateHeight(root);
    updateHeight(child);
    return child;
  }

  // Left-rotate the left child to straighten the bend, then right-rotate root.
  private Node<K, V> leftRightRotation(Node<K, V> root) {
    root.left = leftRotation(root.left);
    return rightRotation(root);
  }

  // Right-rotate the right child to straighten the bend, then left-rotate root.
  private Node<K, V> rightLeftRotation(Node<K, V> root) {
    root.right = rightRotation(root.right);
    return leftRotation(root);
  }
}
