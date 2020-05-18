[<small>Zurück zu *Design Patterns*</small>](../)  
[<small>Zurück zur Übersicht</small>](../../)

# Design Patterns: Designprinzipien

#### 1. Encapsulate what varies

*Identify the aspects of your application that vary and separate them from what stays the same.*

Wenn es zum Beispiel mehrere Klassen gibt, die gleiche Methoden haben, dann sollten diese gleichen Methoden vom Rest der Klassen getrennt werden.

**Vorteile**

- Durch das Auslagern von Code, der variiert, an einen zentralen Ort (zum Beispiel eine Klasse) wird Code-Duplizierung vermieden.



#### 2. Interface > implementation

*Program to an interface, not an implementation*

Als Deklarationstyp einer Variable sollte immer ein allgemeiner Typ (das implementierte Interface, die Superklasse, ...) verwendet werden. Für Klassen, die Gemeinsamkeiten haben, sollte eine Superklasse definiert werden.

**Vorteile**
- Die Objekte sind austauschbarer (zum Beispiel kann man einer Variable vom Typ `Animal` statt einem `Cat`-Objekt auch ein `Dog`-Objekt zuweisen).
- Man kann Collections von den Objekten erstellen.



#### 3. Composition > inheritance

*Favor composition over inheritance*

Wenn man zusätzliches Verhalten über eine Vererbung von der Superklasse bekommt, heißt das immer, dass man *die Elternklasse ist*. Wenn man für zusätzliches Verhalten aber einfach eine Referenz auf ein anderes Objekt besitzt, dann *hat man etwas*, man *ist es aber nicht*.  
Dieses *Haben* ist dem *Sein* vorzuziehen, weil es austauschbar ist (vorausgesetzt, es gibt eine generelle Schnittstelle).

**Vorteile**

- Das Verhalten ist austauschbar (auch zur Laufzeit!) und damit flexibler.



#### 4. Loose coupling

*Strive for loosely coupled designs between objetcs that interact*

Loose coupling (oder lose Kopplung) bedeutet, dass die interagierenden Objekte möglichst wenig Detailwissen voneinander haben. Sie kennen die Schnittstelle, über die sie kommunizieren, aber die dahinterliegende Klasse ist egal. Es kann irgendeine Klasse sein.

**Vorteile**

- Austauschbarkeit (eh klar)



#### 5. Extension > Modification

*Classes should be open for extension, but closed for modification*

Bestehender Code, der getestet wurde, funktioniert und eingesetzt wird, sollte möglichst nicht mehr abgeändert werden. Er sollte nur noch (über externe Klassen) erweitert werden (siehe [Decorator Pattern](dp/decorator.md)).

**Vorteile**

- Bestehende Schnittstellen müssen nicht abgeändert werden, sondern bleiben bestehen.
- Bestehender Code muss nicht neu getestet werden, weil er nicht verändert wird.

**Achtung:** Dieses Design-Pattern sollte auf keinen Fall immer angewendet werden! Es kann in unpassenden Situationen zu unnötig kompliziertem Code führen!



#### 6. Abstraction > Concrete classes

*Depend upon abstraction. Do not depend upon concrete classes*

