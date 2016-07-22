/*
 * MetaAllGenerationExecutor.java 
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
import jMeme.core.individuals.Individual;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;


/**
 * This class implements a population generation strategy consisting into performing two phases:
 * 1) generation of a random population;
 * 2) improvement of all individuals of the random population by means of a local optimizer.
 */
public class MetaAllGenerationExecutor extends GenerationExecutor{

	
	private static final long serialVersionUID = -4659953609584468531L;

	
	/**
	 * Constructor 
	 * @param localOptimizer  object that performs the local optimization of individuals of the initial random population
	 */
	public MetaAllGenerationExecutor(LocalSearchOptimizer localOptimizer){
		super(localOptimizer);
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
			p = JPopulation.randomInitialPopulation(configuration);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int popsize=p.getPopulationSize();
		
		for(int i=0; i<popsize; i++){
			Individual chr=(Individual)p.getIndividual(i);
			
			Individual s=null;
			//inizialize solution
			metaH.inizializeSolution(chr);
			metaH.startToSolve();
			
			s=(Individual)metaH.getBestSolution();
		
			p.setIndividual(s,i);
		
		}
		
		
		return p;
		
		}

	
}
