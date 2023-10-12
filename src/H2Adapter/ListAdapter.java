/**
 * 
 */
package H2Adapter;

import java.util.List;
import java.util.Vector;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.*;


/**
 * Simple Generic List interface adapter for J2ME Class Vector.
 * <p>See also: {@link ListAdapterIterator} , {@link H2Adapter.SubListAdapter}
 * @param <E> the type of elements in this list
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings({"unchecked","rawtypes"}) //Mettevano a dura prova il mio OCD
public class ListAdapter<E> implements List<E>, Iterable<E>{
	/**
	 * Vector adaptee.
	 */
	protected Vector v;
	/**
	 * Constructs a new ListAdapter that wraps a new Vector instance.
	 */
	public ListAdapter() {
		v = new Vector();
	}
	/**
	 * Contructs a new ListAdapter that wraps given Vector.
	 * @throws NullPointerException if specified vector is null
	 * @param vector Vector instance adaptee
	 */
	public ListAdapter(Vector vector){
		if(vector==null) throw new NullPointerException();
		v=vector;
	}
	/**
	 * Inserts the specified element at the specified position in this list.  
	 * Shifts the element currently at that position (if any) and any 
	 * subsequent elements to the right (adds one to their indices).
	 * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
	 * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
	 */
	@Override
	public void add(int index, E element) {
		if(index<0||index>size()) throw new IndexOutOfBoundsException();
		v.insertElementAt(element, index);
	}
	/**
	 * Appends the specified element to the end of this list.
	 * @param e element to be appended to this list
     * @return <tt>true</tt> 
	 */
	@Override
	public boolean add(E e) {
		v.addElement(e);
		return true;
	}
	/**
	 * Inserts all of the elements in the specified collection into this list at the specified position.
	 * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this list in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the
     * operation is in progress.
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if(c==null) throw new NullPointerException();
		for(E e : c) {
			add(index++,e);
		}
		return (c.size()!=0);
	}
	/**
	 * Appends all of the elements in the specified collection to the end of this list,
	 * in the order that they are returned by the specified collection's iterator.
	 * The behavior of this operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
	 * @throws NullPointerException if the specified collection is null
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return addAll(size(),c);
	}
	/**
	 * Removes all of the elements from this list.
	 * The list will be empty after this call returns.
	 */
	@Override
	public void clear() {
		v.removeAllElements();
	}
	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
	 */
	@Override
	public boolean contains(Object o) {
		return v.contains(o);
	}
	/**
	 * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
     * @param  c collection to be checked for containment in this list
     * @return <tt>true</tt> if this list contains all of the elements of the specified collection
	 * @throws NullPointerException if the specified collection is null
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
	 * Compares the specified object with this list for equality.
	 * Returns <tt>true</tt> if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.
     * @param o the object to be compared for equality with this list
     * @return <tt>true</tt> if the specified object is equal to this list
	 */
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof List)) return false;
		ListIterator<E> li1 = listIterator();
		ListIterator<?> li2 = ((List)o).listIterator();
		while(li1.hasNext()&&li2.hasNext()) {
			E o1 = li1.next();
			Object o2 = li2.next();
			if(!(o1==null ? o2==null : o1.equals(o2))) return false;
		}
		return !(li1.hasNext()||li2.hasNext());
	}
	/**
	 * Returns the element at the specified position in this list.
	 * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
	 */
	@Override
	public E get(int index) {
		if(index<0||index>=size()) throw new IndexOutOfBoundsException();
		return (E)v.elementAt(index);
	}
	/**
	 * Returns the hash code value for this list.
	 * The hash code of a list is defined to be the result of the following calculation:
     * <pre>{@code
     *     int hashCode = 1;
     *     for (E e : list)
     *         hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     * }</pre>
     * This ensures that <tt>list1.equals(list2)</tt> implies that
     * <tt>list1.hashCode()==list2.hashCode()</tt> for any two lists,
     * <tt>list1</tt> and <tt>list2</tt>, as required by the general
     * contract of {@link Object#hashCode}.
     * @return the hash code value for this list
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			E e = it.next();
			hash = 31*hash + (e==null ? 0 : e.hashCode());
		}
		return hash;  
	}
	/**
	 * Returns the index of the first occurrence of the specified element in this list, 
	 * or -1 if this list does not contain the element.
	 * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
	 */
	@Override
	public int indexOf(Object o) {
		return v.indexOf(o);
	}
	/**
	 * Returns <tt>true</tt> if this list contains no elements.
     * @return <tt>true</tt> if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return v.isEmpty();
	}
	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * @return an iterator over the elements in this list
	 * @See {@link ListAdapterIterator}
	 */
	@Override
	public Iterator<E> iterator(){
		return new ListAdapterIterator();
	}
	/**
	 * Returns the index of the last occurrence of the specified element in this list, 
	 * or -1 if this list does not contain the element.
	 * @param o element to search for
	 * @return the index of the last occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
	 */
	@Override
	public int lastIndexOf(Object o) {
		return v.lastIndexOf(o);
	}
	/**
	 * Returns a list iterator over the elements in this list (in proper sequence).
	 * @return a list iterator over the elements in this list
	 * @See {@link ListAdapterIterator}
	 */
	@Override
	public ListIterator<E> listIterator(){
		return new ListAdapterIterator();
	}
	/**
	 * Returns a list iterator over the elements in this list (in proper sequence), 
	 * starting at the specified position in the list.
	 * @param index at which the iteration start
	 * @return a list iterator over the elements in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
	 * @See {@link ListAdapterIterator}
	 */
	@Override
	public ListIterator<E> listIterator(int index){
		if(index < 0 || index > size()) throw new IndexOutOfBoundsException();
		return new ListAdapterIterator(index);
	}
	/**
	 * Removes the element at the specified position in this list.
	 * @param index index position of the element to remove
	 * @return value that has been removed
	 * @throws IndexOutOfBoundsException if index has invalid value.
	 */
	@Override
	public E remove(int index) {
		if(index<0||index>=size()) throw new IndexOutOfBoundsException();
		E e = get(index);
		v.removeElementAt(index);
		return e;
	}
	/**
	 * Removes the first occurrence of the specified element from this list, if it is present.
	 * If this list does not contain the element, it is unchanged.  
	 * More formally, removes the element with the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
	 */
	@Override
	public boolean remove(Object o) {
		return v.removeElement(o);
	}
	/**
	 * Removes from this list all of its elements that are contained in the specified collection.
	 * @param c collection containing elements to be removed from this list
     * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #remove(Object)
     * @see #contains(Object)
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
	 * (NOT SUPPORTED) Replaces each element of this list with the result of applying the operator to that element.
	 */
	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Retains only the elements in this list that are contained in the specified collection.
	 * In other words, removes from this list all of its elements that are not contained in the
     * specified collection.
     * @param c collection containing elements to be retained in this list
     * @return <tt>true</tt> if this list changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #remove(Object)
     * @see #contains(Object)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
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
	 * Replaces the element at the specified position in this list with the specified element.
	 * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
	 */
	@Override
	public E set(int index, E element) {
		if(index<0||index>=size()) throw new IndexOutOfBoundsException();
		E temp = (E)v.elementAt(index);
		v.setElementAt(element, index);
		return temp;
	}
	/**
	 * Returns the number of elements in this list.  
	 * If this list contains more than <tt>Integer.MAX_VALUE</tt> 
	 * elements, returns <tt>Integer.MAX_VALUE</tt>.
     * @return the number of elements in this list
	 */
	@Override
	public int size() {
		return v.size();
	}
	/**
	 * (NOT SUPPORTED) Sorts this list according to the order induced by the specified Comparator.
	 */
	@Override
	public void sort(Comparator<? super E> c) {
		throw new UnsupportedOperationException();
	}
	/**
	 * (NOT SUPPORTED) Creates a Spliterator over the elements in this list.
	 */
	@Override
	public Spliterator<E> spliterator(){
		throw new UnsupportedOperationException();
	}
	/**
	 * Returns a view of the portion of this list between the specified
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive.  (If
     * <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported
     * by this list.<p>
     * The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *         (<tt>fromIndex &lt; 0 || toIndex &gt; size ||
     *         fromIndex &gt; toIndex</tt>)
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex){
		if(fromIndex<0||toIndex>size()) throw new IndexOutOfBoundsException();
		return new SubListAdapter<E>(v,fromIndex,toIndex);
	}
	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
	 * <p>Array is a shallow copy and it's not backed to the list.
	 * Structural changes to the list do not affect the array, therefore
	 * it is to be considered an invalid reference.
	 * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.
     * The caller is thus free to modify the returned array.
     * @return an array containing all of the elements in this list in proper sequence
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size()];
		v.copyInto(arr);
		return arr;
	}
	/**
	 * (NOT SUPPORTED) Returns an array containing all of the elements in this list in proper sequence 
	 * (from first to last element); the runtime type of the returned array is that of the specified array.
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * A ListIterator over {@link ListAdapter} instance that allows the programmer
	 * to traverse the list in either direction, modify
	 * the list during iteration, and obtain the iterator's
	 * current position in the list. A {@code ListIterator}
	 * has no current element; its <I>cursor position</I> always
	 * lies between the element that would be returned by a call
	 * to {@code previous()} and the element that would be
	 * returned by a call to {@code next()}.
	 * <p> See also: {@link ListAdapter}
	 * @author Manfè Alessandro 1201538
	 *
	 */
	protected class ListAdapterIterator implements ListIterator<E>{
		/**
		 * Index of the next element called by {@code next}
		 */
		protected int index;
		/**
		 * Tells if {@code remove} can be called or not.
		 */
		protected boolean called;
		/**
		 * index to the last value returned.
		 */
		protected int last;
		/**
		 * Constructs a new ListIterator over given Vector
		 */
		ListAdapterIterator() {
			index=0;
			called=false;
		}
		/**
		 * Constructs a new ListIterator over given Vector, starting form index i.
		 * @param i Starting offset index.
		 */
		ListAdapterIterator(int i) {
			index=i;
			called=false;
		}
		/**
		 * Returns {@code true} if this list iterator has more elements when
		 * traversing the list in the forward direction. (In other words,
		 * returns {@code true} if {@link #next} would return an element rather
		 * than throwing an exception.)
		 * @return {@code true} if the list iterator has more elements when
		 *         traversing the list in the forward direction
		 */
		@Override
		public boolean hasNext() {
			return(index<v.size());
		}
		/**
	     * Returns the next element in the list and advances the cursor position.
	     * This method may be called repeatedly to iterate through the list,
	     * or intermixed with calls to {@link #previous} to go back and forth.
	     * (Note that alternating calls to {@code next} and {@code previous}
	     * will return the same element repeatedly.)
	     * @return the next element in the list
	     * @throws NoSuchElementException if the iteration has no next element
	     */
		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			called=true;
			last=index;
			return (E)v.elementAt(index++);
		}
		 /**
	     * Returns {@code true} if this list iterator has more elements when
	     * traversing the list in the reverse direction.  (In other words,
	     * returns {@code true} if {@link #previous} would return an element
	     * rather than throwing an exception.)
	     * @return {@code true} if the list iterator has more elements when
	     *         traversing the list in the reverse direction
	     */
		@Override
		public boolean hasPrevious() {
			return(index>0);
		}
		/**
	     * Returns the previous element in the list and moves the cursor
	     * position backwards.  This method may be called repeatedly to
	     * iterate through the list backwards, or intermixed with calls to
	     * {@link #next} to go back and forth.  (Note that alternating calls
	     * to {@code next} and {@code previous} will return the same element repeatedly.)
	     * @return the previous element in the list
	     * @throws NoSuchElementException if the iteration has no previous
	     *         element
	     */
		@Override
		public E previous() {
			if(!hasPrevious()) throw new NoSuchElementException();
			called=true;
			last = index-1;
			return (E)v.elementAt(--index);
		}
		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to {@link #next}. (Returns list size if the list
		 * iterator is at the end of the list.)
     	 * @return the index of the element that would be returned by a
     	 *         subsequent call to {@code next}, or list size if the list
     	 *         iterator is at the end of the list
		 */
		@Override
		public int nextIndex() {
			return(index<v.size() ? index : v.size());
		}
		/**
	     * Returns the index of the element that would be returned by a
	     * subsequent call to {@link #previous}. (Returns -1 if the list
	     * iterator is at the beginning of the list.)
	     * @return the index of the element that would be returned by a
	     *         subsequent call to {@code previous}, or -1 if the list
	     *         iterator is at the beginning of the list
	     */
		@Override
		public int previousIndex() {
			return index-1;
		}
		/**
	     * Removes from the list the last element that was returned by {@link
	     * #next} or {@link #previous} (optional operation).  This call can
	     * only be made once per call to {@code next} or {@code previous}.
	     * It can be made only if {@link #add} has not been
	     * called after the last call to {@code next} or {@code previous}.
	     * @throws IllegalStateException if neither {@code next} nor
	     *         {@code previous} have been called, or {@code remove} or
	     *         {@code add} have been called after the last call to
	     *         {@code next} or {@code previous}
	     */
		@Override
		public void remove() {
			if(!called) throw new IllegalStateException();
			called=false;
			index=last;
			v.removeElementAt(last);
		}
		/**
		 * Replaces the last element returned by {@link #next} or
		 * {@link #previous} with the specified element (optional operation).
		 * This call can be made only if neither {@link #remove} nor {@link
		 * #add} have been called after the last call to {@code next} or
		 * {@code previous}.
		 *  @throws IllegalStateException if neither {@code next} nor
		 *         {@code previous} have been called, or {@code remove} or
		 *         {@code add} have been called after the last call to
		 *         {@code next} or {@code previous}
		 */
		@Override
		public void set(E e) {
			if(!called) throw new IllegalStateException();
			v.setElementAt(e, last);
		}
		/**
		 * Inserts the specified element into the list.
		 * The element is inserted immediately before the element that
		 * would be returned by {@link #next}, if any, and after the element
		 * The new element is inserted before the implicit cursor: 
		 * a subsequent call to {@code next} would be unaffected, and a
		 * subsequent call to {@code previous} would return the new element.
		 * that would be returned by {@link #previous}, if any.
		 * @param e the element to insert
		 */
		@Override
		public void add(E e) {
			called=false;
			v.insertElementAt(e, index++);
			
		}
		/**
		 * (NOT SUPPORTED) Performs the given action for each remaining element
		 * until all elements have been processed or the action throws an exception.
		 */
		@Override
		public void forEachRemaining(Consumer<? super E> action){
			throw new UnsupportedOperationException();
		}
	}
}
