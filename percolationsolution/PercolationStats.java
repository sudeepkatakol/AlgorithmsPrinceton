/*
This class performs a Monte Carlo simulation to calculate the percolation threshold 
(Refer to Percolation.java)

Monte Carlo simulation: Initialize all sites to be blocked.
Repeat the following until the system percolates:
	Choose a site (row i, column j) uniformly at random among all blocked sites.
	Open the site (row i, column j).
	The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.

The mean of the all the fractions obtained from the experiments is the estimate for percolation threshold p*;
Also the standard deviation of the experiment can be obtained 
alongwith the upper and lower limits of 95% confidence interval for the mean; 

Assumption: The no. of trials are more than 30 making central-limit theorem applicable. 
 */

package percolationsolution;

import edu.princeton.cs.algs4.*;

import percolationsolution.Percolation;

public class PercolationStats 
{
	//stores results of the experiment;
	private double[] fraction;
	
	//stores mean and stddev for calculating Confidence Interval
	private double mean,stddev;
	
	//stores the parameter passed to the constructors;
	private int trials;
	
	// performs 'trials' no. of independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials)
	{
		if(n>0 && trials>0){
			fraction=new double[trials];
			this.trials=trials;
			for(int i=0; i<trials; i++){
				int count=0;
				Percolation percolation = new Percolation(n);
				while(!percolation.percolates()){
					int p=StdRandom.uniform(n)+1;
					int q=StdRandom.uniform(n)+1;
					if(!percolation.isOpen(p,q)){
						percolation.open(p,q);
						count++;
					}
				}
				fraction[i]=(double)count/(n*n);
				System.out.println(fraction[i]);
			}
		}
		else
			throw new java.lang.IllegalArgumentException();
	}
	
	
	// sample mean of percolation threshold
	public double mean()
	{
		this.mean=StdStats.mean(fraction);
		return mean;
	}
	
	 // sample standard deviation of percolation threshold
	public double stddev()                       
	{
		this.stddev=StdStats.stddev(fraction);
		return stddev;
	}
	 
	// low  endpoint of 95% confidence interval
	public double confidenceLo()                 
	{
		return mean-1.96*stddev/Math.sqrt(trials);
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi()                  
	{
		return mean+1.96*stddev/Math.sqrt(trials);
	}
	
	// test client (described below)
	public static void main(String[] args)    
	{
		   int N = Integer.parseInt(args[0]);
	       int T = Integer.parseInt(args[1]);
	       PercolationStats stats = new PercolationStats(N,T);
	       StdOut.println("mean = "+ stats.mean());
	       StdOut.println("standard deviation = "+ stats.stddev());
	       StdOut.println("95% confidence interval = "+ stats.confidenceLo() + " , " + stats.confidenceHi());
	}
}

