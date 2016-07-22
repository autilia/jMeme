/*
 * GenerationExecutor.java 
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

package jMeme.design.generationExecutor;


import java.io.Serializable;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;

/**
 *  This class implements a generic template for the population generation strategies developed in
 *  jMeme. A population generation strategy can use a local optimizer to improve the initial random population. 
 *  The class declares an abstract method called <code>execute</code>, which 
 *  defines the behavior of the population generation strategy. 
 *
 */
public abstract class GenerationExecutor implements Serializable {

	private static final long serialVersionUID = -1553545538294074234L;

	
	/**
	 * Stores the local optimizer used to improve individuals of the initial random population 
	 */
	protected LocalSearchOptimizer metaH;
	
	
	/**
	 * Constructor
	 */
	public GenerationExecutor(){
		metaH=null;
	}
	
	/**
	 * Constructor 
	 * @param localOptimizer  object that performs the local optimization of individuals of the initial random population
	 */
	public GenerationExecutor(LocalSearchOptimizer localOptimizer){
		metaH=localOptimizer;
	}
	
	/**
	 * Allows to create an initial population for the global search algorithms
	 * 
	 * @param configuration  object that contains all information about the algorithm to run
	 * @return the initial population of individuals to evolve 
	 */
	public abstract JPopulation execute(JConfiguration configuration);
	
	


}
