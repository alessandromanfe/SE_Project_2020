/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;

import H2Adapter.ListAdapter;
import H2Adapter.SubListAdapter;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @safe.summary Junit test suite for SubListAdapter
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings("rawtypes") //Mettevano a dura prova il mio OCD
public class SubListAdapterTest {
	List<String> l;
	List<String> subl;
	
	@BeforeEach
	public void setup() {
		l = new ListAdapter<String>();
		l.add("Uno"); l.add("Due"); l.add("Tre"); l.add("Quattro");
		subl = l.subList(1,3);
	}
	/**
	 * Test for default constructor
	 * @safe.precondition none
	 * @safe.postcondition a new SubListAdapter
	 * @safe.testcases whether: a new empty SubListAdapter instance is created
	 * 							it's a view of the list
	 */
	@Test
	public void testSubListAdapter() {
		assertTrue(subl instanceof SubListAdapter);
		assertTrue(subl!=null);
		assertTrue(l.containsAll(subl));
	}
	/**
	 * Test for size method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the sublist
	 */
	@Test
	public void testSize() {
		assertTrue(subl.size()==2);
		subl.remove(0);
		assertTrue(subl.size()==1);
		assertTrue(l.size()==3);
	}
	/**
	 * Test for isEmpty method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return true only when it's empty
	 */
	@Test
	public void testIsEmpty() {
		assertFalse(subl.isEmpty());
		subl.clear();
		assertTrue(subl.isEmpty());
		subl.add("Falcon 9");
		assertFalse(subl.isEmpty());
	}
	/**
	 * Test for contains method
	 * @safe.precondition filled SubListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given element
	 * 							return true if contains given element
	 */
	@Test
	public void testContains() {
		assertTrue(subl.contains("Due"));
		assertFalse(subl.contains("Uno"));
		subl.remove(0);
		assertFalse(subl.contains("Due"));
	}
	/**
	 * Test for toArray method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition array of sublist elements 
	 * @safe.testcases whether: the array contains the right elements in order
	 * 							referencing works
	 */
	@Test
	public void testToArray() {
		List<List<String>> la =new ListAdapter<List<String>>();
		la.add(l);
		List<List<String>>sla=la.subList(0, 1);
		Object[] arr = sla.toArray();
		assertTrue(((List)arr[0]).contains("Tre"));
		((List)arr[0]).remove("Tre");
		assertFalse(l.contains("Tre"));
	}
	/**
	 * Test for add method
	 * @safe.precondition filled SubListAdapter 
	 * @safe.postcondition sublist and list contain new element
	 * @safe.testcases whether:	return true if element is added
	 * 							sublist and list contain new element
	 */
	@Test
	public void testAdd() {
		assertTrue(subl.add("Demo 2"));
		assertTrue(l.contains("Demo 2"));
		assertEquals(l.get(3),"Demo 2");
	}
	/**
	 * Test for remove(Object) method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition removed element
	 * @safe.testcases whether:	return true if element is removed
	 * 							no longer contains the element
	 * 							return false is does not contains element
	 */
	@Test
	public void testRemove() {
		assertThrows(IndexOutOfBoundsException.class,() -> subl.remove(5));
		assertThrows(IndexOutOfBoundsException.class,() -> subl.remove(-1));
		assertEquals(subl.remove(0),"Due");
		assertFalse(l.contains("Due"));
	}
	/**
	 * Test for containsAll method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains all element in specified Collection
	 * 							return true if contains all element in specified Collection
	 * 							throws nullpointer if given Collection is null
	 */
	@Test
	public void testContainsAll() {
		assertThrows(NullPointerException.class,() -> subl.removeAll(null));
		List<String> temp = new ListAdapter<String>();
		temp.add("Uno"); temp.add("Due");
		assertFalse(subl.containsAll(temp));
		temp.remove("Uno"); temp.add("Tre");
		assertTrue(subl.containsAll(temp));
		assertTrue(subl.containsAll(new ListAdapter<String>()));
	}
	/**
	 * Test for addAll method
	 * @safe.precondition empty SubListAdapter
	 * @safe.postcondition ListAdapter contains all new element
	 * @safe.testcases whether: return false when no element is added
	 * 							return True if sublist and lsit change after insert
	 * 							throws nullpointer if called with null Collection
	 * 							list and sublist contain all new element in order
	 */
	@Test
	public void testAddAll() {
		assertThrows(NullPointerException.class,() -> subl.addAll(null));
		List<String> temp = new ListAdapter<String>();
		temp.add("Cinque"); temp.add("Sei");
		assertTrue(subl.addAll(temp));
		assertTrue(l.contains("Cinque")&&l.contains("Sei"));
		assertTrue(l.size()==6&&l.get(3).equals("Cinque")&&l.get(4).equals("Sei"));
	}
	/**
	 * Test for addAll(index) method
	 * @safe.precondition empty SubListAdapter
	 * @safe.postcondition SubListAdapter contains all new element
	 * @safe.testcases whether: return false when no element is added
	 * 							return True if sublist and list change after insert
	 * 							throws nullpointer if called with null Collection
	 * 							sublist and list contain all new element in order from the index position
	 */
	@Test
	public void testAddAllInedx() {
		assertThrows(NullPointerException.class,() -> subl.addAll(0,null));
		List<String> temp = new ListAdapter<String>();
		temp.add("Cinque"); temp.add("Sei");
		assertTrue(subl.addAll(0,temp));
		assertTrue(l.contains("Cinque")&&l.contains("Sei"));
		assertTrue(l.size()==6&&l.get(1).equals("Cinque")&&l.get(2).equals("Sei"));
	}
	/**
	 * Test for removeAll method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition all common elements removed 
	 * @safe.testcases whether: throws nullpointer if called with null
	 * 							return false if sublist is not modified
	 * 							return true if sublist is modified
	 * 							no longer contains the common elements
	 */
	@Test
	public void testRemoveAll() {
		assertThrows(NullPointerException.class,() -> subl.addAll(null));
		List<String> temp = new ListAdapter<String>();
		temp.add("Tre"); temp.add("Due");
		subl.add("Due"); subl.add("Otto");
		assertTrue(subl.removeAll(temp));
		assertTrue(l.size()==3);
		assertTrue(l.contains("Otto"));
		assertFalse(l.contains("Due")||subl.contains("Tre"));
	}
	/**
	 * Test for retainAll method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition all non common elements removed
	 * @safe.testcases whether:	throws nullpointer if called with null
	 * 							return false if sublist is not modified
	 * 							return true if sublist is modified
	 * 							no longer contains the non common elements
	 */
	@Test
	public void testRetainAll() {
		assertThrows(NullPointerException.class,() -> subl.retainAll(null));
		List<String> temp = new ListAdapter<String>();
		temp.add("Tre"); temp.add("Due");
		subl.add("Due"); subl.add("Otto");
		assertTrue(subl.retainAll(temp));
		assertTrue(l.size()==5);
		assertTrue(l.contains("Due"));
		assertFalse(l.contains("Otto"));
	}
	/**
	 * Test for clear method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether size==0 after method call
	 */
	@Test
	public void testClear() {
		subl.clear();
		assertTrue(subl.size()==0);
		assertTrue(l.size()==2);
	}
	/**
	 * Test for get method
	 * @safe.precondition filled SubListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether:	return element at specified index
	 * 							throws indexOutOfBounds if index is not valid
	 */
	@Test
	public void testGet() {
		assertThrows(IndexOutOfBoundsException.class,() -> subl.get(-1));
		assertThrows(IndexOutOfBoundsException.class,() -> subl.get(5));
		assertEquals(subl.get(1),l.get(2));
	}
	/**
	 * Test for set method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition element modified
	 * @safe.testcases whether: return previous element
	 * 							element has actually been modified
	 * 							throws indexOutOfBounds if index is invalid
	 * 							
	 */
	@Test
	public void testSet() {
		assertThrows(IndexOutOfBoundsException.class,() -> subl.set(-1,null));
		assertThrows(IndexOutOfBoundsException.class,() -> subl.set(5,null));
		assertEquals(subl.set(1, "Elon Musk"),"Tre");
		assertEquals(l.get(2),"Elon Musk");
		
	}
	/**
	 * Test for add(index) method
	 * @safe.precondition filled SubListAdapter 
	 * @safe.postcondition SunListAdapter contains new element
	 * @safe.testcases whether contains new element at specified index
	 */
	@Test
	public void testAddIndex() {
		assertThrows(IndexOutOfBoundsException.class,() -> subl.add(-1,null));
		assertThrows(IndexOutOfBoundsException.class,() -> subl.add(4,null));
		subl.add(1,"Saturn V");
		assertTrue(l.size()==5);
		assertEquals(l.get(2),"Saturn V");
	}
	/**
	 * Test for remove(index) method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition removed element
	 * @safe.testcases whether:	return the element just removed
	 * 							no longer contains the element
	 * 							throws indexOutOfBounds if index is invalid
	 */
	@Test
	public void testRemoveIndex() {
		assertThrows(IndexOutOfBoundsException.class,() -> subl.remove(-1));
		assertThrows(IndexOutOfBoundsException.class,() -> subl.remove(6));
		assertEquals(subl.remove(1),"Tre");
		assertTrue(l.size()==3);
	}
	/**
	 * Test for indexOf method
	 * @safe.precondition filled SubListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether: return the index of the specified element in the sublist
	 * 							return <tt>-1</tt> if element is not contained 
	 */
	@Test
	public void testIndexOf() {
		assertTrue(subl.indexOf("Quattro")==-1);
		assertTrue(subl.indexOf("Due")==0);
		assertTrue(subl.indexOf("Trentasei")==-1);
	}
	/**
	 * Test for lastIndexOf method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether: return the index of the last occurrence of specified element if exist
	 * 							return <tt>-1</tt> if element does not exist
	 */
	@Test
	public void testLastIndexOf() {
		subl.add(2,"Due");
		assertTrue(subl.lastIndexOf("Ventordici")==-1);
		assertTrue(subl.lastIndexOf("Due")==2);
	}
	/**
	 * Test for subList method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition instance of subList
	 * @safe.testcases whether: method initialize correctly a subList 
	 * 				   			list and sublist are actually backed
	 */
	@Test
	public void testSubList() {
		List<String> temp = subl.subList(1,2);
		assertTrue(temp.size()==1);
		assertTrue(temp.contains("Tre"));
	}
	/**
	 * Test for hasNext iterator method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return true only if iteration has more elements
	 */
	@Test
	public void testIt_HasNext() {
		Iterator<String> it = subl.iterator();
		assertTrue(it.hasNext());
		subl.clear(); 
		it = subl.iterator();
		assertFalse(it.hasNext());
	}
	/**
	 * Test for next iterator method
	 * @safe.precondition empty SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether: return the correct element
	 * 							throws nosuchelement if it's at the end of the iteration
	 */
	@Test
	public void testIt_Next() {
		Iterator<String> it = subl.iterator();
		assertEquals(it.next(),"Due");
		subl.clear(); 
		Iterator<String> iter = subl.iterator();
		assertThrows(NoSuchElementException.class,() -> iter.next());
	}
	/**
	 * Test for hasPrevious iterator method
	 * @safe.precondition empty SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return true only if backwards iteration has more elements
	 */
	@Test
	public void testIt_HasPrevious() {
		ListIterator<String> it = subl.listIterator();
		assertFalse(it.hasPrevious());
		it.next();
		assertTrue(it.hasPrevious());
	}
	/**
	 * Test for previous iterator method
	 * @safe.precondition empty SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether: return the correct element
	 * 							throws nosuchelement if it's at the beginning of the iteration
	 */
	@Test
	public void testIt_Previous() {
		ListIterator<String> it = subl.listIterator();
		assertThrows(NoSuchElementException.class,() -> it.previous());
		it.next();
		assertEquals(it.previous(),"Due");
	}
	/**
	 * Test for nextIndex iterator method
	 * @safe.precondition empty SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct index, or size() if iterator is at the end of the sublist
	 * 							
	 */
	@Test
	public void testIt_NextIndex() {
		ListIterator<String> it = subl.listIterator();
		assertTrue(it.nextIndex()==0);
		it.next(); it.next();
		assertTrue(it.nextIndex()==2);
	}
	/**
	 * Test for previousIndex iterator method
	 * @safe.precondition empty SubListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct index, or <tt>-1</tt> if iterator is at the beginning of the sublist
	 * 							
	 */
	@Test
	public void testIt_PreviousIndex() {
		ListIterator<String> it = subl.listIterator();
		assertTrue(it.previousIndex()==-1);
		it.next(); it.next();
		assertTrue(it.previousIndex()==1);
	}
	/**
	 * Test for remove iterator method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition element removed
	 * @safe.testcases whether: throws illegalstate if next or previous has not been called yet
	 * 							remove correct elements
	 * 							sublist contains removed elements 
	 */
	@Test
	public void testIt_Remove() {
		ListIterator<String> it = subl.listIterator();
		assertThrows(IllegalStateException.class,() -> it.remove());
		it.next(); it.next();
		it.remove();
		assertFalse(subl.contains("tre"));
		assertThrows(IllegalStateException.class,() -> it.remove());
		it.previous();
		it.remove();
		assertTrue(subl.isEmpty());
		assertThrows(IllegalStateException.class,() -> it.remove());
		it.add(null);
		assertThrows(IllegalStateException.class,() -> it.remove());
	}
	/**
	 * Test for set iterator method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition element modified
	 * @safe.testcases whether: throws illegalstate if next or previous has not been called yet
	 * 							element has actually been modified 
	 */
	@Test
	public void testIt_Set() {
		ListIterator<String> it = subl.listIterator();
		assertThrows(IllegalStateException.class,() -> it.set(null));
		it.next();
		it.set(null);
		assertEquals(subl.get(0),null);
		it.next();
		it.remove();
		assertThrows(IllegalStateException.class,() -> it.set(null));
	}
	/**
	 * Test for add iterator method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition new element added
	 * @safe.testcases whether: element has actually been added at the right position
	 * 							sublist contains the element
	 * 							
	 */
	@Test
	public void testIt_Add() {
		ListIterator<String> it = subl.listIterator();
		it.next(); it.next();
		it.add("Obama");
		assertTrue(l.size()==5);
		assertEquals(l.get(3),"Obama");
	}
	/**
	 * Test for iterator method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition instance of iterator
	 * @safe.testcases whether method initialize correctly an iterator over the sublist
	 */
	@Test
	public void testIterator() {
		Iterator<String> it = subl.iterator();
		assertTrue(it instanceof ListIterator);
		assertTrue(it!=null);
		List<String> ll = new ListAdapter<String>();
		while(it.hasNext()) ll.add(it.next());
		assertEquals(ll,subl);
	}
	/**
	 * Test for listIterator method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition instance of listIiterator
	 * @safe.testcases whether method initialize correctly a listIterator over the sublist
	 */
	@Test
	public void testListIterator() {
		ListIterator<String> it = subl.listIterator();
		assertTrue(it instanceof ListIterator);
		assertTrue(it!=null);
		List<String> ll = new ListAdapter<String>();
		while(it.hasNext()) ll.add(it.next());
		assertEquals(ll,subl);
	}
	/**
	 * Test for listIterator(index) method
	 * @safe.precondition filled SubListAdapter
	 * @safe.postcondition instance of listIterator
	 * @safe.testcases whether method initialize correctly a listIterator 
	 * 				   over the sublist from the specified index
	 */
	@Test
	public void testListIteratorInt() {
		ListIterator<String> it = subl.listIterator(1);
		assertTrue(it instanceof ListIterator);
		assertTrue(it!=null);
		List<String> ll = new ListAdapter<String>();
		while(it.hasNext()) ll.add(it.next());
		subl.remove(0);
		assertEquals(ll,subl);
		
	}
}
