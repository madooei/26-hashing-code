package practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracticeMain {
  public static void main(String[] args) {
    System.out.println("=== Point: equals/hashCode round-trip ===");
    Map<Point, String> locations = new HashMap<>();
    Point a = new Point(2, 3);
    Point b = new Point(2, 3);
    locations.put(a, "home");
    System.out.println("a.equals(b): " + a.equals(b));
    System.out.println("get with an equal-but-distinct Point: " + locations.get(b));

    System.out.println();
    System.out.println("=== TwoSum: hashing vs. brute force ===");
    int[] nums = {8, 3, 11, 7};
    int target = 10;
    System.out.println("nums = " + Arrays.toString(nums) + ", target = " + target);
    System.out.println("twoSum:           " + Arrays.toString(TwoSum.twoSum(nums, target)));
    System.out.println("twoSumBruteForce: " + Arrays.toString(TwoSum.twoSumBruteForce(nums, target)));

    System.out.println();
    System.out.println("=== GroupAnagrams: bucket by canonical key ===");
    String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
    System.out.println("words = " + Arrays.toString(words));
    List<List<String>> groups = GroupAnagrams.groupAnagrams(words);
    for (List<String> group : groups) {
      System.out.println(group);
    }
  }
}
