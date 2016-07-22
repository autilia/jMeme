/*
 * JGapExstension.java 
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

package jMeme.utils;


import java.util.Iterator;

import org.jgap.FitnessEvaluator;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;

import jMeme.core.individuals.Individual;

/**
 * This class implements some mechanism to extend JGap library. *
 */
public class JGapExtension {

	
	/**
	 * Allows to get the index in the population of the worst individual
	 * 
	 * @param p  the population
	 * 
	 * @return an integer representing the index of the worst individual in the population
	 */
	public static int getIndexOfWorstIndividual(Population p) {
	      
		
		IChromosome worst= null;
		int index=0;
		int indexWorst=0;
		Iterator it = p.getChromosomes().iterator();
	    FitnessEvaluator evaluator = p.getConfiguration().getFitnessEvaluator();
	      double worstFitness;
	      if (!evaluator.isFitter(2.0d, 1.0d)) {
	        worstFitness = -1.0d;
	      }
	      else {
	        worstFitness = Double.MAX_VALUE;
	      }
	      double fitness;
	      while (it.hasNext()) {
	        IChromosome chrom = (IChromosome) it.next();
	         fitness = chrom.getFitnessValue();
	        if (!evaluator.isFitter(fitness, worstFitness) || worst==null) {
	          worst = chrom;
	          worstFitness = fitness;
	          indexWorst=index;
	        }
	        index++;
	      }
	      return indexWorst;
	    }
	
/**
 * Allows to replace the worst individual of the population with a given one
 * 
 * @param p  the population whose the worst individual mist be replaced
 * @param s  the induvidual which must replace the worst individual of the population
 */
public static void replaceWorstIndividual(Genotype p, Individual s){
	
	int index= JGapExtension.getIndexOfWorstIndividual(p.getPopulation());
	p.getPopulation().setChromosome(index, s);

}

public static Object[] chromosomeToArray(IChromosome s){
	
	Gene[] genes= s.getGenes();
	
	int num= genes.length;
	
	Object[] array=new Object[num];
	
	for(int i=0; i<num;i++)
		array[i]= genes[i].getAllele();
		
	
	return array;
}


public static double[] doubleChromosomeToArray(IChromosome s){
	
	Gene[] genes= s.getGenes();
	
	int num= genes.length;
	
	double[] array=new double[num];
	
	for(int i=0; i<num;i++)
		array[i]= ((Double)genes[i].getAllele()).doubleValue();
		
	
	return array;
}


}
