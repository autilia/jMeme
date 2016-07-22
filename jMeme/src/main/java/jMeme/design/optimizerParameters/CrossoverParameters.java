/*
 * CrossoverParameters.java 
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

package jMeme.design.optimizerParameters;

import java.io.Serializable;

import org.jgap.BaseGeneticOperator;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CrossoverOperator;

import jMeme.globalSearchAlgorithms.ga.operators.crossover.BLXalpha;
import jMeme.globalSearchAlgorithms.ga.operators.crossover.PermutationCrossover;
import jMeme.globalSearchAlgorithms.ga.operators.crossover.SBXCrossover;


/**
 * This class allows to set the parameters for the used crossover operator.
 * The parameters are: kind of crossover and probability of crossover.
 */
public class CrossoverParameters implements Serializable{


	private static final long serialVersionUID = -7901192813008639011L;
		
	

	/**
	 * Constant indicating the single point crossover
	 */
	public static final String SinglePoint="SinglePoint";
	/**
	 * Constant indicating the BLX crossover with alpha equal to 3
	 */
	public static final String BLXalpha3="BLXalpha3";
	/**
	 * Constant indicating the SBX crossover with distribution index equal to 2
	 */
	public static final String SBXCrossover2="SBXCrossover2";
	/**
	 * Constant indicating the SBX crossover with distribution index equal to 5
	 */
	public static final String SBXCrossover5="SBXCrossover5";
	/**
	 * Constant indicating the permutation crossover
	 */
	public static final String PermutationCrossover="PermutationCrossover";
	
	
	
	/**
	 * Stores the crossover probability
	 */
	private double rate;
	


	/**
	 * Stores the name of the crossover operator to be defined
	 */
	private String crossoverName;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the single point crossover as operator and 0.99 as crossover probability
	 */
	public CrossoverParameters(){
			
			rate=0.99d;
			
			
			this.crossoverName=this.SinglePoint;
		}
		
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the single point crossover as operator and rate as crossover probability
	 * 
	 * @param rate  number indicating the crossover probability
	 */
		public CrossoverParameters(double rate){
			
			this.rate=rate;
		
			this.crossoverName=this.SinglePoint;
		}
		
		
		/**
		 * Costructor
		 * 
		 * Creates an instance of the class that sets the given crossover as operator and rate as crossover probability
		 * 
		 * @param rate  number indicating the crossover probability
		 * @param name  name of the crossover to use (one of the constants: CrossoverParameters.SinglePoint, CrossoverParameters.BLXalpha3, CrossoverParameters.SBX2, CrossoverParameters.SBX5, CrossoverParameters.PermutationCrossover)
		 *  
		 */
		public CrossoverParameters(double rate, String name){
			
			this.rate=rate;
		
			this.crossoverName=name;
		}
		
	
		
		
		/**
		 * Allows to create a new crossover operator starting from a configuration object
		 * @param c  the configuration of an algorithm
		 * @return an instance of a crossover operator
		 */
		public BaseGeneticOperator getCrossoverOperator(Configuration c){
			try {
			if(this.crossoverName.equals(this.SinglePoint))
				return new CrossoverOperator(c,rate);
			else if(this.crossoverName.equals(this.BLXalpha3))
						return new BLXalpha(c, rate,0.3);
				else if(this.crossoverName.equals(this.SBXCrossover2))
						return new SBXCrossover(c, rate,2.0);
					 else if(this.crossoverName.equals(this.SBXCrossover5))
						return new SBXCrossover(c, rate,5.0);
					 else return new PermutationCrossover(c,rate);
				} catch (InvalidConfigurationException e) {
					return null;
				}
			
		}
		
		
		/**
		 * @return string representation of the defined crossover operator
		 */
		public String toString(){
			String s="";
			s="Crossover type: " + crossoverName + "\n";
			s+="Crossover rate: " + rate + "\n";
			
			return s;
		}

		

		public double getRate() {
			return rate;
		}
	
		public void setRate(double rate) {
			this.rate = rate;
		}
		
	
		public String getCrossoverName() {
			return crossoverName;
		}

	
		public void setCrossoverName(String crossoverName) {
			this.crossoverName = crossoverName;
		}

	
	}

