/*
 * LocalSearchAlgorithmConfiguration.java 
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
import jMeme.performances.AlgorithmPerformance;

public class LocalSearchAlgorithmConfiguration extends JConfiguration {

	private static final long serialVersionUID = -2936485662914459990L;

	
	/**
	 * Stores information about the local search algorithm to run such as name and parameters
	 */
	protected AlgorithmComponent localComponent;

	 /**
     * Constructor 
     * 
     * @param fitFeatures  object that contains features of the fitness function
	 * @param gc  object that contains information about the local search algorithm to run such as name and parameters
     */
	public LocalSearchAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmComponent lc){
		super(fitFeatures);
		finalConditions=new AlgorithmFinalConditions();
		localComponent=lc;

		performance=new AlgorithmPerformance();
		memeticConfig=false;
	}
	
	
	/**
	 * Costructor 
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the local search algorithm to run such as name and parameters
	 */
	public LocalSearchAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmFinalConditions fc, AlgorithmComponent lc){
		super(fitFeatures,fc);
		localComponent=lc;
	
		performance=new AlgorithmPerformance();
		memeticConfig=false;
	}
	
	/**
	 * Costructor
	 * 
	 * @param fitFeatures object that contains features of the fitness function
	 * @param fc object that contains termination criteria specification
	 * @param gc object that contains information about the local search algorithm to run such as name and parameters
	 * @param performance object aimed at storing the performance of the executed algorithm
	 */
	public LocalSearchAlgorithmConfiguration(FitnessFeatures fitFeatures, AlgorithmFinalConditions fc, AlgorithmComponent lc, AlgorithmPerformance performance){
		super(fitFeatures,fc);
		localComponent=lc;

		this.performance=performance;
		memeticConfig=false;
	}
	
	/**
	 * @return string representation of the configuration containing all
	 * configurable elements	
	 */
	public String toString(){
		String s="";
		
		s+="Local Component: " + this.localComponent.getId();
		s+="\nLocal Component features:\n " + this.localComponent.getParameters();
		
		s+="\nCommon features: \n";
		s+="Termination:\n " + this.finalConditions;
		
    	return s;
    	
	}
	
	/**
     * Allows to read a configuration saved in a file
     * 
     * @param fileName name file where the configuration is saved
     * @return an object that contains the configuration read from the file named fileName
     */
	public static LocalSearchAlgorithmConfiguration read(String fileName){
		LocalSearchAlgorithmConfiguration conf=null;
		try
	      {
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         conf = (LocalSearchAlgorithmConfiguration) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("LocalSearchAlgorithmConfiguration class not found");
	         c.printStackTrace();
	         return null;
	      }
	      System.out.println("Deserialized configuration...");
	      
	      return conf;
	}
	
	
	public AlgorithmComponent getLocalComponent() {
		return localComponent;
	}

	
	public void setLocalComponent(AlgorithmComponent localComponent) {
		this.localComponent = localComponent;
	}
	
	
	
	
	
}
