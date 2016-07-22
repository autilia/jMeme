/*
 * RosenbrockFitness.java 
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

package jMeme.problems.Rosenbrock;

import jMeme.core.fitnessFunction.FitnessEvaluationMethod;
import jMeme.core.individuals.Individual;
import jMeme.core.individuals.IndividualComponent;

public class RosenbrockFitness extends FitnessEvaluationMethod {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3760674520206346601L;

	@Override
	public double evaluate(Individual a) {

		
	
		 IndividualComponent[] decisionVariables  = a.getIndividualComponents();

		 int num=decisionVariables.length;
		 
		    double tmp = 0.0;
		    for (int var = 0; var < num-1; var++) {
		    	double one = (Double)decisionVariables[var].getAllele();
		    	double two = (Double)decisionVariables[var+1].getAllele();
		    	  
		    	tmp += ((100 * (two - one * one) * (two - one * one)) + ((one - 1.0) * (one - 1.0)));
		    }       
		    return tmp;
	}

	

	
	
}
