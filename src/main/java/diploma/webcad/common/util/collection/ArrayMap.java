package diploma.webcad.common.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of <code>OrderedMap</code> interface, based on <code>ArrayList</code>.
 * 
 * @author yarik.pro
 *
 * @param <K> - generic type of keys
 * @param <V> - generic type of values
 * @see {@link ArrayList}
 * @see {@link OrderedMap}
 */
public class ArrayMap<K,V> implements  OrderedMap<K,V>{
	
	private ArrayList<Entry<K,V>> body;
	
	@Override
	public int size() {
		return body.size();
	}
	
	@Override
	public boolean isEmpty(){
		return body.size() == 0;
	}
	
	@Override
	public boolean containsKey(Object key){
		return getEntry(key) != null;
	}
	
	private Entry<K,V> getEntry(Object key) {
		if (key==null)
			return null;
		for(Entry<K,V> e : body) {
			if (key.equals(e.key))
				return e;
		}
		return null;
	}
	
	@Override
	public boolean containsValue(Object value) {
		if (value==null)
			return false;
		for(Entry<K,V> e : body) {
			if (value.equals(e.value))
				return true;
		}
		return false;		
	}
	
	private static class Entry<K,V> implements Map.Entry<K, V>{
		
		protected final K key;
		
		protected V value;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V val) {
			value = val;
			return value;
		}
		
	}
	
	public V put(K key, V value) {
		if (key == null || containsKey(key))
			return null;
		body.add(new Entry<K,V>(key,value));
		return value;
	}

	@Override
	public V get(Object key) {
		Entry<K,V> e = getEntry(key);
		if (e!=null)
			return e.value;
		return null;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet(){
		return new HashSet<Map.Entry<K, V>>(body);
	}

	@Override
	public V remove(Object key){
		for(int i = 0; i < body.size(); i++){
			if (body.get(i).key.equals(key)) {
				V value = body.get(i).value;
				body.remove(i);
				return value;
			}
		}
		return null;
		
	}

	@Override
	public int indexOfKey(K key) {
		for(int i = 0; i < body.size(); i++){
			if (body.get(i).key.equals(key))
				return i;
		}
		return -1;
	}

	@Override
	public void clear(){
		body.clear();
	}

	@Override
	public int indexOfValue(V value){
		for(int i = 0; i < body.size(); i++){
			if (body.get(i).value.equals(value))
				return i;
		}
		return -1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map.Entry<K,V>> asEntryList(){
		return (List<Map.Entry<K,V>>) body.clone();
	}

	@Override
	public void remove(int index) {
		body.remove(index);
	}

	@Override
	public V getValue(int index) {
		return body.get(index).value;
	}

	@Override
	public Map.Entry<K, V> getEntry(int index) {
		return body.get(index);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void putAll(Map<? extends K, ? extends V> m) {
		for(Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
			body.add((Entry<K,V>)e);
		}
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<K>(body.size());
		for(Entry<K,V> e : body) {
			set.add(e.key);
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		return asList();
	}

	@Override
	public List<V> asList() {
		List<V> list = new ArrayList<V>(body.size());
		for(Entry<K,V> e : body) {
			list.add(e.value);
		}
		return list;
	}

	@Override
	public K getKey(int index) {
		return body.get(index).key;
	}

	@Override
	public void set(int index, V value) {
		body.get(index).value = value;
	}
	
}
