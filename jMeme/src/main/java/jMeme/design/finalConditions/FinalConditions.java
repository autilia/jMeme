/*
 * FinalConditions.java 
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

import java.io.Serializable;

import jMeme.core.JConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.performances.AlgorithmPerformance;

public abstract class FinalConditions implements Serializable{
	
	private static final long serialVersionUID = -4162297864401534174L;
	

	/**
	 * Constant representing the following termination criterion: stop the algorithm when a number of fitness evaluations is achieved 
	 */
	public static final short FINAL_CONDITION_FITNESSEVALUATIONS = 1;
	/**
	 * Constant representing the following termination criterion: stop the algorithm when the evolution achieves convergence 
	 */
	public static final short FINAL_CONDITION_CONVERGENCE = 2;
	/**
	 * Constant representing the following termination criterion: stop the algorithm when the best solution has a specified fitness value 
	 */
	public static final short FINAL_CONDITION_FITNESSVALUE = 4;
	
	
	/**
	 * Stores the combination of conditions used to stop the algorithm
	 */
	protected short condition;
	
	
	
	/**
	 * Stores the number of fitness evaluations to be reached to stop the algorithm if FINAL_CONDITION_FITNESSEVALUATIONS is set
	 */
	protected int numberFitnessEvaluations;

	
	/**
	 * Stores the number of times that the the best fitness value does not change to be reached to stop the algorithm if FINAL_CONDITION_CONVERGENCE is set
	 */
	protected int timesConvergence;


	/**
	 * Stores the fitness value to be reached to stop the algorithm if FINAL_CONDITION_FITNESSVALUE is set
	 */
	protected double fitnessValue;
	
	/**
	 * Stores the sign of the fitness function: 0 for min and 1 for max
	 */
	protected int signFitness;
	
	
	
	/**
	 * Constructor 
	 */
	public FinalConditions(){
		
		condition=FINAL_CONDITION_FITNESSEVALUATIONS;
		
		numberFitnessEvaluations=5000;
		timesConvergence=50;
		fitnessValue=0.0;
		
		this.signFitness=FitnessFeatures.MIN;
	}
	
	/**
	 * Costructor 
	 * 
	 * @param condition represent the termination criteria specification
	 * @param numberOfFitnessEvaluations  the number of fitness evaluations to be reached to stop the algorithm
	 * @param timesConvergence  the number of times that the best fitness value does not change to be reached to stop the algorithm
	 * @param fitnessValue  the fitness value to be reached to stop the algorithm
	 */
	public FinalConditions(short condition, int numberOfFitnessEvaluations, int timesConvergence, double fitnessValue) {
		this.condition = condition;
		
		
		this.numberFitnessEvaluations = numberOfFitnessEvaluations;
		this.timesConvergence= timesConvergence;
		this.fitnessValue=fitnessValue;
		this.signFitness=FitnessFeatures.MIN;
	}
	
	
	/**
	 * Costructor 
	 * 
	 * @param condition  termination criterion to stop the algorithm
	 * @param value  integer value representing the number of fitness evaluations if the specified condition is FINAL_CONDITION_FITNESSEVALUATIONS
	 *               or the number of the times that the best fitness value does not change if the specified condition is FINAL_CONDITION_CONVERGENCE
	 */
	public FinalConditions(short condition, int value) {
		this.condition = condition;
		
		if (this.condition == FINAL_CONDITION_FITNESSEVALUATIONS)
			numberFitnessEvaluations = value;
		
			
		if (this.condition == FINAL_CONDITION_CONVERGENCE)
			timesConvergence = value;
		
	}
	
	/**
	 * Constructor 
	 * 
	 * @param condition  termination criterion to stop the algorithm
	 * @param value  double value representing the fitness value to be reached to stop the algorithm if the specified condition is FINAL_CONDITION_FITNESSVALUE
	 */
	public FinalConditions(short condition, double value) {
		this.condition = condition;
		
		if (this.condition == FINAL_CONDITION_FITNESSVALUE){
			fitnessValue = value;
			this.signFitness=FitnessFeatures.MIN;
		}
	}
	
	/**
	 * Costructor 
	 * 
	 * @param condition  the combination of termination criteria to stop the algorithm
	 * @param value1  integer value representing the number of fitness evaluations if the specified condition is FINAL_CONDITION_FITNESSEVALUATIONS
	 *                or the number of the times that the best fitness value does not change if the specified condition is FINAL_CONDITION_CONVERGENCE
	 * @param value2  double value representing the fitness value to be reached to stop the algorithm if the specified condition is FINAL_CONDITION_FITNESSVALUE
	 */
	public FinalConditions(short condition, int value1, double value2){
		
        this.condition = condition;
		
		if ((this.condition & FINAL_CONDITION_FITNESSEVALUATIONS) == FINAL_CONDITION_FITNESSEVALUATIONS)
			numberFitnessEvaluations = value1;
		
		if ((this.condition  & FINAL_CONDITION_CONVERGENCE)== FINAL_CONDITION_CONVERGENCE)
			timesConvergence = value1;
		
		if ((this.condition & FINAL_CONDITION_FITNESSVALUE)==FINAL_CONDITION_FITNESSVALUE){
			
			fitnessValue = value2;
			this.signFitness=FitnessFeatures.MIN;
		}
		

		
	}
	

	
	
	/**
	 * Allows to verify if the termination criteria are achieved
	 * 
	 * @param conf the configuration of the algorithm to stop
	 * @return boolean  true if the specified termination criteria are achieved, false, otherwise
	 */
	public abstract boolean verify(JConfiguration conf);
		
	
	
	/**
	 * Allows to verify if the specified number of the fitness evaluations is achieved 
	 * 
	 * @param conf  the configuration of the algorithm to stop
	 * @return  true if the specified number of the fitness evaluations are achieved, false, otherwise
	 */
	public boolean checkNumberEvaluations(JConfiguration conf){
		
		AlgorithmPerformance performance = conf.getPerformance();
		
		if((condition & FINAL_CONDITION_FITNESSEVALUATIONS) == FINAL_CONDITION_FITNESSEVALUATIONS){
			if(performance.getNumberOfFitnessEvaluations()>numberFitnessEvaluations)  
				return true;
		}
		
		return false;
		
	}
	
	

	
	public int getNumberFitnessEvaluations() {
		return numberFitnessEvaluations;
	}

	public void setNumberFitnessEvaluations(int numberFitnessEvaluations) {
		this.numberFitnessEvaluations = numberFitnessEvaluations;
	}

		public double getFitnessValue() {
		return fitnessValue;
	}

	
	 public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	
	public void setCondition(short condition) {
		this.condition = condition;
	}

	
	public short getCondition() {
		return condition;
	}

	public void setCondition(byte condition) {
		this.condition = condition;
	}

	
	public int getTimesConvergence() {
		return timesConvergence;
	}

	public void setTimesConvergence(int timesConvergence) {
		this.timesConvergence = timesConvergence;
	}

	
	public int getSignFitness() {
		return signFitness;
	}

	
	public void setSignFitness(int signFitness) {
		this.signFitness = signFitness;
	}





	
}
