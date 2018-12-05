/*
 * JFitnessFunction.java 
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

import jMeme.core.fitnessFunction.FitnessEvaluationMethod;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.core.individuals.Individual;

import org.jgap.IChromosome;


/**
 * This class implements the generic fitness function used to evaluate a solution 
 * of a problem to be solved.
 */
public class JFitnessFunction extends org.jgap.FitnessFunction {

	
	private static final long serialVersionUID = -325571978104161377L;

	
	/**
	 * Stores the configuration of the algorithm   
	 */
	protected JConfiguration configuration;
	
	
	/**
	 * Stores a reset value for the fitness
	 */
	protected double reset_fitness;
	
	/**
	 * Stores the implementation of the fitness function specific for the problem to be solved
	 */
	protected FitnessEvaluationMethod evalMethod;

	
    /**
     * Constructor 
     * 
     * @param eval object that contains the implementation of the fitness function 
     * for a specific problem.
     */
	public JFitnessFunction(FitnessEvaluationMethod eval){
		super();
		evalMethod=eval;
		
	}
	
	/**
	 * Performs the evaluation of a solution.
	 * @param individual solution to be evaluated.
	 */
	@Override
	protected double evaluate(IChromosome individual) {
		
		configuration=(jMeme.core.JConfiguration)individual.getConfiguration();
		
		//increment number of fitness evaluations
	     configuration.getPerformance().incrementNumberOfFitnessEvaluations();
	     
	     //set the number of fitness evaluation where the individual has been generated
	     ((Individual)individual).setNumberOfEvaluationsToBeGenerated(configuration.getPerformance().getNumberOfFitnessEvaluations());
		
		//check of number of fitness evaluations
				if(initialCheck(individual))
					return reset_fitness;
				
			//fitness implemented by the user
				
			//useful to compute the fitness time
			long startTime=System.currentTimeMillis();
			
			double fitness=evalMethod.evaluate((Individual)individual);
			
			//added in the version jMeme 1.0.1
			//set the time occurred to execute the single fitness evaluation
			configuration.getPerformance().setTimeFitnesses(System.currentTimeMillis()-startTime);
			
			return	fitness;
				
	}
	

	
	private boolean initialCheck(IChromosome a) {
		
     			if(configuration.getFinalConditions().checkNumberEvaluations(configuration)){
						configuration.getPerformance().decrementNumberOfFitnessEvaluations();
						if(configuration.getFitnessFeatures().getFitnessSign()==FitnessFeatures.MIN)
						   {reset_fitness=Double.MAX_VALUE;
						   return true;}
						else {
							reset_fitness= Double.MIN_VALUE;
							return true;
						}
					}
					
					return false;
		
	}


	
	public JConfiguration getConfiguration() {
		return configuration;
	}


	
	public void setConfiguration(JConfiguration configuration) {
		this.configuration = configuration;
	}


	public double getDefault_fitness() {
		return reset_fitness;
	}


	public void setDefault_fitness(double default_fitness) {
		this.reset_fitness = default_fitness;
	}


	
	public FitnessEvaluationMethod getEvalMethod() {
		return evalMethod;
	}


	public void setEvalMethod(FitnessEvaluationMethod evalMethod) {
		this.evalMethod = evalMethod;
	}

	
	

}
