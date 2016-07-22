/*
 * AlgorithmFinalConditions.java 
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
import jMeme.performances.AlgorithmPerformance;




public class AlgorithmFinalConditions extends FinalConditions{

	private static final long serialVersionUID = 5425214626869490845L;
	
	
	/**
	 * Constant representing the following termination criterion: stop the algorithm when a number of iterations is achieved 
	 */
	public static final short FINAL_CONDITION_ITERATION = 8;
	
	
	/**
	 * Stores the number of iterations to be reached to stop the algorithm evolution
	 */
	protected int numberIterations;
	
	
	/**
	 * Constructor 
	 */
	public AlgorithmFinalConditions(){
		super();
		numberIterations=100;
		
	}
	
	/**
	 * Constructor 
	 * 
	 * @param condition  represent the termination criteria specification
	 * @param numberOfIterations  the number of iterations to be reached to stop the algorithm
	 * @param numberOfFitnessEvaluations  the number of fitness evaluations to be reached to stop the algorithm
	 * @param timesConvergence  the number of times that the best fitness value does not change to be reached to stop the algorithm
	 * @param fitnessValue  the fitness value to be reached to stop the algorithm
	 */ 
	public AlgorithmFinalConditions(short condition, int numberOfIterations, int numberOfFitnessEvaluations, int timesConvergence, double fitnessValue) {
		super(condition, numberOfFitnessEvaluations,  timesConvergence,  fitnessValue );
		numberIterations = numberOfIterations;
	}
	
	
	
	/**
	 * Costructor 
	 * 
	 * @param condition  termination criterion to stop the algorithm
	 * @param value  integer value representing the number of fitness evaluations if the specified condition is FINAL_CONDITION_FITNESSEVALUATIONS
	 *               or the number of the times that the best fitness value does not change if the specified condition is FINAL_CONDITION_CONVERGENCE
	 *               or the number of iterations if the specified condition is FINAL_CONDITION_ITERATION
	 */
	public AlgorithmFinalConditions(short condition, int value) {
		super(condition, value);
		
		if (this.condition == FINAL_CONDITION_ITERATION)
			numberIterations = value;
			
		
		
	}
	
	/**
	 * Constructor 
	 * 
	 * @param condition  termination criterion to stop the algorithm
	 * @param value  double value representing the fitness value to be reached to stop the algorithm if the specified condition is FINAL_CONDITION_FITNESSVALUE
	 */
	public AlgorithmFinalConditions(short condition, double value) {
		super(condition ,  value);
	}
	
	
	/**
	 * Costructor 
	 * 
	 * @param condition  the combination of termination criteria to stop the algorithm
	 * @param value1  integer value representing the number of fitness evaluations if the specified condition is FINAL_CONDITION_FITNESSEVALUATIONS
	 *                or the number of the times that the best fitness value does not change if the specified condition is FINAL_CONDITION_CONVERGENCE
	 *                or the number of iterations if the specified condition is FINAL_CONDITION_ITERATION
	 * @param value2  double value representing the fitness value to be reached to stop the algorithm if the specified condition is FINAL_CONDITION_FITNESSVALUE
	 */
	public AlgorithmFinalConditions(short condition, int value1, double value2){
		
       super(condition, value1,value2);
       
		if ((this.condition & FINAL_CONDITION_ITERATION) == FINAL_CONDITION_ITERATION)
			numberIterations = value1;
		
		
	}
	

	/**
	 * Allows to verify if the termination criteria are achieved
	 * 
	 * @param conf the configuration of the algorithm to stop
	 * @return boolean  true if the specified termination criteria are achieved, false, otherwise
	 */
	public boolean verify(JConfiguration conf){
		AlgorithmPerformance performance = conf.getPerformance();
		
		boolean[] aC=new boolean[4];
		
		boolean[] aV=new boolean[4];
		

		if((condition & FINAL_CONDITION_ITERATION) == FINAL_CONDITION_ITERATION){
			aC[0]=true;
			if(performance.getNumberOfIterations()<numberIterations)  
				aV[0]=true;
		}
		if((condition & FINAL_CONDITION_FITNESSEVALUATIONS) == FINAL_CONDITION_FITNESSEVALUATIONS){
			aC[1]=true;
			
		
			if(performance.getNumberOfFitnessEvaluations()<numberFitnessEvaluations)  
				aV[1]=true;
		}
		
		if((condition & FINAL_CONDITION_CONVERGENCE) == FINAL_CONDITION_CONVERGENCE){
			aC[2]=true;
			if(performance.getTimesConvergence() < timesConvergence)  
				aV[2]=true;
		}
		
		
		
		if((condition & FINAL_CONDITION_FITNESSVALUE) == FINAL_CONDITION_FITNESSVALUE){
			aC[3]=true;
			
			if(signFitness==FitnessFeatures.MIN){
		      if(performance.getFitnessValue() > fitnessValue)  
				  aV[3]=true;
			}
		      else {
		    	  if(performance.getFitnessValue() < fitnessValue)  
					  aV[3]=true;
		      }

		}
		
		
		
		boolean finalValue=true;
		for(int i=0; i<4;i++)
			if(aC[i]==true)
				finalValue &=aV[i];
			
		
		return finalValue;
	}



	/**
	 * @return string representation of the specified termination criteria
	 */
	public String toString(){
		
		String s="";
		
		if((condition & FINAL_CONDITION_ITERATION) == FINAL_CONDITION_ITERATION){
			s+="Number of iterations " + numberIterations +"\n";
			
		}
		if((condition & FINAL_CONDITION_FITNESSEVALUATIONS) == FINAL_CONDITION_FITNESSEVALUATIONS){
			s+="Number of evaluations " + numberFitnessEvaluations +"\n";
			
		}
		
		if((condition & FINAL_CONDITION_CONVERGENCE) == FINAL_CONDITION_CONVERGENCE){
			s+="Number of times for convergence " + timesConvergence +"\n";
			 
		}
		
		if((condition & FINAL_CONDITION_FITNESSVALUE) == FINAL_CONDITION_FITNESSVALUE){
			s+="Fitness value " + fitnessValue +"\n";
		}
		
		return s;
		
	}
	

	public void setNumberIterations(int numberIterations) {
		this.numberIterations = numberIterations;
	}
	
	
	public int getNumberIterations() {
		return numberIterations;
	}
	
}
