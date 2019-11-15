package lab8Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedDictionary <K extends Comparable <? super K> , V> implements DictionaryInterface <K, V> {
	private DictNode head;
	private DictNode tail;
	private int numberOfEntries;

	public SortedLinkedDictionary () {
		head = null; 
		tail = null; 
		numberOfEntries = 0;
	}

	public V getValue (K key) {
		V result = null;

		if(isEmpty()) {
			return result;
		}

		DictNode curr = head;

		while (curr != null && key.compareTo (curr.getKey()) > 0)
			curr = curr.getNext(); // check if you find the key

		if (curr == result) {
			return result; // the current is empty
		}

		else if (key.equals(curr.getKey())) {
			return curr.getValue(); // the key exists
		}

		return result;
	}

	public int getSize () {
		return numberOfEntries;
	}

	public void clear () {
		head = null;
		tail = null;
	}

	public boolean isEmpty () {return (head == null) && (tail == null);}

	public boolean contains (K aKey) {
		boolean found = false;
		DictNode currNode = head;

		while (!found && (currNode != null)) {
			if(aKey.equals(currNode.getKey())) {found = true;}
			else {currNode = currNode.getNext();}
		}
		return found;
	}

	private DictNode getNodeAfterKey (K aKey) {
		DictNode curr = head;
		while (curr != null && aKey.compareTo (curr.getKey()) > 0)
			curr = curr.getNext();
		return curr;	
	}

	public V add (K newKey, V newValue ) {
		DictNode nodeAfter;	
		DictNode toAdd = new DictNode (newKey, newValue);

		if (isEmpty()) {
			head = tail = toAdd;
			numberOfEntries = 1;
			return null;
		}  
		nodeAfter = getNodeAfterKey(newKey); 

		if (null == nodeAfter) {
			tail.setNext (toAdd);
			toAdd.setPrev (tail);
			tail = toAdd;
		}

		else if (newKey.equals (nodeAfter.getKey())) {
			//System.out.println ("Replacing value for " + newKey);
			V oldValue = nodeAfter.getValue();
			nodeAfter.setValue(newValue);
			return oldValue;
		}

		else if (nodeAfter == head) {
			toAdd.setNext(head);
			head.setPrev (toAdd);
			head = toAdd;
		}

		else {
			DictNode prevNode = nodeAfter.getPrev();
			prevNode.setNext(toAdd);
			toAdd.setPrev(prevNode);
			toAdd.setNext(nodeAfter);
			nodeAfter.setPrev(toAdd);
		}
		numberOfEntries ++;
		return null;	
	}

	// TODO: finish implementation
	public V remove (K aKey) {
		V result = null;

		for (DictNode curr = head; curr != null && aKey.compareTo(curr.getKey()) > 0; curr = curr.getNext()) {
			if (aKey.equals(curr.getKey())) {
				result = curr.getValue();
				if (numberOfEntries == 1) {
					clear();
				}
				else if (curr == head) {
					head = head.getNext();
					head.setPrev(null);
					numberOfEntries --;
				}
				else if (curr == tail) {
					tail = tail.getPrev();
					tail.setNext(null);
					numberOfEntries --;
				}
				else {
					DictNode before = curr.getPrev();
					DictNode after = curr.getNext();
					curr = null;
					before.setNext(after);
					after.setPrev(before);
					numberOfEntries --;
				}
			}

		}
		return result;
	}

	public Iterator <K> getKeyIterator () {return new KeyIteratorForLinkedDictionary ();}
	public Iterator <V> getValueIterator () {return new ValueIteratorForLinkedDictionary ();}

	private class KeyIteratorForLinkedDictionary implements Iterator <K> {
		private DictNode nextNode;

		public KeyIteratorForLinkedDictionary () {
			nextNode = head;
		}

		public boolean hasNext () {
			return (nextNode != null);
		}

		public K next () {
			if (!hasNext())
				throw new IllegalStateException ("Iteration after the list");
			K result = nextNode.getKey();
			nextNode = nextNode.getNext();
			return result;
		}

		@Override
		public void remove ()  {throw new UnsupportedOperationException ("No remove in dictionary iterator");}
	}

	private class ValueIteratorForLinkedDictionary implements Iterator <V> {
		DictNode next;

		public ValueIteratorForLinkedDictionary () {
			next = head;
		}

		@Override
		public boolean hasNext () {return next != null;}

		@Override
		public V next () {
			V result;

			if(hasNext()) {result = next.getValue(); next = next.getNext();}
			else {throw new NoSuchElementException();}
			return result;
		}
	}

	private class DictNode {
		private K key;
		private V value;
		private DictNode next;
		private DictNode prev;

		public DictNode (K newKey, V newValue) {
			key = newKey; value = newValue;
			next = null; prev = null;
		}

		public K getKey () { return key;}
		public V getValue () {return value;}
		public void setValue (V newValue) { value = newValue; }
		public DictNode getNext () {return next;}
		public DictNode getPrev () {return prev;}
		public void setNext (DictNode n) {next = n;}
		public void setPrev (DictNode p) {prev = p;}
	}
}
