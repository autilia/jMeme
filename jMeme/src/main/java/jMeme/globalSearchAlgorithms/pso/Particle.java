/*
 * Particle.java 
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
package jMeme.globalSearchAlgorithms.pso;


import java.util.List;

import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.IChromosomePool;
import org.jgap.ICloneHandler;
import org.jgap.IJGAPFactory;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;

import jMeme.core.JConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.core.individuals.IndividualComponent;

/**
 * This class implements a generic particle representing a solution to the problem to solve with a particle swarm optimization algorithm.
 */
public class Particle extends Individual{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2113118046350016042L;
	/**
	 * Stores the best fitness value
	 */
	private double bestFitness;
	/**
	 * Stores the best particles's position so far
	 */
	private DoubleParticleComponent bestPosition[];
	/**
	 * Stores the velocity values for the particle
	 */
	private double velocity[];
	
	
	/**
	 * Constructor 
	 */
	public Particle() throws InvalidConfigurationException{
		
		super();
	}

	/**
	 * Constructs a new particle with default settings.
	 * @param c  the configuration to use
	 * @throws InvalidConfigurationException
	 */
	public Particle(JConfiguration c) throws InvalidConfigurationException{
		super(c);	
	}

	/**
	 * Constructs a new particle with a set of given particle components
	 * 
	 * @param c  the configuration to use
	 * @param sampleComponents  the particle components to use to build the particle
	 * @throws InvalidConfigurationException
	 */
	public Particle(JConfiguration geneticConfiguration,
			IndividualComponent[] sampleComponents) throws InvalidConfigurationException {
		super(geneticConfiguration,sampleComponents);

	}
	
	
	 /**
	   * Convenience method that returns a new particle instance with its
	   * component values randomized.
	   * 
	   * @param a_configuration the configuration to use
	   * @return randomly initialized particle
	   * @throws InvalidConfigurationException if the given Configuration
	   * instance is invalid
	   * @throws IllegalArgumentException if the given Configuration instance
	   * is null
	   *
	   */
	public static IChromosome randomInitialIndividual(
			Configuration a_configuration)
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
			newGenes[i] = (IndividualComponent)sampleGenes[i].newGene();
			// Set the gene's value (allele) to a random value.
			// ------------------------------------------------
			newGenes[i].setToRandomValue(generator);
			
            //set velocity
			((DoubleParticleComponent)newGenes[i]).setToRandomVelocity(generator);
		}
		// Finally, construct the new chromosome with the new random
		// genes values and return it.
		// ---------------------------------------------------------
		
		//particle components
		Particle p=new Particle((JConfiguration)a_configuration, newGenes);
		p.bestFitness = Double.NaN;
		p.setBestPosition(null);
		
		
		return p;
	}
	
	
	/**
	   * Returns a copy of this particle. The returned instance can evolve
	   * independently of this instance.
	   * 	   *
	   * @return copy of this particle
	   * @throws IllegalStateException instead of CloneNotSupportedException
	   *
	   */
	public synchronized Particle clone() {
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
						copyOfGenes[i] = (IndividualComponent) getGene(i).newGene();
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
						//setto velocity
						((DoubleParticleComponent)copyOfGenes[i]).setVelocity(((DoubleParticleComponent)getGene(i)).getVelocity());
						
					}
					// Now construct a new Chromosome with the copies of the genes and
					// return it. Also clone the IApplicationData object later on.
					// ---------------------------------------------------------------
					if (getClass() == Particle.class) {
						copy = new Particle((JConfiguration)getConfiguration(), copyOfGenes);
					}
					else {
						copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
						copy.setGenes(copyOfGenes);
					}
				}
				else {
					if (getClass() == Particle.class) {
						copy = new Particle((JConfiguration)getConfiguration());
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
		
		((Particle)copy).setNumberOfEvaluationsToBeGenerated(this.getNumberOfEvaluationsToBeGenerated());
				
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
			if (getClass() == Particle.class) {
				try {
					( (Particle) copy).setMultiObjectives( (List) cloneObject(
							m_multiObjective));
				} catch (Exception ex) {
					throw new IllegalStateException(ex.getMessage());
				}
			}
		}
		
		((Particle)copy).setBestFitness(this.getBestFitness());
		((Particle)copy).setBestPosition(copyPosition(this.getBestPosition()));
		
		return (Particle)copy;
	}
	
	
	/**
	   * Returns a copy of this partical except for fitness value and the application data that are set to default value. The returned instance can evolve
	   * independently of this instance.
	   * 	   
	   * @return a partial copy of this particle
	   * @throws IllegalStateException instead of CloneNotSupportedException
	   *
	   */
	public synchronized Particle partialClone() {
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
						//setto velocity
						((DoubleParticleComponent)copyOfGenes[i]).setVelocity(((DoubleParticleComponent)getGene(i)).getVelocity());
					
					}
					// Now construct a new Chromosome with the copies of the genes and
					// return it. Also clone the IApplicationData object later on.
					// ---------------------------------------------------------------
					if (getClass() == Particle.class) {
						copy = new Particle((JConfiguration)getConfiguration(), copyOfGenes);
					}
					else {
						copy = (IChromosome) getConfiguration().getSampleChromosome().clone();
						copy.setGenes(copyOfGenes);
					}
				}
				else {
					if (getClass() == Particle.class) {
						copy = new Particle((JConfiguration)getConfiguration());
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
		

		((Particle)copy).setNumberOfEvaluationsToBeGenerated(this.getNumberOfEvaluationsToBeGenerated());
				
		
		// Clone multi-objective object if necessary and possible.
		// -------------------------------------------------------
		if (m_multiObjective != null) {
			if (getClass() == Particle.class) {
				try {
					( (Particle) copy).setMultiObjectives( (List) cloneObject(
							m_multiObjective));
				} catch (Exception ex) {
					throw new IllegalStateException(ex.getMessage());
				}
			}
		}
		
		((Particle)copy).setBestFitness(this.getBestFitness());
		((Particle)copy).setBestPosition(copyPosition(this.getBestPosition()));
		
	
		return (Particle)copy;
	}
	
	/**{@inheritDoc}*/
	public boolean isHandlerFor(Object a_obj, Class a_class) {
		if (a_class == Particle.class) {
			return true;
		}
		else {
			return false;
		}
	}

	/**{@inheritDoc}*/
	public Object perform(Object a_obj, Class a_class, Object a_params)
	throws Exception {
		return randomInitialIndividual(getConfiguration());
	}
	
	
	
	


	/** Copy position[] to bestPosition[] */
	private DoubleParticleComponent[] copyPosition(Gene[] d) {
		int size=d.length;
		DoubleParticleComponent[] bestPosition=new DoubleParticleComponent[size];
        for(int i=0;i<size;i++){
			try {
				bestPosition[i]=new DoubleParticleComponent((JConfiguration)d[i].getConfiguration(), ((DoubleParticleComponent)d[i]).getLowerBound(),((DoubleParticleComponent)this.getGenes()[i]).getUpperBound(),
				((DoubleParticleComponent)d[i]).getMinVelocity(),((DoubleParticleComponent)this.getGenes()[i]).getMaxVelocity());
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bestPosition[i].setAllele(((Double)d[i].getAllele()).doubleValue());
			bestPosition[i].setVelocity(((DoubleParticleComponent)d[i]).getVelocity());
        }
        return bestPosition;
	}

	
	

	

	/**
	 * Set best fitness.
	 * If it's the best fitness so far, copy data to bestFitness[]
	 * 
	 * @param fitness the new fitness value
	 * @param maximize true if the problem at issue is to maxime, false, otherwise
	 */
	public void setBestFitness(double fitness, boolean maximize) {
		if( (maximize && (fitness > bestFitness)) // Maximize and bigger? => store data
				|| (!maximize && (fitness < bestFitness)) // Minimize and smaller? => store data too
				|| Double.isNaN(bestFitness) ) {
			bestPosition=copyPosition(this.getGenes());
			bestFitness = fitness;
		}
		
	}

	

	/** Printable string */
	public String toString() {
		String str = "Size: " + this.size() +"\t";

		 str += "fitness: " + this.getFitnessValue() + "\tbest fitness: " + bestFitness;

		if( this.getGenes() != null ) {
			str += "\n\tPosition:\t";
			for( int i = 0; i < this.getGenes().length; i++ )
				str += ((Double)this.getGenes()[i].getAllele()).doubleValue() + "\t";
		}
		
		if( this.getGenes() != null ) {
			str += "\n\tVelocity:\t";
			for( int i = 0; i < this.getGenes().length; i++ )
				str += ((DoubleParticleComponent)this.getGenes()[i]).getVelocity() + "\t";
		}
		

		if( this.getGenes() != null ) {
			str += "\n\tMinVelocity:\t";
			for( int i = 0; i < this.getGenes().length; i++ )
				str += ((DoubleParticleComponent)this.getGenes()[i]).getMinVelocity() + "\t";
		}
		
		

		if( this.getGenes() != null ) {
			str += "\n\tMaxVelocity:\t";
			for( int i = 0; i < this.getGenes().length; i++ )
				str += ((DoubleParticleComponent)this.getGenes()[i]).getMaxVelocity() + "\t";
		}


		if( bestPosition != null ) {
			str += "\n\tBest:\t";
			for( int i = 0; i < bestPosition.length; i++ )
				str += bestPosition[i] + "\t";
		}

		str += "\n";
		
		 if (getApplicationData() != null) {
		      str+= getApplicationData().toString()+"\n";
		    }
		    else {
		      str+= "null\n";
		    }
		
		 return str;
	}

	
	public void setBestPosition(DoubleParticleComponent[] bestPosition) {
		this.bestPosition = bestPosition;
	}
	

	public double getBestFitness() {
		return bestFitness;
	}

	
	
	
	public void setBestFitness(double bestFitness) {
		this.bestFitness = bestFitness;
	}
	
	public DoubleParticleComponent[] getBestPosition() {
		return bestPosition;
	}

	

   public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

}