/*
 * Test.java 
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

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import jMeme.core.Algorithm;
import jMeme.utils.FileUtil;


/**
 * This class implements a typical test for an algorithm. At the end, it writes a report file of the executed test.
 */
public class Test {

	
	public static void execute(String nameResultFile, int numRuns, Algorithm algorithm ){
		
		File test=new File(nameResultFile + ".txt");
		Vector results= new Vector();
		
		
         for(int i=1; i<=numRuns;i++){
        	 
        		
			
			algorithm.reset();
		       
			
			algorithm.execute();

		  
			String result="--------Run number: " + (i) + "---------\n";
			result+=algorithm.getConfiguration().getPerformance().toString();
			result+="\n\n";
			
			if(i==1)
				FileUtil.writeFile(test,result);
			else
				FileUtil.appendFile(test,result);
			
			HashMap map=algorithm.getConfiguration().getPerformance().toMap();
			results.add(map);
			

			 System.out.println("best " + algorithm.getConfiguration().getPerformance().getIndividual());

			
			System.out.println("Runs " + (i)+" executed");
		
         }
         
         
         FileUtil.writeRunsToExcel(results, nameResultFile);
 		
 		System.out.println("Test terminated");
 		
	}
}
