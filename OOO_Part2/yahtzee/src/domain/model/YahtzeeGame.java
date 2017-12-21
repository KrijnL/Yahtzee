package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.DomainException;
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

	public int throwDice(Player player){
		//notifies the observers with a list of integers.
		//if turn is false, the observers will be notified with "noMoreTurns"
		List<Integer> result = new ArrayList<Integer>();
		if(player.getTurn() && turns > 0) {
			turns--;
			for(Dice d: dice) {
				if(!d.isSaved()) {
					result.add(d.throwDice());
				}else {
					result.add(d.getNumberThrown());
				}
			}
			int number = result.get(0);
			boolean yahtzee = true;
			for(int n : result) {
				if(n != number)
					yahtzee = false;
			}
			int returnVal=0;
			if(yahtzee)
				returnVal = number;

			result.add(turns);
			notifyObservers("throwDice", result);
			return returnVal;
		}else {
			notifyObservers("noMoreTurns", null);
			return 0;
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
			for(Dice d : dice) {
			}
			//int[] ints = {counter, i};
			//Notify observers with the index of the dice that is saved.
			notifyObservers("saveDice", i);
		}
	}

	public void updateScoreSheet(Player player, Category category, int score) {
		//verander enkel als categorie nog niet gekozen is.
		//if(getScore(player, category) < 0) {
			getScoreSheet(player).replace(category, score);

			Object[] result = {category, score};
			notifyObservers("updateScore", result);
		//}



	}


	//Returns true when all categories have been filled in on every scoresheet.
	public boolean isDone() {
		boolean done = true;
		for(Player p: players.getPlayers()) {
			for(Category c: Category.values()) {
				if(getScoreSheet(p).get(c) < 0) {
					done = false;
				}
			}
		}
		return done;
	}

	public boolean yahtzeeAttained(Player player) {
		return getScoreSheet(player).get(Category.YAHTZEE) == 50;
	}

	public void resetDice() {
		//remove all dice and make a new set
		dice.clear();
		makeDice();
		//reset turns to 3
		turns = 3;
	}

	public void reset() {
		resetDice();
		resetScoreSheets();
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

	public int getTotalScore(Player player) {
		Map<Category, Integer> sheet = getScoreSheet(player);
		int total = 0;
		
		for(Category c: Category.values()) {
			if(sheet.get(c)>0) {
				total += sheet.get(c);
			}
			if(c.equals(Category.SIXES)) {
				if(total >= 65)
					total += 35;
			}
		}
		return total;
	}

	public Player getWinningPlayer() {
		int score = -1;
		Player player = null;
		for(Player p: players.getPlayers()) {
			if(getTotalScore(p) > score) {
				score = getTotalScore(p);
				player = p;
			}
		}
		return player;
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

	public Category getFirstFreeCategory(Player player) {
		Map<Category, Integer> scoreSheet = getScoreSheet(player);
		for(Category c : Category.values()) {
			if(scoreSheet.get(c) == -1)
				return c;
		}
		throw new DomainException("No more empty categories");
	}

	public int getScore(Player player, Category category) {
		return getScoreSheet(player).get(category);
	}

	public void unSaveDice(String player, int i) {
		if(players.getActivePlayer().equals(player)) {
			int counter = 0;
			for(int x = 0; x < i; x++) {
				if(dice.get(x).isSaved()) {
					counter++;
				}
			}
			dice.get(i).setSaved(false);
			for(Dice d : dice) {
			}
			//int[] ints = {counter, i};
			//Notify observers with the index of the dice that is saved.
			notifyObservers("unSaveDice", i);
		}

	}

	public void removeScore(Player player) {
		scoreSheets.get(player).clear();
		for(Category c :Category.values()) {
			scoreSheets.get(player).put(c, 0);
		}
	}




}
