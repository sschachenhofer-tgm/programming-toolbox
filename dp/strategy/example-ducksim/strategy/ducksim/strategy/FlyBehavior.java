package strategy.ducksim.strategy;

/**
 * Dieses Interface legt eine Methodensignatur fest, über die alle konkreten Implementierungen für Flugverhalten
 * gleich aufgerufen werden. Außerdem wird ein Interface verwendet, weil damit in den Duck-Subklassen die Art von
 * FlyBehavior flexibel und auch noch zur Laufzeit festgelegt werden kann.
 */
public interface FlyBehavior {

	/**
	 * Alle Implementierungen von FlyBehavior (also alle Klassen, die ein Flugverhalten für Enten darstellen sollen),
	 * müssen die Methode fly() implementieren. Damit haben alle FlyBehavior-Implementierungen die selbe Signatur für
	 * die Flug-Methode.
	 */
	public void fly();
}
