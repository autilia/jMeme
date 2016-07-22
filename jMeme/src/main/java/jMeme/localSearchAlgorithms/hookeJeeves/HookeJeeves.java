/*
 * HookeJeeves.java 
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
package jMeme.localSearchAlgorithms.hookeJeeves;

import jMeme.core.configurations.LocalSearchAlgorithmConfiguration;
import jMeme.core.configurations.MemeticAlgorithmConfiguration;
import jMeme.core.individuals.Individual;
import jMeme.design.optimizerParameters.HookeJeevesParameters;
import jMeme.localSearchAlgorithms.LocalSearchOptimizer;

/**
 * This class implements the Hooke and Jeeves procedure. Hooke and Jeeves (HJ) is a deterministic pattern search algorithm
 * (Hooke and Jeeves 1961; Rao 1992). It is a direct search algorithm in the sense that it uses only function evaluations
 * and does not need function derivatives. Its main operation consists of a search along the coordinate axes of the
 * search space, using a suitable step size. For more information, see the following work:
 * R. Hooke and T. A. Jeeves, "Direct Search Solution of Numerical and Statistical Problems", Journal of the ACM, Vol. 8, April 1961, pp. 212-229. 
 */
public class HookeJeeves extends LocalSearchOptimizer{

	
	private static final long serialVersionUID = -6940669262369576930L;
	/**
	 * Stores the halting criterion.  Larger values of epsilon give quicker running time, but a	less accurate solution.
	 */
	private double epsilon;
	/**
	 * Stores a user-supplied convergence parameter which should be set to a value between 0.0 and 1.0.	Larger	   
     * values of rho give greater probability of convergence on highly nonlinear functions, at a 
     * cost of more function evaluations.
	 */
	private double rho;

	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run the Hooke and Jeeves procedure
	 */
	public HookeJeeves(LocalSearchAlgorithmConfiguration conf){
		this.configuration=conf;
		epsilon=((HookeJeevesParameters)conf.getLocalComponent().getParameters()).getEpsilon();
		rho=((HookeJeevesParameters)conf.getLocalComponent().getParameters()).getRho();
	}
	
	/**
	 * Constructor
	 * 
	 * @param conf  the configuration object containing all information to run a memetic algorithm where the Hooke and Jeeves procedure is the local search optimizer
	 */
	public HookeJeeves(MemeticAlgorithmConfiguration conf){
		this.configuration=conf;
		epsilon=((HookeJeevesParameters)conf.getLocalComponent().getParameters()).getEpsilon();
		rho=((HookeJeevesParameters)conf.getLocalComponent().getParameters()).getRho();
	}
	
	
	


@Override
public void startToSolve()
{
	   double[]  delta=new double[configuration.getIndividualSize()];
	   double	 steplength;
	  
	   int		   i, keep;
	   
	   
	   Individual startpt=bestSolution;
	   Individual xbefore=startpt.clone();
	  
	   Individual newx=null;
	   
	   delta=initializeDelta(startpt,rho);
	   
	   
	  
	   steplength = rho;
	 
	   this.localReset();
		
		initialEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
		
		while (getFinalConditions().verify(configuration) && (steplength > epsilon)) {

			this.incrementIteration();
			
	
		    /* find best new point, one coord at a time */
		   newx=xbefore.clone();
		   newx = best_nearby(delta, newx);
		   bestSolution=newx;
		   
			
			currentEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
			this.updateLocalEvaluations();
			
			System.out.println(this.printStatistics());
		
			 
		   /* if we made some improvements, pursue that direction */
		   	
		   keep = 1;
		   while ((isFirstBetterThanSecond(newx,xbefore, this.isMaximize())) && (keep == 1) && this.getFinalConditions().verify(configuration)) {
			  
			
			   xbefore=updateInHookeJeeves(delta, newx,xbefore);
			   if(this.getFinalConditions().verify(configuration))
				   break;
			   
			  	 
			  
			   newx = best_nearby(delta, newx);
			   bestSolution=newx;
			  
			   currentEval=configuration.getPerformance().getNumberOfFitnessEvaluations();
				this.updateLocalEvaluations();
				
				System.out.println(this.printStatistics());
			
			  	
			    if (isFirstBetterThanSecond(xbefore, newx,this.isMaximize())){
				   bestSolution=xbefore;
				   break;
			   }
			   /* make sure that the differences between the new */
			   /* and the old points are due to actual */
			   /* displacements; beware of roundoff errors that */
			   /* might cause newf < fbefore */
			   keep = check(delta, newx,xbefore);
		   }
		   if ((steplength >= epsilon) && (isFirstBetterThanSecond(xbefore, newx,this.isMaximize()))) {
			   steplength = steplength * rho;
			   for (i = 0; i < configuration.getIndividualSize(); i++) {
				   delta[i] *= rho;
			   }
			   
			   bestSolution=xbefore;
			   
				 
		   }
		   
	   }
	  
	  
}


private int check(double[] delta, Individual newx, Individual xbefore){
	
	int  keep = 0;
	
	Individual next=(Individual)newx;
	Individual before=(Individual)xbefore;
	
	  int size=configuration.getIndividualSize();
	
	   for (int i = 0; i < size; i++) {
		   keep = 1;
		   double one=((Double)next.getGenes()[i].getAllele()).doubleValue();
		   double two=((Double)before.getGenes()[i].getAllele()).doubleValue();
			
		   if (Math.abs(one - two) > (0.5 * Math.abs(delta[i])))
			   break;
		   else
			   keep = 0;
	   }
	   
	   return keep;
	
}

private Individual updateInHookeJeeves(double[] delta, Individual newx, Individual xbefore){
	
	Individual next=(Individual)newx;
	Individual before=(Individual)xbefore;
	Individual tmp=next.clone();
	
	  int size=configuration.getIndividualSize();
	  
	  for (int i = 0; i < size; i++) {
		  
		  double one=((Double)next.getGenes()[i].getAllele()).doubleValue();
		  double two=((Double)before.getGenes()[i].getAllele()).doubleValue();
		   /* firstly, arrange the sign of delta[] */
		   if ( (one <= two && !this.isMaximize()) || (one >= two && this.isMaximize()))
			   delta[i] = - Math.abs(delta[i]);
		   else
			   delta[i] = Math.abs(delta[i]);
		  
		   /* now, move further in this direction */
		  // before.getGenes()[i].setAllele(one);
		    
		   next.getGenes()[i].setAllele(one + one - two);
		  
	   }
	  
	  
	  xbefore=tmp;
		 
	  
	  next.resetFitness();
	  next.evaluate();
	  
	  
	  return xbefore;
		 
}


private void executeStep(Individual s1, int index, Individual s2, double delta) {
	Individual initial=(Individual)s2;
	Individual current=(Individual)s1;
	current.getGenes()[index].setAllele(((Double)initial.getGenes()[index].getAllele()).doubleValue()+delta);

}

private double[] initializeDelta(Individual s,double rho){
	int size=s.size();
	double[] delta=new double[size];
	
	for (int i = 0; i <size; i++) {
		   delta[i] = Math.abs(((Double)((Individual)s).getGenes()[i].getAllele()).doubleValue() * rho);
		   if (delta[i] == 0.0)
			   delta[i] = rho;
	   }
	
	return delta;
}

private Individual best_nearby(double[] delta, Individual p){
	 
    int nvars=configuration.getIndividualSize();

    Individual newP=p.clone();
    Individual newP2=null;
	   
	 
	   
	   
	   for (int i = 0; i < nvars; i++) {
		   
		   
		   newP2=newP.partialClone();
		   executeStep(newP2,i,p,delta[i]);
		   newP2.evaluate();
		   
		   
		   
		   if (isFirstBetterThanSecond(newP2, newP, this.isMaximize())){
			   newP=newP2;
			   if(this.getFinalConditions().verify(configuration))
				   break;
			   
		   }
		   else {
			   
			   if(this.getFinalConditions().verify(configuration))
				   break;
			   
			   delta[i] = - delta[i];
			   newP2=newP.partialClone();
			   executeStep(newP2,i,p,delta[i]);
			   newP2.evaluate();
			   
			 	
			   if (isFirstBetterThanSecond(newP2, newP,  this.isMaximize()))
				   newP=newP2;
			   
			   if(this.getFinalConditions().verify(configuration))
				   break;
			   
				  
		   }
		   
		   
		  
	   }
	 return newP;
}


}
