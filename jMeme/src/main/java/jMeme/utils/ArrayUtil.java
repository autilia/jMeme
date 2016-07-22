/*
 * ArrayUtil.java 
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

package jMeme.utils;


/** This class implements some utilities for arrays as static methods.
 */
public final class ArrayUtil {

    



  /**
   * Allows to sum integer elements of an array
   * 
   * @param arr the array whose the elements must be summed
   * 
   * @return  the sum of the elements of the given array
   */
    public static int sum( int [] arr ) {
	return sum( arr, 0, arr.length );
    }

    /**
     * Allows to sum integer elements of an array contained in a range
     * 
     * @param arr the array whose the elements must be summed
     * @param pos  the initial index of the range
     * @param  lenght  the number of numbers to sum
     * 
     * @return  the sum of the elements of the given array
     */
    public static int sum( int [] arr,
				       int pos, int length ) {
	int sum = 0;
	for( int i=pos; i<pos+length; i++ )
	    sum += arr[i];
	return sum;
    }

    
    /**
     * Allows to compute the minimum value in an integer array
     * 
     * @param arr the array whose the minimum value must be computed
     *
     * @return  the minimum value of the given array
     */
    public static int min( int [] arr ) {
	return min( arr, 0, arr.length );
    }

    
    /**
     * Allows to compute the minimum value among the elements of an integer array contained in a range
     * 
     * @param arr the array whose the minimum value must be computed
     * @param pos  the initial index of the range
     * @param  lenght  the number of numbers in the range
     * 
     * @return  the minimum value among the elements of the given array contained in the given range
     */
    public static int min(int [] arr,
				       int pos, int length ) {
	int min = arr[pos];
	for( int i=pos; i<pos+length; i++ )
	    if( min > arr[i] )
		min = arr[i];
	return min;
    }


    /**
     * Allows to compute the maximum value in an integer array
     * 
     * @param arr the array whose the maximum value must be computed
     *
     * @return  the maximum value of the given array
     */
    public static int max(int [] arr ) {
	return max( arr, 0, arr.length );
    }

    /**
     * Allows to compute the maximum value among the elements of an integer array contained in a range
     * 
     * @param arr the array whose the maximum value must be computed
     * @param pos  the initial index of the range
     * @param  lenght  the number of numbers in the range
     * 
     * @return  the maximum value among the elements of the given array contained in the given range
     */
     public static int max(int [] arr,
				       int pos, int length ) {
	int  max = arr[pos];
	for( int i=pos; i<pos+length; i++ )
	    if( max > arr[i] )
		max = arr[i];
	return max;
    }
     
     /**
      * Allows to print on standard output an integer array
      * 
      * @param arr the array to print
      */
     public static void printArray(int[] arr){
     	
     	int num=arr.length;
     	System.out.print("\n[");
     	for(int i=0; i<num; i++)
     		{
     		System.out.print(arr[i]);
     		if(i!=num-1)
     			System.out.print(",");
     		}
     	System.out.print("]\n");
     	
     }
    

     /**
      * Allows to sum double elements of an array
      * 
      * @param arr the array whose the elements must be summed
      * 
      * @return  the sum of the elements of the given array
      */
     public static double sum( double [] arr ) {
    		return sum( arr, 0, arr.length );
    	    }

     /**
      * Allows to sum double elements of an array contained in a range
      * 
      * @param arr the array whose the elements must be summed
      * @param pos  the initial index of the range
      * @param  lenght  the number of numbers to sum
      * 
      * @return  the sum of the elements of the given array
      */
    	     public static double sum( double [] arr,
    					       int pos, int length ) {
    		double sum = 0;
    		for( int i=pos; i<pos+length; i++ )
    		    sum += arr[i];
    		return sum;
    	    }

    	     /**
    	      * Allows to compute the minimum value in a double array
    	      * 
    	      * @param arr the array whose the minimum value must be computed
    	      *
    	      * @return  the minimum value of the given array
    	      */
    	    public static double min( double [] arr ) {
    		return min( arr, 0, arr.length );
    	    }

    	    /**
    	     * Allows to compute the minimum value among the elements of a double array contained in a range
    	     * 
    	     * @param arr the array whose the minimum value must be computed
    	     * @param pos  the initial index of the range
    	     * @param  lenght  the number of numbers in the range
    	     * 
    	     * @return  the minimum value among the elements of the given array contained in the given range
    	     */
    	     public static double min( double [] arr,
    					       int pos, int length ) {
    		double min = arr[pos];
    		for( int i=pos; i<pos+length; i++ )
    		    if( min > arr[i] )
    			min = arr[i];
    		return min;
    	    }

    	     /**
    	      * Allows to compute the maximum value in a double array
    	      * 
    	      * @param arr the array whose the maximum value must be computed
    	      *
    	      * @return  the maximum value of the given array
    	      */
    	    public static double max( double [] arr ) {
    		return max( arr, 0, arr.length );
    	    }

    	    /**
    	     * Allows to compute the maximum value among the elements of a double array contained in a range
    	     * 
    	     * @param arr the array whose the maximum value must be computed
    	     * @param pos  the initial index of the range
    	     * @param  lenght  the number of numbers in the range
    	     * 
    	     * @return  the maximum value among the elements of the given array contained in the given range
    	     */
    	    public static double max( double [] arr,
    					       int pos, int length ) {
    		double max = arr[pos];
    		for( int i=pos; i<pos+length; i++ )
    		    if( max < arr[i] )
    			max = arr[i];
    		return max;
    	    }

    
    	     /**
    	      * Allows to print on standard output a double array
    	      * 
    	      * @param arr the array to print
    	      */
    public static void printArray(double[] arr){
    	
    	int num=arr.length;
    	System.out.print("\n[");
    	for(int i=0; i<num; i++)
    		{
    		System.out.print(arr[i]);
    		if(i!=num-1)
    			System.out.print(",");
    		}
    	System.out.print("]\n");
    	
    }
    
    
    
  


}
