/**
 * 
 */
package H2Adapter;

import java.util.Set;
import java.util.Hashtable;
import java.util.Spliterator;
import java.util.function.Consumer;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Simple Generic Set interface adapter for J2ME Class Hashtable.
 * <p><tt>Since the adaptee is an Hashtable this set does not allow null elements.</tt> 
 * <p>See also: {@link SetAdapterIterator} 
 * @param <E> the type of elements in this set
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings({"unchecked","rawtypes"}) //Mettevano a dura prova il mio OCD
	public class SetAdapter<E> implements Set<E>, Iterable<E>{
	/**
	 * Hashtable adaptee.
	 */
	protected Hashtable ht;
	/**
	 * Constructs a new SetAdapter that wraps new Hashtable instance.
	 */
	public SetAdapter() {
		ht = new Hashtable();
	}
	/**
	 * Constructs a new SetAdapter that wraps given Hashtable.
	 * @param hasht Hashtable instance adaptee
	 * @throws NullPointerException if specified Hashtable is null
	 */
	public SetAdapter(Hashtable hasht) {
		if(hasht==null) throw new NullPointerException();
		ht = hasht;
	}
	/**Adds the specified element to this set if it is not already present.  
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.  In combination with the
     * restriction on constructors, this ensures that sets never contain
     * duplicate elements.
     * @param e element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     *         element
     * @throws NullPointerException if the specified element is null
     */
	@Override
	public boolean add(E e) {
		if(e==null) throw new NullPointerException();
		if(ht.contains(e)) return false;
		ht.put(e,e);
		return true;
	}
	/**Adds all of the elements in the specified collection to this set if
     * they're not already present.  If the specified collection is also a set, 
     * the <tt>addAll</tt> operation effectively modifies this set so that 
     * its value is the <i>union</i> of the two sets.  
     * The behavior of this operation is undefined if the specified
     * collection is modified while the operation is in progress.
     * @param  c collection containing elements to be added to this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements or if the specified collection is null
     * @see #add(Object)
     */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if(c==null) throw new NullPointerException();
		boolean bool=false;
		try {
			bool = c.contains(null);
		}
		catch(NullPointerException npe) {	
		}
		if(bool) throw new NullPointerException("Collection contains null Object");
		boolean b = false;
		for(E e : c) b|=add(e);
		return b;
	}
	/**
	 * Removes all of the elements from this set.
     * The set will be empty after this call returns.
	 */
	@Override
	public void clear() {
		ht.clear();
	}
	/**
	 * Returns <tt>true</tt> if this set contains the specified element.
	 * @param o element whose presence in this set is to be tested
     * @return <tt>true</tt> if this set contains the specified element
     * @throws NullPointerException if the specified element is null
	 */
	@Override
	public boolean contains(Object o) {
		return ht.contains(o);
	}
	/**Returns <tt>true</tt> if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns <tt>true</tt> if it is a <i>subset</i> of this set.
     * @param  c collection to be checked for containment in this set
     * @return <tt>true</tt> if this set contains all of the elements of the
     *         specified collection
     * @throws NullPointerException if the specified collection is null or
     * 		   contains one or more null elements
     */
	@Override
	public boolean containsAll(Collection<?> c) {
		if(c==null) throw new NullPointerException();
		boolean b=false;
		try {
			b = c.contains(null);
		}
		catch(NullPointerException npe) {	
		}
		if(b) throw new NullPointerException("Collection contains null Object");
		for(Object o : c) {
			if(!(contains(o))) return false;
		}
		return true;
	}
	/**
	 * Compares the specified object with this set for equality.  Returns
     * <tt>true</tt> if the specified object is also a set, the two sets
     * have the same size, and every member of the specified set is
     * contained in this set (or equivalently, every member of this set is
     * contained in the specified set).  This definition ensures that the
     * equals method works properly across different implementations of the
     * set interface.
     * @param o object to be compared for equality with this set
     * @return <tt>true</tt> if the specified object is equal to this set
	 */
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof Set)) return false;
		try {
			Set sa = (Set)o;
			if(size()!=sa.size()) return false;
			return containsAll(sa);
		}
		catch(NullPointerException npe){
			return false;
		}
	}
	/**
	 * Returns the hash code value for this set.  The hash code of a set is
     * defined to be the sum of the hash codes of the elements in the set.
     * This ensures that <tt>s1.equals(s2)</tt> implies that
     * <tt>s1.hashCode()==s2.hashCode()</tt> for any two sets <tt>s1</tt>
     * and <tt>s2</tt>.
     * @return the hash code value for this set
     * @see Set#equals(Object)
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		for(E e : this) hash+=e.hashCode();
		return hash;
	}
	/**
	 * Returns <tt>true</tt> if this set contains no elements.
     * @return <tt>true</tt> if this set contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return ht.isEmpty();
	}
	/**
	 * Returns an iterator over the elements in this set.
	 */
	@Override
	public Iterator<E> iterator(){
		return new SetAdapterIterator();
	}
	/**
	 * Removes the specified element from this set if it is present.
	 * Returns <tt>true</tt> if this set contained the element 
	 * (or equivalently, if this set changed as a result of the call).
	 * @param o object to be removed from this set, if present
     * @return <tt>true</tt> if this set contained the specified element
     * @throws NullPointerException if the specified element is null
	 */
	@Override
	public boolean remove(Object o) {
		if(o==null) throw new NullPointerException();
		if(!contains(o)) return false;
		ht.remove(o);
		return true;
	}
	/**
	 * Removes from this set all of its elements that are contained in the specified collection.
	 * If the specified collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of the two sets.
	 * @param  c collection containing elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		if(c==null) throw new NullPointerException();
		Iterator<E> it = iterator();
		boolean b = false;
		while(it.hasNext()) {
			if(c.contains(it.next())) {
				it.remove();
				b=true;
			}
		}
		return b;
	}
	/**
	 * Retains only the elements in this set that are contained in the specified collection.
	 * In other words, removes from this set all of its elements that are not contained in the
     * specified collection.  If the specified collection is also a set, this operation 
     * effectively modifies this set so that its value is the <i>intersection</i> of the two sets.
	 * @param  c collection containing elements to be retained in this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		if(c==null) throw new NullPointerException();
		Iterator<E> it = iterator();
		boolean b = false;
		while(it.hasNext()) {
			if(!c.contains(it.next())) {
				it.remove();
				b=true;
			}
		}
		return b;
	}
	/**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     * @return the number of elements in this set (its cardinality)
     */
	@Override
	public int size() {
		return ht.size();
	}
	/**
	 * (NOT SUPPORTED) Creates a Spliterator over the elements in this set.
	 */
	@Override
	public Spliterator<E> spliterator(){
		throw new UnsupportedOperationException();
	}
	/**Returns an array containing all of the elements in this set.
     * <p>Array is a shallow copy and it's not backed to the set.
	 * Structural changes to the set do not affect the array, therefore
	 * it is to be considered an invalid reference.
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     * @return an array containing all the elements in this set
     */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size()];
		Enumeration en = ht.elements();
		int i=0;
		while(en.hasMoreElements()) arr[i++]=en.nextElement();
		return arr;
	}
	/**
	 * (NOT SUPPORTED) Returns an array containing all of the elements in this set; 
	 * the runtime type of the returned array is that of the specified array.
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * An Iterator over {@link SetAdapter} instance.
	 * <p> Scans the set in the order give by Hashtable enumeration
	 * <p> See also: {@link SetAdapter}
	 * @author Manfè Alessandro 1201538
	 *
	 */
	protected class SetAdapterIterator implements Iterator<E>{
		/**
		 * Hashtable key enumeration
		 */
		protected Enumeration en;
		/**
		 * Last key returned by the enumeration
		 */
		protected Object temp;
		/**
		 * Tells if {@code remove} can be called or not.
		 */
		protected boolean called;
		/**
		 * Index of the next element called by {@code next}
		 */
		protected int index=0;
		/**
		 * Construct a new iterator over {@link SetAdapter}.
		 */
		SetAdapterIterator() {
			en = ht.keys();
			called=false;
		}
		/**
		 * Returns true if the iteration has more elements.
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			return en.hasMoreElements();
		}
		/**
		 * Returns the next element in the iteration.
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			called=true;
			temp=en.nextElement();
			index++;
			return (E)(ht.get(temp));
		}
		/**
		 * Removes from the underlying set the last element returned by this iterator.
		 * @throws IllegalStateException if the {@code next} method has not
		 *         yet been called, or the {@code remove} method has already
		 *         been called after the last call to the {@code next} method
		 */
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
		/**
		 * (NOT SUPPORTED) Performs the given action for each remaining element until all elements have been processed or the action throws an exception.
		 */
		@Override
		public void forEachRemaining(Consumer<? super E> action){
			throw new UnsupportedOperationException();
		}
	}
}
