[<small>Zurück zur Übersicht</small>](../../README.md)

# Spring Boot: Basics

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

Diese Annotation bezieht sich auf eine Methode (typischerweise in der Klasse, die mit `@SpringBootApplication` annotiert ist). Diese Methode erstellt und initialisiert eine Bean und gibt sie dann zurück.<sup>[[6]](#Quellen)</sup>

Kurz zusammengefasst ist eine Bean ein Java-Objekt, bei dem man zwar festlegt, dass man es braucht, aber das man nicht selbst erstellt. Die Bean wird dann von Spring automatisch erstellt und verwaltet, und man kann darauf zugreifen, wenn man das braucht.



### `@RestController`

Bei dieser Klasse handelt es sich um den Controller. Die Aufgabe des Controllers ist es, eingehende Anfragen (HTTP Requests) zu beantworten. Daher definiert der Controller normalerweise [`@RequestMapping`](#@RequestMapping)-Methoden.<sup>[[7]](#Quellen)</sup>



### `@RequestMapping`

Mit `@RequestMapping` werden den annotierten Methoden bestimmte Pfade zugewiesen - HTTP-Requests, die diesen Pfad anfordern, werden dann von der entsprechenden Methode beantwortet.<sup>[[8]](#Quellen)</sup>

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



### Weitere wichtige Annotationen (noch auszuarbeiten)

- `@Autowired`
- `@Entity`
- `@Id`
- `@GeneratedValue`



### Quellen

[1] ["SpringBootApplication"; Spring Boot API Reference; zuletzt besucht am 8.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[2] ["SpringBootApplication"; Spring Boot API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)  
[3] ["Configuration"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html)  
[4] ["EnableAutoConfiguration"; Spring Boot API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/EnableAutoConfiguration.html)  
[5] ["ComponentScan"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ComponentScan.html)  
[6] ["Bean"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Bean.html)  
[7] ["RestController"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)  
[8] ["RequestMapping"; Spring Framework API Reference; zuletzt besucht am 27.4.2019; online](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html)  