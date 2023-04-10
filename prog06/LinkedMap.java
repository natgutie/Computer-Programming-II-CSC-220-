package prog06;
import java.util.*;
import java.util.Map.Entry;

public class LinkedMap <K extends Comparable<K>, V>
    extends AbstractMap<K, V> {

    protected class Entry implements Map.Entry<K, V> {
	K key;
	V value;
	Entry previous, next;
    
	Entry (K key, V value) {
	    this.key = key;
	    this.value = value;
	}
    
	public K getKey () { return key; }
	public V getValue () { return value; }
	public V setValue (V newValue) {
	    V oldValue = value;
	    value = newValue;
	    return oldValue;
	}

	public String toString () {
	    return "{" + key + "=" + value + "}";
	}
    }
  
    protected Entry first, last;
  
    /**
     * Find the Entry e with e.key equal to key.
     * @param key The Key to be found.
     * @return The Entry e with e.key.equals(key)
     * or null if there isn't one.
     */
    protected Entry find (K key) {
	// EXERCISE
	// Return the earliest Entry that is >= to key
	// or null if there is no such Entry.

	for (Entry entry = first; entry != null; entry = entry.next)
		if (entry.key.compareTo(key) >= 0)
			return entry; // did find the entry
	return null; // Did not find the entry.

		//find (dgo) --> start at first and go until null (next) at last entry,
		// does the entries key match each key in the map --> returns what comes after

		//find (ccc) --> find it and return where it should be
    }    
  
    /**
     * Determine if the Entry returned by find is the one we are looking
     * for.
     * @param entry The Entry returned by find.
     * @param key The Key to be found.
     * @return true if find found the entry with that key
     * or false otherwise
     */
    protected boolean found (Entry entry, K key) {
	// EXERCISE
	// return true if it is in the list
	// return false if not found
		//return entry != key; // Fix this.
	if ((entry != null) && (entry.getKey().equals(key))) // compare two similar objects and check if equal too
		return true;
	return false;

    }

    public boolean containsKey (Object keyAsObject) {
	K key = (K) keyAsObject;
	Entry entry = find(key);
	return found(entry, key);
    }
  
    /**
     * Add newEntry just before nextEntry or as last Entry if
     * nextEntry is null.
     * @param nextEntry Entry after newEntry or null if there isn't one.
     * @param newEntry The new Entry to be inserted previous to nextEntry.
     */
    protected void add (Entry nextEntry, Entry newEntry) {
	// EXERCISE
	Entry previousEntry = null;
	///
	// Set previousEntry.  Two cases.
	// 1. what is the previous entry before new entry (previous entry) as long as im not at the end my previous entry is my nextentry.previous
	// if new entry is now my previous entry is last (dependent on last)
		if(nextEntry == null) {
			previousEntry = last;
		}else {
			previousEntry = nextEntry.previous;
		}
	// Set previousEntry.next or first to newEntry.
	// 2. update previous entry so that it points to my new entry if not at very beginning, previousentry.next needs to point to new entry
	// if inserting at entry, first needs to point at new entry (special case --> first)
		if(previousEntry == null) {
			first = newEntry;
		}else {
			previousEntry.next = newEntry;
		}
		//update to point to new entry check if null, update first

	// Set nextEntry.previous or last to newEntry.
	// 3. update next entry, if next entry is not null then nextentry.previous is the new entry
	// is at end == null, then last needs to equal new entry (special case if last)
		if(nextEntry == null) {
			last = newEntry;
		}else {
			nextEntry.previous = newEntry;
		}
	// Set newEntry.previous and newEntry.next.
	// 4. update new entry, so that new entries previous points to new entry, and fowards to the next entry
		newEntry.previous = previousEntry;
		newEntry.next = nextEntry;

    }

    public V get (Object keyAsObject) {
	// EXERCISE
	// Look at containsKey.
	// If Entry with key was found, return its value.
		K key = (K) keyAsObject;
		Entry entry = find(key);

		if(found(entry, key)){
			return entry.getValue();
		}
	// is my key in my list and if it is return the entry (use find), contains key

	///
	return null;
    }
  
    public V put (K key, V value) {
	Entry entry = find(key);
	// EXERCISE
	// Handle the case that the key is already there.
	// Save yourself typing:  setValue returns the old value!

	//use found and setvalue method
		if(found(entry, key)){
			return entry.setValue(value);
		}
	// lab
	// key is not there:
	Entry newEntry = new Entry(key, value);
	add(entry, newEntry);
	return null;
    }      
  
    protected class Iter implements Iterator<Map.Entry<K, V>> {
	Entry entry = first;

	// similar to linkedqueue iterator
	public boolean hasNext () {
		// EXERCISE
		//if (isEmpty()) {
		//	return true;
		//}

		//if (entry == null) {
		//	return false;
		//} else
		//	return true;

		return entry != null;
	}

    
	public Map.Entry<K, V> next () {
	    // EXERCISE
	    // Entry implements Map.Entry<K, V> so you return entry,
	    // not entry.key or entry.value.
	    // What else do you do?

		if(!hasNext()) {
			throw new NoSuchElementException();
		}

		Entry givenEntry = entry;
		entry = entry.next;

	    return givenEntry;

	}
    
	public void remove () {
	    throw new UnsupportedOperationException();
	}
    }
  
    /**
     * Remove Entry entry from list.
     * @param entry The entry to remove.
     */
    protected void remove (Entry entry) {


		if(entry == null){
			return;
		}

		Entry previous = entry.previous;
		Entry next = entry.next;

		if(previous == null){
			first = next;
		}
		else{
			previous.next = next;
			entry.previous = null;
		}

		if (next == null){
			last = previous;
		}

		else{
			next.previous = previous;
			entry.next = null;
		}

		// set up previous and next

    }

    public V remove (Object keyAsObject) {
	// EXERCISE
	// Use find, but make sure you got the right Entry!
	// If you do, then remove it and return its value.

		K key = (K) keyAsObject;
		Entry entry = find(key);

		if(entry == null || !entry.key.equals(key)){
			return null;
		}
		remove(entry);
		return entry.getValue();
    }      

    public int size () {
	int count = 0;
	for (Entry entry = first; entry != null; entry = entry.next)
	    count++;
	return count;
    }

    protected class Setter extends AbstractSet<Map.Entry<K, V>> {
	public Iterator<Map.Entry<K, V>> iterator () {
	    return new Iter();
	}
    
	public int size () { return LinkedMap.this.size(); }
    }
  
    public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
    static void test (Map<String, Integer> map) {
	if (false) {
	    map.put("Victor", 50);
	    map.put("Irina", 45);
	    map.put("Lisa", 47);
    
	    for (Map.Entry<String, Integer> pair : map.entrySet())
		System.out.println(pair.getKey() + " " + pair.getValue());
    
	    System.out.println(map.put("Irina", 55));

	    for (Map.Entry<String, Integer> pair : map.entrySet())
		System.out.println(pair.getKey() + " " + pair.getValue());

	    System.out.println(map.remove("Irina"));
	    System.out.println(map.remove("Irina"));
	    System.out.println(map.get("Irina"));
    
	    for (Map.Entry<String, Integer> pair : map.entrySet())
		System.out.println(pair.getKey() + " " + pair.getValue());
	}
	else {
	    String[] keys = { "Vic", "Ira", "Sue", "Zoe", "Bob", "Ann", "Moe" };
	    for (int i = 0; i < keys.length; i++) {
		System.out.print("put(" + keys[i] + ", " + i + ") = ");
		System.out.println(map.put(keys[i], i));
		System.out.println(map);
		System.out.print("put(" + keys[i] + ", " + -i + ") = ");
		System.out.println(map.put(keys[i], -i));
		System.out.println(map);
		System.out.print("get(" + keys[i] + ") = ");
		System.out.println(map.get(keys[i]));
		System.out.print("remove(" + keys[i] + ") = ");
		System.out.println(map.remove(keys[i]));
		System.out.println(map);
		System.out.print("get(" + keys[i] + ") = ");
		System.out.println(map.get(keys[i]));
		System.out.print("remove(" + keys[i] + ") = ");
		System.out.println(map.remove(keys[i]));
		System.out.println(map);
		System.out.print("put(" + keys[i] + ", " + i + ") = ");
		System.out.println(map.put(keys[i], i));
		System.out.println(map);
	    }
	    for (int i = keys.length; --i >= 0;) {
		System.out.print("remove(" + keys[i] + ") = ");
		System.out.println(map.remove(keys[i]));
		System.out.println(map);
		System.out.print("put(" + keys[i] + ", " + i + ") = ");
		System.out.println(map.put(keys[i], i));
		System.out.println(map);
	    }
	}
    }

    public static void main (String[] args) {
	Map<String, Integer> map = new LinkedMap<String, Integer>();
	test(map);
    }
}
