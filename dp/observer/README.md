[<small>Zurück zu *Design Patterns*</small>](../)  
[<small>Zurück zur Übersicht</small>](../../)

# Observer Pattern

### Problembeschreibung

- Beim Observer Pattern geht es um ein Daten-Objekt, dessen Daten sich **regelmäßig ändern**.
- Andere Objekte, die mit diesen Daten arbeiten (zum Beispiel Bildschirmfenster, die Daten anzeigen), sollten natürlich möglichst **aktuell sein** und immer mit den neuesten Daten arbeiten.
- Natürlich könnten diese Objekte einfach regelmäßig selbst überprüfen, ob sich die Daten geändert haben. Aber dann könnten sie trotzdem nicht sofort, sondern erst nach einiger Zeit auf Änderungen reagieren, und durch die dauernden Abfragen wäre das System unnötig belastet.

Es muss also eine Möglichkeit geben, wie das Daten-Objekt **selbst aktiv wird** und die abhängigen Objekte über Änderungen der Daten informiert! Hier kommt das Observer Pattern ins Spiel.  



### Lösung

- Die Objekte, die die Daten brauchen, können sich **beim Datenobjekt registrieren**.
- Wenn sich beim Datenobjekt etwas ändert, werden die registrierten Objekte darüber **benachrichtigt** und können dann darauf reagieren (die Anzeige aktualisieren usw.)
- Natürlich können sich die Objekte beim Datenobjekt auch wieder **abmelden** und werden dann nicht mehr benachrichtigt.



### Begriffe für die Klassen

- **Subject**: Das ist das "Daten-Objekt". Das Subject hat Daten, die sich immer wieder ändern.
- **Observer**: Observer sind Objekte, die mit Daten vom Subject arbeiten. Sie können sich beim Subject registrieren lassen und werden dann so lange über Änderungen der Daten benachrichtigt, bis sie sich wieder abmelden.  

![UML-Klassendiagramm zum Observer Pattern](classdiagram-push.svg)



### Zwei Varianten: Push und Pull

#### Das Push-Prinzip

Das Klassendiagramm oben zeigt die einfache Variante des Observer Patterns: Das Push-Prinzip.

- Beim Push-Prinzip übergibt das Subject **gleich beim Benachrichtigen** die Daten an die Observer (siehe Signatur von `update()` im Interface `Observer` - hier wird ein `int`-Wert übergeben).

Das hat **Nachteile**:

- Wenn sich das Subject ändert (wenn zum Beispiel ein neuer Wert dazukommt), dann muss im Observer-Interface und in **allen konkreten Observern** die Signatur der `update()`-Methode **geändert** und ein neuer Parameter hinzugefügt werden.
- Bei Subjects, die eine große Menge an Daten enthalten, wird dann die Parameterliste der `update()`-Methode sehr lang.
- Wahrscheinlich brauchen auch nicht alle Observer *alle* Daten - es werden hier also viele Daten unnötig gesendet.

Es ist klar, dass das Push-Prinzip **viele Nachteile** hat und nicht gerade wartungsfreundlich ist. Dafür gibt es das Pull-Prinzip!  



#### Das Pull-Prinzip

Das Pull-Prinzip behebt alle Nachteile, die das Push-Prinzip hat, folgendermaßen:

- Wenn sich die Daten im Subject ändern, werden die Observer zwar sofort **benachrichtigt**, aber es werden ihnen noch **keine Daten übergeben**!
- Die Observer können dann selbstständig die Daten mittels **Getter-Methoden im Subject** auslesen.

Es ergeben sich also folgende **Vorteile** gegenüber dem Push-Prinzip:

- Wenn im Subject ein neuer Wert dazukommt, dann müssen **nur die Observer geändert werden**, die diesen Wert brauchen. Ale anderen Observer rufen den neuen Wert einfach nicht auf.
- Die `update()`-Methode hat **keine Parameter**, und daran ändert sich auch nichts.
- Observer lesen nur die Daten aus, die sie brauchen.

Das aktualisierte **Klassendiagramm** dazu:

![Das UML-Klassendiagramm zum Pull-Prinzip](classdiagram-pull.svg)



### Das Design-Prinzip der losen Kopplung

Das (nach meiner Nummerierung) vierte Design-Prinzip besagt: *Strive for loosely coupled designs between objetcs that interact*. Diese lose Kopplung ist beim Observer-Pattern gut umgesetzt, denn:

- Die grundlegenden Komponenten (Subject und Observer) haben nur **wenig Detailwissen** voneinander.
- Die dahinterliegende Implementierung (die konkreten Subjects und Observer) ist **komplett egal** - jede Klasse kann eines der beiden Interfaces implementieren und damit ein Subject oder Observer werden.  



### Beispiel: Wetterdaten

Anmerkung: Im folgenden Beispiel wurde das Pull-Prinzip verwendet.  

#### Das Interface Subject

Das Subject definiert nur die drei wichtigen, allgemeinen Methoden `registerObserver()`, `deregisterObserver()` und `notifyObservers()`

```java
public interface Subject {

	void registerObserver(Observer o);
	void removeObserver(Observer o);
	void notifyObservers();
}
```



#### Das konkrete Subject: WeatherData

Die Klasse WeatherData verwaltet Wetterdaten: Temperatur, Luftfeuchtigkeit und Luftdruck. Diese Daten werden in Attributen gespeichert. Außerdem gibt es eine `ArrayList`, in die alle Observer gespeichert werden

``````java
private float temperature;
private float humidity;
private float pressure;
private ArrayList<Observer> observers;
``````

Sie hat also Methoden, um die Werte zu setzen und auszulesen

```java
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
```

Außerdem implementiert WeatherData die Methoden `registerObserver()`, `deregisterObserver()` und `notifyObservers()` vom Interface Subject:

```java
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
```



#### Das Interface Observer

Das Observer-Interface definiert nur eine einzige Methode `update()`, um von Änderungen von Daten im Subject benachrichtigt zu werden. Diese Methode hat - wie es beim Pull-Prinzip gehört - keine Parameter

``````java
public interface Observer {
    
	void update();
}
``````



#### Ein konkreter Observer: CurrentConditionsDisplay

CurrentConditionsDisplay soll bei Änderungen der Wetterdaten die Werte der Temperatur und der Luftfeuchtigkeit in die Konsole ausgeben. Der Luftdruck wird hier **nicht gebraucht**. 

Im Konstruktor wird ein WeatherData-Objekt übergeben. Dieses Objekt wird beim Pull-Prinzip gebraucht, weil ja die Daten **ausgelesen werden müssen**. Außerdem trägt sich ein neu erstelltes CurrentConditionsDisplay auch gleich beim WeatherData-Objekt **als Observer ein**.

``````java
public CurrentConditionsDisplay(WeatherData weatherData) {
    this.weatherData = weatherData;
    weatherData.registerObserver(this);
}
``````

Beim Aufruf von `update()` werden **keine Parameter** übergeben. Stattdessen werde die Werte, die benötigt werden (also Temperatur und Luftfeuchtigkeit, aber nicht Luftdruck) **mit Getter-Methoden ausgelesen**.

``````java
public void update() {
    this.temperature = this.weatherData.getTemperature();
    this.humidity = this.weatherData.getHumidity();
    this.display();
}
``````

`display()` gibt dann die Daten in die Konsole aus...

``````java
public void display() {
    System.out.println("Current conditions: " + temperature 
    + "° F degrees and " + humidity + "% humidity");
}
``````