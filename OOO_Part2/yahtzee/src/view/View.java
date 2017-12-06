package view;


import controller.Controller;

public interface View {
	
	
	
	public void start();
	public void closeInput();
	public void setController(Controller controller);
	public YahtzeeWindow openGameWindow(String player, Controller controller, boolean active);
	public String getPlayer();
}
