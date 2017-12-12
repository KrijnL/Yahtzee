package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Observer;
import domain.Subject;
import domain.db.PlayerGroup;

public class YahtzeeGame implements Subject {
	private PlayerGroup players;
	private List<Dice> dice;
	private Map<Player, Map<Category, Integer>> scoreSheets;
	private ArrayList<Observer> observers;
	final static int MAX_TURNS = 3;
	int turns;


	public YahtzeeGame(PlayerGroup players) {
		observers = new ArrayList<Observer>();
		this.players = players;

		dice = new ArrayList<Dice>();
		reset();
		//makeScoreSheets();
		//resetScoreSheets();
		//turns = MAX_TURNS;

	}

	public void throwDice(Player player){
		//notifies the observers with a list of integers.
		//if turn is false, the observers will be notified with "noMoreTurns"
		List<Integer> result = new ArrayList<Integer>();
		if(player.getTurn() && turns > 0) {
			turns--;
			for(Dice d: dice) {
				if(!d.isSaved()) {
					result.add(d.throwDice());
				}
			}
			result.add(turns);
			notifyObservers("throwDice", result);
		}else {
			notifyObservers("noMoreTurns", null);
		}

	}

	public void saveDice(String player, int i) {
		if(players.getActivePlayer().equals(player)) {
			int counter = 0;
			for(int x = 0; x < i; x++) {
				if(!dice.get(x).isSaved()) {
					counter++;
				}
			}
			dice.get(i).setSaved();
			System.out.println("dice: \n");
			for(Dice d : dice) {
				System.out.println(d.isSaved());
			}

			//Notify observers with the index of the dice that is saved.
			notifyObservers("saveDice", counter);
		}
	}

	public void updateScoreSheet(Player player, Category category, int score) {
		//verander enkel als categorie nog niet gekozen is.

		getScoreSheet(player).replace(category, score);
		Object[] result = {category, score};
		notifyObservers("updateScore", result);


	}

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

	private void makeScoreSheets() {
		scoreSheets = new HashMap<Player, Map<Category, Integer>>();
		for(Player p: players.getPlayers()) {
			HashMap<Category, Integer> scoreSheet = new HashMap<Category, Integer>();
			scoreSheets.put(p, scoreSheet);
		}
	}

	private void resetScoreSheet(Player player) {
		scoreSheets.get(player).clear();
		for(Category c :Category.values()) {
			scoreSheets.get(player).put(c, -1);
		}
	}

	private void resetScoreSheets() {
		for(Player p: players.getPlayers()) {
			resetScoreSheet(p);
		}
	}

	public void setScoreSheets() {
		makeScoreSheets();
		resetScoreSheets();
	}

	private Map<Category, Integer> getScoreSheet(Player player){
		return scoreSheets.get(player);
	}


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




}
