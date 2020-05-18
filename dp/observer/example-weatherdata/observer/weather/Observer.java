package observer.weather;

/**
 * Interface für einen Observer des Observer-Patterns - also ein Objekt, das von Änderungen am Subject benachrichtigt
 * wird. Dieses Interface definiert nur eine allgemeine Methode, die für alle Observer gilt.
 *
 * Das verwendete Prinzip ist hier das Pull-Prinzip. Das bedeutet, dass das Subject mit der Benachrichtigung
 * keine Daten übergibt. Stattdessen kann der Observer die Daten über die Getter-Methoden abrufen.
 * Die andere Möglichkeit wäre das Pull-Prinzip, wobei der Aufrufer nicht nur benachrichtigt wird , sondern auch die
 * Daten gleich übergeben bekommt.
 * Das Pull-Prinzip wird in der Praxis häufiger verwendet.
 */
public interface Observer {

	/**
	 * Informiert den Observer über eine Änderung am Subject. Es werden noch keine Daten übergeben, die kann sich der
	 * Observer dann mit den entsprechenden Getter-Methoden holen.
	 */
	void update();
}
