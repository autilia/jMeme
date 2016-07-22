/*
 * SBXCrossover.java 
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



/**
 * This class allows to implements a SBX crossover operator using two individuals.
 *  For more detail about the SBX crossover see the work:
 *  Herrera, Francisco, Manuel Lozano, and Ana M. Sánchez. "A taxonomy for the crossover operator for real‐coded genetic algorithms: An experimental study." 
 *  International Journal of Intelligent Systems 18.3 (2003): 309-338.
 */
public class SBXCrossover extends CrossoverOperator implements Comparable {
	
  /**
   * Constant that defines the minimum difference allowed between real values
   */
  protected static final double EPS= 1.0e-14;
                                      
  /**
   * Constant indicates the default value for the distribution index
   */
  public static final double ETA_C_DEFAULT_ = 20.0;  
  
  /**
 * Stores the probability to perform the crossover
 */
private Double crossoverProbability_ = null;
  /**
 * Stores the distribution index
 */
private double distributionIndex_ = ETA_C_DEFAULT_;

  
  /** 
   * Constructor
   * 
   * @param a_configuration  the configuration of the algorithm
   * @param prob  the crossover probability to be applied
   * @param distributionIn  the distribution index
   */
  public SBXCrossover(final Configuration a_configuration,double prob, double distributionIn)  throws InvalidConfigurationException {
	  super(a_configuration);
	  crossoverProbability_ = prob ;  		
  	distributionIndex_    = distributionIn ;  		
  } // SBXCrossover
    
  /**
   * Perform the crossover operation. 
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
		
		    
    int i;
    double rand;
    double y1, y2, yL, yu;
    double c1, c2;
    double alpha, beta, betaq;
    double valueX1,valueX2;
	
			
		int numberOfVariables = offs1.length;

    
      for (i=0; i<numberOfVariables; i++){
        valueX1 = (Double)x1[i].getAllele();
        valueX2 = (Double)x2[i].getAllele();
        if (generator.nextDouble()<=0.5 ){
          if (java.lang.Math.abs(valueX1- valueX2) > EPS){
            
            if (valueX1 < valueX2){
              y1 = valueX1;
              y2 = valueX2;
            } else {
              y1 = valueX2;
              y2 = valueX1;
            } // if                       
            
            yL = ((DoubleGene)x1[i]).getLowerBound();
            yu = ((DoubleGene)x1[i]).getUpperBound();
            rand = generator.nextDouble();
            beta = 1.0 + (2.0*(y1-yL)/(y2-y1));
            alpha = 2.0 - java.lang.Math.pow(beta,-(distributionIndex_+1.0));
            
            if (rand <= (1.0/alpha)){
              betaq = java.lang.Math.pow ((rand*alpha),(1.0/(distributionIndex_+1.0)));
            } else {
              betaq = java.lang.Math.pow ((1.0/(2.0 - rand*alpha)),(1.0/(distributionIndex_+1.0)));
            } // if
            
            c1 = 0.5*((y1+y2)-betaq*(y2-y1));
            beta = 1.0 + (2.0*(yu-y2)/(y2-y1));
            alpha = 2.0 - java.lang.Math.pow(beta,-(distributionIndex_+1.0));
            
            if (rand <= (1.0/alpha)){
              betaq = java.lang.Math.pow ((rand*alpha),(1.0/(distributionIndex_+1.0)));
            } else {
              betaq = java.lang.Math.pow ((1.0/(2.0 - rand*alpha)),(1.0/(distributionIndex_+1.0)));
            } // if
              
            c2 = 0.5*((y1+y2)+betaq*(y2-y1));
            
            if (c1<yL)
              c1=yL;
            
            if (c2<yL)
              c2=yL;
            
            if (c1>yu)
              c1=yu;
            
            if (c2>yu)
              c2=yu;                        
              
            if (generator.nextDouble()<=0.5) {
              offs1[i].setAllele(c2) ;
              offs2[i].setAllele(c1) ;
            } else {
            	offs1[i].setAllele(c1) ;
                offs2[i].setAllele(c2) ;
            } // if
          } else {
            offs1[i].setAllele(valueX1) ;
            offs2[i].setAllele(valueX2) ;
          } // if
        } else {
        	 offs1[i].setAllele(valueX2) ;
             offs2[i].setAllele(valueX1) ;
        } // if
      } // if
    
      a_candidateChromosomes.add(firstMate);
      a_candidateChromosomes.add(secondMate);
                                                                                            
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
	    int numCrossovers = (int) (size * crossoverProbability_);
	    
	   
	    
		     
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
} // SBXCrossover
