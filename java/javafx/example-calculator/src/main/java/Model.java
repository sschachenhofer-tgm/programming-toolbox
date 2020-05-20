import java.util.ArrayList;
import java.util.List;

/**
 * Eine einfache Model-Klasse. Wie die Model-Klasse genau aufgebaut ist, hängt mit JavaFX nicht zusammen und kann für
 * jede Anwendung frei festgelegt werden. Deswegen werden die einzelnen Methoden hier nicht näher erklärt.
 */
public class Model {

    private List<String> calculations;

    public Model() {
        this.calculations = new ArrayList<>();
    }

    public int performAddition(int n1, int n2) {
        int result = n1 + n2;
        this.calculations.add(String.format("[Addition]\t%d + %d = %d", n1, n2, result));
        return result;
    }

    public int performSubtraction(int n1, int n2) {
        int result = n1 - n2;
        this.calculations.add(String.format("[Subtraction]\t%d - %d = %d", n1, n2, result));
        return result;
    }

    public int performMultiplication(int n1, int n2) {
        int result = n1 * n2;
        this.calculations.add(String.format("[Multiplication]\t%d * %d = %d", n1, n2, result));
        return result;
    }

    public int performDivision(int n1, int n2) throws ArithmeticException {
        int result = n1 / n2;
        this.calculations.add(String.format("[Division]\t%d / %d = %d", n1, n2, result));
        return result;
    }

    public List<String> getCalculationHistory() {
        return this.calculations;
    }
}
