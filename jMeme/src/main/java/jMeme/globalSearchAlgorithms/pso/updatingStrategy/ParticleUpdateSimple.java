/*
 * ParticleUpdateSimple.java 
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

import jMeme.globalSearchAlgorithms.pso.DoubleParticleComponent;
import jMeme.globalSearchAlgorithms.pso.PSO_Optimizer;
import jMeme.globalSearchAlgorithms.pso.Particle;

/**
 * This class implements a particle update strategy. This strategy consists into updating particle's velocity and position using inertia as confidence.
 */
public class ParticleUpdateSimple extends ParticleUpdate {
	
	
	private static final long serialVersionUID = 3420473889360735025L;
	/**
	 * Stores the random vector for local update
	 */
	double rlocal[];
	/**
	 * Stores random vector for global update
	 */
	double rglobal[];

	/**
	 * Constructor 
	 * 
	 * @param dimension  the number of components of a particle
	 */
	public ParticleUpdateSimple(int dimension) {
		super(dimension);
		rlocal = new double[dimension];
		rglobal = new double[dimension];
	}
	
	/** 
	 * Allows to initialize random vectors used for local and global updates (rlocal[] and rglobal[])
	 */
	public void begin() {
		int i,dim = dimension;
		for( i=0 ; i < dim ; i++ ) {
			rlocal[i] = Math.random();
			rglobal[i] = Math.random();
		}
	}
	
	/** 
	 * Allows to perform the updating strategy
	 * 
	 * @param swarm  the PSO algorithm during which the updating must be performed
	 * @param particle  the particle which must be undergone to the updating
	 */
	public void update(PSO_Optimizer swarm, Particle particle) {
	
		
		int size=particle.size();
		DoubleParticleComponent globalBestPosition[] =  ((Particle)swarm.getBestSolution()).getBestPosition();
		DoubleParticleComponent particleBestPosition[] = particle.getBestPosition();
		
		// Update velocity and position
		for( int i = 0; i < size; i++ ) {
			// Update velocity
			double velocity = swarm.getConfidence() * ((DoubleParticleComponent)particle.getGenes()[i]).getVelocity()  // Inertia
					+ rlocal[i] * swarm.getParticleIncrement() * (particleBestPosition[i].doubleValue() - ((Double)particle.getGene(i).getAllele()).doubleValue()) // Local best
					+ rglobal[i] * swarm.getGlobalIncrement() * (globalBestPosition[i].doubleValue() - ((Double)particle.getGene(i).getAllele()).doubleValue()); // Global best
			
			
			
			// Update position
			particle.getGene(i).setAllele(((Double)particle.getGene(i).getAllele()).doubleValue() + velocity);
		}
	}
	
}
