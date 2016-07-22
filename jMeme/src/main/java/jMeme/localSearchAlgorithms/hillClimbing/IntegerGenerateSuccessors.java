/*
 * IntegerGenerateSuccessors.java 
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


import org.jgap.RandomGenerator;
import org.jgap.impl.IntegerGene;

import jMeme.core.individuals.Individual;


/**
 * This class implements a generator of neighborhood for integer individuals.
 *
 */
public class IntegerGenerateSuccessors extends GenerateSuccessors{
	
	/**
	 * Constant indicating the following strategy for generating the neighborhood: generate all successors for an individual component chosen randomly. Therefore the number of neighbors is equal to the number of individual components.
	 */
	public static  final int generateAllSuccessorsXOneComponent=8;
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the generator of neighbors to GenerateSuccessors.generateRandomSuccessors.
	 */
	public IntegerGenerateSuccessors(){
		super();
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the generator of neighbors to the given generation strategy.
	 */
    public IntegerGenerateSuccessors(int m){
		super(m);
	}
	
    @Override
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
		else m=generateAllSuccessorsXOneGene(s);
		
		return m;
			
			
	}
	

	
	protected Individual[] generateAllSuccessorsXOneGene(Individual s){
		
		int numG=((Individual)s).getGenes().length;
		 RandomGenerator generator = ((Individual)s).getConfiguration().getRandomGenerator();

		IntegerGene gene=null;
		int indexGene;
		
	
		indexGene=generator.nextInt(numG);
		gene=(IntegerGene)(((Individual)s).getGenes()[indexGene]);
		
	
		
		int numS=gene.getUpperBounds()-gene.getLowerBounds();
		
		int precValue=((Integer)gene.getAllele()).intValue();
		
		
		
		Individual[] movs = new Individual[numS];
			
		
        for (int i = gene.getLowerBounds(),j=0; i <= gene.getUpperBounds(); i++) {
			
        	if(i!=precValue){
        	movs[j] = (Individual)((Individual)s).clone();
        	movs[j].resetFitness();
			movs[j].getGenes()[indexGene].setAllele(i);
			j++;
        	}
			
			
			}
		return movs;
		
	}

	

}
