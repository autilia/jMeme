/*
 * CompetentStudy.java 
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


package jMeme.design.competent;

import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import jMeme.core.AlgorithmComponent;
import jMeme.core.JConfiguration;
import jMeme.core.Problem;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.generationExecutor.GenerationExecutor;
import jMeme.design.generationExecutor.RandomGenerationExecutor;
import jMeme.design.localIntegration.LocalFrequency;
import jMeme.design.localIntegration.LocalIntensity;
import jMeme.design.localIntegration.LocalOptimizerExecutor;
import jMeme.design.optimizerParameters.GlobalSearchOptimizerParameters;
import jMeme.memeticAlgorithms.MemeticOptimizationAlgorithm;
import jMeme.statisticalTests.Rank;
import jMeme.utils.ArrayUtil;
import jMeme.utils.FileUtil;


/**
 * This class implements the competent study as described in the work:
 * Q. H. Nguyen, Y. S. Ong and N. Krasnogor, "A study on the design issues of Memetic Algorithm," 2007 IEEE Congress on Evolutionary Computation, Singapore, 2007, pp. 2390-2397.
 * doi: 10.1109/CEC.2007.4424770.
 * The class allows to build and save the optimal configuration (among those studied) to a file.
 */
public class CompetentStudy {

	/**
	 * Constants representing the default number of runs executed for each specified algorithm during the competent studty 
	 */
	public static int DEFAULT_NUM_RUNS=10;
	
	/**
	 * Stores the set of problem instances involved in the competent study
	 */
	private Problem[] problemInstances;
	/**
	 * Stores the features of the fitness function related to the problem to solve during the competent study
	 */
	private FitnessFeatures fitnessFeatures;
	/**
	 * Stores the population size of the global search algorithms involved in the competent study
	 */
	private int popSize;
	/**
	 * Stores the object used to create the initial population for the global search algorithms involved in the competent study
	 */
	private GenerationExecutor generator;
	/**
	 * Stores the termination criteria used to stop the memetic algorithms built during the competent study
	 */
	private AlgorithmFinalConditions termination;
	/**
	 * Stores the set of the global search algorithms involved in the competent study
	 */
	private AlgorithmComponent[] globalComponents;
	/**
	 * Stores the set of the local search algorithms involved in the competent study
	 */
	private AlgorithmComponent[] localComponents;
	/**
	 * Stores the set of local intensities to be used during the competent study
	 */
	private int[] localIntensities;
	/**
	 * Stores the set of local frequencies to be used during the competent study
	 */
	private double[] localFrequencies;
	/**
	 * Stores the set of local schemes to be used as individual selection schemes during the competent study
	 */
	private String[] localSchemes;
	
	/**
	 * Stores the file name where the results will be written
	 */
	private String nameResultFile;
	
	
	/**
	 * Stores the number of runs to be executed for each specified algorithm during the competent study
	 */
	private int numRuns;
	
	
	/**
	 * Stores the index (within the set of local search algorithms) of the best local search algorithm obtained after the first step of the competent study
	 */
	private int indexBestLocalComp;
	/**
	 * Stores the index (within the set of global search algorithms) of the best global search algorithm obtained after the first step of the competent study
	 */
	private int indexBestGlobalComp;
	/**
	 * Stores the index (within the set of local frequencies) of the best local frequency obtained after the second step of the competent study
	 */
	private int indexBestFrequency;
	/**
	 * Stores the index (within the set of local intensities) of the best local intensity obtained after the second step of the competent study
	 */
	private int indexBestIntensity;
	/**
	 * Stores the index (within the set of local schemes) of the best local scheme obtained after the third step of the competent study
	 */
	private int indexBestScheme;
	
	/**
	 * Stores the best memetic configuration obtained at the end of the competent study 
	 */
	private MemeticAlgorithmConfiguration bestConf;
	
    /**
     * Constructor 
     * 
     * @param p  the set of problem instances 
     * @param fitnessF  the features of the fitness function related to the problem to solve
     * @param popSize  the population size for all specified global search algorithms
     * @param fc  the termination criteria for the memetic algorithms built during the competent study
     * @param gcs  the set of specified global search algorithms to be used during the competent study
     * @param lcs  the set of specified local search algorithms to be used during the competent study
     * @param li  the set of the local intensities to be used during the competent study
     * @param lf  the set of the local frequencies to be used during the competent study
     * @param schemes  the set of the local schemes to be used during the competent study
     */
	public CompetentStudy(Problem[] p, FitnessFeatures fitnessF,int popSize, AlgorithmFinalConditions fc, AlgorithmComponent[] gcs,AlgorithmComponent[] lcs, int[] li, double[] lf, String[] schemes ){
		problemInstances=p;
		fitnessFeatures=fitnessF;
		
		this.popSize=popSize;
		termination=fc;
		globalComponents=gcs;
		localComponents=lcs;
		localIntensities=li;
		localFrequencies=lf;
		localSchemes=schemes;
		numRuns=CompetentStudy.DEFAULT_NUM_RUNS;
		nameResultFile="";
		 generator=new RandomGenerationExecutor();
	}
	
	/**
     * Constructor 
     * 
     * @param p  the set of problem instances 
     * @param fitnessF  the features of the fitness function related to the problem to solve
     * @param popSize  the population size for all specified global search algorithms
     * @param fc  the termination criteria for the memetic algorithms built during the competent study
     * @param gcs  the set of specified global search algorithms to be used during the competent study
     * @param lcs  the set of specified local search algorithms to be used during the competent study
     * @param li  the set of the local intensities to be used during the competent study
     * @param lf  the set of the local frequencies to be used during the competent study
     * @param schemes  the set of the local schemes to be used during the competent study
     * @param nR  the number of runs to be executed for each memetic algorithm built during the competent study
     */
	public CompetentStudy(Problem[] p, FitnessFeatures fitnessF,int popSize, AlgorithmFinalConditions fc,AlgorithmComponent[] gcs,AlgorithmComponent[] lcs, int[] li, double[] lf, String[] schemes , int nR){
		problemInstances=p;
		fitnessFeatures=fitnessF;
		
		this.popSize=popSize;
		termination=fc;
		globalComponents=gcs;
		localComponents=lcs;
		localIntensities=li;
		localFrequencies=lf;
		localSchemes=schemes;
		numRuns=nR;
		nameResultFile="";
		 generator=new RandomGenerationExecutor();
	}
	
	
	/**
     * Constructor 
     * 
     * @param p  the set of problem instances 
     * @param fitnessF  the features of the fitness function related to the problem to solve
     * @param popSize  the population size for all specified global search algorithms
     * @param fc  the termination criteria for the memetic algorithms built during the competent study
     * @param gcs  the set of specified global search algorithms to be used during the competent study
     * @param lcs  the set of specified local search algorithms to be used during the competent study
     * @param li  the set of the local intensities to be used during the competent study
     * @param lf  the set of the local frequencies to be used during the competent study
     * @param schemes  the set of the local schemes to be used during the competent study
     * @param nR  the number of runs to be executed for each memetic algorithm built during the competent study
     * @param nF  the file name where the results will be written
     */	
	public CompetentStudy(Problem[] p, FitnessFeatures fitnessF,int popSize, AlgorithmFinalConditions fc, AlgorithmComponent[] gcs,AlgorithmComponent[] lcs, int[] li, double[] lf, String[] schemes , int nR, String nF){
		problemInstances=p;
		fitnessFeatures=fitnessF;
		
		this.popSize=popSize;
		termination=fc;
		globalComponents=gcs;
		localComponents=lcs;
		localIntensities=li;
		localFrequencies=lf;
		localSchemes=schemes;
		numRuns=nR;
		nameResultFile=nF;
		 generator=new RandomGenerationExecutor();
	}
	
	
	/**
	 * Allows to execute the competent study and obtain all results written in different files .xls
	 */
	public void execute(){
		executeFirstStep();
		executeSecondStep();
		executeThirdStep();
		
		printBestConfiguration();
	}
	
	
	private void executeFirstStep(){
		
		int numP= problemInstances.length;
		int numG=globalComponents.length;
		int numL=localComponents.length;
		
		int numLI=localIntensities.length;
		
		int indexLI=(int)Math.floor(numLI/2);
		
		int lmfValue=localIntensities[indexLI-1];
		
		LocalIntensity lmf=new LocalIntensity(lmfValue);
		
		LocalFrequency frequencyPercentage=new LocalFrequency(1.0);
		
		String localSelectionScheme=LocalOptimizerExecutor.BEST_SELECTION_SCHEME;
		
		int numConfig=numG*numL;
		double[][]  fitnessConfiguration=new double[numP][numConfig];
		String[] configurationNames=new String[numConfig];
		int indexConfig;
	
		for(int p=0; p<numP; p++){
		
		Problem prob=problemInstances[p];
		String rootNameFile="CompetentStudy\\IPhase"+nameResultFile+prob.getId();
		String result="Start I phase.....\n";
		
		indexConfig=0;
		
		for(int i=0; i<numG; i++){
			AlgorithmComponent gc=globalComponents[i];
			((GlobalSearchOptimizerParameters)gc.getParameters()).setPopulationSize(popSize);
			
			
			for(int j=0; j<numL; j++){
				
				AlgorithmComponent lc=localComponents[j];
				
				JConfiguration.reset();
				MemeticAlgorithmConfiguration memeConf=
						new MemeticAlgorithmConfiguration(fitnessFeatures, termination, gc,lc, lmf, frequencyPercentage, localSelectionScheme, new RandomGenerationExecutor());				
			
				MemeticOptimizationAlgorithm algorithm =new MemeticOptimizationAlgorithm(prob,memeConf);
				  
				String nameFile=rootNameFile+"_"+gc.getId()+"_"+lc.getId();
				File fileTxt=new File(nameFile + ".txt");
				Vector results= new Vector();
				configurationNames[indexConfig]=gc.getId() + "+" + lc.getId();
				
				result+="Run configuration " + gc.getId() + "+" + lc.getId()+"\n";
				
				double sumfitnessRuns=0;
				for(int k=1; k<=numRuns;k++){
		        	 
		        		
						
						algorithm.reset();
					       
						
						algorithm.execute();

					  
						result+="--------Run number: " + (k) + "---------\n";
						result+=memeConf.getPerformance().toString();
						result+="\n\n";
						
						/*if(k==1)
							FileUtil.writeFile(fileTxt,result);
						else
							FileUtil.appendFile(fileTxt,result);
						*/
						HashMap map=memeConf.getPerformance().toMap();
						results.add(map);
						

						 System.out.println("best " + memeConf.getPerformance().getIndividual());

						
						System.out.println("Runs " + (k)+" executed");
					   sumfitnessRuns+=memeConf.getPerformance().getFitnessValue();
			         }
				
				
			         
			         fitnessConfiguration[p][indexConfig]=sumfitnessRuns/numRuns;
			         indexConfig++;
						
			         FileUtil.writeFile(fileTxt,result);
			         FileUtil.writeRunsToExcel(results, nameFile);
			 		
			 		System.out.println("Test terminated on configuration " + gc.getId() + "+" + lc.getId()+"\n");
				
				
			}	
	  }
	  }
	
	   double[][] ranks=Rank.computeRanks(fitnessConfiguration, fitnessFeatures.isMaximize());
		double[] averageRankXConfig=Rank.computeAverageRank(ranks );
	     
	    
	    CompetentStudyUtils.printPhaseResult("CompetentStudy\\IPhase", this.problemInstances, configurationNames, fitnessConfiguration);
	    CompetentStudyUtils.printPhaseResult("CompetentStudy\\IPhaseRank", this.problemInstances, configurationNames, ranks);
	    CompetentStudyUtils.printAverageResult("CompetentStudy\\IPhaseAverageRank", configurationNames, averageRankXConfig);
	    
	    //cerca miglior configurzione
	    Vector indexBetter=new Vector();
	    double best=ArrayUtil.min(averageRankXConfig);
	    
	    for(int i=0; i<numConfig; i++)
	    	if(averageRankXConfig[i]==best)
	    		indexBetter.add(i);
	    
	    int numBest=indexBetter.size();
	    
	    if(numBest==1)
	    	computeBestFirstPhase((Integer)indexBetter.get(0));
	    else {
	    	Random rand = new Random();
	    	int random=rand.nextInt(numBest);
	    	computeBestFirstPhase(random);
	    }
	    
	    System.out.println("End first step...");
	}
	
	
	private void computeBestFirstPhase(int value){
		int numL=localComponents.length;
		
		this.indexBestLocalComp=value%numL;
		this.indexBestGlobalComp=value/numL;
	}
	
	private void executeSecondStep(){
		
		int numP= problemInstances.length;
		int numLF=localFrequencies.length;
		int numLI=localIntensities.length;
		
		AlgorithmComponent gc=globalComponents[indexBestGlobalComp];
		((GlobalSearchOptimizerParameters)gc.getParameters()).setPopulationSize(popSize);
	
		AlgorithmComponent lc=localComponents[indexBestLocalComp];
		
		String localSelectionScheme=LocalOptimizerExecutor.BEST_SELECTION_SCHEME;
		
		int numConfig=numLI*numLF;
		double[][]  fitnessConfiguration=new double[numP][numConfig];
		String[] configurationNames=new String[numConfig];
		
		int indexConfig;
	for(int p=0; p<numP; p++){
		
		Problem prob=problemInstances[p];
		String rootNameFile="CompetentStudy\\IIPhase"+nameResultFile+prob.getId()+"_"+gc.getId()+"_"+lc.getId();
		String result="Start II phase.....\n";
		
		indexConfig=0;
		for(int i=0; i<numLI; i++){
				LocalIntensity li=new LocalIntensity(localIntensities[i]);
			
			for(int j=0; j<numLF; j++){
				
				LocalFrequency lf=new LocalFrequency(localFrequencies[j]);
			
				JConfiguration.reset();
				MemeticAlgorithmConfiguration memeConf=
						new MemeticAlgorithmConfiguration(fitnessFeatures, termination, gc, lc, li, lf, localSelectionScheme, new RandomGenerationExecutor());				
			
				MemeticOptimizationAlgorithm algorithm =new MemeticOptimizationAlgorithm(prob,memeConf);
				  
				String nameFile=rootNameFile+"_"+localIntensities[i]+"_"+localFrequencies[j];
				File fileTxt=new File(nameFile + ".txt");
				Vector results= new Vector();
				configurationNames[indexConfig]=localIntensities[i] + "+" + localFrequencies[j];
				
				
				result+="Run configuration " + localIntensities[i] + "+" + localFrequencies[j]+"\n";
				
				double sumfitnessRuns=0;
				for(int k=1; k<=numRuns;k++){
		        	 
		        		
						
						algorithm.reset();
					       
						
						algorithm.execute();

					  
						result+="--------Run number: " + (k) + "---------\n";
						result+=memeConf.getPerformance().toString();
						result+="\n\n";
						
						/*if(k==1)
							FileUtil.writeFile(fileTxt,result);
						else
							FileUtil.appendFile(fileTxt,result);
						*/
						HashMap map=memeConf.getPerformance().toMap();
						results.add(map);
						

						 System.out.println("best " + memeConf.getPerformance().getIndividual());

						
						System.out.println("Runs " + (k)+" executed");
					   sumfitnessRuns+=memeConf.getPerformance().getFitnessValue();
			         }
			         
			         fitnessConfiguration[p][indexConfig]=sumfitnessRuns/numRuns;
			         indexConfig++;
						
			         FileUtil.writeFile(fileTxt,result);
			         FileUtil.writeRunsToExcel(results, nameFile);
			 		
			 		System.out.println("Test terminated on configuration " + localIntensities[i] + "+" + localFrequencies[j]+"\n");
				
				
			}	
	  }
	  }
	
	 double[][] ranks=Rank.computeRanks(fitnessConfiguration, fitnessFeatures.isMaximize());
		double[] averageRankXConfig=Rank.computeAverageRank(ranks );
	     
	    
		CompetentStudyUtils.printPhaseResult("CompetentStudy\\IIPhase", this.problemInstances, configurationNames, fitnessConfiguration);
	    CompetentStudyUtils.printPhaseResult("CompetentStudy\\IIPhaseRank", this.problemInstances, configurationNames, ranks);
	    CompetentStudyUtils.printAverageResult("CompetentStudy\\IIPhaseAverageRank", configurationNames, averageRankXConfig);
	      
	   
	    //search the best configuration
	    Vector indexBetter=new Vector();
	    double best=ArrayUtil.min(averageRankXConfig);
	    
	    for(int i=0; i<numConfig; i++)
	    	if(averageRankXConfig[i]==best)
	    		indexBetter.add(i);
	    
	    int numBest=indexBetter.size();
	    
	    if(numBest==1)
	    	computeBestSecondPhase((Integer)indexBetter.get(0));
	    else {
	    	Random rand = new Random();
	    	int random=rand.nextInt(numBest);
	    	computeBestSecondPhase(random);
	    }
	    System.out.println("End second step...");
	}
	
	
	private void computeBestSecondPhase(int value){
		int numL=localFrequencies.length;
		
		this.indexBestFrequency=value%numL;
		this.indexBestIntensity=value/numL;
	}
	
	
private void executeThirdStep(){
		
		int numP= problemInstances.length;
		int numS=localSchemes.length;
		
		AlgorithmComponent gc=globalComponents[indexBestGlobalComp];
		((GlobalSearchOptimizerParameters)gc.getParameters()).setPopulationSize(popSize);
	
		AlgorithmComponent lc=localComponents[indexBestLocalComp];
		
		
		LocalIntensity li=new LocalIntensity(localIntensities[indexBestIntensity]);
		
		LocalFrequency lf=new LocalFrequency(localFrequencies[indexBestFrequency]);
		
		int numConfig=numS;
		double[][]  fitnessConfiguration=new double[numP][numConfig];
		String[] configurationNames=new String[numConfig];
		
		int indexConfig;
	for(int p=0; p<numP; p++){
		
		Problem prob=problemInstances[p];
		String rootNameFile="CompetentStudy\\IIIPhase"+nameResultFile+prob.getId()+"_"+gc.getId()+"_"+lc.getId()+"_"+localIntensities[indexBestIntensity]+"_"+localFrequencies[indexBestFrequency];
		String result="Start III phase.....\n";
		
		indexConfig=0;
		for(int i=0; i<numS; i++){
				
			
				String localSelectionScheme=localSchemes[i];
				
				JConfiguration.reset();
				MemeticAlgorithmConfiguration memeConf=
						new MemeticAlgorithmConfiguration(fitnessFeatures, termination, gc,lc, li, lf, localSelectionScheme, new RandomGenerationExecutor());				
			
				MemeticOptimizationAlgorithm algorithm =new MemeticOptimizationAlgorithm(prob,memeConf);
				  
				String nameFile=rootNameFile+"_"+localSelectionScheme;
				File fileTxt=new File(nameFile + ".txt");
				Vector results= new Vector();
				configurationNames[indexConfig]=localSelectionScheme;
				
				result+="Run configuration " + localSelectionScheme+"\n";
				
				double sumfitnessRuns=0;
				for(int k=1; k<=numRuns;k++){
		        	 
		        		
						
						algorithm.reset();
					       
						
						algorithm.execute();

					  
						result+="--------Run number: " + (k) + "---------\n";
						result+=memeConf.getPerformance().toString();
						result+="\n\n";
						
						/*if(k==1)
							FileUtil.writeFile(fileTxt,result);
						else
							FileUtil.appendFile(fileTxt,result);
						*/
						HashMap map=memeConf.getPerformance().toMap();
						results.add(map);
						

						 System.out.println("best " + memeConf.getPerformance().getIndividual());

						
						System.out.println("Runs " + (k)+" executed");
					   sumfitnessRuns+=memeConf.getPerformance().getFitnessValue();
			         }
			         
			         fitnessConfiguration[p][indexConfig]=sumfitnessRuns/numRuns;
			         indexConfig++;
						
			         FileUtil.writeFile(fileTxt,result);
			         FileUtil.writeRunsToExcel(results, nameFile);
			 		
			 		System.out.println("Test terminated on configuration " + localSchemes[i]+"\n");
				
				
			}	
	  }
	  
	
	 double[][] ranks=Rank.computeRanks(fitnessConfiguration, fitnessFeatures.isMaximize());
		double[] averageRankXConfig=Rank.computeAverageRank(ranks );
	     
	    
		CompetentStudyUtils.printPhaseResult("CompetentStudy\\IIIPhase", this.problemInstances, configurationNames, fitnessConfiguration);
	    CompetentStudyUtils.printPhaseResult("CompetentStudy\\IIIPhaseRank", this.problemInstances, configurationNames, ranks);
	    CompetentStudyUtils.printAverageResult("CompetentStudy\\IIIPhaseAverageRank", configurationNames, averageRankXConfig);
	     
	   
	    //cerca miglior configurzione
	    Vector indexBetter=new Vector();
	    double best=ArrayUtil.min(averageRankXConfig);
	    
	    for(int i=0; i<numConfig; i++)
	    	if(averageRankXConfig[i]==best)
	    		indexBetter.add(i);
	    
	    int numBest=indexBetter.size();
	    
	    if(numBest==1)
	    	indexBestScheme=(Integer)indexBetter.get(0);
	    else {
	    	Random rand = new Random();
	    	int random=rand.nextInt(numBest);
	    	indexBestScheme=(Integer)indexBetter.get(random);
	    }
	    
	}
	
    private void printBestConfiguration(){
    	AlgorithmComponent gc=globalComponents[indexBestGlobalComp];
		((GlobalSearchOptimizerParameters)gc.getParameters()).setPopulationSize(popSize);
	
		AlgorithmComponent lc=localComponents[indexBestLocalComp];
		
		
		LocalIntensity li=new LocalIntensity(localIntensities[indexBestIntensity]);
		
		LocalFrequency lf=new LocalFrequency(localFrequencies[indexBestFrequency]);

		String localSelectionScheme=localSchemes[indexBestScheme];
		
		JConfiguration.reset();
    	bestConf=new MemeticAlgorithmConfiguration(fitnessFeatures, termination, gc,lc, li, lf, localSelectionScheme);				
		
    	//print the best configuration    	
    	System.out.println(bestConf.toString());
    	
    	
    	FileUtil.writeFile(new File("CompetentStudy\\BestMemeticConfiguration"+problemInstances[0].getId()+".txt"), bestConf.toString());
    	
    	//save the best configuration in a file
    	bestConf.save("CompetentStudy\\memeticConfiguration"+problemInstances[0].getId()+".ser");
    }
	
	
    
	
	public GenerationExecutor getGenerator() {
		return generator;
	}


	public void setGenerator(GenerationExecutor generator) {
		this.generator = generator;
	}
	
	

	
}
