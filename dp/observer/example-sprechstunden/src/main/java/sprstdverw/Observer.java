package sprstdverw;

/**
 * Ein Observer ist eine Klasse, die mit externen Daten arbeitet, die sich ändern können. Wenn sich die Daten ändern,
 * wird der Observer darüber benachrichtigt. Im Pull-Prinzip werden dabei keine Daten übergeben. Der Observer kann die
 * Daten daraufhin vom Server abrufen.
 *
 * @author Simon Schachenhofer
 * @version 2020-02-26
 */
public interface Observer {
    /**
     * Das Interface Observer hat nur eine Methode: update(). Über diese Methode kann der Observer darüber
     * benachrichtigt werden, dass sich beim Subject Daten geändert haben. Diese Daten werden aber nicht übergeben.
     * Stattdessen kann der Observer auf die Benachrichtigung reagieren und die Daten abfragen.
     */
	void update();
}
