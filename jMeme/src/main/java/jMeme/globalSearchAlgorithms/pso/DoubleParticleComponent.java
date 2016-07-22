/*
 * DoubleParticleComponent.java 
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
package jMeme.globalSearchAlgorithms.pso;

import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;

import jMeme.core.JConfiguration;
import jMeme.core.individuals.IndividualComponent;
import jMeme.core.individuals.RealIndividualComponent;


/**
 * This class implements a real component of a particle to be used in particle swarm optimization algorithms.
 */
public class DoubleParticleComponent  extends RealIndividualComponent {
	 
	 	  
	private static final long serialVersionUID = -4015455434715707371L;
	/**
	 * Stores the maximum value of velocity for this particle component
	 */
	private double maxVelocity;
	  /**
	 * Stores the minimum value of velocity for this particle component
	 */
	private double minVelocity;
	  
	  /**
	 * Stores the velocity value
	 */
	private double velocity;

	
	  /**
	   * Constructs a new real particle component with default settings.
	   *
	   * @param a_config the configuration to use
	   * @throws InvalidConfigurationException
	   *
	   */
	  public DoubleParticleComponent(JConfiguration a_config)
	      throws InvalidConfigurationException {
	    this(a_config, - (Double.MAX_VALUE / 2),
	         Double.MAX_VALUE / 2, - (Double.MAX_VALUE / 2),
	         Double.MAX_VALUE / 2 );
	  }

	  /**
	   * Constructs a new real particle component with the specified lower and upper
	   * bounds.
	   *
	   * @param a_config the configuration to use
	   * @param a_lowerBound the lowest value that this particle component may possess, inclusively
	   * @param a_upperBound the highest value that this particle component may possess, inclusively
	   * @param minVelocity  the lowest value that the velocity of this particle component may possess, inclusively
	   * @param maxVelocity  the highest value that the velocity of this particle component may possess, inclusively
	   * @throws InvalidConfigurationException
	   */
	  public DoubleParticleComponent(JConfiguration a_config, double a_lowerBound,
	                 double a_upperBound, double minVelocity,  double maxVelocity)
	      throws InvalidConfigurationException {
	    super(a_config, a_lowerBound, a_upperBound);
	    this.minVelocity = minVelocity;
	    this.maxVelocity = maxVelocity;
	  }

	  /**
	   * Provides an implementation-independent means for creating new particle component instances.
	   *
	   * @return a new particle component instance of the same type and with the same setup as this concrete particle component
	   */
	  protected IndividualComponent newGeneInternal() {
	    try {
	      DoubleParticleComponent result = new DoubleParticleComponent((JConfiguration)getConfiguration(), this.getLowerBound(),
	                                         this.getUpperBound(), minVelocity, maxVelocity);
	      return result;
	    }
	    catch (InvalidConfigurationException iex) {
	      throw new IllegalStateException(iex.getMessage());
	    }
	  }


	 
	  public void setToRandomValue(RandomGenerator a_numberGenerator) {
	    // maps the randomly determined value to the current bounds.
	    // ---------------------------------------------------------
	    Double d=new Double( (this.getUpperBound() - this.getLowerBound()) *
                a_numberGenerator.nextDouble() + this.getLowerBound());
	    setValue(d);
	  }
	  
	  public void setToRandomVelocity(RandomGenerator a_numberGenerator) {
		    // maps the randomly determined value to the current bounds.
		    // ---------------------------------------------------------
		    setVelocity(new Double( (maxVelocity - minVelocity) *
		                         a_numberGenerator.nextDouble() + minVelocity));
		  }
	  
	 
	  /**
	   * Allows to map the value of this ParticleDoubleComponent to within the bounds specified by
	   * the m_upperBounds and m_lowerBounds instance variables. 
	   */
	  protected void mapValueToWithinBounds() {
	    if (getValue() != null) {
	      Double d_value = ( (Double) getValue());
	      // If the value exceeds either the upper or lower bounds, then
	      // map the value to within the legal range. To do this, we basically
	      // calculate the distance between the value and the double min,
	      // then multiply it with a random number and then care that the lower
	      // boundary is added.
	      // ------------------------------------------------------------------
	      if (d_value.doubleValue() > this.getUpperBound() ){
	          setValue(this.getUpperBound());
	          if(velocity > maxVelocity)
	        	  //velocity=maxVelocity;
	        	  velocity=-velocity*0.5;
	          }
	      else if (d_value.doubleValue() <this.getLowerBound() ){
	          setValue(this.getLowerBound());
	          if(velocity < minVelocity)
	        	 // velocity=minVelocity;
	        	  velocity=-velocity*0.5;
	      }
	    }
	  }
	  
	

	  /**
	   * @return string representation of this particle component
	   */
	  public String toString() {
	    String s = "ParticleDoubleComponent(" + this.getLowerBound() + "," + this.getUpperBound() + ")"
	        + "=";
	    if (getInternalValue() == null) {
	      s += "null";
	    }
	    else {
	      s += getInternalValue().toString();
	    }
	    return s;
	  }

	
	public double getMaxVelocity() {
		return maxVelocity;
	}

	
	public void setMaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	
	public double getMinVelocity() {
		return minVelocity;
	}


	public void setMinVelocity(double minVelocity) {
		this.minVelocity = minVelocity;
	}
	  
	  
	  
	
	public double getVelocity() {
		    return velocity;
		  }
	  

	public void setVelocity(double value) {
		    velocity=value;
		  }

	
}
