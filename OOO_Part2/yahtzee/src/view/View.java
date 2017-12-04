package view;

import java.util.Observer;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public interface View extends Observer{
	
	
	
	public void start();
	public void closeInput();
	public void setController(Controller controller);
	public void openGameWindow(String player, Controller controller);
}
