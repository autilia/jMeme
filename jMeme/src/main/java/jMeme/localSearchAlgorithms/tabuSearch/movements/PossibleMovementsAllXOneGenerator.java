/*
 * PossibleMovementsAllXOneGenerator.java 
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

package jMeme.localSearchAlgorithms.tabuSearch.movements;


import org.jgap.RandomGenerator;



import jMeme.core.individuals.Individual;
import jMeme.core.individuals.IndividualComponent;
import jMeme.design.optimizerParameters.TabuSearchParameters;


/**
 * This class implements a movement generator which generates a given number of movements randomly for aan only individual component of the solution.
 */
public class PossibleMovementsAllXOneGenerator implements IPossibleMovementsGenerator {
  

	private static final long serialVersionUID = -9095765558731593564L;
	
	/**
	 * Stores the number of movements to generate
	 */
	private int numMovs;
	
	/**
	 * Constructor
	 */
	public PossibleMovementsAllXOneGenerator() {
		numMovs=TabuSearchParameters.NUMMOVS_DEFAULT;
    }
	
	/**
	 * Constructor
	 * 
	 * @param num  the number of movements to generate
	 */
	public PossibleMovementsAllXOneGenerator(int num){
		numMovs=num;
	}

    
    public Movement[] getPosibleMovements(Individual solution) {
    	
    	RandomGenerator generator = solution.getConfiguration().getRandomGenerator();

		int numComps=solution.size();
		int numG=generator.nextInt(numComps);
		IndividualComponent c=(IndividualComponent)(solution.getIndividualComponents()[numG]);
		Object precValue=c.getAllele();
		
	    Movement[] movs = new Movement[numMovs];
		Object newValue=0;
		
        for (int i = 0; i < numMovs; i++) {
			
        	
			
			c.setToRandomValue(generator);
			
			newValue=c.getAllele();
						
		    c.setAllele(precValue);
			
			movs[i] = new Movement(i, numG,newValue, precValue,solution.getFitnessValue());
				
			}
		return movs;
    }

    /**
	 * @return string representation of the defined movement generator
	 */
    public String toString(){
    	
   	 String s="";
   	 s+="Movements Generator: " + this.getClass().getName();
   	 s+="Number of movements: " + this.numMovs;
   	 
   	 return s;
    }

}
