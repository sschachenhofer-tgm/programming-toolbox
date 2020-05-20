package sprstdverw;

/**
 * Eine konkrete Observer-Implementierung
 *
 * @author Simon Schachenhofer
 * @version 2020-02-26
 */
public class EmailServiceObserver extends SprechstundenObserver {

    public EmailServiceObserver(Sprechstundenliste sprechstunden) {
        super(sprechstunden);
    }

    /**
     * Bei Änderungen werden zuerst die neuen Daten abgefragt. (siehe SprechstundenObserver.update()). Dann wird die
     * Länge der Sprechstundenliste ausgegeben.
     */
    @Override
    public void update() {
        super.update();
        System.out.println(String.format("EmailServiceObserver: Die Sprechstundenliste hat sich geändert und enthält " +
                        "jetzt %d %s",
                super.getSprechstunden().size(),
                super.getSprechstunden().size() == 1 ? "Eintrag" : "Einträge"));
    }
}
