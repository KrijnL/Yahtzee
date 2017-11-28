package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import domain.model.Player;
import domain.model.Service;
import javafx.stage.Stage;
import view.InputWindow;
import view.View;
import view.YahtzeeView;


public class MainController implements Controller, InputController, YahtzeeController {
	
	private Service service;
	private View view;
	private boolean first = true;
	
	public MainController(Stage primaryStage) {
		service = new Service();
		view = new YahtzeeView("yahtzee", primaryStage, new InputWindow(this) );
	}

	@Override
	public void startView() {
		view.start();
		
	}
	
	public void addPlayer(String name) {
		Player player = new Player(name);
		if(first) {
			first = false;
			player.setTurn(true);
		}
		service.addPlayer(player);
	}
	
	public List<Player> getPlayers(){
		return service.getPlayers();
	}

	@Override
	public void handleOk(String text) {
		
		this.addPlayer(text);
		view.openGameWindow(text, this);
		
		
	}

	@Override
	public void handleCancel() {
		
		view.closeInput();
		
	}
	
	public void addObserver(String player, Observer o) {
		service.addObserver(player, o);
	}

	@Override
	public void handleRoll(String player) {
		view.closeInput();
		Player p = service.getPlayer(player);
		service.throwDice(p);
		
	}
	
	
	

	

}
