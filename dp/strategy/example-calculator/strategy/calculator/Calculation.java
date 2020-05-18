package strategy.calculator;

import java.util.List;

public interface Calculation {
    public List<Double> processCalculations(List<Double> values, double modifier);
}
