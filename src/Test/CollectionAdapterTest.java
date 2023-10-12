/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;
import H2Adapter.CollectionAdapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.junit.jupiter.api.Test;

/**
 * @safe.summary Junit test suite for CollectionAdapter
 * @author Manfè Alessandro 1201538
 *
 */
public class CollectionAdapterTest {
	/**
	 * Test for default constructor
	 * @safe.precondition none
	 * @safe.postcondition a new CollectionAdapter
	 * @safe.testcases wether a new empty CollectionAdapter instance is created
	 */
	@Test
	public void testCollectionAdapter() {
		Collection<String> c = new CollectionAdapter<String>();
		assertTrue(c instanceof CollectionAdapter);
		assertTrue(c.isEmpty());
	}
	/**
	 * Test for Vector adaptee constructor
	 * @safe.precondition none
	 * @safe.postcondition a new CollectionAdapter
	 * @safe.testcases wether new CollectionAdapter instance wrapping specified Vector is created
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Test
	public void testCollectionAdapterVector() {
		Vector v = new Vector();
		v.add("Halo");
		Collection<String> c = new CollectionAdapter<String>(v);
		assertTrue(c instanceof CollectionAdapter);
		assertTrue(c.contains("Halo"));
	}
	/**
	 * Test for add method
	 * @safe.precondition CollectionAdapter filled 
	 * @safe.postcondition CollectionAdapter contains new element
	 * @safe.testcases whether:	return true if element is added
	 * 							contains new element
	 */
	@Test
	public void testAdd() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		assertTrue(ca.add("Elemento"));
		assertTrue(ca.contains("Elemento"));
	}
	/**
	 * Test for addAll method
	 * @safe.precondition CollectionAdapter filled 
	 * @safe.postcondition CollectionAdapter contains all new element
	 * @safe.testcases whether: return false when no element is added
	 * 							return True if collection changes after insert
	 * 							throws nullpointer if called with null Collection
	 * 							contains all new element
	 */
	@Test
	public void testAddAll() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		List<String> ll = null;
		assertThrows(NullPointerException.class, () -> ca.addAll(ll));
		List<String> l=new LinkedList<String>();
		assertFalse(ca.addAll(l));
		l.add("Uno");
		l.add("Due");
		l.add("Tre");
		assertTrue(ca.addAll(l));
		boolean b = ca.contains("Uno")&&ca.contains("Due")&&ca.contains("Tre");
		assertTrue(b);
	}
	/**
	 * Test for clear method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition empty CollectionAdapter
	 * @safe.testcases whether size==0 after method call
	 */
	@Test
	public void testClear() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Uno");
		ca.add("Due");
		ca.clear();
		assertTrue(ca.size()==0);
	}
	/**
	 * Test for contains method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given element
	 * 							return true if contains given element
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testContains() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Elemento");
		assertTrue(ca.contains("Elemento"));
		assertFalse(ca.contains(null));
		assertFalse(ca.contains(new Integer(1)));
	}
	/**
	 * Test for containsAll method
	 * @safe.precondition Collection filled 
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains all element in specified Collection
	 * 							return true if contains all element in specified Collection
	 * 							throws nullpointer if given Collection is null
	 */
	@Test
	public void testContainsAll() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Uno");
		ca.add("Due"); 
		ca.add("Tre");
		List<String> ll =null;
		assertThrows(NullPointerException.class, () -> ca.containsAll(ll));
		List<String> l=new LinkedList<String>();
		l.add("Uno");
		l.add("Due");
		assertTrue(ca.containsAll(l));
	}
	/**
	 * Test for equals method
	 * @safe.precondition CollectionAdapter filled 
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if called with empty or different collection
	 * 							return true if called with a collection that contains the same elements
	 * 							return true if called with the collection itself
	 * 							return false if called with another Object or null
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		CollectionAdapter<String> ca1 =new CollectionAdapter<String>();
		CollectionAdapter<String> ca2 =new CollectionAdapter<String>();
		ca1.add("Hello"); ca1.add("World");
		ca2.add("Hello"); ca2.add("World");
		assertTrue(ca1.equals(ca1));
		assertTrue(ca1.equals(ca2));
		assertFalse(ca1.equals("Definetly not a Collection"));
		assertFalse(ca1.equals(null));
		ca2.remove("Hello"); ca2.add("Hello");
		assertTrue(ca1.equals(ca2));
	}
	/**
	 * Test for hashCode method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition none
	 * @safe.testcases whether hashCode return the sum of element's hashCode
	 */
	@Test
	public void testHashCode() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Hello"); ca.add("World");
		int hash = "Hello".hashCode()+"World".hashCode();
		assertEquals(ca.hashCode(),hash);
	}
	/**
	 * Test for isEmpty method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition none
	 * @safe.testcases whether return true only when it's empty
	 */
	@Test
	public void testIsEmpty() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Boh");
		assertFalse(ca.isEmpty());
		ca.clear();
		assertTrue(ca.isEmpty());
	}
	/**
	 * Test for remove method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition element removed
	 * @safe.testcases whether:	return true if elements is correctly removed
	 * 							set no longer contains element
	 * 							return false if there's an attempt to remove an object that is not contained
	 */
	@Test
	public void testRemove() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Spongebob");
		assertFalse(ca.remove("Patrick"));
		assertTrue(ca.remove("Spongebob"));
		assertFalse(ca.contains("Spongebob"));
	}
	/**
	 * Test for removeAll method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition all common elements removed 
	 * @safe.testcases whether: throws nullpointer if called with null
	 * 							return false if collection is not modified
	 * 							return true if collection is modified
	 * 							no longer contains the common elements
	 */
	@Test
	public void testRemoveAll() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Ed");
		ca.add("Ed");
		ca.add("Edd");
		ca.add("Eddy");
		List<String> l=new LinkedList<String>();
		l.add("Ed");
		l.add("Eddy");
		assertThrows(NullPointerException.class,() -> ca.removeAll(null));
		assertTrue(ca.removeAll(l));
		assertTrue(ca.size()==1&&ca.contains("Edd"));
		assertFalse(ca.removeAll(l));
	}
	/**
	 * Test for retainAll method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition all non common elements removed
	 * @safe.testcases whether:	throws nullpointer if called with null
	 * 							return false if collection is not modified
	 * 							return true if collection is modified
	 * 							no longer contains the non common elements
	 */
	@Test
	public void testRetainAll() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Tonio");
		ca.add("Cartonio");
		ca.add("Tonio");
		ca.add("Lupo Lucio");
		List<String> l=new LinkedList<String>();
		l.add("Cartonio");
		assertThrows(NullPointerException.class,() -> ca.removeAll(null));
		assertTrue(ca.retainAll(l));
		assertTrue((ca.size()==1)&&(ca.contains("Cartonio")));
		assertFalse(ca.retainAll(l));
	}
	/**
	 * Test for toArray method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition array of collection elements
	 * @safe.testcases whether: the array contains the right elements
	 * 							referencing works
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testToArray() {
		CollectionAdapter<Object> cca =new CollectionAdapter<Object>();
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Nanni");
		cca.add(ca);
		cca.add("Tonolo");
		cca.add("Mariconda");
		Object[] arr = cca.toArray();
		boolean b=true;
		for(int i=0; i<arr.length; i++) b&=cca.contains(arr[i]);
		assertTrue(b);
		assertTrue(((CollectionAdapter)arr[0]).contains("Nanni"));
		((CollectionAdapter)arr[0]).remove("Nanni");
		assertFalse(ca.contains("Nanni"));
	}
	/**
	 * Test for size method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the collection
	 */
	@Test
	public void testSize() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Uno");
		ca.add("Due");
		assertTrue(ca.size()==2);
	}
	/**
	 * Test for iterator method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition instance of iterator
	 * @safe.testcases whether method initialize correctly an iterator over the collection
	 */
	@Test
	public void testIterator() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("1"); ca.add("2"); ca.add("3");
		Iterator<String> it = ca.iterator();
		assertTrue(it instanceof Iterator);
		Collection<String> c = new CollectionAdapter<String>();
		while(it.hasNext()) c.add(it.next());
		assertEquals(ca,c);
	}
	/**
	 * Test for hasNext iterator method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition none
	 * @safe.testcases whether return true only if iteration has more elements
	 */
	@Test
	public void testIt_HasNext() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Aldo"); 
		Iterator<String> it = ca.iterator();
		assertTrue(it.hasNext());
		ca.remove("Aldo");
		it=ca.iterator();
		assertFalse(it.hasNext());		
	}
	/**
	 * Test for next iterator method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition none
	 * @safe.testcases whether: return the correct element
	 * 							throws nosuchelement if it's at the end of the iteration
	 */
	@Test
	public void testIt_Next() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		final Iterator<String> it = ca.iterator();
		assertThrows(NoSuchElementException.class,() -> it.next());
		ca.add("Giovanni");
		Iterator<String >iter = ca.iterator();
		assertEquals(iter.next(),"Giovanni");
	}
	/**
	 * Test for remove iterator method
	 * @safe.precondition CollectionAdapter filled
	 * @safe.postcondition element removed
	 * @safe.testcases whether: throws illegalstate if next has not been called yet
	 * 							remove correct elements
	 * 							collection contains removed elements 
	 */
	@Test
	public void testIt_Remove() {
		CollectionAdapter<String> ca =new CollectionAdapter<String>();
		ca.add("Aldo"); ca.add("Giovanni"); ca.add("Giacomo");
		Iterator<String> it = ca.iterator();
		assertThrows(IllegalStateException.class,() -> it.remove());
		Iterator<String> iter = ca.iterator();
		iter.next();
		iter.remove();
		assertFalse(ca.contains("Aldo"));
		assertThrows(IllegalStateException.class,() -> iter.remove());
	}
}
