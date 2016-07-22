/*
 * MemeticAlgorithmPerformance.java 
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

import java.util.HashMap;
import java.util.Iterator;



/**
 * This class allows to store performances of a memetic algorithm during its execution.
 * Stored information is: number of the fitness evaluations, number of iterations, the fitness value, the average fitness value of the population,
 * the number of times that the the best fitness value does not change, the time necessary to perform the algorithm, the time necessary to perform the fitness function, the performance specific for the problem defined by the user,
 * the minimum, maximum, average (on iterations) of the improvement provided by the local procedure, the number of improvements (computed on iterations) of the local procedure.
 */
public class MemeticAlgorithmPerformance extends AlgorithmPerformance{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5551786534098942892L;
	
	/**
	 * Stores the number of iterations of the local procedure integrated in the memetic algorithm. This information is useful during the checking of the termination criteria.
	 */
	protected int numberLocalIterations;
	/**
	 * Stores the number of evaluations of the local procedure integrated in the memetic algorithm. This information is useful during the checking of the termination criteria.
	 */
	protected int numberLocalEvaluations;
	
	/**
	 * Stores the minimum improvement provided by the local search procedure 
	 */
	protected double minLocalImprovement;
	/**
	 * Stores the maximum improvement provided by the local search procedure 
	 */
	protected double maxLocalImprovement;
	/**
	 * Stores the sum of the improvements provided by the local search procedure 
	 */
	protected double sumLocalImprovement;
	/**
	 * Stores the average of the improvements provided by the local search procedure
	 */
	protected double averageLocalImprovement;
	
	/**
	 * Stores the number of improvements provided by the local search procedure
	 */
	protected int numImprovements;
	

	
	/**
	 * Constructor
	 * 
	 * Allows to initialize information.
	 */
	public MemeticAlgorithmPerformance() {
		super();
		minLocalImprovement= Double.MAX_VALUE;
		maxLocalImprovement= Double.MIN_VALUE;
		
		sumLocalImprovement=0;
		averageLocalImprovement=-1;
		
		numImprovements=0;
		numberLocalIterations=0;
		numberLocalEvaluations=0;
		
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
		
		minLocalImprovement= Double.MAX_VALUE;
		maxLocalImprovement= Double.MIN_VALUE;
		sumLocalImprovement=0;
		averageLocalImprovement=-1;
		
		numImprovements=0;
		numberLocalIterations=0;
		numberLocalEvaluations=0;
	}
	
	
	/**
	 * Allows to set only the number of iterations and the number of evaluations the initial values
	 */
	public void runningReset(){
		numberLocalIterations=0;
		numberLocalEvaluations=0;
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
				
			
               if(!problemPerformances.isEmpty())
		       {
            	   Iterator it=problemPerformances.keySet().iterator();
				
				while(it.hasNext()){

					String ss=(String)it.next();
					s+= ss +  problemPerformances.get(ss) + "\n";

				}
		       }
		
		if(this.getMinLocalImporvement() != Double.MAX_VALUE)
			s+="The minor local improvement is: " + this.getMinLocalImporvement() + "\n";
		
		if(this.getMaxLocalImporvement() != Double.MIN_VALUE)
			s+="The major local improvement is: " + this.getMaxLocalImporvement() + "\n";
		
		if(this.getAverageLocalImporvement() != -1)
			s+="The average local improvement is: " + this.getAverageLocalImporvement() + "\n";
		
		s+="The number of local improvements is: " + this.getNumImprovements();
		
		
		
		s+="\n Individual \n" +individual.toString();
		
		if(individual.getApplicationData()!=null)
		   s+="\n" +individual.getApplicationData().toString();
		
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
		v.put("Fitness Time in ms",this.getTime());
		v.put("Fitness value",this.getFitnessValue());
		v.put("Average fitness",this.getAverageFitness());
			
		
		Iterator it=problemPerformances.keySet().iterator();
		
		while(it.hasNext()){
			String ss=(String)it.next();
			v.put(ss,problemPerformances.get(ss) );
		}
		       	
		
		
		if(this.getMinLocalImporvement() != Double.MAX_VALUE)
			v.put("Min local improv.",this.getMinLocalImporvement());
		
		if(this.getMaxLocalImporvement() != Double.MIN_VALUE)
			v.put("Max local improv.", this.getMaxLocalImporvement() );
		
		if(this.getAverageLocalImporvement() != -1)
			v.put("Avg local improv.",this.getAverageLocalImporvement() );
	
		v.put("Num improv.",this.getNumImprovements());
		
		return v;
		
	}
	
	
	/**
	 * Allows to compute the information about the improvements provided by the local procedure 
	 * 
	 * @param fitness1  the fitness value of the best individual before performing the local procedure
	 * @param fitness2  the fitness value of the best individual after performing the local procedure
	 */
	public void setLocalImprovement(double fitness1, double fitness2){
		
		//relative improvement
		//int improvement= (int) Math.round(Math.abs(fitness2-fitness1)/fitness2*100);
		
		//absolute improvement
		double improvement= Math.abs(fitness2-fitness1);
		
		if(improvement > maxLocalImprovement)
			maxLocalImprovement=improvement;
		
		if(improvement < minLocalImprovement)
			minLocalImprovement=improvement;
		
		sumLocalImprovement+=improvement;
		
	}
	
	
	
	public double getAverageLocalImporvement() {
		return averageLocalImprovement;
	}

	public void setAverageLocalImporvement() {
		if(this.numberOfIterations==0)
			this.averageLocalImprovement=sumLocalImprovement;
		else this.averageLocalImprovement = sumLocalImprovement/numberOfIterations;
	}

	public double getMinLocalImporvement() {
		return minLocalImprovement;
	}

	public double getMaxLocalImporvement() {
		return maxLocalImprovement;
	}


    public int getNumImprovements() {
		return numImprovements;
	}

	public void incrementNumImprovements(){
		numImprovements++;
	}

	public void incrementNumLocalIterations(){
		this.numberLocalIterations++;
	}
	
	public void incrementNumLocalEvaluations(){
		this.numberLocalEvaluations++;
	}


	public int getNumberLocalIterations() {
		return numberLocalIterations;
	}

	
	public void setNumberLocalIterations(int numberLocalIterations) {
		this.numberLocalIterations = numberLocalIterations;
	}

	
	public int getNumberLocalEvaluations() {
		return numberLocalEvaluations;
	}

	public void setNumberLocalEvaluations(int numberLocalEvaluations) {
		this.numberLocalEvaluations = numberLocalEvaluations;
	}






	
	
	
	
}
