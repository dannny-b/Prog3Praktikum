package de.hsos.prog3.danibloc.ab04;

import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

import java.awt.event.KeyEvent;
import java.sql.Time;

public class PongSpiel {
    private Interaktionsbrett ib;
    private Spielfeld spielfeld;
    private Spieler spielerLinks;
    private Spieler spielerRechts;
    private final int FPMS = 17;

    public PongSpiel() {
        ib = new Interaktionsbrett();
        ib.willTasteninfo(this);
        startAufstellung();
    }

    private void startAufstellung() {
        spielfeld = new Spielfeld();
        spielerLinks = new Spieler(spielfeld, spielfeld.getMARGIN() + 20, (int) (spielfeld.getHeight() * 0.45));
        spielerRechts = new Spieler(spielfeld, spielfeld.getWidth() + spielfeld.getMARGIN() - 20, (int) (spielfeld.getHeight() * 0.45));
        ib.neuesRechteck(spielerLinks, "spielerLinks", spielerLinks.getSchlaeger().getX(), spielerLinks.getSchlaeger().getY(), spielerLinks.getSchlaeger().getBreite(), spielerLinks.getSchlaeger().getHoehe());
        ib.neuesRechteck(spielerRechts, "spielerRechts", spielerRechts.getSchlaeger().getX(), spielerRechts.getSchlaeger().getY(), spielerRechts.getSchlaeger().getBreite(), spielerRechts.getSchlaeger().getHoehe());
        spielfeld.darstellen(ib);
        spielerLinks.getSchlaeger().darstellenFuellung(ib);
        spielerRechts.getSchlaeger().darstellenFuellung(ib);
    }

    public void spielen() throws InterruptedException {
        long differenz = 0;
        while (true) {
            long vorher = System.currentTimeMillis();
            ib.abwischen();
            spielfeld.darstellen(ib);
            spielerLinks.getSchlaeger().darstellenFuellung(ib);
            spielerRechts.getSchlaeger().darstellenFuellung(ib);
            long nacher = System.currentTimeMillis();
            Thread.sleep(FPMS - (nacher-vorher));
        }

    }

    public void tasteGedrueckt(String s) throws InterruptedException {
        if (s.equals("s")) {
            System.out.println("Spiel gestartet");
            spielen();
        }


        if (s.equals("e")) {
            System.out.println("exit");
            System.exit(1);
        }

        if (s.equals("a")) {
            System.out.println("Spieler 1 hoch");
            spielerLinks.aufwaerts();
        }

        if (s.equals("y")) {
            System.out.println("Spieler 2 runter");
            spielerRechts.abwaerts();
        }
    }
}
