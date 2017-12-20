package view;


import controller.Controller;
import controller.OptionController;

public interface View {
	
	
	
	public void start();
	public void closeInput();
	public void setController(Controller controller);
	public YahtzeeWindow openGameWindow(String player, Controller controller, boolean active);
	public String getPlayer();
	public void openEndWindow(OptionController controller, int points, String winner);
}
