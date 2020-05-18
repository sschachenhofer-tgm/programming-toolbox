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
     *
     * Warum das Attribut hier als private deklariert ist, ist mir nicht ganz klar... Damit wird
     * es nämlich nicht an die Decorator-Implementierungen vererbt und ist nutzlos...
     */
	private TextReader inner;

}
