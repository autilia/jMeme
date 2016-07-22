/*
 * Individual.java 
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

package jMeme.core.individuals;

import java.util.List;
import java.util.Vector;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.IChromosomePool;
import org.jgap.ICloneHandler;
import org.jgap.IJGAPFactory;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;

import jMeme.core.JConfiguration;



/**
 * This class implements a generic individual representing a solution to the problem to solve.
 */
public class Individual extends org.jgap.Chromosome {

	
	private static final long serialVersionUID = 6960795654821165843L;
	/**
	* Stores application-specific data that is attached to this individual.
     */
	protected Object m_applicationData;
	  /**
	   * Holds multiobjective values. Useful for future developments
	   */
	protected List m_multiObjective;
	
	
	

	/**
	 * Stores the number of the evaluation in which the individual has been generated for the first time
	 */
	protected int numberOfEvaluationToBeGenerated;
		
	
	
	/**
	 * Constructs a new individual with default settings.
	 * @param c  the configuration to use
	 * @throws InvalidConfigurationException
	 */
	public Individual(JConfiguration c) throws InvalidConfigurationException{
		super(c);	
	}


	public Individual() throws InvalidConfigurationException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Constructs a new individual with a set of given individual components
	 * 
	 * @param c  the configuration to use
	 * @param sampleComponents  the individual components to use to build the individual
	 * @throws InvalidConfigurationException
	 */
	public Individual(JConfiguration geneticConfiguration,
			IndividualComponent[] sampleComponents) throws InvalidConfigurationException {
		super(geneticConfiguration,sampleComponents);
		
	}
	
	
	 /**
	   * Convenience method that returns a new individual instance with its
	   * component values randomized.
	   * 
	   * @param a_configuration the configuration to use
	   * @return randomly initialized individual
	   * @throws InvalidConfigurationException if the given Configuration
	   * instance is invalid
	   * @throws IllegalArgumentException if the given Configuration instance
	   * is null
	   *
	   */
	public static IChromosome randomInitialIndividual(
			JConfiguration a_configuration)
	throws InvalidConfigurationException {
		// Sanity check: make sure the given configuration isn't null.
		// -----------------------------------------------------------
		if (a_configuration == null) {
			throw new IllegalArgumentException(
					"Configuration instance must not be null");
		}
		// Lock the configuration settings so that they can't be changed
		// from now on.
		// -------------------------------------------------------------
		a_configuration.lockSettings();
		// First see if we can get a Chromosome instance from the pool.
		// If we can, we'll randomize its gene values (alleles) and then
		// return it.
		// -------------------------------------------------------------
		IChromosomePool pool = a_configuration.getChromosomePool();
		if (pool != null) {
			IChromosome randomChromosome = pool.acquireChromosome();
			if (randomChromosome != null) {
				Gene[] genes = randomChromosome.getGenes();
				RandomGenerator generator = a_configuration.getRandomGenerator();
				for (int i = 0; i < genes.length; i++) {
					genes[i].setToRandomValue(generator);
					/**@todo what about Gene's energy?*/
				}
				randomChromosome.setFitnessValueDirectly(FitnessFunction.
						NO_FITNESS_VALUE);
				return randomChromosome;
			}
		}
		// We weren't able to get a Chromosome from the pool, so we have to
		// construct a new instance and build it from scratch.
		// ------------------------------------------------------------------
		IChromosome sampleChromosome =
			a_configuration.getSampleChromosome();
		sampleChromosome.setFitnessValue(FitnessFunction.NO_FITNESS_VALUE);
		Gene[] sampleGenes = sampleChromosome.getGenes();
		IndividualComponent[] newGenes = new IndividualComponent[sampleGenes.length];
		RandomGenerator generator = a_configuration.getRandomGenerator();
		for (int i = 0; i < newGenes.length; i++) {
			// We use the newGene() method on each of the genes in the
			// sample Chromosome to generate our new Gene instances for
			// the Chromosome we're returning. This guarantees that the
			// new Genes are setup with all of the correct internal state
			// for the respective gene position they're going to inhabit.
			// -----------------------------------------------------------
			newGenes[i] = (IndividualComponent) sampleGenes[i].newGene();
			// Set the gene's value (allele) to a random value.
			// ------------------------------------------------
			newGenes[i].setToRandomValue(generator);
			/**@todo what about Gene's energy?*/
		}
		// Finally, construct the new chromosome with the new random
		// genes values and return it.
		// ---------------------------------------------------------
		return new Individual(a_configuration, newGenes);
	}
	
	
	/**
	   * Returns a copy of this individual. The returned instance can evolve
	   * independently of this instance.
	   * 	   *
	   * @return copy of this individual
	   * @throws IllegalStateException instead of CloneNotSupportedException
	   *
	   */
	public synchronized Individual clone() {
		// Before doing anything, make sure that a Configuration object
		// has been set on this Chromosome. If not, then throw an
		// IllegalStateException.
		// ------------------------------------------------------------
		if (getConfiguration() == null) {
			throw new IllegalStateException(
					"The active Configuration object must be set on this " +
			"Chromosome prior to invocation of the clone() method.");
		}
		IChromosome copy = null;
		// Now, first see if we can pull a Chromosome from the pool and just
		// set its gene values (alleles) appropriately.
		// ------------------------------------------------------------
		IChromosomePool pool = getConfiguration().getChromosomePool();
		if (pool != null) {
			copy = pool.acquireChromosome();
			if (copy != null) {
				Gene[] genes = copy.getGenes();
				for (int i = 0; i < size(); i++) {
					genes[i].setAllele(getGene(i).getAllele());
				}
			}
		}
		try {
			if (copy == null) {
				// We couldn't fetch a Chromosome from the pool, so we need to create
				// a new one. First we make a copy of each of the Genes. We explicity
				// use the Gene at each respective gene location (locus) to create the
				// new Gene that is to occupy that same locus in the new Chromosome.
				// -------------------------------------------------------------------
				int size = size();
				if (size > 0) {
					IndividualComponent[] copyOfGenes = new IndividualComponent[size];
					for (int i = 0; i < copyOfGenes.length; i++) {
						copyOfGenes[i] = (IndividualComponent)getGene(i).newGene();
						Object allele = getGene(i).getAllele();
						if (allele != null) {
							IJGAPFactory factory = getConfiguration().getJGAPFactory();
							if (factory != null) {
								ICloneHandler cloner = factory.
								getCloneHandlerFor(allele, allele.getClass());
								if (cloner != null) {
									try {
										allele = cloner.perform(allele, null, this);
									} catch (Exception ex) {
										throw new RuntimeException(ex);
									}
								}
							}
						}
						copyOfGenes[i].setAllele(allele);
					}
					// Now construct a new Chromosome with the copies of the genes and
					// return it. Also clone the IApplicationData object later on.
					// ---------------------------------------------------------------
					if (getClass() == Individual.class) {
						copy = new Individual((JConfiguration)getConfiguration(), copyOfGenes);
					}
					else {
						copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
						copy.setGenes(copyOfGenes);
					}
				}
				else {
					if (getClass() == Individual.class) {
						copy = new Individual((JConfiguration)getConfiguration());
					}
					else {
						copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
					}
				}
			}
			copy.setFitnessValue(m_fitnessValue);
			// Clone constraint checker.
			// -------------------------
			copy.setConstraintChecker(getConstraintChecker());
		} catch (InvalidConfigurationException iex) {
			throw new IllegalStateException(iex.getMessage());
		}
		
		
		((Individual)copy).setNumberOfEvaluationsToBeGenerated(this.getNumberOfEvaluationsToBeGenerated());
				
		// Also clone the IApplicationData object.
		// ---------------------------------------
		try {
			copy.setApplicationData(cloneObject(getApplicationData()));
		} catch (Exception ex) {
			throw new IllegalStateException(ex.getMessage());
		}
		// Clone multi-objective object if necessary and possible.
		// -------------------------------------------------------
		if (m_multiObjective != null) {
			if (getClass() == Individual.class) {
				try {
					( (Individual) copy).setMultiObjectives( (List) cloneObject(
							m_multiObjective));
				} catch (Exception ex) {
					throw new IllegalStateException(ex.getMessage());
				}
			}
		}
		return (Individual)copy;
	}
	
	
	
	/**
	   * Returns a copy of this individual except for fitness value and the application data that are set to default value. The returned instance can evolve
	   * independently of this instance.
	   * 	   
	   * @return a partial copy of this individual
	   * @throws IllegalStateException instead of CloneNotSupportedException
	   *
	   */
	public synchronized Individual partialClone() {
		// Before doing anything, make sure that a Configuration object
		// has been set on this Chromosome. If not, then throw an
		// IllegalStateException.
		// ------------------------------------------------------------
		if (getConfiguration() == null) {
			throw new IllegalStateException(
					"The active Configuration object must be set on this " +
			"Chromosome prior to invocation of the clone() method.");
		}
		IChromosome copy = null;
		// Now, first see if we can pull a Chromosome from the pool and just
		// set its gene values (alleles) appropriately.
		// ------------------------------------------------------------
		IChromosomePool pool = getConfiguration().getChromosomePool();
		if (pool != null) {
			copy = pool.acquireChromosome();
			if (copy != null) {
				Gene[] genes = copy.getGenes();
				for (int i = 0; i < size(); i++) {
					genes[i].setAllele(getGene(i).getAllele());
				}
			}
		}
		try {
			if (copy == null) {
				// We couldn't fetch a Chromosome from the pool, so we need to create
				// a new one. First we make a copy of each of the Genes. We explicity
				// use the Gene at each respective gene location (locus) to create the
				// new Gene that is to occupy that same locus in the new Chromosome.
				// -------------------------------------------------------------------
				int size = size();
				if (size > 0) {
					IndividualComponent[] copyOfGenes = new IndividualComponent[size];
					for (int i = 0; i < copyOfGenes.length; i++) {
						copyOfGenes[i] = (IndividualComponent)getGene(i).newGene();
						Object allele = getGene(i).getAllele();
						if (allele != null) {
							IJGAPFactory factory = getConfiguration().getJGAPFactory();
							if (factory != null) {
								ICloneHandler cloner = factory.
								getCloneHandlerFor(allele, allele.getClass());
								if (cloner != null) {
									try {
										allele = cloner.perform(allele, null, this);
									} catch (Exception ex) {
										throw new RuntimeException(ex);
									}
								}
							}
						}
						copyOfGenes[i].setAllele(allele);
					}
					// Now construct a new Chromosome with the copies of the genes and
					// return it. Also clone the IApplicationData object later on.
					// ---------------------------------------------------------------
					if (getClass() == Individual.class) {
						copy = new Individual((JConfiguration)getConfiguration(), copyOfGenes);
					}
					else {
						copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
						copy.setGenes(copyOfGenes);
					}
				}
				else {
					if (getClass() == Individual.class) {
						copy = new Individual((JConfiguration)getConfiguration());
					}
					else {
						copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
					}
				}
			}
			copy.setFitnessValue(FitnessFunction.NO_FITNESS_VALUE);
			// Clone constraint checker.
			// -------------------------
			copy.setConstraintChecker(getConstraintChecker());
		} catch (InvalidConfigurationException iex) {
			throw new IllegalStateException(iex.getMessage());
		}
		
		
		((Individual)copy).setNumberOfEvaluationsToBeGenerated(this.getNumberOfEvaluationsToBeGenerated());
				
		
		// Clone multi-objective object if necessary and possible.
		// -------------------------------------------------------
		if (m_multiObjective != null) {
			if (getClass() == Individual.class) {
				try {
					( (Individual) copy).setMultiObjectives( (List) cloneObject(
							m_multiObjective));
				} catch (Exception ex) {
					throw new IllegalStateException(ex.getMessage());
				}
			}
		}
		return (Individual)copy;
	}
	
	/**{@inheritDoc}*/
	public boolean isHandlerFor(Object a_obj, Class a_class) {
		if (a_class == Individual.class) {
			return true;
		}
		else {
			return false;
		}
	}

	/**{@inheritDoc}*/
	public Object perform(Object a_obj, Class a_class, Object a_params)
	throws Exception {
		return randomInitialIndividual((JConfiguration)getConfiguration());
	}
	
	
	
	
	
	/**
	 * Evaluates the importance of a solution
	 * 
	 * @return  a double indicating the fitness value
	 */
	public double evaluate() {

		return this.getFitnessValue();
	}
	
	
	/**
	 * Generates an individual with random values
	 * 
	 * @return an individual randomly generated
	 */
	public Individual generateRandomSolution() {
		Individual s=null;
		try {
			s = (Individual)randomInitialIndividual((JConfiguration)this.getConfiguration());
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	

	
	  /**
	   * Compares the given individual to this individual. This individual is
	   * considered to be "less than" the given individual if it has a lower fitness value
	   * with respect to the other individual.
	   *
	   * @param  s the individual against which to compare this individual
	   * @return a negative number if this individual is "less than" the given
	   * individual, zero if they are equal to each other, and a positive number if
	   * this individual is "greater than" the given individual
	   */
	public int compareTo(Individual s) {

		double comparation = this.getFitnessValue() - s.getFitnessValue();
		if (comparation < 0) {
			return -1;
		}
		if (comparation > 0) {
			return 1;
		}

		return 0;
	}


	
	
	
	


	public double absoluteImprovement(Individual s) {
		return s.getFitnessValue() - this.getFitnessValue();
	}
	
	
	public void resetFitness(){
		this.setFitnessValueDirectly(-1);
		
	}
	
	public void setFitness(double value){
		this.setFitnessValueDirectly(value);
	}
	


		
	
	public int getNumberOfEvaluationsToBeGenerated() {
		return numberOfEvaluationToBeGenerated;
	}
	


	public void setNumberOfEvaluationsToBeGenerated(int numberOfEvaluationGeneration) {
		this.numberOfEvaluationToBeGenerated = numberOfEvaluationGeneration;
	}


	
	
	/**
	 * Constructs a new individual with a set of given real values
	 * 
	 * @param c  the configuration to use
	 * @param values  the real values to use to build the individual
	 * @throws InvalidConfigurationException
	 */
	public Individual(JConfiguration geneticConfiguration,
			double[] values) throws InvalidConfigurationException {
		super(geneticConfiguration);
		
		IChromosome sampleChromosome =
				geneticConfiguration.getSampleChromosome();
			sampleChromosome.setFitnessValue(FitnessFunction.NO_FITNESS_VALUE);
			Gene[] sampleGenes = sampleChromosome.getGenes();
			IndividualComponent[] newGenes = new IndividualComponent[sampleGenes.length];
			
			
			for (int i = 0; i < newGenes.length; i++) {
				// We use the newGene() method on each of the genes in the
				// sample Chromosome to generate our new Gene instances for
				// the Chromosome we're returning. This guarantees that the
				// new Genes are setup with all of the correct internal state
				// for the respective gene position they're going to inhabit.
				// -----------------------------------------------------------
				newGenes[i] = (IndividualComponent)sampleGenes[i].newGene();
				// Set the gene's value (allele) to a random value.
				// ------------------------------------------------
				newGenes[i].setAllele(values[i]);
		     
			}
			
			setGenes(newGenes);			
	}


	// ----------------------------------
	  // End of IInitializer implementation
	  // ----------------------------------
	  public void setMultiObjectives(List a_values) {
	    if (m_multiObjective == null) {
	      m_multiObjective = new Vector();
	    }
	    m_multiObjective.clear();
	    m_multiObjective.addAll(a_values);
	  }

	  public List getMultiObjectives() {
	    return m_multiObjective;
	  }
	
	  
	public int getNumberOfEvaluationToBeGenerated() {
		return numberOfEvaluationToBeGenerated;
	}


	public void setNumberOfEvaluationToBeGenerated(int numberOfEvaluationToBeGenerated) {
		this.numberOfEvaluationToBeGenerated = numberOfEvaluationToBeGenerated;
	}


	public IndividualComponent[] getIndividualComponents(){
		return (IndividualComponent[]) this.getGenes();
	}
	
	public IndividualComponent getIndividualComponent(int index){
		return (IndividualComponent)this.getGene(index);
	}
	
	
	
	@Deprecated
	public Gene getGene(int a){
		return super.getGene(a);
	}
	
	@Deprecated
	public Gene[] getGenes(){
		return super.getGenes();
	}
}
