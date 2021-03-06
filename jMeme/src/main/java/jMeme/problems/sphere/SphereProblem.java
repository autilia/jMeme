/*
 * SphereProblem.java 
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

package jMeme.problems.sphere;

import java.util.Map;

import org.jgap.InvalidConfigurationException;

import jMeme.core.JConfiguration;
import jMeme.core.Problem;
import jMeme.core.individuals.Individual;
import jMeme.core.individuals.IndividualComponent;
import jMeme.core.individuals.RealIndividualComponent;
import jMeme.globalSearchAlgorithms.pso.DoubleParticleComponent;
import jMeme.globalSearchAlgorithms.pso.Particle;


public class SphereProblem extends Problem{

	public SphereProblem(int num){
		super();
		dimension=num;
	}
	
	

	public SphereProblem(String name, int num){
		super(name);
		dimension=num;
	}

	public Individual prepareIndividualScheme(JConfiguration c) throws InvalidConfigurationException{
		
	    
		
		IndividualComponent[] sampleGenes = new RealIndividualComponent[dimension];

			
			for(int k=0; k<dimension; k++){
				try {
					sampleGenes[k] = new RealIndividualComponent( c, -5.12, 5.12 );
					
				} catch (InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}

			
			return new Individual(c, sampleGenes);
			
		}
		
	
public Particle prepareParticleScheme(JConfiguration c) throws InvalidConfigurationException{
		
	    
		
		IndividualComponent[] sampleGenes = new DoubleParticleComponent[dimension];

			
			for(int k=0; k<dimension; k++){
				try {
					sampleGenes[k] = new DoubleParticleComponent( c, -5.12, 5.12 , -1, 1);
					
				} catch (InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}

			
			return new Particle(c, sampleGenes);
			
		}





@Override
public Individual[] generateDefaultIndividuals(JConfiguration c)
		throws InvalidConfigurationException {
	// TODO Auto-generated method stub
	return null;
}



@Override
public Map computeProblemPerformance(Individual c) {
	//TODO
	return ((JConfiguration)c.getConfiguration()).getPerformance().getProblemPerformances();
}








	
}
