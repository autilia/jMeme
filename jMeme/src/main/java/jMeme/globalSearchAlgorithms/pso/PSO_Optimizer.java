/*
 * PSO_Optimizer.java 
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


import org.jgap.InvalidConfigurationException;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.Problem;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.optimizerParameters.PSOParameters;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;
import jMeme.globalSearchAlgorithms.pso.updatingStrategy.ParticleUpdate;
import jMeme.globalSearchAlgorithms.pso.updatingStrategy.ParticleUpdateConstriction;
import jMeme.globalSearchAlgorithms.pso.updatingStrategy.ParticleUpdateFullyRandom;
import jMeme.globalSearchAlgorithms.pso.updatingStrategy.ParticleUpdateRandomByParticle;
import jMeme.globalSearchAlgorithms.pso.updatingStrategy.ParticleUpdateSimple;


/**
 * This class implements the evolution performed during one iteration of the Particle Swarm Optimization (PSO). PSO is a population-based optimization algorithm
 * belonging to the class of swarm intelligence algorithms, which are inspired from the
 * dynamics that arise in socially organized colonies. In detail, PSO exploits a population of individuals, called particles, 
 * to probe the search space. Each particle moves with an adaptable velocity within the search space,
 * and retains a memory of the best position it ever encountered. In the global variant of PSO, the best position ever attained by all individuals is communicated to
 * all the particles. In the local variant, each particle is assigned to a neighborhood, and only the best position ever attained by the particles that
 * comprise the neighborhood is communicated among them.
 * For more details, see the works:
 * [1]Shi, Y. and Eberhart. R.C. (1998a). A Modified Particle Swarm Optimizer. In Proceedings IEEE Conference on
 * Evolutionary Computation (pp. 69–73). Anchorage, AK. IEEE Service Center.
 * 
 */
public class PSO_Optimizer extends GlobalSearchOptimizer{

	
	private static final long serialVersionUID = 8914819700351684699L;
	/**
	 * Stores the features of the fitness function
	 */
	private FitnessFeatures fitnessFeatures;
	/**
	 * Stores the global increment (for velocity update), usually called 'c2' constant or social parameter
	 */
	private double globalIncrement;
	/**
	 * Stores the inertia weight or the constriction factor (for velocity update) according to the PSO variant
	 */
	private double confidence;
	
	/**
	 * Stores particle's increment (for velocity update), usually called 'c1' constant or cognitive parameter
	 */
	private double particleIncrement;
	/**
	 * Stores the particle update strategy
	 */
	private ParticleUpdate particleUpdate;
	/**
	 * Stores the name of the particle update strategy
	 */
	private String nameUpdating;

	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a particle swarm optimizer
	 */
	public PSO_Optimizer(GlobalSearchAlgorithmConfiguration conf) {
		configuration=conf;
		this.globalIncrement = ((PSOParameters)conf.getGlobalComponent().getParameters()).getGlobalIncrement();
		this.confidence = ((PSOParameters)conf.getGlobalComponent().getParameters()).getConfidence();
		this.particleIncrement = ((PSOParameters)conf.getGlobalComponent().getParameters()).getParticleIncrement();
		this.bestSolution = null;
		 this.fitnessFeatures=conf.getFitnessFeatures();
		this.nameUpdating = ((PSOParameters)conf.getGlobalComponent().getParameters()).getUpdatingStrategy();

	this.particleUpdate=null;
	}
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where PSO is the global search optimizer
	 */
	public PSO_Optimizer(MemeticAlgorithmConfiguration conf) {
		configuration=conf;
		this.globalIncrement = ((PSOParameters)conf.getGlobalComponent().getParameters()).getGlobalIncrement();
		this.confidence = ((PSOParameters)conf.getGlobalComponent().getParameters()).getConfidence();
		this.particleIncrement = ((PSOParameters)conf.getGlobalComponent().getParameters()).getParticleIncrement();
		this.bestSolution = null;
		this.fitnessFeatures=conf.getFitnessFeatures();
		 // Set up particle update strategy (default: ParticleUpdateSimple) 
		this.nameUpdating = ((PSOParameters)conf.getGlobalComponent().getParameters()).getUpdatingStrategy();
		this.particleUpdate=null;
	}

	
	

	
	@Override
	/**
	 * Allows to execute an iteration of the PSO
	 * 
	 * @param pop  the population to evolve
	 * @param a_conf  the configuration of the PSO
	 */
	public JPopulation execute(JPopulation aPop, JConfiguration aConf) {
	
		if(this.particleUpdate==null)
			{
			this.particleUpdate=this.setUpdating(nameUpdating);
			}
		
		int originalPopSize = aPop.getPopulationSize();
		
		
		// Initialize a particle update iteration
		particleUpdate.begin(this);

		// For each particle...
		for( int i = 0; i < originalPopSize; i++ ) {
			// Update particle's position and speed
			particleUpdate.update(this, ((Particle)aPop.getIndividual(i)));
			
			
			((Particle)aPop.getIndividual(i)).resetFitness();
			((Particle)aPop.getIndividual(i)).getFitnessValue();
			
		}

		// Finish a particle update iteration
		particleUpdate.end(this);
		
		
		// update best local
		determineBest(aPop);
		
		// Increase number of generations.
	    // -------------------------------
	    aConf.incrementGenerationNr();
	    
	    Particle[] p=new Particle[originalPopSize];
	    aPop.getIndividuals().toArray(p);
	    
	    try {
			return new JPopulation(aConf, p);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	/**
	 * Allows to compare this instance with another object in terms of class name
	 */
	public int compareTo(Object a_other) {
		if (a_other == null) {
		      return 1;
		    }
		
		return getClass().getName().compareTo(a_other.getClass().getName());
	}
	
	@Override
	 /**
	   * @return a clone of this instance
	   */
	 public Object clone() {
		 if(configuration.isMemeticConfig())
			    return new PSO_Optimizer((MemeticAlgorithmConfiguration)configuration);
			
			 return new PSO_Optimizer((GlobalSearchAlgorithmConfiguration)configuration);
			
	 
	 }

	
	 
	@Override
	/**
	 * Allows to determine the best individual found until now
	 * 
	 * @param aPop  the population whose the best individual must be determined
	 */
	public void determineBest(JPopulation aPop){
		 int originalPopSize = aPop.getPopulationSize();
		 
			//---
			// Evaluate each particle (and find the 'best' one)
			//---
			for( int i = 0; i < originalPopSize; i++ ) {
				// Evaluate particle
				 double fit=aPop.getIndividual(i).getFitnessValue();
				// System.out.println("pop " + i + " " + fit);
				((Particle)aPop.getIndividual(i)).setBestFitness(fit,fitnessFeatures.isMaximize());

				// Update 'best global' position
			    if( bestSolution==null ||compareFitness(fit,bestSolution.getFitnessValueDirectly()) ) { // Minimize 
					bestSolution=((Particle)aPop.getIndividual(i)).clone();
					bestFitness=bestSolution.getFitnessValue();
				}
				
			}
			
	}

	


	@Override
	public void setSampleIndividual(Problem problem) {
		try {
			configuration.setSampleChromosome(problem.prepareParticleScheme(configuration));
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void reset() {
		this.setBestSolution(null);
		this.setParticleUpdate(null);
	}


	

	public FitnessFeatures getFitnessFeatures() {
		return fitnessFeatures;
	}


	public double getGlobalIncrement() {
		return globalIncrement;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getParticleIncrement() {
		return particleIncrement;
	}

	
	public ParticleUpdate getParticleUpdate() {
		return particleUpdate;
	}

	public void setFitnessFunction(FitnessFeatures fitnessFunction) {
		this.fitnessFeatures = fitnessFeatures;
	}

	public void setGlobalIncrement(double globalIncrement) {
		this.globalIncrement = globalIncrement;
	}


	public void setParticleIncrement(double particleIncrement) {
		this.particleIncrement = particleIncrement;
	}

	public void setParticleUpdate(ParticleUpdate particleUpdate) {
		this.particleUpdate = particleUpdate;
	}


	

	
	private ParticleUpdate setUpdating(String nameUpdating){
		int dimension=configuration.getIndividualSize();
		if(nameUpdating.equals(PSOParameters.simpleUpdating))
			return new ParticleUpdateSimple(dimension);
		else if(nameUpdating.equals(PSOParameters.ramdonXParticleUpdating))
			return new ParticleUpdateRandomByParticle(dimension);
		else if(nameUpdating.equals(PSOParameters.fullyRandomUpdating))
			return new ParticleUpdateFullyRandom(dimension);
		else return new ParticleUpdateConstriction(dimension);
	}


}
