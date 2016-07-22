/*
 * PossibleMovementsOneXAllGenerator.java 
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



/**
 * This class implements a movement generator which generates a random movement for each individual component of the solution.
 */
public class PossibleMovementsOneXAllGenerator implements IPossibleMovementsGenerator {
  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2853227033670889927L;


	/**
	 * Constructor
	 */
	public PossibleMovementsOneXAllGenerator() {
		
    }
	


  
    public Movement[] getPosibleMovements(Individual solution) {
    	
    	RandomGenerator generator = solution.getConfiguration().getRandomGenerator();

    	int numG=solution.getIndividualComponents().length;
		
		
	    Movement[] movs = new Movement[numG];
		Object newValue=0, precValue;
		
        for (int i = 0; i < numG; i++) {
			
			IndividualComponent c=(IndividualComponent)(solution.getIndividualComponent(i));
			precValue=c.getAllele();
			
			c.setToRandomValue(generator);
			
			newValue=c.getAllele();
						
			c.setAllele(precValue);
			
			movs[i] = new Movement(i, i,newValue, precValue,solution.getFitnessValue());
				
			}
		return movs;
    }



    /**
	 * @return string representation of the defined movement generator
	 */
    public String toString(){
    	
      	 String s="";
      	 s+="Movements Generator: " + this.getClass().getName();
      	 s+="Number of movements: individual size";
      	 
      	 return s;
       }
}
