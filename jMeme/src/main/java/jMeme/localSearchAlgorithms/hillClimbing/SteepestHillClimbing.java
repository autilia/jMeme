/*
 * SteepestHillClimbing.java 
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
import jMeme.design.optimizerParameters.HillClimbingParameters;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;


/**
 * This class implements a Steepest Hill Climbing procedure. 
 */
public class SteepestHillClimbing extends LocalSearchOptimizer{

	
	private static final long serialVersionUID = -6014915467435578524L;
	/**
	 * Stores the maximum distance between a solution and one of its neighbors
	 */
	private int maxDist;
	/**
	 * Stores the default value for the number of the neighbors to generate
	 */
	private int numOfNeighbours;
	/**
	 * Stores the object aimed at generating neighborhood of a solution
	 */
	private GenerateSuccessors genSuccessors;


		
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run the Steepest Hill Climbing procedure
	 */
		public SteepestHillClimbing(LocalSearchAlgorithmConfiguration conf){
			this.configuration=conf;
			maxDist=((HillClimbingParameters)conf.getLocalComponent().getParameters()).getMaxDist();
			numOfNeighbours=((HillClimbingParameters)conf.getLocalComponent().getParameters()).getNumMov();
			genSuccessors=((HillClimbingParameters)conf.getLocalComponent().getParameters()).getGenSuccessors();
		}

		/**
		 * Constructor
		 * 
		 * @param conf  the configuration object containing all information to run a memetic algorithm where the Steepest Hill Climbing procedure is the local search optimizer
		 */
		public SteepestHillClimbing(MemeticAlgorithmConfiguration conf){
			this.configuration=conf;
			maxDist=((HillClimbingParameters)conf.getLocalComponent().getParameters()).getMaxDist();
			numOfNeighbours=((HillClimbingParameters)conf.getLocalComponent().getParameters()).getNumMov();
			genSuccessors=((HillClimbingParameters)conf.getLocalComponent().getParameters()).getGenSuccessors();
		
		}
		
		@Override		
		public void startToSolve() {
			
			Individual ns;

			this.localReset();
			
            initialEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
			
			while (getFinalConditions().verify(configuration)) {

				this.incrementIteration();
		
				Individual[] m=genSuccessors.execute(bestSolution, numOfNeighbours);
			
				Individual bestS=this.getBestSuccessor(m);
				
				if(isFirstBetterThanSecond(bestS, bestSolution, this.isMaximize()))
					bestSolution=bestS;
			
				currentEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
				this.updateLocalEvaluations();
				
				System.out.println(this.printStatistics());
			
						
			}
			
			configuration.getPerformance().setIndividual(((Individual)bestSolution));
	
		}
		
	
		private Individual getBestSuccessor(Individual [] m){
			
			Individual best=m[0];
			Individual sup;
		    
			int size=m.length;
			for(int i=1;i<size;i++){
				sup=m[i];
				if(isFirstBetterThanSecond(sup,best, this.isMaximize())){
			    best=sup;
				}
			}
			
			return best;
			
		}

	public int getMaxDist() {
		return maxDist;
	}

	
	public void setMaxDist(int maxDist) {
		this.maxDist = maxDist;
	}

	
	
	public int getNumMov() {
		return numOfNeighbours;
	}

	public void setNumMov(int numMov) {
		this.numOfNeighbours = numMov;
	}

	
	
	public GenerateSuccessors getGenSuccessors() {
		return genSuccessors;
	}

	public void setGenSuccessors(GenerateSuccessors genSuccessors) {
		this.genSuccessors = genSuccessors;
	}

	
}
