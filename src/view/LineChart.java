package view;
import javax.swing.JFrame;  
import java.awt.Font;
import java.awt.Color;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;  
	public class LineChart extends JFrame {  
		  
		  private static final long serialVersionUID = 1L;  
		    static final StandardChartTheme theme = initializeTheme();

		  public LineChart(DefaultCategoryDataset data) {  
		    super("גרף התקדמות");  
		    DefaultCategoryDataset dataset = data;  // Create dataset
		    JFreeChart chart = ChartFactory.createLineChart(  
		        "גרף התקדמות אישי", // Chart title  
		        "משחק", // X-Axis Label  
		        "ניקוד", // Y-Axis Label  
		        dataset  
		        );  		    // Create chart  
		  setTheme(chart);
		    ChartPanel panel = new ChartPanel(chart);  
		    setContentPane(panel);  
		  }
		  public void setTheme(JFreeChart chart)
		  {
			  final ChartTheme chartTheme = theme;
			  if (StandardChartTheme.class.isAssignableFrom(chartTheme.getClass())) {
					StandardChartTheme standardTheme = (StandardChartTheme) chartTheme;
					standardTheme.apply(chart);
			  }
		  }
		  public static StandardChartTheme initializeTheme() {
		        String fontName = "Tahoma";
		        StandardChartTheme theme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();
		        theme.setExtraLargeFont(new Font(fontName, Font.PLAIN, 24)); // title
		        theme.setLargeFont(new Font(fontName, Font.PLAIN, 20)); // axis-title
		        theme.setRegularFont(new Font(fontName, Font.PLAIN, 16));
		        theme.setSmallFont(new Font(fontName, Font.PLAIN, 12));
		        theme.setRangeGridlinePaint(Color.decode("#C0C0C0"));
		        theme.setPlotBackgroundPaint(Color.white);
		        theme.setChartBackgroundPaint(Color.white);
		        theme.setGridBandPaint(Color.red);
		        theme.setGridBandAlternatePaint(Color.red);
		        theme.setTickLabelPaint(Color.BLUE);//values on the XY
		        theme.setBarPainter(new StandardBarPainter());
		        theme.setAxisLabelPaint(Color.decode("#666666"));
		        return theme;
		    }
}
