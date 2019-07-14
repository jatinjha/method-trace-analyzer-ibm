package com.ibm.log;

import java.io.BufferedReader;   
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import processdemo.ProcessBuilderDemo;
import wagu.Board;
import wagu.Table;

public class CompareLogFile extends JFrame{

  //============================== data required to draw graph ================================================
			public static HashMap<String,Double> chart_first = new HashMap<>();
			public static HashMap<String,Double> chart_second = new HashMap<>();
			
	
//======================================= main function =================================================================================	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
	   
	    // data structure to hold content of first log file  
		ArrayList<String> trace_data = new ArrayList<>();
		ArrayList<String> time_trace = new ArrayList<>();
		ArrayList<String> event_trace = new ArrayList<>();
		HashMap<String,String> method_pos = new HashMap<>();
		Stack<String> jstacktrace = new Stack<>();
		HashMap<String,Double> first_file_time = new HashMap<>();
		HashMap<String,Integer> method_freq = new HashMap<>(); 
		
		// data structure to hold content of second log file 
		ArrayList<String> trace_data1 = new ArrayList<>();
		ArrayList<String> time_trace1 = new ArrayList<>();
		ArrayList<String> event_trace1 = new ArrayList<>();
		HashMap<String,String> method_pos1 = new HashMap<>();
		ArrayList<String> anomalies = new ArrayList<>();
		Stack<String> jstacktrace1 = new Stack<>();	
		HashMap<String,Double> second_file_time = new HashMap<>();
		HashMap<String,Integer> method_freq1 = new HashMap<>();

		
		
		try{
			
			//<<<<<<=============================== read both log files ===============================>>>>>>>>>>>>>>>>>>>> 
			
			BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\DELL\\eclipse-workspace\\Demo\\ExampleTrace.log"));
			BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\DELL\\eclipse-workspace\\Demo\\Example1Trace.log"));
		
			String first_file_data = br1.readLine();
			String second_file_data = br2.readLine();
			
			//============== reach to actual content where time and jstack data is given ================================================///
			
			while(first_file_data!=null) {
				if((first_file_data.trim()).equals("J9 timer(UTC)        ThreadID               TP id     Type        TraceEntry"))
					break;
				first_file_data = br1.readLine();
			}first_file_data = br1.readLine();
			
			
			while(second_file_data!=null) {
				if((second_file_data.trim()).equals("J9 timer(UTC)        ThreadID               TP id     Type        TraceEntry"))
					break;
				second_file_data = br2.readLine();
			}second_file_data = br2.readLine();
			
			
			
			//<<<<<<<<<<====================== storing to data perform analysis ======================================>>>>>>>>>>>>>>>>>>>>>>>>>///
			
			//================================== first log file data analysis ======================================================
	        String time_start = "";
	        String time_end = "";
	        boolean first_time = false;
	        
			while(first_file_data!=null){
				String[] split_data = first_file_data.split("\\s+");
				
				//================= starting and ending time of class one=================
				if(split_data[3].equals("Entry") && first_time == false) {
					time_start = split_data[0];
					first_time = true;
				}
				
				
				if(split_data[3].equals("Exit")) {
					time_end = split_data[0];
				}
				
				
				int class_name_len = (ProcessBuilderDemo.getfailClass()).length();
				

				if(split_data[3].equals("Event") && !(split_data[4].equals("jstacktrace:"))){
					//System.out.println("sdf");
					String method_name = (split_data[5]).substring(class_name_len);
					jstacktrace.push(method_name);
					method_pos.put(method_name,split_data[6]);
				}
				
				
			//======================================== method with time ================================================================
			    if(split_data[3].equals("Entry")||split_data[3].equals("Exit")) {
					
			    	event_trace.add(split_data[3]);
				    time_trace.add(split_data[0]);
				    
				    String method_name = split_data[4];
					int class_name_length = ProcessBuilderDemo.getPassClass().length()+2;
					String add_in_met = "";
					for(int i=class_name_length ; method_name.charAt(i)!='(' ; i++) {
						add_in_met += method_name.charAt(i);
				    }
					
					trace_data.add(add_in_met);
					
					if(split_data[3].equals("Entry")) {
						method_freq.put(add_in_met, 0);
					}
			    }
			    
				first_file_data = br1.readLine();
		   }
		   
		    //<<<<<<<========================================= calculate time duration for each method =======================================>>>
           
		    java.util.Iterator data_itr = trace_data.listIterator();
		    java.util.Iterator time_itr = time_trace.listIterator();
		    TreeSet<String>unique_method = new TreeSet<>();
		    while(data_itr.hasNext()) {
		    	String method_name = (String) data_itr.next();
		    	String time_for_method = (String)time_itr.next();
		    	if(!unique_method.contains(method_name)) {
		    		unique_method.add(method_name);
		    		int entry_time_index = trace_data.indexOf(method_name);
		    		int exit_time_index = trace_data.lastIndexOf(method_name);
		    		//System.out.println(entry_time_index+"         "+exit_time_index);
		    		
		    		double duration = giveDurationDouble( time_trace.get(exit_time_index) , time_trace.get(entry_time_index));
		    		first_file_time.put(method_name, duration);
		    		chart_first.put(method_name,duration);
		    		//System.out.println(method_name+"        "+duration);
		    	}
		    } 
		    
		   
		  //========================================== calculate frequency for each method ==================================================  
		    java.util.Iterator itr_method = trace_data.iterator();
		    java.util.Iterator itr_event = event_trace.iterator();
		    while(itr_method.hasNext()) {
		    	String method_name = (String)itr_method.next();
		    	String event_type = (String)itr_event.next();
		    	if(event_type.equals("Entry")) {
		    		int freq_count = method_freq.get(method_name);
		    		freq_count++;
		    		method_freq.replace(method_name, freq_count);
		    	}
		    }
		    
		    
		    //=========================================== representing file data using tabular form in java ========================================
		    
		    System.out.println("============================================== passing class analysis ================================================");
		    System.out.println("   ");
		    java.util.Iterator method_freq_itr = method_freq.entrySet().iterator();
		    List<List<String>> rowsList_first = new ArrayList<List<String>>();
		    List<String> headersList_first = Arrays.asList("Method", "Time", "Frequency");
		    
		    while(method_freq_itr.hasNext()) {
		    	Map.Entry temp = (Entry) method_freq_itr.next();
		    	String temp_name = (String)temp.getKey();
		    	int freq = (int)temp.getValue();
		    	double duration = first_file_time.get(temp_name);
		    	//Arrays.asList(temp_name , String.valueOf(duration) , String.valueOf(freq));
		    	rowsList_first.add(new ArrayList<>(Arrays.asList(temp_name , String.valueOf(duration) , String.valueOf(freq))));
		    }
		    
		    

		    //bookmark 1
		    // a good code has not more than 30 methods
		    Board board_first = new Board(500);
		    String tableString_first = board_first.setInitialBlock(new Table(board_first, 75, headersList_first, rowsList_first).tableToBlocks()).build().getPreview();
		    System.out.println(tableString_first);
		    
		    
		    
		    //durtion for class first
		    double duration_class_first = giveDurationDouble(time_end,time_start);
		    System.out.println("first class time duration :- "+duration_class_first);
		    /*
		     * 
		     *===========================================================================================================================
		     *  										file changed
		     *                                          file changed
		     * 											file changed
		     * 											file changed		
		     * ===========================================================================================================================
		     * 
		     * 
		     */
		    
		    
		    
		    
		    
		    
		  //================================== second log file data analysis ======================================================
		    int count=0;
		    String time_start1 = "";
	        String time_end1 = "";
	        boolean first_time1 = false;
		    while(second_file_data!=null){
				String[] split_data = second_file_data.split("\\s+");
				int class_name_len = (ProcessBuilderDemo.getfailClass()).length();
				
				
				//================= starting and ending time of second one=================
				if(split_data[3].equals("Entry") && first_time1 == false) {
					time_start1 = split_data[0];
					first_time1 = true;
				}
				
				
				if(split_data[3].equals("Exit")) {
					time_end1 = split_data[0];
				}
				
				
				
				
				if(split_data[3].equals("Event") && !(split_data[4].equals("jstacktrace:"))){
					String method_name = (split_data[5]).substring(class_name_len+1);
					jstacktrace1.push(method_name);
					method_pos1.put(method_name,split_data[6]);
				}		
				
				
				//======================================== method with time ================================================================ 
				
				
                if(split_data[3].equals("Entry")||split_data[3].equals("Exit")) {	
				    time_trace1.add(split_data[0]);
				    event_trace1.add(split_data[3]);
				    String method_name = split_data[4];
					int class_name_length = ProcessBuilderDemo.getPassClass().length()+3;
					String add_in_met = "";
					if(method_name.charAt(0) == '*')class_name_length++;
					for(int i=class_name_length ; method_name.charAt(i)!='(' ; i++) {
						add_in_met += method_name.charAt(i);
				    }
					//System.out.println(add_in_met);
					trace_data1.add(add_in_met);
					
					if(split_data[3].equals("Entry")) {
						method_freq1.put(add_in_met, 0);
					}
					
			    }
			
			    
			    /// if exception exist in second or failing case log file
				//using code flow find exception details
				
				if(split_data[3].equals("Exit") && split_data[4].charAt(0)=='*') {
					String exceptions_name = split_data[4];
					int class_name_length = ProcessBuilderDemo.getfailClass().length()+3;
					String add_in_ano = "";
					for(int i=class_name_length ; exceptions_name.charAt(i)!='(' ; i++) {
						add_in_ano += exceptions_name.charAt(i);
					}
					anomalies.add(add_in_ano);
				}
				
				
				
				
		    	if(!(split_data[3].equals("Debug")) && split_data[4].charAt(0) == '*') {
		    		count++;
			     }
		    	
		        //===========================================search in code flow and methods and positions to show on screen============================== 
		    	if(count==1) {
		    		System.out.println("           ");
		    		System.out.println("           ");
		    		System.out.println("           ");
		    		System.out.println("=================================Exception exists================================= ");
				    
		    		java.util.Iterator itr = anomalies.iterator();
		    		while(itr.hasNext()){
		    			String exception_next = (String) itr.next();
		    			String pos_exception =  method_pos1.get(exception_next);
		    			System.out.println("exception found in method :- "+exception_next+"()  "+pos_exception);
		    			
		    		}
		    		count++;
		    		//break;
		    		System.out.println("           ");
		    		System.out.println("           ");
		    		System.out.println("           ");
		    	}
		    	
				second_file_data = br2.readLine();
			}
			
			//<<<<<<<========================================= calculate time duration for each method =======================================>>>
            
		    
		    java.util.Iterator data_itr1 = trace_data1.listIterator();
		    java.util.Iterator time_itr1 = time_trace1.listIterator();
		    TreeSet<String>unique_method1 = new TreeSet<>();
		    while(data_itr1.hasNext()) {
		    	String method_name = (String) data_itr1.next();
		    	String time_for_method = (String)time_itr1.next();
		    	if(!unique_method1.contains(method_name)) {
		    		unique_method1.add(method_name);
		    		int entry_time_index = trace_data1.indexOf(method_name);
		    		int exit_time_index = trace_data1.lastIndexOf(method_name);
		    		//System.out.println(entry_time_index+"         "+exit_time_index);
		    		
		    		double duration = giveDurationDouble( time_trace1.get(exit_time_index) , time_trace1.get(entry_time_index));
		    		second_file_time.put(method_name, duration);
		    		chart_second.put(method_name,duration);
		    		//System.out.println(method_name+"        "+duration);
		    	}
		    }
		    
		    
		  //========================================== calculate frequency for each method ==================================================  
		    java.util.Iterator itr_method1 = trace_data1.iterator();
		    java.util.Iterator itr_event1 = event_trace1.iterator();
		    while(itr_method1.hasNext()) {
		    	String method_name = (String)itr_method1.next();
		    	String event_type = (String)itr_event1.next();
		    	if(event_type.equals("Entry")) {
		    		int freq_count = method_freq1.get(method_name);
		    		freq_count++;
		    		method_freq1.replace(method_name, freq_count);
		    	}
		    } 
		    
		    
		    System.out.println("   ");
		    System.out.println("============================================== failing class analysis ================================================");
		    System.out.println("   ");
		    //=========================================== representing file data using tabular form in java ========================================
		    java.util.Iterator method_freq_itr1 = method_freq1.entrySet().iterator();
		    List<List<String>> rowsList = new ArrayList<List<String>>();
		    List<String> headersList = Arrays.asList("Method", "Time", "Frequency");
		    
		    while(method_freq_itr1.hasNext()) {
		    	Map.Entry temp = (Entry) method_freq_itr1.next();
		    	String temp_name = (String)temp.getKey();
		    	int freq = (int)temp.getValue();
		    	double duration = second_file_time.get(temp_name);
		    	//Arrays.asList(temp_name , String.valueOf(duration) , String.valueOf(freq));
		    	rowsList.add(new ArrayList<>(Arrays.asList(temp_name , String.valueOf(duration) , String.valueOf(freq))));
		    }
		    
		    
		    //bookmark 1
		    // a good code has not more than 30 methods
		    Board board = new Board(500);
		    String tableString = board.setInitialBlock(new Table(board, 75, headersList, rowsList).tableToBlocks()).build().getPreview();
		    System.out.println(tableString);
		    
		    
		    
		    
	
		    // difference in time duration of each method 

	    	System.out.println("=====================  time differenece b/w same methods of passing case and failing case ======================================");
		    java.util.Iterator itr_first = first_file_time.entrySet().iterator();
		    java.util.Iterator itr_second = second_file_time.entrySet().iterator();
		    while(itr_second.hasNext()) {
		    	
		    	Map.Entry second_file_method = (Map.Entry) itr_second.next();
		    	String second_file_name = (String) second_file_method.getKey();
		    	double method_time1 = (double)second_file_method.getValue();
		    	
		    	double method_time = (double)first_file_time.get(second_file_name);
		    	
		    	
		    	double time_gap = method_time - method_time1;
		    	if(time_gap<0)
		    		 time_gap = -time_gap;
		    
		    	System.out.println("*) "+second_file_name+" ===>"+ time_gap);
		   
		    }
		    
		    // =============================== durtion for second first===========================================
		    double duration_class_second = giveDurationDouble(time_end1,time_start1);
		    
		    System.out.println(" ");
		    System.out.println("failing class time duration :- "+duration_class_second);
		    System.out.println(" ");
		    
		    
		    
		    
		    System.out.println(" "); 
		    System.out.println(" ");
		    System.out.println("=================== passing case took this much amount of extra time =================");
		    System.out.println("extra time duration :- "+(duration_class_first-duration_class_second));
		    System.out.println(" ");
		    System.out.println(" ");
		    
		    
		   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static double giveDurationDouble(String exit_time , String entry_time) {
		
		String[] split_time = exit_time.split(":");
		String[] split_time1 = entry_time.split(":");
		
		double duration_hours = (Double.parseDouble(split_time[0]) - Double.parseDouble(split_time1[0]))*3600;
		double duration_min = (Double.parseDouble(split_time[1]) - Double.parseDouble(split_time1[1]))*60;
		double duration_sec = Double.parseDouble(split_time[2]) - Double.parseDouble(split_time1[2]);
		return(duration_hours+duration_min+duration_sec);
	}

}
