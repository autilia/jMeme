/*
 * HookeJeevesParameters.java 
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


/**
 * This class allows to set the parameters for a Hooke and Jeeves (HJ) algorithm.
 * The parameters are: the epsilon and rho.
 */
public class HookeJeevesParameters extends OptimizerParameters {
	
	private static final long serialVersionUID = 2867894472915634275L;
	
	/**
	 * Constant indicating the default value for the epsilon parameter
	 */
	public static double EPSILON_DEFAULT=1;
	/**
	 * Constant indicating the default value for the rho
	 */
	public static double RHO_DEFAULT=0.1;
	
	/**
	 * Stores the halting criterion.  Larger values of epsilon give quicker running time, but a	less accurate solution.
	 */
	private double epsilon;
	/**
	 * Stores a user-supplied convergence parameter which should be set to a value between 0.0 and 1.0.	Larger	   
     * values of rho give greater probability of convergence on highly nonlinear functions, at a 
     * cost of more function evaluations.
	 */
	private double rho;
	
	/**
	 * Constructor 
	 * 
	 * Creates an instance of the class that sets epsilon to 1 and rho to 0.1.
	 */
	public HookeJeevesParameters(){
		super();
		epsilon=HookeJeevesParameters.EPSILON_DEFAULT;
		rho=HookeJeevesParameters.RHO_DEFAULT;
	}
	
	/**
	 * Constructor 
	 * 
	 * Creates an instance of the class that sets epsilon to the given value and rho to 0.1.
	 */
	public HookeJeevesParameters(double epsilon){
		super();
		this.epsilon=epsilon;
		rho=HookeJeevesParameters.RHO_DEFAULT;
	}


	/**
	 * Constructor 
	 * 
	 * Creates an instance of the class that sets epsilon to the given value and rho to the given value.
	 */
	public HookeJeevesParameters(double epsilon, double rho){
		super();
		this.epsilon=epsilon;
		this.rho=rho;
	}



	public double getEpsilon() {
		return epsilon;
	}


	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}


	public double getRho() {
		return rho;
	}


	public void setRho(double rho) {
		this.rho = rho;
	}
	
	/**
	 * @return string representation of the defined HJ setting
	 */
	public String toString(){
		String s="";
		s="Epsilon: " + this.epsilon+ "\n";
		s+="Rho: " + this.rho + "\n";
		
		return s;
	}
		
		
	
}
