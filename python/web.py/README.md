[&larr; Zurück zu *Python*](../)
[&larr; Zurück zur Übersicht](../../README.md)

# Python: Einfache Webservices mit web.py

Mit der Library *web.py* lassen sich einfache Webservices erstellen.

Meine ehrliche Meinung zu web.py ist, dass es eine absolut unlogische, unflexible und schlecht dokumentierte Library ist. Aber wenn man nur schnell ein simples Webservice erstellen möchte, ist sie vielleicht doch ganz hilfreich.



## Installation

web.py wird – wie die allermeisten Python-Libraries – über *pip* installiert:

``````
pip install web.py
``````

**Achtung**: Wenn man web.py dann in einem Modul importieren will, muss man nicht mehr `web.py`, sondern nur `web` eingeben!



## Ein einfaches Webservice

Um ein einfaches Webservice mit web.py zu programmieren, braucht man nur ein Modul. In diesem erstellt man pro Webservice-Endpoint eine eigene Klasse.



### Definieren der Endpoints

Im Modul definiert man eine Konstante `urls` (ein `tuple`). Mit dieser Konstante werden die Endpoints definiert und ihnen die jeweiligen Klassen zugeordnet. Es gibt jeweils eine Klasse pro URL-Endpoint, und diese Klasse kann Requests für alle HTTP-Methoden annehmen.

Man gibt jeweils die URL des Endpoints und dann den Namen der zuständigen Handler-Klasse an (beides als String). Das kann zum Beispiel so aussehen:

``````python
urls = (
    "/", "RootHandler",
	"/hallo", "HalloHandler",
    "/test", "TestHandler"
)
``````

Zwei Anmerkungen dazu:

- Ja, das ist wirklich ein `tuple` und kein `dict`. Ein `dict` würde zwar hundertmal mehr Sinn machen, wenn es darum geht, jeweils zwei Werte einander zuzuordnen, aber die Entwickler von web.py haben sich trotzdem für ein `tuple` entschieden…
- Ja, man gibt wirklich den Klassennamen als String an, nicht die Klasse selbst.



### Die Handler-Klasse

Um Requests an die Endpoints zu beantworten, muss man dann Klassen erstellen, die so heißen, wie in `urls` festgelegt.

- Diese Klassen brauchen **keine `__init__()`-Methode**

- Stattdessen erstellt man Methoden für die einzelnen HTTP-Methoden (`GET`, `POST`, `PUT`, …), um die entsprechenden Requests zu beantworten. Um beim Beispiel von oben zu bleiben: Die Klasse `HalloHandler` könnte zum Beispiel so aussehen:

  ``````python
  class HalloHandler:
      def GET(self):
          """Beantwortet HTTP-Requests an GET /hallo"""
          return "Das ist ein GET-Request"
      
      def POST(self):
          """Beantwortet HTTP-Requests an POST /hallo"""
          return "Das ist ein POST-Request"
      
      def PUT(self):
          """Beantwortet HTTP-Requests an PUT /hallo"""
          return "Das ist ein PUT-Request"
  ``````
  
  Alle anderen HTTP-Methoden (`DELETE`, `PATCH`, `OPTIONS`,...) werden **nicht** beantwortet, weil dafür keine Methoden definiert wurden.

Anmerkung: Ja, die Methodennamen sind wirklich in Großbuchstaben. Nein, das entspricht überhaupt nicht den gängigen Python-Standards. Aber das war den Machern von web.py wohl ziemlich egal…



### Die Handler-Methoden

Wie oben erklärt, bekommt jeder URL-Endpoint eine Methode für jede HTTP-Methode, die beantwortet werden soll. Diese Methode gibt üblicherweise einfach einen String zurück. Dieser String wird dann 1:1 als Response an den Client gesendet.



#### Zugriff auf Request-Parameter

Um auf Request-Parameter (also die *Query-Parameter* bei GET-Requests oder den Body bei POST- und anderen Requests) zuzugreifen, gibt es die Funktion `web.input()`. Diese Funktion gibt ein Objekt zurück, aus dem man wie aus einem `dict` alle Parameter auslesen kann.

Wenn man sich beim Endpoint *GET /hallo* einen Query-Parameter *name* erwartet, kann man so darauf zugreifen:

``````python
class HalloHandler:
	def GET(self):
        name = web.input()["name"]
``````

Es gibt auch noch eine zweite Möglichkeit: Auf die Parameter kann auch wie auf die Attribute eines Objekts zugegriffen werden:

``````python
class HalloHandler:
	def GET(self):
        name = web.input().name
``````



### Starten des Servers

Der Server wird in einem `__main__`-Block folgendermaßen gestartet:

``````python
if __name__ == "__main__":
    web.application(urls, globals()).run()
``````



## Das war‘s auch schon.

web.py kann noch viel mehr (zum Beispiel Datenbankzugriff oder Templating), aber da würde ich dann doch lieber ein richtiges Web-Framework wie Django oder Flask nehmen…