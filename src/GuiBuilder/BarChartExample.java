package GuiBuilder;
  import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;  
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;

import com.ibm.log.CompareLogFile;  
  
public class BarChartExample extends JFrame {  
  
  private static final long serialVersionUID = 1L;  
  
  public BarChartExample(String appTitle) {  
    super(appTitle);  
  
    // Create Dataset  
    CategoryDataset dataset = createDataset();  
      
    //Create chart  
    JFreeChart chart=ChartFactory.createBarChart(  
        "Difference in time duration of each method", //Chart Title  
        "methods", // Category axis  
        "time in seconds", // Value axis  
        dataset,  
        PlotOrientation.VERTICAL,  
        true,true,false  
       );  
  
    ChartPanel panel=new ChartPanel(chart);  
    setContentPane(panel);  
  }  
  
  private CategoryDataset createDataset() {  
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
    
    HashMap<String,Double> first = CompareLogFile.chart_first;
    HashMap<String,Double> second = CompareLogFile.chart_second;
    
    Double time,time1;
    String name_method,name_method1;
    
    java.util.Iterator itr = first.entrySet().iterator();
    while(itr.hasNext()) {
    	Map.Entry mp = (Entry) itr.next();
    	name_method = (String) mp.getKey();
    	time = (Double) mp.getValue();
    	dataset.addValue(time, name_method, "Passing Case");	
    }
      
  
    java.util.Iterator itr1 = second.entrySet().iterator();
    while(itr1.hasNext()) {
    	Map.Entry mp = (Entry) itr1.next();
    	name_method1= (String) mp.getKey();
    	time1 = (Double) mp.getValue();
    	dataset.addValue(time1, name_method1, "Failing Case");	
    }
  
    return dataset;  
  }  
  
  public static void main(String[] args) throws InvocationTargetException, InterruptedException {  
	
	if (SwingUtilities.isEventDispatchThread()) {
		//System.out.println("galat baat ha ");
		  BarChartExample example=new BarChartExample("Bar Chart Window");  
	      example.setSize(800, 400);  
	      example.setLocationRelativeTo(null);  
	      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
	      example.setVisible(true);  
   }
	
	else {
	  SwingUtilities.invokeAndWait(
	    		new Runnable() {
					@Override
					public void run() {
						  BarChartExample example=new BarChartExample("Bar Chart Window");  
					      example.setSize(800, 400);  
					      example.setLocationRelativeTo(null);  
					      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  
					      example.setVisible(true);  
						
					}
	    		});  
	  }
  }
}  