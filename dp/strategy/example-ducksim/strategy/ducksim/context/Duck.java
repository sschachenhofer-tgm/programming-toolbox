package strategy.ducksim.context;

import strategy.ducksim.strategy.FlyBehavior;
import strategy.ducksim.strategy.QuackBehavior;

/**
 * Diese Klasse ist die Superklasse von allen Implementierungen von Enten. Sie ist eine abstrakte Klasse, denn:
 * - Es sollen keine Instanzen von "Duck" erstellt werden können, nur von Subklassen (wie "MallardDuck")
 * - In dieser Klasse können aber trotzdem Implementierungen für Methoden vorgenommen werden, die über alle konkreten
 *   Enten-Subklassen gleich bleiben (wie setFlyBehavior, setQuackBehavior etc.). Diese Möglichkeit hätten Interfaces
 *   mit Default-Methoden zwar auch, aber anscheinend ist das Buch schon älter...
 *
 * Durch die Verwendung einer (abstrakten) Superklasse können Klasse, die Instanzen von Enten erzeugen, den Typ von
 * Variable auf "Duck" festlegen, wodurch das Programm flexibler wird. Außerdem kann man so Collections von Duck-
 * Instanzen erzeugen, obwohl die einzelnen Enten unterschiedliche Klassen haben.
 */
public abstract class Duck {
	FlyBehavior flyBehavior;
	QuackBehavior quackBehavior;
 
	public Duck() {
	}

	/**
	 * Des Flugverhalten der Ente (die concrete Strategy) kann zur Laufzeit festgelegt und auch weiterhin beliebig
	 * geändert werden
	 * @param	fb	Eine Implementierung von FlyBehavior, die also das Fliegen implementiert.
	 */
	public void setFlyBehavior (FlyBehavior fb) {
		flyBehavior = fb;
	}

	/**
	 * Auch das Quakverhalten der Ente (die concrete Strategy) kann zur Laufzeit festgelegt und weiterhin beliebig
	 * geändert werden
	 * @param	qb	Eine Implementierung von QuackBehavior, die also das Quaken implementiert.
	 */
	public void setQuackBehavior(QuackBehavior qb) {
		quackBehavior = qb;
	}

	/**
	 * Diese Methode ist für alle konkreten Duck-Subklassen unterschiedlich, weil alle unterschiedlich ausschauen.
	 * Deshalb ist die Methode als abstract deklariert und wird erst in den konkreten Subklassen implementiert.
	 * Damit ist der Teil des Codes, der für alle Klassen unterschiedlich ist, getrennt von dem Teil des Codes, der
	 * gleich bleibt. Das Designprinzip "Identify the parts of your code that vary and separate them from what stays
	 * the same" ist damit erfüllt.
	 */
	abstract void display();

	/**
	 * Das Fliegen der Ente führt nicht die Duck-Subklasse aus, sondern die als Attribut flyBehavior gespeicherte
	 * Implementierung von FlyBehavior. Damit muss jede Art, in der Enten fliegen können, nur einmal implementiert
	 * werden.
	 */
	public void performFly() {
		flyBehavior.fly();
	}

	/**
	 * Das Quaken der Ente führt nicht die Duck-Subklasse aus, sondern die als Attribut quackBehavior gespeicherte
	 * Implementierung von QuackBehavior. Damit muss jede Art, in der Enten quaken können, nur einmal implementiert
	 * werden.
	 */
	public void performQuack() {
		quackBehavior.quack();
	}

	/**
	 * Alle Enten schwimmen gleich. Für diese Methode ist also keine Strategy erforderlich (wie für fly() und quack()),
	 * und auch keine eigene Implementierung für jede einzelne Duck-Subklasse (wie bei display()). Sollte es aber doch
	 * irgendwann einmal eine Ente geben, die nicht schwimmen kann, dann kann sie diese Methode immer noch
	 * überschreiben, oder man stellt swim() doch auf eine Strategy um.
	 */
	public void swim() {
		System.out.println("All ducks float, even decoys!");
	}
}
