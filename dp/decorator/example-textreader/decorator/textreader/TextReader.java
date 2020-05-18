package decorator.textreader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Das Interface für alle TextReader und deren Decorator.
 *
 * Jede Core-Klasse und jede Decorator-Klasse implementiert TextReader - das ist wichtig, damit man
 * wirklich alle möglichen Kombinationen aus Cores und Decorators als TextReader verwenden kann!
 */
public interface TextReader {

    BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));

    void write(String[] s);
    void read(String[] s);
}
