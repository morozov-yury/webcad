package diploma.webcad.common.util.functional;

public interface Filter<T> {
    boolean fits(T t);
}
