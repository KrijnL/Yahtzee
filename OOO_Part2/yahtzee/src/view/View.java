package view;

import java.util.Observer;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public interface View extends Observer{
	
	
	public void addEventListener(EventHandler<ActionEvent> listener);
	
	public void start();

	void setController(Controller controller);
	public String getName();
	public void openGameViews();
}
