/*
 * StochasticHillClimbing.java 
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

package jMeme.localSearchAlgorithms.hillClimbing;

import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.StochasticHillClimbingParameters;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;

import java.util.Random;

import org.jgap.Gene;
import org.jgap.RandomGenerator;







/**
 * This class implements the stochastic hill climbing procedure.
 */
public class StochasticHillClimbing extends LocalSearchOptimizer{

	

	private static final long serialVersionUID = 8048300499834909249L;
	/**
	 * Stores the maximum distance between a solution and one of its neighbors
	 */
	private int maxDist;
	
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run the Stochastic Hill Climbing procedure
	 */
	public StochasticHillClimbing(LocalSearchAlgorithmConfiguration conf){
		this.configuration=conf;
		maxDist=((StochasticHillClimbingParameters)conf.getLocalComponent().getParameters()).getMaxDist();
	}
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where the Stochastic Hill Climbing procedure is the local search optimizer
	 */
	public StochasticHillClimbing(MemeticAlgorithmConfiguration conf){
		this.configuration=conf;
		maxDist=((StochasticHillClimbingParameters)conf.getLocalComponent().getParameters()).getMaxDist();
	}

	

		@Override
		public void startToSolve() {
			
			Individual ns;
			
			this.localReset();
			
			initialEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
			
			while (getFinalConditions().verify(configuration)) {

				this.incrementIteration();
				
				ns = generateNeighborSolution(bestSolution,maxDist);
				//System.out.println(s.evaluate());
				//System.out.println(ns.evaluate());	
				if (isFirstBetterThanSecond(ns, bestSolution, this.isMaximize())) {
					//iter = 0;
					bestSolution = ns;
					System.out.println("local search migliora");
				}
				//System.out.println(s.evaluate());	
					
				currentEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
				this.updateLocalEvaluations();
				
				System.out.println(this.printStatistics());
				
			}
			
			configuration.getPerformance().setIndividual(((Individual)bestSolution));
			
		}
		
		private Individual generateNeighborSolution(Individual s, int maxDist){

			RandomGenerator generator = this.configuration.getRandomGenerator();

			Individual out = (Individual)s.partialClone();
			
			int numG=out.getGenes().length;
			
			
			int nd = 1 + generator.nextInt(maxDist);
			
			int i=0;	
			while (i < nd){
				int rand=generator.nextInt(numG);
				Random rr=new Random();
				
				Gene gene=(Gene)(out.getGenes()[rand]);
				int iter=this.configuration.getPerformance().getNumberOfIterations()+1;
				
					gene.setToRandomValue(generator);
				    i++;
				}
			
			return out;
		}

	public int getMaxDist() {
		return maxDist;
	}


	public void setMaxDist(int maxDist) {
		this.maxDist = maxDist;
	}


	
	
}
