package domain;

import domain.Observer;

public interface Subject {
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
	void notifyObservers(String type, Object args);
}
