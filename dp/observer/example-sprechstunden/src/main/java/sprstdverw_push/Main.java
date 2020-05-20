package sprstdverw_push;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        // Erstellen einer Sprechstundenliste
        Sprechstundenliste liste = new Sprechstundenliste();

        // Erstellen von zwei Observer-Klassen
        Observer web = new WebServiceObserver(liste);
        Observer mail = new EmailServiceObserver(liste);

        // Verändern der Daten - die Observer sollten benachrichtigt werden
        System.out.println("--- Erwartet: 1 Eintrag ---");
        liste.addSprechstunde("HOEK", "H930D", DayOfWeek.WEDNESDAY, LocalTime.of(15, 10), LocalTime.of(16, 0));

        System.out.println("--- Erwartet: 2 Einträge ---");
        liste.addSprechstunde("DOLD", "H928", DayOfWeek.TUESDAY, LocalTime.of(12, 30), LocalTime.of(13, 20));

        System.out.println("--- Erwartet: 1 Eintrag ---");
        liste.deleteSprechstunde("HOEK");

        // Entfernen des EmailServiceObservers
        liste.removeObserver(mail);

        // Jetzt sollte nur noch der WebServiceObserver benachrichtigt werden
        System.out.println("--- Erwartet: 1 Eintrag, aber nur noch der WebServiceObserver ---");
        liste.addSprechstunde("KRUC", "H827", DayOfWeek.MONDAY, LocalTime.of(9, 50), LocalTime.of(10, 40));

        // Entfernen aller Observer
        liste.removeObservers();

        // Jetzt sollte kein Observer mehr benachrichtigt werden
        System.out.println("--- Erwartet: Keine Ausgabe mehr ---");
        liste.deleteSprechstunde("DOLD");
        liste.deleteSprechstunde("KRUC");

    }
}
