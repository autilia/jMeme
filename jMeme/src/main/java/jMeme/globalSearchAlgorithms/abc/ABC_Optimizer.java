/*
 * ABC_Optimizer.java 
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

package jMeme.globalSearchAlgorithms.abc;


import java.util.Random;

import org.jgap.InvalidConfigurationException;

import jMeme.core.JConfiguration;
import jMeme.core.JPopulation;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.ABCParameters;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;
     

/**
 * This class implements the evolution performed during one iteration of Artificial bee colony (ABC).
 * The model consists of three essential components: food sources, employed foragers, and unemployed foragers, and
 * defines two leading modes of the honey bee colony behaviour: recruitment to a
 * food source and abandonment of a source.
 * For more details, see the work:
 * D. Dervis Karaboga, An Idea Based On Honey Bee Swarm for Numerical Optimization, Technical Report-TR06,Erciyes University, Engineering Faculty, Computer Engineering Department 2005.
 */
public class ABC_Optimizer extends GlobalSearchOptimizer {

	
	private static final long serialVersionUID = -3174410066704526113L;


	/**
	 * Stores the number of trials for releasing a food source
	 */
    private int limit;     
   
   
	  /**
	 *Stores the individual with the current best fitness value  
	 */
		protected Individual currentBest;
		/**
		 * Stores the fitness value of the current best individual
		 */
		protected double currentBestFitness;
	
	/**
	 * Stores the featurs of the fitness function
	 */
	protected FitnessFeatures fitnessFeatures;
	
	/**
	 * Stores the number of trials a food source is not improved
	 */
	protected int[] aCounter;
	
	
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run an ABC optimizer
	 */
	public ABC_Optimizer(GlobalSearchAlgorithmConfiguration conf) {
		configuration=conf;
		this.fitnessFeatures=conf.getFitnessFeatures();
		this.limit=((ABCParameters)conf.getGlobalComponent().getParameters()).getLimit();
		int popSize=((ABCParameters)conf.getGlobalComponent().getParameters()).getPopulationSize();
		aCounter=new int[popSize];
		for(int i=0; i<popSize; i++)
			aCounter[i]=0;
		this.bestSolution = null;
	}


	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where ABC is the global search optimizer
	 */
	public ABC_Optimizer(MemeticAlgorithmConfiguration conf) {
		configuration=conf;
		this.fitnessFeatures=conf.getFitnessFeatures();
		this.limit=((ABCParameters)conf.getGlobalComponent().getParameters()).getLimit();
		int popSize=((ABCParameters)conf.getGlobalComponent().getParameters()).getPopulationSize();
		aCounter=new int[popSize];
		for(int i=0; i<popSize; i++)
			aCounter[i]=0;
		this.bestSolution = null;
	}
   
	

	@Override
	/**
	 * Allows to execute an iteration of the ABC
	 * 
	 * @param pop  the population to evolve
	 * @param a_conf  the configuration of the ABC
	 */
	public JPopulation execute(JPopulation aPop, JConfiguration aConf) {
		
		
		this.SendEmployedBees(aPop);
		this.SendOnlookerBees(aPop);
		this.determineBest(aPop);
		this.SendScoutBees(aPop);
		
		// Increase number of generations.
	    // -------------------------------
	    aConf.incrementGenerationNr();
	    
	    return aPop;
	}
  
   
    public void determineBest(JPopulation aPop){
		 
    	determineCurrentBest(aPop);
    	
		// Update 'best global' position
	    if( bestSolution==null ||compareFitness(currentBestFitness, bestFitness) ) { // Minimize 
			bestSolution=currentBest.clone();
			bestFitness=currentBestFitness;
		}
		
			
}
    
 
    public void determineCurrentBest(JPopulation aPop){
		 int originalPopSize = aPop.getPopulationSize();
		 
		 currentBest=(Individual)aPop.getIndividual(0);
		 currentBestFitness=aPop.getIndividual(0).getFitnessValue();
		
			for( int i = 1; i < originalPopSize; i++ ) {
				
				double fit=aPop.getIndividual(i).getFitnessValue();
				
				if(compareFitness(fit, currentBestFitness)) {  
					currentBest=((Individual)aPop.getIndividual(i)).clone();
					currentBestFitness=fit;
				}
				
			}
			
	}

 
    
      
    private void SendEmployedBees(JPopulation aPop)
    {
    	 int originalPopSize = aPop.getPopulationSize();
    	 
    	// Population neFoodSources=new Population(aPop.getConfiguration(), originalPopSize);
 		Random r=new Random();
        
        for (int i=0;i<originalPopSize;i++)
	{
           Individual ind=((Individual)aPop.getIndividual(i)).partialClone();
           
            
	    /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
           int  neighbour = r.nextInt( originalPopSize);
           /*Randomly selected solution must be different from the solution i*/    
           while(neighbour==i)
        	   neighbour = r.nextInt( originalPopSize);
           int dimension=configuration.getIndividualSize();
           int index=r.nextInt(dimension);
           
           mutate(ind,(Individual)aPop.getIndividual(neighbour), index);
           
           
          double fit=ind.evaluate();
	   
            
	    /*a greedy selection is applied between the current solution i and its mutant*/
	    if ((compareFitness(fit, ((Individual)aPop.getIndividual(i)).getFitnessValue())))
	    {
                /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
                aCounter[i]=0;
                aPop.setIndividual(ind,i);
	    }
            else
	    {   /*if the solution i can not be improved, increase its trial counter*/
                aCounter[i]=aCounter[i]+1;
	    }
        }

    /*end of employed bee phase*/
    }
    
    
    private void mutate(Individual ind, Individual neighbour, int index){
    	double xij=((Double)ind.getGene(index).getAllele());
    	double kij=((Double)neighbour.getGene(index).getAllele());
    	
    	Random r=new Random();
    	
    	 double d=r.nextDouble()*2 -1;
    	 
    	 double newValue=xij + d*(xij-kij);
    	 
    	 ind.getIndividualComponent(index).setAllele(newValue);
    }

    
    
    /* A food source is chosen with the probability which is proportioal to its quality*/
    /*Different schemes can be used to calculate the probability values*/
    /*For example prob(i)=fitness(i)/sum(fitness)*/
    /*or in a way used in the metot below prob(i)=a*fitness(i)/max(fitness)+b*/
    /*probability values are calculated by using fitness values and normalized by dividing maximum fitness value*/
    private double[] CalculateProbabilities(JPopulation aPop)
    {
    	int originalPopSize=aPop.getPopulationSize();
    	double[] prob=new double[originalPopSize];
    	
    	double sum=0;
    	 for (int i=0;i<originalPopSize;i++)
    		{
    	           Individual ind=((Individual)aPop.getIndividual(i));
    	           if(fitnessFeatures.isMaximize() )
    	             sum+=ind.getFitnessValue();
    	           else sum+=ind.getFitnessValue();
    		}
    	 
    	 for (int i=0;i<originalPopSize;i++)
    	 {
    		 Individual ind=((Individual)aPop.getIndividual(i));
    		 if(fitnessFeatures.isMaximize() )
    		 prob[i]=ind.getFitnessValue()/sum;
    		 else  prob[i]=ind.getFitnessValue()/sum;
    	 }
    	 
    	 return prob;
       
        //prob[i]=(0.9*(fitness[i]/maxfit))+0.1;
        

    }
    
    /* here */
    private void SendOnlookerBees(JPopulation aPop)
    {
     
    	int originalPopSize = aPop.getPopulationSize();
    	
    	double prob[]=this.CalculateProbabilities(aPop);
    	 
    	// Population neFoodSources=new Population(aPop.getConfiguration(), originalPopSize);
 		Random r=new Random();
        
        int cont=0;
        int i=0;
        
        while(cont<originalPopSize)
	{
        
        	double rand=r.nextDouble();
        	
        	if(rand<prob[i])
        	
        	{
        	
        	cont++;
        	
        	Individual ind=((Individual)aPop.getIndividual(i)).partialClone();
           
            
	    /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
           int  neighbour = r.nextInt( originalPopSize);
           /*Randomly selected solution must be different from the solution i*/    
           while(neighbour==i)
        	   neighbour = r.nextInt( originalPopSize);
           int dimension=configuration.getIndividualSize();
           int index=r.nextInt(dimension);
           
           mutate(ind,(Individual)aPop.getIndividual(neighbour), index);
           
           
          double fit=ind.evaluate();
	   
            
	    /*a greedy selection is applied between the current solution i and its mutant*/
	    if ((compareFitness(fit, ((Individual)aPop.getIndividual(i)).getFitnessValue())))
	    {
                /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
                aCounter[i]=0;
                aPop.setIndividual(ind, i);
	    }
            else
	    {   /*if the solution i can not be improved, increase its trial counter*/
                aCounter[i]=aCounter[i]+1;
	    }
        
	}else{
		i++;
		if(i==originalPopSize)
			i=0;
	}
    }

	/*end of onlooker bee phase     */
    }

    
    /*determine the food sources whose trial counter exceeds the "limit" value. In Basic ABC, only one scout is allowed to occur in each cycle*/
    private void SendScoutBees(JPopulation aPop)
    {
        int maxCounterIndex;
	maxCounterIndex=0;
	
	int originalPopSize=aPop.getPopulationSize();
        
        Random r = new Random();
        
	for (int i=1;i<originalPopSize;i++)
	{
            if (aCounter[i]>aCounter[maxCounterIndex])
                maxCounterIndex=i;
	}
	
	if(aCounter[maxCounterIndex]>=limit)
	{
		Individual ind=null;
		try {
			ind = (Individual)Individual.randomInitialIndividual((JConfiguration)aPop.getConfiguration());
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   aPop.setIndividual(ind,maxCounterIndex);
	}
    }

 
   
	@Override
	public int compareTo(Object a_other) {
		if (a_other == null) {
		      return 1;
		    }
		
		return getClass().getName().compareTo(a_other.getClass().getName());
	
	}

	

	@Override
	public void reset() {
		this.setBestSolution(null);
		   int size=this.getaCounter().length;
		   for(int i=0; i<size; i++)
				this.getaCounter()[i]=0;
	}
	
	@Override
	 public Object clone() {
		 if(configuration.isMemeticConfig())
		    return new ABC_Optimizer((MemeticAlgorithmConfiguration)configuration);
		
		 return new ABC_Optimizer((GlobalSearchAlgorithmConfiguration)configuration);
			
	 }


	


	public int[] getaCounter() {
		return aCounter;
	}



	public void setaCounter(int[] aCounter) {
		this.aCounter = aCounter;
	}

  



	public FitnessFeatures getFitnessFeatures() {
		return fitnessFeatures;
	}


	
	public void setFitnessFeatures(FitnessFeatures fitnessFeatures) {
		this.fitnessFeatures = fitnessFeatures;
	}






	
	
	 
}
