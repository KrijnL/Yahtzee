package controller;

import db.PlayerDb;
import view.View;

public abstract class ControllerCommon {

	private View view;
	private PlayerDb players;
	
	public ControllerCommon(View view) throws ControllerException{
		setView(view);
	}
	
	public ControllerCommon(View view, PlayerDb players) {
		this(view);
		setDb(players);
	}
	

	/*protected Converter getModel() {
		return model;
	}


	private void setModel(Converter model) throws ControllerException {
		if(model == null){
			throw new ControllerException("Invalid Model");
		}
		this.model = model;
	}*/
	
	protected PlayerDb getDb() {
		return players;
	}
	
	private void setDb(PlayerDb players) {
		this.players = players;
	}

	protected View getView() {
		return view;
	}


	private void setView(View view) throws ControllerException{
		if(view == null){
			throw new ControllerException("Invalid View");
		}
		this.view = view;
	}

}
