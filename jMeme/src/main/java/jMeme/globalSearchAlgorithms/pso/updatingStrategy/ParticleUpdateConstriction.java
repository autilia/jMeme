/*
 * ParticleUpdateConstriction.java 
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
 * This class implements a particle update strategy. This strategy consists into into updating particle's velocity and position using constriction as confidence.
 * Moreover, this strategy selects a rlocal and rglobal randomly for each particle and for each dimension.
 *
 */
public class ParticleUpdateConstriction extends ParticleUpdate {

	
	private static final long serialVersionUID = -1562236255425448286L;

	/**
	 * Constructor 
	 * 
	 * @param dimension  the number of components of a particle
	 */
	public ParticleUpdateConstriction(int dimension) {
		super(dimension);
	}
	
	/** 
	 * Allows to perform the updating strategy
	 * 
	 * @param swarm  the PSO algorithm during which the updating must be performed
	 * @param particle  the particle which must be undergone to the updating
	 */
	public void update(PSO_Optimizer swarm, Particle particle) {
		
		int size=particle.size();
		DoubleParticleComponent globalBestPosition[] = ((Particle)swarm.getBestSolution()).getBestPosition();
		DoubleParticleComponent particleBestPosition[] = particle.getBestPosition();

		// Update velocity and position
		for( int i = 0; i <size; i++ ) {
			
			// Update velocity
			double velocity = swarm.getConfidence() * ( ((DoubleParticleComponent)particle.getGenes()[i]).getVelocity() 
					+ Math.random() * swarm.getParticleIncrement() * (particleBestPosition[i].doubleValue() - ((Double)particle.getGene(i).getAllele()).doubleValue()) // Local best
					+ Math.random() * swarm.getGlobalIncrement() * (globalBestPosition[i].doubleValue() - ((Double)particle.getGene(i).getAllele()).doubleValue())); // Global best
		
			
			// Update position
			particle.getGene(i).setAllele( ((Double)particle.getGene(i).getAllele()).doubleValue() + velocity);

		}
	}
}
