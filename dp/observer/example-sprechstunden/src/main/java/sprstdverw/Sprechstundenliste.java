package sprstdverw;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Sprechstundenliste verwaltet die Sprechstunden. Sie erweitert die Klasse Subject und kann daher Observer über
 * Änderungen benachrichtigen.
 *
 * @author Simon Schachenhofer
 * @version 2020-02-26
 */
public class Sprechstundenliste extends Subject {

	private Map<String, Sprechstunde> sprechstunden;

	public Sprechstundenliste() {
	    super();  // Superklasse initialisieren
	    this.sprechstunden = new HashMap<>();  // Leere Map von Sprechstunden anlegen
    }

	public java.util.Collection<Sprechstunde> getAllSprechstunden() {
		return this.sprechstunden.values();
	}

	public Sprechstunde getSprechstunde(String lehrer) {
		return this.sprechstunden.get(lehrer);
	}

	public void addSprechstunde(String lehrer, String raum, DayOfWeek wochentag, LocalTime beginn, LocalTime ende) {
	    this.sprechstunden.put(lehrer, new Sprechstunde(lehrer, raum, wochentag, beginn, ende));
	    this.notifyObservers();  // Bei einer Änderung sollen die Observer benachrichtigt werden
    }

    public void deleteSprechstunde(String lehrer) {
	    this.sprechstunden.remove(lehrer);
        this.notifyObservers();  // Bei einer Änderung sollen die Observer benachrichtigt werden
    }
}
