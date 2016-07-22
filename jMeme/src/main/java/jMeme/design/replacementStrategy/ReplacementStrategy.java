/*
 * ReplaceemntStrategy.java 
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

package jMeme.design.replacementStrategy;

import java.io.Serializable;

import jMeme.core.JPopulation;
import jMeme.core.individuals.Individual;


/**
 * This class represents a generic template for the replacement strategies.
 * The class declares an abstract method called <code>execute</code>, which 
 * defines the behavior of the replacement strategy.
 */
public abstract class ReplacementStrategy implements Serializable{


	private static final long serialVersionUID = -4208071688969608336L;
	
	/**
	 * Stores the index in the population of the individual to be replaced
	 */
	protected int modifiedIndex;
	
	/**
	 * Constructor
	 */
	public ReplacementStrategy(){
		
	}
	
	/**
	 * Allows to perform the replacement strategy
	 * 
	 * @param p the population where the new solution must be introduced
	 * @param newSol the new solution to be introduced in the population
	 * @param maximize  true if the problem is to maximize, false otherwise
	 */
	public abstract void execute(JPopulation p, Individual newSol, boolean maximize);
	
	

	public int getModifiedIndex() {
		return modifiedIndex;
	}

	
	public void setModifiedIndex(int modifiedIndex) {
		this.modifiedIndex = modifiedIndex;
	}



}
