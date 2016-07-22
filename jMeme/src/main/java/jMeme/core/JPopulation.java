/*
 * JPopulation.java 
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

package jMeme.core;

import jMeme.core.configurations.GlobalSearchAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.globalSearchAlgorithms.GlobalSearchOptimizer;

import java.util.List;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;



/**
 * This class represents the set of solutions evolved by an algorithm. A population
 * may be constructed normally via constructor, or the static
 * randomInitialPopulation() method can be used to generate a population with a
 * randomized solutions.
 */
public class JPopulation extends org.jgap.Genotype{

	
	private static final long serialVersionUID = -3535068185028210817L;
	
	
	/**
	 * 
	 * @param a_configuration
	 * @param a_population
	 * @throws InvalidConfigurationException
	 */
	public JPopulation(JConfiguration a_configuration, Population a_population)
			throws InvalidConfigurationException {
		super(a_configuration, a_population);
	
	}
	
	/**
	 * Constructor 
	 * 
	 * @param a_configuration the configuration object to use
	 * @param inds the set of solutions to be managed by this population instance
	 * @throws InvalidConfigurationException
	 */
	public JPopulation(JConfiguration a_configuration, Individual[] inds)
			throws InvalidConfigurationException {
		super(a_configuration, new Population(a_configuration, inds));
	
	}

	
	/**
	 * Constructor 
	 * 
	 * @param a_configuration  the configuration object to use
	 * @param popSize the size of the population to generate
	 * @throws InvalidConfigurationException
	 */
	public JPopulation(JConfiguration a_configuration, int popSize)
			throws InvalidConfigurationException {
		super(a_configuration, new Population(a_configuration, popSize));
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Evolves the population by using an optimizer specified in the configuration object.
	 */
	@Override
	 public synchronized void evolve() {
		    GlobalSearchOptimizer breeder =(GlobalSearchOptimizer) ((GlobalSearchAlgorithmConfiguration)getConfiguration()).getGlobalOptimizer();
		    JPopulation newPop = breeder.execute(this, (JConfiguration)getConfiguration());
		    setPopulation(newPop.getPopulation());
		  }
	
	
	/**
	 * 
	 * Convenience method that returns a newly constructed population
	 * instance configured according to the given configuration instance.
	 * The population of individuals will be created according to the setup of
	 * the sample individual in the configuration object, but the variable values
	 * will be set to random legal values.
	 *
	 * @param a_configuration the current active configuration object
	 * @return a newly constructed population instance
	 *
	 * @throws IllegalArgumentException if the given configuration object is null
	 * @throws InvalidConfigurationException if the given configuration
	 * instance is not in a valid state
	 *
	 */
	  public static JPopulation randomInitialPopulation(JConfiguration
		      a_configuration)
		      throws InvalidConfigurationException {
		    if (a_configuration == null) {
		      throw new IllegalArgumentException(
		          "The Configuration instance may not be null.");
		    }
		    a_configuration.lockSettings();
		    // Create an array of chromosomes equal to the desired size in the
		    // active Configuration and then populate that array with Chromosome
		    // instances constructed according to the setup in the sample
		    // Chromosome, but with random gene values (alleles). The Chromosome
		    // class randomInitialChromosome() method will take care of that for
		    // us.
		    // ------------------------------------------------------------------
		    int populationSize = a_configuration.getPopulationSize();
		    Population pop = new Population(a_configuration, populationSize);
		    // Do randomized initialization.
		    // -----------------------------
		    JPopulation result = new JPopulation((JConfiguration)a_configuration, pop);
		    result.fillPopulation(populationSize);
		    return result;
		  }
	
	
	public Individual getIndividual(int index){
		
		return (Individual)super.getPopulation().getChromosomes().get(index);
	}
	
	public List getIndividuals(){
		return super.getPopulation().getChromosomes();
	}
	
	public void setIndividuals(List individuals){
		super.getPopulation().setChromosomes(individuals);
	}
	
	public void setIndividual(Individual s, int index){
		 super.getPopulation().setChromosome(index, s);
	}

	
	public Individual getFittestIndividual(){
		return (Individual)super.getFittestChromosome();
	}
	
	public Individual getFittestIndividual(int a, int b){
		return (Individual)super.getFittestChromosome(a,b);
	}

	public List getFittestIndividuals(int numInd){
		return super.getFittestChromosomes(numInd);
	}
	
	public Population getPopulation(){
		return super.getPopulation();
	}
	
	
	public void addIndividual(Individual s){
		super.getPopulation().addChromosome(s);
	}
	
	
	public int getPopulationSize(){
		return super.getPopulation().size();
	}
	
	
	public int indexOfIndividual(Individual ind){
		return super.getPopulation().getChromosomes().indexOf(ind);
	}
	
	
	@Deprecated
	public static Genotype randomInitialGenotype(Configuration
		      a_configuration)
	{
		try {
			return Genotype.randomInitialGenotype(a_configuration);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Deprecated
	public List getFittestChromosomes(int a){
		return super.getFittestChromosomes(a);
	}
	
	@Deprecated
	public IChromosome getFittestChromosome(){
		return super.getFittestChromosome();
	}
	
	
	@Deprecated
	public IChromosome getFittestChromosomes(int a, int b){
		return super.getFittestChromosome(a,b);
	}
	
	
}
