package practice;

import java.util.Objects;

/**
 * A grid coordinate with an {@code x} and a {@code y}. Two points are equal
 * when both coordinates match.
 *
 * <p>Because this class overrides {@code equals}, it must also override
 * {@code hashCode} from the same fields: equal points are required to share a
 * hash code. Without that, a {@code HashMap} sends two equal points to
 * different slots and never consults {@code equals}, so a lookup for a point
 * just stored under an equal key misses.
 */
public class Point {

  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Point)) return false;
    Point other = (Point) o;
    return this.x == other.x && this.y == other.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}
