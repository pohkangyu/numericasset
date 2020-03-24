package guiasset.tabs;

import guiasset.CommonFunction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScenarioTab extends Tab {
	ScenarioTab(String title){
		super(title);
		this.setClosable(false);
		
		HBox content = new HBox();
		content.alignmentProperty().set(Pos.CENTER);
		
        ListView Lst1 = new ListView();     
        VBox vbox1 = CommonFunction.VBoxAdd(10, new Text("Scenario"), Lst1);
        
        TableView Lst2 = new TableView();
        VBox vbox2 = CommonFunction.VBoxAdd(10, new Text("Containing Scenario"), Lst2);
        
        HBox listBox = new HBox();
        listBox.alignmentProperty().set(Pos.CENTER);
        
        //HBox tables = CommonFunction.HBoxAdd(vbox1, vbox2);
        
        
        VBox controlGrid1 = new VBox();
        VBox controlGrid2 = new VBox();
        
        
        controlGrid1.getChildren().add(CommonFunction.HBoxAddLeft(2, new Text("Name:"), new TextField()));
        controlGrid1.getChildren().add(CommonFunction.HBoxAdd(2, new Button("Add"), new Button("Remove")));


        controlGrid2.getChildren().add(CommonFunction.HBoxAddLeft(1, new Text("Choose Asset:"), new ChoiceBox()));
        controlGrid2.getChildren().add(CommonFunction.HBoxAddLeft(1, new Text("Choose Effect:"), new ChoiceBox()));
        controlGrid2.getChildren().add(CommonFunction.HBoxAddLeft(1, new Text("Choose Rate (+/-)"), new TextField()));
	    controlGrid2.getChildren().add(CommonFunction.HBoxAdd(1, new Button("Add"), new Button("Remove")));
        
	    //HBox control = CommonFunction.HBoxAdd(controlGrid1, controlGrid2);
	    
	    GridPane grid = CommonFunction.gridBoxGenerator(10);
	    
        grid.add(vbox1, 0, 0);
        grid.add(vbox2, 1, 0);
	    
        grid.add(controlGrid1, 0, 1);
        grid.add(controlGrid2, 1, 1);

        content.getChildren().addAll(grid);
        
        listBox.setPadding(new Insets(10));
        setContent(content);
		
	}
}
