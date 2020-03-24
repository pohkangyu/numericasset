package guiasset.scenes;

import java.util.ArrayList;
import java.util.List;

import guiasset.InformationStore;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ComboScene extends Stage{
	ArrayList<String> currentCombo;
	public ComboScene(String title) {
		
		try {
			this.currentCombo = (ArrayList<String>) InformationStore.class.getField(title.replace(" ", "").toLowerCase() + "ComboData").get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.currentCombo = new ArrayList<String>();
		}
			
		
		HBox topLayer = new HBox();
		VBox gui = new VBox();
		gui.alignmentProperty().set(Pos.TOP_CENTER);
		gui.setPadding(new Insets(10));
		gui.setSpacing(10);
		
		
		Button addButton = new Button("Add");	
		addButton.setMaxWidth(Double.MAX_VALUE);
		Button removeButton = new Button("Remove");
		removeButton.setMaxWidth(Double.MAX_VALUE);
		
		TextField newName = new TextField();
		
		gui.getChildren().addAll(new Text("Category Name: "),newName , addButton, removeButton);
		
		ListView listView = new ListView();
		currentCombo.forEach(item -> listView.getItems().add(item));
				
		addButton.setOnAction(event ->{
			
			String newCat = newName.getText();
			
			if (newCat!= null && !newCat.equals("") && !currentCombo.contains(newCat)) {
				listView.getItems().add(newCat);
				currentCombo.add(newCat);
			}
		});
		
		removeButton.setOnAction(event ->{
			listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
		});
		
		topLayer.getChildren().addAll(listView, gui);
				
        this.setTitle(title);
        this.setScene(new Scene(topLayer));
        
        
        this.show();
	}

}
