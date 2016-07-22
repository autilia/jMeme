/*
 * LocalOptimizationAlgorithmStudy.java 
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


package jMeme.localSearchAlgorithms;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import jMeme.core.AlgorithmComponent;
import jMeme.core.JConfiguration;
import jMeme.core.Problem;
import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.competent.CompetentStudyUtils;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.statisticalTests.Rank;
import jMeme.utils.ArrayUtil;
import jMeme.utils.FileUtil;


/**
 * This class implements the study of several local search algorithms on the same problem.
 * The class allows to build and save the optimal configuration (among those specified) to a file.
 */
public class LocalOptimizationAlgorithmStudy {

	
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
	 * Stores the termination criteria used to stop the local search algorithms involved in the study
	 */
	private AlgorithmFinalConditions termination;
	/**
	 * Stores the set of the local search algorithms involved in the study
	 */
	private AlgorithmComponent[] localComponents;
	/**
	 * Stores the file name where the results will be written
	 */
	private String nameResultFile;
	
	/**
	 * Stores the number of runs to be executed for each specified global search algorithm during the study
	 */
	private int numRuns;
	
	
	/**
	 * Stores the index (within the set of local search algorithms) of the best local search algorithm obtained after performing the study
	 */
	public int indexBestLocal;
	

	/**
	 * Stores the best configuration for the problem at issue obtained at the end of the study 
	 */
	private LocalSearchAlgorithmConfiguration bestConf;

	
	 /**
     * Constructor 
     * 
     * @param problems  the set of problem instances 
     * @param fitness  the features of the fitness function related to the problem to solve
     * @param finC  the termination criteria for all specified local search algorithms executed during the study
     * @param lcc  the set of specified local search algorithms to be executed during the study
     */
	public LocalOptimizationAlgorithmStudy(Problem[] problems, FitnessFeatures fitness, AlgorithmFinalConditions finC, AlgorithmComponent[] lcc){
		problemInstances=problems;
		fitnessFeatures=fitness;
		
		termination=finC;
		localComponents=lcc;
		
		numRuns=LocalOptimizationAlgorithmStudy.DEFAULT_NUM_RUNS;
	    nameResultFile=LocalOptimizationAlgorithmStudy.DEFAULT_NAME_FILE_RESULT;
	}
	
	 /**
     * Constructor 
     * 
     * @param problems  the set of problem instances 
     * @param fitness  the features of the fitness function related to the problem to solve
     * @param finC  the termination criteria for all specified local search algorithms executed during the study
     * @param lcc  the set of specified local search algorithms to be executed during the study
     * @param nR  the number of runs to be executed for each local search algorithm during the study
     */
	public LocalOptimizationAlgorithmStudy(Problem[] problems, FitnessFeatures fitness, AlgorithmFinalConditions finC, AlgorithmComponent[] lcc, int nR){
		problemInstances=problems;
		fitnessFeatures=fitness;
		
		termination=finC;
		localComponents=lcc;
		
		numRuns=nR;
	    nameResultFile=LocalOptimizationAlgorithmStudy.DEFAULT_NAME_FILE_RESULT;
	}
	
	/**
	 * Allows to execute the study and obtain all results written in three files .xls
	 */
	public void execute(){
		int numP= problemInstances.length;
		int numL=localComponents.length;
		
		double[][]  fitnessConfiguration=new double[numP][numL];
		String[] configurationNames=new String[numL];
		int indexConfig;
	
		for(int p=0; p<numP; p++){
		
		Problem prob=problemInstances[p];
		String rootNameFile="Tests\\LocalSearch"+nameResultFile+prob.getId();
		String result="Start local searches.....\n";
		
		indexConfig=0;
		
		for(int i=0; i<numL; i++){
			AlgorithmComponent lc=localComponents[i];
			
			
			
				JConfiguration.reset();
				LocalSearchAlgorithmConfiguration conf=
						new LocalSearchAlgorithmConfiguration(fitnessFeatures, termination, lc);				
			
				LocalOptimizationAlgorithm algorithm =new LocalOptimizationAlgorithm(prob,conf);
				  
				String nameFile=rootNameFile+"_"+lc.getId();
				File fileTxt=new File(nameFile + ".txt");
				Vector results= new Vector();
				configurationNames[indexConfig]=lc.getId();
				
				result+="Run configuration " + lc.getId() +"\n";
				
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
			 		
			 		System.out.println("Test terminated on configuration " + lc.getId() + "\n");
				
				
			}	
	  
	  }
	
	   double[][] ranks=Rank.computeRanks(fitnessConfiguration, fitnessFeatures.isMaximize());
		double[] averageRankXConfig=Rank.computeAverageRank(ranks );
	     
	    
	    CompetentStudyUtils.printPhaseResult("Tests\\LocalSearches", this.problemInstances, configurationNames, fitnessConfiguration);
	    CompetentStudyUtils.printPhaseResult("Tests\\LocalSearchesRank", this.problemInstances, configurationNames, ranks);
	    CompetentStudyUtils.printAverageResult("Tests\\LocalSearchesAverageRank", configurationNames, averageRankXConfig);
	    
	    //search the best configuration
	    Vector indexBetter=new Vector();
	    double best=ArrayUtil.min(averageRankXConfig);
	    
	    for(int i=0; i<numL; i++)
	    	if(averageRankXConfig[i]==best)
	    		indexBetter.add(i);
	    
        int numBest=indexBetter.size();
	    
	    if(numBest==1)
	    	indexBestLocal=(Integer)indexBetter.get(0);
	    else {
	    	Random rand = new Random();
	    	int random=rand.nextInt(numBest);
	    	indexBestLocal=(Integer)indexBetter.get(random);
	    }
	    
	    System.out.println("End local searches...");
	}
	
	
	
	private void printBestConfiguration(){
    	AlgorithmComponent lc=localComponents[indexBestLocal];
		
			
		JConfiguration.reset();
    	bestConf=new LocalSearchAlgorithmConfiguration(fitnessFeatures, termination, lc);				
		
    	//print the best configuration
    	
    	System.out.println("Local Component: " + lc.getId());
    	System.out.println("Local Component features:\n " + lc.getParameters());
    		
    	
    	FileUtil.writeFile(new File("BestLocalConfiguration.txt"), bestConf.toString());
    	
    	//save the configuration object
    	bestConf.save("localConfiguration.ser");
    }
	
}
