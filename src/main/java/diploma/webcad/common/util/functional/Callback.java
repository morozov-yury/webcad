package diploma.webcad.common.util.functional;

/**
 * procedure
 * @author Arktos
 */
public abstract class Callback<T> extends Function<T, T> {
    public final T apply(final T arg) {
        doWith(arg);
        return arg;
    }

    public abstract void doWith(T t);

    public void doWithAll(final Iterable<T> ts) {
        for (final T t : ts) {
            doWith(t);
        }
    }
}
