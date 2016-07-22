/*
 * MemeticSearchAlgorithmConfiguration.java 
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import jMeme.core.JConfiguration;
import jMeme.core.AlgorithmComponent;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.finalConditions.LocalOptimizerFinalConditions;
import jMeme.design.generationExecutor.GenerationExecutor;
import jMeme.design.generationExecutor.RandomGenerationExecutor;
import jMeme.design.localIntegration.LocalFrequency;
import jMeme.design.localIntegration.LocalIntensity;
import jMeme.design.localIntegration.LocalOptimizerExecutor;
import jMeme.design.optimizerParameters.GlobalSearchOptimizerParameters;
import jMeme.design.optimizerParameters.OptimizerParameters;

import jMeme.localSearchAlgorithms.LocalSearchOptimizer;
import jMeme.performances.AlgorithmPerformance;
import jMeme.performances.MemeticAlgorithmPerformance;

import org.jgap.impl.DefaultConfiguration;

public class MemeticAlgorithmConfiguration extends GlobalSearchAlgorithmConfiguration {

	
	private static final long serialVersionUID = -2653320864739861485L;

	/**
	 * Stores information about the local search algorithm to run such as name and parameters
	 */
	protected AlgorithmComponent localComponent;

	/**
	 * Stores information about the local intensity to apply during the local search algorithm  
	 */
	protected LocalIntensity localIntensity;
	
	/**
	 * Stores information about the termination criteria of the local search algorithm  
	 */
	protected LocalOptimizerFinalConditions localFinalConditions;
	
	/**
	 * Stores information about the individual selection scheme for the local search algorithm
	 */
	protected String localSelectionScheme;
	/**
	 * Stores information about the local frequency to apply during the local search algorithm
	 */
	protected LocalFrequency frequencyPercentage;
	
	
	
	/**
     * Constructor 
     * 
     * @param fitFeatures  object that contains features of the fitness function
	 * @param gc  object that contains information about the global search algorithm to run such as name and parameters
	 * @param lc  object that contains information about the local search algorithm to run such as name and parameters
     */
	public MemeticAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmComponent gc, AlgorithmComponent lc){
		super(fitFeatures, gc);
		localComponent=lc;
		localIntensity=new LocalIntensity();
		localFinalConditions=new LocalOptimizerFinalConditions((AlgorithmFinalConditions)finalConditions,localIntensity);
		localSelectionScheme=LocalOptimizerExecutor.DEFAULT_SELECTION_SCHEME;
		frequencyPercentage=new LocalFrequency();
		
		
		performance=new MemeticAlgorithmPerformance();
		
		memeticConfig=true;
	}
	
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 * @param lc  object that contains information about the local search algorithm to run such as name and parameters
	 */
	public MemeticAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmFinalConditions fc, AlgorithmComponent gc, AlgorithmComponent lc){
		super(fitFeatures,fc, gc);
		
		localComponent=lc;
		localIntensity=new LocalIntensity();
		localFinalConditions=new LocalOptimizerFinalConditions((AlgorithmFinalConditions)finalConditions,localIntensity);
		localSelectionScheme=LocalOptimizerExecutor.DEFAULT_SELECTION_SCHEME;
		frequencyPercentage=new LocalFrequency();
	
		
		performance=new MemeticAlgorithmPerformance();
		memeticConfig=true;
	}
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 * @param lc  object that contains information about the local search algorithm to run such as name and parameters
	 * @param lmf object that contains information about the local intensity 
	 */
	public MemeticAlgorithmConfiguration(FitnessFeatures ff, AlgorithmFinalConditions fc, AlgorithmComponent gc, AlgorithmComponent lc,  LocalIntensity lmf){
		super(ff,fc,gc);
		localComponent=lc;
		localIntensity=lmf;
		localFinalConditions=new LocalOptimizerFinalConditions((AlgorithmFinalConditions)finalConditions,localIntensity);
		localSelectionScheme=LocalOptimizerExecutor.DEFAULT_SELECTION_SCHEME;
		frequencyPercentage=new LocalFrequency();

		

		performance=new MemeticAlgorithmPerformance();
		memeticConfig=true;
	}
	
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 * @param lc  object that contains information about the local search algorithm to run such as name and parameters
	 * @param lmf object that contains information about the local intensity 
	 * @param frequencyPercentage object that contains information about the local frequency
	 */
	public MemeticAlgorithmConfiguration(FitnessFeatures ff, AlgorithmFinalConditions fc, AlgorithmComponent gc, AlgorithmComponent lc,  LocalIntensity lmf, LocalFrequency frequencyPercentage){
		super(ff,fc, gc);
		localComponent=lc;
		localIntensity=lmf;
		localFinalConditions=new LocalOptimizerFinalConditions((AlgorithmFinalConditions)finalConditions,localIntensity);
		
		this.localSelectionScheme=LocalOptimizerExecutor.DEFAULT_SELECTION_SCHEME;
		this.frequencyPercentage=frequencyPercentage;

		performance=new MemeticAlgorithmPerformance();
		memeticConfig=true;
	}
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 * @param lc  object that contains information about the local search algorithm to run such as name and parameters
	 * @param lmf object that contains information about the local intensity 
	 * @param frequencyPercentage object that contains information about the local frequency
	 * @param localSelectionScheme string representing the individual selection scheme for the local search procedure
	 */
	public MemeticAlgorithmConfiguration(FitnessFeatures ff, AlgorithmFinalConditions fc, AlgorithmComponent gc, AlgorithmComponent lc,  LocalIntensity lmf,LocalFrequency frequencyPercentage,  String localSelectionScheme){
		super(ff,fc, gc);
		localComponent=lc;
		localIntensity=lmf;
		localFinalConditions=new LocalOptimizerFinalConditions((AlgorithmFinalConditions)finalConditions,localIntensity);
		
		this.localSelectionScheme=localSelectionScheme;
		this.frequencyPercentage=frequencyPercentage;

		
		performance=new MemeticAlgorithmPerformance();
		memeticConfig=true;
	}
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 * @param lc  object that contains information about the local search algorithm to run such as name and parameters
	 * @param lmf object that contains information about the local intensity 
	 * @param frequencyPercentage object that contains information about the local frequency
	 * @param localSelectionScheme string representing the individual selection scheme for the local search procedure
	 * @param gen object aimed at generating the population 
	 */
	public MemeticAlgorithmConfiguration(FitnessFeatures ff, AlgorithmFinalConditions fc, AlgorithmComponent gc, AlgorithmComponent lc,  LocalIntensity lmf,LocalFrequency frequencyPercentage,  String localSelectionScheme, GenerationExecutor gen){
		super(ff,fc, gc, gen);
		localComponent=lc;
		localIntensity=lmf;
		localFinalConditions=new LocalOptimizerFinalConditions((AlgorithmFinalConditions)finalConditions,localIntensity);
		
		this.localSelectionScheme=localSelectionScheme;
		this.frequencyPercentage=frequencyPercentage;

		
		performance=new MemeticAlgorithmPerformance();
		memeticConfig=true;
	}
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the global search algorithm to run such as name and parameters
	 * @param lc  object that contains information about the local search algorithm to run such as name and parameters
	 * @param lmf object that contains information about the local intensity 
	 * @param frequencyPercentage object that contains information about the local frequency
	 * @param localSelectionScheme string representing the individual selection scheme for the local search procedure
	 * @param gen object aimed at generating the population 
	 * @param performance object aimed at storing the performance of the executed memetic algorithm
	 */
	public MemeticAlgorithmConfiguration(FitnessFeatures ff, AlgorithmFinalConditions fc, AlgorithmComponent gc, AlgorithmComponent lc, LocalIntensity lmf, LocalFrequency frequencyPercentage, String localSelectionScheme, GenerationExecutor gen, MemeticAlgorithmPerformance performance){
		super(ff,fc, gc, gen);
		localComponent=lc;
		localIntensity=lmf;
		localFinalConditions=new LocalOptimizerFinalConditions((AlgorithmFinalConditions)finalConditions,localIntensity);
		this.localSelectionScheme=localSelectionScheme;
		this.frequencyPercentage=frequencyPercentage;

		
		this.performance=performance;
		memeticConfig=true;
	}
	
	
	/**
	 * Allows to instantiate the local search optimizer
	 * @return the local search optimizer to run during the global search evolution
	 */
	public LocalSearchOptimizer getLocalMethod(){
		Class c=null;
		try {
			c = Class.forName(this.localComponent.getOptimizerName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		Constructor  costr=null;
		try {
			costr=c.getConstructor(MemeticAlgorithmConfiguration.class);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		LocalSearchOptimizer localMethod=null;
		try {
			 try {
				localMethod= (LocalSearchOptimizer)costr.newInstance(this);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		return localMethod;
	}
	
	/**
     * Allows to read a configuration saved in a file
     * 
     * @param fileName name file where the configuration is saved
     * @return an object that contains the configuration read from the file named fileName
     */
	public static MemeticAlgorithmConfiguration read(String fileName){
		MemeticAlgorithmConfiguration conf=null;
		try
	      {
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         conf = (MemeticAlgorithmConfiguration) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("MemeticAlgorithmConfiguration class not found");
	         c.printStackTrace();
	         return null;
	      }
	      System.out.println("Deserialized configuration...");
	      
	      return conf;
	}
	
	/**
	 * @return string representation of the configuration containing all
	 * configurable elements	
	 */
	public String toString(){
		String s="";
		
		s+="Global Component: " + this.globalComponent.getId();
		s+="\nGlobal Component features:\n " + this.globalComponent.getParameters();
		s+="\nLocal Component: " + this.localComponent.getId();
		s+="\nLocal Component features:\n " + this.localComponent.getParameters();
		s+="\n"+this.localIntensity;
		s+="\n"+this.frequencyPercentage;
		s+="\nSelection scheme: " + localSelectionScheme;
		
		s+="\nCommon features: \n";
		s+="Population size: " + ((GlobalSearchOptimizerParameters)this.globalComponent.getParameters()).getPopulationSize();
		s+="\nTermination:\n" + this.finalConditions;
		
    
    	return s;
    	
	}
  

	public LocalOptimizerFinalConditions getLocalFinalConditions() {
		return localFinalConditions;
	}

	
	public void setLocalFinalConditions(LocalOptimizerFinalConditions localFinalConditions) {
		    this.localFinalConditions = localFinalConditions;
	}

	
	public String getLocalSelectionScheme() {
		return localSelectionScheme;
	}

	
	public void setLocalSelectionScheme(String localSelectionScheme) {
		this.localSelectionScheme = localSelectionScheme;
	}


	public LocalFrequency getFrequencyPercentage() {
		return frequencyPercentage;
	}

	
	public void setFrequencyPercentage(LocalFrequency frequencyPercentage) {
		this.frequencyPercentage = frequencyPercentage;
	}

	


	public AlgorithmComponent getLocalComponent() {
		return localComponent;
	}

	
	public void setLocalComponent(AlgorithmComponent localComponent) {
		this.localComponent = localComponent;
	}

	
	public LocalIntensity getLocalIntensity() {
		return localIntensity;
	}

	
	public void setLocalIntensity(LocalIntensity localIntensity) {
		this.localIntensity = localIntensity;
	}

	
	
	
	
}
