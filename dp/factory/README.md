[<small>Zurück zur Übersicht</small>](../../README.md)

# Factory Pattern

### Problembeschreibung

Es gibt zwei Varianten des Factory Patterns: [Factory Method](factory-method) und [Abstract Factory](abstract-factory). Das grundlegende Problem ist bei beiden das selbe:

- In jedem Programm müssen irgendwann einmal **Objekte erstellt werden**.
- Oft geht es dabei um unterschiedliche Varianten - also unterschiedliche **Implementierungen des selben Interfaces** (in den Beispielen verschiedene Pizza-Sorten).
- Der Code zum Erstellen von Objekten sollte **nicht im Client-Code enthalten** sein, denn dann wäre das Design-Prinzip *Extension > Modification* ("Classes should be open for extension, but closed for modification") verletzt. Wenn eine neue Klasse dazukommt, die das Interface auch implementiert, dann muss der Client-Code verändert werden!



### Lösung

- [Factory Method](factory-method)
- [Abstract Factory](abstract-factory/README.md)