/*
 * GlobalSearchAlgorithmConfiguration.java 
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


package jMeme.core.configurations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import jMeme.core.AlgorithmComponent;
import jMeme.core.JConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.generationExecutor.GenerationExecutor;
import jMeme.design.generationExecutor.RandomGenerationExecutor;
import jMeme.design.optimizerParameters.GlobalSearchOptimizerParameters;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;
import jMeme.performances.AlgorithmPerformance;

public class GlobalSearchAlgorithmConfiguration extends JConfiguration {

	private static final long serialVersionUID = -6863629325709468147L;

	
	/**
	 * Stores information about the global search algorithm to run such as name and parameters
	 */
	protected AlgorithmComponent globalComponent;
	
	/**
	 * Allows to generate a population with a specific strategy
	 */
	protected GenerationExecutor generator;
	
	/**
	 * Stores the instantiated optimizer to run
	 */
	protected GlobalSearchOptimizer globalOptimizer;
	
	
	
    /**
     * Constructor 
     * 
     * @param fitFeatures  object that contains features of the fitness function
	 * @param gc  object that contains information about the global search algorithm to run such as name and parameters
     */
	public GlobalSearchAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmComponent gc){
		super(fitFeatures);
		finalConditions=new AlgorithmFinalConditions();
		globalComponent=gc;
		generator=new RandomGenerationExecutor();

		performance=new AlgorithmPerformance();
		memeticConfig=false;
	}
	
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 */
	public GlobalSearchAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmFinalConditions fc, AlgorithmComponent gc){
		super(fitFeatures,fc);
		globalComponent=gc;
		generator=new RandomGenerationExecutor();

		performance=new AlgorithmPerformance();
		memeticConfig=false;
	}
	
	/**
	 * Costructor
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 * @param gen object aimed at generating the population 
	 */
	public GlobalSearchAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmFinalConditions fc, AlgorithmComponent gc, GenerationExecutor gen){
		super(fitFeatures,fc);
		globalComponent=gc;
		generator=gen;

		performance=new AlgorithmPerformance();
		memeticConfig=false;
	}
	
	
	/**
	 * Costructor 
	 * ws
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the algorithm to run such as name and parameters
	 * @param gen object aimed at generating the population
	 * @param performance object aimed at storing the performance of the executed algorithm
	 */
	public GlobalSearchAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmFinalConditions fc, AlgorithmComponent gc, GenerationExecutor gen, AlgorithmPerformance performance){
		super(fitFeatures,fc);
		globalComponent=gc;
		generator=gen;
		
		this.performance=performance;
		memeticConfig=false;
	}
	
	
	/**
	 * @return string representation of the configuration containing all
	 * configurable elements	
	 */
	public String toString(){
		String s="";
		
		s+="Global Component: " + this.globalComponent.getId();
		s+="Global Component features:\n " + this.globalComponent.getParameters();
		
		s+="Common features: \n";
		s+="Population size: " + ((GlobalSearchOptimizerParameters)this.globalComponent.getParameters()).getPopulationSize();
		s+="Termination: " + this.finalConditions;
		
    	return s;
    	
	}
	
    /**
     * Allows to read a configuration saved in a file
     * 
     * @param fileName name file where the configuration is saved
     * @return an object that contains the configuration read from the file named fileName
     */
	public static GlobalSearchAlgorithmConfiguration read(String fileName){
		GlobalSearchAlgorithmConfiguration conf=null;
		try
	      {
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         conf = (GlobalSearchAlgorithmConfiguration) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("GlobalSearchAlgorithmConfiguration class not found");
	         c.printStackTrace();
	         return null;
	      }
	      System.out.println("Deserialized configuration...");
	      
	      return conf;
	}

	
	public GlobalSearchOptimizer getGlobalOptimizer() {
		return globalOptimizer;
	}

	public void setGlobalOptimizer(GlobalSearchOptimizer globalOptimizer) {
		this.globalOptimizer = globalOptimizer;
	}

	public AlgorithmComponent getGlobalComponent() {
		return globalComponent;
	}

	public void setGlobalComponent(AlgorithmComponent globalComponent) {
		this.globalComponent = globalComponent;
	}

	public GenerationExecutor getGenerator() {
		return generator;
	}

	public void setGenerator(GenerationExecutor generator) {
		this.generator = generator;
	}

	
	
}
