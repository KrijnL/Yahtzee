package domain.model;

import java.util.List;
import java.util.Observable;
import view.Observer;
import domain.DomainException;
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
	}
	
	public void addPlayerObserver(Player player, Observer o) {
		player.addObserver(o);
	}
	
	public void saveDice(String player, int i) {
		getPlayer(player).saveDice(i);
	}
	
	public void setNextTurn(String player) {
		players.setNextTurn(getPlayer(player));
		
	}

	public Player getActivePlayer() {
		for(Player p: getPlayers()) {
			if(p.getTurn())
				return p;
		}
		throw new DomainException("No active players");
	}
	
	public void resetDice() {
		for(Player p: getPlayers()) {
			p.reset();
		}
	}
	
	public void setScore(String player, Category category, int score) {
		getPlayer(player).updateScoreSheet(category, score);
	}

	
	
}
