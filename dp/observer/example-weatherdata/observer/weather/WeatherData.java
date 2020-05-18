package observer.weather;

import java.util.*;

/**
 * Ein WeatherData-Element enthält wichtige Daten über das Wetter zu einem bestimmten Zeitpunkt. Es implementiert das
 * Interface Subject, damit kann das WeatherData-Objekt Observer hinzufügen, die dann über Änderungen des Wetters
 * informiert werden.
 */
public class WeatherData implements Subject {
	private ArrayList<Observer> observers;
	private float temperature;
	private float humidity;
	private float pressure;
	
	public WeatherData() {
		this.observers = new ArrayList<>();
	}
	
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}
	
	public void removeObserver(Observer o) {
		this.observers.remove(o);
	}
	
	public void notifyObservers() {
		for (Observer o : this.observers) {
			o.update();
		}
	}
	
	private void measurementsChanged() {
		notifyObservers();
	}
	
	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}
	
	public float getTemperature() {
		return temperature;
	}
	
	public float getHumidity() {
		return humidity;
	}
	
	public float getPressure() {
		return pressure;
	}
}
