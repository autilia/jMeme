/*
 * GenerateSuccessors.java 
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

package jMeme.localSearchAlgorithms.hillClimbing;


import org.jgap.Gene;
import org.jgap.RandomGenerator;
import org.jgap.impl.IntegerGene;

import jMeme.core.individuals.Individual;


/**
 * This class implements the strategies to generate the neighborhood of a solution.
 */
public class GenerateSuccessors {
	
	/**
	 * Constant indicating the following strategy for generating the neighborhood: generate a number of random neighbors. The number is given by the parameter "number of neighbors".
	 */
	public static final int generateRandomSuccessors=0;
	/**
	 * Constant indicating the following strategy for generating the neighborhood: generate an only neighbor for each individual component. Therefore the number of neighbors is equal to the number of individual components.
	 */
	public static final int generateASuccessorXAllComponents=1;
	/**
	 * Constant indicating the following strategy for generating the neighborhood: generate a number of neighbors for an only individual chosen randomly.  The number is given by the parameter "number of neighbors".
	 */
    public static final int generateSuccessorsXOneRandomComponent=2;
    /**
	 * Constant indicating the following strategy for generating the neighborhood: generate a number of neighbors for a given individual.  The number is given by the parameter "number of neighbors".
	 */
    public static final int generateSuccessorsXOneComponent=4;
	
	
    /**
	 * Stores the strategy for generating neighbors
	 */
	protected int method;
	/**
	 * Stores the index of the individual component used to compute neighbors
	 */
	protected int indexGene;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the generator of neighbors to GenerateSuccessors.generateRandomSuccessors.
	 */
	public GenerateSuccessors(){
		
		method=this.generateRandomSuccessors;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the generator of neighbors to the given generation strategy.
	 */
    public GenerateSuccessors(int m){
		
		method=m;
	}
	
    /**
     * Allows to execute the specified strategy to generate the neighborhood of a given individual.
     * 
     * @param s  the individual whose it is necessary to generate a neighborhood
     * @param n  the size of the neighborhood to generate
     * @return   a set of individuals representing the neighborhood of the individual s
     */
	public  Individual[]  execute(Individual s, int n){
		
		Individual[] m=null;
		if(method==this.generateRandomSuccessors)
			m=generateRandomSuccessors(n,s);
		else if(method==this.generateASuccessorXAllComponents)
			m=generateASuccessorXAllGenes(s);
		else if (method==this.generateSuccessorsXOneRandomComponent)
			m=generateSuccessorsXOneRandomGene(n,s);
		else if (method==this.generateSuccessorsXOneComponent)
			m=generateSuccessorsXOneGene(n,indexGene,s);
	
		
		return m;
			
			
	}
	
	protected Individual[] generateRandomSuccessors(int n,  Individual s){
		int numG=((Individual)s).getGenes().length;
		RandomGenerator generator =((Individual)s).getConfiguration().getRandomGenerator();

		
		Individual[] movs = new Individual[n];
		
		
        for (int i = 0; i < n; i++) {
			
        	int indexGene=generator.nextInt(numG);
    				
				
				movs[i] = (Individual)((Individual)s).clone();
				movs[i].resetFitness();
				movs[i].getGenes()[indexGene].setToRandomValue(generator);
				
		
			
			}
		return movs;
		
	}
	
	
	protected Individual[] generateASuccessorXAllGenes(Individual s){
		int numG=((Individual)s).getGenes().length;
		RandomGenerator generator = ((Individual)s).getConfiguration().getRandomGenerator();

		
		Individual[] movs = new Individual[numG];
		int newValue=0, precValue;
		
        for (int i = 0; i < numG; i++) {
			
			
				movs[i] = (Individual)((Individual)s).clone();
				movs[i].resetFitness();
				movs[i].getGenes()[i].setToRandomValue(generator);
				
			
			
			}
		return movs;
		
	}
	
	

protected Individual[] generateSuccessorsXOneRandomGene(int n,Individual s){
		
		int numG=((Individual)s).getGenes().length;
		 RandomGenerator generator = ((Individual)s).getConfiguration().getRandomGenerator();

		Gene gene=null;
		int indexGene;
		
		
		indexGene=generator.nextInt(numG);
		gene=(Gene)(((Individual)s).getGenes()[indexGene]);
		
		
		
		
		Individual[] movs = new Individual[n];
			
		
        for (int i =0; i < n; i++) {
			
        	
    		movs[i] = (Individual)((Individual)s).clone();
        	movs[i].resetFitness();
			movs[i].getGenes()[indexGene].setToRandomValue(generator);
			
        	
			
			}
		return movs;
		
	}



protected Individual[] generateSuccessorsXOneGene(int n,int indexGene,Individual s){
	
	int numG=((Individual)s).getGenes().length;
	 RandomGenerator generator = ((Individual)s).getConfiguration().getRandomGenerator();

	Gene gene=(Gene)(((Individual)s).getGenes()[indexGene]);
	
	
	
	
	Individual[] movs = new Individual[n];
		
	
    for (int i =0; i < n; i++) {
		
    	
		movs[i] = (Individual)((Individual)s).clone();
    	movs[i].resetFitness();
		movs[i].getGenes()[indexGene].setToRandomValue(generator);
		
    	
		
		}
	return movs;
	
}




	
	


	public int getIndexGene() {
		return indexGene;
	}



	public void setIndexGene(int indexGene) {
		this.indexGene = indexGene;
	}



	public int getMethod() {
		return method;
	}



	public void setMethod(int method) {
		this.method = method;
	}

	
}
