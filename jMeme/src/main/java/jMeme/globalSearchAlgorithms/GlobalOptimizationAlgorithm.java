/*
 * GlobalOptimizationAlgorithm.java 
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jgap.InvalidConfigurationException;

import jMeme.core.Algorithm;
import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.Problem;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.generationExecutor.GenerationExecutor;
import jMeme.design.optimizerParameters.GlobalSearchOptimizerParameters;
import jMeme.performances.AlgorithmPerformance;


/**
 *  This class implements the generic global search algorithm. It must have a configuration including all its parameters 
 *  and a problem to solve. The configuration contains also the global search optimizer to be executed in order to transform this generic global search algorithm in a specific one.
 *  The class overrides the method <code>execute</code> of the superclass in order to define the behavior of a generic global search algorithm.
 */
public class GlobalOptimizationAlgorithm extends Algorithm {

	/**
	 * Stores the global optimizer to run 
	 */
	protected GlobalSearchOptimizer globalOptimizer;
	/**
	 * Stores the configuration containing all information necessary to run the algorithm 
	 */
	protected GlobalSearchAlgorithmConfiguration configuration;
	/**
	 * Stores the object containing all information about the performance of the algorithm  
	 */
	protected AlgorithmPerformance performance;
	/**
	 * Stores the population to evolve
	 * @uml.associationEnd  
	 */
	protected JPopulation population;
	/**
	 * Stores the object used to generate the initial population 
	 */
	protected GenerationExecutor generator;

	


	
	/**
	 * Constructor 
	 * 
	 * @param p  the problem to solve
	 * @param conf  the configuration containing all information to run the global search algorithm
	 */
	public GlobalOptimizationAlgorithm(Problem p,GlobalSearchAlgorithmConfiguration conf) {
		
	
		this.problem=p;
		
		
		JConfiguration.reset();
		configuration = conf;
		
		performance = configuration.getPerformance();
		
			
		this.generator=configuration.getGenerator();
	
	
	
		globalOptimizer=this.initializeGlobalOptimizer();
		configuration.setGlobalOptimizer(globalOptimizer);
		
		
			
		try {
			configuration.reset();
			configuration.setFitnessEvaluator(configuration.getFitnessFeatures().getFitnessEvaluator());
			configuration.setFitnessFunction(configuration.getFitnessFeatures().getFitnessFunction());
			
			
			configuration.setPopulationSize(((GlobalSearchOptimizerParameters)configuration.getGlobalComponent().getParameters()).getPopulationSize());
		} catch (InvalidConfigurationException e) {
			
			e.printStackTrace();
		}
		
		globalOptimizer.setSampleIndividual(problem);
	}


	/**
	 * Allows to execute the generic behavior of a global search algorithm using a specific global search optimizer
	 */
	public Individual execute() {
		
		long startTime= System.currentTimeMillis();

		population = generator.execute(configuration);
		
		System.out.println("random population " +population);
		
		
		//initialize best global
		globalOptimizer.determineBest(population);
		
		performance.computeProblemPerformance(problem, globalOptimizer.getBestSolution());
	
		performance.setIndividual(globalOptimizer.getBestSolution());
		performance.setFitnessValue(globalOptimizer.getBestFitness());
		
		System.out.println("initial best fitness value: "+ performance.getFitnessValue());
		
		
		while (configuration.getFinalConditions().verify(configuration)) {
			
			System.out.println("Iteration number " + (performance.getNumberOfIterations()+1));
			
			//evaluations.add(performance.getAlignPerformances().getFmeasure());
			//evaluations.add(performance.getNumberOfFitnessEvaluations());
			
			double fitnessPrec=performance.getFitnessValue();
			
			population.evolve();
			
			
			//initialize best global
			globalOptimizer.determineBest(population);
			
			performance.setIndividual(globalOptimizer.getBestSolution());
			performance.setFitnessValue(globalOptimizer.getBestFitness());
			
		
			System.out.println("best fitness value after evolution: "+ performance.getFitnessValue());
			
			performance.incrementNumberOfIterations();
			//System.out.println("number of iterations: "+performance.getNumberOfIterations());
			
			
			System.out.println("number of evaluations: "+performance.getNumberOfFitnessEvaluations());
			
			//update perfomance
			double averageFitness=performance.computeAverageFitness(population.getPopulation());
			performance.updatePerformances(fitnessPrec, averageFitness,problem);
			
						
			System.out.println("iteration ended");
			
			}
		
		
        long endTime=System.currentTimeMillis();
		
		performance.setTime(endTime - startTime);
		System.out.println("time of executions: "+performance.getTime());
		
		System.out.println("evaluations: "+performance.getNumberOfFitnessEvaluations());
		
		performance.computeProblemPerformance(problem, globalOptimizer.getBestSolution());
		
		performance.setSpeed((globalOptimizer.getBestSolution()).getNumberOfEvaluationsToBeGenerated());
		
		
		return globalOptimizer.getBestSolution();
	}

	

	
	/**
	 * Allows to initialize the global optimizer starting from the information contained in the configuration object.
	 * 
	 * @return  the specific global optimizer to run
	 */
	public GlobalSearchOptimizer initializeGlobalOptimizer(){
		
		Class c=null;
		try {
			c = Class.forName(configuration.getGlobalComponent().getOptimizerName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		Constructor  costr=null;
		try {
			costr=c.getConstructor(GlobalSearchAlgorithmConfiguration.class);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		GlobalSearchOptimizer breeder=null;
		try {
			 try {
				breeder= (GlobalSearchOptimizer)costr.newInstance(configuration);
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
		
		

		
		return breeder;
	}
	
	
	/**
	 * Allows to set variables of the algorithm to the initial values
	 */
	public void reset(){
		globalOptimizer.reset();
		performance.reset();
	}
	
	
	
	public GlobalSearchOptimizer getGlobalOptimizer() {
		return globalOptimizer;
	}

	public GlobalSearchAlgorithmConfiguration getConfiguration() {
		return configuration;
	}

	
	public void setConfiguration(GlobalSearchAlgorithmConfiguration configuration) {
		this.configuration = configuration;
	}

	public AlgorithmPerformance getPerformance() {
		return performance;
	}


	public void setPerformance(AlgorithmPerformance performance) {
		this.performance = performance;
	}


	public JPopulation getPopulation() {
		return population;
	}


	public void setPopulation(JPopulation initialPopulation) {
		this.population = initialPopulation;
	}


	public GenerationExecutor getGenerator() {
		return generator;
	}


	public void setGenerator(GenerationExecutor generator) {
		this.generator = generator;
	}


}
