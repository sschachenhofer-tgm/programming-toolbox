package sprstdverw_push;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Die Subject-Klasse ist eine Abstraktion für eine Klasse, die Daten verwaltet und Observer über Änderungen
 * benachrichtigen kann. Observer können sich an- und wieder abmelden. Angemeldetete Observer werden benachrichtigt,
 * wenn sich Daten ändern.
 *
 * @author Simon Schachenhofer
 * @version 2020-02-26
 */
public abstract class Subject {

	private Collection<Observer> observers;  // Eine Liste mit allen angemeldeten Observern

	public Subject() {
	    this.observers = new ArrayList<>();
    }

    /**
     * Über diese Methode können sich Observer anmelden. Danach werden sie über Änderungen benachrichtigt. Um nicht
     * mehr benachrichtigt zu werden, können sich die Observer auch wieder abmelden.
     *
     * @param   o   Das Observer-Objekt
     */
    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    /**
     * Über diese Methode können sich angemeldete Observer wieder abmelden. Danach werden sie nicht mehr über Änderungen
     * benachrichtigt.
     *
     * @param   o   Das Observer-Objekt
     */
	public void removeObserver(Observer o) {
	    this.observers.remove(o);
    }

    /**
     * Über diese Methode werden alle Observer auf einmal abgemeldet. Danach wird kein Observer mehr über Änderungen
     * benachrichtigt.
     */
	public void removeObservers() {
	    this.observers.clear();
    }

    /**
     * Diese Methode benachrichtigt alle angemeldeten Observer über Änderungen. Dabei müssen die Daten übergeben werden,
     * die an die Observer übergeben werden sollen.
     * @param   arg Die Daten, die an die Observer übergeben werden
     */
	public void notifyObservers(Object arg) {
	    for (Observer o : this.observers) {
	        o.update(arg);
        }
    }

}
