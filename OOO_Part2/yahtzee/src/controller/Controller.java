package controller;

import java.util.List;
import java.util.Observer;

import domain.model.Player;

public interface Controller {
	
	public void startView();
	public void addPlayer(String player);
	public List<Player> getPlayers();
	public void handleRoll(String player);
	public void addObserver(String player, Observer o);

}
