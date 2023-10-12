/**
 * 
 */
package H2Adapter;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Collection;
import H2Adapter.SetAdapter;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.*;

/**
 * Simple Generic Map interface adapter for J2ME Class Hashtable.
 * <p><tt>Since the adaptee is an Hashtable this Map does not allow null elements.</tt> 
 * <p>See also: {@link MapAdapter.Entry} 
 * @param <K><V> the type of key and values in this map
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings({"unchecked","rawtypes"}) //Mettevano a dura prova il mio OCD
public class MapAdapter<K,V> implements Map<K,V>{
	/**
	 * Hashtable adaptee.
	 */
	protected Hashtable ht; 
	
	/**
     * A map entry (key-value pair).  The <tt>Map.entrySet</tt> method returns
     * a collection-view of the map, whose elements are of this class.  The
     * <i>only</i> way to obtain a reference to a map entry is from the
     * iterator of this collection-view.  These <tt>Map.Entry</tt> objects are
     * valid <i>only</i> for the duration of the iteration; more formally,
     * the behavior of a map entry is undefined if the backing map has been
     * modified after the entry was returned by the iterator, except through
     * the <tt>setValue</tt> operation on the map entry.
     * <p>See also: {@link H2Adapter.MapAdapter}
     */
	protected class Entry implements Map.Entry<K,V>{
		/**
		 * key element of the entry
		 */
		protected final K key;
		/**
		 * value element of the entry
		 */
		protected V val;
		
		/**
		 * Constructs a new Entry for entrySet.
		 * @param k key reference
		 * @param v value reference
		 */
		public Entry(K k,V v) {
			key=k;
			val=v;
		}
		/**
         * Returns the key corresponding to this entry.
         * @return the key corresponding to this entry
         */
		@Override
		public K getKey() {
			return key;
		}
		/**
		 * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * <tt>remove</tt> operation), the results of this call are undefined.
         * @return the value corresponding to this entry
		 */
		@Override
		public V getValue() {
			return val;
		}
		/**
		 * Replaces the value corresponding to this entry with the specified value (Writes through to the map.)
		 * The behavior of this call is undefined if the mapping has already been
         * removed from the map (by the iterator's <tt>remove</tt> operation).
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         * @throws NullPointerException if the the specified value is null
         */
		@Override
		public V setValue(V value) {
			if(value==null) throw new NullPointerException();
			V temp = (V)(replace(key,value));
			return temp;
		}
		/**
         * Returns the hash code value for this map entry.  The hash code
         * of a map entry <tt>e</tt> is defined to be: <pre>
         *     (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *     (e.getValue()==null ? 0 : e.getValue().hashCode())
         * </pre>
         * This ensures that <tt>e1.equals(e2)</tt> implies that
         * <tt>e1.hashCode()==e2.hashCode()</tt> for any two Entries
         * <tt>e1</tt> and <tt>e2</tt>, as required by the general
         * contract of <tt>Object.hashCode</tt>.
         *
         * @return the hash code value for this map entry
         * @see #equals(Object)
         */
		@Override
		public int hashCode() {
			return (key==null   ? 0 : key.hashCode()) ^
		     (val==null ? 0 : val.hashCode());
		}
		/**
		 * Compares the specified object with this entry for equality.
         * Returns <tt>true</tt> if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries <tt>e1</tt> and <tt>e2</tt> represent the same mapping
         * if<pre>
         *     (e1.getKey()==null ?
         *      e2.getKey()==null : e1.getKey().equals(e2.getKey()))  &amp;&amp;
         *     (e1.getValue()==null ?
         *      e2.getValue()==null : e1.getValue().equals(e2.getValue()))
         * </pre>
         * This ensures that the <tt>equals</tt> method works properly across
         * different implementations of the <tt>Map.Entry</tt> interface.
         * @param o object to be compared for equality with this map entry
         * @return <tt>true</tt> if the specified object is equal to this map
         *         entry
         */
		@Override
		public boolean equals(Object o){
			if(this==o) return true;
			if(!(o instanceof Map.Entry)) return false;
			Map.Entry e = (Map.Entry)o;
			return (key==null ? e.getKey()==null : key.equals(e.getKey()))  &&
				   (val==null ? e.getValue()==null : val.equals(e.getValue()));
			}
	}
	/**
	 * Constructs a new MapAdapter that wraps given Hashtable.
	 */
	public MapAdapter() {
		ht = new Hashtable();
	}
	/**
	 * Constructs a new MapAdapter that wraps given Hashtable.
	 * @param hasht Hashtable instance adaptee
	 * @throws NullPointerException if specified Hashtable is null
	 */
	public MapAdapter(Hashtable h){
		if(h==null) throw new NullPointerException();
		ht=h;
	}
	/**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
	@Override
	public void clear() {
		ht.clear();
	}
	/**
	 * (NOT SUPPORTED) Attempts to compute a mapping for the specified key
	 * and its current mapped value (or null if there is no current mapping).
	 */
	@Override
	public V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction) {
		throw new UnsupportedOperationException();
	}
	/**
	 * (NOT SUPPORTED) If the specified key is not already associated 
	 * with a value (or is mapped to null), attempts to compute its value
	 * using the given mapping function and enters it into this map unless null
	 */
	@Override
	public V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction) {
		throw new UnsupportedOperationException();
	}
	/**
	 * (NOT SUPPORTED) If the value for the specified key is present and non-null, 
	 * attempts to compute a new mapping given the key and its current mapped value.
	 */
	@Override
	public V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction) {
		throw new UnsupportedOperationException();
	}
	/**
     * Returns <tt>true</tt> if this map contains a mapping for the specified key.
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified key
     * @throws NullPointerException if the specified key is null
     */
	@Override
	public boolean containsKey(Object key) {
		if(key==null) throw new NullPointerException();
		return ht.containsKey(key);
	}
	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the specified value.
	 * This operation will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     * @throws NullPointerException if the specified value is null
	 */
	@Override
	public boolean containsValue(Object value){
		if(value==null) throw new NullPointerException();
		return ht.contains(value);
	}
	/**
	 * Returns a {@link H2Adapter.SetAdapter} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     * @return a set view of the mappings contained in this map
	 */
	@Override
	public Set<Map.Entry<K,V>> entrySet(){
		return new SetAdapter<Map.Entry<K,V>>() {
			{
				ht=MapAdapter.this.ht;
			}
			@Override
			public boolean add(Map.Entry<K,V> e) {
				throw new UnsupportedOperationException();
			}
			@Override
			public boolean addAll(Collection<? extends java.util.Map.Entry<K,V>> c) {
				throw new UnsupportedOperationException();
			}
			@Override
			public boolean contains(Object o) {
				if(o==null) throw new NullPointerException();
				if(!(o instanceof Map.Entry)) return false;
				Map.Entry e = (Map.Entry)o;
				return ht.containsKey(e.getKey())&&ht.get(e.getKey()).equals(e.getValue());
			}
			@Override
			public Iterator<Map.Entry<K, V>> iterator() {
				return new SetAdapterIterator() {
					@Override
					public Map.Entry<K, V> next() {
						if(!hasNext()) throw new NoSuchElementException();
						called=true;
						temp = en.nextElement();
						index++;
						return new Entry((K)temp,(V)(ht.get(temp)));
					}
					@Override
					public void remove() {
						if(!called) throw new IllegalStateException();
						called=false;
						ht.remove(temp);
						index--;
						en=ht.keys();
						int i=0;
						while((i++)<index) en.nextElement();
					}
				};
			}
			@Override
			public Object[] toArray() {
				Object[] arr =new Object[size()];
				int i=0;
				for(Map.Entry<K,V> e : this) arr[i++]=e;
				return arr;
			}
			@Override
			public boolean remove(Object o) {
				if(o==null) throw new NullPointerException();
				if(!contains(o)) return false;
				Map.Entry<K,V> e = (Map.Entry<K,V>)o;
				ht.remove(e.getKey());
				return true;
			}
		};
	}
	/**
     * Compares the specified object with this map for equality.  Returns
     * <tt>true</tt> if the given object is also a map and the two maps
     * represent the same mappings.  More formally, two maps <tt>m1</tt> and
     * <tt>m2</tt> represent the same mappings if
     * <tt>m1.entrySet().equals(m2.entrySet())</tt>.  This ensures that the
     * <tt>equals</tt> method works properly across different implementations
     * of the <tt>Map</tt> interface.
     * @param o object to be compared for equality with this map
     * @return <tt>true</tt> if the specified object is equal to this map
     */
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof Map)) return false;
		Map m = (Map)o;
		if (m.size()!=size()) return false;
        try {
            Set<Map.Entry<K,V>> es = entrySet();
            for(Map.Entry<K,V> e : es) {
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(m.get(key)==null && m.containsKey(key))) return false;
                } 
                else if (!value.equals(m.get(key))) return false;
            }
        } catch (ClassCastException cce) {
            return false;
        } catch (NullPointerException npe) {
            return false;
        }
        return true;
	}
	/**
	 * (NOT SUPPORTED) Performs the given action for each entry in this map 
	 * until all entries have been processed or the action throws an exception.
	 */
	@Override
	public void forEach(BiConsumer<? super K,? super V> action) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         {@code null} if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is null
	 */
	@Override
	public V get(Object key) {
		if(key==null) throw new NullPointerException();
		return (V)ht.get(key);
	}
	/**
	 * Returns the value to which the specified key is mapped, or
     * {@code defaultValue} if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned
     * @param defaultValue the default mapping of the key
     * @return the value to which the specified key is mapped, or
     * 		   {@code defaultValue} if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is null
	 */
	@Override
	public V getOrDefault(Object key, V defaultValue) {
		if(key==null) throw new NullPointerException();
		V v = (V)ht.get((K)key);
		return v==null ? defaultValue : v; 
	}
	/**
     * Returns the hash code value for this map.  The hash code of a map is
     * defined to be the sum of the hash codes of each entry in the map's
     * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt>
     * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps
     * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
     * @return the hash code value for this map
     * @see Map.Entry#hashCode()
     * @see #equals(Object)
     */
	@Override
	public int hashCode() {
		Set<Map.Entry<K,V>> s = entrySet();
		int sum = 0;
		for(Map.Entry<K,V> e : s) {
			sum+= e.hashCode();
		}
		return sum;
	}
	/**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
	@Override
	public boolean isEmpty() {
		return ht.isEmpty();
	}
	/**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     * @return a set view of the keys contained in this map
     */
	@Override
	public Set<K> keySet(){
		return new SetAdapter<K>() {
			{
				ht=MapAdapter.this.ht;
			}
			@Override
			public boolean add(K e) {
				throw new UnsupportedOperationException();
			}
			@Override
			public boolean addAll(Collection<? extends K> c) {
				throw new UnsupportedOperationException();
			}
			@Override
			public boolean contains(Object o) {
				return MapAdapter.this.containsKey(o);
			}
			@Override
			public Iterator<K> iterator() {
				return new SetAdapterIterator() {
					@Override
					public K next() {
						if(!hasNext()) throw new NoSuchElementException();
						called=true;
						temp=en.nextElement();
						index++;
						return (K)(temp);
					}
					@Override
					public void remove() {
						if(!called) throw new IllegalStateException();
						called=false;
						ht.remove(temp);
						index--;
						en=ht.keys();
						int i=0;
						while((i++)<index) en.nextElement();
					}
				};
			}

			@Override
			public Object[] toArray() {
				Object[] arr = new Object[size()];
				int i = 0;
				for(K k : this) arr[i++] = k;
				return arr;
			}
		};
	/**
	 * (NOT SUPPORTED) Returns an immutable map.
	 */
	}
	/**
	 * Associates the specified value with the specified key in this map.  
	 * If the map previously contained a mapping for the key, the old value 
	 * is replaced by the specified value.  (A map <tt>m</tt> is said to 
	 * contain a mapping for a key <tt>k</tt> if and only if 
	 * {@link #containsKey(Object) m.containsKey(k)} would return <tt>true</tt>.)
	 * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>,
     *         if the implementation supports <tt>null</tt> values.)
     * @throws NullPointerException if the specified key or value is null
	 */
	@Override
	public V put(K key, V value) {
		return (V)ht.put(key,value);
	}
	/**
	 * Copies all of the mappings from the specified map to this map.  
	 * The effect of this call is equivalent to that
     * of calling {@link #put(Object,Object) put(k, v)} on this map once
     * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
     * specified map.  The behavior of this operation is undefined if the
     * specified map is modified while the operation is in progress.
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null, or if 
     * 		   the specified map contains null keys or values
     * @see #put(Object, Object)
	 */
	@Override
	public void putAll(Map<? extends K,? extends V> m) {
		if(m==null) throw new NullPointerException();
		boolean b = false;
		try {
			b = (m.containsKey(null)||m.containsValue(null));
		}
		catch(NullPointerException npe) {
		}
		if(b) throw new NullPointerException();
		Set s = m.entrySet();
		for(Object o : s) {
			put(((Map.Entry<K,V>)o).getKey(),((Map.Entry<K,V>)o).getValue());
		}
	}
	/**
	 * If the specified key is not already associated with a value (or is mapped
     * to {@code null}) associates it with the given value and returns
     * {@code null}, else returns the current value.
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         {@code null} if there was no mapping for the key.
     * @throws NullPointerException if the specified key or value is null
	 */
	@Override
	public V putIfAbsent(K key, V value) {
		V v = get(key);
		if (v == null) v = put(key, value);
		return v;
	}
	/**
	 * Removes the mapping for a key from this map if it is present
	 * <p>Returns the value to which this map previously associated the key,
     * or <tt>null</tt> if the map contained no mapping for the key.
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @throws NullPointerException if the specified key is null
	 */
	@Override
	public V remove(Object key) {
		if(key==null) throw new NullPointerException();
		return (V)ht.remove(key);
	}
	/**
	 * Removes the entry for the specified key only if it is currently
     * mapped to the specified value.
     * @param key key with which the specified value is associated
     * @param value value expected to be associated with the specified key
     * @return {@code true} if the value was removed
     * @throws NullPointerException if the specified key or value is null
	 */
	@Override
	public boolean remove(Object key, Object value) {
		if(key==null|| value==null) throw new NullPointerException();
		 if (containsKey(key) && get(key).equals(value)) {
		     remove(key);
		     return true;
		 }
		 else return false;
	}
	/**
	 * Replaces the entry for the specified key only if it is
     * currently mapped to some value.
     * @param key key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         {@code null} if there was no mapping for the key.
     * @throws NullPointerException if the specified key or value is null
	 */
	@Override
	public V replace(K key, V value) {
		if(key==null|| value==null) throw new NullPointerException();
		if (containsKey(key)) {
		     return put(key, value);
		} 
		else return null;
	}
	/**
	 * Replaces the entry for the specified key only if currently
     * mapped to the specified value.
     * The implementation does not throw NullPointerException
     * for maps that do not support null values if oldValue is null unless
     * newValue is also null.
     * @param key key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @return {@code true} if the value was replaced
     * @throws NullPointerException if oldValue or key is null
	 */
	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		if(key==null|| newValue==null) throw new NullPointerException();
		if (containsKey(key) && get(key).equals(oldValue)) {
			put(key, newValue);
		    return true;
		}
		else return false;
	}
	/**
	 * (NOT SUPPORTED) Replaces each entry's value with the result of 
	 * invoking the given function on that entry until all entries have 
	 * been processed or the function throws an exception.
	 */
	@Override
	public void replaceAll(BiFunction<? super K,? super V,? extends V> function) {
		throw new UnsupportedOperationException();
	}
	/**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     * @return the number of key-value mappings in this map
     */
	@Override
	public int size() {
		return ht.size();
	}
	/**
	 * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     * @return a collection view of the values contained in this map
	 */
	@Override
	public Collection<V> values(){
		return new SetAdapter<V>() {
			{
				ht=MapAdapter.this.ht;
			}
			@Override
			public boolean add(V e) {
				throw new UnsupportedOperationException();
			}
			@Override
			public boolean addAll(Collection<? extends V> c) {
				throw new UnsupportedOperationException();
			}
			@Override
			public boolean remove(Object o) {
				if(o==null) throw new NullPointerException();
				if(!contains(o)) return false;
				Iterator<V> it = iterator();
				while(it.hasNext()) {
					if(it.next().equals(o)) {
						it.remove();
						return true;
					}
				}
				return false;
			}
		};
	}

}
