package view;


import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import controller.OptionController;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class YahtzeeView extends Stage implements View {
	private Controller controller;
	
	
	private GridPane panel;
	private Stage stage;
	private Map<String, Stage> gameStages;
	
	public YahtzeeView(String name, Stage stage, GridPane pane){
		setStage(stage);
		setPanel(pane);
		stage.setAlwaysOnTop(true);
        Scene mainScene = new Scene(pane, 400, 150);
        getStage().setTitle(name);
        getStage().setScene(mainScene);
        sizeToScene();
        
        gameStages = new HashMap<String, Stage>();
        
        
	}
	
	public YahtzeeWindow openGameWindow(String player, Controller controller, boolean active) {
		Stage gameStage = new Stage();
		YahtzeeWindow gameWindow = new YahtzeeWindow(player, controller, active);
		gameStage.setTitle(player);
		gameStage.setScene(new Scene(gameWindow, 500, 500));
		gameStages.put(player, gameStage);
		gameStage.show();
		controller.addObserver(gameWindow);
		return gameWindow;
		
		
	}
	
	public void openEndWindow(OptionController controller, int points,  String winner) {
		Stage popupStage = new Stage();
		EndWindow endWindow = new EndWindow(controller, points, winner);
		popupStage.setTitle(winner + " has won");
		popupStage.setScene(new Scene(endWindow, 400, 150));
		popupStage.show();
	}
	
	public void closeInput() {
		stage.hide();
	}
	

	private Stage getStage() {
		return this.stage;
	}

	private void setStage(Stage stage) {
		this.stage = stage;
	}

	private void setPanel(GridPane pane) {
		this.panel = pane;
	}


	private Controller getController() {
		return controller;
	}

	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void start() {
		getStage().show();		
	}
	
	@Override
	public String getPlayer() {
		return this.getPlayer();
	}


	



	
	
	
}
