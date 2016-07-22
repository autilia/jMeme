/*
 * Optimizer.java 
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

import org.jgap.InvalidConfigurationException;

import jMeme.core.individuals.Individual;


/**
 *  This class implements a generic template for the optimizers developed in
 *  jMeme. Every search optimizer must have a configuration including all its parameters 
 *  and a problem to solve. 
 */
public abstract class Optimizer implements java.io.Serializable {

	private static final long serialVersionUID = -7569075132777788160L;
	
	
	/**
	 * Stores the configuration object for the optimizer 
	 */
	protected JConfiguration configuration;
	
	/**
	 * Stores the best solution provided by the optimizer  
	 */
	protected Individual bestSolution;
	

    /**
     * Allows to set a template for the solution of the problem 
     * to be solved by the optimizer.
     * 
     * @param problem  the problem to solve
     */
	public void setSampleIndividual(Problem problem) {
		try {
			configuration.setSampleIndividual(problem.prepareIndividualScheme(configuration));
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
	public JConfiguration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(JConfiguration configuration) {
		this.configuration = configuration;
	}
	public Individual getBestSolution() {
		return bestSolution;
	}
	public void setBestSolution(Individual bestSolution) {
		this.bestSolution = bestSolution;
	}
	
	
	
}


