/*
 * TestLocalSearchAlgorithmStudy.java 
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
import jMeme.core.Problem;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.optimizerParameters.ChaoticLocalSearchParameters;
import jMeme.design.optimizerParameters.StochasticHillClimbingParameters;
import jMeme.localSearchAlgorithms.hillClimbing.ChaoticLocalSearch;
import jMeme.localSearchAlgorithms.hillClimbing.StochasticHillClimbing;
import jMeme.problems.sphere.SphereFitness;
import jMeme.problems.sphere.SphereProblem;

/**
 * This class represents an example to run a local search algorithm study by considering the problem "Sphere".
 */
public class TestLocalOptimizationAlgorithmStudy {

	public static void main(String[] args) {
		
		
		//initialize problem
        SphereProblem p=new SphereProblem("Sphere",30);
		
		SphereFitness f=new SphereFitness();
		
		//set fitness features
        FitnessFeatures fitness=new FitnessFeatures(f, FitnessFeatures.MIN);
        
        
        //set parameters to run the competent study
        Problem[] pp=new Problem[1];
        pp[0]=p;
        
        AlgorithmFinalConditions finC=new AlgorithmFinalConditions(FinalConditions.FINAL_CONDITION_FITNESSEVALUATIONS, 50000);
        
        AlgorithmComponent[] lcc=new AlgorithmComponent[2];
        
        StochasticHillClimbingParameters st1=new StochasticHillClimbingParameters();
        
        lcc[0]=new AlgorithmComponent("st1", StochasticHillClimbing.class.getName(), st1);
        
        ChaoticLocalSearchParameters ch1= new ChaoticLocalSearchParameters();
        lcc[1]= new AlgorithmComponent("ch1", ChaoticLocalSearch.class.getName(), ch1);
        
                
        //execute the study
        LocalOptimizationAlgorithmStudy proc=new LocalOptimizationAlgorithmStudy(pp, fitness, finC, lcc, 10);
        proc.execute();
        
	}

}
