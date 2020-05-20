package sprstdverw_push;

/**
 * Ein Observer ist eine Klasse, die mit externen Daten arbeitet, die sich ändern können. Wenn sich die Daten ändern,
 * wird der Observer darüber benachrichtigt. Im Push-Prinzip werden dabei auch die Daten übergeben.
 *
 * @author Simon Schachenhofer
 * @version 2020-02-26
 */
public interface Observer {
    /**
     * Das Interface Observer hat nur eine Methode: update(). Über diese Methode kann der Observer darüber
     * benachrichtigt werden, dass sich beim Subject Daten geändert haben. Diese Daten werden auch gleich übergeben.
     */
	void update(Object arg);
}
