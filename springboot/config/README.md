[&larr; Zurück zu *Spring Boot*](../)  
[&larr; Zurück zur Übersicht](../../README.md)

# Spring Boot: Konfiguration

#### Wo erwartet sich SpringBoot Konfigurationsdateien?

Es wird empfohlen, Spring Boot über mit `@Configuration` annotierte Java-Klassen zu konfigurieren, nicht über externe Konfigurationsfiles. Wenn man trotzdem mit XML-basierter Konfiguration arbeiten möchte, kann man in dieser Klasse die XML-Dateien mit `@ImportResource` importieren <sup>[[1]](#Quellen)</sup>

Die Haupt-Konfigurationsdatei `application.properties` wird von Spring Boot in folgenden Ordnern gesucht:<sup>[[2]](#Quellen)</sup>

- Einem `/config`-Ordner im aktuellen Ordner
- Dem aktuellen Ordner
- Einem `/config`-Package
- Dem Classpath-Root

Wenn man Spring Boot mit Gradle verwendet, liegt `application.properties` standardmäßig in `src/main/resources`. Das ist wahrscheinlich ein Ort, an dem das Gradle-Plugin für Spring Boot die Datei sucht, das ist aber in der Dokumentation nicht beschrieben...



#### Wie geht das Gradle-Plugin für SpringBoot mit den Config-Files um?



#### Wie kann man eigene Config-Settings in SpringBoot setzen?

Die empfohlene Variante ist die folgende:<sup>[[3]](#Quellen)</sup>

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



### Quellen

[1] ["Configuration Classes"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-configuration-classes)  
[2] ["Application Property files"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-application-property-files)  
[3] ["Type-safe Configuration Properties"; Spring Boot Reference Guide; zuletzt besucht am 28.4.2019; online](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties)