package domain;

import java.util.List;
import view.Observer;

public interface Subject {
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
	void notifyObservers(String type, Object args);
}
