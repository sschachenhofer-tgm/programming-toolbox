[&larr; Zurück zu *Design Patterns*](../)  
[&larr; Zurück zur Übersicht](../../README.md)

# Design Patterns: Designprinzipien

### 1. Encapsulate what varies

*Identify the aspects of your application that vary and separate them from what stays the same.*

Wenn es zum Beispiel mehrere Klassen gibt, die gleiche Methoden haben, dann sollten diese gleichen Methoden vom Rest der Klassen getrennt werden.

#### Vorteile

- Durch das Auslagern von Code, der über mehrere Klassen gleich ist, an einen zentralen Ort (zum Beispiel eine Klasse) wird Code-Duplizierung vermieden.
- Dadurch müssen Änderungen nur einmal in der zentralen Klasse durchgeführt werden.

#### Design Patterns

Bei den folgenden Design Patterns ist dieses Prinzip besonders wichtig:

- [Strategy](../strategy/): Gleichbleibender Code (die *Strategies*) wird vom variablen Code (dem *Context*) getrennt und stattdessen in eigene Verhaltensklassen ausgelagert.
- [Decorator](../decorator/): Gleichbleibender Code (die *Decorator*) wird vom variablen Code (den *Core*-Klassen) getrennt.



### 2. Interface > implementation

*Program to an interface, not an implementation.*

Als Deklarationstyp einer Variable sollte immer ein allgemeiner Typ (das implementierte Interface, die Superklasse, ...) verwendet werden. Für Klassen, die Gemeinsamkeiten haben, sollte eine Superklasse definiert werden.

#### Vorteile

- Die Objekte sind austauschbarer (zum Beispiel kann man einer Variable vom Typ `Animal` statt einem `Cat`-Objekt auch ein `Dog`-Objekt zuweisen).
- Man kann Collections von den Objekten erstellen.

#### Design Patterns

Bei den folgenden Design Patterns ist dieses Prinzip besonders wichtig:

- [Strategy](../strategy/): Damit die Verhaltensklassen austauschbar sind, müssen sie ein gemeinsames Interface haben.
- [Decorator](../decorator): Damit sowohl *Decorator*- als auch *Core*-Objekte wieder in einen *Decorator* eingepackt werden können, muss ein gemeinsames Interface existieren.



### 3. Composition > inheritance

*Favor composition over inheritance*

Wenn man zusätzliches Verhalten über eine *Vererbung von der Superklasse* bekommt, heißt das immer, dass man dadurch *die Superklasse ist*. Wenn man für zusätzliches Verhalten aber einfach eine Referenz auf ein anderes Objekt besitzt, dann *hat man etwas*, man *ist es aber nicht*.

Dieses *Haben* ist dem *Sein* vorzuziehen, weil es austauschbar ist.

#### Vorteile

- Das Verhalten ist austauschbar (auch zur Laufzeit!) und damit flexibler.

#### Design Patterns

Bei den folgenden Design Patterns ist dieses Prinzip besonders wichtig:

- [Strategy](../strategy/): Das Verhalten bekommen die *Context*-Klassen nicht über Vererbung, sondern über Variablen, die auf die Verhaltensklassen verweisen.
- [Decorator](../decorator/): Das Decorator-Pattern hat zwar eine Vererbungsstruktur, die ist aber nicht dafür da, Verhalten an Subklassen weiterzugeben. Der einzige Sinn der Vererbungsstruktur ist ein gemeinsames Interface. Stattdessen haben die *Decorators* eine Referenz auf das eingepackte (dekorierte) Objekt, rufen dessen Methode auf und fügen dann das eigene Verhalten dazu.



### 4. Loose coupling

*Strive for loosely coupled designs between objetcs that interact.*

Loose coupling (oder lose Kopplung) bedeutet, dass die interagierenden Objekte möglichst wenig Detailwissen voneinander haben. Sie kennen die Schnittstelle, über die sie kommunizieren, aber die dahinterliegende Klasse ist egal. Es kann irgendeine Klasse sein.

#### Vorteile

- Die lose gekoppelten Objekte sind austauschbar, weil sie abstrahiert sind.
- Dadurch sind sie auch flexibel einsetzbar. Eine Klasse, die auf lose Kopplung setzt, kann man nicht nur in einem Kontext verwenden, sondern in verschiedenen.

#### Design Patterns

Bei den folgenden Design Patterns ist dieses Prinzip besonders wichtig:

- [Observer](../observer/): In einem Projekt kann es viele verschiedene Fälle geben, in denen das Observer Pattern eingesetzt wird. Daher wird versucht, die Interfaces für *Observer* und *Subject* möglichst allgemein zu halten, damit sie von den verschiedenen Klassen verwendet werden können.



### 5. Extension > Modification

*Classes should be open for extension, but closed for modification*

Bestehender Code, der getestet wurde, funktioniert und eingesetzt wird, sollte möglichst nicht mehr abgeändert werden. Er sollte nur noch (über externe Klassen) erweitert werden (siehe [Decorator Pattern](dp/decorator.md)).

#### Vorteile

- Bestehende Schnittstellen müssen nicht abgeändert werden, sondern bleiben bestehen.
- Bestehender Code muss nicht neu getestet werden, weil er nicht verändert wird.

**Achtung:** Dieses Design-Pattern sollte auf keinen Fall immer angewendet werden! In unpassenden Situationen kann es zu unnötig kompliziertem Code führen!

#### Design Patterns

Bei den folgenden Design Patterns ist dieses Prinzip besonders wichtig:

- [Decorator](../decorator/): Beim Decorator wird zusätzliches Verhalten zu einem Objekt hinzugefügt – aber nicht, indem man die bestehende Klasse ändert, sondern, indem man eine *Decorator*-Klasse erstellt.



### 6. Abstraction > Concrete classes

*Depend upon abstraction. Do not depend upon concrete classes.*

