package guiasset.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ComboScene extends Stage{

		public ComboScene(String title) {
			
			HBox topLayer = new HBox();
			VBox gui = new VBox();
			gui.alignmentProperty().set(Pos.TOP_CENTER);
			gui.setPadding(new Insets(10));
			gui.setSpacing(10);
			
			
			Button addButton = new Button("Add");	
			addButton.setMaxWidth(Double.MAX_VALUE);
			Button removeButton = new Button("Remove");
			removeButton.setMaxWidth(Double.MAX_VALUE);
			gui.getChildren().addAll(new Text("Category Name: "), new TextField(), addButton, removeButton);
			
			
			TableView table = new TableView();
			TableColumn firstCol = new TableColumn("Categories");
			table.getColumns().add(firstCol);
			
			topLayer.getChildren().addAll(table, gui);
					
	        this.setTitle(title);
	        this.setScene(new Scene(topLayer));
	        this.show();
		}

}
