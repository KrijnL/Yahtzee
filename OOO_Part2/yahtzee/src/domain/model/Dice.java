package domain.model;

import java.util.Random;
/**
 * @author Krijn + Glenn + Thomas
 */

public class Dice {
	Random rand;
	
	public Dice() {
		rand = new Random();
	}
	
	public int throwDice() {
		return rand.nextInt(6) + 1;
	}
	
}
