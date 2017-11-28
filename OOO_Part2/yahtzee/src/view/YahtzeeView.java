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
import javafx.stage.Stage;

public class YahtzeeView extends Stage implements View {
	private Controller controller;
	
	private InputPanel panel;
	private Stage stage;
	private List<Stage> gameStages;
	
	public YahtzeeView(String name, Stage stage, InputPanel pane){
		
		
		
		setStage(stage);
		setPanel(pane);
		
        Scene mainScene = new Scene(pane, 400, 150);
        getStage().setTitle(name);
        getStage().setScene(mainScene);
        sizeToScene();
        stage.show();
        
        /*secondStage = new Stage();
        secondStage.setTitle(name);
        secondStage.setScene(new Scene(pane, 400, 150));
        sizeToScene();
        secondStage.show();*/
        
	}
	
	public void openGameViews() {
		getStage().hide();
		gameStages = new ArrayList<Stage>();
		for(String player: getController().getDb()) {
			Stage gameStage = new Stage();
			YahtzeePanel gamePanel = new YahtzeePanel();
			gameStage.setScene(new Scene(gamePanel, 500, 500));
			gameStages.add(gameStage);
		}
		for(Stage gameStage: gameStages) {
			gameStage.show();
		}
		
		
	}
	

	private Stage getStage() {
		return this.stage;
	}

	private void setStage(Stage stage) {
		this.stage = stage;
	}

	private void setPanel(InputPanel pane) {
		this.panel = pane;
	}

	private InputPanel getPanel() {
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
	public void addEventListener(EventHandler<ActionEvent> listener){
		getPanel().addEventListener(listener);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getName() {
		return getPanel().getName();
	}
	
	
	
}
