package bio.knowledge.validator;

import java.util.Objects;

/**
 * An immutable pair of objects
 * 
 * @author Lance Hannestad
 *
 * @param <X>
 * @param <Y>
 */
public class Pair<X, Y> {
	
	private X x;
	private Y y;
	
	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public X getX() {
		return x;
	}
	
	public Y getY() {
		return y;
	}
	
	/**
	 * One Pair is equal to another if aniviated only if their paired members
	 * are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Pair<?, ?>) {
			Pair<?, ?> p = (Pair<?, ?>) other;
			return Objects.equals(x, p.getX()) && Objects.equals(y, p.getY());
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[x=" + x.toString() + ",y=" + y.toString() + "]";
	}

}
