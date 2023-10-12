/**
 * 
 */
package H2Adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * Simple Generic backed SubList interface adapter for J2ME Class Vector.
 * A (backed) view of a portion of a {@link H2Adapter.ListAdapter}
 * <p>
 * <p>See also: {@link H2Adapter.ListAdapter}, {@link SubListAdapterIterator}.
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings({"unchecked","rawtypes"}) //Mettevano a dura prova il mio OCD
public class SubListAdapter<E> implements List<E>, Iterable<E>{
	/**
	 * Vector adaptee.
	 */
	protected Vector subv;
	/**
	 * Starting index of the list view (inclusive)
	 */
	private int from;
	/**
	 * Ending index of the list view (exclusive)
	 */
	private int to;
	/**
	 * Constructs a new SubListAdapter that wraps a given Vector bounded by index parameters.
	 * @param vector Vector instance adaptee
	 * @param fromIndex starting index of the list view (inclusive)
	 * @param toIndex ending index of the list view (exclusive)
	 * @throws NullPointerException if specified vector is null
	 */
	public SubListAdapter(Vector vector, int fromIndex, int toIndex){
		if(vector==null) throw new NullPointerException();
		from=fromIndex;
		to=toIndex;
		subv=vector;
	}
	/**
	 * Returns the number of elements in this sublist.  
	 * If this sublist contains more than <tt>Integer.MAX_VALUE</tt> 
	 * elements, returns <tt>Integer.MAX_VALUE</tt>.
     * @return the number of elements in this sublist
	 */
	@Override
	public int size() {
		return to-from;
	}
	/**
	 * Returns <tt>true</tt> if this sublist contains no elements.
     * @return <tt>true</tt> if this sublist contains no elements
	 */
	@Override
	public boolean isEmpty() {
		return to==from;
	}
	/**
	 * Returns <tt>true</tt> if this sublist contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this sublist contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     * @param o element whose presence in this sublist is to be tested
     * @return <tt>true</tt> if this sublist contains the specified element
	 */
	@Override
	public boolean contains(Object o) {
		for(int i=from; i<(from+size());i++) {
			if(((E)subv.elementAt(i)).equals(o)) return true;
		}
		return false;
	}
	/**
	 * Returns an array containing all of the elements in this sublist in proper sequence (from first to last element).
	 * <p>Array is a shallow copy and it's not backed to the sublist.
	 * Structural changes to the sublist do not affect the array, therefore
	 * it is to be considered an invalid reference.
	 * <p>The returned array will be "safe" in that no references to it are
     * maintained by this sublist.
     * The caller is thus free to modify the returned array.
     * @return an array containing all of the elements in this sublist in proper sequence
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size()];
		Iterator<E> iter = iterator();
		int i=0;
		while(iter.hasNext()) arr[i++]=iter.next();
		return arr;
	}
	/**
	 * (NOT SUPPORTED) Returns an array containing all of the elements in this Sublist in proper sequence 
	 * (from first to last element); the runtime type of the returned array is that of the specified array.
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Appends the specified element to the end of this sublist.
	 * @param e element to be appended to this sublist
     * @return <tt>true</tt> 
	 */
	@Override
	public boolean add(E e) {
		subv.insertElementAt(e,to++);
		return true;
	}
	/**
	 * Removes the first occurrence of the specified element from this sublist, if it is present.
	 * If this sublist does not contain the element, it is unchanged.  
	 * More formally, removes the element with the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).
     * @param o element to be removed from this sublist, if present
     * @return <tt>true</tt> if this sublist contained the specified element
	 */
	@Override
	public boolean remove(Object o) {
		if(!contains(o)) return false;
		remove(indexOf(o));
		return true;
	}
	/**
	 * Returns <tt>true</tt> if this sublist contains all of the elements of the specified collection.
     * @param  c collection to be checked for containment in this sublist
     * @return <tt>true</tt> if this sublist contains all of the elements of the specified collection
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
	 * Appends all of the elements in the specified collection to the end of this sublist,
	 * in the order that they are returned by the specified collection's iterator.
	 * The behavior of this operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this sublist, and it's nonempty.)
	 * @throws NullPointerException if the specified collection is null
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if(c==null) throw new NullPointerException();
		for(E e : c) {
			add(e);
		}
		return c.size()!=0;
	}
	/**
	 * Inserts all of the elements in the specified collection into this sublist at the specified position.
	 * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this sublist in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the
     * operation is in progress.
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this sublist
     * @return <tt>true</tt> if this sublist changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if(c==null) throw new NullPointerException();
		for(E e : c) {
			add((index++),e);
		}
		return c.size()!=0;
	}
	/**
	 * Removes from this sublist all of its elements that are contained in the specified collection.
	 * @param c collection containing elements to be removed from this sublist
     * @return <tt>true</tt> if this sublist changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #remove(Object)
     * @see #contains(Object)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		if(c==null) throw new NullPointerException();
		boolean b = false;
		for(Object o : c) {
			while(contains(o)) {
				remove(indexOf(o));
				b=true;
			}
		}
		return b;
	}
	/**
	 * Retains only the elements in this sublist that are contained in the specified collection.
	 * In other words, removes from this sublist all of its elements that are not contained in the
     * specified collection.
     * @param c collection containing elements to be retained in this sublist
     * @return <tt>true</tt> if this sublist changed as a result of the call
	 * @throws NullPointerException if the specified collection is null
	 * @see #remove(Object)
     * @see #contains(Object)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		if(c==null) throw new NullPointerException();
		boolean b = false;
		for(int i=from;i<(from+size());i++) {
			if(!c.contains(subv.elementAt(i))) {
				remove((i--)-from);
				b=true;
			}
		}
		return b;
	}
	/**
	 * Removes all of the elements from this sublist.
	 * The sublist will be empty after this call returns.
	 */
	@Override
	public void clear() {
		while(size()>0) remove(0);
	}
	/**
	 * Returns the element at the specified position in this sublist.
	 * @param index index of the element to return
     * @return the element at the specified position in this sublist
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
	 */
	@Override
	public E get(int index) {
		if(index<0||index>=size()) throw new IndexOutOfBoundsException();
		return (E)subv.elementAt(index+from);
	}
	/**
	 * Replaces the element at the specified position in this sublist with the specified element.
	 * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
	 */
	@Override
	public E set(int index, E element) {
		if(index<0||index>=size()) throw new IndexOutOfBoundsException();
		E temp = (E)subv.elementAt(index+from);
		subv.setElementAt(element, index+from);
		return temp;
	}
	/**
	 * Inserts the specified element at the specified position in this sublist.  
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
		subv.insertElementAt(element ,index+from);
		to++;
		
	}
	/**
	 * Removes the element at the specified position in this sublist.
	 * @param index index position of the element to remove
	 * @return value that has been removed
	 * @throws IndexOutOfBoundsException if index has invalid value.
	 */
	@Override
	public E remove(int index) {
		if(index<0||index>=size()) throw new IndexOutOfBoundsException(((Integer)index).toString());
		E temp = (E)subv.elementAt(index+from);
		subv.removeElementAt(index+from);
		to--;
		return temp;
	}
	/**
	 * Returns the index of the first occurrence of the specified element in this sublist, 
	 * or -1 if this sublist does not contain the element.
	 * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this sublist, or -1 if this sublist does not contain the element
	 */
	@Override
	public int indexOf(Object o) {
		for(int i=from;i<(from+size());i++) {
			if(subv.elementAt(i).equals((E)o)){
				return i-from;
			}
		}
		return -1;
	}
	/**
	 * Returns the index of the last occurrence of the specified element in this sublist, 
	 * or -1 if this sublist does not contain the element.
	 * @param o element to search for
	 * @return the index of the last occurrence of the specified element in
     *         this sublist, or -1 if this sublist does not contain the element
	 */
	@Override
	public int lastIndexOf(Object o) {
		int index = -1;
		for(int i=from;i<(from+size());i++) {
			if(((E)o).equals(subv.elementAt(i))){
				index = i-from;
			}
		}
		return index;
	}
	/**
	 * Returns the hash code value for this sublist.
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
     * @return the hash code value for this sublist
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
	 * Compares the specified object with this sublist for equality.
	 * Returns <tt>true</tt> if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.
     * @param o the object to be compared for equality with this sublist
     * @return <tt>true</tt> if the specified object is equal to this sublist
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
	 * A ListIterator over {@link SubListAdapter} instance that allows the programmer
	 * to traverse the sublist in either direction, modify
	 * the sublist during iteration, and obtain the iterator's
	 * current position in the sublist. A {@code ListIterator}
	 * has no current element; its <I>cursor position</I> always
	 * lies between the element that would be returned by a call
	 * to {@code previous()} and the element that would be
	 * returned by a call to {@code next()}.
	 * <p> See also: {@link SubListAdapter}
	 * @author Manfè Alessandro 1201538
	 *
	 */
	protected class SubListAdapterIterator implements ListIterator<E>{
		/**
		 * Index of the next element called by {@code next}
		 */
		protected int index;
		/**
		 * Tells if {@code remove} can be called or not.
		 */
		protected boolean called;
		/**
		 * Reference to the last value returned.
		 */
		protected int last;
		
		/**
		 * Constructs a new ListIterator for the given SubList.
		 */
		SubListAdapterIterator(){
			index=from;
			called=false;
		}
		/**
		 * Constructs a new ListIterator over given Vector, starting form index i.
		 * @param i Starting offset index.
		 */
		SubListAdapterIterator(int offset) {
			index=from+offset;
			called=false;
		}
		/**
		 * Returns {@code true} if this sublist iterator has more elements when
		 * traversing the sublist in the forward direction. (In other words,
		 * returns {@code true} if {@link #next} would return an element rather
		 * than throwing an exception.)
		 * @return {@code true} if the sublist iterator has more elements when
		 *         traversing the sublist in the forward direction
		 */
		@Override
		public boolean hasNext() {
			return index<to;
		}
		/**
	     * Returns the next element in the sublist and advances the cursor position.
	     * This method may be called repeatedly to iterate through the sublist,
	     * or intermixed with calls to {@link #previous} to go back and forth.
	     * (Note that alternating calls to {@code next} and {@code previous}
	     * will return the same element repeatedly.)
	     * @return the next element in the sublist
	     * @throws NoSuchElementException if the iteration has no next element
	     */
		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException();
			called=true;
			last=index;
			return (E)subv.elementAt(index++);
		}
		/**
	     * Returns {@code true} if this sublist iterator has more elements when
	     * traversing the sublist in the reverse direction.  (In other words,
	     * returns {@code true} if {@link #previous} would return an element
	     * rather than throwing an exception.)
	     * @return {@code true} if the sublist iterator has more elements when
	     *         traversing the sublist in the reverse direction
	     */
		@Override
		public boolean hasPrevious() {
			return index>from;
		}
		/**
	     * Returns the previous element in the sublist and moves the cursor
	     * position backwards.  This method may be called repeatedly to
	     * iterate through the sublist backwards, or intermixed with calls to
	     * {@link #next} to go back and forth.  (Note that alternating calls
	     * to {@code next} and {@code previous} will return the same element repeatedly.)
	     * @return the previous element in the sublist
	     * @throws NoSuchElementException if the iteration has no previous
	     *         element
	     */
		@Override
		public E previous() {
			if(!hasPrevious()) throw new NoSuchElementException();
			called=true;
			last = --index;
			return (E)subv.elementAt(index);
		}
		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to {@link #next}. (Returns sublist size if the sublist
		 * iterator is at the end of the sublist.)
     	 * @return the index of the element that would be returned by a
     	 *         subsequent call to {@code next}, or sublist size if the sublist
     	 *         iterator is at the end of the sublist
		 */
		@Override
		public int nextIndex() {
			return index-from;
		}
		/**
	     * Returns the index of the element that would be returned by a
	     * subsequent call to {@link #previous}. (Returns -1 if the sublist
	     * iterator is at the beginning of the sublist.)
	     * @return the index of the element that would be returned by a
	     *         subsequent call to {@code previous}, or -1 if the sublist
	     *         iterator is at the beginning of the sublist
	     */
		@Override
		public int previousIndex() {
			return index-from-1;
		}
		/**
	     * Removes from the sublist the last element that was returned by {@link
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
			subv.removeElementAt(last);
			index=last;
			to--;
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
			subv.setElementAt(e, last);
		}
		/**
		 * Inserts the specified element into the sublist.
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
			subv.insertElementAt(e, index++);
			to++;
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
	/**
	 * Returns an iterator over the elements in this sublist in proper sequence.
	 * @return an iterator over the elements in this sublist
	 * @See {@link SubListAdapterIterator}
	 */
	@Override
	public Iterator<E> iterator() {
		return new SubListAdapterIterator();
	}
	/**
	 * Returns a list iterator over the elements in this sublist in proper sequence.
	 * @return a list iterator over the elements in this sublist
	 * @See {@link SubListAdapterIterator}
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new SubListAdapterIterator();
	}
	/**
	 * Returns a list iterator over the elements in this sublist (in proper sequence), 
	 * starting at the specified position in the sublist.
	 * @param index at which the iteration start
	 * @return a list iterator over the elements in this sublist
	 * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index > size()})
	 * @See {@link SubListAdapterIterator}
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		if(index<0||index>size()) throw new IndexOutOfBoundsException();
		return new SubListAdapterIterator(index);
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
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex > size() || fromIndex > toIndex) throw new IndexOutOfBoundsException();
		return new SubListAdapter<E>(subv,fromIndex+from,toIndex+from);
	}
	
}