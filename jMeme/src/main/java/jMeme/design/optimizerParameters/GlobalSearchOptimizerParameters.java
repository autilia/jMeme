/*
 * GlobalSearchOptimizerParameters.java 
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

package jMeme.design.optimizerParameters;


/**
 * This class represents the superclass for all classes aimed at specifying the parameters of global search algorithms.
 */
public class GlobalSearchOptimizerParameters extends OptimizerParameters {

		
	private static final long serialVersionUID = -8462380109782682664L;
	
	/**
	 * Stores the number of the individuals in a population
	 */
	protected int populationSize;

	/**
	 * Constructor
	 */
	public GlobalSearchOptimizerParameters()
	{
		populationSize=20;
		
	}
	
	/**
	 * Constructor 
	 * 
	 * @param popSize the population size representing the parameter common to all global search algorithms
	 */
	public GlobalSearchOptimizerParameters(int popSize)
	{
		populationSize=popSize;
		
	}
	
	


     /**
      * representation string of parameters of a global search algorithm
      */
	public  String toString(){
		return "population size " + this.populationSize;
	}
	


	public int getPopulationSize() {
		return populationSize;
	}
	
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	

	
}
