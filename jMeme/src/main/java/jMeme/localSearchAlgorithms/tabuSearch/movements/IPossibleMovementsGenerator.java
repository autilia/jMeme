/*
 * IPossibleMovementsGenerator.java 
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

package jMeme.localSearchAlgorithms.tabuSearch.movements;

import jMeme.core.individuals.Individual;


/**
 * This interface must be implemented by all classes which must generate possible movements during TS procedure.
 *
 */
public interface IPossibleMovementsGenerator extends java.io.Serializable
{

    /**
     * Allows to generate a set of possible movements for the given solution
     * 
     * @param solution  a solution whose a set of movements must be generated
     * 
     * @return  a set of possible movements
     */
    public Movement[] getPosibleMovements( Individual solution );



}


