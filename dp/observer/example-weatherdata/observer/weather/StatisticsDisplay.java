package observer.weather;

import java.util.*;

public class StatisticsDisplay implements Observer, DisplayElement {
	private float maxTemp = 0.0f;
	private float minTemp = 200;
	private float tempSum= 0.0f;
	private int numReadings;
	private WeatherData weatherData;

	public StatisticsDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	public void update() {
		tempSum += this.weatherData.getTemperature();
		numReadings++;

		if (this.weatherData.getTemperature() > maxTemp) {
			maxTemp = this.weatherData.getTemperature();
		}
 
		if (this.weatherData.getTemperature() < minTemp) {
			minTemp = this.weatherData.getTemperature();
		}

		display();
	}

	public void display() {
		System.out.println("Avg/Max/Min temperature = " + (tempSum / numReadings)
			+ "/" + maxTemp + "/" + minTemp);
	}
}
