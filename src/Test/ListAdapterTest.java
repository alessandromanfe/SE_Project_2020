/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;

import H2Adapter.ListAdapter;
import H2Adapter.SubListAdapter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.junit.jupiter.api.Test;



/**
 * @safe.summary Junit test suite for ListAdapter
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings({"unchecked","rawtypes"}) //Mettevano a dura prova il mio OCD
public class ListAdapterTest {
	
	/**
	 * Test for default constructor
	 * @safe.precondition none
	 * @safe.postcondition a new ListAdapter
	 * @safe.testcases whether a new empty ListAdapter instance is created
	 */
	@Test
	public void testListAdapter() {
		List<String> l = new ListAdapter<String>();
		assertTrue(l instanceof ListAdapter);
		assertTrue(l.isEmpty());
	}
	/**
	 * Test for Vector adaptee constructor
	 * @safe.precondition none
	 * @safe.postcondition a new ListAdapter
	 * @safe.testcases whether a ListAdapter instance wrapping specified Vector is created
	 */
	@Test
	public void testListAdapterVector() {
		Vector v = new Vector();
		v.add("Halo");
		List<String> l = new ListAdapter<String>(v);
		assertTrue(l instanceof ListAdapter);
		assertTrue(l.contains("Halo"));
	}
	/**
	 * Test for add method
	 * @safe.precondition empty ListAdapter 
	 * @safe.postcondition List Adapter contains new element
	 * @safe.testcases whether:	return true if element is added
	 * 							contains new element
	 */
	@Test
	public void testAdd() {
		List<String> l = new ListAdapter<String>();
		assertTrue(l.add("Doom"));
		assertTrue(l.contains("Doom"));
	}
	/**
	 * Test for add(index) method
	 * @safe.precondition filled ListAdapter 
	 * @safe.postcondition List Adapter contains new element
	 * @safe.testcases whether contains new element at specified index
	 */
	@Test
	public void testAddIndex() {
		List<String> l = new ListAdapter<String>();
		l.add("Diablo");
		l.add("Diablo 3");
		l.add(1,"Diablo 2");
		assertEquals(l.get(1),"Diablo 2");
		assertTrue(l.size()==3);
	}
	/**
	 * Test for addAll method
	 * @safe.precondition empty ListAdapter
	 * @safe.postcondition ListAdapter contains all new element
	 * @safe.testcases whether: return false when no element is added
	 * 							return True if list changes after insert
	 * 							throws nullpointer if called with null Collection
	 * 							contains all new element in order
	 */
	@Test
	public void testAddAll() {
		List<String> ll = new LinkedList<String>();
		List<String> l = new ListAdapter<String>();
		assertThrows(NullPointerException.class, () -> l.addAll(null));
		ll.add("GTA III");
		ll.add("GTA IV");
		assertTrue(l.addAll(ll));
		boolean b = l.indexOf("GTA III")==0&&l.indexOf("GTA IV")==1;
		assertTrue(b);
	}
	/**
	 * Test for addAll(index) method
	 * @safe.precondition empty ListAdapter
	 * @safe.postcondition ListAdapter contains all new element
	 * @safe.testcases whether: return false when no element is added
	 * 							return True if list changes after insert
	 * 							throws nullpointer if called with null Collection
	 * 							contains all new element in order from the index position
	 */
	@Test
	public void testAddAllIndex() {
		List<String> ll = new LinkedList<String>();
		List<String> l = new ListAdapter<String>();
		assertThrows(NullPointerException.class, () -> l.addAll(null));
		l.add("GTA SanAndreas");
		ll.add("GTA III");
		ll.add("GTA IV");
		assertTrue(l.addAll(1,ll));
		boolean b = l.contains("GTA III")&&l.contains("GTA IV");
		assertTrue(b);
		assertTrue(l.size()==3);
		assertEquals(l.get(1),"GTA III");
		assertEquals(l.get(2),"GTA IV");
	}
	/**
	 * Test for clear method
	 * @safe.precondition not empty ListAdapter
	 * @safe.postcondition empty ListAdapter
	 * @safe.testcases whether size==0 after method call
	 */
	@Test
	public void testClear() {
		List<String> l = new ListAdapter<String>();
		l.add("Warcraft");
		l.clear();
		assertTrue(l.size()==0);
	}
	/**
	 * Test for contains method
	 * @safe.precondition not empty ListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given element
	 * 							return true if contains given element
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testContains() {
		List<String> l = new ListAdapter<String>();
		l.add("Elemento");
		assertTrue(l.contains("Elemento"));
		assertFalse(l.contains(null));
		assertFalse(l.contains(new Integer(1)));
	}
	/**
	 * Test for containsAll method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains all element in specified Collection
	 * 							return true if contains all element in specified Collection
	 * 							throws nullpointer if given Collection is null
	 */
	@Test
	public void testContainsAll() {
		List<String> l = new ListAdapter<String>();
		l.add("Uno");
		l.add("Due"); 
		l.add("Tre");
		assertThrows(NullPointerException.class, () -> l.containsAll(null));
		List<String> ll=new LinkedList<String>();
		l.add("Uno");
		l.add("Due");
		assertTrue(l.containsAll(ll));
	}
	/**
	 * Test for equals method
	 * @safe.precondition filled ListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if called with empty or different lsit
	 * 							return true if called with a list that contains the same elements in same order
	 * 							return true if called with the set itself
	 * 							return false if called with another Object or null
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		List<String> l1 = new ListAdapter<String>();
		List<String> l2 = new ListAdapter<String>();
		l1.add("Hello"); l1.add("World");
		l2.add("Hello"); l2.add("World");
		assertTrue(l1.equals(l2));
		assertFalse(l1.equals("Definetly not a List"));
		l2.remove("Hello"); l2.add("Hello");
		assertFalse(l1.equals(l2));
		assertFalse(l1.equals(null));
	}
	/**
	 * Test for get method
	 * @safe.precondition filled ListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether:	return element at specified index
	 * 							throws indexOutOfBounds if index is not valid
	 */
	@Test
	public void testGet() {
		List<String> l = new ListAdapter<String>();
		l.add("Skyrim");
		assertEquals(l.get(0),"Skyrim");
		assertThrows(IndexOutOfBoundsException.class,() -> l.get(2));
		assertThrows(IndexOutOfBoundsException.class,() -> l.get(-1));
	}
	/**
	 * Test for hashCode method
	 * @safe.precondition filled ListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether hashCode respects how it's defined for list 
	 */
	@Test
	public void tastHashCode() {
		List<String> l = new ListAdapter<String>();
		List<String> ll = new LinkedList<String>();
		l.add("Hello"); l.add("World");
		ll.add("Hello"); ll.add("World");
		assertEquals(l.hashCode(),ll.hashCode());
	}
	/**
	 * Test for indexOf method
	 * @safe.precondition filled ListAdapter 
	 * @safe.postcondition none
	 * @safe.testcases whether: return the index of the specified element in the list
	 * 							return <tt>-1</tt> if element is not contained 
	 */
	@Test
	public void testIndexOf() {
		List<String> l = new ListAdapter<String>();
		l.add("AAA");
		l.add("BBB");
		l.add("AAA");
		boolean b=(l.indexOf("AAA")==0)&&(l.indexOf("BBB")==1);
		assertTrue(b);
		assertTrue(l.indexOf("CCC")==-1);
	}
	/**
	 * Test for isEmpty method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return true only when it's empty
	 */
	@Test
	public void testIsEmpty() {
		List<String> l = new ListAdapter<String>();
		l.add("Squacquerone");
		assertFalse(l.isEmpty());
		l.clear();
		assertTrue(l.isEmpty());
	}
	/**
	 * Test for remove(int) method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition removed element
	 * @safe.testcases whether:	return the element just removed
	 * 							no longer contains the element
	 * 							throws indexOutOfBounds if index is invalid
	 */
	@Test
	public void testRemoveInt() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2");
		assertEquals("1",l.remove(1));
		assertTrue(l.size()==2&&(!l.contains("1")));
		assertThrows(IndexOutOfBoundsException.class,() -> l.remove(4));
		assertThrows(IndexOutOfBoundsException.class,() -> l.remove(-1));
	}
	/**
	 * Test for remove(Object) method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition removed element
	 * @safe.testcases whether:	return true if the element is removed
	 * 							return false if list doesn't contain element
	 * 							no longer contains the element
	 */
	@Test
	public void testRemoveObject() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2");
		l.remove("1");
		assertTrue(l.size()==2&&(!l.contains("1")));
	}
	/**
	 * Test for removeAll method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition all common elements removed 
	 * @safe.testcases whether: throws nullpointer if called with null
	 * 							return false if list is not modified
	 * 							return true if list is modified
	 * 							no longer contains the common elements
	 */
	@Test
	public void testRemoveAll() {
		List<String> l1 = new ListAdapter<String>();
		l1.add("Qui"); l1.add("Quo"); l1.add("Qua"); l1.add("Quo");
		assertThrows(NullPointerException.class,() -> l1.removeAll(null));
		List<String> l2 = new ListAdapter<String>();
		l2.add("Qui"); l2.add("Quo");
		assertTrue(l1.removeAll(l2));
		assertTrue(l1.size()==1&&l1.contains("Qua"));
	}
	/**
	 * Test for retainAll method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition all non common elements removed
	 * @safe.testcases whether:	throws nullpointer if called with null
	 * 							return false if list is not modified
	 * 							return true if list is modified
	 * 							no longer contains the non common elements
	 */
	@Test
	public void testRetainAll() {
		List<String> l1 = new ListAdapter<String>();
		l1.add("Cip"); l1.add("Ciop"); l1.add("Cip");
		assertThrows(NullPointerException.class,() -> l1.retainAll(null));
		List<String> l2 = new ListAdapter<String>();
		l2.add("Ciop");
		assertTrue(l1.retainAll(l2));
		assertTrue(l1.size()==1&&l1.contains("Ciop"));
	}
	/**
	 * Test for set method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition element modified
	 * @safe.testcases whether: return previous element
	 * 							element has actually been modified
	 * 							throws indexOutOfBounds if index is invalid
	 * 							
	 */
	@Test
	public void testSet() {
		List<String> l = new ListAdapter<String>();
		l.add("Albano");
		assertEquals(l.set(0, "Dinosauri"),"Albano");
		assertEquals(l.get(0),"Dinosauri");
		assertTrue(l.size()==1);
		assertThrows(IndexOutOfBoundsException.class,() -> l.set(2,null));
		assertThrows(IndexOutOfBoundsException.class,() -> l.set(-1,null));
	}
	/**
	 * Test for size method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the list
	 */
	@Test
	public void testSize() {
		List<String> l = new ListAdapter<String>();
		l.add("Uno"); l.add("Due");
		assertTrue(l.size()==2);
		l.add("Tre");
		assertTrue(l.size()==3);
	}
	/**
	 * Test for toArray method
	 * @safe.precondition filled listAdapter
	 * @safe.postcondition array of list elements 
	 * @safe.testcases whether: the array contains the right elements in order
	 * 							referencing works
	 */
	@Test
	public void testToArray() {
		List<String> l = new ListAdapter<String>();
		List<Object> ll = new ListAdapter<Object>();
		l.add("MacOS");
		ll.add(l);
		ll.add("oooo");
		ll.add("meringhe");
		Object[] arr = ll.toArray();
		boolean b=true;
		for(int i=0; i<arr.length; i++) b&=ll.get(i).equals(arr[i]);
		assertTrue(b);
		((ListAdapter)arr[0]).add("Linux");
		assertTrue(l.contains("Linux")&&l.size()==2);
		assertTrue(arr.length==3);
		
	}
	/**
	 * Test for lastIndexOf method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether: return the index of the last occurrence of specified element if exist
	 * 							return <tt>-1</tt> if element does not exist
	 */
	@Test
	public void testLastIndexOf() {
		List<String> l = new ListAdapter<String>();
		l.add("AAA");
		l.add("BBB");
		l.add("AAA");
		boolean b=(l.lastIndexOf("AAA")==2)&&(l.lastIndexOf("BBB")==1);
		assertTrue(b);
		assertTrue(l.lastIndexOf("CCC")==-1);
	}
	/**
	 * Test for iterator constructor
	 * @safe.precondition empty listAdapter
	 * @safe.postcondition new iterator instance 
	 * @safe.testcases whether a new instance of iterator is created 
	 */
	@Test
	public void testIt_ListAdapterIterator() {
		List<String> l = new ListAdapter<String>();
		Iterator<String> it = l.listIterator();
		assertTrue(it instanceof ListIterator);
		assertFalse(it.hasNext());
	}
	/**
	 * Test for iterator index constructor
	 * @safe.precondition empty listAdapter
	 * @safe.postcondition new iterator instance 
	 * @safe.testcases whether a new instance of iterator is created starting from the index
	 */
	@Test
	public void testIt_ListAdapterIteratorIndex() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2"); 
		Iterator<String> it = l.listIterator(1);
		assertTrue(it instanceof ListIterator);
		assertTrue(it.hasNext());
		assertEquals(it.next(),"1");
	}
	/**
	 * Test for hasNext iterator method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return true only if iteration has more elements
	 */
	@Test
	public void testIt_HasNext() {
		List<String> l = new ListAdapter<String>();
		l.add("Buh");
		Iterator<String> it = l.iterator();
		assertTrue(it.hasNext());
		it.next();
		assertFalse(it.hasNext());
	}
	/**
	 * Test for next iterator method
	 * @safe.precondition empty ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether: return the correct element
	 * 							throws nosuchelement if it's at the end of the iteration
	 */
	@Test
	public void testIt_Next() {
		List<String> l = new ListAdapter<String>();
		Iterator<String> iter = l.iterator();
		assertThrows(NoSuchElementException.class,() -> iter.next());
		l.add("Gabe Newell");
		Iterator<String> it = l.iterator();
		assertEquals(it.next(),"Gabe Newell");
		assertThrows(NoSuchElementException.class,() -> it.next());
	}
	/**
	 * Test for hasPrevious iterator method
	 * @safe.precondition empty ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return true only if backwards iteration has more elements
	 */
	@Test
	public void testIt_HasPrevious() {
		List<String> l = new ListAdapter<String>();
		ListIterator<String> it = l.listIterator();
		assertFalse(it.hasPrevious());
		l.add("Buh");
		it=l.listIterator();
		it.next();
		assertTrue(it.hasPrevious());
	}
	/**
	 * Test for previous iterator method
	 * @safe.precondition empty ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether: return the correct element
	 * 							throws nosuchelement if it's at the beginning of the iteration
	 */
	@Test
	public void testIt_Previous() {
		List<String> l = new ListAdapter<String>();
		ListIterator<String> iter = l.listIterator();
		assertThrows(NoSuchElementException.class,() -> iter.previous());
		l.add("Buh");
		ListIterator<String> it=l.listIterator();
		it.next();
		assertEquals(it.previous(),"Buh");
		assertThrows(NoSuchElementException.class,() -> it.previous());
	}
	/**
	 * Test for nextIndex iterator method
	 * @safe.precondition empty ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct index, or size() if iterator is at the end of the list
	 * 							
	 */
	@Test
	public void testIt_NextIndex() {
		List<String> l = new ListAdapter<String>();
		ListIterator<String> it=l.listIterator();
		assertTrue(it.nextIndex()==0);
		l.add("0"); l.add("1"); l.add("2");
		it=l.listIterator(2);
		assertEquals(it.nextIndex(),2);
		assertEquals(it.next(),l.get(2));
		assertEquals(it.nextIndex(),3);		
	}
	/**
	 * Test for previousIndex iterator method
	 * @safe.precondition empty ListAdapter
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct index, or <tt>-1</tt> if iterator is at the beginning of the list
	 * 							
	 */
	@Test
	public void testIt_PreviousIndex() {
		List<String> l = new ListAdapter<String>();
		ListIterator<String> it=l.listIterator();
		assertTrue(it.previousIndex()==-1);
		l.add("0"); l.add("1"); l.add("2");
		it=l.listIterator(1);
		assertEquals(it.previousIndex(),0);
		assertEquals(it.previous(),l.get(0));
		assertEquals(it.previousIndex(),-1);
	}
	/**
	 * Test for remove iterator method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition element removed
	 * @safe.testcases whether: throws illegalstate if next or previous has not been called yet
	 * 							remove correct elements
	 * 							list contains removed elements 
	 */
	@Test
	public void testIt_Remove() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2");
		ListIterator<String> it=l.listIterator();
		assertThrows(IllegalStateException.class,() -> it.remove());
		it.next(); it.next();
		it.remove();
		assertFalse(l.contains("1"));
		assertThrows(IllegalStateException.class,() -> it.remove());
		it.previous();
		it.remove();
		assertFalse(l.contains("0"));
		assertThrows(IllegalStateException.class,() -> it.remove());
		
	}
	/**
	 * Test for set iterator method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition element modified
	 * @safe.testcases whether: throws illegalstate if next or previous has not been called yet
	 * 							element has actually been modified 
	 */
	@Test
	public void testIt_Set() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2");
		final ListIterator<String> it=l.listIterator();
		assertThrows(IllegalStateException.class,() -> it.set("8"));
		it.next(); it.next();
		it.set("8");
		assertEquals(l.get(1),"8");
		it.add("9");
		assertThrows(IllegalStateException.class,() -> it.set("8"));
		it.previous();
		it.set("7");
		assertEquals(l.get(2),"7");
		it.remove();
		assertThrows(IllegalStateException.class,() -> it.set("8"));
	}
	/**
	 * Test for add iterator method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition new element added
	 * @safe.testcases whether: element has actually been added at the right position
	 * 							list contains the element
	 * 							
	 */
	@Test
	public void testIt_Add() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2");
		ListIterator<String> it=l.listIterator();
		it.next();
		it.add("3");
		assertEquals(it.previous(),"3");
		assertEquals(l.get(1),"3");
		assertTrue(l.size()==4);
	}
	/**
	 * Test for iterator method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition instance of iterator
	 * @safe.testcases whether method initialize correctly an iterator over the list
	 */
	@Test
	public void testIterator() {
		List<String> l = new ListAdapter<String>();
		l.add("1"); l.add("2"); l.add("3");
		Iterator<String> it = l.iterator();
		assertTrue(it instanceof ListIterator);
		List<String> ll = new ListAdapter<String>();
		while(it.hasNext()) ll.add(it.next());
		assertEquals(ll,l);
	}
	/**
	 * Test for listIterator method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition instance of listIiterator
	 * @safe.testcases whether method initialize correctly a listIterator over the list
	 */
	@Test
	public void testListIterator() {
		List<String> l = new ListAdapter<String>();
		l.add("1"); l.add("2"); l.add("3");
		ListIterator<String> it=l.listIterator();
		assertTrue(it instanceof ListIterator);
		List<String> ll = new ListAdapter<String>();
		while(it.hasNext()) ll.add(it.next());
		assertEquals(ll,l);
	}
	/**
	 * Test for listIterator(index) method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition instance of listIterator
	 * @safe.testcases whether method initialize correctly a listIterator 
	 * 				   over the list from the specified index
	 */
	@Test
	public void testListIteratorIndex() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2");
		ListIterator<String> it=l.listIterator(1);
		assertTrue(it instanceof ListIterator);
		List<String> ll = new ListAdapter<String>();
		while(it.hasNext()) ll.add(it.next());
		l.remove(0);
		assertEquals(ll,l);
	}
	/**
	 * Test for subList method
	 * @safe.precondition filled ListAdapter
	 * @safe.postcondition instance of subList
	 * @safe.testcases whether: method initialize correctly a subList 
	 * 				   			list and sublist are actually backed
	 */
	@Test
	public void testSubList() {
		List<String> l = new ListAdapter<String>();
		l.add("0"); l.add("1"); l.add("2");
		List<String> subl = l.subList(1,2);
		assertTrue(subl instanceof SubListAdapter);
		assertTrue(l.containsAll(subl)&&subl.size()==1);
	}
}
