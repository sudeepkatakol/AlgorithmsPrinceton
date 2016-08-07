/* This class models the Percolation problem.
   
   The model. We model a percolation system using an N-by-N grid of sites. 
   Each site is either open or blocked. A full site is an open site that can be 
   connected to an open site in the top row via a chain of neighboring 
   (left, right, up, down) open sites. We say the system percolates if there is a full 
   site in the bottom row. In other words, a system percolates if we fill all open sites 
   connected to the top row and that process fills some open site on the bottom row. 
   (For the insulating/metallic materials example, the open sites correspond to 
   metallic materials, so that a system that percolates has a metallic path from top to bottom,
   with full sites conducting. For the porous substance example, the open sites correspond
   to empty space through which water might flow, so that a system that percolates lets
   water fill open sites, flowing from top to bottom.)
   
   The problem: If sites are independently set to be open with probability p 
   (and therefore blocked with probability 1 − p), what is the probability that the system percolates? 
   When p equals 0, the system does not percolate; when p equals 1, the system percolates.
   When N is sufficiently large, there is a threshold value p* such that when p < p* 
   a random N-by-N grid almost never percolates, and when p > p*, a random N-by-N grid
   almost always percolates. No mathematical solution for determining the percolation
   threshold p* has yet been derived. 
   Refer to PercolationStats.java which determines the threshold p*.
   
   Applications: Given a composite systems comprised of randomly distributed insulating 
   and metallic materials: what fraction of the materials need to be metallic so that
   the composite system is an electrical conductor? Given a porous landscape with water
   on the surface (or oil below), under what conditions will the water be able to drain
   through to the bottom (or the oil to gush through to the surface)? Scientists have
   defined an abstract process known as percolation to model such situations.
   
   Implementation: Firstly, we create two virtual sites:
   A 'top virtual site' connected to all the sites of the top row of the grid 
   and a 'bottom virtual site' connected to all the sites of the bottom grid.
   The system percolates if the bottom_virtual_site is full.   
   Now, we model all the grid sites along with the top and bottom virtual sites 
   to be N^2+2 nodes indexed from 0 to N^2+1. 
   The nodes with indices 0 & N^2+1 are the top and bottom virtual sites respectively.
   Now the problem reduces to be a "dynamic connectivity" problem where we ought to check
   if nodes 0 & N^2+1 are connected.
   The data structure WeightedQuickUnionFind helps in implementing this model.
   
   Performance: The constructor should take time proportional to N^2.
   All methods take constant time plus a constant number of calls 
   to the WeightedQuickUnionFind methods union(), find(), connected(), and count().

   Exceptions: By convention, the row and column indices i and j are integers between 0 and N − 1, where (0, 0) is the upper-left site: 
   A java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range.
   The constructor throws a java.lang.IllegalArgumentException if N ≤ 0.
 */

package percolationsolution;

import edu.princeton.cs.algs4.*;

public class Percolation 
{
	//number of rows and columns in the grid
	private final int n;
	
	//boolean array to store whether a site is open or closed
	private boolean[] states;
	
	//data structure for solving the problem
	private WeightedQuickUnionUF uf;
	
	/* create n-by-n grid, with all sites blocked
	 * initializing the data structures with N^2+2 nodes
	 * setting the virtual sites to open state.
	 */
	public Percolation(int n)               
	{
		if(n>0){
			this.n=n;
			uf=new WeightedQuickUnionUF(n*n+2);
			states=new boolean[n*n+2]; 
			for(int i=1; i<=n*n; i++) {   
				states[i]=false;
			}
			states[0]=states[n*n+1]=true; 
		}
		else 
			throw new java.lang.IllegalArgumentException();
	}
	
	//maps the grid site to indices of linear array
	private int siteindex(int i, int j)
	{
		return i*n+j+1; 
	}
	
	//checks if index is valid
	private boolean validate(int i, int j){
		return i>=0 && i<=n-1 && j>=0 && j<=n-1; 
	}
	
	//opens site (row i, column j) if it is not open already
	public void open(int i, int j) throws java.lang.IndexOutOfBoundsException       
	{
		if(validate(i,j)){
			int index=siteindex(i,j);
			if(!states[index]){
				states[index]=true;
				if(validate(i-1,j) && isOpen(i-1,j)) uf.union(index, siteindex(i-1,j));
				if(validate(i,j+1) && isOpen(i,j+1)) uf.union(index, siteindex(i,j+1));
				if(validate(i+1,j) && isOpen(i+1,j)) uf.union(index, siteindex(i+1,j));
				if(validate(i,j-1) && isOpen(i,j-1)) uf.union(index, siteindex(i,j-1));
				if(i==0) uf.union(index, 0);
				if(i==n-1) uf.union(index, n*n+1);
			}
		}
		else 
			throw new java.lang.IndexOutOfBoundsException();
	}
	
	//returns true if site (row i, column j) open
	public boolean isOpen(int i, int j)     
	{
		if(validate(i,j)){
			return states[siteindex(i,j)];
		}
		else 
			throw new java.lang.IndexOutOfBoundsException();
	}
	
	//returns true if site (row i, column j) full
	public boolean isFull(int i, int j)     
	{
		if(validate(i,j)){
			int index=siteindex(i,j);
			if(states[index]){
				return uf.connected(0, index);
			}
			else 
				return false;
		}
		else
			throw new java.lang.IndexOutOfBoundsException();
		
	}
	 
	//return true if the system percolate
	public boolean percolates()            
	{
		return uf.connected(0, n*n+1);
	}
	
	 // test client (can be ignored)
	public static void main(String[] args) 
	{
		Percolation percolation=new Percolation(3);
		percolation.open(0, 0);
		percolation.open(0, 1);
		percolation.open(1, 1);
		percolation.open(2, 0);
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++)
				System.out.print(percolation.isFull(i, j) + " ");
			System.out.println();
		}
		//percolation.open(1, 2);
		//percolation.open(1, 0);
		System.out.println(percolation.percolates());
	}	
}
