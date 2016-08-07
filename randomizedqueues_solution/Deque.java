/* 
A double-ended queue or deque (pronounced "deck") is a generalization 
of a stack and a queue that supports inserting and removing items from 
either the front or the back of the data structure.
This is an implementation for the deque data structure.

Implementation:
The generic class Deque provides an implementation for deque using Linked Lists.
A private class (BasicIterator) implements the Iterator interface to provide
a basic iterator for the deque. 

Performance:
The deque implementation supports each deque operation (including construction)
in constant worst-case time and use space proportional to the number of items 
currently in the deque. 
Additionally, the iterator implementation supports each operation 
(including construction) in constant worst-case time.
*/

package randomizedqueues_solution;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
	//Variables that store information about the start, end and size of the deque.
	private Node<Item> head;
	private Node<Item> tail;
	private int size;
	
	//Basic unit of our doubly linked list (deque).
	private class Node<Item>
	{
		private Item item;
		private Node<Item> next;
		private Node<Item> previous;
		
		public Node(Item item)
		{
			this.item=item;
			this.next=null;
			this.previous=null;
		}
	}
	   
	//constructs an empty deque
	public Deque()
	{
		head=null;
		tail=null;
		size=0;
	}
		
	//returns true if the deque is empty
	public boolean isEmpty()                
	{
		return size==0;
	}
		
	// returns the number of items on the deque
	public int size()                       
	{
		return size;	
	}
		
	// inserts the item at the front of the deque
	public void addFirst(Item item)          
	{
		if(item!=null){
			Node<Item> newNode=new Node<Item>(item);
			newNode.next=head;
			head=newNode;
			newNode=null;
			size++;
		}
		else 
			throw new java.lang.NullPointerException();
	}
		
	// inserts the item at the end of the deque
	public void addLast(Item item)           
	{
		if(item!=null){
			Node<Item> newNode=new Node<Item>(item);
			tail.next=newNode;
			newNode.previous=tail;
			tail=newNode;
			newNode=null;
			size++;
		}
		else 
			throw new java.lang.NullPointerException();
	}
		
	// deletes and returns the item at the front of the deque
	public Item removeFirst()                
	{
		if(!isEmpty()){
			Node<Item> first=head;
			head=head.next;
			first.next=null;
			size--;
			return first.item;
		}
		else
			throw new java.util.NoSuchElementException();
	}
		
	// deletes and returns the item at the end of the deque
	public Item removeLast()
	{
		if(!isEmpty()){
			Node<Item> last=tail;
			tail=tail.previous;
			last.previous=null;
			size--;
			return last.item;	
		}
		else
			throw new java.util.NoSuchElementException();
	}
		
	// return an iterators over items in order from front to end
	public Iterator<Item> iterator()
	{
		return new BasicIterator<Item>(head);
	}	
	
	/* Class that implements the Iterator interface.
	 * Provides a very basic iterator that iterates from the front to the end of the deque.
	 * This class does not support the remove method of the Iterator interface.
	 */
	private class BasicIterator<Item> implements Iterator<Item>
	{
		private Node<Item> current;
			
		public BasicIterator(Node<Item> head)
		{
			current = head;
		}
			
		public void remove()
		{
			throw new java.lang.UnsupportedOperationException();
		}
		
		public boolean hasNext()
		{
				return current!=null;
		}
			
		public Item next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current=current.next;
			return item;
		}
	}

		
	// unit testing
	public static void main(String[] args)   
	{
        Deque<String> deque = new Deque<String>();
        for(int i=0; i<args.length; i++) {
            String item = args[i];
            if (!item.equals("-")) {
                deque.addFirst(item);
            }	
            else if (!deque.isEmpty()) {
                StdOut.print(deque.removeFirst() + " ");
                System.out.println();
                Iterator<String> it= deque.iterator();
                while(it.hasNext()){
                	System.out.print(it.next()+ " ");
                	}
                System.out.println();
            }
        }
        StdOut.println("(" + deque.size() + " left on stack)");
	}
}
