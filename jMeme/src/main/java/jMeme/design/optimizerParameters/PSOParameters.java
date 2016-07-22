/*
 * PSOParameters.java 
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

/**
 * This class allows to set the parameters for a particle swarm optimization algorithm.
 * The parameters are: the population size, the cognitive parameter c1, the social parameter c2, the constriction factor, the inertia weight.
 */
public class PSOParameters extends GlobalSearchOptimizerParameters{


	
	private static final long serialVersionUID = 4567789164422633268L;
	/**
	 * Constant indicating the simple updating strategy using inertia as confidence
	 */
	public static final String simpleUpdating="SimpleUpdating";
	/**
	 * Constant indicating the random updating strategy using inertia as confidence and selecting random values in velocity updating 
     * different for each particle	 
     */
	public static final String ramdonXParticleUpdating="ramdonXParticleUpdating";
	/**
	 * Constant indicating the fully random updating strategy using inertia as confidence and selecting random values in velocity updating 
     * different for each particle and for each component of the particle
     */
	public static final String fullyRandomUpdating="fullyRandomUpdating";
	/**
	 * Constant indicating the constriction updating strategy using the constriction factor as confidence and selecting random values in velocity updating 
     * different for each particle and for each component of the particle
     */
	public static final String constrictionUpdating="constrictionUpdating";
	/**
	 * Constant indicating the default value for the social parameter 'c2'
	 */
	public static final double DEFAULT_GLOBAL_INCREMENT = 0.9;
	/**
	 * Constant indicating the default value for the confidence (inertia weight or constriction factor according to pso variant)
	 */
	public static final double CONFIDENCE = 0.95;
	/**
	 * Costant indicating the default value for the cognitive parameter 'c1'
	 */
	public static final double DEFAULT_PARTICLE_INCREMENT = 0.9;
	
	
	/**
	 * Stores the name of the particle update strategy
	 */
	private String updatingStrategy;
	
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
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20, the cognitive parameter to 0.9, the social parameter to 0.9, the confidence value to 0.95, the updating strategy to fully random by considering inertia weight.
	 */
	public PSOParameters()
	{
		super();
		updatingStrategy=PSOParameters.fullyRandomUpdating;
		particleIncrement=PSOParameters.DEFAULT_PARTICLE_INCREMENT;
		globalIncrement=PSOParameters.DEFAULT_GLOBAL_INCREMENT;
		confidence=PSOParameters.CONFIDENCE;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given population size pop, the cognitive parameter to 0.9, the social parameter to 0.9, the confidence value to 0.95, the updating strategy to fully random by considering inertia weight.
	 */
	public PSOParameters(int pop)
	{
		super(pop);
		updatingStrategy=PSOParameters.fullyRandomUpdating;
		particleIncrement=PSOParameters.DEFAULT_PARTICLE_INCREMENT;
		globalIncrement=PSOParameters.DEFAULT_GLOBAL_INCREMENT;
		confidence=PSOParameters.CONFIDENCE;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given population size pop, the cognitive parameter to 0.9, the social parameter to 0.9, the confidence value to 0.95, the updating strategy to the given strategy.
	 */
	public PSOParameters(int pop, String updatingStrategy)
	{
		super(pop);
		this.updatingStrategy=updatingStrategy;
		particleIncrement=PSOParameters.DEFAULT_PARTICLE_INCREMENT;
		globalIncrement=PSOParameters.DEFAULT_GLOBAL_INCREMENT;
		confidence=PSOParameters.CONFIDENCE;
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to the given population size pop, the cognitive parameter to the given value, the social parameter to the given value, the confidence value to the given value, the updating strategy to the given strategy.
	 */
	public PSOParameters(int pop, String updatingStrategy,double particleIncrement, double globalIncrement, double confidence)
	{
		super(pop);
		this.updatingStrategy=updatingStrategy;
		this.particleIncrement=particleIncrement;
		this.globalIncrement=globalIncrement;
		this.confidence=confidence;
	}
	
	
	/**
	 * @return string representation of the defined particle swarm optimization algorithm setting
	 */
	public String toString(){
		String s="";
		s="Confidence : " + confidence+ "\n";
		s+="Global increment: " + globalIncrement + "\n";
		s+="Particle increment: " + particleIncrement + "\n";
		s+="Updating Strategy: " + updatingStrategy + "\n";
		
		return s;
	}
	


	public String getUpdatingStrategy() {
		return updatingStrategy;
	}


	public void setUpdatingStrategy(String updatingStrategy) {
		this.updatingStrategy = updatingStrategy;
	}



	public double getConfidence() {
		return confidence;
	}


	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}


	
	public double getGlobalIncrement() {
		return globalIncrement;
	}


	public void setGlobalIncrement(double globalIncrement) {
		this.globalIncrement = globalIncrement;
	}


	public double getParticleIncrement() {
		return particleIncrement;
	}


	public void setParticleIncrement(double particleIncrement) {
		this.particleIncrement = particleIncrement;
	}



	


	
}
