package view;

import controller.Controller;
import controller.InputController;
import controller.OptionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class EndWindow extends GridPane {
	
	private Label messageLabel;
	private Button buttonNo, buttonYes;
	private OptionController controller;
	
	
	public EndWindow(OptionController controller, int points, String player) {
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		messageLabel = new Label(player + " has won the game with " + points + " points!");
		add(messageLabel, 0, 0);
		setColumnSpan(messageLabel, 2);

		Text nameLabel = new Text("would you like to play again?");
		add(nameLabel, 0, 1);

		buttonYes = new Button("Yes");
		add(buttonYes, 0, 3);
		buttonYes.setOnAction(new YesListener());
		buttonNo = new Button("No");
		add(buttonNo, 1, 3);
		buttonNo.setOnAction(new NoListener());
		
		this.controller = controller;
	}
	
class YesListener implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			controller.handleYes();
		}
	}

	class NoListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			
			controller.handleNo();
		}
	}
	
	
}
