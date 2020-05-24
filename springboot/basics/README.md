[&larr; Zurück zu *Spring Boot*](../)  
[&larr; Zurück zur Übersicht](../../README.md)

# Spring Boot: Basics

## Erstellen eines Spring Boot Projekts

Projekte mit Spring Boot sind oft eher komplex und haben eine große Ordnerstruktur. Deshalb verwendet man normalerweise ein Build Tool wie Gradle oder Maven. In IntelliJ kann man beim Erstellen eines neuen Projektes auswählen, dass man ein Spring-Boot-Projekt erstellen möchte. Dann übernimmt IntelliJ den Großteil der Konfiguration.



## Annotationen in Spring Boot

Viele Klassen und Methoden in Spring Boot werden mit Annotationen *konfiguriert*. Zu Annotationen allgemein: siehe [hier](../../java/annotations/).



## Die wichtigsten Klassen

### Die Hauptklasse

Die Hauptklasse (*Application*) ist die Klasse, über die die ganze Spring-Boot-Anwendung gestartet wird. Sie stellt auch die `@Bean`-Methoden zur Verfügung.

Die wichtigsten Punkte:

- Die Klasse wird mit `@SpringBootApplication` annotiert

- Die Klasse enthält die statische `main()`-Methode mit folgendem Code:

  ``````java
  public static void main(String[] args) {
  	SpringApplication.run(Application.class, args);
  }
  ``````

  Wobei `Application` der Name der Hauptklasse ist.

- Für jede *Bean* (falls die Anwendung welche benötigt – mehr dazu später) wird eine Methode in der Hauptklasse erstellt. Diese Methode wird mit `@Bean` annotiert. Sie erstellt eine Instanz der benötigten Klasse und gibt sie zurück.

  Hier wird zum Beispiel eine Instanz der Klasse `Searcher` erstellt und zurückgegeben:

  ``````java
  @Bean
  public Searcher getSearcher() {
  	return new Searcher();
  }
  ``````



#### `@SpringBootApplication`

Die Annotation `@SpringBootApplication` definiert die Hauptklasse einer Spring Boot-Anwendung. Sie legt unter anderem Beans fest.<sup>[[1]](#Quellen)</sup>

`@SpringBootApplication` ist eine Kurzform und fasst drei Annotationen zusammen:<sup>[[2]](#Quellen)</sup>

- `@Configuration` (legt fest, dass die Klasse `@Bean`-Methoden zur Verfügung stellt)<sup>[[3]](#Quellen)</sup>
- `@EnableAutoConfiguration` (konfiguriert, basierend auf dem Classpath und den definierten Beans, die wahrscheinlich benötigten Beans automatisch)<sup>[[4]](#Quellen)</sup>
- `@ComponentScan` (sucht automatisch nach benützbaren Components)<sup>[[5]](#Quellen)</sup>



#### `@Bean`

Diese Annotation bezieht sich auf eine Methode (typischerweise in der Klasse, die mit `@SpringBootApplication` annotiert ist). Diese Methode erstellt und initialisiert eine Bean und gibt sie dann zurück.<sup>[[6]](#Quellen)</sup>

Kurz zusammengefasst ist eine Bean ein Java-Objekt, bei dem man zwar festlegt, dass man es braucht, aber das man nicht selbst erstellt. Die Bean wird dann von Spring automatisch erstellt und verwaltet, und man kann darauf zugreifen, wenn man das braucht.



### Die Controller-Klasse

#### `@RestController`

Bei dieser Klasse handelt es sich um den Controller. Die Aufgabe des Controllers ist es, eingehende Anfragen (HTTP Requests) zu beantworten. Daher definiert der Controller normalerweise [`@RequestMapping`](#@RequestMapping)-Methoden.<sup>[[7]](#Quellen)</sup>



#### `@RequestMapping`

Mit `@RequestMapping` werden den annotierten Methoden oder Klassen bestimmte Pfade zugewiesen – HTTP-Requests, die diesen Pfad anfordern, werden dann von der entsprechenden Methode beantwortet.<sup>[[8]](#Quellen)</sup> Wenn man nur HTTP-Requests einer bestimmten HTTP-Methode beantworten möchte, kann man auch die Annotationen `@GetMapping`, `@PostMapping` usw. benutzen.

Auch **Klassen** können mit `@RequestMapping` annotiert werden. Das bedeutet dann, dass die gesamte Klasse nur für die entsprechenden URLs zuständig ist. Auf Klassenebene gibt es aber **keine** HTTP-spezifischen Annotationen wie `@GetMapping` oder `@PostMapping`. <sup>[[9]](#Quellen)</sup>

Auf **Methodenebene** wird dagegen empfohlen, nur die spezifischen Annotationen zu verwenden (also z.B. `@GetMapping` statt `@RequestMapping`). Der Pfad, für den eine Methode letztendlich zuständig ist, ergibt sich aus dem Mapping der Klasse und dem Mapping der Methode. Diese Methode beantwortet zum Beispiel für HTTP-Requests an */hallo/welt*:

``````java
@RestController
@RequestMapping("/hallo")
public Class RESTController {
    
    @GetMapping("/welt")
    public String answerHalloWelt(Model m) {
        m.addAttribute("name", "Simon");
        return "begruessung";
    }
}
``````

##### Methodenparameter

Für die angegebenen Parameter dieser Handler-Methoden gibt es viele verschiedene Möglichkeiten. Man kann die Signatur der Methode praktisch **frei wählen** und verschiedene Parameter anfordern, und Spring wird beim Aufruf der Methode dann automatisch die entsprechenden Werte bereitstellen. Die vollständige Auflistung aller möglicher Parameter findet sich in der Dokumentation von Spring <sup>[[11]](#Quellen)</sup>, die wichtigsten sind hier kurz zusammengefasst.

- Zum **Zugriff auf den Request**: Zum Beispiel `org.springframework.web.context.request.WebRequest` oder `javax.servlet.HttpServletRequest`

- Zum **Zugriff auf den Request-Body**: Zum Beispiel ein `java.io.InputStream` oder `java.io.Reader`, oder ein Parameter einer selbst definierten Klasse, annotiert mit `@RequestBody`
- Zum **direkten Zugriff auf den Response-Body**: Zum Beispiel ein `java.io.OutputStream` oder `java.io.Writer`
- Um für die Response ein **HTML-Template** zu benutzen: Ein `org.springframework.ui.Model` oder eine `java.util.Map` – dort werden dann die Werte für die Parameter gespeichert, die in das Template eingesetzt werden.

##### Rückgabetypen

Auch beim Rückgabetypen hat man eine Wahl zwischen verschiedenen Möglichkeiten. <sup>[[12]](#Quellen)</sup>

- Um einfach **direkt die Response** zu definieren, kann die Methode mit `@ResponseBody` annotiert und einfach ein Objekt zurückgegeben werden, das dann in eine HTTP-Response konvertiert wird.
- Um ein **HTML-Template** zu nutzen: Ein `String`, der den Namen des Templates definiert.



### HTML-Templates

HTML-Templates werden normalerweise im Ordner *src/main/resources/templates* abgelegt. Das ist zumindest der Ort, an dem Thymeleaf, die Template-Engine, sich die Templates erwartet.

Ein Thymeleaf-HTML-Template ist eine erweiterte HTML-Datei. Die Grundstruktur ist gleich wie bei jeder anderen HTML-Datei auch – aber es wird ein zusätzlicher *Namespace* definiert – wie zum Beispiel in XML üblich:

``````xml
<html lang="en" xmlns:th="http://www.thymeleaf.org">
	<!-- Code... -->
</html>
``````

Überall dort, wo Thymeleaf einen Wert einsetzen soll, wird dann das Attribut `th:text` genutzt. Hier wird zum Beispiel das Attribut *task* eingesetzt:

``````xml
<!--/*@thymesVar id="task" type="java.lang.String"*/-->
<span th:text="${task}"></span>
``````

Das Kommentar, das die Variable definiert, ist nicht unbedingt notwendig, aber sicher keine schlechte Idee.

Thymeleaf kann noch viel mehr, als in dieses Dokument passen würde. Auf der Thymeleaf-Website findet man aber eine gute, kurze Einführung <sup>[[13]](#Quellen)</sup> und eine ausführliche Anleitung zur Verwendung von Thymeleaf. <sup>[[14]](#Quellen)</sup>



### Components und Beans

(==TODO==: Dieser Teil muss noch ausgearbeitet werden…)



### Quellen

[1] ["SpringBootApplication"; Spring Boot API Reference; abgerufen am 8.4.2019](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[2] ["SpringBootApplication"; Spring Boot API Reference; abgerufen am 27.4.2019](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[3] ["Configuration"; Spring Framework API Reference; abgerufen am 27.4.2019](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html)  
[4] ["EnableAutoConfiguration"; Spring Boot API Reference; abgerufen am 27.4.2019](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html)  
[5] ["ComponentScan"; Spring Framework API Reference; abgerufen am 27.4.2019](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.html)  
[6] ["Bean"; Spring Framework API Reference; abgerufen am 27.4.2019](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)  
[7] ["RestController"; Spring Framework API Reference; abgerufen am 27.4.2019](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)  
[8] ["RequestMapping"; Spring Framework API Reference; abgerufen am 27.4.2019](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html)  
[9] ["Request Mapping"; Spring Framework Reference; abgerufen am 22.5.2020](https://docs.spring.io/spring/docs/5.2.6.RELEASE/spring-framework-reference/web.html#mvc-ann-requestmapping)  
[10] ["Handler Methods"; Spring Framework Reference; abgerufen am 22.5.2020](https://docs.spring.io/spring/docs/5.2.6.RELEASE/spring-framework-reference/web.html#mvc-ann-methods)  
[11] ["Handler Methods: Method Arguments"; Spring Framework Reference; abgerufen am 22.5.2020](https://docs.spring.io/spring/docs/5.2.6.RELEASE/spring-framework-reference/web.html#mvc-ann-arguments)  
[12] ["Handler Methods: Return Values"; Spring Framework Reference; abgerufen am 22.5.2020](https://docs.spring.io/spring/docs/5.2.6.RELEASE/spring-framework-reference/web.html#mvc-ann-return-types)  
[13] ["Getting started with the standard dialect in 5 minutes"; Thymeleaf Documentation; abgerufen am 23.5.2020](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html)  
[14] ["Using Thymeleaf"; Thymeleaf Documentation; abgerufen am 23.5.2020](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

