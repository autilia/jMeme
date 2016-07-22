/*
 * Problem.java 
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

package jMeme.core;


import jMeme.core.individuals.Individual;
import jMeme.globalSearchAlgorithms.pso.Particle;

import java.util.HashMap;
import java.util.Map;

import org.jgap.InvalidConfigurationException;


public abstract class Problem {

	
	
	/**
	 * Stores the dimension of the problem to solve, i.e.
	 * the number of variables in a solution.
	 */
	protected int dimension;
	
	/**
	 * Stores the unique identifier of the problem to solve
	 */
	protected String id;
	
	
	
	public Problem(){
		id="";
	}
	
	public Problem(String id){
		this.id=id;
	}
	
	public abstract Individual prepareIndividualScheme(JConfiguration c)  throws InvalidConfigurationException;
	
	public abstract Particle prepareParticleScheme(JConfiguration c)  throws InvalidConfigurationException;
	
	public abstract Individual[] generateDefaultIndividuals(JConfiguration c) throws InvalidConfigurationException;
		
	public abstract Map computeProblemPerformance(Individual c);
	
	
	/**
	 * @return string representation of the problem
	 */
	public String toString(){
		return this.getId()+"_"+this.dimension;
	}

	
	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
