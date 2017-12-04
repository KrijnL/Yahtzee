package domain.model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import domain.db.PlayerGroup;

public class Service {
	
	PlayerGroup players;
	
	public Service() {
		players = new PlayerGroup();
	}
	
	public void addPlayer(Player player) {
		players.addPlayer(player);
	}

	public List<Player> getPlayers() {
		return players.getPlayers();
		
	}
	
	public Player getPlayer(String player) {
		return players.getPlayer(player);
	}
	
	public void throwDice(Player player){
		players.getPlayer(player).throwDice();
		if(player.getTurn()) {
			players.setNextTurn(player);
		}
	}
	
	public void addObserver(String player, Observer o) {
		getPlayer(player).addObserver(o);
	}

	
	
}
