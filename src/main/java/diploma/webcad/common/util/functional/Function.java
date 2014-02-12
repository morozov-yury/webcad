package diploma.webcad.common.util.functional;

import static diploma.webcad.common.util.collection.CollectionFactory.newArrayList;

import java.util.Collections;
import java.util.List;

import diploma.webcad.common.util.collection.CollectionUtils;


/**
 * Represents the mathematical abstraction of a total function, t.i. one that is
 * defined at any point of its domain. It is thus guaranteed that evaluating
 * f.apply(x) for any x will not throw any kind of exception; failure to follow
 * this rule is a bug.
 * <p/>
 */
public abstract class Function<X, Y> extends PartialFunction<X, Y> {
    public abstract Y apply(X arg) /* throws nothing */;

    public <Z> Function<X, Z> then(final Function<? super Y, Z> next) {
        return then(next, false);
    }

    public <Z> Function<X, Z> then(final Function<? super Y, Z> next, final boolean propagateNull) {
        return new Function<X, Z>() {
            @Override
            public Z apply(final X arg) {

                Y value = Function.this.apply(arg);

                if(propagateNull && value == null) {
                    return null;
                }
                return next.apply(value);
            }

            @Override
            public List<Z> map(final Iterable<? extends X> list) {
                return next.map(Function.this.map(list));
            }
        };
    }

    public List<Y> map(final Iterable<? extends X> arg) {
        if (arg instanceof List) {
            return CollectionUtils.map(this, (List<? extends X>) arg);
        } else {
            final List<Y> res = newArrayList();
            for (final X item : arg) {
                res.add(this.apply(item));
            }
            return Collections.unmodifiableList(res);
        }
    }

}
