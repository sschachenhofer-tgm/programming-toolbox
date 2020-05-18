package decorator.textreader;

import java.io.IOException;

/**
 * Authentication ist im Decorator-Pattern ein Decorator. Er fügt also zur Funktionalität
 * des nächstinneren Objekts weiteres Verhalten hinzu. In diesem Fall wird die Funktionalität
 * des nächstinneren TextReaders um eine Passworteingabe erweitert.
 */
public class Authentication extends Decorator {

    /*
     * Eine Referenz auf das nächstinnere TextReader-Objekt, um Methodenaufrufe an dieses
     * weiterzuleiten.
     */
    private TextReader inner;

    public Authentication(TextReader inner) {
        this.inner = inner;
    }


	public void write(String[] s) {
        //Ausgeben von "PASSWORD:       "
        System.out.print("PASSWORD:\t\t");

        //Einlesen des Passworts vom Benutzer
        try {
            s[0] = IN.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.inner.write(s);
	}

	public void read(String[] s) {
        /*
         * Um den verschlüsselten String und zusätzlich das Passwort an Scrambling zu übergeben,
         * muss ein neues String-Array mit zwei Elementen angelegt werden.
         */
        String[] sNew = {s[0], ""};

        //Ausgeben von "PASSWORD:       "
        System.out.print("PASSWORD:\t\t");

        //Einlesen des Passworts vom Benutzer
        try {
            sNew[1] = IN.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.inner.read(sNew);
	}

}
