/*
 * MultiplierSAScheme.java 
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

package jMeme.localSearchAlgorithms.sa.annealingScheme;

import jMeme.localSearchAlgorithms.sa.annealingScheme.AnnealingScheme;

public class MultiplierSAScheme extends AnnealingScheme {

	
	private static final long serialVersionUID = 5294030680398578283L;
	
	/**
	 * Stores step to use during the temperature updating
	 */
	private double step = 0.9;

	/**
	 * Constructor
	 */
	public MultiplierSAScheme() {
		tMax = 100000;
		tMin = 0.01;
	}

	/**
	 * Constructor 
	 * 
	 * @param tMin  the minimum possible temperature value
	 * @param tMax  the maximum possible temperature value
	 */
	public MultiplierSAScheme(double tMin, double tMax) {
		this.tMax = tMax;
		this.tMin = tMin;
	}

	
  @Override
	public double updateTemperature(double temperature, int iterations) {
		
		return tMax * Math.pow(step, iterations);
	}

  
    /**
	 * @return string representation of the defined scheme
	 */
	public String toString() {
		return "M";
	}
}
