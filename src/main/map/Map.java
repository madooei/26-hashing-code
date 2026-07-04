package map;

import java.util.Iterator;

/**
 * A Map is a collection of key-value pairs with distinct keys and no order.
 *
 * <p>The interface holds two things per entry — a key and a value — so it has
 * one method more than a set: a value can be stored under a key, fetched,
 * removed, and tested for presence, and the map can report its size. There is
 * no operation to ask for a pair by position — a map has no positions — and no
 * way to go from a value back to its key.
 *
 * <p>Several methods return a boolean, but they do not all mean the same thing
 * by it. For {@code containsKey}, the boolean answers a question about
 * membership. For {@code put} and {@code remove}, it reports whether the call
 * changed which keys the map holds: {@code put} on a key that is already present
 * replaces its value and returns false, and {@code remove} of an absent key does
 * nothing and returns false. The value under a key is always whatever was last
 * put there.
 *
 * <p>The map is {@code Iterable<K>}, so its default walk visits the keys. To
 * walk the pairs instead, use {@link #entries()}.
 *
 * @param <K> the type of keys in this map.
 * @param <V> the type of values in this map.
 */
public interface Map<K, V> extends Iterable<K> {

  /**
   * Associates the given value with the given key. If the key is already
   * present, its value is replaced with the given value.
   *
   * @param key the key to add or update.
   * @param value the value to associate with the key.
   * @return true if the key was not previously present, false if it was.
   * @throws IllegalArgumentException if the key or the value is null.
   */
  boolean put(K key, V value);

  /**
   * Returns the value associated with the given key, or null if the key is not
   * present.
   *
   * @param key the key to look up.
   * @return the value associated with the key, or null if the key is absent.
   * @throws IllegalArgumentException if the key is null.
   */
  V get(K key);

  /**
   * Removes the given key and its value from this map if the key is present.
   *
   * @param key the key to remove.
   * @return true if the key was removed, false if it was not present.
   * @throws IllegalArgumentException if the key is null.
   */
  boolean remove(K key);

  /**
   * Returns true if this map contains the given key.
   *
   * @param key the key to look for.
   * @return true if the key is present, false otherwise.
   * @throws IllegalArgumentException if the key is null.
   */
  boolean containsKey(K key);

  /**
   * Returns the number of key-value pairs in this map.
   *
   * @return the number of pairs in this map.
   */
  int size();

  /**
   * Returns an iterator over the keys in this map. Inherited from
   * {@code Iterable<K>}, this is the walk used by an enhanced for loop, so the
   * loop variable is a key.
   *
   * @return an iterator over the keys in this map.
   */
  @Override
  Iterator<K> iterator();

  /**
   * Returns an iterable over the key-value pairs in this map, yielding each pair
   * exactly once. This is a separate iterable from the map itself: the map is
   * iterable over keys, while this view is iterable over pairs.
   *
   * <p>Use it when you want the values as well as the keys. Walking the keys and
   * calling {@code get} on each is O(n^2), because each {@code get} is itself a
   * search; this view hands back the value beside its key in a single O(n) pass.
   *
   * @return an iterable over the key-value pairs in this map.
   */
  Iterable<Map.Entry<K, V>> entries();

  /**
   * A read-only view of one key-value pair. An entry lets you read the key and
   * the value of a pair, but not rewrite the map through it: there are no
   * setters. The methods are named {@code key()} and {@code value()}.
   *
   * @param <K> the type of the key in this pair.
   * @param <V> the type of the value in this pair.
   */
  interface Entry<K, V> {

    /**
     * Returns the key of this pair.
     *
     * @return the key of this pair.
     */
    K key();

    /**
     * Returns the value of this pair.
     *
     * @return the value of this pair.
     */
    V value();
  }
}
