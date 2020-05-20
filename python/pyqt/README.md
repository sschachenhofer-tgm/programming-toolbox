[<small>Zurück zur Übersicht</small>](../../README.md)  
[<small>Zurück zu *Python*</small>](../)

# Python: GUIs erstellen mit PyQt

PyQt ist eine Python-Library, mit der sich das GUI-Framework Qt (eigentlich ein C++-Framework) aus Python nutzen lässt. Dieses Dokument erklärt die wichtigsten Punkte von PyQt anhand einer MVC-Anwendung. Der komplette Code ist [hier](example-currency-converter/) verfügbar.



## Installation

Ganz einfach über `pip`:

``````
pip install PyQt5
``````



## Die Python-Module

### Die View

Bei PyQt wird die View-Klasse aus einem Layout-File generiert. Das Layout-File erstellt man am besten im Designer. Der Designer wird im Terminal mit folgendem Befehl gestartet:

``````
designer
``````

Es öffnet sich ein kleines Fenster - hier wählt man normalerweise *Main Window* aus:

![image-20191217135240671](D:\Dokumente\_Schule\SEW\img\1.png)

Im Designer kann man sich dann sein Layout zusammenbasteln, wie man es haben möchte. Für einfache Layouts bieten sich vor allem die Layouts *Vertical Layout* und *Horizontal Layout* an.

Rechts im *Object Inspector* sollte man die einzelnen Elemente der GUI sinnvoll benennen – die Namen, die dort stehen, werden später auch im Quelltext benutzt.

![image-20191217135943157](D:\Dokumente\_Schule\SEW\img\2.png)

Wenn man fertig ist, speichert der Designer das Layout als `.ui`-Datei ab. Daraus muss man ein Python-Modul erstellen. PyQt bietet dafür das Script `pyuic5`, das sich so nutzen lässt:

``````shell
pyuic5 -o <Output-File> <Input-File>
``````

Also zum Beispiel:

``````shell
pyuic5 -o src/main/python/view.py src/main/resources/layout.ui
``````

Das Ergebnis ist ein ewig langes, unübersichtliches Python-File, mit dem man sich nicht weiter beschäftigen muss. Eigentlich sollte man an diesem File nichts mehr händisch abändern. Alle Änderungen, die man an der GUI durchführen will, sollte man im Designer umsetzen und danach das Python-File mit `pyuic5` neu generieren.



### Der Controller

Der Controller ist auch in PyQt die zentrale Komponente. Er überschneidet sich auch ein bisschen mit der View. Der Controller ist eine eigene Klasse, die von einer Klasse der PyQt-API erbt. Welche Klasse das ist, hängt vom Typ der GUI ab – im Beispiel oben habe ich im Designer „Main Window“ ausgewählt, deshalb ist die Klasse `QMainWindow`. Man sieht die Klasse auch im Designer rechts oben im *Object Inspector* – es ist die Klasse des äußersten Elements *Main Window*: `QMainWindow`.



#### Verbindung zur View

Man importiert die View-Klasse aus dem Python-Modul:

``````python
from view import Ui_MainWindow
``````

In der `__init__()`-Methode stellt man die Verbindung her:

``````python
def __init__(self):
    super().__init__()
    self.view = Ui_MainWindow()
    self.view.setupUi(self)
``````

- Weil die Controller-Klasse von einer anderen Klasse erbt, muss zuerst die `__init__`-Methode der Superklasse aufgerufen werden
- Die Methode `setupUi` richtet die GUI ein und erstellt die einzelnen Elemente
- Man übergibt ihr das Controller-Objekt. Das ist notwendig, damit die Verbindung zwischen *Ereignisauslösern* (*Signals* genannt) und *Ereignisempfängern* (*Slots*) hergestellt werden kann. Mehr dazu später.



#### Zugriff auf GUI-Elemente

Über die Referenz auf die View-Klasse `self.view` kann auf die GUI-Elemente zugegriffen werden, zum Beispiel so:

``````python
def printHello(self):
	self.view.output.setText("Hello world")
``````



### Signals und Slots

Signals und Slots sind die beiden Endpunkte für Ereignisse in PyQt:

- *Signals* werden von der GUI ausgelöst (bei Button-Klicks, Eingaben, beim Scrollen…).
- *Slots* können mit Signals verbunden werden und reagieren auf das Ereignis



#### Signals

In Qt sind schon sehr viele Signals vordefiniert. Im Normalfall muss man keine eigenen erstellen.

Manche Signals übergeben Parameter (das Signal `stateChanged` eines `QCheckBox`-Objekts übergibt zum Beispiel den Zustand der Checkbox als `int`). Viele übergeben aber keine. Das sollte man bedenken, wenn man Slots für bestimmte Signals erstellt.



#### Slots

Für Slots gibt es zwei Möglichkeiten.

Man kann Slots aus der GUI nützen und damit GUI-Elemente untereinander verknüpfen. Damit könnte man zum Beispiel beim Klick eines Buttons (`QPushButton.clicked`) ein Eingabefeld leeren (`QLineEdit.clear`). Man kann aber auch jede existierende Methode als eigenen Slot verwenden. In beiden Fällen muss man auf die Anzahl der Parameter bedenken, die das Signal übergibt.



#### Verbinden von Signals und Slots im Designer

Signals und Slots können einfach im Designer verbunden werden. Es gibt dafür sogar einen eigenen Modus, den man oben in der Toolbar einschalten kann. Man sieht dann alle Verbindungen zwischen Signals und Slots, die aktuell existieren:

![image-20191217143428058](D:\Dokumente\_Schule\SEW\img\3.png)

Um eine neue Verbindung zu erstellen, zieht man einfach vom Element, von dem das *Signal* kommt, zu dem Element, in dem der *Slot* definiert ist. Bei selbst erstellten Slots (die im Controller stehen sollten) zieht man auf das MainWindow. Es öffnet sich ein Fenster:

![image-20191217143648842](D:\Dokumente\_Schule\SEW\img\4.png)

Links wählt man das Signal aus, rechts den Slot. Es werden nur die Slots angezeigt, die mit dem Signal kompatibel sind. Um einen neuen, selbst erstellten Slot hinzuzufügen, klickt man auf *Edit…* und trägt den Slot selbst in die Liste ein:

![image-20191217143905235](D:\Dokumente\_Schule\SEW\img\5.png)

Jetzt einfach die Eingaben bestätigen und die Verbindung zwischen Signal und Slot steht!



#### Verbinden von Signals und Slots im Code

Der Signals-Slots-Modus im Designer ist zwar einfach und praktisch, er speichert aber alle Verbindungen im per `pyuic5` erstellten View-File. Das entspricht eigentlich nicht ganz dem MVC-Pattern – die Verbindungen sollten eher im Controller erstellt werden. Auch im [Beispiel-Code](example-currency-converter) wurde die Verbindung im Controller umgesetzt.

Das macht man am bestem in der `__init__()`-Methode des Controllers, und zwar so:

``````python
def __init__():
    # Code von vorher...
    super().__init__()
    self.view = Ui_MainWindow()
    self.view.setupUi(self)
    
    # Hier wird ein Signal mit einem vordefinierten Slot verbunden
    self.view.exit_button.clicked.connect(self.close)
    
    # Hier wird als Slot eine selbst definiere Methode verwendet
    self.view.convert_button.clicked.connect(self.convert)
    
    # Hier wird ein Signal verwendet, das ein Parameter (vom Typ int) übergibt
    self.view.live_data.stateChanged['int'].connect(self.use_live_data)
    
def convert():
    # Code...
    
def use_live_data(use: int):
    # Code...
``````

PyQt erstellt für jedes Signal, das definiert ist (auch für selbst definierte – wie das geht, wird hier aber nicht behandelt), ein kleines Objekt. Dieses Objekt stellt die Methode `connect()` zur Verfügung. Wenn man der `connect()`-Methode eine andere Methode (oder Funktion) übergibt, wird diese als Slot mit dem Signal verbunden.

**Achtung**: Sowohl bei Signals als auch bei Slots wird **nur der Name ohne Klammern** geschrieben! Wenn die Methode `convert()` als Slot benutzt werden soll, wird trotzdem nur `self.convert` ohne Klammern übergeben. Dadurch wird die Methode nicht aufgerufen, sondern einfach als Objekt an `connect()` übergeben.

Im Beispiel oben:

- Das Signal `clicked` des `exit_button`-Objekts wird mit dem Slot `close()` des Controllers verbunden. Wann `clicked` ausgelöst wird, ist klar: Jedes Mal, wenn der `exit_button` geklickt wird. Die Methode `close()` wird nicht selbst im Controller definiert, sondern kommt von der Superklasse `QMainWindow`. Ein Aufruf bewirkt einfach, dass das Fenster geschlossen wird.

  &rarr; Bei einem Klick auf den Button „Exit“ wird das Fenster geschlossen.

- Das Signal `clicked` des `convert_button`-Objekts wird mit dem Slot `convert()` des Controllers verbunden. `convert()` ist dabei eine selbst definierte Methode. In PyQt kann jede beliebige Methode oder Funktion als Slot verwendet werden – solange die Signatur zu den Parametern passt, die das Signal übergibt.

  &rarr; Bei einem Klick auf den Button „Convert“ soll die selbst definierte Methode `convert()` aufgerufen werden (die im Beispiel eine Währungsumrechnung durchführt).

- Das Signal `stateChanged` des `live_data`-Objekts wird mit dem Slot `use_live_data()` des Controllers verbunden. `stateChanged` gehört zur `QCheckBox`-Klasse und wird ausgelöst, wenn sich der Zustand der Checkbox ändert. Nachdem es verschiedene Fälle geben kann (ausgewählt, nicht ausgewählt und eventuell sogar einen Zwischenzustand), wird ein Parameter vom Typ `int` übergeben.

  Die selbst definierte Methode `use_live_data()`, die als Slot benutzt werden soll, muss daher einen Parameter vom Typ `int` übernehmen.

  &rarr; Wenn sich der Zustand der Checkbox ändert, soll die selbst definierte Methode `use_live_data()` im Controller aufgerufen werden. Diese Methode bekommt den neuen Zustand der Checkbox und reagiert darauf.



### Das Model

Nicht alle Anwendungen brauchen überhaupt ein Model. Wenn man eins erstellt, dann macht es Sinn, so vorzugehen:

- Man erstellt das Model-Objekt im Controller
- In der `__init__()`-Methode des Models übergibt man eine Referenz auf den Controller, damit das Model auch Methoden des Controllers aufrufen kann



## Das Starten der Anwendung

Die Anwendung wird in einem `__main__`-Block gestartet. Diesen Block kann man in das Controller-Modul schreiben, oder man erstellt ein eigenes File dafür.

Der Code ist ganz einfach und eigentlich immer gleich:

``````python
import sys
from PyQt5 import QApplication
from controller import Controller

if __name__ == "__main__":
    app = QApplication([])
    mainWindow = Controller()
    mainWindow.show()
    sys.exit(app.exec())
``````