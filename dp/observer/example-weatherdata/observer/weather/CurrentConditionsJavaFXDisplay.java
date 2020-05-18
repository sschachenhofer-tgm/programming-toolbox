package observer.weather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Eine JavaFX-Application-Klasse, die eine GUI zum Anzeigen der aktuellen Wetterdaten implementiert.
 *
 * @author Simon Schachenhofer
 * @version 2019-03-11
 */
public class CurrentConditionsJavaFXDisplay extends Application implements Observer {

    //Datenattribute
    private WeatherData weatherData;
    private float temperature;
    private float pressure;
    private float humidity;

    //Referenz zum Controller, um die Textfelder updaten zu können
    private CurrentConditionsJavaFXDisplayController controller;

    //Referenz auf den Thread des MetricalWeatherSimulators, um den Thread ggf. beenden zu können
    private MetricalWeatherSimulator simulator;

    public CurrentConditionsJavaFXDisplay() {
        this.weatherData = new WeatherData();
        this.weatherData.registerObserver(this);
    }

    public static void main(String[] args) {
        Application.launch(CurrentConditionsJavaFXDisplay.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/current_conditions.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Current weather");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.simulator = new MetricalWeatherSimulator(this.weatherData);
        this.simulator.start();
    }

    @Override
    public void stop() {
        this.simulator.interrupt();
    }

    @Override
    public void update() {
        this.temperature = weatherData.getTemperature();
        this.pressure = weatherData.getPressure();
        this.humidity = weatherData.getHumidity();

        this.display();
    }

    private void display() {
        this.controller.setTemperature(this.temperature);
        this.controller.setPressure(this.pressure);
        this.controller.setHumidity(this.humidity);
        this.controller.updateStatus();
    }
}
