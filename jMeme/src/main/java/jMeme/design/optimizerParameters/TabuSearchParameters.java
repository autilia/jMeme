/*
 * TabuSearchParameters.java 
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


import jMeme.localSearchAlgorithms.tabuSearch.AspirationIfBestSolution;
import jMeme.localSearchAlgorithms.tabuSearch.IAspirationCriteria;
import jMeme.localSearchAlgorithms.tabuSearch.movements.IPossibleMovementsGenerator;
import jMeme.localSearchAlgorithms.tabuSearch.movements.PossibleMovementsRandomGenerator;

public class TabuSearchParameters  extends OptimizerParameters {
	
	
	private static final long serialVersionUID = 4798380051882428352L;
	
	/**
	 * Constant indicating the default value for the number of movements to generate
	 */
	public static final int NUMMOVS_DEFAULT=20;
	/**
	 * Constant indicating the default value for the size of the tabu list
	 */
	public static final int TABULISTSIZE_DEFAULT=10;
	/**
	 * Constant indicating the default value for the information related to the stopping at the first improvement
	 */
	public static final boolean FIRSTIMPROVEMENT_DEFAULT=true;
	
	/**
	 * Stores the object aimed at generating the possible movements
	 */
	private IPossibleMovementsGenerator movementsGenerator;
	
	 /**
     * Stores the size of the tabu list
     */
	private int tabuListSize;
	
	/**
	 * Stores the information about if the search of the best movement must be stopped at the first improvement
	 */
	private boolean firstImprovement;

	/**
	 * Stores the specific termination criteria for TS
	 */
	private IAspirationCriteria aspiration;
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the movement generator to the random movement generator,the size of the tabu list to 10, the first improvement to true.
	 */
	public TabuSearchParameters(){
		super();
		movementsGenerator=new PossibleMovementsRandomGenerator();
		tabuListSize= TabuSearchParameters.TABULISTSIZE_DEFAULT;
		firstImprovement=TabuSearchParameters.FIRSTIMPROVEMENT_DEFAULT;
		aspiration=new AspirationIfBestSolution();
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the movement generator to the given one,the size of the tabu list to 10, the first improvement to true.
	 * 
	 * @param generator  the object aimed at generating the possible movements
	 */
	public TabuSearchParameters(IPossibleMovementsGenerator generator){
		super();
		movementsGenerator=generator;
		tabuListSize= TabuSearchParameters.TABULISTSIZE_DEFAULT;
		firstImprovement=TabuSearchParameters.FIRSTIMPROVEMENT_DEFAULT;
		aspiration=new AspirationIfBestSolution();
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the movement generator to the given one,the size of the tabu list to the given value, the first improvement to true.
	 * 
	 * @param generator  the object aimed at generating the possible movements
	 * @param listSize  the size of the tabu list
	 */
	public TabuSearchParameters(IPossibleMovementsGenerator generator, int listSize){
		super();
		movementsGenerator=generator;
		tabuListSize= listSize;
		firstImprovement=TabuSearchParameters.FIRSTIMPROVEMENT_DEFAULT;
		aspiration=new AspirationIfBestSolution();
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the movement generator to the given one,the size of the tabu list to the given value, the first improvement to the given value.
	 * 
	 * @param generator  the object aimed at generating the possible movements
	 * @param listSize  the size of the tabu list
	 * @param firstImprovement  the information about if the search of the best movement must be stopped at the first improvement
	 */
	public TabuSearchParameters(IPossibleMovementsGenerator generator, int listSize, boolean firstImprovement){
		super();
		movementsGenerator=generator;
		tabuListSize= listSize;
		this.firstImprovement=firstImprovement;
		aspiration=new AspirationIfBestSolution();
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the movement generator to the given one,the size of the tabu list to the given value, the first improvement to the given value, the aspiration criteria to the given object.
	 * 
	 * @param generator  the object aimed at generating the possible movements
	 * @param listSize  the size of the tabu list
	 * @param firstImprovement  the information about if the search of the best movement must be stopped at the first improvement
	 * @param aspiration  the object used to indicate the specific aspiration criterion
	 */
	public TabuSearchParameters(IPossibleMovementsGenerator generator, int listSize, boolean firstImprovement, IAspirationCriteria aspiration){
		super();
		movementsGenerator=generator;
		tabuListSize= listSize;
		this.firstImprovement=firstImprovement;
		this.aspiration=aspiration;
	}
	
	/**
	 * @return string representation of the defined TS setting
	 */
	public String toString(){
		
		 String s="";
		 s+=this.movementsGenerator;
		 s+="\nSize of the tabu list: " + this.tabuListSize;
		 
		 return s;
	 }
	
 public IPossibleMovementsGenerator getMovementsGenerator() {
		return movementsGenerator;
	}


	public void setMovementsGenerator(IPossibleMovementsGenerator movementsGenerator) {
		this.movementsGenerator = movementsGenerator;
	}


	public int getTabuListSize() {
		return tabuListSize;
	}


	public void setTabuListSize(int tabuListSize) {
		this.tabuListSize = tabuListSize;
	}


	public boolean isFirstImprovement() {
		return firstImprovement;
	}


	public void setFirstImprovement(boolean firstImprovement) {
		this.firstImprovement = firstImprovement;
	}


	public IAspirationCriteria getAspiration() {
		return aspiration;
	}


	public void setAspiration(IAspirationCriteria aspiration) {
		this.aspiration = aspiration;
	}




}
