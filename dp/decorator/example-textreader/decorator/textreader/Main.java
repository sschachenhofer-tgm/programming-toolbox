package decorator.textreader;

public class Main {

    public static void main(String[] args) {

        //Ausgabe des Begrüßungstextes (Zeile 1)
        System.out.println("main:\t\tlet's start!");

        TextReader stream = new Authentication(new Scrambling(new Worker()));
        String[] str = {new String()};

        //Benutzer gibt zuerst Passwort (Klasse Authentication) und dann Text (Klasse Scrambling?) ein
        //(Zeile 2 - 3), danach noch Ausgabe von "encrypt: " (Zeile 4)
        stream.write(str);

        //str[0] wird verschlüsselt und wieder ausgegeben (Zeile 5)
        System.out.println("main:\t\t" + str[0]);

        //Benutzer gibt Passwort erneut ein (wieder Authentication), dann wird der Text entschlüsselt. Ausgabe von
        //"decrypt" und vom entschlüsselten Text (Zeile 6 - 8)
        stream.read(str);
    }
}
