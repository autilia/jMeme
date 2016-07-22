/*
 * DoubleGenerateSuccessors.java 
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
import org.jgap.impl.DoubleGene;

import jMeme.core.individuals.Individual;


/**
 * This class implements a generator of neighborhood for real individuals.
 *
 */
public class DoubleGenerateSuccessors extends GenerateSuccessors{
	
	
	 /**
		 * Constant indicating the following strategy for generating the neighborhood: generate a number of neighbors with a coevolution mutation for a a given individual.  The number is given by the parameter "number of neighbors".
		 */
		public static final int generateRandomConvolutionSuccessors=16;
		  /**
			 * Constant indicating the following strategy for generating the neighborhood: generate a number of neighbors with a coevolution mutation only for an individual component (of the given individual) chosen randomly.  The number is given by the parameter "number of neighbors".
			 */
		public static final int generateCoevolutionSuccessorsXOneGene=32;
		
		
		
		
		/**
		 * Costructor
		 * 
		 * Creates an instance of the class that sets the generator of neighbors to GenerateSuccessors.generateRandomSuccessors.
		 */
	public DoubleGenerateSuccessors() {
		super();
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the generator of neighbors to the given generation strategy.
	 */
	public DoubleGenerateSuccessors(int m) {
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
		else if(method==this.generateRandomConvolutionSuccessors)
			m=generateRandomConvolutionSuccessors(n,s);
		else if(method==this.generateCoevolutionSuccessorsXOneGene)
			m=generateCoevolutionSuccessorsXOneGene(n,s);
		
		return m;
			
			
	}
	
	
	
	
	

protected Individual[] generateCoevolutionSuccessorsXOneGene(int n,Individual s){
	
	int numG=((Individual)s).getGenes().length;
	 RandomGenerator generator = ((Individual)s).getConfiguration().getRandomGenerator();

	DoubleGene gene=null;
	int indexGene;
	
	
	indexGene=generator.nextInt(numG);
	gene=(DoubleGene)(((Individual)s).getGenes()[indexGene]);
	
	
	
	double precValue=((Double)gene.getAllele()).doubleValue();
	
	Individual[] movs = new Individual[n];
		
	double half_range=0.5;
	
    for (int i =0; i < n; i++) {
		
    	
    	movs[i] = (Individual)((Individual)s).clone();
    	movs[i].resetFitness();
    	double  randomValue;
		do{			
		randomValue = ((double)(2*half_range * generator.nextDouble()) - half_range);
	
		   }while((precValue+randomValue)<gene.getLowerBound() || (precValue+randomValue)>gene.getUpperBound());
		
		movs[i].getGenes()[indexGene].setAllele((precValue+randomValue));
    	
		
		
		}
	return movs;
	
}





protected Individual[] generateRandomConvolutionSuccessors(int n,  Individual s){
	
	
	
	int numG=((Individual)s).getGenes().length;
	RandomGenerator generator =((Individual)s).getConfiguration().getRandomGenerator();

	double half_range=0.5;
	
	Individual[] movs = new Individual[n];
	double precValue;
	int indexGene=0;
	
    for (int i = 0; i < n; i++) {
		
    	movs[i] = (Individual)((Individual)s).partialClone();
		
		int num_changes=1;
		
    	for(int j=0;j<num_changes;j++){
    	
    		    indexGene=generator.nextInt(numG);
    			DoubleGene gene=(DoubleGene)(((Individual)s).getGenes()[indexGene]);
    			precValue=gene.doubleValue();
    			double  randomValue;
    			do{			
    			randomValue = ((double)(2*half_range * generator.nextDouble()) - half_range);
    			
    			   }while((precValue+randomValue)<gene.getLowerBound() || (precValue+randomValue)>gene.getUpperBound());
    			
    			movs[i].getGenes()[indexGene].setAllele((precValue+randomValue));
    	}
		}
	return movs;
	
}
	

		
	
     


}
