package decorator.textreader;

/**
 * Eine Klasse, die statische Methoden für die Caesar-"Verschlüsselung" enthält.
 * Die Caesar-Verschlüsselung ist keine wirkliche Verschlüsselung, weil sie extrem unsicher
 * und auch ohne Passwort leicht zu knacken ist. Aber zur Demonstration des TextReader-Beispiels
 * reicht sie aus.
 *
 * @author  Simon Schachenhofer
 * @since   2019-03-16
 * @version 2019-03-16
 */
public class Caesar {

    /**
     * Privater Konstruktor, um eine Instanzierung zu verhindern.
     */
    private Caesar() {}

    /**
     * Verschlüsselt den Text mit der Caesar-Verschlüsselung. Um wie viele Stellen die einzelnen
     * Zeichen verschoben werden wird mit dem Hashwert des Passworts berechnet.
     * @param   raw        Der zu verschlüsselnde Text
     * @param   password    Das Passwort, mit dem verschlüsselt werden soll
     */
    public static String encode(String raw, String password) {
        return Caesar.shift(raw, password.hashCode() % 64);
    }

    /**
     * Entschlüsselt den mit der Caesar-Verschlüsselung verschlüsselten Text. Um wie
     * viele Stellen die einzelnen Zeichen verschoben werden wird mit dem Hashwert des
     * Passworts berechnet.
     * @param   encoded     Der zu entschlüsselnde Text
     * @param   password    Das Passwort, mit dem verschlüsselt wurde
     */
    public static String decode(String encoded, String password) {
        return Caesar.shift(encoded, - password.hashCode() % 64);
    }

    /**
     * Private Hilfsmethode, um alle Zeichen in einem String um eine bestimmte Anzahl
     * an Zeichen zu verschieben.
     * @param   text    Der Text, dessen Zeichen verschoben werden sollen
     * @param   shift   Die Anzahl an Stellen, um die verschoben werden soll
     */
    private static String shift(String text, int shift) {
        StringBuilder shifted = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            shifted.append((char) (c + shift));
        }

        return shifted.toString();
    }
}
