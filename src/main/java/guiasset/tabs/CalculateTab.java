package guiasset.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class CalculateTab extends Tab {
	CalculateTab(String title){
		super(title);
		this.setClosable(false);
		
		HBox content = new HBox();
		content.alignmentProperty().set(Pos.CENTER);
		
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Cash", 13),
                new PieChart.Data("Fix Income", 25),
                new PieChart.Data("Equity", 10)
                );
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Asset");
        
        content.getChildren().addAll(chart, new Button("Calculate"));
        
        this.setContent(content);
	}
}
