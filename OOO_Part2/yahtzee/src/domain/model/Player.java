package domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import domain.DomainException;

public class Player extends Observable {
	private String name;
	private List<Dice> dice;
	private boolean turn;


	public Player(String name) {
		if(name == null || name.isEmpty())
			throw new DomainException("name cannot be empty");
		this.name = name;

		setTurn(false);

		dice = new ArrayList<Dice>();
		for(int i =0; i< 6; i++ ) {
			dice.add(new Dice());
		}
	}


	public void throwDice(){
		List<Integer> result = new ArrayList<Integer>();
		if(turn) {
			for(Dice d: dice) {
				result.add(d.throwDice());
			}
		}
		setChanged();
		notifyObservers(result);
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
}
