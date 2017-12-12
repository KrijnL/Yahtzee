package domain.model;

import java.util.List;
import domain.Observer;
import domain.DomainException;
import domain.db.PlayerGroup;

public class Service {
	
	PlayerGroup players;
	YahtzeeGame game;
	
	public Service() {
		players = new PlayerGroup();
		game = new YahtzeeGame(players);
	}
	
	public void addPlayer(Player player) {
		players.addPlayer(player);
		game.setScoreSheets();
	}

	public List<Player> getPlayers() {
		return players.getPlayers();
		
	}
	
	public Player getPlayer(String player) {
		return players.getPlayer(player);
	}
	
	public void throwDice(Player player){
		game.throwDice(player);
	}
	
	public void addObserver(Observer o) {
		game.addObserver(o);
	}
	
	public void saveDice(String player, int i) {
		game.saveDice(player, i);
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
		game.reset();
	}
	
	public void setScore(String player, Category category, int score) {
		game.updateScoreSheet(players.getPlayer(player), category, score);
	}

	
	
}
