package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.DomainException;
import domain.Observer;
import domain.model.Category;
import domain.model.Player;
import domain.model.Service;
import javafx.stage.Stage;
import view.InputWindow;
import view.View;
import view.YahtzeeView;
import view.YahtzeeWindow;


public class MainController implements Controller {

	private Service service;
	private View view;
	private boolean first = true;
	private Map<String, YahtzeeWindow> windows;
	private boolean firstRoll = true;

	public MainController(Stage primaryStage) {
		service = new Service();
		view = new YahtzeeView("yahtzee", primaryStage, new InputWindow(this) );
		windows = new HashMap<String, YahtzeeWindow>();
	}
	

	@Override
	public void startView() {
		view.start();

	}

	public void addPlayer(String name) {
		Player player = new Player(name);
		if(first) {
			first = false;
			player.setTurn(true);
		}
		service.addPlayer(player);
	}

	public List<Player> getPlayers(){
		return service.getPlayers();
	}

	@Override
	public void handleOk(String player) {
		//Create player
		this.addPlayer(player);

		//Add a new gamewindow to the HashMap with its player as key.
		windows.put(player, view.openGameWindow(player, this, service.getPlayer(player).getTurn()));


	}

	@Override
	public void handleCancel() {

		view.closeInput();

	}

	public void addObserver(Observer o) {
		service.addObserver(o);
	}

	@Override
	public void handleRoll(String playerName) {
		if(firstRoll) {
			firstRoll =false;
			
			
		}
		view.closeInput();
		Player p = service.getPlayer(playerName);
		int number = service.throwDice(p);
		if(number > 0 && service.yahtzeeAttained(p)) {
			addBonus(number, p);
		}

	}

	private void addBonus(int number, Player player) {
		/*String match = match(result, Category.YAHTZEE.getPattern());
		*/
		
		Category cat = null;
		try {	
				cat = service.getFirstFreeCategory(player);
				service.addBonus(player);
				switch(number) {
				case 1:
					cat = Category.ACES;
					break;
				case 2:
					cat = Category.TWOS;
					break;
				case 3:
					cat = Category.THREES;
					break;
				case 4:
					cat = Category.FOURS;
					break;
				case 5:
					cat = Category.FIVES;
					break;
				case 6:
					cat = Category.SIXES;
					break;
				default:
					cat = null;
				}
				ArrayList<Integer> numbers = new ArrayList<Integer>();
				for( int i=0; i<6; i++) {
					numbers.add(number);
				}
				int score = service.getScore(player, cat );
				if(score == -1) {
					service.setScore(player.getName(), cat, number*5);
				}else if(service.getScore(player, Category.FULL_HOUSE)==-1){
					calculateScore(player.getName(), numbers, Category.FULL_HOUSE);
				}else if(service.getScore(player, Category.SMALL_STRAIGHT) == -1) {
					calculateScore(player.getName(), numbers, Category.SMALL_STRAIGHT);
				}else if(service.getScore(player, Category.LARGE_STRAIGHT) == -1) {
					calculateScore(player.getName(), numbers, Category.LARGE_STRAIGHT);
				}
				handleEndTurn(player.getName());
		}catch(DomainException e) {
			endGame();
		}
		
		
		
	}


	@Override
	public void saveDice(String player, int dice) {
		//if(player.equals(getActivePlayer())) {
			service.saveDice( player,  dice);
		//}
	}

	@Override
	public void handleEndTurn(String player) {
		service.setNextTurn(player);
		service.resetDice();
		windows.get(player).setPassive();
		windows.get(getActivePlayer()).setActive();
		for(YahtzeeWindow w : windows.values()) {
			w.reset();
		}



	}

	@Override
	public String getActivePlayer() {
		return service.getActivePlayer().toString();
	}

	@Override
	public void calculateScore(String player, List<Integer> diceSaved, Category category) {
		Collections.sort(diceSaved);
		String result = "";
		for(Integer i : diceSaved) {
			result += i.toString();
		}
		if(category!=null) {
			//Check for bonus
			String match = match(result, Category.YAHTZEE.getPattern());
			boolean bonus = false;
			if(match.length() == 5 && service.yahtzeeAttained(service.getPlayer(player))) {
				bonus = true;
			}
			
			match = match(result, category.getPattern());
			boolean matches = !match.equals("");
			
			int score=0;
			switch(category) {
			case ACES:
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
			case THREE_OF_A_KIND:
			case FOUR_OF_A_KIND:
				if(match.length()>0)
					score = Integer.parseInt(match.substring(0, 1))*match.length();
				break;
			case FULL_HOUSE:
				if(matches || bonus)
					score = 25;
				break;
			case SMALL_STRAIGHT:
				if(matches || bonus)
					score = 30;
				break;
			case LARGE_STRAIGHT:
				if(matches || bonus)
					score = 40;
				break;
			case YAHTZEE:
				if(matches)
					score = 50;
				break;
			case CHANCE:
				for(Integer i: diceSaved) {
					score+= i;
				}
				break;
			default:
				score=0;
				break;
			}
			service.setScore(getActivePlayer(), category, score);
		}
		if(service.isGameDone()) {
			endGame();
			
		}

	}
	
	private void endGame() {
		String winner = service.getWinningPlayer();
		int points = service.getTotalPoints(winner);
		view.openEndWindow(this, points , winner);
	}

	private String match(String text, String pattern) {
		Pattern p =  Pattern.compile(pattern);
		Matcher matcher = p.matcher(text);
		if(matcher.find()) {
			return matcher.group(1);
		}
		else return "";

	}

	@Override
	public void handleYes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNo() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void unSaveDice(String player, int dice) {
		service.unSaveDice(player, dice);
		
	}






}
