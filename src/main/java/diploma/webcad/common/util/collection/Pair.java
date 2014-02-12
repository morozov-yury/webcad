package diploma.webcad.common.util.collection;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

public class Pair<X, Y> implements Serializable {
	private static final long serialVersionUID = 8507916833311772880L;
	public final X first;
    public final Y second;

    public Pair(final X first, final Y second) {
        this.first = first;
        this.second = second;
    }

    public X getFirst() {
        return first;
    }

    public Y getSecond() {
        return second;
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Pair<?, ?> pair = (Pair<?, ?>) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) {
            return false;
        }
        if (second != null ? !second.equals(pair.second) :
            pair.second != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = (first != null ? first.hashCode() : 0);
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    public String toString() {
        return String.format("(%s, %s)", first, second);
    }

    public static <X, Y> Pair<X, Y> of(final X first, final Y second) {
        return new Pair<X, Y>(first, second);
    }

    public static <K, V extends Comparable<? super V>> Comparator<Pair<K, V>> compareOnSecondAsc() {
        return new Comparator<Pair<K, V>>() {
            public int compare(final Pair<K, V> o1, final Pair<K, V> o2) {
                return o1.second.compareTo(o2.second);
            }
        };
    }

    public static <K, V extends Comparable<? super V>> Comparator<Pair<K, V>> compareOnSecondDesc() {
        final Comparator<Pair<K, V>> cmp = compareOnSecondAsc();
        return Collections.reverseOrder(cmp);
    }

    public static <K extends Comparable<? super K>, V> Comparator<Pair<K, V>> compareOnFirstAsc() {
        return new Comparator<Pair<K, V>>() {
            public int compare(final Pair<K, V> o1, final Pair<K, V> o2) {
                return o1.first.compareTo(o2.first);
            }
        };
    }

    public static <K extends Comparable<? super K>, V> Comparator<Pair<K, V>> compareOnFirstDesc() {
        final Comparator<Pair<K, V>> cmp = compareOnFirstAsc();
        return Collections.reverseOrder(cmp);
    }
}
