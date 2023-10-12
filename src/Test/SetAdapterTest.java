/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;

import H2Adapter.ListAdapter;
import H2Adapter.SetAdapter;

import org.junit.jupiter.api.Test;

/**
 * @safe.summary Junit test suite for SetAdapter
 * @author Manfè Alessandro 1201538
 *
 */
@SuppressWarnings({"unchecked","rawtypes"}) //Mettevano a dura prova il mio OCD
public class SetAdapterTest {
	SetAdapter<String> sa;
	
	@BeforeEach
	public void setup() {
		sa = new SetAdapter<String>();
		sa.add("1"); sa.add("2"); sa.add("3"); sa.add("4");
	}
	/**
	 * Test for hashCode method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition none
	 * @safe.testcases whether hashCode return the sum of element's hashCode
	 */
	@Test
	public void testHashCode() {
		int hash="1".hashCode()+"2".hashCode()+"3".hashCode()+"4".hashCode();
		assertEquals(hash,sa.hashCode());
	}
	/**
	 * Test for default constructor
	 * @safe.precondition none
	 * @safe.postcondition a new SetAdapter
	 * @safe.testcases whether a new empty SetAdapter instance is created
	 */
	@Test
	public void testSetAdapter() {
		sa = new SetAdapter<String>();
		assertTrue(sa instanceof SetAdapter);
		assertTrue(sa.isEmpty());
	}
	/**
	 * Test for Hashtable adaptee constructor
	 * @safe.precondition none
	 * @safe.postcondition a new SetAdapter
	 * @safe.testcases whether a SetAdapter instance wrapping specified Hashtable is created
	 */
	@Test
	public void testSetAdapterHashtable() {
		Hashtable hs = new Hashtable();
		hs.put("Chiave","Valore");
		sa = new SetAdapter<String>(hs);
		assertTrue(sa instanceof SetAdapter);
		assertTrue(sa.contains("Valore"));
	}
	/**
	 * Test for add method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition SetAdapter contains new element
	 * @safe.testcases whether:	return true if element is added
	 * 							return false when no element is added
	 * 							throws nullpointer if called with null element
	 * 							contains new element
	 */
	@Test
	public void testAdd() {
		assertTrue(sa.add("sa.add()"));
		assertTrue(sa.contains("sa.add()"));
		assertFalse(sa.add("2"));
		assertThrows(NullPointerException.class,() -> sa.add(null));
	}
	/**
	 * Test for addAll method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition SetAdapter contains all new element
	 * @safe.testcases whether: return false when no element is added
	 * 							return True if set changes after insert
	 * 							throws nullpointer if called with null Collection, or contains null element
	 * 							contains all new element
	 */
	@Test
	public void testAddAll() {
		assertThrows(NullPointerException.class,() -> sa.addAll(null));
		List<String> ll = new ListAdapter<String>();
		ll.add("2"); ll.add("3");
		assertFalse(sa.addAll(ll));
		ll.add(null);
		assertThrows(NullPointerException.class,() -> sa.addAll(ll));
		assertFalse(sa.contains("5"));
		ll.remove(null); ll.add("5"); ll.add("0");
		assertTrue(sa.addAll(ll));
		assertTrue(sa.contains("5")&&sa.contains("0"));
		
	}
	/**
	 * Test for clear method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition empty SetAdapter
	 * @safe.testcases whether size==0 after method call
	 */
	@Test
	public void testClear() {
		sa.clear();
		assertTrue(sa.size()==0);
	}
	/**
	 * Test for contains method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given element
	 * 							return true if contains given element
	 * 							throws nullpointer if called with null parameter
	 */
	@Test
	public void testContains() {
		assertTrue(sa.contains("1"));
		assertFalse(sa.contains("8"));
		assertThrows(NullPointerException.class,() -> sa.contains(null));
	}
	/**
	 * Test for containsAll method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains all element in specified Collection
	 * 							return true if contains all element in specified Collection
	 * 							throws nullpointer if given Collection is null or contains null elements
	 */
	@Test
	public void testContainsAll() {
		assertThrows(NullPointerException.class,() -> sa.containsAll(null));
		List<String> ll = new ListAdapter<String>();
		assertTrue(sa.containsAll(ll));
		ll.add("3"); ll.add("2");
		assertTrue(sa.containsAll(ll));
		ll.add("7");
		assertFalse(sa.containsAll(ll));
		ll.add(null);
		assertThrows(NullPointerException.class,() -> sa.containsAll(ll));
		assertThrows(NullPointerException.class,() -> sa.containsAll(null));	
	}
	/**
	 * Test for equals method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if called with empty or different set
	 * 							return true if called with a set that contains the same elements
	 * 							return true if called with the set itself
	 * 							return false if called with another Object or null
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		SetAdapter<String> temp = new SetAdapter<String>();
		assertFalse(sa.equals(temp));
		temp.add("1"); temp.add("2"); temp.add("3"); temp.add("4");
		assertTrue(sa.equals(temp));
		assertTrue(sa.equals(sa));
		assertFalse(sa.equals("Hei, i'm not a SetAdapter, Cap' e provola"));
		assertFalse(sa.equals(null));
	}
	/**
	 * Test for isEmpty method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition empty SetAdapter
	 * @safe.testcases whether return true only when it's empty
	 */
	@Test
	public void testIsEmpty() {
		assertFalse(sa.isEmpty());
		sa.clear();
		assertTrue(sa.isEmpty());
	}
	/**
	 * Test for iterator method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition instance of iterator
	 * @safe.testcases whether method initialize correctly an iterator  
	 */
	@Test
	public void testIterator() {
		Iterator<String> it = sa.iterator();
		assertTrue(it instanceof Iterator);
		Set<String> s = new SetAdapter<String>();
		while(it.hasNext()) s.add(it.next());
		assertEquals(sa,s);
	}
	/**
	 * Test for remove method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition element removed
	 * @safe.testcases whether:	throws nullpointer if called with null 
	 * 							return true if elements is correctly removed
	 * 							set no longer contains element
	 * 							return false if there's an attempt to remove an object that is not contained
	 */
	@Test
	public void testRemove() {
		assertThrows(NullPointerException.class,() -> sa.remove(null));
		assertTrue(sa.remove("3"));
		assertFalse(sa.contains("3"));
		assertFalse(sa.remove("3"));
	}
	/**
	 * Test for removeAll method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition all common elements removed 
	 * @safe.testcases whether: throws nullpointer if called with null
	 * 							return false if set is not modified
	 * 							return true if set is modified
	 * 							no longer contains the common elements
	 */
	@Test
	public void testRemoveAll() {
		assertThrows(NullPointerException.class,() -> sa.removeAll(null));
		List<String> ll = new ListAdapter<String>();
		assertFalse(sa.removeAll(ll));
		ll.add("1"); ll.add("3");
		assertTrue(sa.removeAll(ll));
		assertFalse(sa.contains("1")||sa.contains("3"));
	}
	/**
	 * Test for retainAll method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition all non common elements removed
	 * @safe.testcases whether:	throws nullpointer if called with null
	 * 							return false if set is not modified
	 * 							return true if set is modified
	 * 							no longer contains the non common elements
	 */
	@Test
	public void testRetainAll() {
		assertThrows(NullPointerException.class,() -> sa.retainAll(null));
		List<String> ll = new ListAdapter<String>();
		ll.add("1"); ll.add("2"); ll.add("3"); ll.add("4");
		assertFalse(sa.retainAll(ll));
		ll.remove("2"); ll.remove("4");
		assertTrue(sa.retainAll(ll));
		assertFalse(sa.contains("2")||sa.contains("4"));
	}
	/**
	 * Test for size method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the set
	 */
	@Test
	public void testSize() {
		assertTrue(sa.size()==4);
		sa.clear();
		assertTrue(sa.size()==0);
	}
	/**
	 * Test for toArray method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition array of set elements
	 * @safe.testcases whether: the array contains the right elements
	 * 							referencing works
	 */
	@Test
	public void testToArray() {
		Set<Object> s = new SetAdapter<Object>();
		s.add(sa);
		s.add("wewe");
		s.add("nenno");
		Object[] arr = s.toArray();
		boolean b=true;
		for(int i=0; i<arr.length; i++) b&=s.contains(arr[i]);
		assertTrue(b);
		((Set<String>)arr[2]).remove("2");
		assertFalse(sa.contains("2"));
	}
	/**
	 * Test for hasNext iterator method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition none
	 * @safe.testcases whether return true only if iteration has more elements
	 */
	@Test
	public void testIt_HasNext() {
		Iterator<String> it = sa.iterator();
		assertTrue(it.hasNext());
		it.next(); it.next(); it.next(); it.next();
		assertFalse(it.hasNext());
	}
	/**
	 * Test for next iterator method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition none
	 * @safe.testcases whether: return the correct element
	 * 							throws nosuchelement if it's at the end of the iteration
	 */
	@Test
	public void testIt_Next() {
		Iterator<String> it = sa.iterator();
		assertTrue(sa.contains(it.next()));
		it.next(); it.next(); it.next();
		assertThrows(NoSuchElementException.class,() -> it.next());
	}
	/**
	 * Test for remove iterator method
	 * @safe.precondition SetAdapter filled with 4 element
	 * @safe.postcondition element removed
	 * @safe.testcases whether: throws illegalstate if next has not been called yet
	 * 							remove correct elements
	 * 							set contains removed elements 
	 */
	@Test
	public void testIt_Remove() {
		Iterator<String> it = sa.iterator();
		assertThrows(IllegalStateException.class,() -> it.remove());
		it.next(); String s = it.next();
		it.remove();
		assertFalse(sa.contains(s));
		assertThrows(IllegalStateException.class,() -> it.remove());
		s = it.next();
		it.remove();
		assertFalse(sa.contains(s));
	}
}
