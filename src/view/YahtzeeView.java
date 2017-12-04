package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class YahtzeeView extends Stage implements View {
	private Controller controller;
	
	private GridPane panel;
	private Stage stage;
	private List<Stage> gameStages;
	
	public YahtzeeView(String name, Stage stage, GridPane pane){
		setStage(stage);
		setPanel(pane);
		
        Scene mainScene = new Scene(pane, 400, 150);
        getStage().setTitle(name);
        getStage().setScene(mainScene);
        sizeToScene();
        
        
	}
	
	public YahtzeeWindow openGameWindow(String player, Controller controller, boolean active) {
		Stage gameStage = new Stage();
		YahtzeeWindow gameWindow = new YahtzeeWindow(player, controller, active);
		gameStage.setTitle(player);
		gameStage.setScene(new Scene(gameWindow, 500, 500));
		gameStage.show();
		//controller.addPlayerObserver(player, gameWindow);
		return gameWindow;
		
		
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

	private GridPane getPanel() {
		return panel;
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
