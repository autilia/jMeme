/*
 * BestLocalOptimizerExecutor.java 
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


import java.util.Iterator;
import java.util.List;

import jMeme.core.JPopulation;
import jMeme.core.individuals.Individual;
import jMeme.design.replacementStrategy.ReplacementStrategy;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;

/**
 *  This class implements local optimizer executor which performs the specified local optimizer on the best individuals of the population. 
 *  In this case, the individual selection scheme is set to BEST_SELECTION_SCHEME.
 */
public class BestLocalOptimizerExecutor extends LocalOptimizerExecutor{

	

	/**
	 * Stores the percentage of individuals of the population which has to be undergone to the local improvement
	 */
	private  double rate;
	
	
	/**
	 * Constructor
	 * 
	 * @param  localOptimizer  local procedure to be applied on the individuals of the population
	 * @param  rate double value indicating the percentage of individuals of the population which has to be undergone to the local improvement
	 */
	public BestLocalOptimizerExecutor(LocalSearchOptimizer localOptimizer,  double rate){
		super(localOptimizer);
		this.rate=rate;
	}
	
	/**
	 * Constructor
	 * 
	 * @param  localOptimizer  local procedure to be applied on the individuals of the population
	 * @param  replacement strategy to be applied in order to introduce the improved individuals in the population
	 * @param  rate double value indicating the percentage of individuals of the population which has to be undergone to the local improvement
	 */
	public BestLocalOptimizerExecutor(LocalSearchOptimizer localOptimizer,  ReplacementStrategy strategy, double rate){
		super(localOptimizer,strategy);
		this.rate=rate;
	}
	
	
	/**
	 * Allows to execute the specified local improvement procedure on the given population
	 * 
	 * @param p  the population on which the specified local optimizer must be applied
	 */
	public void execute(JPopulation p){
		
		int numSelectedIndividual=(int)((rate*p.getPopulationSize()));
		
		if(numSelectedIndividual<1)
			numSelectedIndividual=1;
		
		List best=p.getFittestIndividuals(numSelectedIndividual);
		Individual s=null;
		
		Iterator iter = best.iterator();
		while(iter.hasNext()){
			Individual mm= (Individual)iter.next();
			
			//inizialize solution
			localOptimizer.inizializeSolution(mm);
			
			System.out.println("initial solution " + mm);
			
			localOptimizer.startToSolve();
			
			s=(Individual)localOptimizer.getBestSolution();
			
			this.replacementStrategy.setModifiedIndex(p.indexOfIndividual(mm));
			this.replacementStrategy.execute(p,s, localOptimizer.isMaximize());
			
		}
	}
	
}
