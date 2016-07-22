/*
 * HyperbolicCosineSAScheme.java 
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

public class HyperbolicCosineSAScheme extends AnnealingScheme {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6069327860730873584L;


	/**
	 * Constructor
	 */
	public HyperbolicCosineSAScheme() {
		tMin = 0.01;
		
		tMax = 9000;
		

	}

	/**
	 * Constructor 
	 * 
	 * @param tMin  the minimum possible temperature value
	 * @param tMax  the maximum possible temperature value
	 */
	public HyperbolicCosineSAScheme(double tMin, double tMax) {
		this.tMin = tMin;
		this.tMax = tMax;
	}


	@Override
	public double updateTemperature(double temperature, int iterations) {

		return tMax / cosh(iterations / (tMax / 10));
	}

	private double cosh(double d) {
		return (Math.exp(d) + Math.exp(-d)) / 2;
	}

	
	/**
	 * @return string representation of the defined scheme
	 */
	public String toString() {
		return "CH";
	}

}
