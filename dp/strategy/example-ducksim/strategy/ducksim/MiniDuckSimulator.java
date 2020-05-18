package strategy.ducksim;

import strategy.ducksim.context.DecoyDuck;
import strategy.ducksim.context.MallardDuck;
import strategy.ducksim.context.ModelDuck;
import strategy.ducksim.context.RubberDuck;
import strategy.ducksim.strategy.FlyRocketPowered;

public class MiniDuckSimulator {
 
	public static void main(String[] args) {
 
		MallardDuck mallard = new MallardDuck();
		RubberDuck rubberDuckie = new RubberDuck();
		DecoyDuck decoy = new DecoyDuck();
 
		ModelDuck model = new ModelDuck();

		mallard.performQuack();
		rubberDuckie.performQuack();
		decoy.performQuack();
   
		model.performFly();	
		model.setFlyBehavior(new FlyRocketPowered());
		model.performFly();
	}
}
