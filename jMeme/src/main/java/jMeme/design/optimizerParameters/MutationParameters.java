/*
 * MutationParameters.java 
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
import org.jgap.impl.GaussianMutationOperator;
import org.jgap.impl.MutationOperator;

import jMeme.globalSearchAlgorithms.ga.operators.mutation.PolynomialMutation;

/**
 * This class allows to set the parameters for the used mutation operator.
 * The parameters are: kind of mutation and probability of mutation.
 */
public class MutationParameters implements Serializable {

	private static final long serialVersionUID = 7251522302899305907L;

	/**
	 * Constant indicating the Uniform Mutation
	 */
	public static final String Uniform= "Uniform";
	/**
	 * Constant indicating the Gaussian Mutation
	 */
	public static final String Gaussian = "Gaussian";
	/**
	 * Constant indicating the Polynomial Mutation
	 */
	public static final String Polynomial = "Polynomial";
	
	
	/**
	 * Stores a number used to compute the mutation probability as 1/rate
	 */
	private int rate;
	/**
	 * Stores the name of the mutation operator to be defined
	 */
	private String mutationName;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the Uniform mutation as operator and mutation probability
	 */
	public MutationParameters(){
		rate=50;
		mutationName=this.Uniform;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the Uniform mutation as operator and 1/rate as mutation probability
	 * 
	 * @param rate  number to use for compute the mutation probability
	 */
	public MutationParameters(int rate){
		this.rate=rate;
		mutationName=this.Uniform;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the given mutation as operator and 1/rate as probability mutation.
	 * 
	 * @param rate  number to use for compute the mutation probability
	 * @param name  name of the mutation to use (one of the constants: MutationParameters.Uniform, MutationParameters.Gaussian, MutationParameters.Polynomial)
	 */
	public MutationParameters(int rate, String name){
		this.rate=rate;
		mutationName=name;
	}
	
	/**
	 * Allows to create a new mutation operator starting from a configuration object
	 * @param c  the configuration of an algorithm
	 * @return an instance of a mutation operator
	 */
	public BaseGeneticOperator getMutationOperator(Configuration c)
	{
		try {
		if(mutationName.equals(this.Uniform))
			return new MutationOperator(c,rate);
		else if(mutationName.equals(this.Gaussian))
			return new GaussianMutationOperator(c,rate);
		else  if(mutationName.equals(this.Polynomial))
			return new PolynomialMutation(c,rate);
		else return null;
		} catch (InvalidConfigurationException e) {
			return null;
		}
	}
	

	
	
	/**
	 * @return string representation of the defined mutation operator
	 */
	public String toString(){
		String s="";
		s="Mutation type: " + mutationName + "\n";
		s+="Mutation rate: " + (1/(double)rate) + "\n";
		
		return s;
	}
	

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getMutationName() {
		return mutationName;
	}

	public void setMutationName(String mutationName) {
		this.mutationName = mutationName;
	}



	
	
}
