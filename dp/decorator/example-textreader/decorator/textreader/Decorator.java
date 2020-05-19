package decorator.textreader;

/**
 * Die abstrakte Decorator-Klasse Decorator. Alle Decorator müssen von dieser Klasse erben.
 * Die Klasse selbst erbt von TextReader - damit sind alle Decorator auch TextReader.
 */
public abstract class Decorator implements TextReader {

    /*
     * Alle Decorator brauchen eine Referenz auf das nächstinnere Objekt, um Methodenaufrufe
     * an dieses weiterleiten zu können. Dabei ist es egal, ob das innere Objekt wieder ein
     * Decorator ist oder nicht, weil alle Decorators und Cores von der selben Klasse erben:
     * TextReader.
     */
    private TextReader inner;
    
    public Decorator(TextReader inner) {
        this.inner = inner;
    }
    
    public void write(String[] s) {
        this.inner.write(s);
    }
    
    public void read(String[] s) {
        this.inner.read(s);
    }
}
