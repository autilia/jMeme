/*
 * FitnessFeatures.java 
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

package jMeme.core.fitnessFunction;

import org.jgap.DefaultFitnessEvaluator;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.FitnessEvaluator;

import jMeme.core.JFitnessFunction;


public class FitnessFeatures implements java.io.Serializable{

	private static final long serialVersionUID = -6378197373097926727L;
	
	/**
	 * Constant to specify the sign of the fitness function equal to min
	 */
	public static final int MIN=0;
	/**
	 * Constant to specify the sign of the fitness function equal to max
	 */
	public static final int MAX=1;
	
	
	/**
	 * Stores the fitness function
	 */
	private JFitnessFunction fitnessFunction;
	
	/**
	 * Stores the kind of evaluator.
	 * The choice is between DeltaFitnessEvaluator for maxizing and DefaultFitnessEvaluator for minimizing
	 */
	private FitnessEvaluator fitnessEvaluator;
	
	/**
	 * Stores information about the sign of the fitness function.
	 * The possible values are: 0 for min, 1 for max
	 */
	private int fitnessSign;
	

	/**
	 * Constructor 
	 * 
	 * @param fitnessMethod object that contains the implementation of the fitness function specific for a user problem
	 */
	public FitnessFeatures(FitnessEvaluationMethod fitnessMethod){
		this.fitnessFunction=new JFitnessFunction(fitnessMethod);
		this.fitnessEvaluator= new DeltaFitnessEvaluator();
		fitnessSign=FitnessFeatures.MIN;
	}

	/**
	 * Constructor 
	 * 
	 * @param fitnessMethod object that contains the implementation of the fitness function specific for a user problem
	 * @param sign integer representing the sign of the fitness function: 0 for min, 1 for max
	 */
	public FitnessFeatures(FitnessEvaluationMethod fitnessMethod, int sign){
		this.fitnessFunction=new JFitnessFunction(fitnessMethod);
		setFitnessSign(sign);
		
	}
	
	
	public JFitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

	public void setFitnessFunction(JFitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}

	public FitnessEvaluator getFitnessEvaluator() {
		return fitnessEvaluator;
	}

	public void updateFitnessEvaluator(FitnessEvaluator fitnessEvaluator) {
		this.fitnessEvaluator = fitnessEvaluator;
	}


	public int getFitnessSign() {
		return fitnessSign;
	}


	/**
	 * Allows to set the fitness evaluator starting from the sign of the fitness function
	 * 
	 * @param fitnessSign sign of the fitness function: 0 for min and 1 for max
	 */
	public void setFitnessSign(int fitnessSign) {
		this.fitnessSign = fitnessSign;
		if(fitnessSign==FitnessFeatures.MIN)
		    this.fitnessEvaluator = new DeltaFitnessEvaluator();
		else if(fitnessSign==FitnessFeatures.MAX)
		    this.fitnessEvaluator = new DefaultFitnessEvaluator();
		else {
			this.fitnessEvaluator = null;
		}
	}


	/**
	 * Allows to know if the sign of the fitness function is to maximize
	 * @return boolean: true if the sign of the fitness function is max, false otherwise
	 */
	public boolean isMaximize()
	{
		if(this.getFitnessSign()==FitnessFeatures.MAX)
			return true;
	
		return false;
	}


}
