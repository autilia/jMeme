/*
 * FitnessEvaluationMethod.java 
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

package jMeme.core.fitnessFunction;

import jMeme.core.individuals.Individual;

public abstract class FitnessEvaluationMethod implements java.io.Serializable{

	private static final long serialVersionUID = -5753118255978431079L;

	/**
	 * Allows to implement the evaluation of an individual specific for a problem
	 * 
	 * @param a_subject object representing the individual to evaluate
	 * @return double representing the fitness value
	 */
	public abstract double evaluate(Individual a_subject) ;
	 
}
