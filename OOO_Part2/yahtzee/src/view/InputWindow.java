package view;



import controller.InputController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class InputWindow extends GridPane {

	private TextField nameField;
	private Label messageLabel;
	private Button buttonOk, buttonCancel;
	private InputController controller;
	

	public InputWindow(InputController controller) {
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
		buttonOk.setOnAction(new OkListener());
		
		this.controller = controller;
	}


	class CancelListener implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent event) {
			controller.handleCancel();
		}
	}

	class OkListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			String text = nameField.getText();
			controller.handleOk(text);
			nameField.setText("");
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