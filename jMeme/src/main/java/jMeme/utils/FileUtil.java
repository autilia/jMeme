/*
 * FileUtil.java 
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


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

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
 * This class implements a set of mechanisms to write results of the executed tests.
 */
public class FileUtil {

	
	/**
	 * Allows to write a string to a file
	 * 
	 * @param file  the name of the file
	 * @param dataString the string to write
	 * 
	 * @return  true if the writing is successfull, false otherwise
	 */
	public static boolean writeFile (File file, String dataString) {
	    try {
	       BufferedWriter out =new BufferedWriter (new FileWriter (file));
	       out.write(dataString);
	       out.flush ();
	       out.close ();
	    }
	    catch (IOException e) {
	       return false;
	    }
	    return true;
	  } 
	
	
	/**
	 * Allows to append a string to a file
	 * 
	 * @param file  the name of the file
	 * @param dataString the string to append
	 * 
	 * @return  true if the writing is successfull, false otherwise
	 */
	public static boolean appendFile (File file, String dataString) {
	    try {
	       BufferedWriter out =new BufferedWriter (new FileWriter (file,true));
	       out.append(dataString);
	       out.flush ();
	       out.close ();
	    }
	    catch (IOException e) {
	       return false;
	    }
	    return true;
	  } 
	
	/**
	 * Allows to write to an Excel file the results of the runs executed in a test
	 * 
	 * @param v  a vector containing the results of the test
	 * @param nameF  the name of the file where the results of the test must be written
	 */
	public static void writeRunsToExcel(Vector v, String nameF){
		
		String filename = nameF+".xls";
	      WorkbookSettings ws = new WorkbookSettings();
	      ws.setLocale(new Locale("it", "IT"));
	      WritableWorkbook workbook=null;
		try {
			workbook = Workbook.createWorkbook(new File(filename), ws);
		} catch (IOException e) {
			e.printStackTrace();
		}
	      
		WritableSheet s = workbook.createSheet("Sheet1", 0);
	     
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 
			      10, WritableFont.BOLD);
			    WritableCellFormat cf = new WritableCellFormat(wf);
			    try {
					cf.setWrap(true);
				} catch (WriteException e) {
					e.printStackTrace();
				}

		NumberFormat format1 = new NumberFormat("#########0.0");
		WritableCellFormat cellF1 = new WritableCellFormat(format1);
			
		NumberFormat format2 = new NumberFormat("####0.0###############");
		WritableCellFormat cellF2 = new WritableCellFormat(format2);
		
	
		
		
			
       Iterator it=((HashMap)v.get(0)).keySet().iterator();
		
        int i=0;
      
      while(it.hasNext()){
			String ss=(String)it.next();
			Label l= new Label(i,0, ss, cf);
			 try {
				s.addCell(l);
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		
		
		
		
		Iterator iter= v.iterator();
		
		int index=1;
		while(iter.hasNext()){
			
			HashMap map= (HashMap)iter.next();
			
			Iterator iter2=map.keySet().iterator();
			int k=0;
			while(iter2.hasNext()){
				String st=(String)iter2.next();
				
				Object o= map.get(st);
				
				Number  n=null;
				if(o instanceof Double)
					n = new Number(k,index,(Double)o,cellF2);
				else if(o instanceof Integer)
					n= new Number(k,index,(Integer)o,cellF1);
				else if (o instanceof Long)
					n= new Number(k,index,(Long)o,cellF1);
				
			
				try {
					s.addCell(n);
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
				
				k++;
			}
			index++;
				
		}
		
		try {
			workbook.write();
			try {
				workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	            
	    
	    
	}
	


}
