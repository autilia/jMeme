/*
 * BLXalpha.java 
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
import org.jgap.Population;
import org.jgap.RandomGenerator;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DoubleGene;

/** This class represents a BLX-alpha crossover operator. 
 *  For more detail about the BLX-alpha crossover see the work:
 *  Herrera, Francisco, Manuel Lozano, and Ana M. Sánchez. "A taxonomy for the crossover operator for real‐coded genetic algorithms: An experimental study." 
 *  International Journal of Intelligent Systems 18.3 (2003): 309-338.
 */

public class BLXalpha extends CrossoverOperator implements Comparable{
	
		/**
		 * This field represents the crossover usage rate. This essentially is the probability with which a child is created through crossover. If the child does is not created through crossover (by falling outside this probability), it is just a copy of one of the two parents (randomly selected) that would have been used in the crossover.
		 */
		private double crossoverUsageRate;
		/**
		 * This field represents alpha value used for BLX crossover.
		 */
		private double blxAlpha;

		/**
		 * Stores the lower index of individual component that undergoes the crossover
		 */
		private int lowIndex;
		
		/**
		 * Stores the greater index of individual component that undergoes the crossover
		 */
		private int highIndex;

		/** Constructor
		 * 
		 * @param a_configuration  the configuration of the algorithm
		 * @param xoverUsageRate  the crossover usage rate
		 * @param blxAlpha  the alpha value
		 */
		public BLXalpha(final Configuration a_configuration,double xoverUsageRate, double blxAlpha) throws InvalidConfigurationException{
		   	this(a_configuration,xoverUsageRate, blxAlpha, 0, Integer.MAX_VALUE);
		}

		/** Constructor
		 * 
		 * @param a_configuration  the configuration of the algorithm
		 * @param xoverUsageRate  the crossover usage rate
		 * @param blxAlpha  the alpha value
		 * @param lowIndex  the lower index of the individual that undergoes the crossover
		 * @param highIndex  the greater index of the individual that undergoes the crossover
		 */
		public BLXalpha(final Configuration a_configuration,double xoverUsageRate, double blxAlpha, int lowIndex, int highIndex) throws InvalidConfigurationException {
			super(a_configuration);
			crossoverUsageRate = xoverUsageRate;
			this.blxAlpha = blxAlpha;
			this.lowIndex = Math.max(0, lowIndex);
			this.highIndex = Math.max(highIndex, this.lowIndex);
		}
		
		
		/**Allows to execute the blx crossover on two individuals 
		 * 
		 * @param firstMate  the first selected individual to perform the crossover
		 * @param secondMate  the second selected individual to perform the crossover
		 * @param a_candidateChromosomes  the population of the new individuals
		 * @param generator  a generator of random number
		 */
		  public void doCrossover(IChromosome firstMate, IChromosome secondMate,
		          List a_candidateChromosomes,
		          RandomGenerator generator) {
		    
			    
			    IChromosome firstMate2 = (IChromosome) firstMate.clone();
			    IChromosome secondMate2 = (IChromosome) secondMate.clone();
			    
			      
			      Gene[] offs1 =  firstMate.getGenes();
			      Gene[] offs2 =  secondMate.getGenes();
			      Gene[] x1 =  firstMate2.getGenes();
			      Gene[] x2 =  secondMate2.getGenes();
			      
			      
					int numDimensions = offs1.length;
					double mom;
					double dad;
					double min;
					double max;
					double delta;
					double value1;
					double value2;
					
						int lowSlice = lowIndex;
						int highSlice = Math.min(numDimensions - 1, highIndex);
							for(int j = lowSlice; j <= highSlice; j++) {
									mom = (Double)offs1[j].getAllele();
									dad = (Double)offs2[j].getAllele();
									min = Math.min(mom, dad);
									max = Math.max(mom, dad);
									delta = blxAlpha * (max - min);
									value1 = min - delta + Math.random() * (max - min + 2.0f * delta); 
									value1 = Math.max(((DoubleGene)x1[j]).getLowerBound(), Math.min(((DoubleGene)x1[j]).getUpperBound(), value1));
									//System.out.println(value);
									((DoubleGene)x1[j]).setAllele(new Double(value1));
								
									value2 = min - delta + Math.random() * (max - min + 2.0f * delta); 
									value2 = Math.max(((DoubleGene)x2[j]).getLowerBound(), Math.min(((DoubleGene)x2[j]).getUpperBound(), value2));
									//System.out.println(value);
									((DoubleGene)x2[j]).setAllele(new Double(value2));
							
							}
								
					
		    
		      a_candidateChromosomes.add(firstMate2);
		      a_candidateChromosomes.add(secondMate2);
		    //  System.out.println("crossover ");
		     // System.out.println(firstMate);
		     // System.out.println(secondMate);
		      //System.out.println(firstMate2);
		                                                                                            
		  } // doCrossover
		  
		  
		  /**
		  * Executes the operation
		  * 
		  * @param a_population  the population from which individuals must be selected
		  * @param a_candidateChromosomes  list where the new individuals must be inserted
		  */
		  public void operate(final Population a_population,
		          final List a_candidateChromosomes) {
			  RandomGenerator generator = getConfiguration().getRandomGenerator();
			// Work out the number of crossovers that should be performed.
			    // -----------------------------------------------------------
			    int size = Math.min(getConfiguration().getPopulationSize(),
			                        a_population.size());
			    int numCrossovers = (int) (size * crossoverUsageRate);
			    
		    
			  int index1, index2;
			  for (int i = 0; i < numCrossovers; i++) {
			  index1 = generator.nextInt(size);
		      index2 = generator.nextInt(size);
		      IChromosome chrom1 = a_population.getChromosome(index1);
		      IChromosome chrom2 = a_population.getChromosome(index2);
		         
		      IChromosome firstMate = (IChromosome) chrom1.clone();
		      IChromosome secondMate = (IChromosome) chrom2.clone();
		     
		    doCrossover(firstMate, secondMate, a_candidateChromosomes, generator);
			  }
		        
		  
		  } // execute 
		

}
