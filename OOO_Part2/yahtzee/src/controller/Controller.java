package controller;

import java.util.List;

import domain.model.Category;
import domain.model.Player;
import domain.Observer;

public interface Controller extends InputController{
	
	public void startView();
	public void addPlayer(String player);
	public List<Player> getPlayers();
	public void handleRoll(String player);
	public void addObserver( Observer o);
	public void saveDice(String player, int dice);
	public void handleEndTurn(String player);
	public String getActivePlayer();
	public void calculateScore(String player, List<Integer> result, Category value);

}
