package de.hsos.prog3.danibloc.ab04;

import de.hsos.prog3.danibloc.ab04.logik.TastenEreignis;
import de.hsos.prog3.danibloc.ab04.ui.Ball;
import de.hsos.prog3.danibloc.ab04.ui.Rectangle;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;
import de.hsos.prog3.danibloc.ab04.util.*;

import java.awt.event.KeyEvent;
import java.net.http.WebSocket;
import java.sql.Time;

public class PongSpiel implements TastenEreignis {
    private Interaktionsbrett ib;
    private Ball ball;

    private boolean gestartet;

    public Ball getBall() {
        return ball;
    }

    public int getFPMS() {
        return FPMS;
    }

    private Spielfeld spielfeld;
    private Spieler spielerLinks;
    private Spieler spielerRechts;

    public Spielen spielenThread;
    private final int FPMS = 17;
    private final int BALL_DIM = 15;

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


    public PongSpiel() throws CloneNotSupportedException {
        ib = new Interaktionsbrett();
        startAufstellung();
        ib.willTasteninfo(this);
        gestartet = false;

    }

    private void startAufstellung() {
        spielfeld = new Spielfeld();
        spielerLinks = new Spieler(spielfeld, spielfeld.getMARGIN() + 20, (int) (spielfeld.getHeight() * 0.45));
        spielerRechts = new Spieler(spielfeld, spielfeld.getWidth() + spielfeld.getMARGIN() - 20, (int) (spielfeld.getHeight() * 0.45));
        Ball ball = new Ball(new Rectangle(spielerLinks.getX() + spielfeld.getWidth() / 80, spielerLinks.getY(), BALL_DIM, BALL_DIM));
        ib.neuesRechteck(spielerLinks, "spielerLinks", spielerLinks.getSchlaeger().getX(), spielerLinks.getSchlaeger().getY(), spielerLinks.getSchlaeger().getBreite(), spielerLinks.getSchlaeger().getHoehe());
        ib.neuesRechteck(spielerRechts, "spielerRechts", spielerRechts.getSchlaeger().getX(), spielerRechts.getSchlaeger().getY(), spielerRechts.getSchlaeger().getBreite(), spielerRechts.getSchlaeger().getHoehe());
        ib.neuesRechteck(ball, "Ball", ball.getX(), ball.getY(), ball.getBreite(), ball.getHoehe());
        spielfeld.darstellen(ib);
        spielerLinks.getSchlaeger().darstellenFuellung(ib);
        spielerRechts.getSchlaeger().darstellenFuellung(ib);
        ball.getForm().darstellenFuellung(ib);
    }

    public void spielen() throws InterruptedException {
        spielenThread = new Spielen("spielenThread", this);
        spielenThread.start();
    }

    public void tasteGedrueckt(String s) throws InterruptedException {


        if (s.equals("s") && gestartet==false) {
            System.out.println("Spiel gestartet");
            gestartet = true;
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

        if (s.equals("Oben")) {
            spielerRechts.aufwaerts();
        }

        if (s.equals("Unten")) {
            spielerRechts.abwaerts();
        }
    }


}
