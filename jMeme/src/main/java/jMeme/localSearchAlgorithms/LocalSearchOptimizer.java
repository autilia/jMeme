/*
 * LocalSearchOptimizer.java 
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

package jMeme.localSearchAlgorithms;

import jMeme.core.Optimizer;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.performances.MemeticAlgorithmPerformance;


/**
 *  This class implements a generic template for the local search optimizers developed in
 *  jMeme. Every local search optimizer must have a configuration including all its parameters inherited by the superclass. 
 *  The class declares an abstract method called <code>startToSolve</code>, which 
 *  defines the behavior of the optimizer.
 */
public abstract class LocalSearchOptimizer extends Optimizer {


	private static final long serialVersionUID = 4346539067609852805L;
	
	/**
	 * Stores the number of evaluations before starting the local procedure
	 */
	protected int initialEval;
	/**
	 * Stores the current number of evaluations
	 */
	protected int currentEval;

	
	/**
	 * Allows to execute the local search procedure
	 */
	public abstract void startToSolve();

	
	/**
	 * Allows to set the initial solution on which starting the local procedure
	 * 
	 * @param s  an individual to start the local procedure
	 */
	public void inizializeSolution(Individual s) {
		this.bestSolution = (Individual) s.clone();

	}
	
	
	/**
	 * Allows to increment the number of the interation during a local procedure	
	 */
	public void incrementIteration() {
		if (configuration.isMemeticConfig())
			((MemeticAlgorithmPerformance) configuration.getPerformance())
					.incrementNumLocalIterations();
		else
			configuration.getPerformance().incrementNumberOfIterations();
	}

	/**
	 * Allows to increment the number of evaluations performed during the local procedure
	 */
	public void updateLocalEvaluations() {
		if (configuration.isMemeticConfig())
			((MemeticAlgorithmPerformance) configuration.getPerformance())
					.setNumberLocalEvaluations(currentEval-initialEval);

	}

	
	/**
	 * Allows to set variables of the local procedure to initial values
	 */
	public void localReset(){
		if (configuration.isMemeticConfig()){
			((MemeticAlgorithmPerformance) configuration.getPerformance()).setNumberLocalEvaluations(0);
		
			((MemeticAlgorithmPerformance) configuration.getPerformance()).setNumberLocalIterations(0);		
		}
	}
	
	
	/**
	 * Allows to compute the best solution between two given solutions
	 * 
	 * @param s1  a solution to be compared
	 * @param s2  a solution to be compared
	 * @param isMaximizing  a boolean value indicating if the the problem is to maximize (true) or minimize (false)
	 * @return true if the first solution is better than the second one, false otherwise
	 */
	public boolean isFirstBetterThanSecond(Individual s1,Individual s2, 
            final boolean isMaximizing){

		int comparison = s1.compareTo(s2);
		
		if(comparison > 0)
		return isMaximizing ? true : false;
		else if (comparison <0) 
		return isMaximizing ? false : true;
		
		return false;
}
	
	
	
	/**
	 * Allows to print the performance of the local search procedure
	 * 
	 * @return  a string containing the performance of the local search procedure
	 */
	public String printStatistics(){
		String result="";
		if (configuration.isMemeticConfig()){
			result+="Iterations "+((MemeticAlgorithmPerformance) configuration.getPerformance()).getNumberLocalIterations();
			result+=" Evaluations "+((MemeticAlgorithmPerformance) configuration.getPerformance()).getNumberLocalEvaluations();
					
		}else{
			result+="Iterations "+ configuration.getPerformance().getNumberOfIterations();
			result+=" Evaluations "+ configuration.getPerformance().getNumberOfFitnessEvaluations();
		
		}
		return result;
	}
	
	
	public int getIterations(){
		if (configuration.isMemeticConfig())
			return ((MemeticAlgorithmPerformance) configuration.getPerformance()).getNumberLocalIterations();
			
			
		return configuration.getPerformance().getNumberOfIterations();
		
		
	}
	
	public FinalConditions getFinalConditions(){
		if (configuration.isMemeticConfig())
			return ((MemeticAlgorithmConfiguration) configuration).getLocalFinalConditions();
		
		return configuration.getFinalConditions();
	}
	
	
	
	public int getNumberOfEvaluations(){
		if (configuration.isMemeticConfig())
			return ((MemeticAlgorithmPerformance) configuration.getPerformance()).getNumberLocalEvaluations();
			
			
		return configuration.getPerformance().getNumberOfFitnessEvaluations();
		
		
	}
	
	

	

	
	public boolean isMaximize() {
		return configuration.getFitnessFeatures().isMaximize();
	}

	
	public int getInitialEval() {
		return initialEval;
	}

	
	public void setInitialEval(int initialEval) {
		this.initialEval = initialEval;
	}


	public int getCurrentEval() {
		return currentEval;
	}

	
	public void setCurrentEval(int currentEval) {
		this.currentEval = currentEval;
	}
	
	

}
