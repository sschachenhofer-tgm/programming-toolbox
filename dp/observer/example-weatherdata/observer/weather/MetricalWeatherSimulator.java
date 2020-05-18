package observer.weather;

/**
 * Diese Klasse simuliert Veränderungen des Wetters. Jeweils im Abstand von fünf Sekunden werden neue
 * Temperatur-, Luftdruck- und Luftfeuchtigkeitswerte berechnet und im WeatherData-Objekt gesetzt, das dann die
 * registrierten Observer (in diesem Fall nur ein CurrentConditionsJavaFXDisplay) benachrichtigt.
 *
 * Im Gegensatz zu den anderen beiden WeatherStation-Klassen werden hier die in Österreich gebräuchlichen Einheiten
 * verwendet: Grad Celsius für Temperatur und bar für den Luftdruck.
 *
 * @author Simon Schachenhofer
 * @version 2019-03-09
 */
public class MetricalWeatherSimulator extends Thread {

    private WeatherData weatherData;

    /**
     * Erstellt einen neuen MetricalWeatherSimulator.
     *
     * @param   wd  Das WeatherData-Objekt, dessen Daten verändert werden sollen.
     * @since 2019-03-11
     */
    public MetricalWeatherSimulator(WeatherData wd) {
        this.weatherData = wd;
    }

    public void run() {
        //Im 5-Sekunden-Abstand neue Werte generieren
        while (true) {
            //Zufällige Temperatur in Grad Celsius
            float randTemp = (float) ((Math.random() * 50) - 10);  // -10 bis 40 °C

            //Zufälliger Luftdruck in bar
            float randPressure = (float) ((Math.random() * 0.1) + 1);  // 0,9 bis 1,1 bar

            //Zufällige Luftfeuchtigkeit in %
            float randHumidity = (float) ((Math.random() * 80) + 20);  // 20 bis 100 %

            this.weatherData.setMeasurements(randTemp, randHumidity, randPressure);

            //5 Sekunden warten
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }


}
