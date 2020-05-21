package observer.weather;

public class CurrentConditionsDisplay implements Observer, DisplayElement {

	private float temperature;
	private float humidity;
	private WeatherData weatherData;
	
	public CurrentConditionsDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;
		this.weatherData.registerObserver(this);
	}

    /**
     * Wenn sich die Daten des WeatherData-Objekts ändern, wird diese Methode aufgerufen, die dann auf die Änderung
     * reagiert.
     */
	public void update() {
		this.temperature = this.weatherData.getTemperature();
		this.humidity = this.weatherData.getHumidity();
		this.display();
	}
	
	public void display() {
		System.out.println(String.format("Current conditions: %f ° C and %f %% humidity", this.temperature, this.humidity));
	}
}
