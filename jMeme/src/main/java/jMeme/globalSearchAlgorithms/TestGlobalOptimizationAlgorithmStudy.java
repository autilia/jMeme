/*
 * TestGlobalSearchAlgorithmStudy.java 
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
import jMeme.core.Problem;
import jMeme.core.fitnessFunction.FitnessFeatures;
import jMeme.design.finalConditions.AlgorithmFinalConditions;
import jMeme.design.finalConditions.FinalConditions;
import jMeme.design.optimizerParameters.ABCParameters;
import jMeme.design.optimizerParameters.CrossoverParameters;
import jMeme.design.optimizerParameters.DEParameters;
import jMeme.design.optimizerParameters.GAParameters;
import jMeme.globalSearchAlgorithms.ga.GA_Optimizer;
import jMeme.problems.sphere.SphereFitness;
import jMeme.problems.sphere.SphereProblem;

/**
 * This class represents an example to run a global search algorithm study by considering the problem "Sphere".
 */
public class TestGlobalOptimizationAlgorithmStudy {

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
        
        AlgorithmComponent[] gcc=new AlgorithmComponent[3];
       
        
        DEParameters deF1=new DEParameters(0.6, 0.9, DEParameters.xTypeBest, DEParameters.yType1, 0.6);
        
        gcc[0]=new AlgorithmComponent("de1", "jMeme.globalSearchAlgorithms.de.DE_Optimizer", deF1);
       
        DEParameters deF2=new DEParameters(0.6, 0.9, DEParameters.xTypeRand, DEParameters.yType1, 0.6);
        
        gcc[1]=new AlgorithmComponent("de2", "jMeme.globalSearchAlgorithms.de.DE_Optimizer", deF2);
       
        GAParameters ga1= new GAParameters(50, new CrossoverParameters(0.9, CrossoverParameters.BLXalpha3));
        
        gcc[2]=new AlgorithmComponent("ga1", GA_Optimizer.class.getName(), ga1 );
      
                 
        //execute the study
        GlobalOptimizationAlgorithmStudy proc=new GlobalOptimizationAlgorithmStudy(pp, fitness, 100, finC, gcc, 10);
        proc.execute();
        
	}

}
