package diploma.webcad.common.util.collection;

import java.util.List;
import java.util.Map;

/**
 * <p>An object that maps keys to values. This interface extends 
 * the <code>Map</code> interface and provides all the facilities 
 * of it. Also guarantees that the elements will be ordered 
 * and provides positional access. In fact, combines features 
 * of the <code>Map</code> and the <code>List</code> interfaces 
 * with some restrictions.</p>
 * 
 * <p>In order to enforce uniqueness of keys this interface has no
 * method for setting entry in some position. There is only a 
 * method to set the value to the entry that specified by index.</p>
 * 
 * @author yarik.pro
 *
 * @param <K> - generic type of keys
 * @param <V> - generic type of values
 * @see {@link List}
 * @see {@link Map}
 */
public interface OrderedMap<K,V> extends Map<K, V> {
		
	//Views
	
	/**
	 * Returns a list of values.
	 */
	List<V> asList();
	
	/**
	 * Returns a list of key-value pairs.
	 */
	List<Entry<K,V>> asEntryList();
	
	//Positional access operations
	
	/**
	 * Get the value in the entry with index <code>index</code>.
	 * 
	 * @param index of entry
	 * @return value of entry
	 */
	V getValue(int index);
	
	/**
	 * Get the key in the entry with index <code>index</code>.
	 * 
	 * @param index of entry
	 * @return key of entry
	 */
	K getKey(int index);
	
	Entry<K,V> getEntry(int index);
	
	/**
	 * Sets the <code>value</code> to the entry that specified
	 * by <code>index</code>
	 * 
	 * @param index of entry
	 * @param setted value
	 */
	void set(int index, V value);
	
	/**
	 * Removes entry that specified by index
	 * 
	 * @param index of entry to be removed
	 */
	void remove(int index);
	
	//Search operations
	
	/**
	 * Returns the index of entry that specified by key
	 * @param key for searching the entry
	 * @return index of found entry
	 */
	int indexOfKey(K key);
	
	/**
	 * Returns the index of first entry thats value are equal
	 * to the value in parameter.
	 * 
	 * @param key for searching the entry
	 * @return index of found entry
	 */
	int indexOfValue(V value);
	
}
