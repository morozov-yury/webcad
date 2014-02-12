package diploma.webcad.common.util.collection;

import static diploma.webcad.common.util.collection.CollectionFactory.newArrayList;
import static diploma.webcad.common.util.collection.CollectionFactory.newLinkedMap;
import static diploma.webcad.common.util.collection.CollectionFactory.newLinkedSet;
import static diploma.webcad.common.util.collection.CollectionFactory.newList;
import static diploma.webcad.common.util.collection.CollectionFactory.newUnorderedMap;
import static diploma.webcad.common.util.functional.Filters.memberOf;
import static diploma.webcad.common.util.functional.Filters.not;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import diploma.webcad.common.util.functional.Filter;
import diploma.webcad.common.util.functional.Function;
import diploma.webcad.common.util.functional.PartialFunction;
import diploma.webcad.core.annotation.NotNull;
import diploma.webcad.core.annotation.Nullable;

public class CollectionUtils {
    /**
     * @deprecated use {@link com.comunicator.common.util.collections.CollectionFactory#list(Object[])}
     *             )}
     */
    public static <T> List<T> list(final T... values) {
        return CollectionFactory.list(values);
    }

    /**
     * @deprecated use {@link com.comunicator.common.util.collections.CollectionFactory#set(Object[])}
     *             )}
     */
    public static <K> Set<K> set(final K... items) {
        return CollectionFactory.set(items);
    }

    /**
     * Infinitely repeats a value
     */
    public static <T> Iterable<T> repeat(final T value) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    public boolean hasNext() {
                        return true;
                    }

                    public T next() {
                        return value;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static <T> Iterable<T> cycle(final Iterable<T> src) {
        return iterable(new Iterator<T>() {
            private Iterator<T> holder = src.iterator();

            public boolean hasNext() {
                if (!holder.hasNext()) {
                    holder = src.iterator();
                }
                return true;
            }

            public T next() {
                if (!hasNext()) {
                    throw new RuntimeException();//we always have next
                }
                return holder.next();
            }

            public void remove() {
                throw new UnsupportedOperationException("not supported");
            }
        });
    }

    public static <T> Iterable<T> filter(final Iterable<? extends T> source,
                                         final Filter<? super T> filter) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new FilterIterator<T>(source, filter);
            }
        };
    }

    /**
     * Returns new list, containing only elements from original list for which
     * filter yields true.
     */
    public static <T> List<T> filterList(final Iterable<? extends T> source,
                                         final Filter<? super T> filter) {
        final List<T> res = newArrayList();
        for (final T t : source) {
            if (filter.fits(t)) {
                res.add(t);
            }
        }
        return res;
    }

    /**
     * Returns new list, containing only elements from original list for which
     * filter yields true. Feeds from any {@link Collection}.
     */
    public static <T> List<T> filterAsList(final Collection<? extends T> source,
                                           final Filter<? super T> filter) {
        final List<T> res = newArrayList(source.size());
        for (final T t : source) {
            if (filter.fits(t)) {
                res.add(t);
            }
        }
        return res;
    }

    /**
     * Overloaded for filter list
     */
    public static <T> List<T> filterAsList(final Collection<? extends T> source,
                                           final List<Filter<? super T>> filters) {
        final List<T> res = newArrayList(source.size());
        external:
        for (final T t : source) {
            for (final Filter<? super T> filter : filters) {
                if (!filter.fits(t)) {
                    continue external;
                }
            }
            res.add(t);
        }
        return res;
    }

    public static <T> List<T> join(final Collection<? extends T>... parts) {
        int size = 0;
        for (final Collection<? extends T> part : parts) {
            size += part.size();
        }
        final List<T> result = newList(size);
        for (final Collection<? extends T> part : parts) {
            result.addAll(part);
        }
        return result;
    }

    public static <T> List<T> join(
        final Iterable<? extends Collection<? extends T>> parts) {
        int size = 0;
        for (final Collection<? extends T> part : parts) {
            size += part.size();
        }
        final List<T> result = newList(size);
        for (final Collection<? extends T> part : parts) {
            result.addAll(part);
        }
        return result;
    }

//    /**
//     * Splits items in the list into equivalence classes (equal by 'first') and
//     * returns a list of best representatives of each equivalence class, 'best'
//     * being defined as 'first in the natural ordering induced by 'second').
//     *
//     * @param first  uniqueness comparator
//     * @param second comparator for choosing the best of equal items
//     */
//    public static <B, T extends B> List<T> uniq(
//        final Comparator<B> first,
//        final Comparator<B> second,
//        final List<T> list) {
//        // Schwartz transform
//        class Indexed implements Comparable<Indexed> {
//            final T item;
//            final int index;
//
//            public Indexed(final T arg, final int i) {
//                item = arg;
//                index = i;
//            }
//
//            public int compareTo(final Indexed other) {
//                return index - other.index;
//            }
//        }
//        final Function<T, Indexed> ADD_INDEX = new Function<T, Indexed>() {
//            int i = 0;
//
//            public Indexed apply(final T arg) {
//                return new Indexed(arg, i++);
//            }
//        };
//        final Function<Indexed, T> DROP_INDEX = new Function<Indexed, T>() {
//            public T apply(final Indexed arg) {
//                return arg.item;
//            }
//        };
//
//        final Comparator<B> together =
//            Comparators.chainedComparator(first, second);
//
//        final Comparator<Indexed> ON_ITEM = new Comparator<Indexed>() {
//            public int compare(final Indexed a, final Indexed b) {
//                return together.compare(a.item, b.item);
//            }
//        };
//
//        final List<Indexed> sorted = Cf.newList(map(ADD_INDEX, list));
//
//        // Sort on item
//        Collections.sort(sorted, ON_ITEM);
//
//        final List<Indexed> res = new ArrayList<Indexed>();
//        if (sorted.size() == 0) {
//            return new ArrayList<T>();
//        }
//
//        Indexed last = sorted.get(0);
//
//        res.add(last);
//
//        // Uniq
//        for (int i = 1; i < sorted.size(); ++i) {
//            final Indexed ix = sorted.get(i);
//            if (first.compare(ix.item, last.item) != 0) {
//                res.add(ix);
//            }
//            last = ix;
//        }
//
//        // Sort according to index (stabilize)
//        Collections.sort(res);
//
//        return map(DROP_INDEX, res);
//    }

    /**
     * @return An unmodifiable random-access list built from the results of
     *         applying 'f' to each element of 'source' in order of iteration.
     */
    public static <A, R> List<R> map(final Function<? super A, R> f,
                                     final Iterable<A> source) {
        return f.map(source);
    }

//    /**
//     * @return An unmodifiable random-access list built from the results of
//     *         applying function created by fable 'f' to each element of
//     *         'source' in order of iteration.
//     */
//    public static <A, R> List<R> map(final Functiable<? super A, R> fable,
//                                     final Iterable<A> source) {
//        return fable.asFunction().map(source);
//    }

    /**
     * @return An unmodifiable random-access list built from the results of
     *         applying 'f' to each element of 'list' in order of iteration.
     */
    public static <A, R> List<R> map(final Function<? super A, R> f,
                                     final List<A> list) {
        if (list.isEmpty()) {
            return emptyList();
        }
        if (list.size() == 1) {
            return singletonList(f.apply(list.get(0)));
        }
        final List<R> res = newArrayList(list.size());
        for (final A item : list) {
            res.add(f.apply(item));
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * @return An unmodifiable random-access list built from the results of
     *         applying 'f' to each element of 'source' in order of iteration,
     *         except for those where f.apply() throws IllegalArgumentException.
     *         Those elements are simply skipped.
     */
    public static <A, R> List<R> mapWhereDefined(
        final PartialFunction<? super A, R> f,
        final Iterable<A> source) {
        final List<R> res = new ArrayList<R>();
        for (final A item : source) {
            try {
                res.add(f.apply(item));
            } catch (IllegalArgumentException e) {
                // skip this element
            }
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * An analog of {@link #mapWhereDefined(PartialFunction, Iterable)}, but
     * slightly more efficient (avoids reallocation of the result list at growth
     * by initially allocating as many items as there are in the source list).
     * <p/>
     * Can also be LESS efficient if the function is sparse, t.i. the fraction
     * of skipped elements is large, since in this case it will be a waste of
     * space to allocate as many items as the source list contains.
     *
     * @return An unmodifiable random-access list built from the results of
     *         applying 'f' to each element of 'sourceList' in order of
     *         iteration, except for those where f.apply() throws
     *         IllegalArgumentException. Those elements are simply skipped.
     */
    public static <A, R> List<R> mapWhereDefined(
        final PartialFunction<? super A, R> f,
        final List<A> sourceList) {
        if (sourceList.isEmpty()) {
            return emptyList();
        }
        if (sourceList.size() == 1) {
            try {
                return singletonList(f.apply(sourceList.get(0)));
            } catch (IllegalArgumentException e) {
                return emptyList();
            }
        }
        final List<R> res = newArrayList(sourceList.size());
        for (final A item : sourceList) {
            try {
                res.add(f.apply(item));
            } catch (IllegalArgumentException e) {
                // skip this element
            }
        }
        return Collections.unmodifiableList(res);
    }

    public static <K, V, E> Map<K, V> mapFromIterable(
        final Function<? super E, ? extends K> k,
        final Function<? super E, ? extends V> v,
        final Iterable<E> source) {
        final Map<K, V> res = new LinkedHashMap<K, V>();
        for (E item : source) {
            res.put(k.apply(item), v.apply(item));
        }
        return res;
    }

//    public static <K, V, E> Map<K, V> mapFromIterable(
//        final Function<? super E, ? extends K> k,
//        final Function<? super E, ? extends V> v,
//        final Iterable<E> source,
//        final Builder<Map<K, V>> mapBuilder) {
//        final Map<K, V> res = mapBuilder.build();
//        for (final E item : source) {
//            res.put(k.apply(item), v.apply(item));
//        }
//        return res;
//    }
//
//    public static <K, V, E> MultiMap<K, V> multiMapFromIterable(
//        final Function<? super E, ? extends K> k,
//        final Function<? super E, ? extends V> v,
//        final Iterable<E> source) {
//        final MultiMap<K, V> res = newMultiMap();
//        for (final E item : source) {
//            res.append(k.apply(item), v.apply(item));
//        }
//        return res;
//    }
//
//    /**
//     * create empty multimap built upon Collections.emptyMap(), making it
//     * effectively immutable
//     *
//     * @param <K> key
//     * @param <V> stored value
//     * @return empty multimap. doesn't create new one for every request
//     */
//    public static <K, V> MultiMap<K, V> emptyMultiMap() {
//        return (MultiMap<K, V>) EMPTY_MULTI_MAP;
//    }
//
//    private final static MultiMap<Object, Object> EMPTY_MULTI_MAP =
//        new MultiMap<Object, Object>((Map<Object, List<Object>>) Collections.EMPTY_MAP);

    public static <V, K> Map<K, V> toMapWithComputedKey(
        final Function<? super V, K> val2key,
        final Collection<? extends V> values) {
        final Map<K, V> res = new LinkedHashMap<K, V>(values.size());
        for (final V item : values) {
            res.put(val2key.apply(item), item);
        }
        return res;

    }

    public static <V, K> Map<K, V> toMapWithComputedKey(
        final Function<? super V, K> val2key,
        final Iterable<? extends V> values) {
        final Map<K, V> res = new LinkedHashMap<K, V>();
        for (final V item : values) {
            res.put(val2key.apply(item), item);
        }
        return res;
    }

//    public static <V, K> MultiMap<K, V> toMultiMapWithComputedKey(
//        final Function<? super V, K> val2key,
//        final Iterable<? extends V> values) {
//        final MultiMap<K, V> res = new MultiMap<K, V>(true);
//        for (final V item : values) {
//            res.append(val2key.apply(item), item);
//        }
//        return res;
//    }

    public static <T> int count(
        final Iterable<T> items,
        final Filter<? super T> filter) {
        int res = 0;
        for (final T item : items) {
            if (filter.fits(item)) {
                res++;
            }
        }
        return res;
    }

    public static <T, U> List<U> filterByType(final List<? super T> objects,
                                              final Class<U> clazz,
                                              final boolean keepNulls) {
        final List<U> res = new ArrayList<U>();

        for (final Object item : objects) {
            if ((item == null && keepNulls) ||
                (item != null && clazz.isAssignableFrom(item.getClass()))) {
                res.add(clazz.cast(item));
            }
        }

        return res;
    }

    @Nullable
    public static <T> T firstOrNull(final Iterable<T> items) {
        return nextOrNull(items.iterator());
    }

    @Nullable
    public static <T> T nextOrNull(final Iterator<T> iterator) {
        return iterator.hasNext() ? iterator.next() : null;
    }

    public static <K, V> Map<K, V> zipMap(final K[] keys, final V[] values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException(
                "There must be an equal number of keys/values, " +
                    "whereas there are " + keys.length + " keys " + values.length +
                    " values");
        }
        final Map<K, V> res = newLinkedMap();
        for (int i = 0; i < keys.length; ++i) {
            res.put(keys[i], values[i]);
        }
        return res;
    }

    public static <T> T first(
        final Iterable<T> items) throws NoSuchElementException {
        return items.iterator().next();
    }

    public static <T> T first(
        final Iterable<T> items, final Filter<T> filter, @Nullable T defValue
    ) {
        for (T item : items) {
            if (filter.fits(item)) {
                return item;
            }
        }
        return defValue;
    }

    /**
     * Returns unmodifiable view of tail (all items but the first one) of a
     * list, or the empty unmodifiable list if the source list is empty.
     *
     * @param list input list, should support <code>sublist()</code> method
     * @return empty list if the list is empty; otherwise an unmodifiable view
     *         of its tail
     */
    public static <T> List<T> tail(final List<T> list) {
        if (list.isEmpty()) {
            return emptyList();
        } else {
            return unmodifiableList(list.subList(1, list.size()));
        }
    }

    /**
     * Attempts to take n items off the end of the the given list
     */
    public static <T> List<T> tail(final List<T> list, int count) {
        if (list.isEmpty()) {
            return emptyList();
        }
        return unmodifiableList(
            list.subList(
                Math.max(0, list.size() - count), list.size()));
    }

    @Deprecated
    public static <T> T head(final List<T> list)
        throws IllegalArgumentException {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        return list.get(0);
    }

//    public static <K, V> MapWithDefault<K, V> withDefault(final Map<K, V> map,
//                                                          final V def) {
//        return new MapWithDefault<K, V>(map, def);
//    }

    public static <T> List<T> head(final List<T> list, final int count) {
        if (list.isEmpty()) {
            return emptyList();
        }
        return unmodifiableList(list.subList(0, Math.min(count, list.size())));
    }

    public static <A, B> List<Pair<A, B>> zip(
        final List<A> as,
        final List<B> bs) {
        final List<Pair<A, B>> res = new ArrayList<Pair<A, B>>();
        int n = as.size();
        if (n != bs.size()) {
            throw new IllegalArgumentException(
                "List sizes must be equal, but got: as=" + as.size() + ", bs=" +
                    bs.size());
        }
        for (int i = 0; i < n; ++i) {
            res.add(new Pair<A, B>(as.get(i), bs.get(i)));
        }
        return res;
    }

    public static <K extends Comparable<? super K>> List<K> sortAsc(
        final Collection<K> items) {
        final List<K> res = newList(items);
        Collections.sort(res);
        return res;
    }
    
    /**
     * 
     * @param items - should be sorted
     * @return list without consecutive duplicates
     */
    public static <K> List<K> unique(final Collection<K> items) {
    	final List<K> res = newArrayList();
    	for(K val : items)
    		if(res.size() == 0 || !last(res).equals(val))
    			res.add(val);
    	return res;
    }

    public static <K> List<K> sortAsc(final Collection<K> items,
                                      final Comparator<? super K> comparator) {
        final List<K> res = newList(items);
        Collections.sort(res, comparator);
        return res;
    }

    /**
     * Computes an <i>ordering permutation</i> of the list, t.i., such a
     * permutation that permuting the list with it will give an ordered list
     * (with respect to the given comparator). For example, for a list 42 37 16
     * 4 20, the ordering permutation will be 3 2 4 1 0.
     */
    public static <T> List<Integer> orderingAsc(final List<T> ts,
                                                final Comparator<? super T> comparator) {
        // TODO IMHO, function violates the specification
        final List<Pair<Integer, T>> indexed = zip(range(0, ts.size()), ts);
        Collections.sort(indexed, Pair.<Integer, T>compareOnFirstAsc());
        return unzip(indexed).first;
    }

    /**
     * Like {@link #orderingAsc(java.util.List, java.util.Comparator)}, but with
     * reversed ordering
     */
    public static <T> List<Integer> orderingDesc(
        final List<T> ts,
        final Comparator<? super T> comparator) {
        return orderingAsc(ts, Collections.reverseOrder(comparator));
    }

//    /**
//     * Like {@link #orderingAsc(java.util.List, java.util.Comparator)}, with
//     * natural ordering
//     */
//    public static <T extends Comparable<? super T>> List<Integer> orderingAsc(
//        final List<T> ts) {
//        return orderingAsc(ts, Comparators.<T>naturalOrder());
//    }
//
//    /**
//     * Like {@link #orderingAsc(java.util.List, java.util.Comparator)}, but with
//     * reversed natural ordering
//     */
//    public static <T extends Comparable<? super T>> List<Integer> orderingDesc(
//        final List<T> ts) {
//        return orderingDesc(ts, Comparators.<T>naturalOrder());
//    }

    public static <K> List<K> take(final int count, final List<K> items) {
        return new ArrayList<K>(
            items.subList(
                0, Math.min(count, items.size())));
    }

    public static <K> List<K> take(final int count, final Iterator<K> seq) {
        return take(count, iterable(seq));
    }

    public static <K> List<K> take(final int count, final Iterable<K> seq) {
        List<K> res = new ArrayList<K>();
        Iterator<K> it = seq.iterator();
        for (int i = 0; i < count && it.hasNext(); ++i) {
            res.add(it.next());
        }
        return res;
    }

    public static <K> List<K> take(final List<K> seq, final int from, final int count) {
        final List<K> res = new ArrayList<K>();
        for (int i = from; i < count + from && i < seq.size(); ++i) {
            res.add(seq.get(i));
        }
        return res;
    }

    public static <K> K maxBy(Iterable<? extends K> items,
                              Comparator<? super K> comparator) {
        final Iterator<? extends K> iter = items.iterator();
        if (!iter.hasNext()) {
            throw new NoSuchElementException();
        }

        K res = iter.next();

        while (iter.hasNext()) {
            final K next = iter.next();
            if (comparator.compare(next, res) > 0) {
                res = next;
            }
        }
        return res;
    }

    /**
     * @deprecated use {@link com.comunicator.common.util.collections.CollectionFactory#list(Iterable)}
     *             )}
     */
    public static <K> List<K> toList(final Iterable<? extends K> items) {
        return CollectionFactory.list(items);
    }

    public static <K, V> Iterable<V> mapIterable(final Iterable<K> src,
                                                 final Function<K, V> f) {
        return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new MapIterator<K, V>(src, f);
            }
        };
    }


    public static <K, V> Iterable<V> mapIterable(final Iterable<K> src,
                                                 final Function<K, V> f, final int batchSize) {
        if (batchSize < 1) {
            throw new IllegalArgumentException();
        }
        return iterable(
            new Iterator<V>() {
                final Iterator<K> srcIter = src.iterator();

                private Iterator<V> advance() {
                    return f.map(take(batchSize, srcIter)).iterator();
                }

                Iterator<V> batchIter = null;

                public boolean hasNext() {
                    if (batchIter == null || !batchIter.hasNext()) {
                        batchIter = advance();
                    }
                    return batchIter.hasNext();
                }

                public V next() {
                    return batchIter.next();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            }
        );
    }


    /**
     * unlike mapIterable, the returned Itareble does not throw an exception if
     * src.next() or f.apply() throws an exception; it just returns null in this
     * case Use it if you want to ignor errors in f.apply() and continue loop
     */
    public static <K, V> Iterable<V> unsafeMapIterable(final Iterable<K> src,
                                                       final Function<K, V> f) {
        return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new UnsafeMapIterator<K, V>(src, f);
            }
        };
    }

    public static <A, B> Pair<List<A>, List<B>> unzip(
        final Iterable<Pair<A, B>> pairs) {
        final Pair<List<A>, List<B>> res =
            new Pair<List<A>, List<B>>(new ArrayList<A>(), new ArrayList<B>());
        for (Pair<A, B> pair : pairs) {
            res.first.add(pair.first);
            res.second.add(pair.second);
        }
        return res;
    }

//    public static <A, B> Pair<NullableList<A>, NullableList<B>> unzipN(
//        final Iterable<NullablePair<A, B>> pairs) {
//        final Pair<NullableList<A>, NullableList<B>> res =
//            pair(
//                NullableList.wrap(new ArrayList<A>()),
//                NullableList.wrap(new ArrayList<B>()));
//        for (final NullablePair<A, B> pair : pairs) {
//            res.first.add(pair.first);
//            res.second.add(pair.second);
//        }
//        return res;
//    }
//
//    public static <A, B> Pair<NotNullList<A>, NotNullList<B>> unzipNN(
//        final Iterable<NotNullPair<A, B>> pairs) {
//        final Pair<NotNullList<A>, NotNullList<B>> res =
//            pair(
//                NotNullList.wrap(new ArrayList<A>()),
//                NotNullList.wrap(new ArrayList<B>()));
//        for (final NotNullPair<A, B> pair : pairs) {
//            res.first.add(pair.first);
//            res.second.add(pair.second);
//        }
//        return res;
//    }

    /**
     * Converts an iterator to iterable.
     * <p/>
     * WARNING: Use with extreme care! The iterator 'iter' will, of course, be
     * modified during iteration on the return value (it is not cloned), so
     * don't use it after invocation of this method or you'll get extremely
     * subtle and hard to catch errors!
     */
    public static <T> Iterable<T> iterable(final Iterator<T> iter) {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return iter;
            }
        };
    }

    public static <T> T last(final List<T> list) {
        return list.get(list.size() - 1);
    }


    public static <T> T lastOrNull(final List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return last(list);
    }

    public static <T> LinkedHashSet<T> union(final Collection<? extends T> as,
                                             final Collection<? extends T> bs) {
        final LinkedHashSet<T> res =
            new LinkedHashSet<T>(as.size() + bs.size(), 1);
        res.addAll(as);
        res.addAll(bs);
        return res;
    }

    public static <T> LinkedHashSet<T> union(final Collection<? extends T>... collections) {
        final LinkedHashSet<T> res = new LinkedHashSet<T>();
        for (Collection<? extends T> c : collections) {
            res.addAll(c);
        }
        return res;
    }

    public static <T> LinkedHashSet<T> unionAll(final List<List<T>> collections) {
        final LinkedHashSet<T> res = new LinkedHashSet<T>();
        for (Collection<T> c : collections) {
            res.addAll(c);
        }
        return res;
    }

    public static <T> List<T> add(final List<? extends T> a, final T t) {
        final List<T> b = newArrayList(a);
        b.add(t);
        return b;
    }

    public static <T> Set<T> minus(
        final Collection<T> as,
        final Collection<? super T> bs) {
        final Set<T> res = new LinkedHashSet<T>(as);
        res.removeAll(bs);
        return res;
    }

    public static <K, V> List<Pair<K, V>> unzipR(
        final Iterable<Pair<K, List<V>>> pairs) {
        final List<Pair<K, V>> res = new ArrayList<Pair<K, V>>();
        for (final Pair<K, ? extends Iterable<V>> pair : pairs) {
            for (final V v : pair.second) {
                res.add(new Pair<K, V>(pair.first, v));
            }
        }
        return res;
    }

    public static <K, V> List<Pair<K, V>> unzipL(
        final Iterable<Pair<List<K>, V>> pairs) {
        final List<Pair<K, V>> res = new ArrayList<Pair<K, V>>();
        for (final Pair<? extends Iterable<K>, V> pair : pairs) {
            for (final K k : pair.first) {
                res.add(new Pair<K, V>(k, pair.second));
            }
        }
        return res;
    }

    /**
     * Produces List containing n elements, each being the reference to t
     * param.
     *
     * @param n number of element
     * @param t "original" instance
     * @return List of size n, containing t's.
     */
    public static <T> List<T> replicate(final int n, final T t) {
        final List<T> res = new ArrayList<T>(n);
        for (int i = 0; i < n; ++i) {
            res.add(t);
        }
        return res;
    }

    public static List<Integer> range(final int fromInc, final int toExc) {
        final List<Integer> res = newList();
        for (int i = fromInc; i < toExc; ++i) {
            res.add(i);
        }
        return res;
    }

    public static List<Long> range(final long fromInc, final long toExc) {
        final List<Long> res = newList();
        for (long i = fromInc; i < toExc; ++i) {
            res.add(i);
        }
        return res;
    }

    public static <T> List<T> removeSublist(final List<T> source,
                                            final int from,
                                            final int count) {
        final List<T> res = newArrayList(source.size() - count);

        // TODO Why do we iterate through all the source?
        for (int i = 0; i < source.size(); i++) {
            if (i < from || from + count - 1 < i) {
                res.add(source.get(i));
            }
        }

        return res;
    }

    public static <T> boolean isASubsetOfB(
        final Collection<T> as,
        final Collection<T> bs) {
        return bs.containsAll(as);
    }

//    public static <K, V> Map<V, K> flip(final MultiMap<K, V> map) {
//        return flip(map, true);
//    }
//
//    public static <K, V> Map<V, K> flip(final MultiMap<K, V> map, boolean allowDups) {
//        final Map<V, K> out = Cf.newUnorderedMap();
//        for (Map.Entry<K, List<V>> e : map.entrySet()) {
//            for (V v : e.getValue()) {
//                final K old = out.put(v, e.getKey());
//                if (!allowDups && old != null) {
//                    throw new RuntimeException();
//                }
//            }
//        }
//        return out;
//    }
//
//    public static <K, V> Map<V, K> flip(
//        final Map<K, V> map,
//        final Builder<Map<V, K>> bld) {
//        final Map<V, K> res = bld.build();
//        for (final Map.Entry<K, V> kv : map.entrySet()) {
//            res.put(kv.getValue(), kv.getKey());
//        }
//        return res;
//    }
//
//
//    public static <K, V> Map<V, K> flip(
//        final Map<K, V> map,
//        final Builder<Map<V, K>> bld, final boolean allowDups) {
//        final Map<V, K> res = bld.build();
//        for (final Map.Entry<K, V> kv : map.entrySet()) {
//            final K old = res.put(kv.getValue(), kv.getKey());
//            if (old != null && !allowDups) {
//                throw new RuntimeException();
//            }
//        }
//        return res;
//    }
//
//
//    public static <K, V> Map<V, K> flip(final Map<K, V> map, final boolean allowDups) {
//        return flip(map, new Builder<Map<V, K>>() {
//            @Override
//            public Map<V, K> build() {
//                return Cf.newUnorderedMap();
//            }
//        }, allowDups);
//    }
//
//    public static <K, V> Map<V, K> flip(final Map<K, V> map) {
//        return flip(
//            map, new Builder<Map<V, K>>() {
//            @Override
//            public Map<V, K> build() {
//                return Cf.newUnorderedMap();
//            }
//        }, true);
//    }
//
//    public static <K, V> MultiMap<V, K> extFlip(final Map<K, V> map) {
//        final MultiMap<V, K> res = newMultiMap();
//        for (final Map.Entry<K, V> kv : map.entrySet()) {
//            res.append(kv.getValue(), kv.getKey());
//        }
//        return res;
//    }

    public static <K, V> Map<K, V> project(
        final Map<? super K, ? extends V> map,
        final Collection<? extends K> keys) {
        final Map<K, V> res = newUnorderedMap();
        for (final K k : keys) {
            res.put(k, map.get(k));
        }
        return res;
    }

    public static <F, T> Collection<T> concatMapInto(
        final Function<? super F, ? extends Collection<? extends T>> f,
        final Iterable<? extends F> items,
        final Collection<T> accumulator) {
        for (final F item : items) {
            accumulator.addAll(f.apply(item));
        }
        return accumulator;
    }

    private static class FilterIterator<T> implements Iterator<T> {
        private final Iterator<? extends T> si;
        private final Filter<? super T> filter;
        private T next;
        private boolean hasNext;

        public FilterIterator(final Iterable<? extends T> source,
                              final Filter<? super T> filter) {
            this.filter = filter;
            si = source.iterator();
            advance();
        }

        public boolean hasNext() {
            return hasNext;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final T res = next;
            advance();
            return res;
        }

        public void remove() {
            si.remove();
        }

        private void advance() {
            hasNext = false;
            while (si.hasNext()) {
                next = si.next();
                if (filter.fits(next)) {
                    hasNext = true;
                    break;
                }
            }
        }
    }

    public static <T> Set<T> intersect(final Collection<T> first,
                                       final Collection<T>... rest) {
        // TODO IMHO, not very optimal. Better choose the smallest collection at first.
        final Set<T> res = newLinkedSet();
        top:
        for (final T t : first) {
            for (final Collection<T> c : rest) {
                if (!c.contains(t)) {
                    continue top;
                }
            }
            res.add(t);
        }
        return res;
    }

    private static class MapIterator<K, V> implements Iterator<V> {
        private Iterator<K> ki;
        private final Function<K, V> f;

        public MapIterator(final Iterable<K> src, final Function<K, V> f) {
            this.f = f;
            ki = src.iterator();
        }

        public boolean hasNext() {
            return ki.hasNext();
        }

        public V next() {
            final K key = ki.next();
            return f.apply(key);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * UnsafeMapIterator does not throw an exception if src.next() or f.apply()
     * throws and exception; it just returns null in this case
     */
    private static class UnsafeMapIterator<K, V> extends MapIterator<K, V> {

        public UnsafeMapIterator(
            final Iterable<K> src,
            final Function<K, V> f) {
            super(src, f);
        }

        @Override
        public V next() {
            try {
                return super.next();
            } catch (final Exception e) {
                return null;
            }
        }
    }


    public static <K, V> List<Pair<K, V>> zipWithConstantL(
        final K constant,
        final Iterable<? extends V> rest) {
        final List<Pair<K, V>> res = new ArrayList<Pair<K, V>>();
        for (final V v : rest) {
            res.add(new Pair<K, V>(constant, v));
        }
        return res;
    }

    public static <K, V> List<Pair<V, K>> zipWithConstantR(
        final Iterable<? extends V> rest,
        final K constant) {
        final List<Pair<V, K>> res = new ArrayList<Pair<V, K>>();
        for (final V v : rest) {
            res.add(new Pair<V, K>(v, constant));
        }
        return res;
    }

    public static <T> boolean any(
        final Filter<? super T> filter,
        final Iterable<T> ts) {
        for (final T t : ts) {
            if (filter.fits(t)) {
                return true;
            }
        }
        return false;
    }

    public static <K> boolean intersects(
        final Collection<K> a,
        final Collection<K> b) {
        return a.size() < b.size() ? any(memberOf(b), a) : any(memberOf(a), b);
    }

    public static <T> boolean all(
        final Filter<? super T> filter,
        final Collection<T> ts) {
        return !any(not(filter), ts);
    }


//    public static <K, V> Map<K, V> zipMap(final Pair<K, V>... in) {
//        return zipMap(Arrays.asList(in));
//    }
//
//    public static <K, V> Map<K, V> zipMap(final Iterable<Pair<K, V>> in, final Builder<Map<K, V>> builder) {
//        final Map<K, V> out = builder.build();
//        for (final Pair<? extends K, ? extends V> pair : in) {
//            out.put(pair.first, pair.second);
//        }
//        return out;
//    }

    /**
     * Builds map from given iterable of pairs.
     * <p/>
     * If .first of pairs are not unique, already place value will be
     * overwritten
     */
    public static <K, V> Map<K, V> zipMap(final Iterable<Pair<K, V>> in) {
        final Map<K, V> out;
        if (in instanceof Collection) {
            out = newUnorderedMap(((Collection<?>) in).size());
        } else {
            out = newUnorderedMap();
        }
        for (final Pair<? extends K, ? extends V> pair : in) {
            out.put(pair.first, pair.second);
        }
        return out;
    }

    /**
     * Zip iterables until one exhausts and interpret the result as key/value
     * pairs.
     */
    public static <K, V> Map<K, V> zipMap(final Iterable<? extends K> ks,
                                          final Iterable<? extends V> vs) {
        final Map<K, V> map = newLinkedMap();
        final Iterator<? extends K> kit = ks.iterator();
        final Iterator<? extends V> vit = vs.iterator();
        while (kit.hasNext() && vit.hasNext()) {
            map.put(kit.next(), vit.next());
        }
        return map;
    }

    /**
     * Splits given list into chunks of given size (last chunk may be smaller).
     * Returned lists are sub-lists of the original one, so all rules of {@link
     * List#subList(int, int)} are applicable to them
     *
     * @param xs original list
     */
    public static <T> List<List<T>> split(final List<T> xs,
                                          final int chunkSize) {
        final int n = xs.size();
        final int chunkCount = n / chunkSize + ((n % chunkSize == 0) ? 0 : 1);
        final List<List<T>> chunks = newList(chunkCount);
        for (int i = 0; i < chunkCount; i++) {
            chunks.add(
                xs.subList(
                    i * chunkSize, Math.min((i + 1) * chunkSize, n)));
        }

        return chunks;
    }

    /**
     * Splits given collection in two parts using result of given filter
     * application as split criteria. If filter yields true, element is added to
     * the first list, if false - to the second.
     *
     * @return Pair of lists: first - of fitting elements of original list,
     *         second - of non-fitting.
     */
    public static <T> Pair<List<T>, List<T>> split(final Collection<T> xs,
                                                   final Filter<? super T> filter) {
        final List<T> fits = CollectionFactory.newList(xs.size());
        final List<T> unfits = CollectionFactory.newList(xs.size());
        for (final T x : xs) {
            if (filter.fits(x)) {
                fits.add(x);
            } else {
                unfits.add(x);
            }
        }
        return Pair.of(fits, unfits);
    }

    /**
     * Returns a reversed variant of the given list without modifying it.
     */
    public static <T> List<T> reversed(final List<T> list) {
        final List<T> reversed = newArrayList(list);
        Collections.reverse(reversed);
        return reversed;
    }

    public static <T> boolean isEmpty(final Collection<T> c) {
        return c == null || c.isEmpty();
    }

    public static <T> boolean isNonEmpty(final Collection<T> c) {
        return c != null && !c.isEmpty();
    }

    public static <K, V> boolean isEmpty(final Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> boolean isNonEmpty(final Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    public static <T> int size(final Collection<T> c) {
        return c == null ? 0 : c.size();
    }

    /**
     * @param c collection
     * @return An unmodifiable not null collection (empty in case of
     *         <code>c</code> is null).
     */
    @NotNull
    public static <T> Collection<T> emptyIfNull(
        @Nullable
        final Collection<T> c) {
        return c == null ? Collections.<T>emptyList() : c;
    }

    /**
     * @param s set
     * @return An unmodifiable not null set (empty in case of <code>s</code> is
     *         null).
     */
    @NotNull
    public static <T> Set<T> emptyIfNull(
        @Nullable
        final Set<T> s) {
        return s == null ? Collections.<T>emptySet() : s;
    }

    /**
     * @param l list
     * @return An unmodifiable not null list (empty in case of <code>l</code> is
     *         null).
     */
    @NotNull
    public static <T> List<T> emptyIfNull(
        @Nullable
        final List<T> l) {
        return l == null ? Collections.<T>emptyList() : l;
    }

    /**
     * @param m map
     * @return An unmodifiable not null map (empty in case of <code>m</code> is
     *         null).
     */
    @NotNull
    public static <K, V> Map<K, V> emptyIfNull(
        @Nullable
        final Map<K, V> m) {
        return m == null ? Collections.<K, V>emptyMap() : m;
    }

//    /**
//     * Returns the index of the first occurrence of the specified element in
//     * this list, starting at the specified index, or -1 if nothing found. More
//     * formally, returns the lowest index <tt>i</tt> (<tt>i>=fromIndex</tt>)
//     * such that <tt>(t==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;t.equals(get(i)))</tt>,
//     * or -1 if there is no such index.
//     *
//     * @param list      list in which to search for the specified element
//     * @param t         element to search for
//     * @param fromIndex starting search index
//     * @return the index of the first occurrence of the specified element in
//     *         this list, starting at the specified index, or -1 if nothing
//     *         found.
//     */
//    public static <T> int indexOf(
//        final List<T> list,
//        final T t,
//        int fromIndex) {
//        fromIndex = minmax(0, list.size(), fromIndex);
//        final int offset = list.subList(fromIndex, list.size()).indexOf(t);
//        return offset < 0 ? -1 : offset + fromIndex;
//    }

//     /**
//     * The Iterable constructs an Iterator, which starts execution of function
//     * in multiple threads. The call to next() returns one of available results
//     * (order is not guaranteed). The Pair returned by the Iterator contains
//     * (argument, result) of the Function call.
//     * If the Function.applly() throws a RuntimeException or Error, it is propagated to the result
//     * and Pair.getSecond() will throw this exception. Pair.getFirst() returne the argument, which caused the exceprion.
//     * If keys.next() throws a RuntimeException or Error, pair.getFirst() will return null, Pair,.getSecond will throw the exception
//     * (this is very unlikely case).
//     *
//     * @param keys       the source Iterable
//     * @param function   will be applied to keys to produce results
//     * @param maxThreads maximum threads to use for parallel process. It is also
//     *                   the maximum size of results queue, so it limits memory
//     *                   consumption as well.
//     * @param <Key>      argument type
//     * @param <Value>    result type
//     */
//    public static <Key, Value> Iterable<Pair<Key, Value>> mapAsync(
//        final Iterable<Key> keys,
//        final Function<Key, Value> function,
//        final int maxThreads) {
//        return new Iterable<Pair<Key, Value>>() {
//            @Override
//            public Iterator<Pair<Key, Value>> iterator() {
//                return new AsyncIterator<Key, Value>(new AsyncIteratorWorker<Key, Value>(
//                    keys.iterator(), function, maxThreads));
//            }
//        };
//    }
//
//    /**
//     * We need a delegation for garbage collection. If we held a direct reference to AsyncIteratorWorker from
//     * the caller of mapAsync(), it would be never garbage collected (being running thread!).
//     * AsyncIterator is subject to garbage collection and kills the worker, thus allowing collection of the worker
//     *
//     * @param <Key>
//     * @param <Value>
//     */
//    private static class AsyncIterator<Key, Value> implements Iterator<Pair<Key, Value>> {
//        private final AsyncIteratorWorker<Key, Value> worker;
//
//        private AsyncIterator(AsyncIteratorWorker<Key, Value> worker) {
//            this.worker = worker;
//        }
//
//        @Override
//        public boolean hasNext() {
//            return worker.hasNext();
//        }
//
//        @Override
//        public Pair<Key, Value> next() {
//            return worker.next();
//        }
//
//        @Override
//        public void remove() {
//            worker.remove();
//        }
//
//        @Override
//        protected void finalize() throws Throwable {
//            if (worker.isAlive()) {
//                Logger.getLogger(getClass()).info("Interrupting worker");
//                worker.interrupt();
//            }
//        }
//    }
//
//    private static class AsyncIteratorWorker<Key, Value> extends Thread
//        implements Iterator<Pair<Key, Value>> {
//        private static AtomicLong instanceCount = new AtomicLong();
//
//        private final Iterator<Key> keys;
//        private final Function<Key, Value> function;
//        private final Semaphore semaphore;
//        // executor service and result queue are of unlimited size; size is actuallyt limited by semaphore
//        private final ExecutorService executorService;
//        private final BlockingQueue<AsyncResult<Key, Value>> resultQueue;
//
//        private boolean running;
//        private AtomicInteger expectedResults;
//        private Either<? extends Error, ? extends RuntimeException>
//            failureReason = null;
//
//        public AsyncIteratorWorker(final Iterator<Key> keys,
//                                   final Function<Key, Value> function,
//                                   final int maxThreads
//        ) {
//            super(Thread.currentThread().getName() + "_" + "AsyncIteratorWorker_" + instanceCount.incrementAndGet());
//            this.keys = keys;
//            this.function = function;
//            this.semaphore = new Semaphore(maxThreads);
//            this.resultQueue =
//                new LinkedBlockingQueue<AsyncResult<Key, Value>>();
//            executorService = Executors.newCachedThreadPool(
//                ThreadFactories.custom(
//                    false, Thread.currentThread().getName() + "_" + "AsyncIteratorTask_" + instanceCount.get() + "_",
//                    new Log4jUncaughtExceptionHandler(
//                        Logger.getLogger(getClass()), Level.ERROR)));
//            this.expectedResults = new AtomicInteger(-1);
//            updateStatus();
//            start();
//        }
//
//        public synchronized boolean hasNext() {
//            if (failureReason != null) {
//                throw new IllegalStateException(
//                    failureReason.isLeftNotRight() ? failureReason.asLeft() :
//                        failureReason.asRight());
//            }
//            return (expectedResults.get() > 0 || running);
//        }
//
//        public Pair<Key, Value> next() {
//            Logger.getLogger(getClass()).debug(
//                "5:$ " + getName() + " semaphore.availablePermits()" +
//                    semaphore.availablePermits() + " expected: " +
//                    expectedResults.get());
//            if (!hasNext()) {
//                throw new NoSuchElementException();
//            }
//            try {
//                final AsyncResult<Key, Value> result;
//                result = resultQueue.take();
//                expectedResults.addAndGet(-1);
//                semaphore.release();
//                Logger.getLogger(getClass()).debug(
//                    "6:$ " + getName() + " semaphore.availablePermits()" +
//                        semaphore.availablePermits() + " expected: " +
//                        expectedResults.get());
//                return result;
//            } catch (final InterruptedException e) {
//                // never expected
//                throw new RuntimeException(e);
//            }
//        }
//
//        @Override
//        public void remove() {
//            throw new RuntimeException("Not implemented");
//        }
//
//        @Override
//        public void run() {
//            // expected results is the number of tasks submitted to executorService
//            // running is true if source iterator hasNext; in this case at least one more task will be definitely submitted
//            while (running) {
//                try {
//                    Logger.getLogger(getClass()).debug(
//                        "1:$ " + getName() + " semaphore.availablePermits()" +
//                            semaphore.availablePermits() + " expected results: " +
//                            expectedResults.get());
//                    semaphore.acquire();
//                    submitTask();
//                } catch (InterruptedException e) {
//                    // stop execution abruptly
//                    Logger.getLogger(getClass())
//                        .warn(getName() + " interrupted; exiting");
//                    die();
//                    return;
//                }
//            }
//            executorService.shutdown();
//            // do not wait for its completion; just exit
//        }
//
//        private void die() {
//            Logger.getLogger(getClass()).info("I am dying");
//            executorService.shutdownNow();
//            running = false;
//            expectedResults.set(0);
//        }
//
//        private void submitTask() throws InterruptedException {
//            // hasNext() is true when running
//            final Key key;
//            try {
//                key = keys.next();
//            } catch (RuntimeException e) {
//                final AsyncResult<Key, Value> result = new AsyncResult<Key, Value>(null, e);
//                synchronized (this) {
//                    resultQueue.put(result);
//                    updateStatus();
//                    Logger.getLogger(getClass()).debug(
//                        "2:$ " + getName() +
//                            " semaphore.availablePermits()" +
//                            semaphore.availablePermits() + " expected results: " +
//                            expectedResults.get());
//                    return;
//                }
//            }
//            final Function<Key, Value> localFunction = function;
//            final BlockingQueue<AsyncResult<Key, Value>> localQueue =
//                resultQueue;
//            // keep invariant in synchronized block
//            synchronized (this) {
//                executorService.submit(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                AsyncResult<Key, Value> result;
//                                try {
//                                    result =
//                                        new AsyncResult<Key, Value>(
//                                            key,
//                                            localFunction.apply(key));
//                                } catch (RuntimeException e) {
//                                    result =
//                                        new AsyncResult<Key, Value>(key, e);
//                                }
//                                localQueue.put(result);
//                                Logger.getLogger(getClass()).debug(
//                                    "3:$ " +
//                                        AsyncIteratorWorker.this.getName() +
//                                        " semaphore.availablePermits()" +
//                                        semaphore.availablePermits() +
//                                        " expected results: " +
//                                        expectedResults.get());
//                            } catch (InterruptedException e) {
//                                // only can happen when executor is shutdownNow()
//                                // fatal - exit immediately
//                                Logger.getLogger(getClass()).info(
//                                    "Interrupted - shutting down");
//                                Thread.currentThread().interrupt();
//                            }
//                        }
//                    });
//                // correct running status and expected results after submit
//                updateStatus();
//                Logger.getLogger(getClass()).debug(
//                    "3a:$ " + getName() +
//                        " semaphore.availablePermits()" +
//                        semaphore.availablePermits() + " expected results: " +
//                        expectedResults.get());
//            }
//        }
//
//        private void updateStatus() {
//            expectedResults.incrementAndGet();
//            try {
//                running = keys.hasNext();
//            } catch (RuntimeException e) {
//                running = false;
//                failureReason = Either.right(e);
//            } catch (Error e) {
//                running = false;
//                failureReason = Either.left(e);
//            }
//        }
//
//        @Override
//        protected void finalize() throws Throwable {
//            this.interrupt();
//        }
//    }


    public static <K, V, M extends Map<K, V>> M put(
        final M in,
        final K k,
        final V v) {
        in.put(k, v);
        return in;
    }

//    public static class AsyncResult<Arg, Value> extends Pair<Arg, Value> {
//        private final Either<Error, RuntimeException> throwable;
//
//        /**
//         * use this constructor to return a result associated with the argument
//         *
//         * @param arg   argument
//         * @param value result
//         */
//        public AsyncResult(final Arg arg, final Value value) {
//            super(arg, value);
//            this.throwable = null;
//        }
//
//        public AsyncResult(final Arg arg, final RuntimeException exception) {
//            super(arg, null);
//            this.throwable = Either.right(exception);
//
//        }
//
//        public AsyncResult(final Arg arg, final Error error) {
//            super(arg, null);
//            this.throwable = Either.left(error);
//
//        }
//
//        @Override
//        public Value getSecond() {
//            if (throwable == null) {
//                return super.getSecond();
//            } else {
//                if (throwable.isLeftNotRight()) {
//                    throw throwable.asLeft();
//                } else {
//                    throw throwable.asRight();
//                }
//            }
//        }
//    }
//
//    /**
//     * Return unmodifiable trimmed Map version of given map.
//     */
//    public <K, V> Map<K, V> trim(final Map<K, V> in) {
//        if (in.size() == 1) {
//            final Map.Entry<K, V> only = in.entrySet().iterator().next();
//            final K key = only.getKey();
//            final V value = only.getValue();
//            return new SingletonMap<K, V>(key, value);
//        }
//        final Map<K, V> out = new HashMap<K, V>(in.size(), 1.0f);
//        out.putAll(in);
//
//        return out;
//    }

    /**
     * Return unmodifiable trimmed List version of given List.
     */
    public <T> List<T> trim(final List<T> in) {
        if (in.size() == 1) {
            return Collections.singletonList(in.get(0));
        }
        return Collections.unmodifiableList(newArrayList(in));
    }

    /**
     * Return single element from result if there is only one element in the
     * result. Return null if result is null or empty. Throws
     * IllegalArgumentException if result contains more than one element.
     */
    public static <T> T expectedSingleResult(final
                                             @Nullable
                                             List<T> result) {
        if (result == null || result.size() == 0) {
            return null;
        }
        if (result.size() > 1) {
            throw new IllegalArgumentException(
                "There are more than one element in result");
        }
        return result.get(0);
    }

    /**
     * Converts a String separated by commas, dots, spaces or 
     * semicolons to the HashSet of strings
     */
    public static HashSet<String> stringToSet(String s) {
    	String[] strArr = StringUtils.split(s, " ,.;");
    	HashSet<String> set = new HashSet<String>();
		for(String tag: strArr) {
			set.add(tag);
		}
		return set;
    }
    
    /**
     * Converts a HashSet of Strings to string separated by commas
     */
    public static String setToString(Set<String> s) {
    	String str = s.toString();
    	return str.substring(1, str.length()-1);
    }
    
    public static boolean isEquals(Collection<?> first, Collection<?> second){
    	
    	if (isNonEmpty(first)) {
			if (isEmpty(second)) {
				return false;
			}
			
	    	for(Object f : first){
	    		boolean tmpFlag = false;
	    		for(Object s: second){
	    			if (f.equals(s)) {
	    				tmpFlag = true;
	    				break;
	    			}
	    		}
	    		if(!tmpFlag){
	    			return false;
	    		}
	    	}
	    	
	    	for(Object s : second){
	    		boolean tmpFlag = false;
	    		for(Object f: first){
	    			if (f.equals(s)) {
	    				tmpFlag = true;
	    				break;
	    			}
	    		}
	    		if(!tmpFlag){
	    			return false;
	    		}
	    	}
	    	return true;
		} else if (isNonEmpty(second)) {
			return false;
		} else{
			return true;
		}
    	
    }
}
