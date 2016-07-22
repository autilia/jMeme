/*
 * DEParameters.java 
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
 * This class allows to set the parameters for a differential evolution algorithm.
 * The parameters are: the population size, the factor F, the crossover constant CR, the vector to be perturbed, the number of difference vectors considered for perturbation.
 */
public class DEParameters extends GlobalSearchOptimizerParameters{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5224579614529340750L;
	
	
	/**
	 * Constant indicating the default value for the factor F
	 */
	public static final double DEFAULT_F=1;
	/**
	 * Constant indicating the default value for the crossover constant CR
	 */
	public static final double DEFAULT_CR=0.5;
	/**
	 * Constant indicating that the vector to be perturbed is a random one
	 */
	public static final String xTypeRand= "Rand";
	/**
	 * Constant indicating that the vector to be perturbed is the best one
	 */
	public static final String xTypeBest= "Best";
	/**
	 * Constant indicating that the vector to be perturbed is at a location between a randomly
     * chosen individual and the best individual
	 */
	public static final String xTypeRandBest= "RandToBest";
	/**
	 * Constant indicating that the number of difference vectors considered for perturbation is 1
	 */
	public static final int yType1=1;
	/**
	 * Constant indicating that the number of difference vectors considered for perturbation is 2
	 */
	public static final int yType2=2;

	
	
	/**
	 * Stores the scaling factor F
	 */
	private double F;  
	/**
	 * Stores the crossover constant
	 */
	private double CR;  
	/**
	 * Stores the greedness of the scheme used by the DE
	 */
	private double Lambda;
    /**
	 * Stores a string denoting the vector to be perturbed. It can be DEParameters.xTypeBest, DEParameters.xTypeRand, DEParameters.xTypeRandBest
	 */
    private String xType;
    /**
     * Stores the number of difference vectors considered for perturbation. It can be DEParameters.yType1 or DEParameters.yType2.
     */
	public static int yType;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20, the factor F to 1, the parameter CR to 0.5, the parameter lambda to 1, the vector to be perturbed to a random one, the number of difference vectors considered for perturbation to 1.
	 */
	public DEParameters()
	{
		super();
		
		 F=DEParameters.DEFAULT_F;
		 Lambda=DEParameters.DEFAULT_F;
		 CR=DEParameters.DEFAULT_CR;
		 xType=DEParameters.xTypeRand;
		 yType=DEParameters.yType1;
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given value, the factor F to 1, the parameter CR to 0.5, the parameter lambda to 1, the vector to be perturbed to a random one, the number of difference vectors considered for perturbation to 1.
	 * 
	 * @param pop  the population size
	 */
	public DEParameters(int pop)
	{
		super(pop);
		 F=DEParameters.DEFAULT_F;
		 Lambda=DEParameters.DEFAULT_F;
		 CR=DEParameters.DEFAULT_CR;
		 xType=DEParameters.xTypeRand;
		 yType=DEParameters.yType1;
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given value, the factor F to the given value, the parameter CR to 0.5, the parameter lambda to 1, the vector to be perturbed to a random one, the number of difference vectors considered for perturbation to 1.
	 * 
	 * @param pop  the population size
	 * @param f  the factor F
	 */
	public DEParameters(int pop, double f)
	{
		super(pop);
		 F=f;
		 Lambda=f;
		 CR=DEParameters.DEFAULT_CR;
		 xType=DEParameters.xTypeRand;
		 yType=DEParameters.yType1;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given value, the factor F to the given value, the parameter CR to the given value, the parameter lambda to 1, the vector to be perturbed to a random one, the number of difference vectors considered for perturbation to 1.
	 * 
	 * @param pop  the population size
	 * @param f  the factor F
	 * @param cr  the crossover constant CR
	 */
	public DEParameters(int pop,  double f, double cr)
	{
		super(pop);
		 F=f;
		 Lambda=f;
		 CR=cr;
		 xType=DEParameters.xTypeRand;
		 yType=DEParameters.yType1;
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given value, the factor F to the given value, the parameter CR to the given value, the parameter lambda to 1, the vector to be perturbed to the given string, the number of difference vectors considered for perturbation to 1.
	 *
	 * @param pop  the population size
	 * @param f  the factor F
	 * @param cr  the crossover constant CR
	 * @param xType  the string indicating the vector to be perturbed
	 */
	public DEParameters(int pop, double f, double cr, String xtype)
	{
		super(pop);
		 F=f;
		 Lambda=f;
		 CR=cr;
		 xType=xtype;
		 yType=DEParameters.yType1;
	}

	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given value, the factor F to the given value, the parameter CR to the given value, the parameter lambda to 1, the vector to be perturbed to the given string, the number of difference vectors considered for perturbation to the given string.
	 *
	 * @param pop  the population size
	 * @param f  the factor F
	 * @param cr  the crossover constant CR
	 * @param xType  the string indicating the vector to be perturbed
	 * @param yType  the string indicating the difference vectors considered for perturbation
	 */
	public DEParameters(int pop,  double f, double cr, String xtype, int ytype)
	{
		super(pop);
		 F=f;
		 Lambda=f;
		 CR=cr;
		 xType=xtype;
		 yType=ytype;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given value, the factor F to the given value, the parameter CR to the given value, the parameter lambda to the given value, the vector to be perturbed to the given string, the number of difference vectors considered for perturbation to the given string.
	 *
	 * @param pop  the population size
	 * @param f  the factor F
	 * @param cr  the crossover constant CR
	 * @param xType  the string indicating the vector to be perturbed
	 * @param yType  the string indicating the difference vectors considered for perturbation
	 * @param lambda  the parameter lambda
	 */
	public DEParameters(int pop, double f, double cr, String xtype, int ytype, double lambda)
	{
		super(pop);
		 F=f;
		 Lambda=lambda;
		 CR=cr;
		 xType=xtype;
		 yType=ytype;
	}

	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20, the factor F to the given value, the parameter CR to the given value, the parameter lambda to 1, the vector to be perturbed to the given string, the number of difference vectors considered for perturbation to the given string.
	 * 
	 * @param f  the factor F	
	 * @param cr  the crossover constant CR
	 * @param xType  the string indicating the vector to be perturbed
	 * @param yType  the string indicating the difference vectors considered for perturbation
	 * @param lambda  the parameter lambda
     */
	public DEParameters( double f, double cr, String xtype, int ytype, double lambda)
	{
		super();
		 F=f;
		 Lambda=lambda;
		 CR=cr;
		 xType=xtype;
		 yType=ytype;
	}
	
	
	/**
	 * @return string representation of the defined BB-BC setting
	 */
	public String toString(){
		String s="";
		s="Lambda : " + this.Lambda+ "\n";
		s+="CR: " + this.CR + "\n";
		s+="F: " + this.F + "\n";
		s+="xType: " + this.xType + "\n";
		s+="yType: " + this.yType + "\n";
		
		return s;
	}


	public double getF() {
		return F;
	}



	public void setF(double f) {
		F = f;
	}



	public double getCR() {
		return CR;
	}


	
	public void setCR(double cR) {
		CR = cR;
	}



	public double getLambda() {
		return Lambda;
	}


	
	public void setLambda(double lambda) {
		Lambda = lambda;
	}



	public String getxType() {
		return xType;
	}



	public void setxType(String xType) {
		this.xType = xType;
	}


	public int getyType() {
		return yType;
	}


	
	public void setyType(int yType) {
		this.yType = yType;
	}



	

	
	
	
}
