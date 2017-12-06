package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.Controller;
import domain.model.Category;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class YahtzeeWindow extends BorderPane implements Observer{

	private String player;
	private Label playerPlaying, title;
	private Button buttonRoll, buttonOk;
	private Controller controller;
	private ArrayList<Button> diceThrown, diceSaved;
	private ComboBox<Category> cbxCategory;
	private HBox titlePane;
	private GridPane gamePane;
	private SwingNode jTableNode;
	private JTable table;
	private ArrayList<Object> categoryNames = new ArrayList<Object>();
	private Object[] columns = {"", "Game 1"};
	private int gameNumber = 1;


	public YahtzeeWindow(String player, Controller controller, boolean active) {
		//INIT
		this.controller = controller;
		this.player = player;
		this.diceThrown = new ArrayList<Button>();
		this.diceSaved = new ArrayList<Button>();

		for(Category c: Category.values()) {
			categoryNames.add(c);
			if(c.equals(Category.SIXES)) {
				categoryNames.add("total:");
				categoryNames.add("bonus:");
				categoryNames.add("total:");
			}
			if(c.equals(Category.CHANCE)) {
				categoryNames.add("lower section total");
				categoryNames.add("upper section total");
				categoryNames.add("grand total");
			}
		}

		setTop(addTitlePane());
		setCenter(addGamePane());
		if(!active) {
			setPassive();
		}

		
		




	}
	
	private Object[][] getTableRows() {
		Object[][] result = new Object[categoryNames.size()][];
		for(int i=0; i<categoryNames.size(); i++) {
			ArrayList<Object> row = new ArrayList<Object>();
			row.add(categoryNames.get(i).toString().toLowerCase() + " :");
			for(int j=1; j<columns.length; j++);{
				row.add(0);
			}
			result[i] = row.toArray();
		}
		return result;
	}

	private Node addGamePane() {
		gamePane = new GridPane();
		gamePane.setHgap(10);
		gamePane.setVgap(10);
		gamePane.setPadding(new Insets(10, 10, 10, 10));
		gamePane.setStyle("-fx-background-color: #91AA9D;");

		//Roll Dice button
		buttonRoll = new Button("Roll Dice");
		buttonRoll.setOnAction(new RollButtonListener());
		gamePane.add(buttonRoll, 0,0, 10, 1);

		//Dice buttons
		makeDiceButtons();

		//Categories
		this.cbxCategory = new ComboBox<>();
		cbxCategory.getItems().setAll(Category.values());
		gamePane.add(cbxCategory, 0, 6, 7, 1);

		//End turn button
		buttonOk = new Button("End Turn");
		buttonOk.setOnAction(new EndTurnListener());
		gamePane.add(buttonOk, 0, 8, 7, 1);



		Object[][] rowData = getTableRows();

		table = new JTable(rowData, columns);
		JScrollPane jsp = new JScrollPane(table);
		SwingNode jTableNode = new SwingNode();
		//jsp.setMaximumSize(new Dimension(20000, 40));
		jTableNode.setContent(jsp);
		gamePane.add(jTableNode, 12, 0, 10, 30);
		
		return gamePane;

	}


	private Node addTitlePane() {
		titlePane = new HBox();
		titlePane.setPadding(new Insets(15, 12, 15, 12));
		titlePane.setSpacing(10);
		titlePane.setStyle("-fx-background-color: #193441;");

		//Title + x playing
		title = new Label("Yahtzee");
		playerPlaying = new Label(controller.getActivePlayer() + " playing");
		title.setTextFill(Color.web("#FCFFF5"));
		playerPlaying.setTextFill(Color.web("#FCFFF5"));


		final Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		spacer.setMinSize(10, 1);
		titlePane.getChildren().addAll(title, spacer, playerPlaying);
		return titlePane;
	}

	private void removeButton(Button button) {
		gamePane.getChildren().remove(button);

	}

	public void setPassive() {
		gamePane.getChildren().removeAll(buttonRoll, cbxCategory, buttonOk);
	}

	public void setActive() {
		gamePane.add(buttonRoll, 0,0, 10, 1);
		gamePane.add(cbxCategory, 0, 6, 7, 1);
		gamePane.add(buttonOk, 0, 8, 7, 1);

	}

	@Override
	public void update(String type, Object args) {

		switch(type) {
		case "throwDice":
			if(args instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<Integer> results = (List<Integer>) args;
			
			if(results.size()>0) {
				for(int i = 0 ;i < diceThrown.size();  i++) {
					Button b = diceThrown.get(i);
					b.setText(""+results.get(i));

				}
				//Integer at index diceThrown.size() is the amount of turns the player has left.
				if(results.get(diceThrown.size()) == 0) {
					for(int i=diceThrown.size()-1; i>=0; i--) {
						Button button = diceThrown.get(i);
						moveButton(button);
						buttonRoll.setText("No more turns!");
					}
				}
			}
			}
			break;
		case "saveDice":
			int i = (Integer) args;
			Button button = diceThrown.get(i);
			moveButton(button);
			break;
		case "noMoreTurns" :
			//TODO
			break;
		case "updateScore" :
			if(controller.getActivePlayer().equals(player)) {
				Object[] result = (Object[]) args;
				Category c = (Category) result[0];
				int score = (int) result[1];
				int index = categoryNames.indexOf(c);

				setScore(score, index);
				
				//Compute Totals
				int total = 0;
				int firstTotalIndex = categoryNames.indexOf(Category.SIXES)+1;
				int secondTotalIndex = categoryNames.size()-3;
				//lower section totals
				for(int j = 0; j<firstTotalIndex; j++) {
					total += getScore(j);	
				}
				setScore(total, firstTotalIndex);
				if(total >= 63) {
					setScore(35, firstTotalIndex+1);
					total += 35;
				}
				setScore(total, firstTotalIndex + 2);
				setScore(total, secondTotalIndex);
				//upper section totals
				total = 0;
				for(int j = firstTotalIndex+3; j <secondTotalIndex; j++) {
					total += getScore(j);
				}
				setScore(total, secondTotalIndex+1);
				total += getScore(secondTotalIndex);
				setScore(total, secondTotalIndex + 2);
			}
			break;
		default:
			break;
		}

	}

	public void reset() {
		for(int i = 0; i<diceThrown.size(); i++) {
			Button b = diceThrown.get(i);
			removeButton(b);
		}
		for(int i=0; i<diceSaved.size(); i++) {
			Button b = diceSaved.get(i);
			removeButton(b);
		}
		diceThrown.clear();
		diceSaved.clear();
		makeDiceButtons();
		setPlayerPlaying();
		buttonRoll.setText("Roll Dice");
	}
	
	private void setScore(Object value, int rowIndex) {
		table.getModel().setValueAt(value, rowIndex, gameNumber);
	}
	
	private int getScore(int rowIndex) {
		return Integer.parseInt(table.getModel().getValueAt(rowIndex, gameNumber).toString());
	}

	private void setPlayerPlaying() {
		playerPlaying.setText(controller.getActivePlayer() + " playing");
	}

	private void moveButton(Button button) {
		diceThrown.remove(button);
		diceSaved.add(button);
		removeButton(button);
		gamePane.add(button, diceSaved.size()+1, 10);
	}

	private void makeDiceButtons() {
		for(int i = 0; i < 5; i++) {
			Button b = new Button();
			diceThrown.add(b);
			b.setPrefSize(30, 30);
			gamePane.add(b, i, 3);
			b.setOnAction(new DiceButtonListener(i, b));
		}
	}


	class DiceButtonListener implements EventHandler<ActionEvent> {
		private int dice;
		private Button button;

		public DiceButtonListener(int dice, Button button) {
			this.dice = dice;
			this.button = button;
		}

		@Override
		public void handle(ActionEvent event) {
			//Check if dice have been thrown and if player is the current active player.
			if(controller.getActivePlayer().equals(player) && !button.getText().equals("")) {
				//Save dice & remove from available dice
				button.setOnAction(null);
				controller.saveDice(player, dice);



			}
		}

	}

	class RollButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			controller.handleRoll(player);

		}

	}

	class EndTurnListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			for(int i=diceThrown.size()-1; i>=0; i--) {
				Button button = diceThrown.get(i);
				moveButton(button);
			}
			List<Integer> result = new ArrayList<Integer>();
			for(Button b: diceSaved) {
				result.add(Integer.parseInt(b.getText()));
			}
			controller.calculateScore(player, result, cbxCategory.getValue());
			controller.handleEndTurn(player );


		}

	}



}
