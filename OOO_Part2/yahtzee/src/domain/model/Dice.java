package domain.model;

import java.util.Random;
/**
 * @author Krijn + Glenn + Thomas
 */

public class Dice {
	private Random rand;
	private boolean saved;
	private int numberThrown;
	
	public Dice() {
		rand = new Random();
		this.saved=false;
	}
	
	
	public int throwDice() {
		//this.numberThrown = rand.nextInt(6) + 1;
		numberThrown = 5;
		return numberThrown;
	}
	
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
	public void setSaved() {
		this.saved = true;
	}
	
	public boolean isSaved() {
		return this.saved;
	}
	
	
}
