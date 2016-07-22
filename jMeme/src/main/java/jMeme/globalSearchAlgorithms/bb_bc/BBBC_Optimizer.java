/*
 * BBBC_Optimizer.java 
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

package jMeme.globalSearchAlgorithms.bb_bc;


import java.util.Random;

import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DoubleGene;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.core.individuals.Individual;
import jMeme.core.individuals.RealIndividualComponent;
import jMeme.design.optimizerParameters.BB_BCParameters;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;

/**
 * This class implements the evolution performed during one iteration of Big Bang-Big Crunch (BB-BC). BB-BC is a population-based heuristic search
consisting of two parts: The Big Bang where candidate solutions are randomly distributed over the search space; and a Big Crunch where
a contraction operation estimates a weighted average or center of mass for the population. Each sequential Big Bang is then randomly
distributed about the center of mass in the Erol's version. The center of mass in Erol's version can be computed by the equation (2) in [1] or as the best solution.
Instead, in Camp's version, positions of candidate solutions at the beginning of each Big Bang are normally distributed
around a new point located between the center of mass computed by the equation (2) in [1] and the best global solution.
* 
* For more information on BB-BC, see
* [1] Osman K. Erol, Ibrahim Eksin, A new optimization method: Big Bang-Big Crunch, Advances in Engineering Software, vol. 37, issue 2, pp. 106-111, 2006.
* [2] Charles V. Camp, Design of space trusses using Big Bang-Big Crunch optimization, Journal of Structural Engineering, vol. 133, issue 7, pp. 999-1008, 2007.
 
 */
public class BBBC_Optimizer extends GlobalSearchOptimizer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4907441548069642507L;


	/**
	 * Stores the fitness value of the current best individual
	 */
	private double currentBestFitness;
	
	
	/**
	 * Stores the center of the mass of the current population
	 */
	private Individual currentCenterOfMass;
	
	/**
	 * Stores the features of the used fitness function
	 */
	private FitnessFeatures fitnessFeatures;
	/**
	 * Stores the parameter beta controlling the influence of the global best solution
	 */
	private double beta;
	
	/**
	 * Stores the parameter alfa limiting the size of the search space
	 */
	private double alfa;
	/**
	 * Stores the information about the individual to use during the Big Crunch phase. 
	 * In particular, this individual is the best individual if type is BB_BCParameters.CENTER_TYPE_BEST, it is the center of mass if type is BB_BCParameters.CENTER_TYPE_EQUATION2.
	 */
	private String centerType;
	
	/**
	 * Stores the information about which version between Erol's and Camp's must be executed.
	 * In particular, if this parameter is BB_BCParameters.COMPUTATION_TYPE_EROL, the Erol's version is executed, if it is BB_BCParameters.COMPUTATION_TYPE_CAMP then the Camp's version is executed.
	 */
	private String computationType;
	
	  /**
	 * Stores the number of iterations executed
	 */
	protected int numIteration;

	
/**
 * Constructor
 * 
 * @param conf  the configuration object containing all information to run a BB-BC optimizer
 */
	public BBBC_Optimizer(GlobalSearchAlgorithmConfiguration conf) {
		configuration=conf;
		this.beta = ((BB_BCParameters)conf.getGlobalComponent().getParameters()).getBeta();
		this.alfa=((BB_BCParameters)conf.getGlobalComponent().getParameters()).getAlfa();
		this.centerType=((BB_BCParameters)conf.getGlobalComponent().getParameters()).getCenterType();
		this.computationType=((BB_BCParameters)conf.getGlobalComponent().getParameters()).getComputationType();
		
		this.fitnessFeatures=conf.getFitnessFeatures();
		
		numIteration=1;
	}

	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where BB_BC is the global search optimizer
	 */
	public BBBC_Optimizer(MemeticAlgorithmConfiguration conf) {
		configuration=conf;
		this.beta = ((BB_BCParameters)conf.getGlobalComponent().getParameters()).getBeta();
		this.alfa=((BB_BCParameters)conf.getGlobalComponent().getParameters()).getAlfa();
		this.centerType=((BB_BCParameters)conf.getGlobalComponent().getParameters()).getCenterType();
		this.computationType=((BB_BCParameters)conf.getGlobalComponent().getParameters()).getComputationType();
		
		
		this.fitnessFeatures=conf.getFitnessFeatures();
	
	
		numIteration=1;
	}
	
	

	
	@Override
	/**
	 * Allows to execute an iteration of the BB_BC
	 * 
	 * @param pop  the population to evolve
	 * @param a_conf  the configuration of the BB_BC
	 */
	public JPopulation execute(JPopulation aPop, JConfiguration aConf) {
	
		int originalPopSize = aPop.getPopulationSize();
		
		
		//set the global best
		determineBest(aPop);
				
		//compute the current center of the mass
		if(centerType.equals(BB_BCParameters.CENTER_TYPE_BEST))
			currentCenterOfMass=currentBest;
		else computeCenterOfMass(aPop, aConf);
		
		
		
		 Individual[] p=new Individual[originalPopSize];
		   
		 
		 Random rnd=new Random(System.currentTimeMillis());
		 
		 
		
		 
		 int dimension=configuration.getIndividualSize();
	
		for( int i = 0; i < originalPopSize; i++ ) {
		
			double[] genes=new double[dimension];
			
			for(int j=0;j<dimension; j++){
				double xcom=((RealIndividualComponent)(this.currentCenterOfMass.getIndividualComponents()[j])).doubleValue();
				double xbest=((RealIndividualComponent)(this.bestSolution.getIndividualComponents()[j])).doubleValue();
				
				
				double range=((RealIndividualComponent)(this.currentBest.getIndividualComponents()[j])).getUpperBound()-((RealIndividualComponent)(this.currentBest.getIndividualComponents()[j])).getLowerBound();
				
				double r=rnd.nextGaussian();
				
				if(computationType.equals(BB_BCParameters.COMPUTATION_TYPE_CAMP))
				   genes[j]= this.beta*xcom+(1-this.beta)*xbest+((r*this.alfa*range)/numIteration); 
				else  genes[j]= xcom+((r*range)/numIteration); 
				
			}
			try {
				p[i]=new Individual((JConfiguration)aConf, genes);
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}

		
	    aConf.incrementGenerationNr();
	    this.incrementNumIteration();
	    
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
			    return new BBBC_Optimizer((MemeticAlgorithmConfiguration) configuration);
			   
			   return new BBBC_Optimizer((GlobalSearchAlgorithmConfiguration) configuration);
			
		  }


	 
	@Override
	/**
	 * Allows to determine the best individual found until now
	 * 
	 * @param aPop  the population whose the best individual must be determined
	 */
	public void determineBest(JPopulation aPop){
		
		determineCurrentBest(aPop);
		 
		// Update 'best global' position
	    if( bestSolution==null ||compareFitness(currentBestFitness, bestFitness) ) {
			bestSolution=currentBest.clone();
			bestFitness=currentBestFitness;
		}
				
					
	}
	
	
	/**
	 * Allows to determine the best individual of the current population
	 * 
	 * @param aPop  the population whose the best individual must be determined
	 */
	 public void determineCurrentBest(JPopulation aPop){
		 int originalPopSize = aPop.getPopulationSize();
		 
		 currentBest=(Individual)aPop.getIndividual(0);
		 currentBestFitness=aPop.getIndividual(0).getFitnessValue();
		
		
			for( int i = 1; i < originalPopSize; i++ ) {
				
				double fit=aPop.getIndividual(i).getFitnessValue();
				
				if(compareFitness(fit, currentBestFitness)) {  
					currentBest=((Individual)aPop.getIndividual(i)).clone();
					currentBestFitness=fit;
					

				}
				
			}
			
	}

	
	private void computeCenterOfMass(JPopulation aPop, Configuration aConf){
		 int originalPopSize = aPop.getPopulationSize();
		 
		 double sumFitness=1/aPop.getIndividual(0).getFitnessValue();
		 
		 int dimension=configuration.getIndividualSize();
			
		 double genes[]=new double[dimension];
			
		 for(int j=0; j<dimension;j++)
			 genes[j]=(((DoubleGene)aPop.getIndividual(0).getIndividualComponents()[j]).doubleValue())/aPop.getIndividual(0).getFitnessValue();
		 
			
			for( int i = 1; i < originalPopSize; i++ ) {
				
				 double fit=aPop.getIndividual(i).getFitnessValue();
				 sumFitness+=1/fit;
				 
				 for(int j=0; j<dimension;j++)
					 genes[j]+=(((DoubleGene)aPop.getIndividual(i).getIndividualComponents()[j]).doubleValue())/fit;
				
			}
			
			
			for(int j=0; j<dimension;j++){
				genes[j]=genes[j]/sumFitness;
				
			}
				
			try {
				currentCenterOfMass=new Individual((JConfiguration)aConf, genes);
			} catch (InvalidConfigurationException e) {
			
				e.printStackTrace();
			}
			
			
		//	System.out.println("center of mass " + currentCenterOfMass);
	}
	
	



	@Override
	/**
	 * Allows to set variables of the algorithm to initial values
	 */
	public void reset() {
		this.setBestSolution(null);
		this.resetNumIterations();
	}

	
	public Individual getCurrentBest() {
		return currentBest;
	}


	public void setCurrentBest(Individual currentBest) {
		this.currentBest = currentBest;
	}
	
	public Individual getCurrentCenterOfMass() {
		return currentCenterOfMass;
	}

	public void setCurrentCenterOfMass(Individual currentCenterOfMass) {
		this.currentCenterOfMass = currentCenterOfMass;
	}

	
	public int getNumIteration() {
		return numIteration;
	}


	public void resetNumIterations() {
		this.numIteration = 0;
	}
	
	public void incrementNumIteration(){
		
		this.numIteration+=1;
	}



	
	public FitnessFeatures getFitnessFeatures() {
		return fitnessFeatures;
	}

	
	public void setFitnessFunction(FitnessFeatures fitnessFunction) {
		this.fitnessFeatures = fitnessFeatures;
	}

	

	public double getBeta() {
		return beta;
	}


	public void setBeta(double beta) {
		this.beta = beta;
	}


	public double getAlfa() {
		return alfa;
	}


	public void setAlfa(double alfa) {
		this.alfa = alfa;
	}

	public void setFitnessFeatures(FitnessFeatures fitnessFeatures) {
		this.fitnessFeatures = fitnessFeatures;
	}





}
