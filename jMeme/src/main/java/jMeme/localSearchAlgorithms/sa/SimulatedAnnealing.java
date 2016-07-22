/*
 * SimulatedAnnealing.java 
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
package jMeme.localSearchAlgorithms.sa;




import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.SimulatedAnnealingParameters;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;
import jMeme.localSearchAlgorithms.sa.annealingScheme.AnnealingScheme;

import java.util.Random;

import org.jgap.Gene;
import org.jgap.RandomGenerator;



/**
 * This class implementation the Simulated Annealing metaheuristic. This metaheuristic
 * performs a search through the solution space mainly guided by its parameter
 * called temperature. At the end, it returns the best solution achieved during
 * the process of search.
 */
public class SimulatedAnnealing extends LocalSearchOptimizer {
	
	
	private static final long serialVersionUID = 8337448847271896565L;
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
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run the Simulated Annealing procedure
	 */	
	public SimulatedAnnealing(LocalSearchAlgorithmConfiguration conf){
		this.configuration=conf;
		maxDist=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getMaxDist();
	   logValue=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getLogValue();
	   as=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getAnnealingScheme();
	   nTrials=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getnTrials();
	}
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where the Simulated Annealing procedure is the local search optimizer
	 */
	public SimulatedAnnealing(MemeticAlgorithmConfiguration conf){
		this.configuration=conf;
		maxDist=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getMaxDist();
	   logValue=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getLogValue();
	   as=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getAnnealingScheme();
	   nTrials=((SimulatedAnnealingParameters)conf.getLocalComponent().getParameters()).getnTrials();
	}
	
	
	@Override
	public void startToSolve() {
		Individual s=bestSolution.clone();
		
		Individual bs = s.clone();
		
		this.localReset();
		
		initialEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
		
	        
		double  temperature = setInitialTemperature(s);
		
		
		  
		do {
			
			
			this.incrementIteration();
		
			Individual tmp = generateNeighborSolution(s,maxDist);
			double improvement = s.absoluteImprovement(tmp);
			if (isFirstBetterThanSecond(tmp, s,this.isMaximize())) {
				s = tmp;
				if (isFirstBetterThanSecond(s, bs, this.isMaximize())) {
					bs= s;
				}
			} else {
				double delta=-improvement;
				if(this.isMaximize())
					delta=improvement;
				if (Math.random() < Math.exp(delta/ temperature)) {
					s = tmp;
				}
			}
		
			
			
			temperature = updateTemperature(temperature, this.getIterations());
			
			currentEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
			this.updateLocalEvaluations();
			
			System.out.println(this.printStatistics());
			  
		} while (!isCooled(temperature) && getFinalConditions().verify(configuration));
		
		
		
		
		bestSolution=bs;
		
		
	}




	private double setInitialTemperature(Individual s) {
		double sum = 0d;
		Individual sol = s.generateRandomSolution();
		for (int i = 0; i < nTrials; i++){
			Individual st=generateNeighborSolution(sol,maxDist);
			sum += Math.abs(st.evaluate()- sol.evaluate());}
		
		return -(sum / nTrials) / Math.log(logValue);
	}


	private double updateTemperature(double temperature, int iterations) {
		return as.updateTemperature(temperature, iterations);
	}


	private boolean isCooled(double temperature) {
		return as.isCooled(temperature);
	}
	
	private Individual generateNeighborSolution(Individual s, int maxDist){

		RandomGenerator generator = this.configuration.getRandomGenerator();

		Individual out = (Individual)s.partialClone();
		
		int numG=out.getGenes().length;
		
		
		int nd = 1 + generator.nextInt(maxDist);
		
		int i=0;	
		while (i < nd){
			int rand=generator.nextInt(numG);
			Random rr=new Random();
			
			Gene gene=(Gene)(out.getGenes()[rand]);
			int iter=this.configuration.getPerformance().getNumberOfIterations()+1;
			
				gene.setToRandomValue(generator);
			    i++;
			}
		
		return out;
	}
	

	
	public double getLogValue() {
		return logValue;
	}


	public void setLogValue(double logValue) {
		this.logValue = logValue;
	}


	public int getMaxDist() {
		return maxDist;
	}


	public void setMaxDist(int maxDist) {
		this.maxDist = maxDist;
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
