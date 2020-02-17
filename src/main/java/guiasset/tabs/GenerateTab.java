package guiasset.tabs;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import guiasset.InformationStore;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

public class GenerateTab extends TabPane{

	public GenerateTab(String[] level1tab) {
		//Tab Planes to insert objects
		super();
		
		//Reflections, cast and generate Tabs to be added
		for (int i = 0; i < level1tab.length; i++) {
			try {
				
				Class<?> reflectedClass = Class.forName(InformationStore.dir_path + level1tab[i] + 
						InformationStore.append_tab);
				Tab tabAdd = (Tab) reflectedClass.getDeclaredConstructor(String.class).newInstance(level1tab[i]);

				this.getTabs().add(tabAdd);
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}		
	}
}
