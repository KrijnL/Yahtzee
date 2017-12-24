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
	private boolean turn;


	public Player(String name) {
		//set name
		if(name == null || name.isEmpty())
			throw new DomainException("name cannot be empty");
		this.name = name;
		//set turn
		setTurn(false);
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

	@Override
	public String toString() {
		return this.name;
	}
}

	