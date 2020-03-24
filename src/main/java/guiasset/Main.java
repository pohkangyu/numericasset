package guiasset;

import javafx.application.Application;

public class Main {
	//Call upon the application to launch
    public static void main(String[] args) {
    	Application.launch(GuiStage.class, new String[]{InformationStore.title, InformationStore.primarySceneCss});
    }
}

