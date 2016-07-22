/*
 * MetaFewGenerationExecutor.java 
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


import java.util.Iterator;
import java.util.List;

import org.jgap.InvalidConfigurationException;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.individuals.Individual;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;
import jMeme.utils.JGapExtension;


/**
 * This class implements a population generation strategy consisting into performing two phases:
 * 1) generation of a random population;
 * 2) improvement of a percentage of individuals (specified through the input rate) of the random population by means of a local optimizer.
 */
public class MetaFewGenerationExecutor extends GenerationExecutor{

	private static final long serialVersionUID = 2898842086290881057L;


	/**
	 * Stores the percentage of the individuals that has to be undergone to the local improvement
	 */
	protected double rate;
	
	
	/**
	 * Constructor 
	 * @param localOptimizer  object that performs the local optimization of individuals of the initial random population
	 * @param rate  double value indicating the percentage of individuals of the random initial population which has to be undergone to the local improvement
	 */
	public MetaFewGenerationExecutor(LocalSearchOptimizer localOptimizer, double rate){
		super(localOptimizer);
		this.rate=rate;
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
		
		 int numSelectedIndividuals=(int)((rate*p.getPopulationSize()));
		
        if(numSelectedIndividuals<1)
        	numSelectedIndividuals=1;
		
		List best=p.getFittestIndividuals(numSelectedIndividuals);
		Individual s=null;
		
		Iterator iter = best.iterator();
		while(iter.hasNext()){
			Individual mm= (Individual)iter.next();
			//initialize solution
			metaH.inizializeSolution(mm);
			metaH.startToSolve();
			
			s=(Individual)metaH.getBestSolution();
		
			JGapExtension.replaceWorstIndividual(p, s);
		}
		
		
		return p;
		
		}
	
	
	

	
}
