package observer.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstrakte Klasse für ein Subject des Observer-Patterns - also ein Objekt, über dessen Änderungen die Observer
 * benachrichtigt werden. Diese abstrakte Klasse implementiert nur drei allgemeine Methoden, die für alle Subjects
 * allgemein gelten.
 */
public abstract class Subject {

    private List<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

	/**
	 * Fügt einen neuen Observer zur Liste an Observern hinzu. Dieser Observer bekommt ab dann so lange
	 * Benachrichtigungen über Änderungen des Subjects, bis er sich mit removeObserver(Observer) wieder abmeldet.
	 * @param	o	Der neue Observer, der hinzugefügt werden soll.
	 */
	public void registerObserver(Observer o) {
        this.observers.add(o);
    }

	/**
	 * Entfernt einen Observer wieder aus der Liste an Observern. Er bekommt dann keine Benachrichtigungen mehr.
	 * @param	o	Der Observer, der aus der Liste wieder abgemeldet werden soll.
	 */
	public void deregisterObserver(Observer o) {
	    this.observers.remove(o);
    }

	/**
	 * Benachrichtigt alle angemeldeten Observer über Änderungen am Subject.
	 */
	public void notifyObservers() {
	    for (Observer o : this.observers) {
	        o.update();
        }
    }
}
