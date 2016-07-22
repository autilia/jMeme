/*
 * TestGlobalOptimizationAlgorithm.java 
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


import jMeme.core.AlgorithmComponent;
import jMeme.core.JConfiguration;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.generationExecutor.RandomGenerationExecutor;
import jMeme.design.optimizerParameters.ABCParameters;
import jMeme.design.optimizerParameters.CrossoverParameters;
import jMeme.design.optimizerParameters.GAParameters;
import jMeme.globalSearchAlgorithms.abc.ABC_Optimizer;
import jMeme.globalSearchAlgorithms.ga.GA_Optimizer;
import jMeme.problems.sphere.SphereFitness;
import jMeme.problems.sphere.SphereProblem;
import jMeme.test.Test;


/**
 * This class represents an example to run a global search algorithm by considering the problem "Sphere".
 */
public class TestGlobalOptimizationAlgorithm {

	
	public static void	 main(String[] args) {
		
		String fileName="TestResults";		
		
		SphereProblem p=new SphereProblem(30);
		
		SphereFitness f=new SphereFitness();
		
        FitnessFeatures fitness=new FitnessFeatures(f, FitnessFeatures.MIN);
		
        AlgorithmFinalConditions finC=new AlgorithmFinalConditions(FinalConditions.FINAL_CONDITION_FITNESSEVALUATIONS, 550);
    
        
        
     	GAParameters genAlgorithmFeatures=new GAParameters(10, 50, new CrossoverParameters(0.9, CrossoverParameters.BLXalpha3));
     
      	//	ABCParameters abcp=new ABCParameters(20);
      
          //   BB_BCParameters bbp=new BB_BCParameters(10, 1, 0.1);
            // bbp.setCenterType(BB_BCParameters.CENTER_TYPE_EQUATION2);
            //bbp.setComputationType(BB_BCParameters.COMPUTATION_TYPE_CAMP);
  
            //DEParameters deF2=new DEParameters(30,0.6, 0.9, DEParameters.xTypeBest, DEParameters.yType1, 0.6);
            // PSOParameters psoA=new PSOParameters(100);
        
      	AlgorithmComponent gc=new AlgorithmComponent("ga", GA_Optimizer.class.getName(), genAlgorithmFeatures);
     // 	 AlgorithmComponent abc=new AlgorithmComponent("abc", ABC_Optimizer.class.getName(), abcp);
      	// AlgorithmComponent de=new AlgorithmComponent("de", DE_Optimizer.class.getName(), deF2);
       // AlgorithmComponent bbbc=new AlgorithmComponent("bbbc", BBBC_Optimizer.class.getName(), bbp);
        //AlgorithmComponent pso=new AlgorithmComponent("pso", PSO_Optimizer.class.getName(), psoA);
       
          
        JConfiguration.reset();
		GlobalSearchAlgorithmConfiguration confga=new GlobalSearchAlgorithmConfiguration(fitness, finC, gc,new RandomGenerationExecutor());
		//GlobalSearchAlgorithmConfiguration confabc=new GlobalSearchAlgorithmConfiguration(fitness, finC, abc,new RandomGenerationExecutor());
		//GlobalSearchAlgorithmConfiguration confbbbc=new GlobalSearchAlgorithmConfiguration(fitness, finC, bbbc,new RandomGenerationExecutor());
		//GlobalSearchAlgorithmConfiguration confde=new GlobalSearchAlgorithmConfiguration(fitness, finC, de,new RandomGenerationExecutor());
		//GlobalSearchAlgorithmConfiguration confpso=new GlobalSearchAlgorithmConfiguration(fitness, finC, pso,new RandomGenerationExecutor());
		
		
		GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,confga);
		//GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,confabc);
		//GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,confbbbc);
		//GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,confde);
		//GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,confpso);
		
		   
		  
		Test.execute(fileName, 1, algorithm);
 		
	}

}
