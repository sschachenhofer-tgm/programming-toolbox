package sprstdverw;

import java.util.Collection;
import java.util.Collections;

/**
 * Um lose Kopplung zu gewährleisten, ist zwischen dem Interface Observer und den konkreten Observer-Klassen noch diese
 * abstrakte Klasse zwischengeschalten. Sie enthält die Referenz auf das konkrete Subject, die Sprechstundenliste.
 *
 * Dadurch kann Observer als einfaches Interface ohne Referenz auf das Subject gestaltet werden und die lose Kopplung
 * ist gewährleistet.
 *
 * @author Simon Schachenhofer
 * @version 2020-02-26
 */
public abstract class SprechstundenObserver implements Observer {

    private Collection<Sprechstunde> sprechstunden;  // Die Liste von Sprechstunden, mit der der Observer arbeitet
    private Sprechstundenliste subject;  // Eine Referenz auf das Subject, das bei Änderungen diesen Observer benachrichtigt

    public SprechstundenObserver(Sprechstundenliste sprechstunden) {
        this.subject = sprechstunden;
        this.sprechstunden = Collections.emptyList();

        this.subject.addObserver(this);  // Anmelden beim Subject, um über Änderungen benachrichtigt zu werden
    }

    /**
     * Bei Änderungen von Daten sollen die neuen Daten geladen und gespeichert werden.
     * Was dann mit den Daten genau geschehen soll, kann in jedem konkreten Observer selbst implementiert werden.
     */
    @Override
    public void update() {
        this.sprechstunden = this.subject.getAllSprechstunden();
    }

    public Collection<Sprechstunde> getSprechstunden() {
        return this.sprechstunden;
    }
}
