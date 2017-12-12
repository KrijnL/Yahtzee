package domain.db;

import java.util.ArrayList;
import java.util.List;

import domain.DomainException;
import domain.model.Player;

public class PlayerGroup {
	
	private List<Player> players;
	
	public PlayerGroup() {
		players  = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player) {
		if(player == null)
			throw new DbException("player cannot be null");
		for(Player p : players) {
			if(player.getName().equals(p.getName()))
				throw new DbException("player already exists");
		}
		players.add(player);
	}

	public List<Player> getPlayers() {
		return this.players;
	}
	
	public Player getPlayer(Player player) {
		int i = players.indexOf(player);
		if(i < 0)
			throw new DbException("player not found");
		return players.get(i);
	}
	
	public Player getPlayer(String player) {
		for(Player p : players) {
			if(p.getName().equals(player))
				return p;
		}
		throw new DomainException("player not found");
	}
	
	public void setNextTurn(Player player) {
		int i = players.indexOf(player);
		players.get(i).setTurn(false);
		i = (i+1) % players.size();
		players.get(i).setTurn(true);
		
		
	}
	
	public String getActivePlayer() {
		for(Player p: getPlayers()) {
			if(p.getTurn())
				return p.toString();
		}
		throw new DomainException("No active players");
	}
}
