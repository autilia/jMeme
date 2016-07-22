/*
 * AnnealingScheme.java 
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

import java.io.Serializable;

/**
 * This class represents the generic template for an annealing scheme.
 * The class declares an abstract method <code>updateTemperature</code>, used to update the temperature at each iteration of the SA.
 */
public abstract class AnnealingScheme implements Serializable{
    
	private static final long serialVersionUID = -3603314480879588662L;
	/**
	 * Stores the maximum possible temperature value
	 */
    double tMax;
    /**
	 * Stores the minimum possible temperature value
	 */
    double tMin;

    
   /**
    * Allows to update the temperature value at each iteration of the SA evolution.
    * 
    * @param temperature  the current temperature
    * @param iterations  the current number of iterations
    * 
    * @return  a double value representing the new temperature value
    */
    public abstract double updateTemperature(double temperature, int iterations);


    /**
     * Allows to check if the temperature value is achieved its minimum value
     * 
     * @param temperature  the current temperature value
     * 
     * @return  true if the termination criterion is achieved, false otherwise
     */
    public boolean isCooled(double temperature) {
		return temperature < tMin;
	}


}
