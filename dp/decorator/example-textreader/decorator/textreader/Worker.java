package decorator.textreader;

import java.io.IOException;

/**
 * Diese Klasse stellt im Decorator-Pattern den Core dar. Worker implementiert also die
 * Kernfunktionalität aller TextReader. Um den Code zur Laufzeit dynamisch zu erweitern, kann der
 * Core in beliebig viele Decorator "eingepackt" werden.
 *
 * Auch Worker erbt von TextReader. Damit kann man auch den Worker selbst ohne irgendwelche
 * Decorator als TextReader einsetzen.
 */
public class Worker implements TextReader {
    public void write(String[] s) {
        System.out.print("Input:\t\t");
        try {
            s[0] = IN.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(String[] s) {
        System.out.println("Output:\t\t" + s[0]);
    }
}
