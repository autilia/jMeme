/*
 * TestCompetentStudyXSphere.java 
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

package jMeme.test.examples;

import jMeme.core.AlgorithmComponent;
import jMeme.core.Problem;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.competent.CompetentStudy;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.localIntegration.LocalOptimizerExecutor;
import jMeme.design.optimizerParameters.CrossoverParameters;
import jMeme.design.optimizerParameters.GAParameters;
import jMeme.design.optimizerParameters.PSOParameters;
import jMeme.design.optimizerParameters.SimulatedAnnealingParameters;
import jMeme.design.optimizerParameters.StochasticHillClimbingParameters;
import jMeme.globalSearchAlgorithms.GlobalOptimizationAlgorithmStudy;
import jMeme.globalSearchAlgorithms.ga.GA_Optimizer;
import jMeme.globalSearchAlgorithms.pso.PSO_Optimizer;
import jMeme.localSearchAlgorithms.LocalOptimizationAlgorithmStudy;
import jMeme.localSearchAlgorithms.hillClimbing.StochasticHillClimbing;
import jMeme.localSearchAlgorithms.sa.SimulatedAnnealing;
import jMeme.problems.sphere.SphereFitness;
import jMeme.problems.sphere.SphereProblem;



/**
 * This class implements an example of competent design for the problem Sphere.
 */
public class TestCompetentStudyXSphere {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
       SphereProblem pSphere=new SphereProblem("Sphere",2);
		
        SphereFitness fSphere=new SphereFitness();
 		
         FitnessFeatures fitnessSphere=new FitnessFeatures(fSphere, FitnessFeatures.MIN);
     
         
        
        
        Problem[] pp=new Problem[1];
        pp[0]=pSphere;
        
        AlgorithmFinalConditions finC=new AlgorithmFinalConditions(FinalConditions.FINAL_CONDITION_FITNESSEVALUATIONS, 1500);
        AlgorithmComponent[] gcc=new AlgorithmComponent[2];
        AlgorithmComponent[] lcc=new AlgorithmComponent[2];
    
        
        
        GAParameters ga= new GAParameters(50, new CrossoverParameters(0.9, CrossoverParameters.BLXalpha3));
        
        gcc[0]=new AlgorithmComponent("ga", GA_Optimizer.class.getName(), ga );
      
        
        PSOParameters pso= new PSOParameters();
        
        gcc[1]=new AlgorithmComponent("pso", PSO_Optimizer.class.getName(), pso );
      
        
        
        StochasticHillClimbingParameters st=new StochasticHillClimbingParameters();
        
        lcc[0]=new AlgorithmComponent("st", StochasticHillClimbing.class.getName(), st);
        
        SimulatedAnnealingParameters sa= new SimulatedAnnealingParameters();
        lcc[1]= new AlgorithmComponent("sa", SimulatedAnnealing.class.getName(), sa);
         
       CompetentStudy proc=new CompetentStudy(pp, fitnessSphere, 20, finC, gcc, lcc, new int[]{10,30,50}, new double[]{0.05, 0.5, 1.0}, new String[]{LocalOptimizerExecutor.BEST_SELECTION_SCHEME, LocalOptimizerExecutor.RANDOM_SELECTION_SCHEME, LocalOptimizerExecutor.STRATIFIED_SELECTION_SCHEME});
       proc.execute();
        
	
       // GlobalOptimizationAlgorithmStudy proc1=new GlobalOptimizationAlgorithmStudy(pp, fitnessSphere, 20, finC, gcc);
       // proc1.execute();
        
       // LocalOptimizationAlgorithmStudy proc2= new LocalOptimizationAlgorithmStudy(pp, fitnessSphere, finC, lcc);
       // proc2.execute();
	}

}
