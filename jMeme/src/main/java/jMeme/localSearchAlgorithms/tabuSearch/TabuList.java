/*
 * TabuList.java 
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

package jMeme.localSearchAlgorithms.tabuSearch;


import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.TabuSearchParameters;
import jMeme.localSearchAlgorithms.tabuSearch.movements.Movement;


/**
 * This class implements the a tabu list for the TS procedure.
 */
public class TabuList {
	
	/**
	 * Stores a set of tabu individuals
	 */
	private Individual[] tabuList;    
    
	/**
	 * Stores the current position in the tabu list
	 */
    private int   currentPos;   
   
   /**
    * Stores the number of current tabu individuals
    */
    private int   listLength;   
    
    
    
    
    /**
     * Constructor
     */
    public TabuList()
    {   
    	listLength=TabuSearchParameters.TABULISTSIZE_DEFAULT;
    }   
    
    
    /**
     * Constructor
     * 
     * @param size  the size of the tabu list
     */
    public TabuList( int size )
    {
     
        this.listLength = size;
        this.tabuList   = new Individual[ listLength ];
        this.currentPos = 0;
      
    }  
    
    
   /**
    * Allows to determine if an individual undergone to a given movement is a tabu solution
    * 
    * @param sol  the original solution
    * @param move  the generated movement
    * 
    * @return  true if the individual undergone to the given movement is a tabu solution, false otherwise
    */
    public boolean isTabu(Individual sol, Movement move) 
    {   
         
        for( int i = 1; i <=listLength; i++ )
            if ( currentPos - i < 0 )
                return false;
            else
                if ( equalsInTabuList(tabuList[ (currentPos-i) % listLength ],sol) ){
                	
                	return true;}
        return false;
    }   
    
  /**
   * Allows to insert a solution in the tabu list 
   * 
   * @param sol  the solution to be inserted in the tabu list
   */
    public void setTabu(Individual sol) 
    {        
        tabuList[ (currentPos++) % listLength ] = sol;
    }   
    
    /**
     * Allows to determine if two solutions contained in the tabu list are equal.
     * 
     * @param s1  one solution
     * @param s2  one solution
     * 
     * @return  true if the two solutions in the tabu list are equal, false otherwise
     */
	public boolean equalsInTabuList(Individual s1, Individual s2){
		
		
		int size1=s1.getIndividualComponents().length;
		int size2=s2.getIndividualComponents().length;
		
		if(size1!=size2)
			return false;
		
		for(int i=0;i<size1;i++)
			if((s1.getIndividualComponent(i).getAllele())!=(s2.getIndividualComponent(i).getAllele()))
				return false;
		
		return true;
	}


	

}
