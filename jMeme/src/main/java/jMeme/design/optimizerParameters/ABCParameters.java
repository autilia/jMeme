/*
 * ABC_Parameters.java 
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
 * This class allows to set the parameters for an ABC.
 * The parameters are: the population size and the limit.
 */
public class ABCParameters extends GlobalSearchOptimizerParameters{

	

	private static final long serialVersionUID = -5085518650391486247L;
	/**
	 * Constant indicating the default value for the parameter limit
	 */
	public static final int DEFAULT_LIMIT=100;
	  
	
	/**
	 * Stores the number of trials for releasing a food source
	 */
	private int limit;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and the parameter limit to 100.
	 */
	public ABCParameters()
	{
		super();
		limit=ABCParameters.DEFAULT_LIMIT;
	}
	

	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given size  and the parameter limit to 100.
	 * 
	 * @param pop  the population size
	 */
	public ABCParameters(int pop)
	{
		super(pop);
		limit=ABCParameters.DEFAULT_LIMIT;
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given size and the parameter limit to the given value.
	 * 
	 * @param pop  the population size
	 * @param limit the value for the parameter limit
	 */
	public ABCParameters(int pop, int limit)
	{
		super(pop);
		this.limit=limit;
	}
	
	
	/**
	 * @return string representation of the defined ABC setting
	 */
	public String toString(){
		String s="";
		s+="ABC limit: " + this.limit;
		
		return s;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	


	
	
}
