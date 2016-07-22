/*
 * Movement.java 
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

public class Movement {
	
	/**
	 * Stores the position of the movement
	 */
	protected int position;
	/**
	 * Stores the value of the movement
	 */
	protected Object value;
	/**
	 * Stores the precedent value contained in the position of the movement
	 */
	protected Object precValue;
	/**
	 * Stores the fitness value of the solution without the movement
	 */
	protected double precFitness;
	/**
	 * Stores an index for the movement  
	 */
	protected int moveNumber;




   /**
    * Constructor 
    * 
    * @param moveNumber  an index for the movement
    * @param position  the position of the movement
    * @param value  the value of the movement
    * @param precValue  the precedent value contained in the position of the movement
    * @param precFitness  the fitness value of the solution without the movement
    */
	public Movement(int moveNumber, int position, Object value, Object precValue, double precFitness) {
		this.position = position;
		this.value = value;
		this.precValue = precValue;
		this.precFitness=precFitness;
		this.moveNumber = moveNumber;

		
	}

	public int getMovementNumber() {
		return moveNumber;
	}

	
	/**
	 * Allows to perform a movement on a given solution
	 * 
	 * @param soln  one solution
	 */
	public void move(Individual soln) {

		performMovement(soln, position, value);
		soln.resetFitness();
	}

	/**
	 * Allows to undo the movement on a given solution
	 * 
	 * @param soln   one solution
	 */
	public void undo(Individual soln) {
		
		performMovement(soln, position,precValue);
		soln.setFitness(precFitness);
	}

	private void performMovement(Individual s, int pos, Object value) {
	    (((Individual)s).getIndividualComponents())[pos].setAllele(value);
	
	}
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		Movement m = (Movement) o;

		if (m.position == this.position && m.value == this.value) {
			return true;
		}
		return false;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getPrecValue() {
		return precValue;
	}

	public void setPrecValue(Object precValue) {
		this.precValue = precValue;
	}

	public double getPrecFitness() {
		return precFitness;
	}

	public void setPrecFitness(double precFitness) {
		this.precFitness = precFitness;
	}
	

	public int getMoveNumber() {
		return moveNumber;
	}



	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}

}
