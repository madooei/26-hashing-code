package hashing;

/**
 * String hash functions built from the multiply-and-add (Horner) loop.
 *
 * <p>Each method walks the string once, folding every character into a single
 * running {@code int}. The methods differ only in the constants they use and in
 * whether they mix by adding or by XOR.
 *
 * <p>This is a static utility class and is not meant to be instantiated.
 */
public final class StringHash {

  private StringHash() {
    // This class should not be instantiated!
  }

  /**
   * Computes the polynomial hash of {@code key} for the given base.
   *
   * <p>Uses Horner's method: starting from {@code 0}, each step multiplies the
   * running total by {@code base} and adds the next character. For a key
   * {@code c0 c1 c2} this evaluates {@code c0*base^2 + c1*base + c2} in one pass,
   * which is O(n) in the length of the key. Any base above {@code 1} makes the
   * order of the characters matter.
   *
   * <p>The result is an {@code int} and may be negative when the running total
   * overflows.
   *
   * @param key  the string to hash
   * @param base the polynomial base (use a value above 1)
   * @return the polynomial hash of {@code key}
   */
  public static int polynomialHash(String key, int base) {
    int h = 0;
    for (int i = 0; i < key.length(); i++) {
      h = base * h + key.charAt(i);   // multiply by the base, then add
    }
    return h;
  }

  /**
   * Computes Java's built-in string hash, which is the polynomial hash with a
   * base of {@code 31}.
   *
   * <p>This produces the same value as {@link String#hashCode()}. The result is
   * an {@code int} and may be negative when the running total overflows.
   *
   * @param key the string to hash
   * @return the base-31 polynomial hash of {@code key}
   */
  public static int javaHash(String key) {
    int h = 0;
    for (int i = 0; i < key.length(); i++) {
      h = 31 * h + key.charAt(i);
    }
    return h;
  }

  /**
   * Computes the DJB2 hash by Dan Bernstein: the multiply-and-add loop with a
   * base of {@code 33}, starting the running total from the seed {@code 5381}
   * instead of {@code 0}.
   *
   * <p>The result is an {@code int} and may be negative when the running total
   * overflows.
   *
   * @param key the string to hash
   * @return the DJB2 hash of {@code key}
   */
  public static int djb2(String key) {
    int h = 5381;
    for (int i = 0; i < key.length(); i++) {
      h = 33 * h + key.charAt(i);
    }
    return h;
  }

  /**
   * Computes the FNV-1a hash (Fowler, Noll, and Vo). It keeps the
   * one-character-at-a-time walk but combines each character differently: it
   * XORs the character into the running total and then multiplies by a large
   * fixed prime. It starts from a fixed seed.
   *
   * <p>The result is an {@code int} and may be negative when the running total
   * overflows.
   *
   * @param key the string to hash
   * @return the FNV-1a hash of {@code key}
   */
  public static int fnv1a(String key) {
    int h = 0x811c9dc5;                       // a fixed starting seed
    for (int i = 0; i < key.length(); i++) {
      h = (h ^ key.charAt(i)) * 0x01000193;   // XOR the character in, then multiply
    }
    return h;
  }
}
