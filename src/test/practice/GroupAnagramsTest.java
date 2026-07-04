package practice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GroupAnagrams}. The grouping is order-insensitive: neither
 * the order of the groups nor the order of words within a group is specified.
 * So every assertion first normalizes both sides to a canonical form (sort the
 * words in each group, then sort the groups) before comparing. Each scenario is
 * checked against both the map-keyed and the pairwise solution.
 */
public class GroupAnagramsTest {

  // Canonicalize an order-insensitive grouping so two correct groupings that
  // differ only in order compare equal: sort each inner group, then sort the
  // groups by their string form.
  private static List<List<String>> normalize(List<List<String>> groups) {
    List<List<String>> copy = new ArrayList<>();
    for (List<String> group : groups) {
      List<String> inner = new ArrayList<>(group);
      Collections.sort(inner);
      copy.add(inner);
    }
    Collections.sort(copy, new Comparator<List<String>>() {
      @Override
      public int compare(List<String> a, List<String> b) {
        return a.toString().compareTo(b.toString());
      }
    });
    return copy;
  }

  // Assert that both solutions produce the expected grouping.
  private static void assertGroupsMatch(String[] words, List<List<String>> expected) {
    assertEquals(normalize(expected), normalize(GroupAnagrams.groupAnagrams(words)));
    assertEquals(normalize(expected), normalize(GroupAnagrams.groupAnagramsPairwise(words)));
  }

  @Test
  public void primaryExampleGroupsByLetters() {
    String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
    List<List<String>> expected = new ArrayList<>();
    expected.add(Arrays.asList("eat", "tea", "ate"));
    expected.add(Arrays.asList("tan", "nat"));
    expected.add(Arrays.asList("bat"));
    assertGroupsMatch(words, expected);
  }

  @Test
  public void emptyInputYieldsNoGroups() {
    String[] words = {};
    List<List<String>> expected = new ArrayList<>();
    assertGroupsMatch(words, expected);
  }

  @Test
  public void wordWithNoPartnerFormsGroupOfOne() {
    String[] words = {"bat"};
    List<List<String>> expected = new ArrayList<>();
    expected.add(Arrays.asList("bat"));
    assertGroupsMatch(words, expected);
  }

  @Test
  public void duplicateWordLandsInOneGroupWithBothCopies() {
    String[] words = {"tea", "tea"};
    List<List<String>> expected = new ArrayList<>();
    expected.add(Arrays.asList("tea", "tea"));
    assertGroupsMatch(words, expected);
  }
}
