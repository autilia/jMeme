/*
 * GA_Optimizer.java 
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

package jMeme.globalSearchAlgorithms.ga;

import org.jgap.InvalidConfigurationException;
import org.jgap.Population;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.GAParameters;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;


/**
 * This class implements the evolution performed during one iteration of a genetic algorithm as implemented in JGap. 
 */
public class GA_Optimizer extends GlobalSearchOptimizer{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7994540977950274563L;
	
	/**
	 * Stores the object devoted to perform the evolution of a population according to a genetic algorithm.
	 */
	private org.jgap.impl.GABreeder breeder;
	
	
	/**
	 * Constructor 
	 * 
	 * @param conf  configuration of the global search algorithm to be performed
	 */
	public GA_Optimizer(GlobalSearchAlgorithmConfiguration conf){
		configuration=conf;
	    breeder=new org.jgap.impl.GABreeder();
	    
	    configuration.setPreservFittestIndividual(true);
		
	    GAParameters algorithmFeatures=(GAParameters)conf.getGlobalComponent().getParameters();
		
	  //set selector
		  
	     try {
				configuration.removeNaturalSelectors(false);
				configuration.addNaturalSelector(algorithmFeatures.getSelector().getSelectorOperator(configuration), false);
			} catch (InvalidConfigurationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
		
		//set mutation
		configuration.getGeneticOperators().set(1,algorithmFeatures.getMutation().getMutationOperator(configuration));
		
		//set crossover
		configuration.getGeneticOperators().set(0,algorithmFeatures.getCrossover().getCrossoverOperator(configuration));
	
	}
	
	
	/**
	 * Constructor 
	 * 
	 * @param conf  configuration of the memetic algorithm to be performed
	 */
	public GA_Optimizer(MemeticAlgorithmConfiguration conf){
		configuration=conf;
	    breeder=new org.jgap.impl.GABreeder();
	    
	    
        configuration.setPreservFittestIndividual(true);
		
	    GAParameters algorithmFeatures=(GAParameters)conf.getGlobalComponent().getParameters();
		
	  //set selector
		  
	     try {
	    	    
				configuration.removeNaturalSelectors(false);
				configuration.addNaturalSelector(algorithmFeatures.getSelector().getSelectorOperator(configuration), false);
			} catch (InvalidConfigurationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
		
		//set mutation
		configuration.getGeneticOperators().set(1,algorithmFeatures.getMutation().getMutationOperator(configuration));
		
		//set Crossover
		configuration.getGeneticOperators().set(0,algorithmFeatures.getCrossover().getCrossoverOperator(configuration));
	
	}


	@Override
	
	public JPopulation execute(JPopulation a_pop, JConfiguration a_conf) {
		Population p=breeder.evolve(a_pop.getPopulation(), a_conf);
		
		try {
			return new JPopulation(a_conf,p);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	@Override
	/**
	 * Allows to compare this instance with another object in terms of class name
	 */
	public int compareTo(Object a_other) {
		if (a_other == null) {
		      return 1;
		    }
		
		return getClass().getName().compareTo(a_other.getClass().getName());

	}


	@Override
	 /**
	   * @return a clone of this instance
	   */
	public Object clone() {
		if(configuration.isMemeticConfig())
		    return new GA_Optimizer((MemeticAlgorithmConfiguration)configuration);
		
		 return new GA_Optimizer((GlobalSearchAlgorithmConfiguration)configuration);
		
	}

	

	@Override
	/**
	 * Allows to set variables of the algorithm to initial values
	 */
	public void reset() {
		
	}

	@Override
	/**
	 * Allows to determine the best individual of the population given in input
	 * 
	 * @param pop  the population whose the best individual must be determined
	 */
	public void determineBest(JPopulation pop) {
		int originalPopSize = pop.getPopulationSize();
		 
		 bestSolution=(Individual)pop.getIndividual(0);
		 bestFitness=pop.getIndividual(0).getFitnessValue();
		
			for( int i = 1; i < originalPopSize; i++ ) {
				
				double fit=pop.getIndividual(i).getFitnessValue();
				
				if(compareFitness(fit, bestFitness)) {  
					bestSolution=((Individual)pop.getIndividual(i)).clone();
					bestFitness=fit;
				}
				
			}
	}

}
