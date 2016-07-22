/*
 * MemeticOptimizationAlgorithm.java 
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

package jMeme.memeticAlgorithms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jgap.InvalidConfigurationException;

import jMeme.core.Algorithm;
import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.Problem;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.generationExecutor.GenerationExecutor;
import jMeme.design.localIntegration.BestLocalOptimizerExecutor;
import jMeme.design.localIntegration.LocalOptimizerExecutor;
import jMeme.design.localIntegration.RandomLocalOptimizerExecutor;
import jMeme.design.localIntegration.StratifiedLocalOptimizerExecutor;
import jMeme.design.optimizerParameters.GlobalSearchOptimizerParameters;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;
import jMeme.performances.MemeticAlgorithmPerformance;



/**
 *  This class implements the generic memeticalgorithm. It must have a configuration including all its parameters 
 *  and a problem to solve. The configuration contains the global search optimizer and the local optimizer executor to be integrated in order to transform this generic memetic algorithm in a specific one.
 *  The class overrides the method <code>execute</code> of the superclass in order to define the behavior of a generic memetic algorithm.
 */
public class MemeticOptimizationAlgorithm extends Algorithm{

	
	
	/**
	 * Stores the global optimizer to run 
	 */
	protected GlobalSearchOptimizer globalOptimizer;
	/**
	 * Stores the configuration containing all information necessary to run the algorithm 
	 */
	protected MemeticAlgorithmConfiguration configuration;
	/**
	 * Stores the object containing all information about the performance of the algorithm  
	 */
	protected MemeticAlgorithmPerformance performance;
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
	 * Stores the local optimizer executor to run 
	 */
	protected LocalOptimizerExecutor localOptimizerExecutor;

	

	

	/**
	 * Constructor 
	 * 
	 * @param p  the problem to solve
	 * @param conf  the configuration containing all information to run the memetic algorithm
	 */
	public MemeticOptimizationAlgorithm(Problem p, MemeticAlgorithmConfiguration conf) {
		
	
		this.problem=p;
		
		
		JConfiguration.reset();
		configuration = conf;
		
		performance = (MemeticAlgorithmPerformance)configuration.getPerformance();
		
			
		this.generator=configuration.getGenerator();
	
	
		
		globalOptimizer=this.initializeGlobalOptimizer();
		configuration.setGlobalOptimizer(globalOptimizer);
	
		
		this.localOptimizerExecutor = this.initializeLocalSearchProcess();
		
		try {
			configuration.reset();
			configuration.setFitnessEvaluator(configuration.getFitnessFeatures().getFitnessEvaluator());
			configuration.setFitnessFunction(configuration.getFitnessFeatures().getFitnessFunction());
			
			configuration.setPopulationSize(((GlobalSearchOptimizerParameters)configuration.getGlobalComponent().getParameters()).getPopulationSize());
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		globalOptimizer.setSampleIndividual(problem);
	}


	/**
	 * Allows to execute the generic behavior of a memetic algorithm using a specific global search optimizer and local search executor
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
			
			double fitness1=performance.getFitnessValue();
			
		
			System.out.println("best fitness value after evolution: "+ performance.getFitnessValue());
			
			
			
			//update perfomance (se non si usano alcuni tipi di condizioni finali puo' essere omesso)
			double averageFitness=performance.computeAverageFitness(population.getPopulation());
			performance.updatePerformances(fitnessPrec, averageFitness,problem);
			
			
			localOptimizerExecutor.execute(population);
				
			//update best global
			globalOptimizer.determineBest(population);

			performance.setIndividual(globalOptimizer.getBestSolution());
			performance.setFitnessValue(globalOptimizer.getBestFitness());
			
				
			System.out.println("best fitness value after local search: " + performance.getFitnessValue());
					
			
			double fitness2=performance.getFitnessValue();
				
				//System.out.println("fitness1 " + fitness1);
				//System.out.println("fitness2 " + fitness2);
				
				
			if(globalOptimizer.compareFitness(fitness2, fitness1))
				   performance.incrementNumImprovements();
		
			
				performance.setLocalImprovement(fitness1, fitness2);
				
			
			
			
			
			performance.incrementNumberOfIterations();
			//System.out.println("number of iterations: "+performance.getNumberOfIterations());
			
			
			System.out.println("number of evaluations: "+performance.getNumberOfFitnessEvaluations());
			
			//update perfomance (se non si usano alcuni tipi di condizioni finali puo' essere omesso)
			averageFitness=performance.computeAverageFitness(population.getPopulation());
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
	 * Allows to set variables of the algorithm to the initial values
	 */
	public void reset(){
		globalOptimizer.reset();
		performance.reset();
	}
	


	/**
	 * Allows to initialize the local optimizer executor starting from the information contained in the configuration object.
	 * 
	 * @return  the specific local optimizer executor to run
	 */
	public LocalOptimizerExecutor initializeLocalSearchProcess(){
		
		if(configuration.getLocalSelectionScheme().equals(LocalOptimizerExecutor.BEST_SELECTION_SCHEME))
			return new BestLocalOptimizerExecutor(configuration.getLocalMethod(), configuration.getFrequencyPercentage().getFrequency());
		
		if(configuration.getLocalSelectionScheme().equals(LocalOptimizerExecutor.RANDOM_SELECTION_SCHEME))
			return new RandomLocalOptimizerExecutor(configuration.getLocalMethod(), configuration.getFrequencyPercentage().getFrequency());
	
		if(configuration.getLocalSelectionScheme().equals(LocalOptimizerExecutor.STRATIFIED_SELECTION_SCHEME))
			return new StratifiedLocalOptimizerExecutor(configuration.getLocalMethod(), configuration.getFrequencyPercentage().getFrequency());
	
		return null;
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
			costr=c.getConstructor(MemeticAlgorithmConfiguration.class);
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


	public GlobalSearchOptimizer getGlobalOptimizer() {
		return globalOptimizer;
	}



	public MemeticAlgorithmConfiguration getConfiguration() {
		return configuration;
	}



	public void setConfiguration(MemeticAlgorithmConfiguration configuration) {
		this.configuration = configuration;
	}



	public MemeticAlgorithmPerformance getPerformance() {
		return performance;
	}


	
	public void setPerformance(MemeticAlgorithmPerformance performance) {
		this.performance = performance;
	}


	public LocalOptimizerExecutor getLocalOptimizerExecutor() {
		return localOptimizerExecutor;
	}
}
