/*
 * ExecuteCMA.java 
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

package jMeme.test;

import jMeme.core.Problem;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.memeticAlgorithms.MemeticOptimizationAlgorithm;
import jMeme.problems.Rosenbrock.RosenbrockProblem;
import jMeme.problems.rastrigin.RastriginProblem;


/**
 * This class implements an executor of Memetic Algorithm on a problem starting from an object containing the memetic configuration.
 *
 */
public class ExecuteCMA {

	public static void	 main(String[] args) {
		
	RastriginProblem pRastrigin=new RastriginProblem("Rastrigin",2);
			
	
	//	SphereProblem pSphere=new SphereProblem("Sphere",2);
		
 //RosenbrockProblem pRosenbrock=new RosenbrockProblem("Rosenbrock",2);
		
	String nameConf="CompetentStudy\\memeticConfigurationRastrigin.ser";
	Problem problem=pRastrigin;
		
	MemeticOptimizationAlgorithm algorithm =new MemeticOptimizationAlgorithm(problem,MemeticAlgorithmConfiguration.read(nameConf));
	     
    
	Test.execute("TestRastrigin", 10, algorithm);
    
	 System.out.println(MemeticAlgorithmConfiguration.read(nameConf));
		 
	}
}
