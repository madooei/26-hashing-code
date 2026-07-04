package hashing;

/**
 * A short demo that hashes a few sample words and prints the results.
 */
public class Main {

  public static void main(String[] args) {
    String[] words = {"cat", "dog", "cow"};

    System.out.println("Polynomial hash (base 31):");
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      System.out.println(word + " -> " + StringHash.polynomialHash(word, 31));
    }

    System.out.println();
    System.out.println("The two anagrams the notes trace:");
    System.out.println("CAB -> " + StringHash.polynomialHash("CAB", 31));
    System.out.println("ABC -> " + StringHash.polynomialHash("ABC", 31));

    System.out.println();
    System.out.println("Other hashes on the word \"cat\":");
    System.out.println("djb2  -> " + StringHash.djb2("cat"));
    System.out.println("fnv1a -> " + StringHash.fnv1a("cat"));
  }
}
