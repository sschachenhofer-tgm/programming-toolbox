package sprstdverw_push;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Eine Klasse, die Daten zu einer Sprechstunde enthält.
 * @author Simon Schachenhofer
 * @version 2020-02-26
 */
public class Sprechstunde {

	private String lehrer;
	private String raum;
	private DayOfWeek wochentag;
	private LocalTime beginn;
	private LocalTime ende;

	public Sprechstunde() {
        this.lehrer = "";
        this.raum = "";
        this.wochentag = DayOfWeek.MONDAY;
        this.beginn = LocalTime.of(0, 0);
        this.ende = LocalTime.of(23, 59);
	}

	public Sprechstunde(String lehrer, String raum, java.time.DayOfWeek wochentag, java.time.LocalTime beginn,
                        java.time.LocalTime ende) {
        this.lehrer = lehrer;
        this.raum = raum;
        this.wochentag = wochentag;
        this.beginn = beginn;
        this.ende = ende;
	}

	public String getLehrer() {
		return this.lehrer;
	}

	public String getRaum() {
		return this.raum;
	}

	public String getWochentag() {
		return this.wochentag.getDisplayName(TextStyle.FULL, Locale.GERMAN);
	}

	public String getBeginn() {
		return this.beginn.format(DateTimeFormatter.ISO_LOCAL_TIME);
	}

	public String getEnde() {
        return this.ende.format(DateTimeFormatter.ISO_LOCAL_TIME);
	}

	public void setLehrer(String lehrer) {
        this.lehrer = lehrer;
	}

	public void setRaum(String raum) {
        this.raum = raum;
	}

	public void setWochentag(java.time.DayOfWeek wochentag) {
        this.wochentag = wochentag;
	}

	public void setBeginn(java.time.LocalTime beginn) {
        this.beginn = beginn;
	}

	public void setEnde(java.time.LocalTime ende) {
        this.ende = ende;
	}

	@Override
	public String toString() {
	    return String.format("Sprechstunde mit %s: Jeden %s von %s bis %s",
                this.lehrer, this.getWochentag(), this.getBeginn(), this.getBeginn());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sprechstunde) {
            Sprechstunde spr = (Sprechstunde) obj;
            return this.lehrer.equals(spr.lehrer) && this.raum.equals(spr.raum) &&
                    this.beginn.equals(spr.beginn) && this.ende.equals(spr.ende);
        } else {
            return false;
        }
    }
}
