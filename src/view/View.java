package view;


import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public interface View {
	
	
	
	public void start();
	public void closeInput();
	public void setController(Controller controller);
	public YahtzeeWindow openGameWindow(String player, Controller controller, boolean active);
	public String getPlayer();
}
