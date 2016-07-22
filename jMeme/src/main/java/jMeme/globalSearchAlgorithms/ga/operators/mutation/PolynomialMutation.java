/*
 * PolynomialMutation.java 
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

package jMeme.globalSearchAlgorithms.ga.operators.mutation;


import java.util.List;
import java.util.Vector;

import org.jgap.BaseGeneticOperator;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.ICompositeGene;
import org.jgap.IGeneticOperatorConstraint;
import org.jgap.IUniversalRateCalculator;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.RandomGenerator;
import org.jgap.data.config.Configurable;
import org.jgap.impl.DefaultMutationRateCalculator;
import org.jgap.impl.DoubleGene;

/**
 * This class implements a polynomial mutation operator. For more details, see the following work:
 * Kalyanmoy Deb and Debayan Deb., "Analysing mutation schemes for real-parameter genetic algorithms," Int. J. Artif. Intell. Soft Comput. 4, 1 (February 2014), 1-28. 
 *
 */
public class PolynomialMutation extends BaseGeneticOperator implements Configurable{
	
	
	private static final long serialVersionUID = -6153747203048794329L;

	/**
     * Constant that defines a default index for mutation
     */
    public static final double ETA_M_DEFAULT_ = 20.0;

	/**
	 * Stores the distribution index for mutation
	 */
	private double distributionIndex=ETA_M_DEFAULT_;
	



	/**
	 * Stores a calculator for dynamically determining the mutation rate. If set to null the value of m_mutationRate will be used. 
	 */
	  IUniversalRateCalculator m_mutationRateCalc;

	  /**
	 * Stores a mutation configuration object
	 */
	MutationOperatorConfigurable m_config = new
	      MutationOperatorConfigurable();


	  /**
	   * Constructor 
	   * 
	   * 
	   * Allows to construct a new instance of this MutationOperator without a specified
	   * mutation rate, which results in dynamic mutation being turned on. This
	   * means that the mutation rate will be automatically determined by this
	   * operator based upon the number of genes present in the chromosomes.
	   * 
	   * @param a_conf the configuration to use
	   * @throws InvalidConfigurationException
	   */
	  public PolynomialMutation(final Configuration a_conf)
	      throws InvalidConfigurationException {
	    super(a_conf);
	    setMutationRateCalc(new DefaultMutationRateCalculator(a_conf));
	  }

	  /**
	   * Construct
	   * 
	   * Allows to construct a new instance of this MutationOperator with a specified
	   * mutation rate calculator, which results in dynamic mutation being turned
	   * on.
	   * 
	   * @param a_config the configuration to use
	   * @param a_mutationRateCalculator calculator for dynamic mutation rate computation
	   * @throws InvalidConfigurationException
	   *
	   */
	  public PolynomialMutation(final Configuration a_config,
	                          final IUniversalRateCalculator
	                          a_mutationRateCalculator)
	      throws InvalidConfigurationException {
	    super(a_config);
	    setMutationRateCalc(a_mutationRateCalculator);
	  }

	  /**
	   * Constructor
	   * 
	   * Allows to construct a new instance of this MutationOperator with the given
	   * mutation rate.
	   *
	   * @param a_config  the configuration to use
	   * @param a_desiredMutationRate  desired rate of mutation, expressed as
	   *                               the denominator of the 1 / X fraction. For example, 1000 would result
	   *                               in 1/1000 genes being mutated on average. A mutation rate of zero disables
	   *                               mutation entirely
	   * @throws InvalidConfigurationException
	   *
	   */
	  public PolynomialMutation(final Configuration a_config,
	                          final int a_desiredMutationRate)
	      throws InvalidConfigurationException {
	    super(a_config);
	    m_config.m_mutationRate = a_desiredMutationRate;
	    setMutationRateCalc(null);
	  }

	  /**
	   * Execute the operation
	   * 
	   * @param a_population  the population of chromosomes from the current evolution prior to exposure to any genetic operators.
	   * @param a_candidateChromosomes the pool of chromosomes that have been mutated
	   */
	  public void operate(final Population a_population,
	                      final List a_candidateChromosomes) {
	    if (a_population == null || a_candidateChromosomes == null) {
	      // Population or candidate chromosomes list empty:
	      // nothing to do.
	      // -----------------------------------------------
	      return;
	    }
	    if (m_config.m_mutationRate == 0 && m_mutationRateCalc == null) {
	      // If the mutation rate is set to zero and dynamic mutation rate is
	      // disabled, then we don't perform any mutation.
	      // ----------------------------------------------------------------
	      return;
	    }
	    // Determine the mutation rate. If dynamic rate is enabled, then
	    // calculate it using the IUniversalRateCalculator instance.
	    // Otherwise, go with the mutation rate set upon construction.
	    // -------------------------------------------------------------
	    boolean mutate = false;
	    RandomGenerator generator = getConfiguration().getRandomGenerator();
	    // It would be inefficient to create copies of each Chromosome just
	    // to decide whether to mutate them. Instead, we only make a copy
	    // once we've positively decided to perform a mutation.
	    // ----------------------------------------------------------------
	    int size = Math.min(getConfiguration().getPopulationSize(),
	                        a_population.size());
	    IGeneticOperatorConstraint constraint = getConfiguration().
	        getJGAPFactory().getGeneticOperatorConstraint();
	    for (int i = 0; i < size; i++) {
	      IChromosome chrom = a_population.getChromosome(i);
	      Gene[] genes = chrom.getGenes();
	      IChromosome copyOfChromosome = null;
	      // For each Chromosome in the population...
	      // ----------------------------------------
	      for (int j = 0; j < genes.length; j++) {
	        if (m_mutationRateCalc != null) {
	          // If it's a dynamic mutation rate then let the calculator decide
	          // whether the current gene should be mutated.
	          // --------------------------------------------------------------
	          mutate = m_mutationRateCalc.toBePermutated(chrom, j);
	        }
	        else {
	          // Non-dynamic, so just mutate based on the the current rate.
	          // In fact we use a rate of 1/m_mutationRate.
	          // ----------------------------------------------------------
	          mutate = (generator.nextInt(m_config.m_mutationRate) == 0);
	        }
	        if (mutate) {
	          // Verify that crossover allowed.
	          // ------------------------------
	          /**@todo move to base class, refactor*/
	          if (constraint != null) {
	            List v = new Vector();
	            v.add(chrom);
	            if (!constraint.isValid(a_population, v, this)) {
	              continue;
	            }
	          }
	          // Now that we want to actually modify the Chromosome,
	          // let's make a copy of it (if we haven't already) and
	          // add it to the candidate chromosomes so that it will
	          // be considered for natural selection during the next
	          // phase of evolution. Then we'll set the gene's value
	          // to a random value as the implementation of our
	          // "mutation" of the gene.
	          // ---------------------------------------------------
	          if (copyOfChromosome == null) {
	            // ...take a copy of it...
	            // -----------------------
	            copyOfChromosome = (IChromosome) chrom.clone();
	            // ...add it to the candidate pool...
	            // ----------------------------------
	            a_candidateChromosomes.add(copyOfChromosome);
	            // ...then mutate all its genes...
	            // -------------------------------
	            genes = copyOfChromosome.getGenes();
	          }
	          // Process all atomic elements in the gene. For a StringGene this
	          // would be as many elements as the string is long , for an
	          // IntegerGene, it is always one element.
	          // --------------------------------------------------------------
	          if (genes[j] instanceof ICompositeGene) {
	            ICompositeGene compositeGene = (ICompositeGene) genes[j];
	            for (int k = 0; k < compositeGene.size(); k++) {
	              mutateGene(compositeGene.geneAt(k), generator);
	            }
	          
	          }
	          /**@todo isValid for super genes*/
//	          else if (genes[j] instanceof org.jgap.supergenes.Supergene) {
//	            org.jgap.supergenes.Supergene superGene = (org.jgap.supergenes.Supergene) genes[j];
//	            for (int k = 0; k < superGene.size(); k++) {
//	              mutateSupergene(superGene.geneAt(k), generator);
//	            }
//	          }
	          else {
	            mutateGene(genes[j], generator);
	           // System.out.println("sono in mutation 2" );
	            
			 	 
	          }
	        }
	      }
	    }
	  }

	
	  private void mutateGene(final Gene a_gene, final RandomGenerator a_generator) {
	     applyPolynomialMutation(a_gene, a_generator);
	  }

	  
	  private void applyPolynomialMutation(Gene a_gene,final RandomGenerator a_generator){
		    
		  double xy, val, deltaq;
	             DoubleGene gen=(DoubleGene)a_gene;
                 double y      = gen.doubleValue();
                 double yl     = gen.getLowerBound();                
                 double yu     = gen.getUpperBound();
                 double delta1 = (y-yl)/(yu-yl);
                 double  delta2 = (yu-y)/(yu-yl);
                 double   rnd = a_generator.nextDouble();
                 double   mut_pow = 1.0/(distributionIndex+1.0);
                 if (rnd <= 0.5)
                          {
                                  xy     = 1.0-delta1;
                                  val    = 2.0*rnd+(1.0-2.0*rnd)*(Math.pow(xy,(distributionIndex+1.0)));
                                  deltaq =  java.lang.Math.pow(val,mut_pow) - 1.0;
                          }
                          else
                          {
                                  xy = 1.0-delta2;
                                  val = 2.0*(1.0-rnd)+2.0*(rnd-0.5)*(java.lang.Math.pow(xy,(distributionIndex+1.0)));
                                  deltaq = 1.0 - (java.lang.Math.pow(val,mut_pow));
                          }
                          y = y + deltaq*(yu-yl);
                          if (y<yl)
                                  y = yl;
                          if (y>yu)
                                  y = yu;
                      
             gen.setAllele(y);  
                  
          
	  }
	  
	  

	  public IUniversalRateCalculator getMutationRateCalc() {
	    return m_mutationRateCalc;
	  }

	
	  public void setMutationRateCalc(final IUniversalRateCalculator
	                                  a_mutationRateCalc) {
	    m_mutationRateCalc = a_mutationRateCalc;
	    if (m_mutationRateCalc != null) {
	      m_config.m_mutationRate = 0;
	    }
	  }

	
	  public void setMutationRate(int a_mutationRate) {
	    m_config.m_mutationRate = a_mutationRate;
	    setMutationRateCalc(null);
	  }

	  /**
	   * Compares this GeneticOperator against the specified object. The result is
	   * true if and the argument is an instance of this class and is equal wrt the
	   * data.
	   *
	   * @param a_other the object to compare against
	   * @return true: if the objects are the same, false otherwise
	   *
	   */
	  public boolean equals(final Object a_other) {
	    try {
	      return compareTo(a_other) == 0;
	    } catch (ClassCastException cex) {
	      return false;
	    }
	  }

	  /**
	   * Compares the given GeneticOperator to this GeneticOperator.
	   *
	   * @param a_other the instance against which to compare this instance
	   * @return a negative number if this instance is "less than" the given
	   * instance, zero if they are equal to each other, and a positive number if
	   * this is "greater than" the given instance
	   *
	   */
	  public int compareTo(Object a_other) {
	    if (a_other == null) {
	      return 1;
	    }
	    PolynomialMutation op = (PolynomialMutation) a_other;
	    if (m_mutationRateCalc == null) {
	      if (op.m_mutationRateCalc != null) {
	        return -1;
	      }
	    }
	    else {
	      if (op.m_mutationRateCalc == null) {
	        return 1;
	      }
	      else {
	      }
	    }
	    if (m_config.m_mutationRate != op.m_config.m_mutationRate) {
	      if (m_config.m_mutationRate > op.m_config.m_mutationRate) {
	        return 1;
	      }
	      else {
	        return -1;
	      }
	    }
	    // Everything is equal. Return zero.
	    // ---------------------------------
	    return 0;
	  }

	  public int getMutationRate() {
	    return m_config.m_mutationRate;
	  }

	  class MutationOperatorConfigurable
	      implements java.io.Serializable {
	    /**
	     * The current mutation rate used by this MutationOperator, expressed as
	     * the denominator in the 1 / X ratio. For example, X = 1000 would
	     * mean that, on average, 1 / 1000 genes would be mutated. A value of zero
	     * disables mutation entirely.
	     */
	    public int m_mutationRate;
	  }
	  
	  public double getDistributionIndex() {
			return distributionIndex;
		}
	 

		public void setDistributionIndex(double distributionIndex) {
			this.distributionIndex = distributionIndex;
		}
	}
