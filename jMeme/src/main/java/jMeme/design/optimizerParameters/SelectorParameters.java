/*
 * SelectorParameters.java 
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

package jMeme.design.optimizerParameters;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;


/**
 * This class allows to set the parameters for the used selector operator.
 * The parameters are: kind of selector and probability of selection (used only for some kinds of selection).
 */
public class SelectorParameters implements Serializable{
	
	private static final long serialVersionUID = 8861338174718275531L;

    /**
     * Constant indicating the determistic selector operator
     */
	public static final String BEST_CHROMOSOMES= "Best Chromosomes";
	/**
	 * Constant indicating the roulette wheel selection operator
	 */
	public static final String ROULETTE= "Roulette";
	/**
	 * Constant indicating the threshold selection operator
	 */
	public static final String THRESHOLD= "Threshold";
	/**
	 * Constant indicating the tournamet selection operator
	 */
	public static final String TOURNAMENT = "tournament";
	
	
	/**
	 * Stores a number indicating a parameter related to the specified selector
	 * This parameter is (1) the probability to be applied for the deterministic selector; (2) the threshold value for the threshold selector;
	 * (3) the number of individuals involving in the tournament for the tournament selector. This parameter is not used for the roulette wheel selection.
	 */
	private double selectorParameter;
	/**
	 * Stores the name of the selection operator to be defined
	 */
	private String nameSelector;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the deterministic selector as operator and the related parameter equal to 0.9
	 */
	public SelectorParameters(){
		selectorParameter=0.9d;
		nameSelector=this.BEST_CHROMOSOMES;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the deterministic selector as operator and rate as the probability of this selector
	 * 
	 * @param rate  the probability of selection for the deterministic selector operator
	 */
	public SelectorParameters(double rate){
		this.selectorParameter=rate;
		nameSelector=this.BEST_CHROMOSOMES;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the given selector as operator and rate as related parameter.
	 * 
	 * @param rate  parameter related to the given selector name
	 * @param name  name of the selector to use (one of the constants: SelectorParameters.BEST_CHROMOSOMES, SelectorParameters.ROULETTE, SelectorParameters.THRESHOLD, SelectorParameters.TOURNAMENT)
	 */
	public SelectorParameters(double rate, String name){
		this.selectorParameter=rate;
		nameSelector=name;
	}
	
	
	/**
	 * Allows to create a new selector operator starting from a configuration object
	 * @param c  the configuration of an algorithm
	 * @return an instance of a selector operator
	 */
	public NaturalSelector getSelectorOperator(Configuration conf){
		try {
		if(nameSelector.equals(this.BEST_CHROMOSOMES))
				return new BestChromosomesSelector(conf, selectorParameter);
		else if(nameSelector.equals(this.ROULETTE))
			return  new WeightedRouletteSelector(conf);
			else if(nameSelector.equals(this.THRESHOLD))
				return new ThresholdSelector(conf, selectorParameter);
			else if(nameSelector.equals(this.TOURNAMENT))
				 return new TournamentSelector(conf, (int)selectorParameter, 1.0);
			else return null;
		} catch (InvalidConfigurationException e) {
			return null;
		}
		
		
	}
	
	/**
	 * @return string representation of the defined selection operator
	 */
	public String toString(){
		String s="";
		s="Selector type: " + nameSelector + "\n";
		
		String n="Selector rate: ";
		if(this.nameSelector.equals(this.TOURNAMENT))
			n="Tournment size: ";
		if(!this.nameSelector.equals(this.ROULETTE))
		  s+=n + selectorParameter + "\n";
		
		return s;
	}
	
	
	public double getRate() {
		return selectorParameter;
	}


	public void setRate(double rate) {
		this.selectorParameter = rate;
	}



	public String getNameSelector() {
		return nameSelector;
	}

	
	public void setNameSelector(String nameSelector) {
		this.nameSelector = nameSelector;
	}



}
