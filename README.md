# Hashing

The companion code for the hashing chapter. This chapter builds no data
structure of its own; it studies the function that later chapters will build
on. `StringHash` collects the hash functions the notes develop and trace:
`polynomialHash`, the Horner-rule polynomial hash with a caller-chosen base;
`javaHash`, the base-31 special case that reproduces Java's own
`String.hashCode`; and two classics for comparison, `djb2` and `fnv1a`.

The practice package applies hashing from the user's side. `Point` demonstrates
the equals/hashCode contract: a class that overrides `equals` must override
`hashCode` from the same fields, or a `HashMap` sends two equal keys to
different slots and a lookup for a point just stored under an equal key misses.
`TwoSum` replaces the brute-force inner scan with a single map lookup, and
`GroupAnagrams` buckets words by their sorted-letter canonical key — two words
are anagrams exactly when they share that key.

The map package carries the course `Map` ADT forward, backed by `TreeMap` from
the tree chapters. That backing charges O(log n) per lookup, and the practice
solutions state their costs with that tax included. A map with constant-time
lookup is what the next chapters build; when it arrives, the same code gets
faster without changing a line.

## Prerequisites

- JDK 17+ (JUnit 6 requires it). The JUnit jar is already vendored in `lib/`;
  there is nothing to download.

## Repository Layout

```plaintext
.
├── lib/
│   └── junit-platform-console-standalone-6.1.0.jar
├── src/
│   ├── main/
│   │   ├── hashing/                      # the chapter's hash functions
│   │   │   ├── StringHash.java           #   polynomialHash, javaHash, djb2, fnv1a
│   │   │   └── Main.java                 #   hashes a few sample words and prints the results
│   │   ├── map/                          # the course Map ADT (carried forward)
│   │   │   ├── Map.java                  #   the Map contract
│   │   │   └── TreeMap.java              #   balanced-tree map, O(log n) lookup
│   │   └── practice/                     # the chapter exercises
│   │       ├── Point.java                #   the equals/hashCode contract on a grid coordinate
│   │       ├── TwoSum.java               #   brute-force scan vs. one-pass map
│   │       ├── GroupAnagrams.java        #   pairwise comparison vs. canonical-key bucketing
│   │       └── PracticeMain.java         #   runs the three practice demos
│   └── test/
│       ├── hashing/
│       │   └── StringHashTest.java       #   the worked hashes from the notes, including CAB/ABC
│       └── practice/
│           ├── PointTest.java
│           ├── TwoSumTest.java
│           └── GroupAnagramsTest.java
└── scripts/
    ├── test.sh                           # compile + run every JUnit suite
    ├── run.sh                            # compile + run the hash-function demo
    └── run-practice.sh                   # compile + run the practice demos
```

## Run the tests

```bash
./scripts/test.sh
```

This compiles everything under `src/` and runs the whole suite.
`StringHashTest` checks the hashes the notes trace by hand — including the
anagrams `CAB` and `ABC`, which the polynomial hash finally tells apart — and
confirms `javaHash` agrees with `String.hashCode`. The practice suites exercise
both Two Sum solutions, both Group Anagrams solutions, and the `Point`
equals/hashCode round-trip through a `HashMap`.

## Run the demos

```bash
./scripts/run.sh           # hash a few sample words with each hash function
./scripts/run-practice.sh  # the Point, TwoSum, and GroupAnagrams demos
```
