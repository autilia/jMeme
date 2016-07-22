/*
 * GlobalSearchOptimizer.java 
 * 
 * This file is part of the jMeme library, the Evolutionary Computational tool 
   for designing Competent Memetic Algorithms (CMAs).
   
   Author:  Autilia Vitiello <avitiello@unisa.it>

   Copyright (c) 2016  Autilia Vitiello
	
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Lesser General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Lesser General Public License for more details.
 
   You should have received a copy of the GNU Lesser General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
   
 */

package jMeme.globalSearchAlgorithms;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.Optimizer;
import jMeme.core.individuals.Individual;


/**
 *  This class implements a generic template for the global search optimizers developed in
 *  jMeme. Every global search optimizer must have a configuration including all its parameters inherited by the superclass. 
 *  The class declares an abstract method called <code>execute</code>, which 
 *  defines the behavior of the optimizer.
 */
public abstract class GlobalSearchOptimizer extends Optimizer {

	private static final long serialVersionUID = -553008818943098951L;
	
	/**
	 * Stores the fitness value of the current best solution
	 */
	protected double bestFitness;
	
	/**
	 * Stores the current best solution
	 */
	protected Individual currentBest;

	
	
	
	
	/**
	 * Allows to determine the best solution given a population
	 * 
	 * @param pop  the population where the best solution must be determined
	 */
	public abstract void determineBest(JPopulation pop);
	
	/**
	 * Allows to execute the global search optimizer
	 * 
	 * @param pop  the population to evolve
	 * @param conf  the algorithm configuration containing all information to run the global search optimizer
	 * @return
	 */
	public abstract JPopulation execute(JPopulation pop, JConfiguration conf);
	
	/**
	 * Allows to set the variables of the global search optimizer to initial values
	 */
	public abstract void reset();
	
	/**
	 * Allows to compare this global search optimizer with other one.
	 * 
	 * @param a_other  the global search optimizer to be compared
	 * @return  The value 0 if the class name of the argument is a string lexicographically to the class name of this object; a value less than 0 if the class name of the argument is a string lexicographically greater than the class name of this object; and a value greater than 0 if the class name of the argument is a string lexicographically less than the class name of this object.
	 */
	public abstract int compareTo(Object a_other);
	
	
	
	/**
	 * Allows to clone the global search optimizer
	 */
	public abstract Object clone();
	 
	
	/**
	 * Allows to compare fitness values and answers to the following question: is the fitness value of the new solution better than the fitness value of the current solution?
	 * 
	 * @param newFitness  the fitness value of the new solution
	 * @param fitness  the fitness value of the current solution
	 * @return  true if the new solution is better than the current solution, false otherwise
	 */
	 public boolean compareFitness(double newFitness, double fitness){

			if((configuration.getFitnessFeatures().isMaximize() && (newFitness > fitness))
				|| (!configuration.getFitnessFeatures().isMaximize() && (newFitness < fitness)))
				   return true;
			
			return false;
		}
	

	 
		public double getBestFitness(){
			return bestFitness;
		}


		public Individual getCurrentBest() {
			return currentBest;
		}


		public void setCurrentBest(Individual currentBest) {
			this.currentBest = currentBest;
		}


		public void setBestFitness(double bestFitness) {
			this.bestFitness = bestFitness;
		}
		
		
	 

}
