/**
 * This is an example client program that takes a command-line integer k. 
 * It reads in a sequence of N strings from standard input using StdIn.readString(); 
 * and prints out exactly k of them, uniformly at random. Each item from the sequence is printed out at most once. 
 * We assume that 0 ≤ k ≤ n, where N is the number of string on standard input.
 * 
 * Performance:
 * The running time of Subset is linear in the size of the input.
 * 
 * Implementation:
 * The class uses RandomizedQueue data structure of size N for its functionality.
 */


package randomizedqueues_solution;

import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class Subset {

	public static void main(String[] args) {
		int k= Integer.parseInt(args[0]);
		RandomizedQueue<String> subset= new RandomizedQueue<String>();
		while(!StdIn.isEmpty()) {
			String input = StdIn.readString();
			subset.enqueue(input);
		}
		Iterator<String> it = subset.iterator();
		while(k-->0) {
			StdOut.println(it.next());
		}
	}
}