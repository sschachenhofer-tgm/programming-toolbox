package strategy.ducksim.strategy;

import strategy.ducksim.strategy.QuackBehavior;

public class FakeQuack implements QuackBehavior {
	public void quack() {
		System.out.println("Qwak");
	}
}
