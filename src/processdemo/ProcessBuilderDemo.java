package processdemo;

import java.util.*; 
import GuiBuilder.MethodTraceGUI;
import java.io.*;
import java.lang.Object;


public class ProcessBuilderDemo {

	
	
	public static String getPassClass() {
		
		return(MethodTraceGUI.getPass()); 
		
	}
	
    public static String getfailClass() {
		
    	return(MethodTraceGUI.getFail());
		
	}
	
	
	public static void main(String[] args) throws IOException {
	
		
	String pass_class = ProcessBuilderDemo.getPassClass();
	String fail_class = ProcessBuilderDemo.getfailClass();
	
	FileWriter fw = new FileWriter("C:\\Users\\DELL\\eclipse-workspace\\Demo\\command.txt");
	//System.out.println("call to hui ha");
	//System.out.println("adsda"+pass_class);
	
	String write_in_file = "javac "+pass_class+".java"+String.format("%n")+"java "+pass_class;
	String write_in_file1 = "javac "+fail_class+".java"+String.format("%n")+"java "+fail_class;
	String log_command = "java -Xtrace:none -Xtrace:methods="+pass_class+",trigger=method{"+pass_class+",jstacktrace},maximal=mt,output=ExampleOut.trc "+pass_class;
	String log_command1 = "java -Xtrace:none -Xtrace:methods="+fail_class+",trigger=method{"+fail_class+",jstacktrace},maximal=mt,output=Example1Out.trc "+fail_class;
	String gen_command = "java com.ibm.jvm.format.TraceFormat ExampleOut.trc ExampleTrace.log";
	String gen_command1 = "java com.ibm.jvm.format.TraceFormat Example1Out.trc Example1Trace.log";
	
	
	fw.write(write_in_file);
	fw.write(String.format("%n"));
	fw.write(write_in_file1);
	fw.write(String.format("%n"));
	fw.close();
 
	ProcessBuilder pb = new ProcessBuilder("cmd");
	File commands = new File("C:\\Users\\DELL\\eclipse-workspace\\Demo\\command.txt");
	File logGen = new File("C:\\Users\\DELL\\eclipse-workspace\\Demo\\logGen.txt");
	File output = new File("C:\\Users\\DELL\\eclipse-workspace\\Demo\\output.txt");
	File errorF = new File("C:\\Users\\DELL\\eclipse-workspace\\Demo\\error.txt");
	
	
    pb.redirectInput(commands);
    pb.redirectOutput(output);
	pb.redirectError(errorF);
	pb.start();
	

	
	FileWriter lg = new FileWriter("C:\\Users\\DELL\\eclipse-workspace\\Demo\\logGen.txt");
	lg.write(log_command);
	lg.write(String.format("%n"));
	lg.write(log_command1);
	lg.write(String.format("%n"));
	lg.write(gen_command);
	lg.write(String.format("%n"));
	lg.write(gen_command1);
	lg.write(String.format("%n"));
	lg.close();
	
	File outputl = new File("C:\\Users\\DELL\\eclipse-workspace\\Demo\\outputl.txt");
	File errorl = new File("C:\\Users\\DELL\\eclipse-workspace\\Demo\\errorl.txt");
	pb.redirectInput(logGen);
	pb.redirectOutput(outputl);
	pb.redirectError(errorl);
	
	pb.start();
	
	
 }
}
