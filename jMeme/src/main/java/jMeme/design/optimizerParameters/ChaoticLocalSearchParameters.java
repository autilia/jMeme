/*
 * ChaoticLocalSearchParameters.java 
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
 * This class allows to set the parameters for the chaotic local search procedure.
 * The parameters are: the number of neighbour to generate.
 */
public class ChaoticLocalSearchParameters extends OptimizerParameters {


	private static final long serialVersionUID = 4284087753776271137L;
	
	/**
	 * Constant indicating the default value for the number of the neighbours to generate
	 */
	public static final int NUMOFNEIGHBOURS_DEFAULT=10;
	

	/**
	 * Stores the number of neighbours to generate
	 */
	private int numOfNeighbours;
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the number of neighbours to generate to 10.
	 */
	public ChaoticLocalSearchParameters(){
		super();
		numOfNeighbours=ChaoticLocalSearchParameters.NUMOFNEIGHBOURS_DEFAULT;
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the number of neighbours to generate to the given value.
	 * 
	 * @param numMov  the number of neighbours to generate
	 */
	public ChaoticLocalSearchParameters( int numMov){
		super();
		this.numOfNeighbours=numMov;
	}
	
	/**
	 * @return string representation of the defined chaotic local search procedure
	 */
	public String toString(){
		String s="";
		s+="Number of Neighbours: " + this.numOfNeighbours + "\n";
		
		return s;
	}



	public int getNumMov() {
		return numOfNeighbours;
	}


	public void setNumMov(int numMov) {
		this.numOfNeighbours = numMov;
	}



	
	
}
