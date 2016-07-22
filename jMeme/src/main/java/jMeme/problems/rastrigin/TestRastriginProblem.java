/*
 * TestRastriginProblem.java 
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

package jMeme.problems.rastrigin;


import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import jMeme.core.AlgorithmComponent;
import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.generationExecutor.RandomGenerationExecutor;
import jMeme.design.optimizerParameters.ABCParameters;
import jMeme.design.optimizerParameters.BB_BCParameters;
import jMeme.design.optimizerParameters.CrossoverParameters;
import jMeme.design.optimizerParameters.DEParameters;
import jMeme.design.optimizerParameters.GAParameters;
import jMeme.design.optimizerParameters.PSOParameters;
import jMeme.globalSearchAlgorithms.GlobalOptimizationAlgorithm;
import jMeme.globalSearchAlgorithms.pso.PSO_Optimizer;
import jMeme.utils.FileUtil;

public class TestRastriginProblem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String fileName="here";
		
		TestRastriginProblem t=new TestRastriginProblem(2, fileName);
	}
	
	public TestRastriginProblem(int numRuns, String nameResultFile){
		
		
		RastriginProblem p=new RastriginProblem(2);
		
		RastriginFitness f=new RastriginFitness();
		
        FitnessFeatures fitness=new FitnessFeatures(f, FitnessFeatures.MIN);
		
        AlgorithmFinalConditions finC=new AlgorithmFinalConditions(FinalConditions.FINAL_CONDITION_FITNESSEVALUATIONS, 20);
    
	
        
        
      		GAParameters genAlgorithmFeatures=new GAParameters(5, 100, new CrossoverParameters(0.9, CrossoverParameters.BLXalpha3));
     
      		ABCParameters abc=new ABCParameters(100);
      
  BB_BCParameters bb1=new BB_BCParameters(100);
  
        DEParameters deF2=new DEParameters(100,0.6, 0.9, DEParameters.xTypeBest, DEParameters.yType1, 0.6);
        PSOParameters psoA=new PSOParameters(10);
        
      //AlgorithmComponent gc=new AlgorithmComponent("de2", DE_Optimizer.class.getName(), deF2);
      //AlgorithmComponent gc=new AlgorithmComponent("bbbc", BBBC_Optimizer.class.getName(), bb1);
     // AlgorithmComponent gc=new AlgorithmComponent("ga", GA_Optimizer.class.getName(), genAlgorithmFeatures);
      AlgorithmComponent gc=new AlgorithmComponent("pso", PSO_Optimizer.class.getName(), psoA);
       // AlgorithmComponent gc=new AlgorithmComponent("abc", ABC_Optimizer.class.getName(), abc);
          
      GlobalSearchAlgorithmConfiguration conf=new GlobalSearchAlgorithmConfiguration(fitness, finC, gc,new RandomGenerationExecutor());
		
	    GlobalOptimizationAlgorithm algorithm= new GlobalOptimizationAlgorithm(p,conf);
		
		File test=new File(nameResultFile + ".txt");
		Vector results= new Vector();
		
		
         for(int i=1; i<=numRuns;i++){
        	 
        		
			
			algorithm.reset();
		       
			
			algorithm.execute();

		  
			String result="--------Run number: " + (i) + "---------\n";
			result+=conf.getPerformance().toString();
			result+="\n\n";
			
			if(i==1)
				FileUtil.writeFile(test,result);
			else
				FileUtil.appendFile(test,result);
			
			HashMap map=conf.getPerformance().toMap();
			results.add(map);
			

			 System.out.println("best " + conf.getPerformance().getIndividual());

			
			System.out.println("Runs " + (i)+" executed");
		
         }
         
         
         FileUtil.writeRunsToExcel(results, nameResultFile);
 		
 		System.out.println("Test terminated");
 		
	}

}
