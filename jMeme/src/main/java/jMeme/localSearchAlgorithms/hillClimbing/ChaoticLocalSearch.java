/*
 * ChaoticLocalSearch.java 
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


import java.util.Random;

import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.ChaoticLocalSearchParameters;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;



/**
 * This class implements the chaotic local search procedure.
 */
public class ChaoticLocalSearch extends LocalSearchOptimizer{

	
	private static final long serialVersionUID = 8387869181914071640L;

	/**
	 * Stores the number of neighbours to generate
	 */
	private int numOfNeighbours;
	
	

	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run the chaotic local search procedure
	 */
		public ChaoticLocalSearch(LocalSearchAlgorithmConfiguration conf){
			this.configuration=conf;
			numOfNeighbours=((ChaoticLocalSearchParameters)conf.getLocalComponent().getParameters()).getNumMov();
		}
		
		/**
		 * Constructor
		 * 
		 * @param conf  the configuration object containing all information to run a memetic algorithm where the chaotic local search procedure is the local search optimizer
		 */
		public ChaoticLocalSearch(MemeticAlgorithmConfiguration conf){
			this.configuration=conf;
		    numOfNeighbours=((ChaoticLocalSearchParameters)conf.getLocalComponent().getParameters()).getNumMov();
		}
		
		
		@Override
		public void startToSolve() {
			
            this.localReset();
			
            initialEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
			
			while (getFinalConditions().verify(configuration)) {

				this.incrementIteration();
					

				Individual[] m=this.generateSuccessors(bestSolution, numOfNeighbours);
			
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

		private Individual[] generateSuccessors(Individual sol, int M){
			
			Individual[] chaoticS=this.generateChaoticSequences(M);
			
			Individual[] movs = new Individual[M];
			
	        Random rnd=new Random();
			
	        int numG=((Individual)bestSolution).getGenes().length;
			
			
			for(int i=0;i<M; i++)
			{
				
				movs[i]=(Individual) bestSolution.partialClone();
				 for(int j=0; j<numG;j++)
				 {
					 
					 if(rnd.nextDouble()<0.05){
					 double oldAllele=(Double)((Individual)bestSolution).getGenes()[j].getAllele();
					
					 double gamma=1-Math.pow(((i+1-1)/(i+1)),1);
				     double newValue=(1-gamma)*oldAllele+gamma*((Double)chaoticS[i].getGenes()[j].getAllele());
						
					 movs[i].getGenes()[j].setAllele(newValue);
					 }
					 
					
				 }
				
				 
			}
			
			return movs;
		}
		
		private Individual[] generateChaoticSequences(int M){
			
			Individual[] chaoticS=new Individual[M];
			
			int numG=((Individual)bestSolution).getGenes().length;
			
			
			chaoticS[0]=(Individual) bestSolution.generateRandomSolution();
			
			for(int i=1;i<M; i++)
			{
				chaoticS[i]=chaoticS[i-1].partialClone();
			 for(int j=0; j<numG;j++)
			 {
				 double oldAllele=(Double)chaoticS[i-1].getGenes()[j].getAllele();
				 double newValue=4*oldAllele*(1-oldAllele);
				 chaoticS[i].getGenes()[j].setAllele(newValue);
			 }
				
			}
			
			
			return chaoticS;
		}




	public int getNumOfNeighbours() {
		return numOfNeighbours;
	}

	
	public void setNumOfNeighbours(int numOfNeighbours) {
		this.numOfNeighbours = numOfNeighbours;
	}


	
	
	
}
