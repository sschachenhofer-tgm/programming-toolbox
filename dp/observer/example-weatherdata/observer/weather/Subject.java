package observer.weather;

/**
 * Interface für ein Subject des Observer-Patterns - also ein Objekt, über dessen Änderungen die Observer benachrichtigt
 * werden. Dieses Interface definiert nur drei allgemeine Methoden, die für alle Subjects allgemein gelten.
 */
public interface Subject {

	/**
	 * Fügt einen neuen Observer zur Liste an Observern hinzu. Dieser Observer bekommt ab dann so lange
	 * Benachrichtigungen über Änderungen des Subjects, bis er sich mit removeObserver(Observer) wieder abmeldet.
	 * @param	o	Der neue Observer, der hinzugefügt werden soll.
	 */
	void registerObserver(Observer o);

	/**
	 * Entfernt einen Observer wieder aus der Liste an Observern. Er bekommt dann keine Benachrichtigungen mehr.
	 * @param	o	Der Observer, der aus der Liste wieder abgemeldet werden soll.
	 */
	void removeObserver(Observer o);

	/**
	 * Benachrichtigt alle angemeldeten Observer über Änderungen am Subject.
	 */
	void notifyObservers();
}
