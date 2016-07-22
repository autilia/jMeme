/*
 * Algorithm.java 
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

package jMeme.core;

import jMeme.core.individuals.Individual;


/** 
 *  This class implements a generic template for the algorithms developed in
 *  jMeme. Every algorithm must have a configuration including all its parameters 
 *  and a problem to solve. 
 *  The class declares an abstract method called <code>execute</code>, which 
 *  defines the behavior of the algorithm.
 */ 

public abstract class Algorithm {

	
	/**
	 * Stores the problem to solve 
	 */
	protected Problem problem;

	
	/**   
	  * Launches the execution of an specific algorithm.
	  * 
	  * @return an <code>Individual</code> that is a solution for the problem to solve	 
	  */
	public abstract Individual execute();
	
	
	/**   
	  * Executes the setting of the variables of an specific algorithm to the initial values. 
	  */
	public abstract void reset();
	
	
	public Problem getProblem() {
		return problem;
	}

	
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	
	public abstract JConfiguration getConfiguration();
	
}