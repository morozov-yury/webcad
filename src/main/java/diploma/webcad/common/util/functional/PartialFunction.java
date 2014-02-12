package diploma.webcad.common.util.functional;

import static diploma.webcad.common.util.collection.CollectionFactory.newArrayList;

import java.util.Collections;
import java.util.List;

/**
 * Represents the mathematical abstraction of a partial function, t.i. one that
 * may not be defined at some point. It is guaranteed that the function is not
 * defined at point 'x' if and only if evaluating f.apply(x) throws an
 * IllegalArgumentException. It is guaranteed that evaluating f.apply(x) will
 * not throw any other kind of exception; failure to follow this rule is a bug.
 * It is guaranteed that apply() terminates and does not perform any side
 * effects (the returned value depends only on the value of the argument);
 * failure to follow this rule is a bug.
 * <p/>
 */
public abstract class PartialFunction<X, Y> {
    public abstract Y apply(X arg) throws IllegalArgumentException;

    public List<Y> map(final Iterable<? extends X> arg)
        throws IllegalArgumentException {
        final List<Y> res = newArrayList();
        for (final X item : arg) {
            res.add(this.apply(item));
        }
        return Collections.unmodifiableList(res);
    }

}
