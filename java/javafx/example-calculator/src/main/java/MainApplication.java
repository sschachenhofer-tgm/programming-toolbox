import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Diese Klasse ist die Anwendungsklasse der JavaFX-Anwendung. Hier wird aus dem FXML-Code das GUI-Fenster erstellt und
 * der JavaFX-Thread gestartet.
 */
public class MainApplication extends Application {

    /**
     * In dieser Methode könnte man Code zur Initialisierung der GUI/Anwendun ausführen. init() wird immer ausgeführt,
     * bevor die Anwendung mit start() gestartet wird.
     */
    @Override
    public void init() {
        // Code...
    }

    /**
     * Die Methode start() muss in jeder Anwendungsklasse auf jeden Fall überschrieben werden (in der Klasse Application
     * ist sie abstract). Hier wird das FXML-Dokument geladen, die Stage (also das GUI-Fenster erstellt) und die Scene
     * (eine Ansicht der GUI) erstellt und dann das Fenster eingeblendet).
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**
     * In diese Methode könnte man Code schreiben, der ausgeführt werden soll, bevor die Anwendung beendet wird.
     */
    @Override
    public void stop() {
        // Code...
    }

    public static void main(String[] args) {
        // Mit launch() wird die JavaFX-Laufzeitumgebung und dann in weiterer Folge auch die Anwendung gestartet
        launch(args);
    }
}
