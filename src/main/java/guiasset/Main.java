package guiasset;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
    	Application.launch(GuiStage.class, new String[]{InformationStore.title, InformationStore.primarySceneCss});
    }
}

