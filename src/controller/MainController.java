package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.Observer;
import domain.model.Category;
import domain.model.Player;
import domain.model.Service;
import javafx.scene.control.Button;
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

	public void addPlayerObserver(Player player, Observer o) {
		service.addPlayerObserver(player, o);
	}

	@Override
	public void handleRoll(String playerName) {
		if(firstRoll) {
			firstRoll =false;
			for(Player player: service.getPlayers()) {
				for(YahtzeeWindow w : windows.values()) {
					addPlayerObserver(player, w);
				}
			}
		}
		view.closeInput();
		Player p = service.getPlayer(playerName);
		service.throwDice(p);

	}

	@Override
	public void saveDice(String player, int dice) {
		if(player.equals(getActivePlayer())) {
			service.saveDice(player, dice);
		}
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
			String match = match(result, category.getPattern());
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
				if(matches)
					score = 25;
				break;
			case SMALL_STRAIGHT:
				if(matches)
					score = 30;
				break;
			case LARGE_STRAIGHT:
				if(matches)
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

	}

	private String match(String text, String pattern) {
		Pattern p =  Pattern.compile(pattern);
		Matcher matcher = p.matcher(text);
		if(matcher.find()) {
			return matcher.group(1);
		}
		else return "";

	}






}
