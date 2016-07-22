/*
 * CompetentStudyUtils.java 
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
package jMeme.design.competent;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jMeme.core.Problem;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



/**
 * This class implements some utils to write the results of a competent study in files with extension .xls
 */
public class CompetentStudyUtils {

	
	/**
	 * Allows to print the results of a step of the competent study in a file .xls
	 * 
	 * @param phaseName  the name of the step of the competent study
	 * @param problems  the set of problem instances
	 * @param configurations  the set of the names of the built memetic configurations
	 * @param values  the results of the phase
	 */
	public static void printPhaseResult(String phaseName, Problem[] problems, String[] configurations, double[][] values){

		String filename =phaseName +"_results.xls";
			      WorkbookSettings ws = new WorkbookSettings();
			      ws.setLocale(new Locale("it", "IT"));
			      WritableWorkbook workbook=null;
				try {
					workbook = Workbook.createWorkbook(new File(filename), ws);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      
				WritableSheet s = workbook.createSheet("Sheet1", 0);
			     
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 
					      10, WritableFont.BOLD);
					    WritableCellFormat cf = new WritableCellFormat(wf);
					    try {
							cf.setWrap(true);
						} catch (WriteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				NumberFormat format1 = new NumberFormat("#########0.0");
				WritableCellFormat cellF1 = new WritableCellFormat(format1);
					
				NumberFormat format2 = new NumberFormat("####0.0###############");
				WritableCellFormat cellF2 = new WritableCellFormat(format2);
				
			
				
			int numConfig= configurations.length;
			int numP=problems.length;
					
		    for(int i=0; i<numConfig; i++){
				Label l= new Label(i+1,0, configurations[i], cf);
					 try {
						s.addCell(l);
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
		    
			
		    for(int i=0; i<numP; i++){
				Label l= new Label(0,i+1, problems[i].toString(), cf);
					 try {
						s.addCell(l);
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
				
				
				
				
				for(int i=0; i<numConfig; i++)
				  for(int j=0; j<numP; j++){
					
						
						Number  n= new Number(i+1,j+1,values[j][i],cellF2);
						
						try {
							s.addCell(n);
						} catch (RowsExceededException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (WriteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
				}
				
				try {
					workbook.write();
					try {
						workbook.close();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			            
			    
			    
	}
	
	/**
	 * Allows to print the average of results of a step of the competent study in a file .xls
	 * 
     * @param phaseName  the name of the step of the competent study
	 * @param configurations  the set of the names of the built memetic configurations
	 * @param values  the results of the phase
	 */
	public static void printAverageResult(String phaseName, String[] configurations, double[]values){

		String filename =phaseName +"_results.xls";
			      WorkbookSettings ws = new WorkbookSettings();
			      ws.setLocale(new Locale("it", "IT"));
			      WritableWorkbook workbook=null;
				try {
					workbook = Workbook.createWorkbook(new File(filename), ws);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      
				WritableSheet s = workbook.createSheet("Sheet1", 0);
			     
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 
					      10, WritableFont.BOLD);
					    WritableCellFormat cf = new WritableCellFormat(wf);
					    try {
							cf.setWrap(true);
						} catch (WriteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				NumberFormat format1 = new NumberFormat("#########0.0");
				WritableCellFormat cellF1 = new WritableCellFormat(format1);
					
				NumberFormat format2 = new NumberFormat("####0.0###############");
				WritableCellFormat cellF2 = new WritableCellFormat(format2);
				
			
				
			int numConfig= configurations.length;
					
		    for(int i=0; i<numConfig; i++){
				Label l= new Label(i,0, configurations[i], cf);
					 try {
						s.addCell(l);
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
		    
			
		   
				
				
				
				
				for(int i=0; i<numConfig; i++)
				{	
						
						Number  n= new Number(i,1,values[i],cellF2);
						
						try {
							s.addCell(n);
						} catch (RowsExceededException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (WriteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
				}
				
				try {
					workbook.write();
					try {
						workbook.close();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			            
			    
			    
	}


}
