/*
 * JConfiguration.java 
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


package jMeme.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.jgap.InvalidConfigurationException;

import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.core.individuals.Individual;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.performances.AlgorithmPerformance;


/**
 * This class represents the generic template of a configuration necessary to execute an algorithm.
 * A configuration for any algorithm contains information about fitness function and termination criteria.
 */
public abstract class JConfiguration extends org.jgap.impl.DefaultConfiguration {

	private static final long serialVersionUID = 3167435675942609171L;


	
	/**
	 * Stores termination criteria of the algorithm
	 */
	protected AlgorithmFinalConditions finalConditions;
	
	/**
	 * Stores fitness features of the algorithm
	 */
	protected FitnessFeatures fitnessFeatures;
	
	/**
	 * Stores performance of the algorithm such as 
	 * the number of iterations, the number of evaluations, time of execution and so on.  
	 */
	protected AlgorithmPerformance performance;

	/**
	 * Stores the information about the nature of the configuration, 
	 * true if the configuration is related to a memetic algorithm.
	 */
	protected boolean memeticConfig;
	
	
 /**
  * Constructor
  * 
  * @param fitFeatures  object that contains features of the fitness function
  */
	public JConfiguration( FitnessFeatures fitFeatures){
		super("","");
		
		finalConditions=new AlgorithmFinalConditions();
		finalConditions.setSignFitness(fitnessFeatures.getFitnessSign());
		
		fitnessFeatures = fitFeatures;
		
	}
	
	/**
	 * Constructor 
	 * 
	 * @param fitFeatures  object that contains features of the fitness function
	 * @param fc  object that contains termination criteria specification
	 */
	public JConfiguration(FitnessFeatures fitFeatures, AlgorithmFinalConditions fc){
		super("","");
		
		fitnessFeatures = fitFeatures;
		
		finalConditions=fc;
		
		finalConditions.setSignFitness(fitnessFeatures.getFitnessSign());
		
		
		}
	

	/**
	 * Allows to save the configuration to a file
	 * 
	 * @param fileName  name of the file that will store the configuration
	 */
	public void save(String fileName){
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(fileName);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this);
	         out.close();
	         fileOut.close();
	         System.out.printf("Configuration Serialized");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	
    /**
     * Allows to set a template for the solution of the problem 
     * to be solved by the related algorithm.
     * 
     * @param ind  a sample of a solution for the problem to be solved
     */
	public void setSampleIndividual(Individual ind){
		try {
			this.setSampleChromosome(ind);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	   * Retrieves the individual size being used by the algorithm. 
	   * This value is set automatically when the sample individual is provided.
	   *
	   * @return individual size used in this configuration
	   */
	public int getIndividualSize(){
		return this.getChromosomeSize();
		
	}
	
	 /**
	   * @return string representation of the configuration containing all
	   * configurable elements
	   */
	public abstract String toString();
	
	public boolean isMemeticConfig() {
		return memeticConfig;
	}

	public FinalConditions getFinalConditions() {
		return finalConditions;
	}
	
	public void setFinalConditions(AlgorithmFinalConditions finalConditions) {
		this.finalConditions = finalConditions;
	}
	
	public FitnessFeatures getFitnessFeatures() {
		return fitnessFeatures;
	}
	
	public void setFitnessFeatures(FitnessFeatures fitnessFeatures) {
		this.fitnessFeatures = fitnessFeatures;
	}
	
	public AlgorithmPerformance getPerformance() {
		return performance;
	}

	public void setPerformance(AlgorithmPerformance performance) {
		this.performance = performance;
	}
	

}
