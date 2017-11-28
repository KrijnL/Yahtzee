package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import db.PlayerDb;
import db.PlayerDbMemory;

public class YahtzeeController implements Controller {
	
	PlayerDb players; 
	
	public YahtzeeController() {
		
	}

	@Override
	public void startView() {
		// TODO Auto-generated method stub
		
	}
	
	public void addPlayer(String player) {
		if(this.players == null) {
			throw new ControllerException("no database set.");
		}
		players.addPlayer(player);
	}
	
	public void addDbObserver(Observer o) {
		players.addObserver(o);
	}

	@Override
	public void setDb(PlayerDb db) {
		this.players = db;
		
	}

	@Override
	public List<String> getDb() {
		return players.getPlayers();
	}


	

}
