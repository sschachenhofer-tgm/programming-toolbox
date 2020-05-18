[<small>Zurück zur Übersicht</small>](../README.md)

# Spring Boot

## Annotationen bei Spring Boot

Annotationen allgemein: siehe [hier](../java/annotations/)

Spring Boot benützt verschiedene Annotationen – auch zur Laufzeit. Hier sollen die wichtigsten erklärt werden.



### `@SpringBootApplication`

Die Annotation `@SpringBootApplication` definiert die Hauptklasse einer Spring Boot-Anwendung. Sie legt unter anderem Beans fest.<sup>[[1]](#Quellen)</sup>

`@SpringBootApplication` ist eine Kurzform und fasst drei Annotationen zusammen:<sup>[[2]](#Quellen)</sup>

- `@Configuration` (legt fest, dass die Klasse `@Bean`-Methoden zur Verfügung stellt)<sup>[[3]](#Quellen)</sup>
- `@EnableAutoConfiguration` (konfiguriert, basierend auf dem Classpath und den definierten Beans, die wahrscheinlich benötigten Beans automatisch)<sup>[[4]](#Quellen)</sup>
- `@ComponentScan` (sucht automatisch nach benützbaren Components)<sup>[[5]](#Quellen)</sup>



### `@Bean`

Diese Annotation bezieht sich auf eine Methode (typischerweise in der Klasse, die mit `@SpringBootApplication` annotiert ist). Diese Methode erstellt und initialisiert eine Bean und gibt sie dann zurück.<sup>[[2]](#Quellen)</sup>

Kurz zusammengefasst ist eine Bean ein Java-Objekt, bei dem man zwar festlegt, dass man es braucht, aber das man nicht selbst erstellt. Die Bean wird dann von Spring automatisch erstellt und verwaltet, und man kann darauf zugreifen, wenn man das braucht.



### `@RestController`

Bei dieser Klasse handelt es sich um den Controller. Die Aufgabe des Controllers ist es, eingehende Anfragen (HTTP Requests) zu beantworten. Daher definiert der Controller normalerweise [`@RequestMapping`](#@RequestMapping)-Methoden.<sup>[[3]](#Quellen)</sup>



### `@RequestMapping`

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



## Konfiguration

#### Wo erwartet sich SpringBoot Konfigurationsdateien?

Es wird empfohlen, Spring Boot über mit `@Configuration` annotierte Java-Klassen zu konfigurieren, nicht über externe Konfigurationsfiles. Wenn man trotzdem mit XML-basierter Konfiguration arbeiten möchte, kann man in dieser Klasse die XML-Dateien mit `@ImportResource` importieren <sup>[[9]](#Quellen)</sup>

Die Haupt-Konfigurationsdatei `application.properties` wird von Spring Boot in folgenden Ordnern gesucht:<sup>[[10]](#Quellen)</sup>

- Einem `/config`-Ordner im aktuellen Ordner
- Dem aktuellen Ordner
- Einem `/config`-Package
- Dem Classpath-Root

Wenn man Spring Boot mit Gradle verwendet, liegt `application.properties` standardmäßig in `src/main/resources`. Das ist wahrscheinlich ein Ort, an dem das Gradle-Plugin für Spring Boot die Datei sucht, das ist aber in der Dokumentation nicht beschrieben...



#### Wie geht das Gradle-Plugin für SpringBoot mit den Config-Files um?



#### Wie kann man eigene Config-Settings in SpringBoot setzen?

Die empfohlene Variante ist die folgende:<sup>[[11]](#Quellen)</sup>

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

   

#### Wo kann man die wichtigsten Default-Configs von SpringBoot vorfinden?

#### Welche Config-Files können gesetzt werden und wofür?



## Kenn mich nicht aus

*Falls beim Beantworten der Testfragen eigene Fragen aufkommen, die man nicht beantworten kann, weil man etwas nicht versteht, kann man die hier hinein schreiben und nachher abhaken, wenn man sie beantwortet hat.*

- [ ] Was macht das Gradle-Plugin mit dem vorgefundenen application.properties-File?
- [ ] Zu [Frage 2c](#2c-wie-kann-man-eigene-config-settings-in-springboot-setzen): Wie kann man diese Properties benutzen? Wie kann man allgemein auf Properties von Spring zugreifen?



## Quellen

[1] ["SpringBootApplication"; Spring Boot API Reference; zuletzt besucht am 8.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[2] ["SpringBootApplication"; Spring Boot API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[3] ["Configuration"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html)  
[4] ["EnableAutoConfiguration"; Spring Boot API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html)  
[5] ["ComponentScan"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.html)  
[6] ["Bean"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)  
[7] ["RestController"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)  
[8] ["RequestMapping"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html)  
[9] ["Configuration Classes"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-configuration-classes)  
[10] ["Application Property files"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-application-property-files)  
[11] ["Type-safe Configuration Properties"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties)