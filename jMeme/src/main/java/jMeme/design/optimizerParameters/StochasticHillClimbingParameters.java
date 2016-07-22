/*
 * HillClimbingParameters.java 
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
 * This class allows to set the parameters for the Stochastic hill climbing procedure.
 * The parameters are: the maximum distance between a solution and one of its neighbors.
 */
public class StochasticHillClimbingParameters extends OptimizerParameters {

	
	private static final long serialVersionUID = -7218786963654386601L;

	/**
	 * Constant indicating the default value for the maximum distance between a solution and one of its neighbours
	 */
	public static final int MAXDIST_DEFAULT=1;
	/**
	 * Stores the maximum distance between a solution and one of its neighbors
	 */
	private int maxDist;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the maximum distance between a solution and one of its neighbors to 1.
	 */
	public StochasticHillClimbingParameters(){
		super();
		maxDist=StochasticHillClimbingParameters.MAXDIST_DEFAULT;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the maximum distance between a solution and one of its neighbors to the given value.
	 */
	public StochasticHillClimbingParameters(int maxDist){
		super();
		this.maxDist=maxDist;
	}

	/**
	 * @return string representation of the defined Stochastic hill climbing procedure
	 */
	public String toString(){
		String s="";
		s="Max distance: " + maxDist + "\n";
		
		return s;
	}
	
	
	public int getMaxDist() {
		return maxDist;
	}


	
	public void setMaxDist(int maxDist) {
		this.maxDist = maxDist;
	}
	

}
