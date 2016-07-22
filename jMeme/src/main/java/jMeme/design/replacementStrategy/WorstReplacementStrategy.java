/*
 * WorstReplacementStrategy.java 
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


package jMeme.design.replacementStrategy;

import jMeme.core.JPopulation;
import jMeme.core.individuals.Individual;
import jMeme.utils.JGapExtension;


/**
 * This class implements the following replacement strategy:
 * introduce the new solution in place of the worst individual in the population if the new solution is better.
 */
public class WorstReplacementStrategy extends ReplacementStrategy{
	
	
	private static final long serialVersionUID = 8903937125255183089L;


	/**
	 * Constructor
	 */
	public WorstReplacementStrategy(){
		super();
	}
	
	
	/**
	 * Allows to perform the replacement strategy
	 * 
	 * @param p the population where the new solution must be introduced
	 * @param newSol the new solution to be introduced in the population
	 * @param maximize  true if the problem is to maximize, false otherwise
	 */
	public  void execute(JPopulation p, Individual newSol, boolean maximize){
		
		int index= JGapExtension.getIndexOfWorstIndividual(p.getPopulation());
		if((newSol.getFitnessValueDirectly()>p.getPopulation().getChromosome(index).getFitnessValueDirectly() && maximize) ||
				(newSol.getFitnessValueDirectly()<p.getPopulation().getChromosome(index).getFitnessValueDirectly() && !maximize))
		            p.getPopulation().setChromosome(index, newSol);
	
	}
}
