package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Observer;
import domain.DomainException;
import domain.Subject;

public class Player{
	private String name;
	private List<Dice> dice;
	private boolean turn;
	private int turns;
	private ArrayList<Observer> observers;
	private Map<Category, Integer> scoreSheet;


	public Player(String name) {
		//set name
		if(name == null || name.isEmpty())
			throw new DomainException("name cannot be empty");
		this.name = name;
		//set turn
		setTurn(false);
		this.turns = 3;

		//init Dice
		dice = new ArrayList<Dice>();
		makeDice();

		//init observers
		observers = new ArrayList<Observer>();

		//init scoreSheet
		scoreSheet = new HashMap<Category, Integer>();
		resetScoreSheet();
	}


	public void throwDice(){
		//notifies the observers with a list of integers.
		//if turn is false, the observers will be notified with "noMoreTurns"
		List<Integer> result = new ArrayList<Integer>();
		if(turn && turns > 0) {
			turns--;
			for(Dice d: dice) {
				if(!d.isSaved()) {
					result.add(d.throwDice());
				}
			}
			result.add(turns);
			//notifyObservers("throwDice", result);
		}else {
			//notifyObservers("noMoreTurns", null);
		}

	}


	public void updateScoreSheet(Category category, int score) {
		//verander enkel als categorie nog niet gekozen is.
		if(scoreSheet.get(category) < 0) {
			scoreSheet.replace(category, score);
			Object[] result = {category, score};
			//notifyObservers("updateScore", result);
		}else {
			//notifyObservers("categoryUsed", category);
		}
	}


	public String getName() {
		return this.name;
	}

	public boolean getTurn() {
		return this.turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	/*public void saveDice(int i) {
		int counter = 0;
		for(int x = 0; x < i; x++) {
			if(!dice.get(x).isSaved()) {
				counter++;
			}
		}
		dice.get(i).setSaved();

		//Notify observers with the index of the dice that is saved.
		//notifyObservers("saveDice", counter);

	}*/

	public void reset() {
		//remove all dice and make a new set
		dice.clear();
		makeDice();
		//reset turns to 3
		turns = 3;
	}

	private void makeDice() {
		for(int i =0; i< 5; i++ ) {
			dice.add(new Dice());
		}
	}

	private void resetScoreSheet() {
		scoreSheet.clear();
		for(Category c :Category.values()) {
			scoreSheet.put(c, -1);
		}
	}

	@Override
	public String toString() {
		return this.name;
	}

	/*
	@Override
	public void addObserver(Observer o) {
		observers.add(o);

	}


	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);

	}



	@Override 
	public void notifyObservers(String type, Object args){
		for(Observer o: observers) {
			o.update(type , args);
		}
	}
	*/






}
