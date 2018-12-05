/*
 * AlgorithmPerformance.java 
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


package jMeme.performances;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jgap.IChromosome;
import org.jgap.Population;

import jMeme.core.Problem;
import jMeme.core.individuals.Individual;


/**
 * This class allows to store performances of an algorithm during its execution.
 * Stored information is: number of the fitness evaluations, number of iterations, the fitness value, the average fitness value of the population,
 * the number of times that the the best fitness value does not change, the time necessary to perform the algorithm, the time necessary to perform the fitness function, the performance specific for the problem defined by the user.
 */
public class AlgorithmPerformance implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6723460113110002102L;
	
	/**
	 * Stores the number of executed evaluations. This information is useful during the checking of the termination criteria.
	 */
	protected int numberOfFitnessEvaluations;
	
	/**
	 * Stores the fitness value of the best individual
	 */
	protected double fitnessValue;
	
	/**
	 * Stores the number of executed iterations. This information is useful during the checking of the termination criteria.
	 */
	protected int numberOfIterations;
	
	/**
	 * Stores the number of times that the the best fitness value does not change. This information is useful during the checking of the termination criteria.
	 */
	protected int timesConvergence;
	/**
	 * Stores the average of the fitness values of a population
	 */
	protected double averageFitness;
	/**
	 * Stores the time in ms necessary to perform the whole execution of the algorithm
	 */
	protected long time;
	
	/**
	 * Stores the performance measures specific for the problem at issue. These performance measures will be defined by the user in the class implementing the problem.
	 */
	protected Map problemPerformances;
	
	
    
	/**
	 * Stores the time in ms necessary to perform a fitness function
	 */
	protected long timeFitnesses;


	
	/**
	 * Stores the number of evaluations to generate the best individual
	 */
	protected int speed;
	
	
	/**
	 * Stores the best individual
	 */
	protected Individual individual;


	/**
	 * Constructor
	 * 
	 * Allows to initialize information.
	 */
	public AlgorithmPerformance() {
		numberOfFitnessEvaluations = 0;
		numberOfIterations=0;
		timesConvergence=0;
		
		timeFitnesses=0;
		time=0;
	
		averageFitness=-1;
		
		fitnessValue=-1;
		individual=null;
		
		speed=0;
		
		problemPerformances=new HashMap();
		
	}
	
	
	/**
	 * Allows to update performance including that defined by the user
	 * 
	 * @param newFitness  the fitness of the best individual of the new generation
	 * @param averageFitness  the average fitness value of the new generation
	 * @param problem  the problem at issue
	 */
	public void updatePerformances(double newFitness, double averageFitness, Problem problem){
		
	if(newFitness==this.getFitnessValue())
		this.incrementTimesConvergence();
	else this.setTimesConvergence(0);
	
	
	this.setAverageFitness(averageFitness);
	
	
	this.computeProblemPerformance(problem, this.getIndividual());
	
	

	}
	
	
	/**
	 * Allows to compute the average fitness value of a given population.
	 * 
	 * @param population  the population whose the average fitness values must be computed
	 * @return  a double value representing the average fitness value
	 */
	public static double computeAverageFitness(Population population) {
		double averageFitness = 0;
		for(Iterator<IChromosome> it = population.getChromosomes().iterator(); it.hasNext();) {
			averageFitness += ((IChromosome)it.next()).getFitnessValue();
		}
		
		averageFitness /= population.size();
			
		return averageFitness;
	}
	
	/**
	 * Allows to set the information to the initial values
	 */
	public void reset(){
		numberOfFitnessEvaluations=0;
		numberOfIterations=0;
		timesConvergence=0;

		problemPerformances= new HashMap();			
		
		averageFitness=-1;
		time=0;
		
	
		
        timeFitnesses=0;
		
		fitnessValue=-1;
		individual=null;
		
		speed=0;
	}

	
	/**
	 * @return  string representation of the performance of the executed algorithm
	 */
	public String toString(){
		
		String s="The number of Iterations is: " + this.numberOfIterations + "\n"
		+ "The number of Evaluations is: " + this.numberOfFitnessEvaluations + "\n"
			+ "Speed: " + this.speed + "\n"
		+ "The time execution is: " +  this.time + "\n"
		+ "The fitness time is: " +  this.timeFitnesses + "\n"
		+ "The fitness value is: " +  this.fitnessValue + "\n";
		
		if(this.averageFitness!=-1)
		s+= "The average fitness value is: " +  this.averageFitness + "\n";
		
		
	if(!problemPerformances.isEmpty()){
       Iterator it=problemPerformances.keySet().iterator();
		
		while(it.hasNext()){

			String ss=(String)it.next();
			s+= ss +  problemPerformances.get(ss) + "\n";

		}
	}
	
	
		s+="\n Individual \n" +individual.toString();
		
		if(individual.getApplicationData()!=null)
		   s+="\n Individual application data \n" +individual.getApplicationData().toString();
		
		return s;
		
	}
	
	/**
	 * @return  a map of the performance of the executed algorithm
	 */
	public HashMap toMap(){
		HashMap v=new HashMap();
		
		v.put("Number of iterations",this.getNumberOfIterations());
		v.put("Number of fitness evaluations",this.getNumberOfFitnessEvaluations());
		v.put("Speed", this.getSpeed());
		v.put("Time in ms",this.getTime());
		v.put("Fitness Time in ms",this.getTimeFitnesses());
		v.put("Fitness value",this.getFitnessValue());
		v.put("Average fitness",this.getAverageFitness());
			
		
		Iterator it=problemPerformances.keySet().iterator();
		
		while(it.hasNext()){
			String ss=(String)it.next();
			v.put(ss,problemPerformances.get(ss) );
		}
		       
		return v;
		
	}
	
	
	
	public void incrementTimeFitnesses(double d){
     	timeFitnesses+=d;
    }
	

	
	
	public int getTimesConvergence() {
		return timesConvergence;
	}


	public void setTimesConvergence(int timesConvergence) {
		this.timesConvergence = timesConvergence;
	}

	
	public int getNumberOfFitnessEvaluations() {
		return numberOfFitnessEvaluations;
	}
	

	public void setNumberOfFitnessEvaluations(int numberOfFitnessEvaluations) {
		this.numberOfFitnessEvaluations = numberOfFitnessEvaluations;
	}
	
	public void incrementNumberOfFitnessEvaluations() {
		this.numberOfFitnessEvaluations++;
	}
	
	public void decrementNumberOfFitnessEvaluations() {
		this.numberOfFitnessEvaluations--;
	}
	
	public void computeProblemPerformance(Problem p, Individual chromosome) {
		problemPerformances=p.computeProblemPerformance(chromosome);
		
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}

		

	public double getAverageFitness() {
		return averageFitness;
	}


	public Individual getIndividual() {
		return individual;
	}
	


	public double getFitnessValue() {
		return fitnessValue;
	}


	public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}


	public void setIndividual(Individual ind) {
		this.individual=ind;
		this.fitnessValue = ind.getFitnessValue();
	}

	public void incrementNumberOfIterations(){
		this.numberOfIterations++;
	}
	
	public void incrementTimesConvergence(){
		this.timesConvergence++;
	}


	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	




	public long getTimeFitnesses() {
	return timeFitnesses;
}







	public Map getProblemPerformances() {
		return problemPerformances;
	}



	public void setProblemPerformances(Map problemPerformances) {
		this.problemPerformances = problemPerformances;
	}



	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}



	public void setTimeFitnesses(long timeFitnesses) {
		this.timeFitnesses = timeFitnesses;
	}



	public void setAverageFitness(double averageFitness) {
		this.averageFitness = averageFitness;
	}






	
	
	
}
