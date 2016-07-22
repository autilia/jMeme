/*
 * TestSphereProblem.java 
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

package jMeme.problems.sphere;


import jMeme.core.AlgorithmComponent;
import jMeme.core.JConfiguration;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.finalConditions.LocalOptimizerFinalConditions;
import jMeme.design.generationExecutor.RandomGenerationExecutor;
import jMeme.design.localIntegration.LocalFrequency;
import jMeme.design.localIntegration.LocalIntensity;
import jMeme.design.localIntegration.LocalOptimizerExecutor;
import jMeme.design.optimizerParameters.ABCParameters;
import jMeme.design.optimizerParameters.BB_BCParameters;
import jMeme.design.optimizerParameters.CrossoverParameters;
import jMeme.design.optimizerParameters.DEParameters;
import jMeme.design.optimizerParameters.GAParameters;
import jMeme.design.optimizerParameters.OptimizerParameters;
import jMeme.design.optimizerParameters.PSOParameters;
import jMeme.design.optimizerParameters.StochasticHillClimbingParameters;
import jMeme.design.optimizerParameters.TabuSearchParameters;
import jMeme.globalSearchAlgorithms.GlobalOptimizationAlgorithm;
import jMeme.globalSearchAlgorithms.abc.ABC_Optimizer;
import jMeme.globalSearchAlgorithms.bb_bc.BBBC_Optimizer;
import jMeme.globalSearchAlgorithms.de.DE_Optimizer;
import jMeme.globalSearchAlgorithms.ga.GA_Optimizer;
import jMeme.localSearchAlgorithms.LocalOptimizationAlgorithm;
import jMeme.localSearchAlgorithms.hillClimbing.StochasticHillClimbing;
import jMeme.localSearchAlgorithms.tabuSearch.TabuSearch;
import jMeme.memeticAlgorithms.MemeticOptimizationAlgorithm;
import jMeme.performances.MemeticAlgorithmPerformance;
import jMeme.test.Test;

public class TestSphereProblem {

	/**
	 * @param args
 */
	public static void	 main(String[] args) {
		
		String fileName="here";
		
		TestSphereProblem t=new TestSphereProblem(1, fileName);
	}
	
	public TestSphereProblem(int numRuns, String nameResultFile){
		
		
		SphereProblem p=new SphereProblem(30);
		
		SphereFitness f=new SphereFitness();
		
        FitnessFeatures fitness=new FitnessFeatures(f, FitnessFeatures.MIN);
		
        AlgorithmFinalConditions finC=new AlgorithmFinalConditions(FinalConditions.FINAL_CONDITION_FITNESSEVALUATIONS, 550);
    
        

        
      		GAParameters genAlgorithmFeatures=new GAParameters(10, 50, new CrossoverParameters(0.9, CrossoverParameters.BLXalpha3));
     
      		ABCParameters abc=new ABCParameters(20);
      
  BB_BCParameters bb1=new BB_BCParameters(10, 1, 0.1);
bb1.setCenterType(BB_BCParameters.CENTER_TYPE_EQUATION2);
  bb1.setComputationType(BB_BCParameters.COMPUTATION_TYPE_CAMP);
  
        DEParameters deF2=new DEParameters(30,0.6, 0.9, DEParameters.xTypeBest, DEParameters.yType1, 0.6);
        PSOParameters psoA=new PSOParameters(100);
        
      AlgorithmComponent degc=new AlgorithmComponent("de2", DE_Optimizer.class.getName(), deF2);
      //AlgorithmComponent gc=new AlgorithmComponent("bbbc", BBBC_Optimizer.class.getName(), bb1);
      AlgorithmComponent gc=new AlgorithmComponent("ga", GA_Optimizer.class.getName(), genAlgorithmFeatures);
       //AlgorithmComponent gc=new AlgorithmComponent("pso", PSO_Optimizer.class.getName(), psoA);
        AlgorithmComponent abcc=new AlgorithmComponent("abc", ABC_Optimizer.class.getName(), abc);
          
    // GlobalSearchAlgorithmConfiguration conf=new GlobalSearchAlgorithmConfiguration(fitness, finC, gc,new RandomGenerationExecutor());
		
	  //  GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,conf);
	
      StochasticHillClimbingParameters st1=new StochasticHillClimbingParameters(1);
      
     AlgorithmComponent lc=new AlgorithmComponent("st1", StochasticHillClimbing.class.getName(), st1);
   
     AlgorithmComponent t3=new AlgorithmComponent("provabbbc", BBBC_Optimizer.class.getName(), bb1);
	    
     MemeticAlgorithmConfiguration conf=new MemeticAlgorithmConfiguration(fitness, finC, t3,lc, new LocalIntensity(LocalOptimizerFinalConditions.FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED,60),new LocalFrequency(0.1), LocalOptimizerExecutor.BEST_SELECTION_SCHEME, new RandomGenerationExecutor(), new MemeticAlgorithmPerformance() );
	     
		
		MemeticOptimizationAlgorithm algorithm =new MemeticOptimizationAlgorithm(p,conf);
		
		 
		 JConfiguration.reset();
		  GlobalSearchAlgorithmConfiguration confbb=new GlobalSearchAlgorithmConfiguration(fitness, finC, degc,new RandomGenerationExecutor());
			
		   GlobalOptimizationAlgorithm algorithmbb= new GlobalOptimizationAlgorithm(p,confbb);
		
		   
		   OptimizerParameters tabuP=new TabuSearchParameters();
		AlgorithmComponent lcT=new AlgorithmComponent("tabu", TabuSearch.class.getName(), tabuP);
		   
		   JConfiguration.reset();
		   LocalSearchAlgorithmConfiguration confl=new LocalSearchAlgorithmConfiguration(fitness, finC, lcT);
		    LocalOptimizationAlgorithm algorithml = new LocalOptimizationAlgorithm(p, confl);
		  
		  
		Test.execute(nameResultFile, numRuns, algorithml);
 		
	}

}
