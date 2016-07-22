/*
 * ParticleUpdate.java 
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

package jMeme.globalSearchAlgorithms.pso.updatingStrategy;

import java.io.Serializable;

import jMeme.globalSearchAlgorithms.pso.Particle;
import jMeme.globalSearchAlgorithms.pso.PSO_Optimizer;

/**
 * This class implements the generic template for a particle update strategy.
 */
public abstract class ParticleUpdate implements Serializable{
	
	
	private static final long serialVersionUID = 3096827330273865293L;
	/**
	 * Stores the number of the components of a particle
	 */
	protected int dimension;
	
	/**
	 * Constructor 
	 * 
	 * @param dimension  the number of components of a particle
	 */
	public ParticleUpdate(int dimension) {
		this.dimension=dimension;
	}
	
	/** 
	 * Allows to initialize random vectors used for local and global updates (rlocal[] and rother[]).
	 * This method is called at the begining of each iteration.
	 */
	public void begin(PSO_Optimizer swarm) {
	}
	
	/** 
	 * Allows to perform the updating strategy. This method must be overwritten by all classes that inherit this class.
	 * 
	 * @param swarm  the PSO algorithm during which the updating must be performed
	 * @param particle  the particle which must be undergone to the updating
	 */
	public abstract void update(PSO_Optimizer swarm, Particle particle);
	
	/** 
	 * Allows to define tasks to be performed after an iteration.
	 * This method is called at the end of each iteration.
	 * 
	 * @param swarm  the PSO algorithm during which the updating must be performed
	 */
	public void end(PSO_Optimizer swarm) {
	}
	

}
