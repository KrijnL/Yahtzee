package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class YahtzeeWindow extends GridPane implements Observer {

	private String player;
	private Label playerName, title, diceField, savedDiceField;
	private Button buttonRoll;
	private Controller controller;


	public YahtzeeWindow(String player, Controller controller) {
		this.controller = controller;
		this.player = player;
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		title = new Label("Yahtzee");
		add(title, 0, 0);

		playerName = new Label(player + " playing");
		add(playerName, 1, 1);
		setColumnSpan(playerName, 2);
		
		savedDiceField = new Label("");
		add(savedDiceField, 1, 3);

		buttonRoll = new Button("Roll Dice");
		add(buttonRoll, 1,2);
		buttonRoll.setOnAction(new RollButtonListener());

		diceField = new Label("");
		add(diceField, 1, 4);

	}

	@Override
	public void update(Observable o, Object arg) {
		String str = "";
		if(arg instanceof List<?>) {
			List<Integer> results = (List<Integer>) arg;
			if(results.size()>0) {
				for(int i = 0 ;i < results.size();  i++) {
					str += "Dice " + i + ": " + results.get(i) + "\n";
				}
			}else {
				str = "sorry, it's not your turn";
			}
		}
		diceField.setText(str);

	}

	class RollButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			controller.handleRoll(player);

		}

	}



}
