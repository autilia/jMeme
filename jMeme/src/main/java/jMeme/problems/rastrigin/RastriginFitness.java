/*
 * RastriginFitness.java 
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

package jMeme.problems.rastrigin;

import jMeme.core.fitnessFunction.FitnessEvaluationMethod;
import jMeme.core.individuals.Individual;
import jMeme.core.individuals.IndividualComponent;



public class RastriginFitness extends FitnessEvaluationMethod {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7123623539249071871L;

	@Override
	public double evaluate(Individual a) {

		
		
	
		 IndividualComponent[] decisionVariables  = a.getIndividualComponents();

		 int num=decisionVariables.length;
		 
		 double sum = 0.0;
			for (int i = 0; i < num; i++) {
				double xi = (Double)decisionVariables[i].getValue();
				sum += (xi * xi) - (10.0 * Math.cos(2.0 * Math.PI * xi));
			}
			return (10.0 * num) + sum;
	}
	

	
	
}
