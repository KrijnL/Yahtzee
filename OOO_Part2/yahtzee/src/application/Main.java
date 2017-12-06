package application;
	

import controller.Controller;
import controller.MainController;

import javafx.application.Application;
import javafx.stage.Stage;


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