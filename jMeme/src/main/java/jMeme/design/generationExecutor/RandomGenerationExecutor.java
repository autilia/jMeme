/*
 * RandomGenerationExecutor.java 
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

package jMeme.design.generationExecutor;


import org.jgap.InvalidConfigurationException;
import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;


/**
 * This class implements a population generation strategy consisting into generating a random population.
 */
public class RandomGenerationExecutor extends GenerationExecutor{

	private static final long serialVersionUID = -6530225790784434837L;

	/**
	 * Costructor
	 */
	public RandomGenerationExecutor(){
		super();
	}
	

	/**
	 * Allows to create an initial population for the global search algorithms
	 * 
	 * @param configuration  object that contains all information about the algorithm to run
	 * @return the initial population of individuals to evolve 
	 */
	public JPopulation execute(JConfiguration configuration){

		//	create random population
		JPopulation p=null;
		try {
			p = JPopulation.randomInitialPopulation(configuration);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
		}
	
	
}
