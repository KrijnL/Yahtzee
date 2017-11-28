package application;
	
import javax.swing.JOptionPane;

import controller.Controller;
import controller.MainController;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.InputWindow;
import view.View;
import view.YahtzeeView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	Controller controller;
	
	@Override
	public void start(Stage primaryStage) {
		controller = new MainController(primaryStage);
		controller.startView();
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}