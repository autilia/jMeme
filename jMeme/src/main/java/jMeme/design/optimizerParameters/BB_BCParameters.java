/*
 * BB_BCParameters.java 
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
 * This class allows to set the parameters for a BB_BC.
 * The parameters are: the population size, alpha, beta, the center type and the computation type.
 */
public class BB_BCParameters extends GlobalSearchOptimizerParameters{

	/**
	 * 
	 */
	private static final long serialVersionUID = 321862784981578992L;
	
	/**
	 * Constant indicating the default value for the parameter beta
	 */
	public static double DEFAULT_BETA = 0.25;
	/**
	 * Constant indicating the default value for the parameter alpha
	 */
	public static double DEFAULT_ALFA = 10;
	
	/**
	 * Constant indicating to use as center of the mass the best individual
	 */
	public static String CENTER_TYPE_BEST="Best";
	/**
	 * Constant indicating to use the equation (2) of the work [1] as center of the mass.
	 * 
	 * [1] Osman K. Erol, Ibrahim Eksin, A new optimization method: Big Bang-Big Crunch, Advances in Engineering Software, vol. 37, issue 2, pp. 106-111, 2006.
	 */
	public static String CENTER_TYPE_EQUATION2="Equation2";
	
	/**
	 * Constant indicating to use the Erol's version
	 */
	public static String COMPUTATION_TYPE_EROL="Erol";
	/**
	 * Constant indicating to use the Camp's version
	 */
	public static String COMPUTATION_TYPE_CAMP="Camp";
	
	
	/**
	 * Stores the parameter beta controlling the influence of the global best solution
	 */
	private double beta;
	/**
	 * Stores the parameter alfa limiting the size of the search space
	 */
	private double alfa;
	/**
	 * Stores the information about the individual to use during the Big Crunch phase. 
	 * In particular, this individual is the best individual if type is BB_BCParameters.CENTER_TYPE_BEST, it is the center of mass if type is BB_BCParameters.CENTER_TYPE_EQUATION2.
	 */
	private String centerType;
	
	/**
	 * Stores the information about which version between Erol's and Camp's must be executed.
	 * In particular, if this parameter is BB_BCParameters.COMPUTATION_TYPE_EROL, the Erol's version is executed, if it is BB_BCParameters.COMPUTATION_TYPE_CAMP then the Camp's version is executed.
	 */
	private String computationType;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20, the alpha value to 10, the beta value to 0.25, the center type as the equation (2), the version of Camp.
	 */
	public BB_BCParameters()
	{
		super();
		alfa=BB_BCParameters.DEFAULT_ALFA;
		beta=BB_BCParameters.DEFAULT_BETA;
		centerType=BB_BCParameters.CENTER_TYPE_EQUATION2;
		computationType=BB_BCParameters.COMPUTATION_TYPE_CAMP;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given size, the alpha value to 10, the beta value to 0.25, the center type as the equation (2), the version of Camp.
	 * 
	 * @param pop  the population size
	 */
	public BB_BCParameters(int pop)
	{
		super(pop);
		alfa=BB_BCParameters.DEFAULT_ALFA;
		beta=BB_BCParameters.DEFAULT_BETA;
		centerType=BB_BCParameters.CENTER_TYPE_EQUATION2;
		computationType=BB_BCParameters.COMPUTATION_TYPE_CAMP;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given size, alpha to the given value, beta to the given value, the center type as the equation (2), the version of Camp.
	 * 
	 * @param pop  the population size
	 * @param alfa  the alpha parameter
	 * @param beta  the beta parameter
	 */
	public BB_BCParameters(int pop, double alfa, double beta)
	{
		super(pop);
		this.alfa=alfa;
		this.beta=beta;
		centerType=BB_BCParameters.CENTER_TYPE_EQUATION2;
		computationType=BB_BCParameters.COMPUTATION_TYPE_CAMP;
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given size, the alpha value to 10, the beta value to 0.25, the center type to the given type, the version to the given version.
	 * 
	 * @param pop  the population size
	 * @param centerType  the center type
	 * @param computationType  the computation type
	 */
	public BB_BCParameters(int pop, String centerType, String computationType)
	{
		super(pop);
		alfa=BB_BCParameters.DEFAULT_ALFA;
		beta=BB_BCParameters.DEFAULT_BETA;
		this.centerType=centerType;
		this.computationType=computationType;
	}




	/**
	 * @return string representation of the defined BB-BC setting
	 */
	public String toString(){
		String s="";
		s+="Alfa: " + this.alfa;
		s+="\nBeta: " + this.beta;
		s+="\nCenter type: " + this.centerType;
		
		return s;
	}


	public String getCenterType() {
		return centerType;
	}


	public void setCenterType(String centerType) {
		this.centerType = centerType;
	}


	public String getComputationType() {
		return computationType;
	}


	public void setComputationType(String computationType) {
		this.computationType = computationType;
	}


	public double getBeta() {
		return beta;
	}


	public void setBeta(double beta) {
		this.beta = beta;
	}


	public double getAlfa() {
		return alfa;
	}


	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}
	
	
}
