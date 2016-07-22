/*
 * IntegerIndividualComponent.java 
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

package jMeme.core.individuals;

import org.jgap.InvalidConfigurationException;

import jMeme.core.JConfiguration;


/**
 * This class implements an integer component of an individual
 */
public class IntegerIndividualComponent extends org.jgap.impl.IntegerGene implements IndividualComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 388009498219004465L;

	

	/**
	   * Constructs a new integer individual component with default settings.
	   *
	   * @param a_config the configuration to use
	   * @throws InvalidConfigurationException
	   *
	   */
	public IntegerIndividualComponent(JConfiguration conf)
			throws InvalidConfigurationException {
		super(conf);
		
	}
	
	
	  /**
	   * Constructs a new integer individual component with the specified lower and upper
	   * bounds.
	   *
	   * @param a_config the configuration to use
	   * @param a_lowerBound the lowest value that this particle component may possess, inclusively
	   * @param a_upperBound the highest value that this particle component may possess, inclusively
	   * @throws InvalidConfigurationException
	   */
	public IntegerIndividualComponent(JConfiguration conf, int l, int u)
			throws InvalidConfigurationException {
		super(conf, l, u);
		
	}
	
	
	 /**
	   * Provides an implementation-independent means for creating new individual component instances.
	   *
	   * @return a new individual component instance of the same type and with the same setup as this concrete individual component
	   */
	 protected IndividualComponent newGeneInternal() {
		    try {
		      IntegerIndividualComponent result= new IntegerIndividualComponent((JConfiguration)getConfiguration(), this.getLowerBound(),
		                                         this.getUpperBound());
		      return result;
		    }
		    catch (InvalidConfigurationException iex) {
		      throw new IllegalStateException(iex.getMessage());
		    }
		  }
	 
	 
	  public int getLowerBound() {
	    return super.getLowerBounds();
	  }

	  
	  public int getUpperBound() {
	    return super.getUpperBounds();
	  }

	  
	  public Object getValue(){
			return super.getAllele();
		}
		
		@Deprecated
		public Object getAllele(){
			return super.getAllele();
		}
		
		
		public void setValue(Object a_newValue) {
			super.setAllele(a_newValue);
		}

		@Deprecated
		public void setAllele(Object a_newValue){
			super.setAllele(a_newValue);
		}
	
}
