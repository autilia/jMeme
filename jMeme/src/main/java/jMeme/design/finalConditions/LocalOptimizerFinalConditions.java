/*
 * LocalOptimizerFinalConditions.java 
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

package jMeme.design.finalConditions;

import jMeme.core.JConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.localIntegration.LocalIntensity;
import jMeme.performances.AlgorithmPerformance;
import jMeme.performances.MemeticAlgorithmPerformance;

public class LocalOptimizerFinalConditions extends AlgorithmFinalConditions{


	private static final long serialVersionUID = 6640044183874261232L;
	
	/**
	 * Constant representing the following termination criterion: stop the local optimizer when a number of local iterations is achieved 
	 */
	public static final short FINAL_CONDITION_ITERATION_INTEGRATED = 16;
	
	
	/**
	 * Constant representing the following termination criterion: stop the local optimizer when a number of local fitness evaluations is achieved 
	 */
	public static final short FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED = 32;
	
	/**
	 * Stores the number of local fitness evaluations to be reached to stop the local optimizer if FINAL_CONDITION_LOCALFITNESSEVALUATIONS is set
	 */
	protected int numberLocalEvaluations;

	/**
	 * Stores the number of local iterations to be reached to stop the local optimizer if FINAL_CONDITION_LOCALITERATION is set
	 */
	protected int numberLocalIterations;
	
	/**
	 * Constructor 
	 * 
	 * @param gc  object that contains the information about the termination criteria of the memetic algorithm
	 * @param li  object that contains the information about the local intensity to which the individuals must be undergone during the local procedure
	 */
	public LocalOptimizerFinalConditions(AlgorithmFinalConditions gc, LocalIntensity li){
		numberIterations=gc.getNumberIterations();
		numberFitnessEvaluations=gc.getNumberFitnessEvaluations();
		
		
		timesConvergence=gc.getTimesConvergence();
		fitnessValue=gc.getFitnessValue();
		
		signFitness=gc.getSignFitness();
		
		numberLocalEvaluations=li.getNumberLocalEvaluations();
		numberLocalIterations=li.getNumberLocalIterations();
		
		short condition1=gc.getCondition();
		short condition2=li.getCondition();
		condition=(short)(condition1 | condition2);
	}
	
	
	/**
	 * Allows to verify if the termination criteria specified for both the memetic algorithm and the nested local search procedure are achieved 
	 * 
	 * @param conf the configuration of the algorithm to stop
	 * @return boolean  true if the specified termination criteria are achieved, false, otherwise
	 */
	public boolean verify(JConfiguration conf) {
		boolean superValue=super.verify(conf);
		
		
        MemeticAlgorithmPerformance performance = (MemeticAlgorithmPerformance)conf.getPerformance();
		
		boolean[] aC=new boolean[2];
		
		boolean[] aV=new boolean[2];
		

		if((condition & FINAL_CONDITION_ITERATION_INTEGRATED) == FINAL_CONDITION_ITERATION_INTEGRATED){
			aC[0]=true;
			if(performance.getNumberLocalIterations()<numberLocalIterations)  
				aV[0]=true;
		}
		
		
		if((condition & FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED) == FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED){
			aC[1]=true;
			if(performance.getNumberLocalEvaluations()<numberLocalEvaluations)  
				aV[1]=true;
		}
		
		
		
		boolean finalValue=true;
		for(int i=0; i<2;i++)
			if(aC[i]==true)
				finalValue &=aV[i];
			
		
		return (finalValue&superValue);
	}
	

	 /**
	   * @return string representation of the specified termination criteria
	   */
	public String toString(){
		
		String s=super.toString();
		
		if((condition & FINAL_CONDITION_ITERATION_INTEGRATED) == FINAL_CONDITION_ITERATION_INTEGRATED){
			s+="Number of local iterations " + numberLocalIterations +"\n";
			
		}
		if((condition & FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED) == FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED){
			s+="Number of evaluations " + numberLocalEvaluations +"\n";
			
		}
		
		
		
		return s;
		
	}
	
}
