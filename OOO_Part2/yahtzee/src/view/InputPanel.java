package view;


import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class InputPanel extends GridPane {

	private TextField nameField;
	private Label messageLabel;
	private Button buttonOk, buttonCancel;

	public InputPanel() {
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));

		messageLabel = new Label("What's your name?");
		add(messageLabel, 0, 0);
		setColumnSpan(messageLabel, 2);

		Text nameLabel = new Text("Name: ");
		add(nameLabel, 0, 1);
		nameField = new TextField();
		add(nameField, 1, 1);

		buttonCancel = new Button("Cancel");
		add(buttonCancel, 0, 3);
		buttonCancel.setOnAction(new CancelListener());
		buttonOk = new Button("Save");
		add(buttonOk, 1, 3);



	}

	class CancelListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			System.exit(0);
		}
	}

	class OkListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String text = nameField.getText();
		}
	}

	public String getText() {
		return nameField.getText().trim();
	}

	public void addEventListener(EventHandler<ActionEvent> isHandler){
		buttonOk.setOnAction(isHandler);
	}

	/*@Override
	public void update(Observable o, Object arg) {

		String message = "You entered " + nameField.getText() + ". Enter another name";
		nameField.setText("");
		messageLabel.setText(message);

	}*/

}