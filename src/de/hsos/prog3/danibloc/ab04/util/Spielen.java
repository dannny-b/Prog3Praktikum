package de.hsos.prog3.danibloc.ab04.util;

import de.hsos.prog3.danibloc.ab04.PongSpiel;
import de.hsos.prog3.danibloc.ab04.ui.Ball;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;

public class Spielen extends Thread {
    Interaktionsbrett ib;
    Spielfeld spielfeld;
    PongSpiel spiel;
    Ball ball;
    Spieler spielerLinks;
    Spieler spielerRechts;
    private Thread thread;
    private String threadName;

    public Spielen(String threadName, PongSpiel spiel) throws InterruptedException {
        this.spiel = spiel;
        this.threadName = threadName;
        this.ib = this.spiel.getIb();
        this.spielfeld = this.spiel.getSpielfeld();
        this.spielerLinks = this.spiel.getSpielerLinks();
        this.spielerRechts = this.spiel.getSpielerRechts();
        this.ball = this.spiel.getBall();
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
            this.sleep(spiel.getFPMS() - differenz);

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
