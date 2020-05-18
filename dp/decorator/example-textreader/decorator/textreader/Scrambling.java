package decorator.textreader;

/**
 * Scrambling ist im Decorator-Pattern ein Decorator. Er fügt also zur Funktionalität
 * des nächstinneren Objekts weiteres Verhalten hinzu. In diesem Fall wird die Funktionalität
 * des nächstinneren TextReaders um eine Verschlüsselung erweitert.
 */
public class Scrambling extends Decorator {

    /*
     * Eine Referenz auf das nächstinnere TextReader-Objekt, um Methodenaufrufe an dieses
     * weiterzuleiten.
     */
    private TextReader inner;

    public Scrambling(TextReader inner) {
        this.inner = inner;
    }

	public void write(String[] s) {
        //Das Passwort wird im String-Array übergeben und muss als lokale Variable gespeichert werden
        String password = s[0];

        //Den Benutzer zur Eingabe des Textes auffordern
        this.inner.write(s);

        //Verschlüsseln des Textes
        String rawText = s[0];
        s[0] = Caesar.encode(rawText, password);

        //Ausgeben von "encrypt:"
        System.out.println("encrypt:");
	}

	public void read(String[] s) {
        //Das Passwort wird im String-Array übergeben und muss als lokale Variable gespeichert werden
        String password = s[1];

        //Entschlüsseln des Textes
        String encodedText = s[0];
        s[0] = Caesar.decode(encodedText, password);

        //Ausgeben von "decrypt:"
        System.out.println("decrypt:");

        this.inner.read(s);
	}

}
