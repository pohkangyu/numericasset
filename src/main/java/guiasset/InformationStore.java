package guiasset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class InformationStore {
	//To generate a path
	public static final String dir_path = "guiasset.tabs.";
	public static final String append_tab = "Tab";
	
	//Primary Scene
	public static final String title = "Numeric Asset";
	
	public static final String primarySceneCss = "/resource/bootstrapfx.css";
	
	
	//Tabs for mainScene 
	public static final String[] mainSceneTab = new String[] {"Portfolio", "Balancing"};
	
	//Tabs for PortfolioTab
	public static final String[] portfolioTab = new String[] {"FixIncome", "Equity", "Cash"};
	public static final String[] BalancingTab = new String[] {"Calculate", "Scenario"};

	//Tabs for BalancingTab
	public static final String[] balancingTab = new String[] {};
	
	//Information for Fixed Income
	public static final List<String> fixincomeFields = Arrays.asList("Fix Income Name", "Principle","Coupon Rate", 
																	"Coupon Frequency", "Start Date", "Maturity Date",
																	"Purchase Date");
	
	public static final List<String> fixincomeFieldsProperties = Arrays.asList("String", "Integer", "2SF", 
																		"Integer", "Date", "Date",
																		"Date");
	
	public static final List<String> fixincomeComboChoice = Arrays.asList("Category");

	public static final List<String> fixincomeChoice = Arrays.asList("Spot Curve");
		
	public static final List<String> spotcurveXAxis = Arrays.asList("Year 1", "Year 2", "Year 3", "Year 4", "Year 5", "Year 6");
	public static final List<String> spotcurveAxis = Arrays.asList("No. Year", "Percentage");
	public static final  List<XYChart.Series<Integer,Double>> spotcurveChoiceData = FXCollections.observableArrayList(
            new LineChart.Series<Integer,Double>("Default", FXCollections.observableArrayList(
                new XYChart.Data<Integer,Double>(0, 1.0),
                new XYChart.Data<Integer,Double>(1, 1.4),
                new XYChart.Data<Integer,Double>(2, 1.9),
                new XYChart.Data<Integer,Double>(3, 2.3),
                new XYChart.Data<Integer,Double>(4, 0.5),
                new XYChart.Data<Integer,Double>(5, 0.5),
                new XYChart.Data<Integer,Double>(6, 0.5),
                new XYChart.Data<Integer,Double>(7, 0.5)
            ))
        );

	//Information for Equity
	public static final List<String> equityFields = Arrays.asList("Equity Name", "Equity Value", "Purchase Date");
	
	public static final List<String> equityFieldsProperties = Arrays.asList("String", "Integer","Date");

	public static final List<String> equityButtons = Arrays.asList("Add", "Remove");
	
	public static final List<String> equityChoice = Arrays.asList("Growth Curve");
	
	public static final List<String> equityComboChoice = Arrays.asList("Category");
	
	public static final List<String> growthcurveXAxis = Arrays.asList("Year 1", "Year 2", "Year 3", "Year 4", "Year 5", "Year 6");
	public static final List<String> growthcurveAxis = Arrays.asList("No. Year", "Percentage");
	public static final  List<XYChart.Series<Integer,Double>> growthcurveChoiceData = FXCollections.observableArrayList(
            new LineChart.Series<Integer,Double>("Default", FXCollections.observableArrayList(
                new XYChart.Data<Integer,Double>(0, 1.0),
                new XYChart.Data<Integer,Double>(1, 1.4),
                new XYChart.Data<Integer,Double>(2, 1.9),
                new XYChart.Data<Integer,Double>(3, 2.3),
                new XYChart.Data<Integer,Double>(4, 0.5),
                new XYChart.Data<Integer,Double>(5, 0.5),
                new XYChart.Data<Integer,Double>(6, 0.5),
                new XYChart.Data<Integer,Double>(7, 0.5)
            ))
        );
	
	//Information for Cash
	public static final List<String> cashFields = Arrays.asList("Cash Name", "Cash Value", "Purchase Date");
	
	public static final List<String> cashFieldsProperties = Arrays.asList("String", "Integer","Date");

	public static final List<String> cashButtons = Arrays.asList("Add", "Remove");
	
	public static final List<String> cashComboChoice = Arrays.asList("Category");
	
	public static final List<String> cashChoice = Arrays.asList("Cash Growth");
	public static final List<String> cashgrowthXAxis = Arrays.asList("Year 1", "Year 2", "Year 3", "Year 4", "Year 5", "Year 6");
	public static final List<String> cashgrowthAxis = Arrays.asList("No. Year", "Percentage");
	public static final  List<XYChart.Series<Integer,Double>> cashgrowthChoiceData = FXCollections.observableArrayList(
            new LineChart.Series<Integer,Double>("Default", FXCollections.observableArrayList(
                new XYChart.Data<Integer,Double>(0, 1.0),
                new XYChart.Data<Integer,Double>(1, 1.4),
                new XYChart.Data<Integer,Double>(2, 1.9),
                new XYChart.Data<Integer,Double>(3, 2.3),
                new XYChart.Data<Integer,Double>(4, 0.5),
                new XYChart.Data<Integer,Double>(5, 0.5),
                new XYChart.Data<Integer,Double>(6, 0.5),
                new XYChart.Data<Integer,Double>(7, 0.5)
            ))
        );
	
}

