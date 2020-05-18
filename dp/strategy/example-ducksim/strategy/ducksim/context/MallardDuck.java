package strategy.ducksim.context;

import strategy.ducksim.strategy.FlyWithWings;
import strategy.ducksim.strategy.Quack;

public class MallardDuck extends Duck {
 
	public MallardDuck() {
		quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
	}
 
	public void display() {
		System.out.println("I'm a real Mallard duck");
	}
}
