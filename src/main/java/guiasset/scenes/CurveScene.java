package guiasset.scenes;
import java.util.ArrayList;
import java.util.List;
import guiasset.CommonFunction;
import guiasset.InformationStore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CurveScene extends Stage{

	@SuppressWarnings("rawtypes")
	private String nameConvention;
	private List<Node> textFieldArray;
	private TextField newCurveName;
	private ChoiceBox<String> choiceBox;
	private LineChart<Number, Number> lineChart;
	private int counter;
	private List<String> curveAxis;
	private List<String> curveXAxis;
	private List<Series> curveChoiceData;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CurveScene(String title){
		
		nameConvention = title.replaceAll(" ", "").toLowerCase();
		
		try {
			curveChoiceData = (List<XYChart.Series>) InformationStore.class.getField(nameConvention + "ChoiceData").get(null);
			curveAxis = (List<String>) InformationStore.class.getField(nameConvention + "Axis").get(null);
			curveXAxis = (List<String>) InformationStore.class.getField(nameConvention + "XAxis").get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Top layer adding childrens
		VBox topLayer = new VBox();
		HBox graphAndInputs = new HBox();
		GridPane bottomGUI = CommonFunction.gridBoxGenerator(10);
		topLayer.getChildren().addAll(graphAndInputs, bottomGUI);
		
		//graphAndInputs layer adding children
        graphAndInputs.setPadding(new Insets(10));
        GridPane inputGrid = CommonFunction.gridBoxGenerator(10);
        inputGrid.alignmentProperty().set(Pos.CENTER);

        	//adding the headers for inputs
        textFieldArray = new ArrayList<Node>();
        curveXAxis.forEach(item -> textFieldArray.add(new Text(item)));
        inputGrid.addColumn(0, textFieldArray.toArray(new Node[textFieldArray.size()]));
        		
        textFieldArray = new ArrayList<Node>();
        curveXAxis.forEach(item -> {
        								TextField toAdd = (TextField) CommonFunction.generateTextFieldWithProperties("2SF");
        								toAdd.textProperty().addListener((observable, oldValue, newValue) -> {
        									updateGraphFromText();
        								});
										textFieldArray.add(toAdd);
        							}
        					);
        inputGrid.addColumn(1, textFieldArray.toArray(new Node[textFieldArray.size()]));
        
        lineChart = createLineChart();
        
        drawInitalGraph();
        
        graphAndInputs.getChildren().addAll(inputGrid, lineChart);
        
		//bottomGUI adding buttons
        newCurveName = new TextField();
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        choiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(extractSeriesName()));
        choiceBox.getSelectionModel().selectFirst();

        addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					addCurveFromTextField(addButton);
					
			}});
        
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeCurveFromChoice();
				
			}
        });
        
        
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
        
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				if ((int)newValue > -1) {
					updateTextFieldArrays(choiceBox.getItems().get((int) newValue));
				}

				}
			}
		);
        
		bottomGUI.add(new Text("Name of new " + title + " :"), 0, 0);
		bottomGUI.add(new Text(title +" to remove :"), 0, 1);

        
		bottomGUI.add(newCurveName, 1, 0);
		bottomGUI.add(addButton, 2, 0);
		bottomGUI.add(removeButton, 2, 1);
		bottomGUI.add(choiceBox, 1, 1);
		bottomGUI.alignmentProperty().set(Pos.CENTER);
		
        this.setTitle(title);
        this.setScene(new Scene(topLayer));
        this.show();
	}
	
	protected void updateTextFieldArrays(String newValue) {
		Series series = getSeriesViaName(newValue);
		for (int i = 0 ;i < textFieldArray.size(); i++) {
			XYChart.Data<Number,Number> pt = (Data<Number, Number>) series.getData().get(i);
			((TextField) textFieldArray.get(i)).setText(pt.getYValue().toString());
		}
	}

	protected void updateTextFieldFromData() {
		XYChart.Series series = lineChart.getData().get(0);
		
		for (int i = 0 ;i < series.getData().size(); i++) {
			XYChart.Data<Number,Number> pt = (Data<Number, Number>) series.getData().get(i);
			((TextField) textFieldArray.get(i)).setText(pt.getYValue().toString());
		}
	}
	
	//true for add
	protected void updateChoiceBoxToSelection(boolean addOrRemove, String choice) {
		if (addOrRemove) {
			choiceBox.getItems().add(choice);
			choiceBox.getSelectionModel().select(choice);
		}
		else {
			choiceBox.getItems().remove(choice);
			choiceBox.getSelectionModel().selectFirst();
		}		
	}

	protected void updateGraphFromText() {
        //defining a series
        XYChart.Series series = new XYChart.Series<>();
        lineChart.getData().remove(0);
        for (int i = 0 ; i < textFieldArray.size() ; i++) {
        	double Y = 0;
        	Y = Double.valueOf(((TextField) textFieldArray.get(i)).getText());
        	series.getData().add(i, new XYChart.Data(i, Y));
        }
        lineChart.getData().add(series);
	}


	protected void removeCurveFromChoice() {
		//get the value in choice box
		String choice = choiceBox.getValue();
		
		if (!choice.contains("Default")) {
			removeSeriesViaName(choice);
			updateChoiceBoxToSelection(false, choice);
		}
	}

	protected void addCurveFromTextField(Button btn) {
		String newName = newCurveName.getText();
		
		//if text field is empty throw error
		if (newName.isEmpty() || newName == null || newName.isBlank()) {
			CommonFunction.toolTipShow(btn, "Please edit name of curve");
		}
		else if ((getSeriesViaName(newName) != null)) {
			CommonFunction.toolTipShow(btn, "Curve name exist!");
		}
		else {
			//store the data that is currently in the textfields
			XYChart.Series series = new XYChart.Series();
			series.setName(newCurveName.getText());
			
			counter = 0;
			
			textFieldArray.forEach(textfield -> {
					double YValue = Double.valueOf(((TextField) textfield).getText());
					series.getData().add(counter, new XYChart.Data<Integer, Double>(counter, YValue));
					counter++;
				}
			);
			addSeriesViaName(newName, series);
	        updateChoiceBoxToSelection(true, newName);
		}
	}
	
	//init create the chart
	@SuppressWarnings("unchecked")
	private LineChart<Number, Number> createLineChart() {
		
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(curveAxis.get(0));
        yAxis.setLabel(curveAxis.get(1));
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle(nameConvention);
        lineChart.getData().add(new XYChart.Series<Number, Number>());
        
      
		return lineChart;
	}
	
	public List<String> extractSeriesName() {
		List<String> names = new ArrayList<String>();
		curveChoiceData.forEach(item -> names.add(item.getName()));
		return names;
	}
	
	private Series getSeriesViaName(String name) {
		for (int i = 0 ; i < curveChoiceData.size(); i++) {
			if (curveChoiceData.get(i).getName().equals(name)) {
				return curveChoiceData.get(i);
			} 
		}
		
		return null;
	}
	
	private void removeSeriesViaName(String name) {
		int item = extractSeriesName().indexOf(name);
		curveChoiceData.remove(item);
	}
	
	
	private void addSeriesViaName(String name, Series series) {
		series.setName(name);
		curveChoiceData.add(series);
	}
	
	private void printData() {
		for (int i = 0 ; i < curveChoiceData.size(); i++) {
			System.out.println(curveChoiceData.get(i).getName());
			for (int x = 0 ; x < curveChoiceData.get(i).getData().size() ; x++)
			{
				XYChart.Data<Number,Number> pt = (Data<Number, Number>) curveChoiceData.get(i).getData().get(x);
				System.out.println(pt.getXValue() + "---" + pt.getYValue());
			}
		}
	}
	
	private void drawInitalGraph() {
        if (getSeriesViaName("Default") != null) {
            updateTextFieldArrays("Default");
        }
        else {
        	for (int i = 0 ; i < curveXAxis.size() ; i++) {
                lineChart.getData().get(0).getData().add(new XYChart.Data<Number,Number>(i, 1.0));
        	}
        }
	}

}
