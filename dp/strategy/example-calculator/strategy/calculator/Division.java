package strategy.calculator;

import java.util.ArrayList;
import java.util.List;

public class Division implements Calculation {

    @Override
    public List<Double> processCalculations(List<Double> values, double modifier) throws ArithmeticException {
        if (modifier == 0) throw new ArithmeticException("Division by 0");

        List<Double> res = new ArrayList<>();
        for (Double value : values) res.add(value / modifier);
        return res;
    }
}
