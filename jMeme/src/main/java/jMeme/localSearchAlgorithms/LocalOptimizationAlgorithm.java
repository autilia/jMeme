/*
 * LocalOptimizationAlgorithm.java 
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jgap.InvalidConfigurationException;

import jMeme.core.Algorithm;
import jMeme.core.JConfiguration;
import jMeme.core.Problem;
import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.performances.AlgorithmPerformance;


/**
 *  This class implements the generic local search algorithm. It must have a configuration including all its parameters 
 *  and a problem to solve. The configuration contains also the local search optimizer to be executed in order to transform this generic local search algorithm in a specific one.
 *  The class overrides the method <code>execute</code> of the superclass in order to define the behavior of a generic local search algorithm.
 */
public class LocalOptimizationAlgorithm extends Algorithm{

	
	/**
	 * Stores the configuration containing all information necessary to run the algorithm 
	 */
	protected LocalSearchAlgorithmConfiguration configuration;
	/**
	 * Stores the object containing all information about the performance of the algorithm  
	 */
	protected AlgorithmPerformance performance;
	
	/**
	 * Stores the local optimizer to run 
	 */
	protected LocalSearchOptimizer localOptimizer;
	
	/**
	 * Constructor 
	 * 
	 * @param p  the problem to solve
	 * @param conf  the configuration containing all information to run the local search algorithm
	 */
	public LocalOptimizationAlgorithm(Problem p, LocalSearchAlgorithmConfiguration conf ) {
		
		this.problem=p;
		
		
		this.configuration=conf;
			
		this.localOptimizer=this.initializeLocalOptimizer();
		
		performance = configuration.getPerformance();
		
				
		try {
			configuration.reset();
			configuration.setFitnessEvaluator(configuration.getFitnessFeatures().getFitnessEvaluator());
			configuration.setFitnessFunction(configuration.getFitnessFeatures().getFitnessFunction());
			
			configuration.setPopulationSize(1);
			
		} catch (InvalidConfigurationException e) {
			
			e.printStackTrace();
		}
	
		localOptimizer.setSampleIndividual(problem);
	}


	/**
	 * Allows to execute the generic behavior of a local search algorithm using a specific local search optimizer
	 */
	public Individual execute() {
		
		long startTime= System.currentTimeMillis();

		Individual solution=null;
		try {
			solution = (Individual) Individual.randomInitialIndividual(configuration);
		} catch (InvalidConfigurationException e) {
			
			e.printStackTrace();
		}
		
	
		performance.setIndividual(solution);
		
		System.out.println("fitness value: "+ performance.getFitnessValue());
	
		performance.computeProblemPerformance(problem, solution);
		
			
	    	localOptimizer.inizializeSolution(solution);
	    	localOptimizer.startToSolve();
			
			solution=(Individual)localOptimizer.getBestSolution();
			
			performance.setIndividual(solution);
			//add in version jMeme 1.1
			performance.computeProblemPerformance(problem, solution);
			
			
			System.out.println("number of iterations: "+performance.getNumberOfIterations());
			
			System.out.println("number of evaluations: "+performance.getNumberOfFitnessEvaluations());
			
		

        long endTime=System.currentTimeMillis();
		
		performance.setTime(endTime - startTime);
		System.out.println("time of executions: "+performance.getTime());
		
		System.out.println("time fitnesses: "+performance.getTimeFitnesses());
		
		performance.setSpeed(solution.getNumberOfEvaluationsToBeGenerated());
		
		
		return solution;
	}

	


	
	
	/**
	 * Allows to initialize the local optimizer starting from the information contained in the configuration object.
	 * 
	 * @return the specific local optimizer to run
	 */
	public LocalSearchOptimizer initializeLocalOptimizer(){
		
		Class c=null;
		try {
			c = Class.forName(configuration.getLocalComponent().getOptimizerName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		Constructor  costr=null;
		try {
			costr=c.getConstructor(LocalSearchAlgorithmConfiguration.class);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		LocalSearchOptimizer localMethod=null;
		try {
			 try {
				localMethod= (LocalSearchOptimizer)costr.newInstance(configuration);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		return localMethod;
	}


	
	@Override
	public JConfiguration getConfiguration() {
		return configuration;
	}
	
	
	

	public AlgorithmPerformance getPerformance() {
		return performance;
	}


	public void reset(){
		performance.reset();
	}

}
