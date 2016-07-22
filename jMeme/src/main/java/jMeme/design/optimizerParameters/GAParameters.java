/*
 * GAParameters.java 
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

package jMeme.design.optimizerParameters;

/**
 * This class allows to set the parameters for a genetic algorithm.
 * The parameters are: the population size, mutation operator, crossover operator and selector operator.
 */
public class GAParameters extends GlobalSearchOptimizerParameters{

	
	/**
	 * Stores the object containing all information about the mutation to be used
	 */
	private MutationParameters mutation;
	/**
	 * Stores the object containing all information about the crossover to be used
	 */
	private CrossoverParameters crossover;
	/**
	 * Stores the object containing all information about the selection to be used
	 */
	private SelectorParameters selector;
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and as operators: the Uniform mutation, the single point crossover, the deterministic selector
	 */
	public GAParameters()
	{
		super();
		crossover=new CrossoverParameters();
		mutation=new MutationParameters();
		selector=new SelectorParameters();
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the Uniform mutation, the single point crossover, the deterministic selector.
	 * 
	 * @param pop the population size
	 */
	public GAParameters(int pop)
	{
		super(pop);
		crossover=new CrossoverParameters();
		mutation=new MutationParameters();
		selector=new SelectorParameters();
				
	}
	

	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the Uniform mutation with mut as probability, the single point crossover with cross as probability, the deterministic selector.
	 * 
	 * @param pop  the population size
	 * @param mut  the mutation probability
	 * @param cross the crossover probability
	 */
	public GAParameters(int pop, int mut, double cross)
	{
		super(pop);
		crossover=new CrossoverParameters(cross);
		mutation=new MutationParameters(mut);
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and as operators: the Uniform mutation with mut as probability, the single point crossover with cross as probability, the deterministic selector.
	 * 
	 * @param mut  the mutation probability
	 * @param cross the crossover probability
	 */
	public GAParameters( int mut, double cross)
	{
		super();
		crossover=new CrossoverParameters(cross);
		mutation=new MutationParameters(mut);
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the Uniform mutation with mut as probability, the single point crossover with cross as probability, the selector sf.
	 * 
	 * @param pop  the population size
	 * @param mut  the mutation probability
	 * @param cross the crossover probability
	 * @param sf  the object containing all information about the selector to use
	 */
	public GAParameters(int pop, int mut, double cross, SelectorParameters sf)
	{
		super(pop);
		crossover=new CrossoverParameters(cross);
		mutation=new MutationParameters(mut);
		selector=sf;
		
	}
	
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population 20 to pop and as operators: the Uniform mutation with mut as probability, the single point crossover with cross as probability, the selector sf.
	 * 
	 * @param mut  the mutation probability
	 * @param cross the crossover probability
	 * @param sf  the object containing all information about the selector to use
	 */
	public GAParameters( int mut, double cross, SelectorParameters sf)
	{
		super();
		crossover=new CrossoverParameters(cross);
		mutation=new MutationParameters(mut);
		selector=sf;
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the mutation operator mf, the single point crossover with cross as probability, the deterministic selector.
	 * 
	 * @param pop the population size
	 * @param mf  the object containing all information about the mutation to use
	 * @param cross the crossover probability
	 */
	public GAParameters(int pop, MutationParameters mf, double cross)
	{
		super(pop);
		crossover=new CrossoverParameters(cross);
		mutation=mf;
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and as operators: the mutation operator mf, the single point crossover with cross as probability, the deterministic selector.
	 * 
	 * @param mf  the object containing all information about the mutation to use
	 * @param cross the crossover probability
	 */
	public GAParameters( MutationParameters mf, double cross)
	{
		super();
		crossover=new CrossoverParameters(cross);
		mutation=mf;
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the mutation operator mf, the crossover operator cf, the deterministic selector.
	 * 
	 * @param pop the population size
	 * @param mf  the object containing all information about the mutation to use
	 * @param cf  the object containing all information about the crossover to use 
	 */
	public GAParameters(int pop, MutationParameters mf, CrossoverParameters cf)
	{
		super(pop);
		crossover=cf;
		mutation=mf;
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and as operators: the mutation operator mf, the crossover operator cf, the deterministic selector.
	 * 
	 * @param mf  the object containing all information about the mutation to use
	 * @param cf  the object containing all information about the crossover to use 
	 */
	public GAParameters( MutationParameters mf, CrossoverParameters cf)
	{
		super();
		crossover=cf;
		mutation=mf;
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the Uniform mutation with mut as probability, the crossover operator cross, the deterministic selector.
	 * 
	 * @param pop  the population size
	 * @param mut  the mutation probability
	 * @param cross  the object containing all information about the crossover to use 
	 */
	public GAParameters(int pop, int mut, CrossoverParameters cross)
	{
		super(pop);
		crossover=cross;
		mutation=new MutationParameters(mut);
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and as operators: the Uniform mutation with mut as probability, the crossover operator cross, the deterministic selector.
	 * 
	 * @param mut  the mutation probability
	 * @param cross  the object containing all information about the crossover to use 
	 */
	public GAParameters( int mut, CrossoverParameters cross)
	{
		super();
		crossover=cross;
		mutation=new MutationParameters(mut);
		selector=new SelectorParameters();
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the Uniform mutation with mut as probability, the crossover operator cross, the selector operator sf.
	 * 
	 * @param pop  the population size
	 * @param mut  the mutation probability
	 * @param cross  the object containing all information about the crossover to use  
	 * @param sf  the object containing all information about the selector to use
	 */
	public GAParameters(int pop, int mut, CrossoverParameters cross, SelectorParameters sf)
	{
		super(pop);
		crossover=cross;
		mutation=new MutationParameters(mut);
		selector=sf;
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and as operators: the Uniform mutation with mut as probability, the crossover operator cross, the selector operator sf.
	 * 
	 * @param mut  the mutation probability
	 * @param cross  the object containing all information about the crossover to use 
	 * @param sf  the object containing all information about the selector to use
	 */
	public GAParameters( int mut, CrossoverParameters cross, SelectorParameters sf)
	{
		super();
		crossover=cross;
		mutation=new MutationParameters(mut);
		selector=sf;
		
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to pop and as operators: the mutation operator mf, the crossover operator cf, the selector operator sf.
	 * 
	 * @param pop  the population size
	 * @param mf  the object containing all information about the mutation to use
	 * @param cf  the object containing all information about the crossover to use 
	 * @param sf  the object containing all information about the selector to use
	 */
	public GAParameters(int pop, MutationParameters mf, CrossoverParameters cf, SelectorParameters sf)
	{
		super(pop);
		crossover=cf;
		mutation=mf;
		selector=sf;
	}
	
	/**
	 * Costructor
	 * 
	 * Creates an instance of the class that sets the population size to 20 and as operators: the mutation operator mf, the crossover operator cf, the selector operator sf.
	 * 
	 * @param mf  the object containing all information about the mutation to use
	 * @param cf  the object containing all information about the crossover to use
	 * @param sf  the object containing all information about the selector to use
	 */
	public GAParameters(MutationParameters mf, CrossoverParameters cf, SelectorParameters sf)
	{
		super();
		crossover=cf;
		mutation=mf;
		selector=sf;
	}
	
	
	


	/**
	 * @return string representation of the defined genetic algorithm setting
	 */
    public String toString(){
	  String s="";
	  s+=crossover.toString() + mutation.toString() + selector.toString();
	  return s;
    }


	


	public MutationParameters getMutation() {
		return mutation;
	}
	
	public void setMutation(MutationParameters mutation) {
		this.mutation = mutation;
	}

	public CrossoverParameters getCrossover() {
		return crossover;
	}
	public void setCrossover(CrossoverParameters crossover) {
		this.crossover = crossover;
	}
	
	public SelectorParameters getSelector() {
		return selector;
	}

	public void setSelector(SelectorParameters selector) {
		this.selector = selector;
	}
	
}
