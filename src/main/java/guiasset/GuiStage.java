package guiasset;

import java.util.List;
import guiasset.tabs.GenerateTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiStage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	//obtain parameters from main as args[]
    	Parameters params = getParameters();
    	List<String> args = params.getRaw();

    	//create a new scene
        Scene scene = new Scene(new GenerateTab(InformationStore.mainSceneTab));
        
        //set title
        primaryStage.setTitle(args.get(0));
        
        //add style and config scene
        scene.getStylesheets().addAll(args.get(1));
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setWidth(1024);
        primaryStage.show();
    }

}
