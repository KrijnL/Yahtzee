package application;
	
import javax.swing.JOptionPane;

import controller.Controller;
import controller.ControllerCommon;
import controller.YahtzeeController;
import controller.keylistener.NameButtonListener;
import db.PlayerDb;
import db.PlayerDbMemory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.InputPanel;
import view.View;
import view.YahtzeeView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		Controller controller = new YahtzeeController();
		PlayerDb players = new PlayerDbMemory();
		//Starten met input panelen
		InputPanel pane = new InputPanel();
		View view = new YahtzeeView("yahtzee", primaryStage, pane);
		EventHandler<ActionEvent> handler = new NameButtonListener(view, players);
		pane.addEventListener(handler);
		players.addObserver(pane);
		controller.setDb(players);
		view.setController(controller);
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}