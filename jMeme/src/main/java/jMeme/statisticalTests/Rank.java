/*
 * Rank.java 
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

package jMeme.statisticalTests;

import java.util.Vector;


/**
 * This class implements a strategy to rank a set of values. In particular, the implemented strategy is the fractional ranking.
 *
 */
public class Rank {

public static double[] fractionalRank(double[] values, boolean maximize){
		
		int num=values.length;
		
		Vector supp=new Vector();
		for(int i=0; i<num; i++)
			supp.add(values[i]);
		
		
		double[] ranks=new double[num];
		
		int count=num;
		int pos=0;
		while(count>0){
			double m;
			m=min(supp);
			if(maximize)
				m=max(supp);
			
			Vector indexMin1=new Vector();
			
			for(int j=0; j<num;j++)
				{if(values[j]==m)
					indexMin1.add(j);
				
				}
			
					
			
			int numO=indexMin1.size();
			
			double sumt=0;
			for(int k=0, p=pos+1; k<numO;k++,p++)
				sumt+=p;
				
			double newRank=sumt/numO;
			
			pos=pos+numO;
			
			for(int i=0; i<numO; i++)
				
				{
				ranks[(Integer)indexMin1.get(i)]=newRank;	
				supp.remove(m);
				}
			
			count=supp.size();
			
		}
		
       return ranks;
	}
	
	public static double min(Vector<Double> v){
		int num=v.size();
		
		double min=v.get(0);
		for(int i=1; i<num;i++)
			if(v.get(i)<min)
				min=v.get(i);
		
		return min;
		
	}
	
	public static double max(Vector<Double> v){
		int num=v.size();
		
		double max=v.get(0);
		for(int i=1; i<num;i++)
			if(v.get(i)>max)
				max=v.get(i);
		
		return max;
		
	}
	
	
	
	public static  double[][] computeRanks(double[][] fitness, boolean maximize){
	    
    	int numP=fitness.length;
		int numConfig=fitness[0].length;
		
		double[][] ranks=new double[numP][numConfig];
		
		for(int i=0; i<numP; i++)
		{
			ranks[i]=fractionalRank(fitness[i], maximize);
		}
		
		return ranks;
    
    }
	public static  double[] computeAverageRank(double[][] ranks){

		int numP=ranks.length;
		int numConfig=ranks[0].length;
		
		
		double[] sumCol=new double[numConfig];
		
		for(int i=0; i<numConfig; i++)
			for(int j=0; j<numP; j++)
			   sumCol[i]+=ranks[j][i];
		
		
     double[] averageRank=new double[numConfig];
		
		for(int i=0; i<numConfig; i++)
			averageRank[i]=sumCol[i]/numP;
		
		return averageRank;
	
	}
	
	
}
