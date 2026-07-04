package hashing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StringHash}, grounded in the worked traces from the notes.
 */
public class StringHashTest {

  // The notes trace the base-31 Horner loop character by character:
  //   "CAB": 0 -> 67 -> 2142 -> 66468
  //   "ABC": 0 -> 65 -> 2081 -> 64578
  @Test
  public void polynomialHashMatchesTracedCab() {
    assertEquals(66468, StringHash.polynomialHash("CAB", 31));
  }

  @Test
  public void polynomialHashMatchesTracedAbc() {
    assertEquals(64578, StringHash.polynomialHash("ABC", 31));
  }

  @Test
  public void anagramsHashDifferently() {
    assertNotEquals(
        StringHash.polynomialHash("CAB", 31),
        StringHash.polynomialHash("ABC", 31));
  }

  // The notes state the base-31 hash is exactly Java's built-in string hash,
  // so javaHash("CAB") is the same 66468 the polynomial hash produced.
  @Test
  public void javaHashIsPolynomialHashBase31() {
    assertEquals(66468, StringHash.javaHash("CAB"));
    assertEquals(
        StringHash.polynomialHash("CAB", 31),
        StringHash.javaHash("CAB"));
  }

  @Test
  public void javaHashMatchesStringHashCode() {
    assertEquals("hello".hashCode(), StringHash.javaHash("hello"));
  }

  @Test
  public void polynomialHashIsDeterministic() {
    assertEquals(
        StringHash.polynomialHash("banana", 31),
        StringHash.polynomialHash("banana", 31));
  }

  @Test
  public void djb2IsDeterministic() {
    assertEquals(StringHash.djb2("banana"), StringHash.djb2("banana"));
  }

  @Test
  public void fnv1aIsDeterministic() {
    assertEquals(StringHash.fnv1a("banana"), StringHash.fnv1a("banana"));
  }

  @Test
  public void differentStringsGenerallyDifferInDjb2() {
    assertNotEquals(StringHash.djb2("cat"), StringHash.djb2("dog"));
  }

  @Test
  public void differentStringsGenerallyDifferInFnv1a() {
    assertNotEquals(StringHash.fnv1a("cat"), StringHash.fnv1a("dog"));
  }
}
