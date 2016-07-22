/*
 * TabuSearch.java 
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

package jMeme.localSearchAlgorithms.tabuSearch;

import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.*;
import jMeme.design.optimizerParameters.TabuSearchParameters;
import jMeme.localSearchAlgorithms.tabuSearch.movements.Movement;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;
import jMeme.localSearchAlgorithms.tabuSearch.movements.IPossibleMovementsGenerator;


/**
 * This class implements the Tabu Search (TS), created by Fred W. Glover in 1986. TS is a local search method which uses some mechanisms to avoid to to become stuck in suboptimal regions.
 * In particular, at each step, worsening moves can be accepted if no improving move is available. In addition, prohibitions are introduced to discourage the search from coming back to previously-visited solutions.
 */
public class TabuSearch extends LocalSearchOptimizer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4600286021263435168L;

	
	/**
	 * Stores the object aimed at generating the possible movements
	 */
	private IPossibleMovementsGenerator movementsGenerator;
    /**
     * Stores the size of the tabu list
     */
	private int tabuListSize;

	/**
	 * Stores the information about if the search of the best movement must be stopped at the first improvement
	 */
	private boolean firstImprovement;

	/**
	 * Stores the specific termination criteria for TS
	 */
	private IAspirationCriteria aspiration;
	
	/**
	 * Stores the object representing the tabu list
	 */
	private TabuList tabuList;
	
	/**
	 * Stores the current best solution
	 */
	protected Individual currentSolution;
	
   
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run the Tabu Search procedure
	 */	
	public TabuSearch(LocalSearchAlgorithmConfiguration conf){
		this.configuration=conf;
		movementsGenerator=((TabuSearchParameters)conf.getLocalComponent().getParameters()).getMovementsGenerator();
		tabuListSize=((TabuSearchParameters)conf.getLocalComponent().getParameters()).getTabuListSize();
		firstImprovement=((TabuSearchParameters)conf.getLocalComponent().getParameters()).isFirstImprovement();
	    aspiration=((TabuSearchParameters)conf.getLocalComponent().getParameters()).getAspiration();
	    tabuList=new TabuList(tabuListSize);
	}
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where the Tabu Search procedure is the local search optimizer
	 */
	public TabuSearch(MemeticAlgorithmConfiguration conf){
		this.configuration=conf;
		movementsGenerator=((TabuSearchParameters)conf.getLocalComponent().getParameters()).getMovementsGenerator();
		tabuListSize=((TabuSearchParameters)conf.getLocalComponent().getParameters()).getTabuListSize();
		firstImprovement=((TabuSearchParameters)conf.getLocalComponent().getParameters()).isFirstImprovement();
	    aspiration=((TabuSearchParameters)conf.getLocalComponent().getParameters()).getAspiration();
	    tabuList=new TabuList(tabuListSize);
	}
	



	
	@Override
	public void startToSolve() {
		
		 this.localReset();
			
         initialEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
			
		
         while (getFinalConditions().verify(configuration)) {

		
                     this.incrementIteration();
					
		             performTabuIteration();
					
				
					currentEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
					this.updateLocalEvaluations();
					
					System.out.println(this.printStatistics());
				
				}
				
				configuration.getPerformance().setIndividual(((Individual)bestSolution));

	}

	


	private void performTabuIteration() {

		
		Individual currentSol = getCurrentSolution();
		
			
		final Movement[] movList = this.movementsGenerator.getPosibleMovements(currentSol);
	
		
		
		
		final Object[] bestMovArr;

		bestMovArr = getBestMovement(currentSol, movList, tabuList,aspiration, isMaximize(), firstImprovement);

		
		
		final Movement bestMov = (Movement) bestMovArr[0];
		final Individual bestMovSol = (Individual) bestMovArr[1];
		final boolean bestMovTabu = ((Boolean) bestMovArr[2]).booleanValue();

		
		tabuList.setTabu(bestMovSol);
		
			
    	currentSol=bestMovSol.clone();
			

	
    	
			if (isFirstBetterThanSecond(bestMovSol, bestSolution, isMaximize())) {
				bestSolution = (Individual) currentSol.clone();
				
			}

		

	}

	

	private Object[] getBestMovement(final Individual soln,
			final Movement[] movements, final TabuList tl,
			final IAspirationCriteria ac, final boolean max,
			final boolean chooseBestImprovingMovement) {

		Movement bestMove = movements[0];

		Individual aux = (Individual) soln.clone();
		Individual bestMovSol = (Individual) soln.clone();
		boolean bestMovTabu = false;

		bestMove.move(bestMovSol);
	
	
		bestMovTabu = isTabu(soln, bestMove, bestMovSol, tl, ac,this);
		
		
		if (chooseBestImprovingMovement) {
			if (!bestMovTabu && isFirstBetterThanSecond(bestMovSol,soln, isMaximize())) {
				return new Object[] { bestMove, bestMovSol,
						new Boolean(bestMovTabu) };
			}
		}

		

		final int movLong = movements.length;
	
			for (int i = 1; i < movLong; i++) {
				Movement mov = movements[i];
				
				
				mov.move(aux);

				
				 if (isFirstBetterThanSecond(aux,bestMovSol, isMaximize())){
					 
					 boolean newIsTabu = isTabu(soln, mov, aux, tl, ac, this);

					 if( !(!bestMovTabu && newIsTabu) )
				     {

					
						bestMove = mov;
						bestMovSol = (Individual) aux.clone();
						bestMovTabu = newIsTabu;

						if (chooseBestImprovingMovement) {
							if (!bestMovTabu && isFirstBetterThanSecond(bestMovSol,soln, isMaximize())) {
								return new Object[] { bestMove, bestMovSol,
										new Boolean(bestMovTabu) };
							}
						}

					
				}
				 }  
				 
				 else {

					 if( bestMovTabu && !isTabu(soln, mov, aux, tl, ac, this)) {
						bestMove = mov;
						bestMovSol = (Individual) aux.clone();
						bestMovTabu = false;
					}
				}
				
				mov.undo(aux);
				
			}


			
			

		return new Object[] { bestMove, bestMovSol, new Boolean(bestMovTabu) };
	}

	private boolean isTabu(final Individual soln, final Movement mov,
			final Individual changedSol, final TabuList tl,
			final IAspirationCriteria ac, final TabuSearch This) {
		boolean tabu = false;

		
		
		boolean valueTabu = tl.isTabu(changedSol, mov);
		
		if (valueTabu) {
			tabu = true;
			
			if (ac != null) {
				
				if (ac.tabuAspiration(soln, mov, changedSol, This)) {
				
					tabu = false;
				}
			}
			
		}

		return tabu;
	}
	

	public Individual getBestSolution() {
	 	return (Individual) bestSolution.clone();
	}

	public Individual getCurrentSolution() {

		if(currentSolution==null)
			return bestSolution;
		
		return (Individual) currentSolution.clone();

	}



}
