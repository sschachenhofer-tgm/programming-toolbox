# Fragen zu Annotationen und SpringBoot

Folgende Fragen sind ausschließlich über die Spring-Dokumentation und -API zu recherchieren und in einem README.md zu dokumentieren (Quellen **immer** angeben!)

## Inhalt

- [**Annotationen**](#annotationen)
  - [1. Beschreibe folgende Annotationen](#1-beschreibe-folgende-annotationen)
    - [`@SpringBootApplication`](#springbootapplication)
    - [`@Bean`](#bean)
    - [`@RestController`](#restcontroller)
    - [`@RequestMapping`](#requestmapping)
  - [1a. Was sind Annotationen?](#1a-was-sind-annotationen)
  - [1b. Welche Möglichkeiten gibt es, diese selbst zu implementieren?](#1b-welche-möglichkeiten-gibt-es-diese-selbst-zu-implementieren)
  - [1c. Wie hängen Annotationen mit Interfaces zusammen? Welche Gemeinsamkeiten gibt es?](#1c-wie-hängen-annotationen-mit-interfaces-zusammen-welche-gemeinsamkeiten-gibt-es)
  - [1d. Kann man @SpringBootApplication auch anders anschreiben?](#1d-kann-man-springbootapplication-auch-anders-anschreiben)
  - [1e. Welche @Target Optionen gibt es? Was bedeuten sie?](#1e-welche-target-optionen-gibt-es-was-bedeuten-sie)
  - [1f. Erläutere genau die Verwendung von @Retention](#1f-erläutere-genau-die-verwendung-von-retention)
- [**Konfiguration von Spring Boot**](#konfiguration)
  - [2a. Wo erwartet sich SpringBoot Konfigurationsdateien?](#2a-wo-erwartet-sich-springboot-konfigurationsdateien)
  - [2b. Wie geht das Gradle-Plugin für SpringBoot mit den Config-Files um?](#2b-wie-geht-das-gradle-plugin-für-springboot-mit-den-config-files-um)
  - [2c. Wie kann man eigene Config-Settings in SpringBoot setzen?](#2c-wie-kann-man-eigene-config-settings-in-springboot-setzen)
  - [2d. Wo kann man die wichtigsten Default-Configs von SpringBoot vorfinden?](#2d-wo-kann-man-die-wichtigsten-default-configs-von-springboot-vorfinden)
  - [2e. Welche Config-Files können gesetzt werden und wofür?](#2e-welche-config-files-können-gesetzt-werden-und-wofür)
- [**Unklare Fragen**](#kenn-mich-nicht-aus)
- [**Quellen**](#quellen)



## Annotationen

#### 1. Beschreibe folgende Annotationen

##### @SpringBootApplication

Die Annotation `@SpringBootApplication` definiert die Hauptklasse einer Spring Boot-Anwendung. Sie legt unter anderem Beans fest.<sup>[[1]](#Quellen)</sup>

##### @Bean

Diese Annotation bezieht sich auf eine Methode (typischerweise in der Klasse, die mit `@SpringBootApplication` annotiert ist). Diese Methode erstellt und initialisiert eine Bean und gibt sie dann zurück.<sup>[[2]](#Quellen)</sup>

Kurz zusammengefasst ist eine Bean ein Java-Objekt, bei dem man zwar festlegt, dass man es braucht, aber das man nicht selbst erstellt. Die Bean wird dann von Spring automatisch erstellt und verwaltet, und man kann darauf zugreifen, wenn man das braucht.

##### @RestController

Bei dieser Klasse handelt es sich um den Controller. Die Aufgabe des Controllers ist es, eingehende Anfragen (HTTP Requests) zu beantworten. Daher definiert der Controller normalerweise [`@RequestMapping`](#@RequestMapping)-Methoden.<sup>[[3]](#Quellen)</sup>

##### @RequestMapping

Mit `@RequestMapping` werden den annotierten Methoden bestimmte Pfade zugewiesen - HTTP-Requests, die diesen Pfad anfordern, werden dann von der entsprechenden Methode beantwortet.<sup>[[4]](#Quellen)</sup>

Diese Methode ist zum Beispiel für HTTP-Requests an [/hallo/welt]() verantwortlich (angenommen, der Server ist unter der Domain [schachenhofer.at]() zu erreichen, dann beantwortet sie also Requests an [https://www.schachenhofer.at/hallo/welt]()):

``````java
@RestController
public Class RESTController {
    
    @RequestMapping("/hallo/welt")
    public String answerHalloWelt(Model m) {
        m.addAttribute("name", this.name);
        return "begruessung";
    }
}
``````

Für die angegebenen Parameter und den Rückgabewert dieser Methoden gibt es viele verschiedene Möglichkeiten. Oft wird folgende Kombination genutzt:

- Die Methode hat einen Parameter vom Typ `Model` - dieses Objekt kann man benutzen, um Variablen , die dann in ein Website-Template eingefügt werden
- Der Rückgabewert ist ein `String` und steht für ein HTML-Template im Ordner `src/main/resources/templates` (zum Beispiel steht `"begruessung"` für das Template `begruessung.html`). In dieses Template werden dann mit der Template-Engine **Thymeleaf** die Model-Variablen eingefügt.

Wenn man nur HTTP-Requests einer bestimmten Art beantworten möchte, kann man auch die Annotationen `@GetMapping`, `@PostMapping` usw. benutzen.



#### 1a. Was sind Annotationen?

Annotationen sind eine Möglichkeit, zu einer Klasse, Methode, Variable etc. Meta-Informationen hinzuzufügen.<sup>[[5]](#Quellen)</sup> Das kann verschiedene Nutzen haben:

- Um dem Compiler Kriterien mitzuteilen, die er überprüfen muss (zum Beispiel mit `@Override`)
- Um Softwaretools zusätzliche Informationen zu geben
- Um Entwicklern zusätzliche Informationen zu geben (zum Beispiel `@Deprecated` für veraltete Methoden)
- Manchmal werden Annotationen auch zur Laufzeit verwendet (zum Beispiel `@Component` und `@Autowired` bei Spring Boot)



#### 1b. Welche Möglichkeiten gibt es, diese selbst zu implementieren?

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

Es gibt Meta-Annotationen, die benutzt werden können, um zusätzliche Informationen zur eigenen Annotation hinzuzufügen - siehe [`@Target`](#1e-welche-target-optionen-gibt-es-was-bedeuten-sie), [`@Retention`](#1f-erläutere-genau-die-verwendung-von-retention).<sup>[[6]](#Quellen)</sup>

Die Elemente einer Annotation können dann so gesetzt werden:

``````java
@MeineAnnotation(value="Hallo Prof. Borko!", attribut2=7)
public class MeineAnnotierteKlasse {
    //Klassen-Code...
}
``````

Praktisch: Wenn die Annotation ein Element `value` hat und sonst keine weiteren Elemente, oder wenn alle weiteren Elemente Default-Werte haben, kann die beim Verwenden der Annotation die Kurzform benutzt werden, bei der `value=` nicht geschrieben werden muss:<sup>[[7]](#Quellen)</sup>

``````java
@MeineAnnotation("Hallo Prof. Brabenetz!")
public class MeineAnnotierteKlasse {
    //Klassen-Code...
}
``````



#### 1c. Wie hängen Annotationen mit Interfaces zusammen? Welche Gemeinsamkeiten gibt es?

Laut der Java Language Specification sind Annotations eine besondere Art von Interfaces.<sup>[[8]](#Quellen)</sup>

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



#### 1d. Kann man @SpringBootApplication auch anders anschreiben?

Ja. `@SpringBootApplication` ist eine Kurzform und fasst drei Annotationen zusammen:<sup>[[9]](#Quellen)</sup>

- `@Configuration` (legt fest, dass die Klasse `@Bean`-Methoden zur Verfügung stellt)<sup>[[10]](#Quellen)</sup>
- `@EnableAutoConfiguration` (konfiguriert, basierend auf dem Classpath und den definierten Beans, die wahrscheinlich benötigten Beans automatisch)<sup>[[11]](#Quellen)</sup>
- `@ComponentScan` (sucht automatisch nach benützbaren Components)<sup>[[12]](#Quellen)</sup>



#### 1e. Welche @Target Optionen gibt es? Was bedeuten sie?

`@Target` ist eine Meta-Annotation, die sich auf andere Annotationen bezieht. Sie legt fest, für welche Elemente die Annotation benutzt werden kann (zum Beispiel Klasse, Konstruktoren, ...). Es gibt folgende Optionen:<sup>[[13]](#Quellen), [[14]](#Quellen), [[15]](#Quellen)</sup>

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



#### 1f. Erläutere genau die Verwendung von @Retention

Auch die Annotation `@Retention` ist eine *Meta-Annotation* und bezieht sich auf andere Annotationen. Sie legt fest, wie lange die Annotation "beibehalten" (bzw. gespeichert) wird.<sup>[[13]](#Quellen)</sup> Dabei gibt es drei Möglichkeiten:

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



## Konfiguration

#### 2a. Wo erwartet sich SpringBoot Konfigurationsdateien?

Es wird empfohlen, Spring Boot über mit `@Configuration` annotierte Java-Klassen zu konfigurieren, nicht über externe Konfigurationsfiles. Wenn man trotzdem mit XML-basierter Konfiguration arbeiten möchte, kann man in dieser Klasse die XML-Dateien mit `@ImportResource` importieren <sup>[[16]](#Quellen)</sup>

Die Haupt-Konfigurationsdatei `application.properties` wird von Spring Boot in folgenden Ordnern gesucht:<sup>[[17]](#Quellen)</sup>

- Einem `/config`-Ordner im aktuellen Ordner
- Dem aktuellen Ordner
- Einem `/config`-Package
- Dem Classpath-Root

Wenn man Spring Boot mit Gradle verwendet, liegt `application.properties` standardmäßig in `src/main/resources`. Das ist wahrscheinlich ein Ort, an dem das Gradle-Plugin für Spring Boot die Datei sucht, das ist aber in der Dokumentation nicht beschrieben...

#### 2b. Wie geht das Gradle-Plugin für SpringBoot mit den Config-Files um?

#### 2c. Wie kann man eigene Config-Settings in SpringBoot setzen?

Die empfohlene Variante ist die folgende:<sup>[[18]](#Quellen)</sup>

1. Man erstellt eine Java-Klasse mit `@ConfigurationProperties(namespace)` (`namespace` ist ein String und gibt den Prefix, also den Namensraum, für alle Konfigurationsvariablen aus dieser Klasse an):

   ``````java
   @ConfigurationProperties("custom")
   public class CustomProperties {
   	
   }
   ``````

2. Dieser Klasse fügt man Attribute hinzu. Man kann auch Java-Klassen benutzen.

   ``````java
   @ConfigurationProperties("custom")
   public class CustomProperties {
       
   	public String customProperty1 = "Simon Schachenhofer";
       public int customProperty2 = 7;
       public LocalDate sewTestTermin = LocalDate.of(2019, 5, 6);
   }
   ``````

3. Man fügt zur Konfigurations-Klasse (die mit `@Configuration` oder `@SpringBootApplication` annotiert ist) eine weitere Annotation `@EnableConfigurationProperties` hinzu und gibt den Klassennamen der Klasse mit den eigenen Konfigurationsvariablen an:

   ``````java
   @SpringBootApplication
   @EnableConfigurationProperties(CustomProperties.class)
   public class Application {
       
       //main-Methode
       
       //Bean-Definitionen
   
   }
   ``````

   

#### 2d. Wo kann man die wichtigsten Default-Configs von SpringBoot vorfinden?

#### 2e. Welche Config-Files können gesetzt werden und wofür?



## Kenn mich nicht aus

*Falls beim Beantworten der Testfragen eigene Fragen aufkommen, die man nicht beantworten kann, weil man etwas nicht versteht, kann man die hier hinein schreiben und nachher abhaken, wenn man sie beantwortet hat.*

- [ ] Was macht das Gradle-Plugin mit dem vorgefundenen application.properties-File?
- [ ] Zu [Frage 2c](#2c-wie-kann-man-eigene-config-settings-in-springboot-setzen): Wie kann man diese Properties benutzen? Wie kann man allgemein auf Properties von Spring zugreifen?



## Quellen

[1] ["SpringBootApplication"; Spring Boot API Reference; zuletzt besucht am 8.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[2] ["Bean"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)  
[3] ["RestController"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)  
[4] ["RequestMapping"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html)  
[5] ["Lesson: Annotations"; The Java Tutorials; zuletzt besucht am 25.4.2019; online](https://docs.oracle.com/javase/tutorial/java/annotations/index.html)  
[6] ["Declaring an Annotation Type"; The Java Tutorials; zuletzt besucht am 27.4.2019; online](https://docs.oracle.com/javase/tutorial/java/annotations/declaring.html)  
[7] ["Single-Element Annotations"; Java Language Specification; zuletzt besucht am 27.4.2019; online](https://docs.oracle.com/javase/specs/jls/se12/html/jls-9.html#jls-9.7.3)  
[8] ["Annotation Types"; Java Language Specification; zuletzt besucht am 27.4.2019; online](https://docs.oracle.com/javase/specs/jls/se12/html/jls-9.html#jls-9.6)  
[9] ["SpringBootApplication"; Spring Boot API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[10] ["Configuration"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html)  
[11] ["EnableAutoConfiguration"; Spring Boot API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html)  
[12] ["ComponentScan"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.html)  
[13] ["Predefined Annotation Types"; The Java Tutorials; zuletzt besucht am 28.4.2019; online](https://docs.oracle.com/javase/tutorial/java/annotations/predefined.html)  
[14] ["Target"; Java API Reference; zuletzt besucht am 28.4.2019; online](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/Target.html)  
[15] ["ElementType"; Java API Reference; zuletzt besucht am 28.4.2019; online](https://docs.oracle.com/javase/8/docs/api/java/lang/annotation/ElementType.html)  
[16] ["Configuration Classes"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-configuration-classes)  
[17] ["Application Property files"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-application-property-files)  
[18] ["Type-safe Configuration Properties"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties)