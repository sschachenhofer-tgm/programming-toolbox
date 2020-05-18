package strategy.calculator;

import java.util.ArrayList;
import java.util.List;

public class Multiplication implements Calculation {

    @Override
    public List<Double> processCalculations(List<Double> values, double modifier) {
        List<Double> res = new ArrayList<>();
        for (Double value : values) res.add(value * modifier);
        return res;
    }
}
