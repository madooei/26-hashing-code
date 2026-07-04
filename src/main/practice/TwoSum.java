package practice;

import map.Map;
import map.TreeMap;

/**
 * Two Sum on an unsorted array: find the indices of the two numbers that add
 * up to a target. Each input has exactly one such pair, and the same element
 * may not be used twice.
 */
public final class TwoSum {
  private TwoSum() {
    // This class should not be instantiated!
  }

  /**
   * Solves Two Sum in one pass with a map from value to index, replacing the
   * inner scan of the brute force with a single lookup.
   *
   * <p>Walk the array once. At each element, check whether its complement is
   * already in the map before storing anything; if so, the map hands back the
   * complement's index and we have the pair. Otherwise record the current value
   * and its index and move on.
   *
   * <p>We make one pass and each step does one {@code containsKey}, at most one
   * {@code get}, and one {@code put}. Backed by a {@code TreeMap} those cost
   * $O(\log n)$ apiece, so the whole thing is $O(n \log n)$ time and $O(n)$
   * extra space. The extra $\log n$ is the map's own lookup cost; swapping in a
   * constant-time {@code HashMap} drops each lookup to $O(1)$ and the same code
   * becomes $O(n)$, without ever sorting the array.
   *
   * @param nums the input array
   * @param target the target sum
   * @return the indices of the two numbers that add up to the target
   */
  public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new TreeMap<>();  // value -> index

    for (int i = 0; i < nums.length; i++) {
      int complement = target - nums[i];
      if (seen.containsKey(complement)) {
        return new int[]{seen.get(complement), i};
      }
      seen.put(nums[i], i);
    }

    return new int[0];  // unreachable: the problem guarantees a solution
  }

  /**
   * Solves Two Sum by trying every pair with two nested loops.
   *
   * <p>For each element, look at every element after it and check whether the
   * two add up to the target. The inner loop starts at {@code i + 1}, so each
   * pair is tried once and no element is paired with itself. Values are
   * compared but the original indices are returned, so the array is never
   * reordered.
   *
   * <p>Two nested loops, each running up to {@code n} times, give a worst-case
   * time of $O(n^2)$: the inner loop is a search for the complement, restarted
   * from scratch at every element.
   *
   * @param nums the input array
   * @param target the target sum
   * @return the indices of the two numbers that add up to the target
   */
  public static int[] twoSumBruteForce(int[] nums, int target) {
    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
          return new int[]{i, j};  // found the pair
        }
      }
    }
    return new int[0];  // unreachable: the problem guarantees a solution
  }
}
