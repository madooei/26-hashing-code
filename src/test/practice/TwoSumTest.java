package practice;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Two Sum solutions.
 */
public class TwoSumTest {
  @Test
  public void twoSumFindsPairInMiddleAndEnd() {
    assertArrayEquals(new int[]{1, 3}, TwoSum.twoSum(new int[]{8, 3, 11, 7}, 10));
  }

  @Test
  public void twoSumFindsPairWithNegativeNumber() {
    assertArrayEquals(new int[]{1, 2}, TwoSum.twoSum(new int[]{5, -2, 4}, 2));
  }

  @Test
  public void twoSumBruteForceFindsPairInMiddleAndEnd() {
    assertArrayEquals(new int[]{1, 3}, TwoSum.twoSumBruteForce(new int[]{8, 3, 11, 7}, 10));
  }

  @Test
  public void twoSumBruteForceFindsPairWithNegativeNumber() {
    assertArrayEquals(new int[]{1, 2}, TwoSum.twoSumBruteForce(new int[]{5, -2, 4}, 2));
  }
}
