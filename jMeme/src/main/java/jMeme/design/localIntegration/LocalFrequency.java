/*
 * LocalFrequency.java 
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

package jMeme.design.localIntegration;

import java.io.Serializable;


/**
 * This class implements one of the main components influencing the integration between local and global searches: the local frequency.
 * The local frequency  defines the proportion of individuals in each population that will undergo individual learning.
 */
public class LocalFrequency implements Serializable{

	private static final long serialVersionUID = -7893281238225481359L;

    /**
     * Stores default value for the local frequency
     */
	public static final int DEFAULT_FREQUENCY_PERCENTAGE=1;
	
	
	/**
	 * Stores the percentage of the population which must be undergone to the local procedure
	 */
	private double frequency;
	
	/**
	 * Constructor
	 */
	public LocalFrequency(){
		this(LocalFrequency.DEFAULT_FREQUENCY_PERCENTAGE);
	}
	
	/**
	 * Constructor 
	 * 
	 * @param freq the percentage of the population which must be undergone to the local procedure
	 */
	public LocalFrequency(double freq){
		frequency=freq;
	}
	
	/**
	 * representation string of the local frequency
	 */
	public String toString(){
		String s="";
		s+="Local Frequency: " + this.getFrequency();
		
		return s;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	

	
}
