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
	
	public int throwDice(Player player){
		return game.throwDice(player);
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
		game.resetDice();
	}
	
	public void setScore(String player, Category category, int score) {
		game.updateScoreSheet(players.getPlayer(player), category, score);
	}
	
	public boolean yahtzeeAttained(Player player) {
		return game.yahtzeeAttained(player);
	}

	public boolean isGameDone() {
		
		return game.isDone();
	}

	public String getWinningPlayer() {
		return game.getWinningPlayer().toString();
	}

	public int getTotalPoints(String winner) {
		return game.getTotalScore(getPlayer(winner));
	}

	public Category getFirstFreeCategory(Player player) {
		return game.getFirstFreeCategory(player);
		
	}
	
	public int getScore(Player player, Category category) {
		return game.getScore(player, category);
	}
	
	public void addBonus(Player player) {
		game.updateScoreSheet(player, game.getFirstFreeCategory(player), 100);
	}

	public void unSaveDice(String player, int dice) {
		game.unSaveDice(player, dice);
		
	}

	public void resetScore(String player) {
		game.removeScore(this.getPlayer(player));
		
	}

	public void resetGame() {
		game.reset();
		
	}

	public void resetTurn() {
		players.resetTurn();
	}

	
	
}
