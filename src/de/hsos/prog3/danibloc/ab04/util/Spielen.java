package de.hsos.prog3.danibloc.ab04.util;

import de.hsos.prog3.danibloc.ab04.PongSpiel;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;

public class Spielen extends Thread {
    Interaktionsbrett ib;
    Spielfeld spielfeld;
    Spieler spielerLinks;
    Spieler spielerRechts;
    private final int FPMS = 17;
    private Thread thread;
    private String threadName;

    public Spielen(String threadName, PongSpiel spiel) throws InterruptedException {
        this.threadName = threadName;
        this.ib = spiel.getIb();
        this.spielfeld = spiel.getSpielfeld();
        this.spielerLinks = spiel.getSpielerLinks();
        this.spielerRechts = spiel.getSpielerRechts();

    }

    public void run() {
        try {
            spielen();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void spielen() throws InterruptedException {
        long differenz = 0;
        while (true) {
            long vorher = System.currentTimeMillis();
            ib.abwischen();
            spielfeld.darstellen(ib);
            synchronized (spielerLinks) {
                spielerLinks.getSchlaeger().darstellenFuellung(ib);
            }
            synchronized (spielerRechts) {
                spielerRechts.getSchlaeger().darstellenFuellung(ib);
            }
            long nacher = System.currentTimeMillis();
            differenz = (nacher - vorher);
            this.sleep(FPMS - differenz);

        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.setPriority(1);
            thread.start();

        }
    }

}
