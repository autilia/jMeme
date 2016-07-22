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

import jMeme.localSearchAlgorithms.hillClimbing.GenerateSuccessors;

/**
 * This class allows to set the parameters for the Hill climbing procedure and its Steepest variant.
 * The parameters are: the number of neighbor to generate, the maximum distance between a solution and one of its neighbors, the way how the neighbors must be generated.
 */
public class HillClimbingParameters extends OptimizerParameters {

	private static final long serialVersionUID = 5508424086663164587L;
	/**
	 * Constant indicating the default value for the maximum distance between a solution and one of its neighbours
	 */
	public static final int MAXDIST_DEFAULT=1;
	/**
	 * Constant indicating the default value for the number of the neighbors to generate
	 */
	public static final int NUMOFNEIGHBOURS_DEFAULT=10;
	
	/**
	 * Stores the maximum distance between a solution and one of its neighbors
	 */
	private int maxDist;
	/**
	 * Stores the default value for the number of the neighbors to generate
	 */
	private int numOfNeighbours;
	/**
	 * Stores the object aimed at generating neighborhood of a solution
	 */
	private GenerateSuccessors genSuccessors;

	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the number of neighbours to generate to 10, the maximum distance between a solution and one of its neighbors to 1, the generation of neighbors in a random way.
	 */
	public HillClimbingParameters(){
		super();
		maxDist=HillClimbingParameters.MAXDIST_DEFAULT;
		numOfNeighbours=HillClimbingParameters.NUMOFNEIGHBOURS_DEFAULT;
		genSuccessors=new GenerateSuccessors(); 
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the number of neighbours to generate to 10, the maximum distance between a solution and one of its neighbors to the given value, the generation of neighbors in a random way.
	 *
	 * @param  maxDist  the maximum distance between a solution and one of its neighbors
	 */
	public HillClimbingParameters(int maxDist){
		super();
		this.maxDist=maxDist;
		numOfNeighbours=HillClimbingParameters.NUMOFNEIGHBOURS_DEFAULT;
		genSuccessors=new GenerateSuccessors();
	}


	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the number of neighbours to generate to the given value, the maximum distance between a solution and one of its neighbors to the given value, the generation of neighbors in a random way.
	 *
	 * @param  maxDist  the maximum distance between a solution and one of its neighbors
	 * @param  numMov  the number of the neighbors to generate
	 */
	public HillClimbingParameters(int maxDist, int numMov){
		super();
		this.maxDist=maxDist;
		this.numOfNeighbours=numMov;
		genSuccessors=new GenerateSuccessors();
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the number of neighbours to generate to the given value, the maximum distance between a solution and one of its neighbors to the given value, the generation of neighbors to the given generator.
	 *
	 * @param  maxDist  the maximum distance between a solution and one of its neighbors
	 * @param  numMov  the number of the neighbors to generate
	 * @param  genSuc  the generator of neighbors for a solution
	 */
	public HillClimbingParameters(int maxDist, int numMov, GenerateSuccessors genSuc){
		super();
		this.maxDist=maxDist;
		this.numOfNeighbours=numMov;
		genSuccessors=genSuc;
	}

	
	/**
	 * @return string representation of the defined hill climbing procedure
	 */
	public String toString(){
		String s="";
		s="Max distance: " + maxDist + "\n";
		s+="Number of Neighbours: " + this.numOfNeighbours + "\n";
		
		return s;
	}
	
	public int getMaxDist() {
		return maxDist;
	}


	
	public void setMaxDist(int maxDist) {
		this.maxDist = maxDist;
	}


	public int getNumMov() {
		return numOfNeighbours;
	}


	public void setNumMov(int numMov) {
		this.numOfNeighbours = numMov;
	}


	public GenerateSuccessors getGenSuccessors() {
		return genSuccessors;
	}



	public void setGenSuccessors(GenerateSuccessors genSuccessors) {
		this.genSuccessors = genSuccessors;
	}
	

	
}
