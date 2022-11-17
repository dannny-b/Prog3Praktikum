package de.hsos.prog3.danibloc.ab04;

import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;
import de.hsos.prog3.danibloc.ab04.util.*;

import java.awt.event.KeyEvent;
import java.net.http.WebSocket;
import java.sql.Time;

public class PongSpiel {
    private Interaktionsbrett ib;

    public Interaktionsbrett getIb() {
        return ib;
    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
    }

    public Spieler getSpielerLinks() {
        return spielerLinks;
    }

    public Spieler getSpielerRechts() {
        return spielerRechts;
    }

    private Spielfeld spielfeld;
    private Spieler spielerLinks;
    private Spieler spielerRechts;

    public Spielen spielenThread;
    private final int FPMS = 17;

    public PongSpiel(){
        ib = new Interaktionsbrett();
        startAufstellung();
        ib.willTasteninfo(this);

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
        spielenThread = new Spielen("spielenThread",this);
        spielenThread.start();
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
            spielerLinks.aufwaerts();
        }

        if (s.equals("y")) {
            spielerLinks.abwaerts();
        }
    }


}
