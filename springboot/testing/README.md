[<small>Zurück zur Übersicht</small>](../../README.md)

# Spring Boot: Testing

[Quelle](https://spring.io/guides/gs/testing-web/)

### Getestete Klasse

(Beispiel - `Controller.java`)

``````java
@Controller
public class HomeController {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "Hello World";
    }

}
``````



### build.gradle

Diese Zeile in `build.gradle` einfügen:

``````groovy
testCompile("org.springframework.boot:spring-boot-starter-test")
``````



### Testklasse

(Beispiel - `ApplicationTest.java`)

``````java
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private HomeController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
``````

- Annotationen für die **Klasse**: `@RunWith(SpringRunner.class)` und `@SpringBootTest`
- Annotationen für die **Testmethoden**: `@Test` (wie gehabt)
- **Wenn Objekte benötigt werden**: Mit `@Autowired` injecten lassen - sie werden wie bei einer `@Before`-Methode vor den Testcases injected.



### Aufwendigere Testklasse zum Testen von Funktionen

(Beispiel - `HttpRequestTest.java`)

``````java
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Hello World");
    }
}
``````

- `@RunWith(SpringRunner.class)` wie normal
- `@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)` (um Konflikte mit Ports zu vermeiden)
- Die zufällig generierte Portnummer wird dann in die Variable mit `@LocalServerPort` gespeichert
- `TestRestTemplate`-Objekt mit `@Autowired` - das wird zum testweisen Abschicken von HTTP-Requests gebraucht
  - Auf diesem Objekt wird dann `getForObject()` mit URL und dem Rückgabedatentyp (hier `String`) aufgerufen - der Rückgabewert ist dann die Response, die getestet werden kann



### Testen mit gemocktem Server

Was ist der Unterschied zum Testen mit Server?

Naja, hier wird nur der Layer getestet, bei dem Requests eingehen und an den Controller weitergegeben werden. Es werden fast alle Komponenten benutzt, aber der Server muss nicht laufen.

``````java
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }
}
``````

- `@AutoConfigureMockMvc` für die Klasse...
- ...und ein `MockMvc`-Attribut mit `@Autowired`, um ein automatisch von Spring generiertes Mock-Objekt zu erhalten (das `MockMvc`-Attribut mit `@Autowired` bleibt, an den Tests ändert sich auch nichts)
- Dann in der Testmethode die lange Aufrufkette...
  - `this.mockMvc.perform(get(<URL>))` - legt fest, welche Adresse angefragt und damit getestet werden soll
  - `andDo()` für allgemeine Methodenaufrufe (z.B. `MockMvcResultHandlers.print()`)
  - `andExpect()` für Tests - da muss ein `MockMvcResultMatcher` übergeben werden. Diese werden über die Factory-Klasse `MockMvcResultMatchers` erstellt. Es gibt zum Beispiel `status()`, `content()`, `header()`, `cookie()`, `request()`...

Statt `@SpringBootTest` und `@AutoConfigureMockMvc` kann auch `@WebMvcTest` genutzt werden, um nur den Weblayer zu testen.



### Mocken von Beans

```java
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(service.greet()).thenReturn("Hello Mock");
        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello Mock")));
    }
}
```

- Bean-Attribut mit `@MockBean` erstellen
- Im Testcase festlegen, was die gemockte Bean machen soll
  - `when()` legt fest, *wann* diese Aktion ausgeführt wird
  - `thenReturn()` legt fest, *was* dann gemacht werden soll
