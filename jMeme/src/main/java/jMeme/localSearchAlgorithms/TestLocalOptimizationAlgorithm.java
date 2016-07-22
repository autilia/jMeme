/*
 * TestLocalSearchAlgorithm.java 
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


import jMeme.core.AlgorithmComponent;
import jMeme.core.JConfiguration;
import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.optimizerParameters.StochasticHillClimbingParameters;
import jMeme.localSearchAlgorithms.hillClimbing.StochasticHillClimbing;

import jMeme.problems.sphere.SphereFitness;
import jMeme.problems.sphere.SphereProblem;
import jMeme.test.Test;


/**
 * This class represents an example to run a local search algorithm by considering the problem "Sphere".
 */
public class TestLocalOptimizationAlgorithm {

	
	public static void	 main(String[] args) {
		
		String fileName="TestResults";		
		
		SphereProblem p=new SphereProblem(30);
		
		SphereFitness f=new SphereFitness();
		
        FitnessFeatures fitness=new FitnessFeatures(f, FitnessFeatures.MIN);
		
        AlgorithmFinalConditions finC=new AlgorithmFinalConditions(FinalConditions.FINAL_CONDITION_FITNESSEVALUATIONS, 550);
    
        
        
        StochasticHillClimbingParameters st1=new StochasticHillClimbingParameters();
        
        //ChaoticLocalSearchParameters ch1= new ChaoticLocalSearchParameters();
        
        
      	AlgorithmComponent stoc=new AlgorithmComponent("st1", StochasticHillClimbing.class.getName(), st1);
      	// AlgorithmComponent chaotic=new AlgorithmComponent("ch1", ChaoticLocalSearch.class.getName(), ch1);
          
        JConfiguration.reset();
		LocalSearchAlgorithmConfiguration confstoc=new LocalSearchAlgorithmConfiguration(fitness, finC, stoc);
		//LocalSearchAlgorithmConfiguration confch=new LocalSearchAlgorithmConfiguration(fitness, finC, chaotic);
		
		LocalOptimizationAlgorithm algorithm= new LocalOptimizationAlgorithm(p,confstoc);
		//LocalOptimizationAlgorithm algorithm= new LocalOptimizationAlgorithm(p,confch);
		   
		  
		Test.execute(fileName, 1, algorithm);
 		
	}

}
