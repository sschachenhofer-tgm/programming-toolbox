package observer.weather;

public class StatisticsDisplay implements Observer, DisplayElement {
	private float maxTemp = 0.0f;
	private float minTemp = 200;
	private float tempSum= 0.0f;
	private int numReadings;
	private WeatherData weatherData;

	public StatisticsDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;
		this.weatherData.registerObserver(this);
	}

	public void update() {
		tempSum += this.weatherData.getTemperature();
		numReadings++;

		this.maxTemp = Float.max(this.maxTemp, this.weatherData.getTemperature());
		this.minTemp = Float.min(this.minTemp, this.weatherData.getTemperature());

		this.display();
	}

	public void display() {
	    System.out.println(String.format("Avg/Max/Min temperature: %f/%f/%f",
                (this.tempSum / this.numReadings), this.maxTemp, this.minTemp));
	}
}
