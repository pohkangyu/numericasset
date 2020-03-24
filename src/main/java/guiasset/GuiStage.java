package guiasset;

import java.util.List;
import javafx.stage.Screen;
import guiasset.tabs.GenerateTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiStage extends Application {
	
	//Main GUI application to be launch

    public static double paneWidth;
	public static double paneHeight;

	@Override
    public void start(Stage primaryStage) throws Exception {
    	

        
    	//obtain parameters from main as args[]
    	Parameters params = getParameters();
    	//Get title and css style
    	List<String> args = params.getRaw();

    	//create a new scene
        Scene scene = new Scene(new GenerateTab(InformationStore.mainSceneTab));
        
        //set title
        primaryStage.setTitle(args.get(0));
        
        //add style and config scene
        scene.getStylesheets().addAll(args.get(1));
        primaryStage.setScene(scene);
        
        //Get primary screen bounds
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        
        paneWidth = screenWidth * InformationStore.widthSize;
        paneHeight = screenHeight * InformationStore.heightSize;
        
        primaryStage.setWidth(paneWidth);
        primaryStage.setHeight(paneHeight);
        
        primaryStage.setX(screenWidth * (1 - InformationStore.widthSize) / 2);
        primaryStage.setY(screenHeight * (1 - InformationStore.heightSize) / 2);
        
        primaryStage.show();
    }

}
