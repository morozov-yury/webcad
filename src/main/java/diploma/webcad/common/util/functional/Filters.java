package diploma.webcad.common.util.functional;

//import com.comunicator.common.util.Su;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import diploma.webcad.common.util.collection.CollectionFactory;
import diploma.webcad.common.util.collection.Pair;

public abstract class Filters {
    private Filters() {
    }

    public static <K> Filter<K> or(final Filter<K> a, final Filter<K> b) {
        return new Filter<K>() {
            public boolean fits(final K k) {
                return a.fits(k) || b.fits(k);
            }
        };
    }

    public static <K> Filter<K> or(final Filter<K>... filters) {
        return or(CollectionFactory.list(filters));
    }

    public static <K> Filter<K> or(final Iterable<Filter<K>> filters) {
        return new Filter<K>() {
            public boolean fits(final K k) {
                for (final Filter<K> filter : filters) {
                    if (filter.fits(k)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public static <K> Filter<K> and(final Filter<K> a, final Filter<K> b) {
        return new Filter<K>() {
            public boolean fits(final K k) {
                return a.fits(k) && b.fits(k);
            }
        };
    }

    public static <K> Filter<K> and(final Filter<K>... filters) {
        return and(CollectionFactory.list(filters));
    }

    /**
     * Constructs a new and-filter: entity fits if it fits all the filters.
     * <p/>
     * If filters are empty, then any entity fits.
     *
     * @param filters constituent filters
     * @param <K>     type of entities filtered
     * @return and-filter
     */
    public static <K> Filter<K> and(final Iterable<Filter<K>> filters) {
        return new Filter<K>() {
            public boolean fits(final K k) {
                for (final Filter<K> filter : filters) {
                    if (!filter.fits(k)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public static <K> Filter<K> implies(final Filter<K> a, final Filter<K> b) {
        return new Filter<K>() {
            public boolean fits(final K k) {
                return b.fits(k) || !a.fits(k);
            }
        };
    }

    public static <K> Filter<K> not(final Filter<K> f) {
        return new Filter<K>() {
            public boolean fits(K k) {
                return !f.fits(k);
            }
        };
    }

    public static <K, V> Filter<K> on(final Function<? super K, V> fun,
                                      final Filter<V> filter) {
        return new Filter<K>() {
            public boolean fits(final K k) {
                return filter.fits(fun.apply(k));
            }
        };
    }

    public static <K> Filter<K> memberOf(final Collection<? extends K> ofWhat) {
        final Set<? extends K> set =
                (ofWhat instanceof Set) ? (Set<? extends K>) ofWhat :
                        new HashSet<K>(ofWhat);

        return new Filter<K>() {
            public boolean fits(final K k) {
                return set.contains(k);
            }
        };
    }

    public static <K, V> Filter<Pair<K, V>> onSecond(final Filter<V> filter) {
        return new Filter<Pair<K, V>>() {
            public boolean fits(final Pair<K, V> kvPair) {
                return filter.fits(kvPair.second);
            }
        };
    }

    public static <K, V> Filter<Pair<K, V>> onFirst(final Filter<K> filter) {
        return new Filter<Pair<K, V>>() {
            public boolean fits(final Pair<K, V> kvPair) {
                return filter.fits(kvPair.first);
            }
        };
    }

    public static <X> Filter<X> equalTo(final X item) {
        return new Filter<X>() {
            public boolean fits(final X x) {
                return x == item || x.equals(item);
            }
        };
    }

    public static <T> Filter<T> isNotNull() {
        return new Filter<T>() {
            public boolean fits(final T t) {
                return t != null;
            }
        };
    }

    public static <T> Filter<T> isNull() {
        return new Filter<T>() {
            public boolean fits(final T t) {
                return t == null;
            }
        };
    }

    public static <K> Filter<K> cached(final Filter<K> filter) {
        final Map<K, Boolean> map = new WeakHashMap<K, Boolean>();
        return new Filter<K>() {
            public boolean fits(final K k) {
                Boolean res = map.get(k);
                if (res == null) {
                    map.put(k, res = filter.fits(k));
                }
                return res;
            }
        };
    }

    public enum Comparison {
        EQ, LT, GT, LE, GE
    }

    public static <X extends Comparable<? super X>> Filter<X> comparesAs(final Comparison cmp,
                                                                         final X rightSide) {
        return new Filter<X>() {
            public boolean fits(final X x) {
                int c = x.compareTo(rightSide);
                if (c == 0) {
                    return (cmp == Comparison.EQ || cmp == Comparison.LE ||
                            cmp == Comparison.GE);
                }
                if (c < 0) {
                    return (cmp == Comparison.LT || cmp == Comparison.LE);
                } else {
                    return (cmp == Comparison.GT || cmp == Comparison.GE);
                }
            }
        };
    }

    public static <T> Filter<T> alwaysTrue() {
        return new Filter<T>() {
            public boolean fits(final T t) {
                return true;
            }
        };
    }

    public static <T> Filter<T> alwaysFalse() {
        return new Filter<T>() {
            public boolean fits(final T t) {
                return false;
            }
        };
    }


    public static Filter<String> matchesPatternExact(final Pattern p) {
        return new Filter<String>() {
            public boolean fits(final String s) {
                return p.matcher(s).matches();
            }
        };
    }

    public static Filter<String> matchesPatternAnywhere(final Pattern p) {
        return new Filter<String>() {
            public boolean fits(final String s) {
                return p.matcher(s).find();
            }
        };
    }

    public static Filter<String> matchesPatternAnywhere(final String pattern) {
        return matchesPatternAnywhere(Pattern.compile(pattern));
    }

    public static Filter<String> matchesPatternExact(final String pattern) {
        return matchesPatternExact(Pattern.compile(pattern));
    }

    public static Filter<String> isNotEmpty() {
        return new Filter<String>() {
            public boolean fits(final String value) {
                //return !Su.isEmpty(value);
            	return StringUtils.isEmpty(value);
            }
        };
    }

    @Deprecated
    public static <T> Filter<T> contains(final Collection<T> collection) {
        return memberOf(collection);
    }
}
