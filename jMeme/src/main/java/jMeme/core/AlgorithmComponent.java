/*
 * AlgorithmComponent.java 
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

import jMeme.design.optimizerParameters.OptimizerParameters;

/**
 * This class contains the information about an optimizer, 
 * identified by means its name, and its parameters.
 */
public class AlgorithmComponent implements java.io.Serializable{
	
	private static final long serialVersionUID = -8837170626204052785L;
	
	/**
	 * Represents the component identifier
	 */
	private String id;
	
	/**
	 * Represents the name of the optimizer
	 */
	private String optimizerName;
	
	/**
	 * Includes all parameters with their values for the specific optimizer.
	 */
	private OptimizerParameters parameters;
	
	/**
	 * Constructor
	 * 
	 * @param id  identifier of the optimizer 
	 * @param optimizerName  name of the optimizer 
	 * @param parameters  object including all parameters for the optimizer
	 */
	public AlgorithmComponent(String id, String optimizerName, OptimizerParameters parameters){
		this.id=id;
		this.optimizerName=optimizerName;
		this.parameters=parameters;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOptimizerName() {
		return optimizerName;
	}

	public void setOptimizerName(String optimizerName) {
		this.optimizerName = optimizerName;
	}

	public OptimizerParameters getParameters() {
		return parameters;
	}

	public void setParameters(OptimizerParameters parameters) {
		this.parameters = parameters;
	}

	
	
	
}
