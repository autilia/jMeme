/*
 * SimpleAnnealingSAScheme.java 
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

public class SimpleAnnealingScheme extends AnnealingScheme {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 769881025397577876L;




	/**
	 * Constructor
	 */
	public SimpleAnnealingScheme() {
		tMax = 0.95;
		tMin = 0.00001;
	}

	/**
	 * Constructor 
	 * 
	 * @param tMin  the minimum possible temperature value
	 * @param tMax  the maximum possible temperature value
	 */
	public SimpleAnnealingScheme(double tMax, double tMin) {
		this.tMax = tMax;
		this.tMin = tMin;
	}

	@Override
	public double updateTemperature(double temperature, int it) {
		return temperature*tMax;
	}

	


	/**
	 * @return string representation of the defined scheme
	 */
	public String toString() {
		return "S";
	}

}
