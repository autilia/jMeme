/*
 * DE_Optimizer.java 
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

package jMeme.globalSearchAlgorithms.de;

import java.util.Random;

import org.jgap.InvalidConfigurationException;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.Problem;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.DEParameters;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;
import jMeme.utils.JGapExtension;



/**
 * This class implements the evolution performed during one iteration of Differential Evolution (DE). The DE algorithm is a population based algorithm like genetic algorithms using the similar operators;
* crossover, mutation and selection. The main difference in constructing better solutions is that genetic
* algorithms rely on crossover while DE relies on mutation operation. This main operation is based on the
* differences of randomly sampled pairs of solutions in the population.
* The algorithm uses mutation operation as a search mechanism and selection operation to direct the
* search toward the prospective regions in the search space. The DE algorithm also uses a non-uniform
* crossover that can take child vector parameters from one parent more often than it does from others. By
* using the components of the existing population members to construct trial vectors, the recombination
* (crossover) operator efficiently shuffles information about successful combinations, enabling the search for a
* better solution space. These are different strategies of DE denoted as DE/x/y where x represents a string denoting the vector to be perturbed and y is the number of difference
* vectors considered for perturbation of x. The crossover used in all strategies is the binomial.
* 
* For more information, see the works:
* [1] Storn, Rainer, and Kenneth Price. "Differential evolution–a simple and efficient heuristic for global optimization over continuous spaces." Journal of global optimization 11.4 (1997): 341-359.
* [2] R. Storn, "On the usage of differential evolution for function optimization," Fuzzy Information Processing Society, 1996. NAFIPS., 1996 Biennial Conference of the North American, Berkeley, CA, 1996, pp. 519-523.
 */
public class DE_Optimizer extends GlobalSearchOptimizer {


	
	/**
	 * Stores the scaling factor F
	 */
	private double F;  
	/**
	 * Stores the crossover constant
	 */
	private double CR;  
	/**
	 * Stores the greedness of the scheme used by the DE
	 */
	private double Lambda;
    /**
	 * Stores a string denoting the vector to be perturbed. It can be DEParameters.xTypeBest, DEParameters.xTypeRand, DEParameters.xTypeRandBest
	 */
    private String xType;
    /**
     * Stores the number of difference vectors considered for perturbation. It can be DEParameters.yType1 or DEParameters.yType2.
     */
	public static int yType;
	

    /**
	 * Stores the fitness value of the current best individual
	 */
	protected double currentBestFitness;
	/**
	 * Stores the index in the population of the current best individual
	 */
	protected int indexCurrentBest;
	
	
	/**
	 * Stores the dimension of the problem, i.e., the number of individual components
	 */
	public int dimension;
	
	
	/**
	 * Stores the features of the used fitness function
	 */
	protected FitnessFeatures fitnessFeatures;
	
	
	
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a DE optimizer
	 */
	public DE_Optimizer(GlobalSearchAlgorithmConfiguration conf) {
		configuration=conf;
	  F=((DEParameters)conf.getGlobalComponent().getParameters()).getF();
	  Lambda=((DEParameters)conf.getGlobalComponent().getParameters()).getLambda();
	  CR=((DEParameters)conf.getGlobalComponent().getParameters()).getCR();
	  xType=((DEParameters)conf.getGlobalComponent().getParameters()).getxType();
	  yType=((DEParameters)conf.getGlobalComponent().getParameters()).getyType();
		 this.fitnessFeatures=conf.getFitnessFeatures();
		 this.dimension=conf.getIndividualSize();
	
	}
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where DE is the global search optimizer
	 */
	public DE_Optimizer(MemeticAlgorithmConfiguration conf) {
		configuration=conf;
		  F=((DEParameters)conf.getGlobalComponent().getParameters()).getF();
		  Lambda=((DEParameters)conf.getGlobalComponent().getParameters()).getLambda();
		  CR=((DEParameters)conf.getGlobalComponent().getParameters()).getCR();
		  xType=((DEParameters)conf.getGlobalComponent().getParameters()).getxType();
		  yType=((DEParameters)conf.getGlobalComponent().getParameters()).getyType();
			 this.fitnessFeatures=conf.getFitnessFeatures();
			 this.dimension=conf.getIndividualSize();
		
		}
	
	

	public void setFitnessFeatures(FitnessFeatures fitnessFeatures) {
		this.fitnessFeatures = fitnessFeatures;
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
		    return new DE_Optimizer((MemeticAlgorithmConfiguration)configuration);
		
		 return new DE_Optimizer((GlobalSearchAlgorithmConfiguration)configuration);
		
	  }




	@Override
	/**
	 * Allows to execute an iteration of the DE
	 * 
	 * @param pop  the population to evolve
	 * @param a_conf  the configuration of the DE
	 */
public JPopulation execute(JPopulation a_pop, JConfiguration a_conf) {
   
	 
   
   int originalPopSize = a_conf.getPopulationSize();
   
   
  
   int dimension=a_conf.getChromosomeSize();
  
   Individual[] mutatedInds=new Individual[originalPopSize];
   
   this.determineCurrentBest(a_pop);
	
// For each individual...
		for( int i = 0; i < originalPopSize; i++ ) {
		
			mutatedInds[i]=mutation(i, xType, yType,a_pop);
			
			
		}
		
		
		Individual[] crossInds=new Individual[originalPopSize];
		
		// For each individual...
				for( int i = 0; i < originalPopSize; i++ ) {
				
					crossInds[i]=crossover(i, mutatedInds, a_pop);
							
				}
				
               Individual[] newInds=new Individual[originalPopSize];
				
				for( int i = 0; i < originalPopSize; i++ ) {
					
					 double originalFitness=a_pop.getIndividual(i).getFitnessValue();
					 double newFitness=crossInds[i].getFitnessValue();
							
						// Update 'best global' position
					    if( compareFitness(newFitness, originalFitness) )  // Minimize 
							newInds[i]=crossInds[i];
						else 
newInds[i]=(Individual)a_pop.getIndividual(i);
				}
				
				
				
				
				JPopulation newPop=null;
				try {
					newPop = new JPopulation(a_conf, newInds);
				} catch (InvalidConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				this.determineBest(newPop);
				
				// Increase number of generations.
			    // -------------------------------
			    a_conf.incrementGenerationNr();
				
		return newPop;
				
 }

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
		indexCurrentBest=0;
		
			for( int i = 1; i < originalPopSize; i++ ) {
				
				double fit=aPop.getIndividual(i).getFitnessValue();
				
				if(compareFitness(fit, currentBestFitness)) {  
					currentBest=((Individual)aPop.getIndividual(i)).clone();
					currentBestFitness=fit;
					indexCurrentBest=i;
				}
				
			}
			
	}
	
	@Override
	/**
	 * Allows to set variables of the algorithm to initial values
	 */
	public void reset() {
		this.setBestSolution(null);
	}
	 
 
 private Individual mutation(int i, String xtype, int ytype, JPopulation a_pop){
	 
	 Random rnd=new Random(System.currentTimeMillis());
		
	 int dim=a_pop.getConfiguration().getChromosomeSize();
	int dimension=a_pop.getPopulationSize();
	 
	 double[] genes=new double[dim];
		
		double[] genes0=null;
		if(xType.equals(DEParameters.xTypeRandBest)) 
			  genes0=JGapExtension.doubleChromosomeToArray(a_pop.getIndividual(i));
			
		double[] genes1=null;
		
		int first=-1;
		
		if(xType.equals(DEParameters.xTypeRand)){
			int r1=i;
			while(r1==i)
	        r1=rnd.nextInt(dimension);
			
			 genes1=JGapExtension.doubleChromosomeToArray(a_pop.getIndividual(r1));
		
			 first=r1;
		}
		else if(xType.equals(DEParameters.xTypeBest) || xType.equals(DEParameters.xTypeRandBest)) {
			 genes1=JGapExtension.doubleChromosomeToArray(currentBest);
             first=indexCurrentBest;
		}
		
		int r2=i;
		while(r2==i || r2==first)
        r2=rnd.nextInt(dimension);
		
		
		int r3=i;
		while(r3==i || r3==first || r3==r2)
        r3=rnd.nextInt(dimension);
		
		double[] genes2=JGapExtension.doubleChromosomeToArray(a_pop.getIndividual(r2));
		double[] genes3=JGapExtension.doubleChromosomeToArray(a_pop.getIndividual(r3));
	
		double[] genes4=null;
		double[] genes5=null;
		
		if(ytype==DEParameters.yType2){
			
			int r4=i;
			while(r4==i || r4==first || r4==r2 || r4==r3)
	        r4=rnd.nextInt(dimension);
			
			
			int r5=i;
			while(r5==i || r5==first || r5==r2 || r5==r3 || r5==r4)
	        r5=rnd.nextInt(dimension);
			
			genes4=JGapExtension.doubleChromosomeToArray(a_pop.getIndividual(r4));
			genes5=JGapExtension.doubleChromosomeToArray(a_pop.getIndividual(r5));
		
		}
		
		for(int j=0;j<dim; j++){
			
			if(ytype==DEParameters.yType1)
				if(xType.equals(DEParameters.xTypeRand) || xType.equals(DEParameters.xTypeBest))
			         genes[j]= genes1[j]+ this.F*(genes2[j]-genes3[j]);
				else if(xType.equals(DEParameters.xTypeRandBest))
						genes[j]=genes0[j]- this.Lambda*(genes1[j]-genes0[j])+this.F*(genes2[j]-genes3[j]);
			
			if(ytype==DEParameters.yType2)
				if(xType.equals(DEParameters.xTypeRand) || xType.equals(DEParameters.xTypeBest))
				   genes[j]= genes1[j]+ this.F*(genes2[j]+genes3[j]-genes4[j]-genes5[j]);
				else if(xType.equals(DEParameters.xTypeRandBest))
					genes[j]=genes0[j]- this.Lambda*(genes1[j]-genes0[j])+this.F*(genes2[j]+genes3[j]-genes4[j]-genes5[j]);

		}
		
			try {
				return new Individual((JConfiguration)a_pop.getConfiguration(), genes);
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
 }
 
 
 private Individual crossover(int i, Individual[] mutatedInds, JPopulation a_pop){
	 
	 int dimension=a_pop.getConfiguration().getChromosomeSize();
	 
	 Random rnd=new Random(System.currentTimeMillis());
		
	 
	 double[] genes=new double[dimension];
		
		int random=rnd.nextInt(dimension);
		
		double[] mutatedGenes=JGapExtension.doubleChromosomeToArray(mutatedInds[i]);
		double[]originalGenes=JGapExtension.doubleChromosomeToArray(a_pop.getIndividual(i));

		
		for(int j=0;j<dimension; j++){
			
			double rd=rnd.nextDouble();
			if(rd<=this.CR || j==random)
				genes[j]= mutatedGenes[j];
			else 
				genes[j]= originalGenes[j];
		}
		try {
			return new Individual((JConfiguration)a_pop.getConfiguration(), genes);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
 }
 
	
 
 private double controlF(double f){
	 
	 if(f>=0 && f<=2)
		 return f;
	 
	 return DEParameters.DEFAULT_F;
	 
 }





}
