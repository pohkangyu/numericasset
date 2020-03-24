package guiasset;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import org.controlsfx.control.CheckComboBox;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class CommonFunction {
	
	public static GridPane gridBoxGenerator(int padding) {
		
        GridPane gridPane = new GridPane();

        //Setting the padding  
        gridPane.setPadding(new Insets(padding, padding, padding, padding)); 
        
        //Setting the vertical and horizontal gaps between the columns 
        gridPane.setVgap(padding); 
        gridPane.setHgap(padding); 
        
        gridPane.setAlignment(Pos.TOP_LEFT);
        
		return gridPane;

	}
	public static Node getNodeByRowColumnIndex(final int column, final int row, GridPane gridPane) {
	    javafx.scene.Node result = null;
	    ObservableList<Node> children = gridPane.getChildren();

	    for (javafx.scene.Node node : children) {
	        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return result;
	}
	
	public static void toolTipShow(final Node node, String message)
		{
    		final Tooltip tooltip = new Tooltip(message);
    		Node container = node.getParent();
    		Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
    		Point2D coord = container.localToScreen(node.getLayoutX(), node.getLayoutY());
    		tooltip.show(node, coord.getX() + ((Region) node).getWidth() + 30, coord.getY() + 30);
    		
    		Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    tooltip.hide();
                }
            };
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
	                    try {
	                        Thread.sleep(1000);
	                        Platform.runLater(runnable);
	                    } catch (InterruptedException e) {
	                }
                    return null;
                }
            };
            new Thread(sleeper).start();
    	}
	
	
	public static Control generateTextFieldWithProperties(String properties) {
		
		Control returnField = null;
		switch(properties) {
			
			case "Integer": 
				{
					TextField txtField = new TextField();
					Pattern validEditingState = Pattern.compile("^\\d+$");
					UnaryOperator<TextFormatter.Change> filter = c -> {
					    String text = c.getControlNewText();
					    if (validEditingState.matcher(text).matches()) {
					        return c ;
					    } else {
					        return null ;
					    }
					};
					TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, filter);
					txtField.setTextFormatter(textFormatter);
					returnField = txtField;
					break;
				}
			case "String": 
				{
					TextField txtField = new TextField();
					Pattern pattern = Pattern.compile("[a-zA-Z]*");
					UnaryOperator<TextFormatter.Change> filter = c -> {
					    if (pattern.matcher(c.getControlNewText()).matches()) {
					        return c ;
					    } else {
					        return null ;
					    }
					};
					TextFormatter<String> formatter = new TextFormatter<>(filter);
					txtField.setTextFormatter(formatter);
					returnField = txtField;
					break;
				}
			case "2SF": 
				{
					TextField txtField = new TextField();
					Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
					UnaryOperator<TextFormatter.Change> filter = c -> {
					    String text = c.getControlNewText();
					    if (validEditingState.matcher(text).matches()) {
					        return c ;
					    } else {
					        return null ;
					    }
					};
					StringConverter<Double> converter = new StringConverter<Double>() {

					    @Override
					    public Double fromString(String s) {
					        if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
					            return 0.0 ;
					        } else {
					        	if (s.contains(".")) {
					        		s = s.concat("000");
					        	}
					        	else
					        	{
					        		s = s.concat(".000");
					        	}
					            return Double.valueOf(s.substring(0, s.indexOf('.') + 4));
					        }
					    }
					    @Override
					    public String toString(Double d) {
					        return d.toString();
					    }
					};
					
					TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
					txtField.setTextFormatter(textFormatter);
					returnField = txtField;
					break;
				}
			case "Date": 
				{
					returnField = new DatePicker();		
					break;
				}
			default:
				{
					returnField = new TextField();
				}
		}
		returnField.setMaxWidth(100);
		return returnField;		
	}
	
	public static ChoiceBox generateChoiceBox(List list) {
		
		ChoiceBox choiceBox = new ChoiceBox();
		choiceBox.setItems(FXCollections.observableArrayList(list));
		choiceBox.getSelectionModel().selectFirst();
		
		return choiceBox;
	}
	public static CheckComboBox generateComboBox(List<String> list) {
		// TODO Auto-generated method stub
		return new CheckComboBox<String>(FXCollections.observableArrayList(list));
	}
	
	public static HBox HBoxAdd(int padding, Node... children) {
		HBox temp = new HBox();
		temp.setPadding(new Insets(padding));
		temp.alignmentProperty().set(Pos.CENTER);
		temp.setSpacing(10);
		
		temp.getChildren().addAll(children);
		return temp;
	}
	
	public static HBox HBoxAddLeft(int padding, Node... children) {
		HBox temp = new HBox();
		temp.setPadding(new Insets(padding));
		temp.alignmentProperty().set(Pos.BASELINE_LEFT);
		temp.setSpacing(10);
		
		temp.getChildren().addAll(children);
		return temp;
	}
	
	public static VBox VBoxAdd(int padding, Node... children) {
		VBox temp = new VBox();
		temp.setPadding(new Insets(padding));
		temp.alignmentProperty().set(Pos.CENTER);
		temp.setSpacing(10);
		temp.getChildren().addAll(children);
		return temp;
	}
	
}
