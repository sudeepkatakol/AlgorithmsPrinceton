/**
A randomized queue is similar to a stack or queue, except that the 
item removed is chosen uniformly at random from items in the data structure.
Basically, its a "Bag".
 
Implementation:
Resizing Array: For every iterator to be constructed a new array is created and the elements of the Queue are copied in random order.
Thus, the order of two or more iterators to the same randomized queue is mutually independent, i.e. each iterator maintains its own random order.
The array size grows by double when it is 100% full and shrinks to half its size when it is 25% full. 
This is essential to make sure enqueue and deque take constant amortized time.    
 

Exceptions:
Throws a java.lang.NullPointerException if the client attempts to add a null item;
Throws a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue; 
Throws a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
Throws a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
  
Performance: 
Randomized queue implementation supports each randomized queue operation (besides creating an iterator) in constant amortized time, i.e.
any sequence of m randomized queue operations (starting from an empty queue) should take at most cm steps in the worst case, for some constant c. 
A randomized queue containing n items uses at most 48n + 192 bytes of memory.
Iterator operations next() and hasNext() take constant worst-case time and construction of iterator takes linear time.
Uses a linear amount of extra memory per iterator.
*/



package randomizedqueues_solution;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] queue;
	private int size; //last-first=size at all times.
	public static final int INITIAL_CAPACITY=5; //initial capacity of the array.
	private int first; //denotes the index where deque operation takes place
	private int last; //denotes the index at which new Item is enqueued.
	
	// constructs an empty randomized queue
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		queue= (Item[]) new Object[INITIAL_CAPACITY];
		size=0;
		first=0;
		last=0;
	}
	
	//returns true if the queue is empty
	public boolean isEmpty() {
		return size==0;
	}
	
	//returns the number of items on the queue
	public int size() {
		return size;
	}
	
	//adds the item on the Randomized queue
	public void enqueue(Item item) {
		if(item!=null) {
			size=size+1;
			queue[last]=item;
			last=last+1;
			if(size==queue.length)
				resize(2*queue.length);
		}
		else
			throw new java.lang.NullPointerException();
	}
	
	//removes and returns a random item
	public Item dequeue() {
		if(size()>0) {
			size=size-1;			
			Item item = queue[first];
			queue[first]=null;
			first=first+1;
			if(size>0 && size==queue.length/4) 
				resize(queue.length/2);
			return item;
		}
		else
			throw new java.util.NoSuchElementException();
	}
	
	
	//returns (but do not remove) a random item
	public Item sample() {
		int random= StdRandom.uniform(first, last+1);
		return queue[random];
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		Item[] newQueue= (Item[]) new Object[capacity];
		for(int i=0, j=first; i<size && j<last; i++, j++)
			newQueue[i] = queue[j];
		queue = newQueue;
		first = 0; 
		last = size;
		newQueue=null;
	}
	
	//returns an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomIterator<Item>();
	}
	
	private class RandomIterator<Item> implements Iterator<Item> {
		private Item[] randomQueue;
		private int iteratingIndex;
		private int n;
		@SuppressWarnings("unchecked")
		public RandomIterator() {
			this.n=size; 
			randomQueue = (Item[]) new Object[n];
			for(int i=0, j=first; i<n && j<last; i++, j++) {
				randomQueue[i]= (Item) queue[j];
			}
			randomize();
			iteratingIndex=0;
		}
		
		private void randomize() {
			for(int i=0; i<n; i++) {
				int swapIndex=StdRandom.uniform(size);
				Item temp= randomQueue[i];
				randomQueue[i]=randomQueue[swapIndex];
				randomQueue[swapIndex]=temp;
			}
		}
		
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}
		
		public boolean hasNext() {
			return iteratingIndex!=n;
		}
		
		public Item next() {
			Item item= randomQueue[iteratingIndex];
			iteratingIndex=iteratingIndex+1;
			return item;
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> bag = new RandomizedQueue<Integer>();
		bag.enqueue(5);
		bag.enqueue(11);
        bag.enqueue(14);
        bag.enqueue(25);
        bag.enqueue(12);
        System.out.println(bag.queue);
        Iterator<Integer> it1= bag.iterator();
        bag.dequeue();
        Iterator<Integer> it2=bag.iterator();
        while(it1.hasNext()) {
        	System.out.print(it1.next() + " ");
        }
        System.out.println();
        while(it2.hasNext()) {
        	System.out.print(it2.next() + " ");
        }
        System.out.println();
	}

}
