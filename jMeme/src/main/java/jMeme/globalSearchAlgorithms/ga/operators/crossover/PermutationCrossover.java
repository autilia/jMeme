/*
 * PermutationCrossover.java 
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

package jMeme.globalSearchAlgorithms.ga.operators.crossover;

import java.util.List;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.impl.CrossoverOperator;


/**
 * This class implements the permutation crossover. This crossover works as follows:
 * one crossover point is selected, till this point the permutation is copied from the first parent,
 * then the second parent is scanned and if the number is not yet in the offspring it is added.
 * The same procedure is applied for the second offspring by considering reversed roles for parents. 
 */
public class PermutationCrossover extends CrossoverOperator  {

	private static final long serialVersionUID = 6010981398631089743L;



	/**
 	* Constructor 
 	* 
 	* @param a_configuration  the configuration to be used
 	* @throws InvalidConfigurationException
 	*/
	public PermutationCrossover(final Configuration a_configuration)
		      throws InvalidConfigurationException {
		    super(a_configuration);
    }
	
	/**
 	* Constructor 
 	* 
 	* @param a_configuration  the configuration to be used
 	* @param a_crossoverRatePercentage  the crossover probability
 	* 
 	* @throws InvalidConfigurationException
 	*/
	 public PermutationCrossover(final Configuration a_configuration, final double a_crossoverRatePercentage)
                                                                 throws InvalidConfigurationException {
         super(a_configuration, a_crossoverRatePercentage);
     }
	  
	  
	  
   /**Allows to execute the blx crossover on two individuals 
	* 
	* @param firstMate  the first selected individual to perform the crossover
	* @param secondMate  the second selected individual to perform the crossover
	* @param a_candidateChromosomes  the population of the new individuals
	* @param generator  a generator of random number
	*/
	protected void doCrossover(IChromosome firstMate, IChromosome secondMate,
              List a_candidateChromosomes,
              RandomGenerator generator) {
		  
		     
		  
Gene[] firstGenes = firstMate.getGenes();
Gene[] firstGenesClone=firstMate.getGenes().clone();
Gene[] secondGenes = secondMate.getGenes();
int locus = generator.nextInt(firstGenes.length-1)+1;


System.out.println("locus " + locus);

int index1=locus;
for (int i = 0; i < secondGenes.length; i++)
{
    boolean isFind = false;
    for (int j = 0; j < locus; j++)
    {
        if (secondGenes[i].compareTo(firstGenes[j])==0)
        {
            isFind = true;
        }
    }

    if (!isFind){
    	firstGenes[index1]= secondGenes[i].newGene();
    	firstGenes[index1].setAllele(secondGenes[i].getAllele());
        index1++;   
    }
}

int index2=locus;
for (int k = 0; k < firstGenesClone.length; k++)
{
    boolean isFind = false;
    for (int j = 0; j < locus; j++)
    {
        if (firstGenesClone[k].compareTo(secondGenes[j])==0)
        {
            isFind = true;
        }
    }

    if (!isFind){
    	 secondGenes[index2]= firstGenesClone[k].newGene();
    	 secondGenes[index2].setAllele(firstGenesClone[k].getAllele());
        index2++;   
    }
}


// Add the modified chromosomes to the candidate pool so that
// they'll be considered for natural selection during the next
// phase of evolution.
// -----------------------------------------------------------
a_candidateChromosomes.add(firstMate);
a_candidateChromosomes.add(secondMate);
}
	
}
