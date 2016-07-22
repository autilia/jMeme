/*
 * LocalOptimizerExecutor.java 
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


package jMeme.design.localIntegration;

import jMeme.core.JPopulation;
import jMeme.design.replacementStrategy.ReplacementStrategy;
import jMeme.design.replacementStrategy.WorstReplacementStrategy;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;


/**
 *  This class implements a generic template for the local optimizer executors developed in
 *  jMeme. Every local optimizer executor must have a local optimizer to execute on individuals of the population
 *  and a replacement strategy aimed at specifying how the improved individuals must be introduced in the population. 
 *  The class declares an abstract method called <code>execute</code>, which 
 *  defines the behavior of the local optimizer executor.
 */
public abstract class LocalOptimizerExecutor {

	/**
	 * Constant indicating the best selection scheme as the individual selection scheme for the local procedure
	 * See the following work for more details:
	 * Q. H. Nguyen, Y. S. Ong and N. Krasnogor, "A study on the design issues of Memetic Algorithm," 2007 IEEE Congress on Evolutionary Computation, Singapore, 2007, pp. 2390-2397.
     * doi: 10.1109/CEC.2007.4424770
	 */
	public static final String BEST_SELECTION_SCHEME="Best";
	/**
	 * Constant indicating the random selection scheme as the individual selection scheme for the local procedure
	 * Q. H. Nguyen, Y. S. Ong and N. Krasnogor, "A study on the design issues of Memetic Algorithm," 2007 IEEE Congress on Evolutionary Computation, Singapore, 2007, pp. 2390-2397.
     * doi: 10.1109/CEC.2007.4424770
	 */
	public static final String RANDOM_SELECTION_SCHEME="Random";
	/**
	 * Constant indicating the stratified selection scheme as the individual selection scheme for the local procedure
	 * Q. H. Nguyen, Y. S. Ong and N. Krasnogor, "A study on the design issues of Memetic Algorithm," 2007 IEEE Congress on Evolutionary Computation, Singapore, 2007, pp. 2390-2397.
     * doi: 10.1109/CEC.2007.4424770
     */
	public static final String STRATIFIED_SELECTION_SCHEME="Stratified";
	
	/**
	 * Constant indicating the default selection scheme for the local procedure
	 */
	public static final String DEFAULT_SELECTION_SCHEME=BEST_SELECTION_SCHEME;
	
	
	/**
	 * Stores the local search optimizer that the local optimizer executor must apply on individuals of the population
	 */
	protected LocalSearchOptimizer localOptimizer;
	/**
	 * Stores the information about the replacement strategy the local optimizer executor must apply 
	 * to introduce the improved individuals in the population
	 */
	protected ReplacementStrategy replacementStrategy;
	
	
	/**
	 * Constructor
	 * 
	 * @param localOptimizer  local procedure to be applied on the individuals of the population
	 */
	public LocalOptimizerExecutor(LocalSearchOptimizer localOptimizer){
		this.localOptimizer=localOptimizer;
		replacementStrategy=new WorstReplacementStrategy();
	}
	
	/**
	 * Constructor
	 * 
	 * @param localOptimizer  local procedure to be applied on the individuals of the population
	 * @param strategy  replacement strategy to be applied in order to introduce the improved individuals in the population
	 */
	public LocalOptimizerExecutor(LocalSearchOptimizer localOptimizer, ReplacementStrategy strategy){
		this.localOptimizer=localOptimizer;
		replacementStrategy=strategy;
	}
	
	/**
	 * Allows to execute the specified local improvement procedure on the given population
	 * @param p  the population on which the specified local optimizer must be applied
	 */
	public abstract void execute(JPopulation p);
	
	
	public ReplacementStrategy getReplacementStrategy() {
		return replacementStrategy;
	}


	public void setReplacementStrategy(ReplacementStrategy replacementStrategy) {
		this.replacementStrategy = replacementStrategy;
	}

	
	public LocalSearchOptimizer getLocalOptimizer() {
		return localOptimizer;
	}

	
	public void setLocalOptimizer(LocalSearchOptimizer metaH) {
		this.localOptimizer = metaH;
	}

	
	
}
