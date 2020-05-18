[<small>Zurück zur Übersicht</small>](../../README.md)

# Java: Annotationen

### Was sind Annotationen?

Annotationen sind eine Möglichkeit, zu einer Klasse, Methode, Variable etc. Meta-Informationen hinzuzufügen.<sup>[[1]](#Quellen)</sup> Das kann verschiedene Nutzen haben:

- Um dem Compiler Kriterien mitzuteilen, die er überprüfen muss (zum Beispiel mit `@Override`)
- Um Softwaretools zusätzliche Informationen zu geben
- Um Entwicklern zusätzliche Informationen zu geben (zum Beispiel `@Deprecated` für veraltete Methoden)
- Manchmal werden Annotationen auch zur Laufzeit verwendet (zum Beispiel bei [Spring Boot](../../springboot/))



### Wie kann man Annotationen selbst implementieren?

Man kann eigene Annotation Types in einem eigenen File selbst implementieren - wie Klassen, Interfaces oder Enums. Die Syntax ist die folgende:

``````java
public @interface MeineAnnotation {
    //Code
}
``````

Attribute ("Elemente" genannt) für die Annotation können auch hinzugefügt werden - die Syntax ist dabei ähnlich wie bei abstrakten Methoden in einem Interface:

``````java
public @interface MeineAnnotation {
    String value();
    int attribut2();
}
``````

Es können auch Standardwerte für die Elemente festgelegt werden:

``````java
public @interface MeineAnnotation {
    String value() default "Hallo Welt";
    int attribut2() default 0;
}
``````

Es gibt Meta-Annotationen, die benutzt werden können, um zusätzliche Informationen zur eigenen Annotation hinzuzufügen - siehe [`@Target`](#1e-welche-target-optionen-gibt-es-was-bedeuten-sie), [`@Retention`](#1f-erläutere-genau-die-verwendung-von-retention).<sup>[[2]](#Quellen)</sup>

Die Elemente einer Annotation können dann so gesetzt werden:

``````java
@MeineAnnotation(value="Hallo Prof. Borko!", attribut2=7)
public class MeineAnnotierteKlasse {
    //Klassen-Code...
}
``````

Praktisch: Wenn die Annotation ein Element `value` hat und sonst keine weiteren Elemente, oder wenn alle weiteren Elemente Default-Werte haben, kann die beim Verwenden der Annotation die Kurzform benutzt werden, bei der `value=` nicht geschrieben werden muss:<sup>[[3]](#Quellen)</sup>

``````java
@MeineAnnotation("Hallo Prof. Brabenetz!")
public class MeineAnnotierteKlasse {
    //Klassen-Code...
}
``````



### Was sind die Unterschiede zu Interfaces?

Laut der Java Language Specification sind Annotations eine besondere Art von Interfaces.<sup>[[4]](#Quellen)</sup>

Es gibt folgende **Gemeinsamkeiten**:

- Annotations haben den selben Namespace wie Interfaces (und Klassen und Enums)
- Von der Syntax her unterscheiden sich die Deklarationen von Interfaces und Annotations nur im `@`-Zeichen vor `interface`, und darin, dass Annotations in manchen Bereichen mehr Einschränkungen haben
- Annotations haben *Elemente* - die entsprechen den abstrakten Methoden von Interfaces
- Elemente von Annotationen sind immer automatisch `public` und `abstract`

Und folgende **Unterschiede**:

- Vor dem Keyword `interface` kommt bei der Deklarierung einer Annotation ein `@`-Zeichen
- Bei Annotations kann kein Eltern-Interface mit `extends` angegeben werden - das Eltern-Interface ist immer implizit `java.lang.Annotation`
- Der "Rückgabewert" (bzw. der Typ) eines Elements kann nur einer der folgenden Typen sein:
  - Ein primitiver Datentyp (`boolean`, `byte`, `int`, `float`,...)
  - `String`
  - `Class`
  - Ein Enum- oder Annotation-Type
  - Ein Array von einem dieser Datentypen



### Wie wird `@Target` benutzt?

`@Target` ist eine Meta-Annotation, die sich auf andere Annotationen bezieht. Sie legt fest, für welche Elemente die Annotation benutzt werden kann (zum Beispiel Klasse, Konstruktoren, ...). Es gibt folgende Optionen:<sup>[[5]](#Quellen), [[6]](#Quellen), [[7]](#Quellen)</sup>

- `ElementType.ANNOTATION_TYPE`: Es handelt sich um eine Meta-Annotation, die sich auf eine andere Annotation bezieht.

  ``````java
  @MeineAnnotation
  @interface AndereAnnotation {
      String value();
  }
  ``````

- `ElementType.CONSTRUCTOR`: Die Annotation bezieht sich auf Konstruktoren.

  ``````java
  public class MeineKlasse {
      private int bla;
      
      @MeineAnnotation
      public MeineKlasse() {
          this.bla = 0;
      }
  }
  ``````

- `ElementType.FIELD`: Die Annotation bezieht sich auf eine Klassen- oder Instanzvariable (ein *Field*). Sie wird bei der Deklaration dieser Variable verwendet.

  ``````java
  public class MeineKlasse {
      @MeineAnnotation
      public int bla;
  }
  ``````

- `ElementType.LOCAL_VARIABLE`: Die Annotation bezieht sich auf eine lokale Variable und wird bei ihrer Deklaration verwendet.

  ``````java
  public class HelloWorld {
      public static void main(String[] args) {
          
          @MeineAnnotation
          String name = args[0];
          
      	System.out.println("Hello " + name);
      }
  }
  ``````

- `ElementType.METHOD`: Die Annotation bezieht sich auf eine Methode.

  ``````java
  public class MeineKlasse {
      @MeineAnnotation
      public helloWorld() {
          System.out.println("Hello world!");
      }
  }
  ``````

- `ElementType.PACKAGE`: Die Annotation bezieht sich auf eine Package-Deklaration. Sie kann nur in der Datei `package-info.java` verwendet werden, einer Datei, die für Dokumentation auf Package-Ebene verwendet wird.

    ``````java
    /**
     * Dieses Package zeigt verschiedene Möglichkeiten, eigene Annotationen zu definieren
     */
    
    @MeineAnnotation
    package com.sschachenhofer.hello;
    ``````

- `ElementType.PARAMETER`: Die Annotation bezieht sich auf ein Formalparameter einer Methode.

    ``````java
    public class MeineKlasse {
        public helloWorld(@MeineAnnotation name) {
            System.out.println("Hello " + name);
        }
    }
    ``````

- `ElementType.TYPE`: Die Annotation bezieht sich auf Typen (das sind Klassen, innere Klassen, Interfaces, Enums und andere Annotations).

    ``````java
    @MeineAnnotation
    public class MeineKlasse {
        public void bla() {
            System.out.println("bla");
        }
    }
    ``````

Neu sind noch zwei weitere Optionen:

- `ElementType.TYPE_PARAMETER`: Die Annotation bezieht sich auf Klassenparameter.

  ``````java
  public class MeineKlasse<@MeineAnnotation M> {
      
  }
  ``````

- `ElementType.TYPE_USE`: Die Annotation bezieht sich auf die Verwendung von Typen (ganz genau verstehe ich das aber auch nicht).

  ``````java
  public class MeineKlasse {
      public static void main(String[] args) {
          @MeineAnnotation
          String s = "Hallo";
      }
  }
  ``````



### Wie wird `@Retention` benutzt?

Auch die Annotation `@Retention` ist eine *Meta-Annotation* und bezieht sich auf andere Annotationen. Sie legt fest, wie lange die Annotation "beibehalten" (bzw. gespeichert) wird.<sup>[[5]](#Quellen)</sup> Dabei gibt es drei Möglichkeiten:

- `RetentionPolicy.SOURCE`: Die Annotation ist zwar im Sourcecode sichtbar, wird aber vom Compiler ignoriert und kommt dann in der `.class`-Datei nicht mehr vor.
- `RetentionPolicy.CLASS`: Die Annotation wird auch vom Compiler verarbeitet und kommt dann im `.class`-File vor. Beim Ausführen des Codes von der JVM werden sie aber nicht mehr beibehalten.
- `RetentionPolicy.RUNTIME`: Die Annotation wird auch zur Laufzeit von der JVM noch beibehalten.

Die Annotation wird so benutzt:

``````java
@Retention(RetentionPolicy.CLASS)
@interface MeineAnnotation {
    String value();
}
``````



### Quellen

[1] ["Lesson: Annotations"; The Java Tutorials; zuletzt besucht am 25.4.2019; online](https://docs.oracle.com/javase/tutorial/java/annotations/index.html)  
[2] ["Declaring an Annotation Type"; The Java Tutorials; zuletzt besucht am 27.4.2019; online](https://docs.oracle.com/javase/tutorial/java/annotations/declaring.html)  
[3] ["Single-Element Annotations"; Java Language Specification; zuletzt besucht am 27.4.2019; online](https://docs.oracle.com/javase/specs/jls/se12/html/jls-9.html#jls-9.7.3)  
[4] ["Annotation Types"; Java Language Specification; zuletzt besucht am 27.4.2019; online](https://docs.oracle.com/javase/specs/jls/se12/html/jls-9.html#jls-9.6)  
[5] ["Predefined Annotation Types"; The Java Tutorials; zuletzt besucht am 28.4.2019; online](https://docs.oracle.com/javase/tutorial/java/annotations/predefined.html)  
[6] ["Target"; Java API Reference; zuletzt besucht am 28.4.2019; online](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/Target.html)  
[7] ["ElementType"; Java API Reference; zuletzt besucht am 28.4.2019; online](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/ElementType.html)  