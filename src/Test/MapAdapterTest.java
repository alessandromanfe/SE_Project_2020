/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import H2Adapter.CollectionAdapter;
import H2Adapter.MapAdapter;
import H2Adapter.SetAdapter;

/**
 * @safe.summary Junit test suite for MapAdapter
 * @author Manfè Alessandro 1201538
 *
 */

@SuppressWarnings({"unchecked","rawtypes","unlikely-arg-type"}) //Mettevano a dura prova il mio OCD
public class MapAdapterTest {
	MapAdapter<String,String> ma;
	
	final class MyEntry<K, V> implements Map.Entry<K, V> {
	    private final K key;
	    private V value;

	    public MyEntry(K key, V value) {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public K getKey() {
	        return key;
	    }

	    @Override
	    public V getValue() {
	        return value;
	    }

	    @Override
	    public V setValue(V value) {
	        V old = this.value;
	        this.value = value;
	        return old;
	    }
	    @Override
		public int hashCode() {
			return (key==null   ? 0 : key.hashCode()) ^
		     (value==null ? 0 : value.hashCode());
		}
	    @Override
		public boolean equals(Object o){
			if(this==o) return true;
			if(!(o instanceof Map.Entry)) return false;
			Map.Entry e = (Map.Entry)o;
			return (key==null ? e.getKey()==null : key.equals(e.getKey()))  &&
				   (value==null ? e.getValue()==null : value.equals(e.getValue()));
			}
	}
	
	@BeforeEach
	public void setup() {
		ma = new MapAdapter<String,String>();
		ma.put("K0","V0"); ma.put("K1","V1"); ma.put("K2","V2"); ma.put("K3","V3");
	}
	/**
	 * Test for default constructor
	 * @safe.precondition none
	 * @safe.postcondition a new MapAdapter
	 * @safe.testcases whether a new empty MapAdapter instance is created
	 */
	@Test
	public void testMapAdapter() {
		ma = new MapAdapter<String,String>();
		assertTrue(ma instanceof MapAdapter);
		assertTrue(ma.isEmpty());
	}
	/**
	 * Test for Hashtable adaptee constructor
	 * @safe.precondition none
	 * @safe.postcondition a new MapAdapter
	 * @safe.testcases whether a MapAdapter instance wrapping specified Hashtable is created
	 */
	@Test
	public void testMapAdapterHashtable() {
		Hashtable hs = new Hashtable();
		hs.put("Chiave","Valore");
		ma = new MapAdapter<String,String>(hs);
		assertTrue(ma instanceof MapAdapter);
		assertTrue(ma.containsValue("Valore")&&ma.containsKey("Chiave"));
	}
	/**
	 * Test for clear method
	 * @safe.precondition MapAdapter filled with 4 entry
	 * @safe.postcondition empty MapAdapter
	 * @safe.testcases whether size==0 after method call
	 */
	@Test
	public void testClear() {
		ma.clear();
		assertTrue(ma.size()==0);
	}
	/**
	 * Test for containsKey method
	 * @safe.precondition MapAdapter filled with 4 entry
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given key
	 * 							return true if contains given key
	 * 							throws nullpointer if called with null parameter
	 */
	@Test
	public void testContainsKey() {
		assertTrue(ma.containsKey("K1"));
		assertThrows(NullPointerException.class,() -> ma.containsKey(null));
		assertFalse(ma.containsKey("K5"));
		assertFalse(ma.containsKey(new Integer(5)));
	}
	/**
	 * Test for containsValue method
	 * @safe.precondition MapAdapter filled with 4 entry
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given value
	 * 							return true if contains given value
	 * 							throws nullpointer if called with null parameter
	 */
	@Test
	public void testContainsValue() {
		assertTrue(ma.containsValue("V1"));
		assertThrows(NullPointerException.class,() -> ma.containsValue(null));
		assertFalse(ma.containsValue("V5"));
		assertFalse(ma.containsValue(new Integer(5)));
	}
	/**
	 * Test for equals method
	 * @safe.precondition MapAdapter filled with 4 entry
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if called with empty or different map
	 * 							return true if called with a map that contains the same entries
	 * 							return true if called with the set itself
	 * 							return false if called with Object that's null or contains null
	 */
	@Test
	public void testEquals() {
		Map<String,String> temp = new HashMap<String,String>();
		temp.put("K1","V1"); temp.put("K3","V3");
		assertFalse(ma.equals(temp));
		ma.remove("K0"); ma.remove("K2");
		assertTrue(ma.equals(temp));
		assertTrue(ma.equals(ma));
		temp.remove("K1,V1"); temp.put(null,null);
		assertFalse(ma.equals(temp));
		assertFalse(ma.equals(null));
	}
	/**
	 * Test for get method
	 * @safe.precondition MapAdapter filled with 4 entry
	 * @safe.postcondition none
	 * @safe.testcases whether: throws nullpointer is called with null
	 * 							return the right element
	 * 							return null if the key does not exist
	 */
	@Test
	public void testGet() {
		assertThrows(NullPointerException.class,() -> ma.get(null));
		assertEquals(ma.get("K3"),"V3");
		assertEquals(ma.get("K7"),null);
		assertEquals(ma.get(new Integer(7)),null);
	}
	/**
	 * Test for getOrDefault method
	 * @safe.precondition MapAdapter filled with 4 entry
	 * @safe.postcondition none
	 * @safe.testcases whether: throws nullpointer is called with null
	 * 							return the right element
	 * 							return default value if the key does not exist
	 */
	@Test
	public void testGetOrDefault() {
		assertThrows(NullPointerException.class,() -> ma.getOrDefault(null,"Buh"));
		assertEquals(ma.getOrDefault("K3","Buh"),"V3");
		assertEquals(ma.getOrDefault("K7","Buh"),"Buh");
		assertEquals(ma.getOrDefault(new Integer(7),"Bah"),"Bah");
	}
	/**
	 * Test for hashCode method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition none
	 * @safe.testcases whether hashCode return the sum of entries' hashCode
	 */
	@Test
	public void testHashCode() {
		Map<String,String> temp = new HashMap<String,String>();
		temp.putAll(ma);
		assertEquals(ma.hashCode(),temp.hashCode());
	}
	/**
	 * Test for isEmpty method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition empty MapAdapter
	 * @safe.testcases whether return true only when it's empty
	 */
	@Test
	public void testIsEmpty() {
		assertFalse(ma.isEmpty());
		ma.clear();
		assertTrue(ma.isEmpty());
	}
	/**
	 * Test for put method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition contains new entry 
	 * @safe.testcases whether:	throws nullpointer if at least one param is null
	 * 							return previous value
	 * 							map contains new entry
	 * 							return null if key does not exist
	 */
	@Test
	public void testPut() {
		assertThrows(NullPointerException.class,() -> ma.put("eheh",null));
		assertThrows(NullPointerException.class,() -> ma.put(null,"uhuh"));
		String s = ma.put("K1","Antani");
		assertEquals(s,"V1");
		assertEquals(ma.get("K1"),"Antani");
		assertEquals(ma.put("K42","hololo"),null);
	}
	/**
	 * Test for putAll method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition contains new entries 
	 * @safe.testcases whether:	throws nullpointer if map is null or contains null
	 * 							map contains all specified map's entries
	 */
	@Test
	public void testPutAll() {
		assertThrows(NullPointerException.class,() -> ma.putAll(null));
		Map<String,String> temp = new HashMap<String,String>();
		temp.put("23",null);
		assertThrows(NullPointerException.class,() -> ma.putAll(temp));
		temp.clear();
		temp.put("K4","V4"); temp.put("K5","V5");
		ma.putAll(temp);
		assertTrue(ma.containsKey("K4")&&ma.containsKey("K5"));
		assertTrue(ma.get("K4").equals("V4")&&ma.get("K5").equals("V5"));
	}
	/**
	 * Test for putIfAbsent method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition contains new entry 
	 * @safe.testcases whether:	throws nullpointer if at least one param is null
	 * 							return current value if key exist
	 * 							return null if key does not exist
	 * 							map contains new entry
	 */
	@Test
	public void testPutIfAbsent() {
		assertThrows(NullPointerException.class,() -> ma.putIfAbsent(null,null));
		assertEquals(ma.putIfAbsent("K1","Magalli"),"V1");
		assertEquals(ma.putIfAbsent("K5","V5"),null);
		assertTrue(ma.containsKey("K5"));
		assertEquals(ma.get("K5"),"V5");
	}
	/**
	 * Test for remove method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition element removed
	 * @safe.testcases whether:	throws nullpointer if called with null 
	 * 							return the value of the removed entry
	 * 							map no longer contains element
	 * 							return null if there's an attempt to remove an object that is not contained
	 */
	@Test
	public void testRemove() {
		assertThrows(NullPointerException.class,() -> ma.remove(null));
		assertEquals(ma.remove("K0"),"V0");
		assertFalse(ma.containsValue("V0")||ma.containsKey("K0"));
		assertEquals(ma.remove("Platano"),null);
	}
	/**
	 * Test for remove(K,V) method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition entry removed
	 * @safe.testcases whether:	throws nullpointer if at least one param is null 
	 * 							return true if the entry is correctly removed
	 * 							return false if the entry does not exist
	 * 							map no longer contains entry
	 */
	@Test
	public void testRemoveKV() {
		assertThrows(NullPointerException.class,() -> ma.remove("Buha",null));
		assertThrows(NullPointerException.class,() -> ma.remove(null,"eee"));
		assertTrue(ma.remove("K1","V1"));
		assertFalse(ma.containsValue("V1")||ma.containsKey("K1"));
		assertFalse(ma.remove("K0","V1"));
		assertFalse(ma.remove("K1","V0"));
	}
	/**
	 * Test for replace(K,V) method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition value replaced
	 * @safe.testcases whether:	throws nullpointer if at least one param is null 
	 * 							return previous value associated with the key
	 * 							return null if the key does not exist
	 * 							value is actually replaced
	 */
	@Test
	public void testReplaceKV() {
		assertThrows(NullPointerException.class,() -> ma.replace("Buha",null));
		assertThrows(NullPointerException.class,() -> ma.replace(null,"eee"));
		assertEquals(ma.replace("Maremma","KTM"),null);
		assertEquals(ma.replace("K2","Ninni Bruschetta"),"V2");
		assertEquals(ma.get("K2"),"Ninni Bruschetta");
	}
	/**
	 * Test for replace(K,V,V) method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition value replaced
	 * @safe.testcases whether:	throws nullpointer if at least one param is null 
	 * 							only works if the key is associated with the specified value
	 * 							return true if value is replaced
	 * 							return null if the key does not exist or it's mapped to another value
	 * 							value is actually replaced
	 */
	@Test
	public void testReplaceKVV() {
		assertThrows(NullPointerException.class,() -> ma.replace("Buha","V1",null));
		assertThrows(NullPointerException.class,() -> ma.replace(null,null,"eee"));
		assertFalse(ma.replace("Maremma","KTM","V1"));
		assertFalse(ma.replace("K2","V1","Jerry Cala"));
		assertTrue(ma.replace("K2","V2","Jerry Cala"));
		assertEquals(ma.get("K2"),"Jerry Cala");
	}
	/**
	 * Test for size method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the set
	 */
	@Test
	public void testSize() {
		assertTrue(ma.size()==4);
		ma.clear();
		assertTrue(ma.size()==0);
	}
	
//MAP.ENTRYSET() TEST
	
	/**
	 * Test for entrySet method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition none
	 * @safe.testcases whether return an instance of a set that contains all map entries
	 */
	@Test
	public void testEntrySet() {
		Set s = ma.entrySet();
		assertTrue(s instanceof SetAdapter);
		boolean b=true;
		for(Object e : s) {
			Map.Entry me = (Map.Entry)e;
			b&=ma.containsKey(me.getKey())&&ma.containsValue(me.getValue());
		}
		assertTrue(b);
	}
	/**
	 * Test for add entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition none
	 * @safe.testcases whether throws UnsupportedOperation
	 */
	@Test
	public void testES_add() {
		Set s = ma.entrySet();
		assertThrows(UnsupportedOperationException.class,() -> s.add((s.iterator().next())));
	}
	/**
	 * Test for addAll entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition none
	 * @safe.testcases whether throws UnsupportedOperation
	 */
	@Test
	public void testES_addAll() {
		Set s = ma.entrySet();
		assertThrows(UnsupportedOperationException.class,() -> s.addAll(new CollectionAdapter()));
	}
	/**
	 * Test for clear entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition empty set and map
	 * @safe.testcases whether map and set are empty after method call
	 */
	@Test
	public void testES_clear() {
		Set s = ma.entrySet();
		s.clear();
		assertTrue(s.isEmpty());
		assertTrue(ma.isEmpty());
	}
	/**
	 * Test for contains entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given entry
	 * 							return true if contains given entry
	 * 							throws nullpointer if called with null parameter
	 */
	@Test
	public void testES_contains() {
		Set s = ma.entrySet();
		assertTrue(s.contains(new MyEntry("K1","V1")));
		assertFalse(s.contains(new MyEntry("K2","V1")));
		assertThrows(NullPointerException.class,() -> s.contains(null));
	}
	/**
	 * Test for containsAll entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains all element in specified Collection
	 * 							return true if contains all element in specified Collection
	 * 							throws nullpointer if given Collection is null or contains null elements
	 */
	@Test
	public void testES_containsAll() {
		Set s = ma.entrySet();
		assertThrows(NullPointerException.class,() -> s.containsAll(null));
		assertTrue(s.containsAll(s));
		Set sa = new SetAdapter();
		sa.add(new MyEntry("K2","V2"));
		sa.add(new MyEntry("K3","V3"));
		assertTrue(s.containsAll(sa));
		sa.add(new MyEntry(null,null));
		assertThrows(NullPointerException.class,() -> s.containsAll(sa));
	}
	/**
	 * Test for equals entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if called with empty or different set
	 * 							return true if called with a set that contains the same elements
	 * 							return true if called with the set itself
	 * 							return false if called with another Object or null
	 */
	@Test
	public void testES_equals() {
		Set s = ma.entrySet();
		assertFalse(s.equals(null));
		assertTrue(s.equals(s));
		Set sa = new SetAdapter();
		assertFalse(s.equals(sa));
		sa.add(new MyEntry("K0","V0")); sa.add(new MyEntry("K1","V1"));
		sa.add(new MyEntry("K2","V2")); sa.add(new MyEntry("K3","V3"));
		assertTrue(s.equals(sa));
	}
	/**
	 * Test for hashCode entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition none
	 * @safe.testcases whether hashCode return the sum of map entries's hashCode
	 */
	@Test
	public void testES_hashCode() {
		Set s = ma.entrySet();
		Set sa = new SetAdapter();
		sa.add(new MyEntry("K0","V0")); sa.add(new MyEntry("K1","V1"));
		sa.add(new MyEntry("K2","V2")); sa.add(new MyEntry("K3","V3"));
		assertTrue(s.hashCode()==sa.hashCode());
	}
	/**
	 * Test for isEmpty entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition empty SetAdapter
	 * @safe.testcases whether return true only when it's empty
	 */
	@Test
	public void testES_isEmpty() {
		Set s = ma.entrySet();
		assertFalse(s.isEmpty());
		ma.clear();
		assertTrue(s.isEmpty());
	}
	/**
	 * Test for entrySet iterator 
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition modified map
	 * @safe.testcases whether: method initialize correctly an iterator
	 * 							hasNext: return true only if iteration has more elements
	 * 							next: 	 return the correct element
	 * 								  	 throws nosuchelement if it's at the end of the iteration
	 * 							remove:  throws illegalstate if next has not been called yet
	 * 									 remove correct elements
	 * 									 set contains removed elements 
	 */
	@Test 
	public void testES_iterator() {
		Set s = ma.entrySet();
		Iterator it = s.iterator();
		assertTrue(it instanceof Iterator);
		assertTrue(it.hasNext());
		Map.Entry e = (Map.Entry)(it.next());
		assertTrue(ma.containsValue(e.getValue())&&ma.containsKey(e.getKey()));
		it.remove();
		assertThrows(IllegalStateException.class,() -> it.remove());
		assertFalse(ma.containsKey(e.getKey())&&ma.containsValue(e.getValue()));
		it.next(); it.next(); it.next();
		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class,() -> it.next());
	}
	/**
	 * Test for remove entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition element removed
	 * @safe.testcases whether:	throws nullpointer if called with null 
	 * 							return true if elements is correctly removed
	 * 							set and map no longer contains element
	 * 							return false if there's an attempt to remove an object that is not contained
	 */
	@Test
	public void testES_remove() {
		Set s = ma.entrySet();
		assertTrue(s.remove(new MyEntry("K3","V3")));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertThrows(NullPointerException.class,() -> s.remove(null));
		assertFalse(s.remove(new MyEntry("Diletta","Leotta")));
	}
	/**
	 * Test for removeAll entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition all common elements removed 
	 * @safe.testcases whether: throws nullpointer if called with null
	 * 							return false if map and set are not modified
	 * 							return true if map and set are modified
	 * 							map and set no longer contains the common elements
	 */
	@Test
	public void testES_removeAll() {
		Set s = ma.entrySet();
		Set sa = new SetAdapter<Map.Entry>();
		assertFalse(s.removeAll(sa));
		sa.add(new MyEntry("K2","V2")); sa.add(new MyEntry("K3","V3"));
		assertTrue(s.removeAll(sa));
		assertFalse(ma.containsValue("V2")||ma.containsKey("K2"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertTrue(s.size()==2);
		assertThrows(NullPointerException.class,() -> s.removeAll(null));
	}
	/**
	 * Test for retainAll entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition all non common elements removed
	 * @safe.testcases whether:	throws nullpointer if called with null
	 * 							return false if map and set are not modified
	 * 							return true if map and set are modified
	 * 							map and set no longer contains the non common elements
	 */
	@Test
	public void testES_retainAll() {
		Set s = ma.entrySet();
		Set sa = new SetAdapter<Map.Entry>();
		sa.add(new MyEntry("K1","V1")); sa.add(new MyEntry("K0","V0"));
		assertTrue(s.retainAll(sa));
		assertFalse(ma.containsValue("V2")||ma.containsKey("K2"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertTrue(s.size()==2);
		s.clear();
		assertFalse(s.retainAll(sa));
		assertThrows(NullPointerException.class,() -> s.retainAll(null));
	}
	/**
	 * Test for size entrySet method
	 * @safe.precondition SetAdapter filled map entries
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the set
	 */
	@Test
	public void testES_size() {
		Set s = ma.entrySet();
		assertTrue(s.size()==ma.size());
	}
	/**
	 * Test for toArray entrySet method
	 * @safe.precondition SetAdapter filled with map entries
	 * @safe.postcondition array of entries
	 * @safe.testcases whether: the array contains the right elements
	 * 							referencing works
	 */
	@Test 
	public void testES_toArray() {
		Set s = ma.entrySet();
		Object[] arr = s.toArray();
		boolean b=true;
		for(int i=0; i<arr.length; i++) b&=ma.containsKey(((Map.Entry)arr[i]).getKey());
		assertTrue(b);
		assertTrue(arr.length==4);
		((Map.Entry)arr[0]).setValue("Capperi");
		((Map.Entry)arr[1]).setValue("Acciughe");
		assertTrue(ma.containsValue("Capperi")&&ma.containsValue("Acciughe"));
	}
	
//MAP.KEYSET() TEST
	
	/**
	 * Test for keySet method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition none
	 * @safe.testcases whether return an instance of a set that contains all map keys
	 */
	@Test
	public void testKeySet() {
		Set s = ma.keySet();
		assertTrue(s instanceof SetAdapter);
		boolean b=true;
		for(Object e : s) {
			b&=ma.containsKey((String)e);
		}
		assertTrue(b);
	}
	/**
	 * Test for add keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition none
	 * @safe.testcases whether throws UnsupportedOperation
	 */
	@Test
	public void testKS_add() {
		Set s = ma.keySet();
		assertThrows(UnsupportedOperationException.class,() -> s.add("A String"));
	}
	/**
	 * Test for addAll keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition none
	 * @safe.testcases whether throws UnsupportedOperation
	 */
	@Test
	public void testKS_addAll() {
		Set s = ma.keySet();
		assertThrows(UnsupportedOperationException.class,() -> s.addAll(new CollectionAdapter()));
	}
	/**
	 * Test for clear keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition empty set and map
	 * @safe.testcases whether map and set are empty after method call
	 */
	@Test
	public void testKS_clear() {
		Set s = ma.keySet();
		s.clear();
		assertTrue(ma.isEmpty()&&s.isEmpty());
	}
	/**
	 * Test for contains keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given key
	 * 							return true if contains given key
	 * 							throws nullpointer if called with null parameter
	 */
	@Test
	public void testKS_contains() {
		Set s = ma.keySet();
		assertTrue(s.contains("K1"));
		assertFalse(s.contains("K23"));
		assertThrows(NullPointerException.class,() -> s.contains(null));
	}
	/**
	 * Test for containsAll keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains all element in specified Collection
	 * 							return true if contains all element in specified Collection
	 * 							throws nullpointer if given Collection is null or contains null elements
	 */
	@Test
	public void testKS_containsAll() {
		Set s = ma.keySet();
		assertThrows(NullPointerException.class,() -> s.containsAll(null));
		assertTrue(s.containsAll(s));
		Set sa = new SetAdapter();
		sa.add("K2");
		sa.add("K3");
		assertTrue(s.containsAll(sa));
		sa.add("wewe");
		assertFalse(s.containsAll(sa));
	}
	/**
	 * Test for equals keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if called with empty or different set
	 * 							return true if called with a set that contains the same elements
	 * 							return true if called with the set itself
	 * 							return false if called with another Object or null
	 */
	@Test
	public void testKS_equals() {
		Set s = ma.keySet();
		assertFalse(s.equals(null));
		assertTrue(s.equals(s));
		Set sa = new SetAdapter();
		assertFalse(s.equals(sa));
		sa.add("K0"); sa.add("K1"); sa.add("K2"); sa.add("K3");
		assertTrue(s.equals(sa));
	}
	/**
	 * Test for hashCode keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition none
	 * @safe.testcases whether hashCode return sum of all keys' hashCode
	 */
	@Test
	public void testKS_hashCode() {
		Set s = ma.keySet();
		int hash="K1".hashCode()+"K2".hashCode()+"K3".hashCode()+"K0".hashCode();
		assertEquals(hash,s.hashCode());
	}
	/**
	 * Test for isEmpty keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition empty SetAdapter
	 * @safe.testcases whether return true only when it's empty
	 */
	@Test
	public void testKS_isEmpty() {
		Set s = ma.keySet();
		assertFalse(s.isEmpty());
		ma.clear();
		assertTrue(s.isEmpty());
	}
	/**
	 * Test for keySet iterator 
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition modified map
	 * @safe.testcases whether: method initialize correctly an iterator
	 * 							hasNext: return true only if iteration has more elements
	 * 							next: 	 return the correct element
	 * 								  	 throws nosuchelement if it's at the end of the iteration
	 * 							remove:  throws illegalstate if next has not been called yet
	 * 									 remove correct elements
	 * 									 set contains removed elements 
	 */
	@Test 
	public void testKS_iterator() {
		Set s = ma.keySet();
		Iterator it = s.iterator();
		assertTrue(it instanceof Iterator);
		assertTrue(it.hasNext());
		String str = (String)(it.next());
		assertTrue(ma.containsKey(str));
		it.remove();
		assertThrows(IllegalStateException.class,() -> it.remove());
		assertFalse(ma.containsKey(str));
		it.next(); it.next(); it.next();
		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class,() -> it.next());
	}
	/**
	 * Test for remove keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition element removed
	 * @safe.testcases whether:	throws nullpointer if called with null 
	 * 							return true if key is correctly removed
	 * 							set and map no longer contains key
	 * 							return false if there's an attempt to remove an object that is not contained
	 */
	@Test
	public void testKS_remove() {
		Set s = ma.keySet();
		assertTrue(s.remove("K3"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertThrows(NullPointerException.class,() -> s.remove(null));
		assertFalse(s.remove("Don Matteo"));
	}
	/**
	 * Test for removeAll keyySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition all common elements removed 
	 * @safe.testcases whether: throws nullpointer if called with null
	 * 							return false if map and set are not modified
	 * 							return true if map and set are modified
	 * 							map and set no longer contains the common elements
	 */
	@Test
	public void testKS_removeAll() {
		Set s = ma.keySet();
		Set sa = new SetAdapter<String>();
		assertFalse(s.removeAll(sa));
		sa.add("K2"); sa.add("K3");
		assertTrue(s.removeAll(sa));
		assertFalse(ma.containsValue("V2")||ma.containsKey("K2"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertTrue(s.size()==2);
		assertThrows(NullPointerException.class,() -> s.removeAll(null));
	}
	/**
	 * Test for retainAll keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition all non common elements removed
	 * @safe.testcases whether:	throws nullpointer if called with null
	 * 							return false if map and set are not modified
	 * 							return true if map and set are modified
	 * 							map and set no longer contains the non common elements
	 */
	@Test
	public void testKS_retainAll() {
		Set s = ma.keySet();
		Set sa = new SetAdapter<String>();
		sa.add("K1"); sa.add("K0");
		assertTrue(s.retainAll(sa));
		assertFalse(ma.containsValue("V2")||ma.containsKey("K2"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertTrue(s.size()==2);
		s.clear();
		assertFalse(s.retainAll(sa));
		assertThrows(NullPointerException.class,() -> s.retainAll(null));
	}
	/**
	 * Test for size keySet method
	 * @safe.precondition SetAdapter filled map keys
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the set
	 */
	@Test
	public void testKS_size() {
		Set s = ma.keySet();
		assertTrue(s.size()==ma.size());
	}
	/**
	 * Test for toArray keySet method
	 * @safe.precondition SetAdapter filled with map keys
	 * @safe.postcondition array of entries
	 * @safe.testcases whether: the array contains the right elements
	 * 							referencing works
	 */
	@Test 
	public void testKS_toArray() {
		Map m = new MapAdapter<Object,String>();
		m.put(ma,"mah");
		m.put("bah", "buh");
		m.put("ajeje","brazorf");
		Object[] arr = m.keySet().toArray();
		boolean b=true;
		for(int i=0; i<arr.length; i++) b&=m.containsKey(arr[i]);
		assertTrue(b);
		((Map)arr[0]).remove("K1");
		assertFalse(ma.containsKey("K1"));
	}
	
//MAP.VALUES() TEST
	
	/**
	 * Test for values method
	 * @safe.precondition MapAdapter filled with 4 entries
	 * @safe.postcondition none
	 * @safe.testcases whether return an instance of a set that contains all map keys
	 */
	@Test
	public void testValues() {
		Collection c = ma.values();
		assertTrue(c instanceof SetAdapter);
		boolean b=true;
		for(Object e : c) {
			b&=ma.containsValue((String)e);
		}
		assertTrue(b);
	}
	/**
	 * Test for add Collection values method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition none
	 * @safe.testcases whether throws UnsupportedOperation
	 */
	@Test
	public void testV_add() {
		Collection c = ma.values();
		assertThrows(UnsupportedOperationException.class,() -> c.add("A String"));
	}
	/**
	 * Test for addAll values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition none
	 * @safe.testcases whether throws UnsupportedOperation
	 */
	@Test
	public void testV_addAll() {
		Collection c = ma.values();
		assertThrows(UnsupportedOperationException.class,() -> c.addAll(new CollectionAdapter()));
	}
	/**
	 * Test for clear values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition empty set and map
	 * @safe.testcases whether map and set are empty after method call
	 */
	@Test
	public void testV_clear() {
		Collection c = ma.values();
		c.clear();
		assertTrue(ma.isEmpty());
	}
	/**
	 * Test for contains values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains given value
	 * 							return true if contains given value
	 * 							throws nullpointer if called with null parameter
	 */
	@Test
	public void testV_contains() {
		Collection c = ma.values();
		assertTrue(c.contains("V1"));
		assertFalse(c.contains("V23"));
		assertThrows(NullPointerException.class,() -> c.contains(null));
	}
	/**
	 * Test for containsAll values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if does not contains all element in specified Collection
	 * 							return true if contains all element in specified Collection
	 * 							throws nullpointer if given Collection is null or contains null elements
	 */
	@Test
	public void testV_containsAll() {
		Collection c = ma.values();
		assertThrows(NullPointerException.class,() -> c.containsAll(null));
		assertTrue(c.containsAll(c));
		Collection ca = new CollectionAdapter();
		ca.add("V2");
		ca.add("V3");
		assertTrue(c.containsAll(ca));
		ca.add("wewe");
		assertFalse(c.containsAll(ca));
	}
	/**
	 * Test for equals values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition none
	 * @safe.testcases whether:	return false if called with empty or different set
	 * 							return true if called with a set that contains the same elements
	 * 							return true if called with the set itself
	 * 							return false if called with another Object or null
	 */
	@Test
	public void testV_equals() {
		Collection c = ma.values();
		assertFalse(c.equals(null));
		assertTrue(c.equals(c));
		Collection ca = new SetAdapter();
		assertFalse(c.equals(ca));
		ca.add("V0"); ca.add("V1"); ca.add("V2"); ca.add("V3");
		assertTrue(c.equals(ca));
	}
	/**
	 * Test for hashCode values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition none
	 * @safe.testcases whether hashCode return sum of all values' hashCode
	 */
	@Test
	public void testV_hashCode() {
		Collection c = ma.values();
		int hash="V1".hashCode()+"V2".hashCode()+"V3".hashCode()+"V0".hashCode();
		assertEquals(hash,c.hashCode());
	}
	/**
	 * Test for isEmpty values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition empty SetAdapter
	 * @safe.testcases whether return true only when it's empty
	 */
	
	@Test
	public void testV_isEmpty() {
		Collection c = ma.values();
		assertFalse(c.isEmpty());
		ma.clear();
		assertTrue(c.isEmpty());
	}
	/**
	 * Test for values Collection iterator 
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition modified map
	 * @safe.testcases whether: method initialize correctly an iterator
	 * 							hasNext: return true only if iteration has more elements
	 * 							next: 	 return the correct element
	 * 								  	 throws nosuchelement if it's at the end of the iteration
	 * 							remove:  throws illegalstate if next has not been called yet
	 * 									 remove correct elements
	 * 									 set contains removed elements 
	 */
	@Test 
	public void testV_iterator() {
		Collection c = ma.values();
		Iterator it = c.iterator();
		assertTrue(it instanceof Iterator);
		assertTrue(it.hasNext());
		String str = (String)(it.next());
		assertTrue(ma.containsValue(str));
		it.remove();
		assertThrows(IllegalStateException.class,() -> it.remove());
		assertFalse(ma.containsValue(str));
		it.next(); it.next(); it.next();
		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class,() -> it.next());
	}
	/**
	 * Test for remove values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition element removed
	 * @safe.testcases whether:	throws nullpointer if called with null 
	 * 							return true if key is correctly removed
	 * 							set and map no longer contains key
	 * 							return false if there's an attempt to remove an object that is not contained
	 */
	@Test
	public void testV_remove() {
		Collection c = ma.values();
		assertTrue(c.remove("V3"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertThrows(NullPointerException.class,() -> c.remove(null));
		assertFalse(c.remove("Don Matteo"));
	}
	/**
	 * Test for removeAll values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition all common elements removed 
	 * @safe.testcases whether: throws nullpointer if called with null
	 * 							return false if map and set are not modified
	 * 							return true if map and set are modified
	 * 							map and set no longer contains the common elements
	 */
	@Test
	public void testV_removeAll() {
		Collection c = ma.values();
		Collection ca = new CollectionAdapter<String>();
		assertFalse(c.removeAll(ca));
		ca.add("V2"); ca.add("V3");
		assertTrue(c.removeAll(ca));
		assertFalse(ma.containsValue("V2")||ma.containsKey("K2"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertTrue(c.size()==2);
		assertThrows(NullPointerException.class,() -> c.removeAll(null));
	}
	/**
	 * Test for retainAll values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition all non common elements removed
	 * @safe.testcases whether:	throws nullpointer if called with null
	 * 							return false if map and set are not modified
	 * 							return true if map and set are modified
	 * 							map and set no longer contains the non common elements
	 */
	@Test
	public void testV_retainAll() {
		Collection c = ma.values();
		Collection ca = new CollectionAdapter<String>();
		ca.add("V1"); ca.add("V0");
		assertTrue(c.retainAll(ca));
		assertFalse(ma.containsValue("V2")||ma.containsKey("K2"));
		assertFalse(ma.containsValue("V3")||ma.containsKey("K3"));
		assertTrue(c.size()==2);
		c.clear();
		assertFalse(c.retainAll(ca));
		assertThrows(NullPointerException.class,() -> c.retainAll(null));
	}
	/**
	 * Test for size values Collection method
	 * @safe.precondition SetAdapter filled map values
	 * @safe.postcondition none
	 * @safe.testcases whether return the correct size of the set
	 */
	@Test
	public void testV_size() {
		Collection c = ma.values();
		assertTrue(c.size()==ma.size());
	}
	/**
	 * Test for toArray values Collection method
	 * @safe.precondition SetAdapter filled with map values
	 * @safe.postcondition array of entries
	 * @safe.testcases whether: the array contains the right elements
	 * 							referencing works
	 */
	@Test 
	public void testV_toArray() {
		Map m = new MapAdapter<String,MapAdapter<String,String>>();
		m.put("mah",ma);
		Object[] arr = m.values().toArray();
		boolean b=true;
		for(int i=0; i<arr.length; i++) b&=m.containsValue(arr[i]);
		assertTrue(b);
		((Map)arr[0]).remove("K1");
		assertFalse(ma.containsKey("K1"));
	}
}
