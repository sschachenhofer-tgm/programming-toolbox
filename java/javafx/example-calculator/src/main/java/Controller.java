import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    private Model model;

    // Diese Attribute bekommen vom FXMLLoader automatisch die Referenzen auf die entsprechenden GUI-Elemente zugewiesen
    @FXML private TextField input1;
    @FXML private TextField input2;
    @FXML private TextField result;
    @FXML private ComboBox<String> operation;
    @FXML private Label statusBar;

    /**
     * Initialisieren des Controller-Objekts
     *
     * Alles, was mit der Initialisierung der GUI-Elemente zu tun hat, kann nicht hier im Konstruktor gemacht werden!
     * Zu dem Zeitpunkt, zu dem der Konstruktor ausgeführt wird, ist das FXML-Dokument nämlich noch nicht geladen.
     * Die Attribute für die GUI-Elemente haben alle noch den Wert null. Stattdessen kann die Methode initialize()
     * verwendet werden.
     */
    public Controller() {
        // Erstellen des Models
        this.model = new Model();
    }

    /**
     * Initialisieren der GUI-Elemente
     *
     * Diese Methode wird vom automatisch ausgeführt, sobald das FXML-Dokument geladen wurde und den Controller-
     * Attributen die Referenzen auf die GUI-Elemente zugewiesen wurden.
     *
     * Die Annotation @FXML ist hier nicht zwingend notwendig. Sie ermöglicht aber dem FXMLLoader Zugriff auf die
     * Methode, auch, wenn sie nicht public ist.
     */
    @FXML
    protected void initialize() {
        // Die verfügbaren Optionen (die Rechenarten) werden zur ComboBox operation hinzugefügt
        this.operation.getItems().addAll("+", "-", "*", "/");
    }

    /**
     * Durchführen einer Berechnung
     *
     * Diese Methode ist ein Event Handler. Im FXML-Dokument wurde festgelegt, dass sie ausgeführt werden soll, wenn man
     * auf den Button "Berechnen" klickt. JavaFX-Event-Handler können unterschiedliche Signaturen haben: Sie können
     * einen Parameter vom Typ javafx.event.Event haben, können aber auch parameterlos sein. Nachdem hier keine Details
     * zum Event gebraucht werden, wurde der Parameter weggelassen.
     */
    @FXML
    protected void performCalculation() {
        try {
            int n1 = Integer.parseInt(this.input1.getText());
            int n2 = Integer.parseInt(this.input2.getText());
            int result;

            switch (this.operation.getSelectionModel().getSelectedItem()) {
                case "+":  // Addition
                    result = this.model.performAddition(n1, n2);
                    break;
                case "-":  // Perform a division
                    result = this.model.performSubtraction(n1, n2);
                    break;
                case "*":  // Multiplikation
                    result = this.model.performMultiplication(n1, n2);
                    break;
                case "/":  // Division
                    result = this.model.performDivision(n1, n2);
                    break;
                default:
                    result = 0;
            }
            this.result.setText(String.valueOf(result));

        } catch (NumberFormatException nfe) {
            this.statusBar.setText("You can only perform mathematical operations on numbers. Please input numbers");
        } catch (ArithmeticException ae) {
            this.statusBar.setText("There was a problem with your calculation - did you try dividing by zero?");
        }
    }
}
