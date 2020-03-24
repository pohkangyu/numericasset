package guiasset.tabs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.controlsfx.control.CheckComboBox;

import guiasset.CommonFunction;
import guiasset.GuiStage;
import guiasset.InformationStore;
import guiasset.scenes.ComboScene;
import guiasset.scenes.CurveScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.control.ComboBox;
public class AssetTab extends Tab {
	
	private GridPane gridPane = CommonFunction.gridBoxGenerator(10);
	//Fields for the header of the col
	private List<String> inputFieldName;
	private List<String> inputFieldProperties;
	//Choices for inputs type
	private List<String> choiceFieldName;
	private HashMap<String, List<Series>> choiceFieldData = new HashMap<String, List<Series>>();

	//buttons for combo
	private List<String> comboChoiceName;
	private HashMap<String, List<String>> comboChoiceData = new HashMap<String, List<String>>();

	//data for the curves
	
	AssetTab(String title){
		super(title);
		this.setClosable(false);
		String temp = title.replaceAll(" ", "").toLowerCase();
		

		//This tab consist of 1 x Scroll pane of items and 1 row of buttons that is in a VBox
        gridPane = CommonFunction.gridBoxGenerator(10);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        
        HBox buttonGrid = new HBox();
        buttonGrid.setPadding(new Insets(10,10,10,10));
        buttonGrid.setSpacing(10);

        VBox vbox = new VBox(scrollPane, buttonGrid);
        vbox.setAlignment(Pos.BOTTOM_LEFT);
        scrollPane.setMinWidth(400);
        gridPane.setMinWidth(400);
        buttonGrid.setMinWidth(400);

        this.setContent(vbox);
        
		//Add button for removing and adding rows
		Arrays.asList("Add", "Remove").forEach(item -> buttonGrid.getChildren().add(generateButton(item)));
        
		//Init nodes of header to be added
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(new Text("No."));
		
        //Add Input fields to nodes
		try {
			this.inputFieldName = (List<String>) InformationStore.class.getField(temp + "Fields").get(null);
			this.inputFieldProperties = (List<String>) InformationStore.class.getField(temp + "Properties").get(null);
			this.inputFieldName.forEach(item -> nodes.add(new Text(item)));
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		//Add Input Choice and their button to nodes
		try {
			//obtaining data and add the text for the headers
			this.choiceFieldName = (List<String>) InformationStore.class.getField(temp + "Choice").get(null);
			this.choiceFieldName.forEach(item -> nodes.add(new Text(item)));			
			
			//try getting all the data for the input choices
			for (int i = 0 ; i < this.choiceFieldName.size() ; i++) {
				String extractInfo = choiceFieldName.get(i).replaceAll(" ", "").toLowerCase();
				List<Series> seriesData = (List<XYChart.Series>) InformationStore.class.getField(extractInfo + "ChoiceData").get(null);
				choiceFieldData.put(choiceFieldName.get(i), seriesData);
			}
			
			//generating buttons for curve input
			for (Entry<String, List<Series>> item : choiceFieldData.entrySet()) {
				Node btn = generateInputSceneButton(item.getKey(), "choiceBox");
				buttonGrid.getChildren().add(btn);
			}
						
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		//Add Combo Choice and their button to nodes
		try {
			this.comboChoiceName = (List<String>) InformationStore.class.getField(temp.replace(" ", "").toLowerCase() + "ComboChoice").get(null);
			this.comboChoiceName.forEach(item -> nodes.add(new Text(item)));
			this.comboChoiceName.forEach(item -> buttonGrid.getChildren().add(generateInputSceneButton(item, "comboBox")));
			
			//try getting all the data for the input choices				
			for (int i = 0 ; i < this.comboChoiceName.size() ; i++) {
				String extractInfo = comboChoiceName.get(i).replaceAll(" ", "").toLowerCase();
				List<String> strLst = (List<String>) InformationStore.class.getField(extractInfo + "ComboData").get(null);
				comboChoiceData.put(comboChoiceName.get(i), strLst);
			}
			
			
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
					updateComboBox(title);
					});
			});
		}
		
		return inputSceneButton;
	}
	
	
	private void updateComboBox(String item) {
		for (int i = 0 ; i < choiceFieldName.size() ; i++) {

			if (comboChoiceName.get(i).equals(item)) {

				int toUpdate = inputFieldName.size() + choiceFieldName.size() + 1 + i;
				List<String> dataPointer = comboChoiceData.get(item);
				
				for (int x = 0 ; x < gridPane.getChildren().size(); x++) {
					
					Node child = gridPane.getChildren().get(x);
					
					int col = gridPane.getColumnIndex(child);
					int row = gridPane.getRowIndex(child);
					
					
					if (col == toUpdate && row != 0) {
						CheckComboBox box = (CheckComboBox) child;
						CheckComboBox newBox = CommonFunction.generateComboBox(dataPointer);
						
						box.getCheckModel().getCheckedItems().forEach(action -> { newBox.getCheckModel().check(action);});

						gridPane.getChildren().remove(child);
						gridPane.add(newBox, col, row);
					}	
					
				}
			}
		}
		
	}

	private void updateChoiceBox(String item) {
		
		for (int i = 0 ; i < choiceFieldName.size() ; i++) {

			if (choiceFieldName.get(i).equals(item)) {
				
				int toUpdate = inputFieldName.size() + 1 + i;
				List<Series> dataPointer = choiceFieldData.get(item);
				
				for (int x = 0 ; x < gridPane.getChildren().size(); x++) {
					
					Node child = gridPane.getChildren().get(x);
					
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
		
		if (this.inputFieldProperties != null) {
			this.inputFieldProperties.forEach(item -> 
													nodes.add(CommonFunction.generateTextFieldWithProperties(item))
													);
		}
		
		if (this.choiceFieldName != null) {
			
		this.choiceFieldName.forEach(item -> {
					List<Series> seriesPtr = choiceFieldData.get(item);
					nodes.add(CommonFunction.generateChoiceBox(extractSeriesName(seriesPtr)));
					}
				);
		}
		
		this.comboChoiceName.forEach(item -> {
			CheckComboBox<String> box = CommonFunction.generateComboBox(comboChoiceData.get(item));
			box.setMaxWidth(100);
			nodes.add(box);		
		});

				
		
		if (this.choiceFieldName != null || this.inputFieldName !=null)
			gridPane.addRow(gridPane.getRowCount(), nodes.toArray(new Node[nodes.size()]));

  	}
	public List<String> extractSeriesName(List<Series> series) {
		List<String> names = new ArrayList<String>();
		series.forEach(item -> names.add(item.getName()));
		return names;
	}
}
