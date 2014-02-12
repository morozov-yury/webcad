package diploma.webcad.common.util.collection;

//import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
//import com.comunicator.common.util.collections.nullable.NotNullList;
//import com.comunicator.common.util.collections.nullable.NotNullListAdapter;
//import com.comunicator.common.util.collections.nullable.NullableList;
//import com.comunicator.common.util.collections.nullable.NullableListAdapter;
//import com.comunicator.common.util.collections.rel.BiRelation;
//import com.comunicator.common.util.collections.rel.TriRelation;
//import com.comunicator.common.util.functional.Monoid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import diploma.webcad.core.annotation.NotNull;


public class CollectionFactory {
    /**
     * Returns a *modifiable* list containing the requested
     * values. The list is completely independent from the
     * array, t.i. their changes are NOT mutually reflected
     * (contrary to Arrays.asList()).
     */
    @NotNull
    public static <T> List<T> list(T... values) {
        List<T> res = newArrayList(values.length);
        for (T t : values) res.add(t);
        return res;
    }

    public static <T> List<T> list(T a) {
        List<T> ts = newList(1);
        ts.add(a);
        return ts;
    }

    public static <T> List<T> list(T a, T b) {
        List<T> ts = newList(2);
        ts.add(a);
        ts.add(b);
        return ts;
    }

    public static <T> List<T> list(T a, T b, T c) {
        List<T> ts = newList(3);
        ts.add(a);
        ts.add(b);
        ts.add(c);
        return ts;
    }

    public static <T> List<T> selfOrEmptyList(List<T> list) {
        return (list == null) ? new ArrayList<T>() : list;
    }

    /**
     * Returns an *unmodifiable* list containing the requested
     * values. The list is completely independent from the
     * array, t.i. their changes are NOT mutually reflected
     * (contrary to Arrays.asList()).
     */
    @NotNull
    public static <T> List<T> unmodifiableList(T... values) {
        return Collections.unmodifiableList(list(values));
    }

    @NotNull
    public static <K> List<K> list(Iterable<? extends K> items) {
        List<K> res = newArrayList();
        for (K item : items) {
            res.add(item);
        }
        return res;
    }

    @NotNull
    public static <T> ArrayList<T> newList(int initialCapacity) {
        return new ArrayList<T>(initialCapacity);
    }

    @NotNull
    public static <T> ArrayList<T> newList() {
        return new ArrayList<T>();
    }

    @NotNull
    public static <T> ArrayList<T> newArrayList() {
        return newList();
    }

    @NotNull
    public static <T> ArrayList<T> newArrayList(int initialCapacity) {
        return new ArrayList<T>(initialCapacity);
    }

    @NotNull
    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<T>();
    }

    @NotNull
    public static <T> LinkedList<T> newLinkedList(final T[] ts) {
        return new LinkedList<T>(Arrays.asList(ts));
    }

    @NotNull
    public static <T> LinkedList<T> newLinkedList(final Collection<? extends T> ts) {
        return new LinkedList<T>(ts);
    }


    @NotNull
    public static <T> ArrayList<T> newList(Collection<? extends T> contents) {
        return new ArrayList<T>(contents);
    }

    @NotNull
    public static <T> ArrayList<T> newList(Iterable<? extends T> contents) {
        ArrayList<T> res = newList();
        for (T t : contents) res.add(t);
        return res;
    }

    @NotNull
    public static <T> LinkedList<T> newLinkedList(Iterable<? extends T> contents) {
        LinkedList<T> res = newLinkedList();
        for (T t : contents) res.add(t);
        return res;
    }

    @NotNull
    public static <T> ArrayList<T> newList(T[] ts) {
        ArrayList<T> res = newList();
        res.addAll(Arrays.asList(ts));
        return res;
    }

    @NotNull
    public static <T> ArrayList<T> newArrayList(Collection<? extends T> contents) {
        return newList(contents);
    }

    @NotNull
    public static <T> ArrayList<T> newArrayList(T[] ts) {
        return newList(ts);
    }

    /**
     * The returned set is modifiable.
     */
    @NotNull
    public static <K> Set<K> set(K... items) {
        return new LinkedHashSet<K>(Arrays.asList(items));
    }

    /**
     * The returned set is modifiable.
     */
    @NotNull
    public static <K> Set<K> set(Iterable<? extends K> iterable) {
        final Set<K> set = CollectionFactory.newUnorderedSet();
        for (final K k : iterable) {
            set.add(k);
        }
        return set;
    }

    @NotNull
    public static <T> HashSet<T> newHashSet() {
        return new HashSet<T>();
    }

    @NotNull
    public static <T> HashSet<T> newHashSet(int initialCapacity) {
        return new HashSet<T>(initialCapacity);
    }

    @NotNull
    public static <T> HashSet<T> newHashSet(Collection<T> c) {
        return new HashSet<T>(c);
    }

    @NotNull
    public static <T> HashSet<T> newUnorderedSet() {
        return new HashSet<T>();
    }

    @NotNull
    public static <T> HashSet<T> newUnorderedSet(int initialCapacity) {
        return new HashSet<T>(initialCapacity);
    }

    @NotNull
    public static <T> HashSet<T> newUnorderedSet(Collection<? extends T> contents) {
        return new HashSet<T>(contents);
    }

    @NotNull
    public static <T> HashSet<T> newUnorderedSet(Iterable<? extends T> ts) {
        HashSet<T> s = newUnorderedSet();
        for (T t : ts) s.add(t);
        return s;
    }

    @NotNull
    public static <T> LinkedHashSet<T> newLinkedSet() {
        return new LinkedHashSet<T>();
    }

    @NotNull
    public static <T> LinkedHashSet<T> newLinkedSet(Collection<? extends T> contents) {
        return new LinkedHashSet<T>(contents);
    }

    @NotNull
    public static <T> LinkedHashSet<T> newLinkedSet(int initialCapacity) {
        return new LinkedHashSet<T>(initialCapacity);
    }

    @NotNull
    public static <T> TreeSet<T> newTreeSet() {
        return new TreeSet<T>();
    }

    @NotNull
    public static <T> TreeSet<T> newTreeSet(Comparator<? super T> comparator) {
        return new TreeSet<T>(comparator);
    }

    @NotNull
    public static <T> TreeSet<T> newTreeSet(Collection<? extends T> contents) {
        return new TreeSet<T>(contents);
    }

    @NotNull
    public static <K, V> HashMap<K, V> newUnorderedMap(int initialCapacity) {
        return new HashMap<K, V>(initialCapacity);
    }

    @NotNull
    public static <K, V> HashMap<K, V> newUnorderedMap() {
        return new HashMap<K, V>();
    }

//    @NotNull
//    public static <K, V> SoftHashMap<K, V> newSoftMap() {
//        return new SoftHashMap<K, V>();
//    }

    @NotNull
    public static <K, V> HashMap<K, V> newUnorderedMap(Map<? extends K, ? extends V> contents) {
        return new HashMap<K, V>(contents);
    }

    @NotNull
    public static <K, V> LinkedHashMap<K, V> newLinkedMap() {
        return new LinkedHashMap<K, V>();
    }

    @NotNull
    public static <K, V> LinkedHashMap<K, V> newLinkedMap(Map<? extends K, ? extends V> contents) {
        return new LinkedHashMap<K, V>(contents);
    }

    @NotNull
    public static <K, V> LinkedHashMap<K, V> newLinkedMap(int initialCapacity) {
        return new LinkedHashMap<K, V>(initialCapacity);
    }


    @NotNull
    public static <K, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<K, V>();
    }

    @NotNull
    public static <K, V> TreeMap<K, V> newTreeMap(Map<? extends K, ? extends V> contents) {
        return new TreeMap<K, V>(contents);
    }

    @NotNull
    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<K> comparator) {
        return new TreeMap<K, V>(comparator);
    }

    @NotNull
    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<K, V>();
    }

//    public static <K, V> MonoidMap<K, V> newMonoidMap(Monoid<V> monoid) {
//        return new MonoidMap<K, V>(monoid);
//    }
//
//    @NotNull
//    public static <L, R> BiRelation<L, R> emptyRelation() {
//        return new BiRelation<L, R>();
//    }
//
//    @NotNull
//    public static <L, R> BiRelation<L, R> newRelation(List<Pair<L, R>> pairs) {
//        return new BiRelation<L, R>(pairs);
//    }
//
//    @NotNull
//    public static <A, B, C> TriRelation<A, B, C> newTriRelation(List<Triple<A, B, C>> triples) {
//        return new TriRelation<A, B, C>(triples);
//    }
//
//    @NotNull
//    public static <L, R> BiRelation<L, R> asRelation(Map<L, R> map) {
//        List<Pair<L, R>> pairs = newList();
//        for (Map.Entry<L, R> entry : map.entrySet()) {
//            pairs.add(new Pair<L, R>(entry.getKey(), entry.getValue()));
//        }
//        return newRelation(pairs);
//    }

    @NotNull
    public static <T> T[] array(T... ts) {
        return ts;
    }


//    /**
//     * @deprecated Use {@link #newMultiMap}.
//     */
//    @Deprecated
//    public static <K, V> MultiMap<K, V> newMultimap() {
//        return newMultiMap();
//    }
//
//    @NotNull
//    public static <K, V> MultiMap<K, V> newMultiMap() {
//        return new MultiMap<K, V>();
//    }
//
//    @NotNull
//    public static <K, V> MultiMap<K, V> newMultiMap(Map<K, List<V>> backingMap) {
//        return new MultiMap<K, V>(backingMap);
//    }
//
//    @NotNull
//    public static <K, V> MultiMap<K, V> newMultiMap(Map<K, List<V>> backingMap, int initialBucketCapacity) {
//        return new MultiMap<K, V>(backingMap, initialBucketCapacity);
//    }
//
//    @NotNull
//    public static <K, V> MultiMap<K, V> newMultiMap(@NotNull final MultiMap<? extends K, ? extends V> multiMap) {
//        return new MultiMap<K, V>(multiMap);
//    }
//
//    @NotNull
//    public static <K, V> MultiSet<K, V> newMultiSet() {
//        return new MultiSet<K, V>();
//    }

    @NotNull
    public static <A, B> Pair<A, B> pair(A a, B b) {
        return new Pair<A, B>(a, b);
    }

//    @Deprecated
//    @NotNull
//    public static <A, B, C> Triple<A, B, C> triple(A a, B b, C c) {
//        return new Triple<A, B, C>(a, b, c);
//    }
//
//    @NotNull
//    public static <K> Bag<K> newBag() {
//        return Bag.newHashBag();
//    }
//
//    @NotNull
//    public static <K> Bag<K> newBag(Collection<K> ks) {
//        Bag<K> res = Bag.newHashBag();
//        res.addAll(ks);
//        return res;
//    }
//
//    @NotNull
//    public static <K> Bag<K> newBag(Bag<K> ks) {
//        Bag<K> res = Bag.newHashBag();
//        res.addAll(ks);
//        return res;
//    }
//
//    @NotNull
//    public static <K> PriorityBag<K> newPriorityBag() {
//        return PriorityBag.newInsertionOrderedPriorityBag();
//    }
//
//    @NotNull
//    public static <K> PriorityBag<K> newPriorityBag(int initialCapacity) {
//        return PriorityBag.newInsertionOrderedPriorityBag(initialCapacity);
//    }

    @NotNull
    public static <K, V> Map<K, V> newConstMap(Collection<? extends K> keys, V value) {
        Map<K, V> res = newLinkedMap();
        for (K key : keys) res.put(key, value);
        return res;
    }

//    @NotNull
//    public static <K, V> BidiMap<K, V> newBidiMap() {
//        return new BidiMap<K, V>();
//    }
//
//    @NotNull
//    public static <K, V> MultiMap<K, V> newUnorderedMultiMap() {
//        return new MultiMap<K, V>(false);
//    }
//
//    @NotNull
//    public static <K, V> MultiMap<K, V> newLinkedMultimap() {
//        return new MultiMap<K, V>();
//    }
//
//    @NotNull
//    public static <T> NullableList<T> newNullableList() {
//        return new NullableList<T>();
//    }
//
//    @NotNull
//    public static <T> NullableList<T> newNullableList(T[] ts) {
//        return new NullableListAdapter<T>(newList(ts));
//    }
//
//    @NotNull
//    public static <T> NullableList<T> newNullableList(Collection<? extends T> ts) {
//        return new NullableListAdapter<T>(newList(ts));
//    }
//
//    @NotNull
//    public static <T> NotNullList<T> newNotNullList() {
//        return new NotNullList<T>();
//    }
//
//    @NotNull
//    public static <T> NotNullList<T> newNotNullList(T[] ts) {
//        return new NotNullListAdapter<T>(newList(ts));
//    }
//
//    @NotNull
//    public static <T> NotNullList<T> newNotNullList(Collection<? extends T> ts) {
//        return new NotNullListAdapter<T>(newList(ts));
//    }
//
//    @NotNull
//    public static <K, V> BidiMap<K, V> asBimap(Map<K, V> map) {
//        return new BidiMap<K, V>(map);
//    }

    @NotNull
    public static <T> Collection<T> singleton(T t) {
        return Collections.singleton(t);
    }

    @NotNull
    public static <T> List<T> singletonOrEmptyList(T t) {
        return t == null ? Collections.<T>emptyList() : Collections.singletonList(t);
    }

    @NotNull
    public static <K extends Enum<K>, V> Map<K, V> newEnumMap(Class<K> enumClass) {
        return new EnumMap<K, V>(enumClass);
    }

    @NotNull
    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> m) {
        return new HashMap<K, V>(m);
    }

    @NotNull
    public static <K, V> HashMap<K, V> newHashMap(int initialCapacity) {
        return new HashMap<K, V>(initialCapacity);
    }

    @NotNull
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    @NotNull
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

//    @NotNull
//    public static <K, V> Map<K, V> newConstMap(K[] keys, V[] values) {
//        if (keys.length != values.length) {
//            throw new IllegalStateException("Keys and values have different lengths (" + keys.length + ", " + values.length + ")");
//        }
//        if (keys.length == 0) return emptyMap();
//        if (keys.length == 1) return singletonMap(keys[0], values[0]);
//
//        Map<K, V> m = keys.length < 10 ? new Object2ObjectArrayMap<K, V>(keys, values) : new HashMap<K, V>(keys.length);
//        for (int i = 0; i < keys.length; i++) {
//            m.put(keys[i], values[i]);
//        }
//        return m;
//    }

    @NotNull
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<K, V>();
    }
}
