/**
 * 
 */
package H2Adapter;

import java.util.Collection;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.Vector;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Simple Generic Collection interface adapter for J2ME Class Vector.
 * <p>See also: {@link CollectionAdapterIterator} 
 * @param <E> the type of elements in this Collection
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings({"unchecked","rawtypes"}) //Mettevano a dura prova il mio OCD
public class CollectionAdapter<E> implements Collection<E>, Iterable<E>{
	/**
	 * Vector adaptee.
	 */
	protected Vector v;
	/**
	 * Constructs a new CollectionAdapter that wraps given Vector.
	 * @param vector Vector instance adaptee
	 * @throws NullPointerException if vector is null
	 */
	public CollectionAdapter(Vector vector) {
		if(vector==null) throw new NullPointerException();
		v=vector;
	}
	/**
	 * Constructs a new CollectionAdapter that wraps a new Vector instance.
	 */
	public CollectionAdapter(){
		v = new Vector();
	}
	/**
	 * Ensures that this collection contains the specified element.
     * Returns <tt>true</tt> if this collection changed as a
     * result of the call.
     * @param e element whose presence in this collection is to be ensured
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 */
	@Override
	public boolean add(E e) {
		v.addElement(e);
		return true;
	}
	/**
	 * Adds all of the elements in the specified collection to this collection.		
     * The behavior of this operation is undefined if the specified collection 
     * is modified while the operation is in progress.
     * (This implies that the behavior of this call is undefined if the
     * specified collection is this collection, and this collection is
     * nonempty.).
     * @param c collection containing elements to be removed from this collection
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws NullPointerException if parameter 'e' is null.
	 * @see #remove(Object)
     * @see #contains(Object)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if(c==null) throw new NullPointerException();
		for(E e : c) add(e);
		return (c.size()!=0);
	}
	/**
	 * Removes all of the elements from this collection.
	 * The collection will be empty after this method returns.
	 */
	@Override
	public void clear() {
		v.removeAllElements();
	}
	/**
	 * Returns <tt>true</tt> if this collection contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this collection
     * contains at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     * @param o element whose presence in this collection is to be tested
     * @return <tt>true</tt> if this collection contains the specified element
	 */
	@Override
	public boolean contains(Object o) {
		return v.contains(o);
	}
	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements
     * in the specified collection.
     * @param  c collection to be checked for containment in this collection
     * @return <tt>true</tt> if this collection contains all of the elements
     *         in the specified collection
     * @throws NullPointerException if the specified collection is null.
     * @see #contains(Object)
     */
	@Override
	public boolean containsAll(Collection<?> c) {
		if(c==null) throw new NullPointerException();
		for(Object o : c) {
			if(!contains(o)) return false;
		}
		return true;
	}
	/**
	 * Compares the specified object with this collection for equality.
	 * @param o object to be compared for equality with this collection
     * @return <tt>true</tt> if the specified object is equal to this collection
	 */
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof Collection)) return false;
		Collection c = (Collection)o; 
		if(size()!=c.size()) return false;
		return containsAll(c);
	}
	/**
	 * Returns the hash code value for this collection.
	 * @return the hash code value for this collection
	 */
	@Override
	public int hashCode() {
		int hash=0;
		for(E e : this) hash+=(e==null ? 0 : e.hashCode());
		return hash;
	}
	/**
	 * Returns true if this collection contains no elements. 
	 * @return <tt>true</tt> if this collection contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return v.isEmpty();
	}
	/**
	 * Returns an iterator ({@link CollectionAdapterIterator}) 
	 * over the elements in this collection.
	 * @return an iterator over the elements in this collection.
	 */
	@Override
	public Iterator<E> iterator(){
		return new CollectionAdapterIterator();
	}
	/**
	 * (NOT SUPPORTED) Returns a possibly parallel Stream with this collection as its source.
	 */
	@Override
	public Stream<E> parallelStream(){
		throw new UnsupportedOperationException(); 
	}
	/**
	 * Removes a single instance of the specified element from this
     * collection, if it is present.  More formally, removes an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this collection contains one or more such elements.
     * @param o element to be removed from this collection, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
	 */
	@Override
	public boolean remove(Object o) {
		return v.removeElement(o);
	}
	/**
	 * Removes all of this collection's elements that are also contained in the
     * specified collection (optional operation).  After this call returns,
     * this collection will contain no elements in common with the specified
     * collection.
     * @param c collection containing elements to be removed from this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #contains(Object)
	 * @see #remove(Object)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		if(c==null) throw new NullPointerException();
		boolean b = false;
		for(Object o : c) {
			while(contains(o)) b|=remove(o);
		}
		return b;
	}
	/**
	 * (NOT SUPPORTED) Removes all of the elements of this collection that satisfy the given predicate.
	 */
	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Retains only the elements in this collection that are contained in the
     * specified collection.  In other words, removes from this collection all 
     * of its elements that are not contained in the specified collection.
     * @param c collection containing elements to be retained in this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
     * @see #contains(Object)
	 */
	@Override
	public boolean retainAll(Collection <?> c) {
		if(c==null) throw new NullPointerException();
		boolean b = false;
		for(int i = 0; i<size(); i++) {
			if(!c.contains(v.elementAt(i))) {
				v.removeElementAt(i);
				b=true;
				i--;
			}
		}
		return b;
	}
	/**
	 * Returns the number of elements in this collection.  If this collection
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     * @return the number of elements in this collection
	 */
	@Override
	public int size() {
		return v.size();
	}
	/**
	 * (NOT SUPPORTED) Creates a Spliterator over the elements in this collection.
	 */
	@Override
	public Spliterator<E> spliterator(){
		throw new UnsupportedOperationException();
	}
	/**
	 * (NOT SUPPORTED) Returns a sequential Stream with this collection as its source.
	 */
	@Override
	public Stream<E> stream(){
		throw new UnsupportedOperationException();
	}
	/**
	 * Returns an array containing all of the elements in this collection.
	 * <p>Array is a shallow copy and it's not backed to the Collection.
	 * Structural changes to the collection do not affect the array, therefore
	 * it is to be considered an invalid reference.
	 * <p>The returned array will be "safe" in that no references to it are
     * maintained by this collection.
     * The caller is thus free to modify the returned array.
	 * @return an array containing all of the elements in this collection
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size()];
		Iterator<E> it = iterator();
		int i=0;
		while(it.hasNext()) arr[i++]=it.next();
		return arr;
	}
	/**
	 * (NOT SUPPORTED) Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * An iterator over a {@link CollectionAdapter} instance.
	 * <p> Scans the collection in no particular order.
	 * <p> See also: {@link CollectionAdapter}
	 * @author Manfè Alessandro 1201538
	 *
	 */
	protected class CollectionAdapterIterator implements Iterator<E>{
		/**
		 * Index of the next element called by {@code next}
		 */
		protected int index;
		/**
		 * Tells if {@code remove} can be called or not.
		 */
		protected boolean called;
		/**
		 * Construct a new iterator over {@link CollectionAdapter}.
		 */
		CollectionAdapterIterator() {
			index = 0;
			called=false;
		}
		/**
		 * Returns true if the iteration has more elements.
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			if(index<v.size()) return true;
			else return false;
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
			return (E)v.elementAt(index++);
		}
		/**
		 * Removes from the underlying collection the last element returned by this iterator.
		 * @throws IllegalStateException if the {@code next} method has not
		 *         yet been called, or the {@code remove} method has already
		 *         been called after the last call to the {@code next} method
		 */
		@Override
		public void remove() {
			if(!called) throw new IllegalStateException();
			called=false;
			v.removeElementAt(--index);
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