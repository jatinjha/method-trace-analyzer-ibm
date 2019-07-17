# method-trace-analyzer-ibm

<h3>    Description     </h3>
 Java based GUI application to parse and compare multiple trace files, to help debugging the code. Method trace is used for 
 post-mortem debugging. It consists of timestamp of entry and exit points for each method invocation. They may also 
 contain stack-trace for each invocation. In case of functionality issues, comparing of the data is used for debugging.

<h3>     Functionality included     </h3>

 1. Compare two trace files: one from failing and passing case each and find out the
anomaly.

2. Parse one or more trace file and suggest anomalies - I.e. Flag methods which are not
completing their execution / Taking longer to execute. This will be helpful in addressing
hang and performance related problems.

3. Parse one or more trace file and create a tabular and graphical view for the number of
times each method is invoked. Comparative view in case of multiple files.

4. Compare code-flow and stack trace for failing and passing case and find anomalies. 

<h3>     Technology Stack:     </h3>
 Java, IBM Java Method trace, Eclipse , WindowBuilder, Open JDK with OpenJ9 , JFreechart 
<h3>     Description of files uploaded:     </h3>

 1. CompareLogFile.java ---> for analysis of generated log files
 
 2. BarChartExample.java --> to create bar graph
 
 3. MethodTraceGUI.java --> to provide UI for writing java file names and to invoke different classes
 
 4. ProcessBuilderDemo.java --> to write and read data from generated files
 
 5. wagu --> provide classes for presenting data in tabular form on console
 
 6. ibm-project.pdf --> ppt for showing output of project
 
 7. Undertaking.pdf --> Documentation of Undertaking
 
 8. Video.mp4 --> Video showing implementation of functionalities
 
 9. Ideationdocument.pdf --> Documentation consisting of idea
 
 <h3>     Team Name:Akatsuki    </h3>
 <h3>     Team Details: Jatin Jha , Sakar Jain   </h3>
