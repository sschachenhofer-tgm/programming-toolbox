package strategy.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private List<Double> values;
    private double modifier;
    private Calculation calculation;

    public Calculator() {
        this.values = new ArrayList<>();
    }

    public void addValue(double value) {
        this.values.add(value);
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
    }

    public boolean removeValue(double value) {
        return this.values.remove(value);
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public List<Double> processCalculations() {
        return this.calculation.processCalculations(this.values, this.modifier);
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "values=" + values +
                ", modifier=" + modifier +
                ", calculation=" + calculation +
                '}';
    }
}