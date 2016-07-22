/*
 * RandomGenerationExecutorWithDefaultChromosome.java 
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


import org.jgap.InvalidConfigurationException;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.Problem;
import jMeme.core.individuals.Individual;


/**
 * This class implements a population generation strategy consisting into generating an initial population composed of:
 * 1) a set of individuals specified by user in the class implementing the user problem;
 * 2) a set of random individuals.
 * The number of the generated random individuals is equal to the population size minus the number of user individuals.
 */
public class RandomGenerationExecutorWithDefaultChromosomes extends GenerationExecutor{

	private static final long serialVersionUID = -5287695884417519889L;

	/**
	 * Stores the problem to solve
	 */
	protected Problem problem;
	
	
	
	/**
	 * Constructor 
	 * @param prob  the specific problem to solve
	 */
	public RandomGenerationExecutorWithDefaultChromosomes(Problem prob){
		super();
		problem=prob;
	}
	


	/**
	 * Allows to create an initial population for the global search algorithms
	 * 
	 * @param configuration  object that contains all information about the algorithm to run
	 * @return the initial population of individuals to evolve 
	 */
	public JPopulation execute(JConfiguration configuration){

		//	create random population
		JPopulation p=null;
		try {
			p = newRandomInitialPopulation(configuration);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
		}
	
	private JPopulation newRandomInitialPopulation(JConfiguration
		      a_configuration)
		      throws InvalidConfigurationException {
		    if (a_configuration == null) {
		      throw new IllegalArgumentException(
		          "The Configuration instance may not be null.");
		    }
		    a_configuration.lockSettings();
		   
		    
		    int populationSize = a_configuration.getPopulationSize();
	
		    
		  	Individual[] individuals=problem.generateDefaultIndividuals(a_configuration);
		  	
		    int defaultSize=individuals.length;
		    int sizeRandomPop=populationSize-defaultSize;
		    
		    JPopulation result = new JPopulation(a_configuration, populationSize);
		    result.fillPopulation(sizeRandomPop);
		    
		    for(int i=0;i<defaultSize;i++)
		       result.addIndividual(individuals[i]);
		    
		      
		   // Iterator iter=result.getPopulation().getChromosomes().iterator();
		    //while(iter.hasNext())
		      //System.out.println("population " + iter.next());
		    
		    
		    return result;
		  }
	
	
	
	
}
