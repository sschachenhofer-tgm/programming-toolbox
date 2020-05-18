package observer.weather;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Der Controller für die JavaFX-GUI zum Anzeigen von aktuellen Wetterdaten.
 *
 * @author Simon Schachenhofer
 */
public class CurrentConditionsJavaFXDisplayController {

    //JavaFX Attribute
    @FXML private Text temperatureText;
    @FXML private Text pressureText;
    @FXML private Text humidityText;
    @FXML private Text statusText;

    //Es wird ein Default-Konstruktor verwendet. Objekte dieser Klasse benötigen keine Referenz auf andere Objekte.

    /**
     * Aktualisiert den angezeigten Wert der Temperatur. Dabei wird der Wert auf eine Nachkommastelle gerundet.
     *
     * @param   temperature Die neue Temperatur
     * @since 2019-03-11
     */
    public void setTemperature(float temperature) {
        Platform.runLater(() -> this.temperatureText.setText((Math.round(temperature * 10f) / 10f) + " \u00B0 C"));
    }

    /**
     * Aktualisiert den angezeigten Wert des Luftdrucks. Dabei wird der Wert auf drei Nachkommastellen gerundet.
     *
     * @param   pressure    Der neue Luftdruck
     * @since 2019-03-11
     */
    public void setPressure(float pressure) {
        Platform.runLater(() -> this.pressureText.setText((Math.round(pressure * 1000f) / 1000f) + " bar"));
    }

    /**
     * Aktualisiert den angezeigten Wert der Luftfeuchtigkeit. Dabei wird der Wert auf eine Nachkommastelle gerundet.
     *
     * @param   humidity    Die neue Luftfeuchtigkeit
     * @since 2019-03-11
     */
    public void setHumidity(float humidity) {
        Platform.runLater(() -> this.humidityText.setText((Math.round(humidity * 10f) / 10f) + " %"));
    }

    /**
     * Aktualisiert den angezeigten Status auf den aktuellen Zeitpunkt. Der Status zeigt immer den Zeitpunkt an, zu dem
     * zuletzt die Wetterdaten aktualisiert wurden.
     *
     * @since 2019-03-11
     */
    public void updateStatus() {
        Platform.runLater(() -> this.statusText.setText("Stand: " +
                new SimpleDateFormat("dd. MM yyyy, HH:mm:ss").format(new Date())));
    }
}
