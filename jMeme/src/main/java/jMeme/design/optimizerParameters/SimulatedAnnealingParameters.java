/*
 * SimulatedAnnealingParameters.java 
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

package jMeme.design.optimizerParameters;

import jMeme.localSearchAlgorithms.sa.annealingScheme.AnnealingScheme;
import jMeme.localSearchAlgorithms.sa.annealingScheme.SimpleAnnealingScheme;

public class SimulatedAnnealingParameters extends OptimizerParameters {

	private static final long serialVersionUID = 4370136274517424655L;
	
	/**
	 * Constant indicating the default value for maximum distance between a solution and one of its neighbors
	 */
	public static final int MAXDIST_DEFAULT=1;
	/**
	 * Constant indicating the default value for the logarithmic value to use during the decreasement of the temparature
	 */
	public static final double LOGVALUE_DEFAULT=0.8;
	/**
	 * Constant indicating the default value for the number of trials to perform during the computation of the initial temperature
	 */
	public static final int NTRIALS_DEFAULT=5;
	
	/**
	 * Stores the logarithmic value to use during the decreasement of the temparature
	 */
	private double logValue;
	/**
	 * Stores the maximum distance between a solution and one of its neighbors
	 */
	private int maxDist;
	/**
	 * Stores the method which performs the annealing scheme  
	 */
	private AnnealingScheme as;
	/**
	 * Stores the number of trials to perform during the computation of the initial temperature
	 */
	private int nTrials;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the maximum distance between a solution and one of its neighbors to 1,the logarithmic value  to 0.8, the  number of trials to 5 and the simulated annealing scheme to the original one.
	 */
	public SimulatedAnnealingParameters(){
		super();
		maxDist=SimulatedAnnealingParameters.MAXDIST_DEFAULT;
		logValue=SimulatedAnnealingParameters.LOGVALUE_DEFAULT;
		as=new SimpleAnnealingScheme();
		nTrials=SimulatedAnnealingParameters.NTRIALS_DEFAULT;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the maximum distance between a solution and one of its neighbors to the given value,the logarithmic value  to 0.8, the  number of trials to 5 and the simulated annealing scheme to the original one.
	 * 
	 * @param maxdist  the maximum distance between a solution and one of its neighbors
	 */
	public SimulatedAnnealingParameters(int maxdist){
		super();
		maxDist=maxdist;
		logValue=SimulatedAnnealingParameters.LOGVALUE_DEFAULT;
		nTrials=SimulatedAnnealingParameters.NTRIALS_DEFAULT;
		as=new SimpleAnnealingScheme();
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the maximum distance between a solution and one of its neighbors to 1,the logarithmic value  to the given value, the  number of trials to 5 and the simulated annealing scheme to the original one.
	 * 
	 * @param logV  the logarithmic value to use during the decreasement of the temparature
	 */
	public SimulatedAnnealingParameters(double logV){
		super();
		maxDist=SimulatedAnnealingParameters.MAXDIST_DEFAULT;
		logValue=logV;
		nTrials=SimulatedAnnealingParameters.NTRIALS_DEFAULT;
		as=new SimpleAnnealingScheme();
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the maximum distance between a solution and one of its neighbors to 1,the logarithmic value  to 0.8, the  number of trials to 5 and the simulated annealing scheme to the given scheme.
	 * 
	 * @param as  the method which must perform the annealing scheme
	 */
	public SimulatedAnnealingParameters(AnnealingScheme as){
		super();
		maxDist=SimulatedAnnealingParameters.MAXDIST_DEFAULT;
		logValue=SimulatedAnnealingParameters.LOGVALUE_DEFAULT;
		nTrials=SimulatedAnnealingParameters.NTRIALS_DEFAULT;
		this.as=this.as;
	}
	
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the maximum distance between a solution and one of its neighbors to the given value, the logarithmic value to the given value, the number of trials to the given value and the simulated annealing scheme to the given value.
	 * 
	 * @param maxdist  the maximum distance between a solution and one of its neighbors
	 * @param logV  the logarithmic value to use during the decreasement of the temparature
	 * @param as  the method which must perform the annealing scheme
	 * @param nTrials  the number of trials to perform during the computation of the initial temperature
	 */
	public SimulatedAnnealingParameters(int maxdist, double logV, AnnealingScheme as, int nTrials){
		super();
		maxDist=maxdist;
		logValue=logV;
		this.nTrials=nTrials;
		this.as=as;
	}

	/**
	 * @return string representation of the defined SA setting
	 */
	public String toString(){
		String s="";
		s="Max distance: " + maxDist + "\n";
		s+="Log value: " + this.logValue + "\n";
		s="Number of trials: " + this.nTrials + "\n";
		s+="Annealing scheme: " + this.as + "\n";
		
		return s;
	}
	

	public int getMaxDist() {
		return maxDist;
	}



	public void setMaxDist(int maxDist) {
		this.maxDist = maxDist;
	}


	public double getLogValue() {
		return logValue;
	}


	public void setLogValue(double logValue) {
		this.logValue = logValue;
	}


	public AnnealingScheme getAnnealingScheme() {
		return as;
	}


	public void setAnnealingScheme(AnnealingScheme as) {
		this.as = as;
	}


	public int getnTrials() {
		return nTrials;
	}

	public void setnTrials(int nTrials) {
		this.nTrials = nTrials;
	}
	

	
}
