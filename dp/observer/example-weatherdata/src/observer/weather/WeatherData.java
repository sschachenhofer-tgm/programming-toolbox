package observer.weather;

/**
 * Ein WeatherData-Element enthält wichtige Daten über das Wetter zu einem bestimmten Zeitpunkt. Es erweitert die
 * abstrakte Subject-Klasse und kann damit Observer hinzufügen, die dann über Änderungen des Wetters informiert werden.
 */
public class WeatherData extends Subject {

	private float temperature;
	private float humidity;
	private float pressure;
	
	public WeatherData() {
		this.temperature = 20;
		this.humidity = 50;
		this.pressure = 1;
	}

	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;

		// Die Observer über die Datenänderung benachrichtigen
        super.notifyObservers();
	}
	
	public float getTemperature() {
		return this.temperature;
	}
	
	public float getHumidity() {
		return this.humidity;
	}
	
	public float getPressure() {
		return this.pressure;
	}
}
