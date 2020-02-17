package guiasset.tabs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import guiasset.CommonFunction;
import guiasset.InformationStore;
import guiasset.scenes.ComboScene;
import guiasset.scenes.CurveScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
public class AssetTab extends Tab {
	
	private GridPane gridPane = CommonFunction.gridBoxGenerator(10);
	//Fields for the header of the col
	private List<String> inputFields;
	//Choices for inputs type
	private List<String> inputChoice;
	//buttons for input
	private List<String> inputButtons;
	//buttons for combo
	private List<String> comboChoice;
	//data for the curves
	private HashMap<String, List<Series>> curveChoiceData = new HashMap<String, List<Series>>();;
	
	AssetTab(String title){
		super(title);
		this.setClosable(false);
		String temp = title.replaceAll(" ", "").toLowerCase();
		

		//This tab consist of 1 x Scroll pane of items and 1 row of buttons that is in a VBox
        gridPane = CommonFunction.gridBoxGenerator(10);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinHeight(512);
        scrollPane.setContent(gridPane);
        
        HBox buttonGrid = new HBox();
        buttonGrid.setPadding(new Insets(10,10,10,10));
        buttonGrid.setSpacing(10);
        
        VBox vbox = new VBox(scrollPane, buttonGrid);
//        TableView table = new TableView();
//        TableColumn col1 = new TableColumn("dcsc");
//        table.setEditable(true);
//        table.getColumns().addAll(col1);
        //table.setItems(FXCollections.observableArrayList( "Hi", "f" ));

        //VBox vbox = new VBox(table, buttonGrid);
        
        this.setContent(vbox);
        
		//Add button for removing and adding rows
		Arrays.asList("Add", "Remove").forEach(item -> buttonGrid.getChildren().add(generateButton(item)));
        
		//Init nodes of header to be added
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(new Text("No."));
		
        //Add Input fields to nodes
		try {
			this.inputFields = (List<String>) InformationStore.class.getField(temp + "Fields").get(null);
			this.inputFields.forEach(item -> nodes.add(new Text(item)));
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		//Add Input Choice and their button to nodes
		try {
			this.inputChoice = (List<String>) InformationStore.class.getField(temp + "Choice").get(null);
			this.inputChoice.forEach(item -> nodes.add(new Text(item)));
			this.inputChoice.forEach(item -> buttonGrid.getChildren().add(generateInputSceneButton(item, "choiceBox")));
			
			
			//try getting all the data for the input choices
			for (int i = 0 ; i < this.inputChoice.size() ; i++) {
				String extractInfo = inputChoice.get(i).replaceAll(" ", "").toLowerCase();
				List<Series> seriesData = (List<XYChart.Series>) InformationStore.class.getField(extractInfo + "ChoiceData").get(null);
				curveChoiceData.put(inputChoice.get(i), seriesData);
			}
			
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		//Add Combo Choice and their button to nodes
		try {
			this.comboChoice = (List<String>) InformationStore.class.getField(temp + "ComboChoice").get(null);
			this.comboChoice.forEach(item -> nodes.add(new Text(item)));
			this.comboChoice.forEach(item -> buttonGrid.getChildren().add(generateInputSceneButton(item, "comboBox")));
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		//Adding the headers
		gridPane.addRow(0, nodes.toArray(new Node[nodes.size()]));
		
		//Adding the first input boxes
		addRow();
		
	    this.setContent(vbox);
	    
	}

	private Node generateInputSceneButton(String title, String actions) {
		Button inputSceneButton = new Button(title);
		if (actions.equals("choiceBox")) {
			inputSceneButton.setOnAction(action -> {
				CurveScene scene = new CurveScene(title);
				scene.setOnCloseRequest(event ->{
					updateChoiceBox(title);
					});
			});
		}
		else if (actions.equals("comboBox")) {
			inputSceneButton.setOnAction(action -> {
				ComboScene scene = new ComboScene(title);
				scene.setOnCloseRequest(event ->{
					//updateChoiceBox(title);
					});
			});
		}
		
		return inputSceneButton;
	}

	private void updateChoiceBox(String item) {
		
		for (int i = 0 ; i < inputChoice.size() ; i++) {
			if (inputChoice.get(i).equals(item)) {
				int toUpdate = inputFields.size() + 1 + i;
				List<Series> dataPointer = curveChoiceData.get(item);
				
				
				for (int x = 0 ; x < gridPane.getChildren().size(); x++) {
					Node child = gridPane.getChildren().get(i);
					
					int col = gridPane.getColumnIndex(child);
					int row = gridPane.getRowIndex(child);
					
					if (col == toUpdate && row != 0) {
						
						ChoiceBox box = (ChoiceBox) child;
						String value = (String) box.getValue();
						ChoiceBox newBox = CommonFunction.generateChoiceBox(extractSeriesName(dataPointer));
						newBox.getSelectionModel().select(box.getValue());						
						gridPane.getChildren().remove(child);
						gridPane.add(newBox, col, row);
					}	
					
				}
			}
		}
	}
	

	private Node generateButton(String item) {
		Button btn = new Button(item);	

		switch (item) {
			case ("Add"):{
				btn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						addRow();
					}});
				break;
				}
			case ("Remove"):{
				btn.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						removeRow();
					}});
				break;				
				}
			}
		return btn;
	}

	protected void removeRow() {
    	if ((gridPane.getRowCount()-1) >= 2) 
    	{
        	gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == (gridPane.getRowCount()-1));
    	}		
	}

	protected void addRow() {
		
		//Get the number of row currently in the grid
		int totalCurrentRow = gridPane.getRowCount();
		
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(new Text(totalCurrentRow + ")"));
		
		if (this.inputFields != null) {
			this.inputFields.forEach(item -> 
													nodes.add(CommonFunction.generateTextFieldWithProperties(item))
													);
		}
		
		if (this.inputChoice != null) {
		this.inputChoice.forEach(item -> {
					List<Series> seriesPtr = curveChoiceData.get(item);
					nodes.add(CommonFunction.generateChoiceBox(extractSeriesName(seriesPtr)));
					}
				);
		}
		
		if (this.inputChoice != null || this.inputFields !=null)
			gridPane.addRow(gridPane.getRowCount(), nodes.toArray(new Node[nodes.size()]));

  	}
	public List<String> extractSeriesName(List<Series> series) {
		List<String> names = new ArrayList<String>();
		series.forEach(item -> names.add(item.getName()));
		return names;
	}
}
