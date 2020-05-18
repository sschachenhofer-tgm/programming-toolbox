package strategy.ducksim.strategy;

/**
 * Dieses Interface legt eine Methodensignatur fest, über die alle konkreten Implementierungen für Quakverhalten
 * gleich aufgerufen werden. Außerdem wird ein Interface verwendet, weil damit in den Duck-Subklassen die Art von
 * QuackBehavior flexibel und auch noch zur Laufzeit festgelegt werden kann.
 */
public interface QuackBehavior {

	/**
	 * Alle Implementierungen von QuackBehavior (also alle Klassen, die ein Quakverhalten für Enten darstellen sollen),
	 * müssen die Methode quack() implementieren. Damit haben alle QuackBehavior-Implementierungen die selbe Signatur
	 * für die Quack-Methode.
	 */
	public void quack();
}
