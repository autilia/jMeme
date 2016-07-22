/*
 * GlobalOptimizationAlgorithmStudy.java 
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

package jMeme.globalSearchAlgorithms;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import jMeme.core.AlgorithmComponent;
import jMeme.core.JConfiguration;
import jMeme.core.Problem;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.competent.CompetentStudyUtils;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.generationExecutor.GenerationExecutor;
import jMeme.design.generationExecutor.RandomGenerationExecutor;
import jMeme.design.optimizerParameters.GlobalSearchOptimizerParameters;
import jMeme.statisticalTests.Rank;
import jMeme.utils.ArrayUtil;
import jMeme.utils.FileUtil;



/**
 * This class implements the study of several global search algorithms on the same problem.
 * The class allows to build and save the optimal configuration (among those specified) to a file.
 */
public class GlobalOptimizationAlgorithmStudy {

	/**
	 * Constants representing the default number of runs executed for each specified algorithm during the study
	 */
	public static int DEFAULT_NUM_RUNS=10;
	/**
	 * Constants representing the default file name used to store the results of the study
	 */
	public static String DEFAULT_NAME_FILE_RESULT="ProblemName";
	
	
	/**
	 * Stores the set of problem instances involved in the study
	 */
	private Problem[] problemInstances;
	/**
	 * Sores the set of problem instances involved in the study
	 */
	private FitnessFeatures fitnessFeatures;
	/**
	 * Stores the population size of the global search algorithms involved in the study
	 */
	private int popSize;
	/**
	 * Stores the termination criteria used to stop the global search algorithms involved in the study
	 */
	private AlgorithmFinalConditions termination;
	/**
	 * Stores the set of the global search algorithms involved in the study
	 */
	private AlgorithmComponent[] globalComponents;
	/**
	 * Stores the file name where the results will be written
	 */
	private String nameResultFile;
	/**
	 * Stores the object used to create the initial population for the global search algorithms involved in the study
	 */
	private GenerationExecutor generator;
	
	/**
	 * Stores the number of runs to be executed for each specified global search algorithm during the study
	 */
	private int numRuns;
	
	
	/**
	 * Stores the index (within the set of global search algorithms) of the best global search algorithm obtained after performing the study
	 */
	public int indexBestGlobal;
	
	/**
	 * Stores the best configuration for the problem at issue obtained at the end of the study 
	 */
	private GlobalSearchAlgorithmConfiguration bestConf;

	
    /**
     * Constructor 
     * 
     * @param problems  the set of problem instances 
     * @param fitness  the features of the fitness function related to the problem to solve
     * @param popSize  the population size for all specified global search algorithms
     * @param finC  the termination criteria for all specified global search algorithms executed during the study
     * @param gcc  the set of specified global search algorithms to be executed during the study
     */
	public GlobalOptimizationAlgorithmStudy(Problem[] problems, FitnessFeatures fitness, int popSize, AlgorithmFinalConditions finC, AlgorithmComponent[] gcc){
		problemInstances=problems;
		fitnessFeatures=fitness;
		
		this.popSize=popSize;
		termination=finC;
		globalComponents=gcc;
		
		numRuns=GlobalOptimizationAlgorithmStudy.DEFAULT_NUM_RUNS;
	    nameResultFile=GlobalOptimizationAlgorithmStudy.DEFAULT_NAME_FILE_RESULT;
	    generator=new RandomGenerationExecutor();
	}
	
	
	 /**
     * Constructor 
     * 
     * @param problems  the set of problem instances 
     * @param fitness  the features of the fitness function related to the problem to solve
     * @param popSize  the population size for all specified global search algorithms
     * @param finC  the termination criteria for all specified global search algorithms executed during the study
     * @param gcc  the set of specified global search algorithms to be executed during the study
     * @param nR  the number of runs to be executed for each global search algorithm during the study
     */
	public GlobalOptimizationAlgorithmStudy(Problem[] problems, FitnessFeatures fitness, int popSize, AlgorithmFinalConditions finC, AlgorithmComponent[] gcc, int nR){
		problemInstances=problems;
		fitnessFeatures=fitness;
		
		this.popSize=popSize;
		termination=finC;
		globalComponents=gcc;
		
		numRuns=nR;
	    nameResultFile=GlobalOptimizationAlgorithmStudy.DEFAULT_NAME_FILE_RESULT;
	    generator=new RandomGenerationExecutor();
	}
	
	
	
	/**
	 * Allows to execute the study and obtain all results written in three files .xls
	 */
	public void execute(){
		int numP= problemInstances.length;
		int numG=globalComponents.length;
		
		double[][]  fitnessConfiguration=new double[numP][numG];
		String[] configurationNames=new String[numG];
		int indexConfig;
	
		for(int p=0; p<numP; p++){
		
		Problem prob=problemInstances[p];
		String rootNameFile="Tests\\GlobalSearch"+nameResultFile+prob.getId();
		String result="Start global searches.....\n";
		
		indexConfig=0;
		
		for(int i=0; i<numG; i++){
			AlgorithmComponent gc=globalComponents[i];
			((GlobalSearchOptimizerParameters)gc.getParameters()).setPopulationSize(popSize);
			
			
			
				JConfiguration.reset();
				
				GlobalSearchAlgorithmConfiguration conf=
						new GlobalSearchAlgorithmConfiguration(fitnessFeatures, termination, gc, generator);				
			
				GlobalOptimizationAlgorithm algorithm =new GlobalOptimizationAlgorithm(prob,conf);
				  
				String nameFile=rootNameFile+"_"+gc.getId();
				File fileTxt=new File(nameFile + ".txt");
				Vector results= new Vector();
				configurationNames[indexConfig]=gc.getId();
				
				result+="Run configuration " + gc.getId() +"\n";
				
				double sumfitnessRuns=0;
				for(int k=1; k<=numRuns;k++){
		        	 
		        		
						
						algorithm.reset();
					       
						
						algorithm.execute();

					  
						result+="--------Run number: " + (k) + "---------\n";
						result+=conf.getPerformance().toString();
						result+="\n\n";
						
						/*if(k==1)
							FileUtil.writeFile(fileTxt,result);
						else
							FileUtil.appendFile(fileTxt,result);
						*/
						HashMap map=conf.getPerformance().toMap();
						results.add(map);
						

						 System.out.println("best " + conf.getPerformance().getIndividual());

						
						System.out.println("Runs " + (k)+" executed");
					   sumfitnessRuns+=conf.getPerformance().getFitnessValue();
			         }
				
				
			         
			         fitnessConfiguration[p][indexConfig]=sumfitnessRuns/numRuns;
			         indexConfig++;
						
			         FileUtil.writeFile(fileTxt,result);
			         FileUtil.writeRunsToExcel(results, nameFile);
			 		
			 		System.out.println("Test terminated on configuration " + gc.getId() + "\n");
				
				
			}	
	  
	  }
	
	   double[][] ranks=Rank.computeRanks(fitnessConfiguration, fitnessFeatures.isMaximize());
		double[] averageRankXConfig=Rank.computeAverageRank(ranks );
	     
	    
	    CompetentStudyUtils.printPhaseResult("Tests\\GlobalSearches", this.problemInstances, configurationNames, fitnessConfiguration);
	    CompetentStudyUtils.printPhaseResult("Tests\\GlobalSearchesRank", this.problemInstances, configurationNames, ranks);
	    CompetentStudyUtils.printAverageResult("Tests\\GlobalSearchesAverageRank", configurationNames, averageRankXConfig);
	    
	    //search the best configuration
	    Vector indexBetter=new Vector();
	    double best=ArrayUtil.min(averageRankXConfig);
	    
	    for(int i=0; i<numG; i++)
	    	if(averageRankXConfig[i]==best)
	    		indexBetter.add(i);
	    
        int numBest=indexBetter.size();
	    
	    if(numBest==1)
	    	indexBestGlobal=(Integer)indexBetter.get(0);
	    else {
	    	Random rand = new Random();
	    	int random=rand.nextInt(numBest);
	    	indexBestGlobal=(Integer)indexBetter.get(random);
	    }
	    
	    System.out.println("End global searches...");
	}
	
	
	
	private void printBestConfiguration(){
    	AlgorithmComponent gc=globalComponents[indexBestGlobal];
		((GlobalSearchOptimizerParameters)gc.getParameters()).setPopulationSize(popSize);
	
			
		JConfiguration.reset();
    	bestConf=new GlobalSearchAlgorithmConfiguration(fitnessFeatures, termination, gc);				
		
    	//print configuration
    	
    	System.out.println("Global Component: " + gc.getId());
    	System.out.println("Global Component features:\n " + gc.getParameters());
    		
   
    	FileUtil.writeFile(new File("BestGlobalConfiguration.txt"), bestConf.toString());
    	
    	//save the best configuration
    	bestConf.save("globalConfiguration.ser");
    }
	
}
