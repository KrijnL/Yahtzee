package controller;

import java.util.List;
import java.util.Observer;

import db.PlayerDb;

public interface Controller {
	
	public void addPlayer(String player);
	public void startView();
	public void setDb(PlayerDb db);
	public List<String> getDb();

}
