package practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import map.Map;
import map.TreeMap;

/**
 * Group Anagrams: partition a list of words so that words built from the same
 * letters land in the same group.
 *
 * <p>Two solutions are offered. {@link #groupAnagramsPairwise(String[])} tests
 * each new word against a representative of every existing group, and
 * {@link #groupAnagrams(String[])} keys a map on each word's sorted-letter form
 * so every anagram finds its group by a single lookup.
 */
public final class GroupAnagrams {

  private GroupAnagrams() {
  }

  /**
   * Groups anagrams by keying a map on each word's canonical (sorted-letter)
   * form: every anagram of a word sorts to the same string, so that string is a
   * label the whole group shares. One pass appends each word to the list stored
   * under its key.
   *
   * <p>The outer map is the course {@code Map}, backed by {@code TreeMap} for
   * now; a {@code HashMap} can be dropped in later without touching this code,
   * because the string key already satisfies the equal-keys contract (equal
   * anagrams build equal keys).
   *
   * <p>Let {@code n} be the number of words and {@code k} the length of the
   * longest word. Each word sorts once at O(k log k), then a {@code TreeMap}
   * operation at O(k log n) places it, for O(n * k * (log k + log n)) overall.
   * Swapping in a constant-time {@code HashMap} drops the map operation to O(k),
   * leaving just the sorting at O(n * k log k).
   *
   * @param words the words to group; each assumed non-empty lowercase letters.
   * @return the groups of anagrams, in no particular order.
   */
  public static List<List<String>> groupAnagrams(String[] words) {
    Map<String, List<String>> groups = new TreeMap<>();

    for (String word : words) {
      char[] chars = word.toCharArray();
      Arrays.sort(chars);
      String key = new String(chars);        // the sorted-letter form, e.g. "aet"

      List<String> group = groups.get(key);
      if (group == null) {                   // first word with this key
        group = new ArrayList<>();
        groups.put(key, group);
      }
      group.add(word);
    }

    List<List<String>> result = new ArrayList<>();
    for (Map.Entry<String, List<String>> entry : groups.entries()) {
      result.add(entry.value());
    }
    return result;
  }

  /**
   * Groups anagrams by brute force: keep the groups made so far, and for each
   * new word scan them, joining the first whose representative it matches or
   * starting a new group when none matches.
   *
   * <p>Let {@code n} be the number of words and {@code k} the length of the
   * longest word. Each word may be compared against the representative of every
   * existing group (up to {@code n} of them), and each comparison sorts two
   * words at O(k log k), so the worst case is about O(n^2 * k log k). The waste
   * is that the same representative is re-sorted on every comparison; keying by
   * a canonical form computed once removes it.
   *
   * @param words the words to group; each assumed non-empty lowercase letters.
   * @return the groups of anagrams, in no particular order.
   */
  public static List<List<String>> groupAnagramsPairwise(String[] words) {
    List<List<String>> groups = new ArrayList<>();

    for (String word : words) {
      boolean placed = false;
      for (List<String> group : groups) {
        if (areAnagrams(word, group.get(0))) {  // compare with the representative
          group.add(word);
          placed = true;
          break;
        }
      }
      if (!placed) {
        List<String> newGroup = new ArrayList<>();
        newGroup.add(word);
        groups.add(newGroup);
      }
    }

    return groups;
  }

  // Two words are anagrams exactly when their letters, sorted, are identical.
  private static boolean areAnagrams(String a, String b) {
    char[] ca = a.toCharArray();
    char[] cb = b.toCharArray();
    Arrays.sort(ca);
    Arrays.sort(cb);
    return Arrays.equals(ca, cb);
  }
}
