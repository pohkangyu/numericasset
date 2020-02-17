package guiasset.tabs;

import guiasset.InformationStore;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PortfolioTab extends Tab{
	
	PortfolioTab(String title){
		
        super(title);
        setClosable(false);
        
        TabPane content = new GenerateTab(InformationStore.portfolioTab);

        content.setSide(Side.TOP);
        setContent(content);

	}

}
