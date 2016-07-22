/*
 * TestRosenbrockProblem.java 
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

package jMeme.problems.Rosenbrock;


import jMeme.core.AlgorithmComponent;
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
import jMeme.design.optimizerParameters.PSOParameters;
import jMeme.design.optimizerParameters.StochasticHillClimbingParameters;
import jMeme.globalSearchAlgorithms.ga.GA_Optimizer;
import jMeme.localSearchAlgorithms.hillClimbing.StochasticHillClimbing;
import jMeme.memeticAlgorithms.MemeticOptimizationAlgorithm;
import jMeme.performances.MemeticAlgorithmPerformance;
import jMeme.test.Test;

public class TestRosenbrockProblem {

	/**
	 * @param args
 */
	public static void	 main(String[] args) {
		
		String fileName="here";
		
		TestRosenbrockProblem t=new TestRosenbrockProblem(1, fileName);
	}
	
	public TestRosenbrockProblem(int numRuns, String nameResultFile){
		
		
		RosenbrockProblem p=new RosenbrockProblem(30);
		
		RosenbrockFitness f=new RosenbrockFitness();
		
        FitnessFeatures fitness=new FitnessFeatures(f, FitnessFeatures.MIN);
		
        AlgorithmFinalConditions finC=new AlgorithmFinalConditions(FinalConditions.FINAL_CONDITION_FITNESSEVALUATIONS, 550);
    
        
		
        
      		GAParameters genAlgorithmFeatures=new GAParameters(10, 50, new CrossoverParameters(0.9, CrossoverParameters.BLXalpha3));
     
      		ABCParameters abc=new ABCParameters(100);
      
  BB_BCParameters bb1=new BB_BCParameters(100);
  
        DEParameters deF2=new DEParameters(100,0.6, 0.9, DEParameters.xTypeBest, DEParameters.yType1, 0.6);
        PSOParameters psoA=new PSOParameters(100);
        
      //AlgorithmComponent gc=new AlgorithmComponent("de2", DE_Optimizer.class.getName(), deF2);
      //AlgorithmComponent gc=new AlgorithmComponent("bbbc", BBBC_Optimizer.class.getName(), bb1);
      AlgorithmComponent gc=new AlgorithmComponent("ga", GA_Optimizer.class.getName(), genAlgorithmFeatures);
       //AlgorithmComponent gc=new AlgorithmComponent("pso", PSO_Optimizer.class.getName(), psoA);
     //   AlgorithmComponent gc=new AlgorithmComponent("abc", ABC_Optimizer.class.getName(), abc);
          
    // GlobalSearchAlgorithmConfiguration conf=new GlobalSearchAlgorithmConfiguration(fitness, finC, gc,new RandomGenerationExecutor());
		
	  //  GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,conf);
	
      StochasticHillClimbingParameters st1=new StochasticHillClimbingParameters(1);
      
     AlgorithmComponent lc=new AlgorithmComponent("st1", StochasticHillClimbing.class.getName(), st1);
   
     MemeticAlgorithmConfiguration conf=new MemeticAlgorithmConfiguration(fitness, finC, gc,lc, new LocalIntensity(LocalOptimizerFinalConditions.FINAL_CONDITION_FITNESS_EVALUATIONS_INTEGRATED,60),new LocalFrequency(0.1), LocalOptimizerExecutor.BEST_SELECTION_SCHEME, new RandomGenerationExecutor(), new MemeticAlgorithmPerformance() );
	     
		
		MemeticOptimizationAlgorithm algorithm =new MemeticOptimizationAlgorithm(p,conf);
		  
		Test.execute(nameResultFile, numRuns, algorithm);
 		
	}

}
