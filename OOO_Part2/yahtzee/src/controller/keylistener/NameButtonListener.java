package controller.keylistener;

import controller.ControllerCommon;
import controller.ControllerException;
import db.PlayerDb;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import view.View;

public class NameButtonListener extends ControllerCommon implements EventHandler<ActionEvent> {

	public NameButtonListener(View view, PlayerDb players) throws ControllerException {
		super(view, players);
	}

	@Override
	public void handle(ActionEvent event) {
		String name = getView().getName();
		this.getDb().addPlayer(name);
		if(getDb().getSize() ==  2) {
			getView().openGameViews();
		}
	}

}
