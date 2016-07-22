/*
 * LocalIntensity.java 
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

import java.io.Serializable;

import jMeme.design.finalConditions.LocalOptimizerFinalConditions;

/**
 * This class implements one of the main components for the integration between global and local searches: the local intensity.
 * The local intensity is the amount of computational budget allowable for individual learning to expend on improving a single solution.
 * The computational budget can be expressed in terms of number of fitness evaluations or iterations.
 */
public class LocalIntensity implements Serializable{

	
	private static final long serialVersionUID = 672094248544347642L;
	/**
	 * Stores the number of fitness evaluations to be reached to stop the local search procedure
	 */
	private int numberLocalEvaluations;
	/**
	 * Stores the number of fitness evaluations to be reached to stop the local search procedure
	 */
	private int numberLocalIterations;
	/**
	 * Stores the termination criterion. This can be LOCAL_FITNESS_EVALUATIONS or LOCAL_ITERATIONS
	 */
	private short condition;
	
	/**
	 * Constant indicating the following termination criterion: the local procedure runs until the specified number of fitness evaluations is reached
	 */
	public static short LOCAL_FITNESS_EVALUATIONS=LocalOptimizerFinalConditions.FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED;
	/**
	 * Constant indicating the following termination criterion: the local procedure runs until the specified number of iterations is reached
	 */
	public static short LOCAL_ITERATIONS=LocalOptimizerFinalConditions.FINAL_CONDITION_ITERATION_INTEGRATED;
	
	
	/**
	 * Stores the default number of iterations to be performed by the local search procedure
	 */
	public static final int DEFAULT_LOCAL_ITERATIONS=10;
	/**
	 * Stores the default number of fitness evaluations to be performed by the local search procedure
	 */
	public static final int DEFAULT_LOCAL_EVALUATIONS=10;
	
	/**
	 * Constructor
	 */
	public LocalIntensity(){
		this(LocalIntensity.LOCAL_FITNESS_EVALUATIONS,LocalIntensity.DEFAULT_LOCAL_EVALUATIONS);
	}
	
	/**
	 * Constructor
	 * 
	 * @param value number of the fitness evaluations to be performed by the local search procedure
	 */
	public LocalIntensity(int value){
		this(LocalIntensity.LOCAL_FITNESS_EVALUATIONS,value);
	}
	
	/**
	 * Constructor 
	 * 
	 * @param condition the termination criterion of the local procedure
	 * @param value number of the fitness evaluations or iterations according to the specified condition
	 */
	public LocalIntensity(short condition, int value) {
		
		this.condition=condition;
		
			
		if (this.condition == LocalIntensity.LOCAL_FITNESS_EVALUATIONS)
			numberLocalEvaluations = value;
		
		if (this.condition == LocalIntensity.LOCAL_ITERATIONS)
			numberLocalIterations = value;
		
	}
	

	/**
	 * representation string of the local intensity
	 */
	public String toString(){
		String s="";
		s+="Local Intensity: " + this.numberLocalEvaluations;
		
		return s;
	}

	public int getNumberLocalEvaluations() {
		return numberLocalEvaluations;
	}

	public void setNumberLocalEvaluations(int numberLocalEvaluations) {
		this.numberLocalEvaluations = numberLocalEvaluations;
	}

	public int getNumberLocalIterations() {
		return numberLocalIterations;
	}

	public void setNumberLocalIterations(int numberLocalIterations) {
		this.numberLocalIterations = numberLocalIterations;
	}

	public short getCondition() {
		return condition;
	}

	public void setCondition(short condition) {
		this.condition = condition;
	}
	

}
