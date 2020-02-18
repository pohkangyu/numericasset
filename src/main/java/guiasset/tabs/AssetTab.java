package guiasset.tabs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import data.DataStructure;
import data.DynamicPropertyValueFactory;
import guiasset.CommonFunction;
import guiasset.InformationStore;
import guiasset.scenes.ComboScene;
import guiasset.scenes.CurveScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.ChoiceBox;
public class AssetTab extends Tab {
	
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
		
        
        HBox buttonGrid = new HBox();
        buttonGrid.setPadding(new Insets(10,10,10,10));
        buttonGrid.setSpacing(10);
        
        TableView table = new TableView();
        table.setEditable(true);

        VBox vbox = new VBox(table, buttonGrid);
        this.setContent(vbox);
        
        //Add Input fields to nodes
			try {
				this.inputFields = (List<String>) InformationStore.class.getField(temp + "Fields").get(null);
				
				this.inputFields.forEach(col -> {
					
					//generate a new column
					TableColumn colGenerated = new TableColumn(col);
					
					//set the width of the column in relation to the length of the header that was provided
					colGenerated.setPrefWidth(new Text(col).getLayoutBounds().getWidth() + 50);
					
					//define how to populate the cells inside the table
					colGenerated.setCellValueFactory(
			                new DynamicPropertyValueFactory<DataStructure, Integer>(col));
					
					//define how the cells are updated
					colGenerated.setCellFactory(TextFieldTableCell.<DataStructure>forTableColumn());
					
					colGenerated.setOnEditCommit(new EventHandler() {

						@Override
						public void handle(Event event) {
							System.out.println("xx");
							
						}
						
					});
					
					//finally add the column to the table
					table.getColumns().add(colGenerated);					
				});
				
				table.getItems().add(new DataStructure("Fix Income Name", "Hi"));
				
				
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			

		
		//Add Input Choice and their button to nodes
		try {
			this.inputChoice = (List<String>) InformationStore.class.getField(temp + "Choice").get(null);

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
			this.comboChoice.forEach(item -> buttonGrid.getChildren().add(generateInputSceneButton(item, "comboBox")));
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		//Adding the first input boxes
		
	    this.setContent(vbox);
	    
	}

	private Node generateInputSceneButton(String title, String actions) {
		Button inputSceneButton = new Button(title);
		if (actions.equals("choiceBox")) {
			inputSceneButton.setOnAction(action -> {
				CurveScene scene = new CurveScene(title);
				scene.setOnCloseRequest(event ->{
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
	

	public List<String> extractSeriesName(List<Series> series) {
		List<String> names = new ArrayList<String>();
		series.forEach(item -> names.add(item.getName()));
		return names;
	}
}
