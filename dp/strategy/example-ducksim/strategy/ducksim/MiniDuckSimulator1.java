package strategy.ducksim;

import strategy.ducksim.context.Duck;
import strategy.ducksim.context.MallardDuck;
import strategy.ducksim.context.ModelDuck;
import strategy.ducksim.strategy.FlyRocketPowered;

public class MiniDuckSimulator1 {
 
	public static void main(String[] args) {
 
		Duck mallard = new MallardDuck();
		mallard.performQuack();
		mallard.performFly();
   
		Duck model = new ModelDuck();
		model.performFly();
		model.setFlyBehavior(new FlyRocketPowered());
		model.performFly();

	}
}
