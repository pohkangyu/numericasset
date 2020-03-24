package guiasset.tabs;

import guiasset.CommonFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CalculateTab extends Tab {
	CalculateTab(String title){
		super(title);
		this.setClosable(false);
				
		
		
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Cash", 13),
                new PieChart.Data("Fix Income", 25),
                new PieChart.Data("Equity", 10)
                );
        
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Portfolio");
        

        
        this.setContent(chart);
	}
	
	
	
	
}
