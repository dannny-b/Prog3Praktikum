package de.hsos.prog3.danibloc.ab04;

import de.hsos.prog3.danibloc.ab04.logik.KollisionsDetektion;
import de.hsos.prog3.danibloc.ab04.ui.Ball;
import de.hsos.prog3.danibloc.ab04.ui.Rectangle;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

public class PongSpiel {
    private Interaktionsbrett ib;
    private static Ball ball;

    private KollisionsDetektion detektor;
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

    private final int FPMS = 1;
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


    public PongSpiel() {
        ib = new Interaktionsbrett();
        startAufstellung();
        ib.willTasteninfo(this);
        gestartet = false;

    }

    private void startAufstellung() {
        spielfeld = new Spielfeld();
        spielerLinks = new Spieler(spielfeld, spielfeld.getMARGIN() + 20, (int) (spielfeld.getHeight() * 0.45));
        spielerRechts = new Spieler(spielfeld, spielfeld.getWidth() + spielfeld.getMARGIN() - 20, (int) (spielfeld.getHeight() * 0.45));
        ball = new Ball(new Rectangle(spielerLinks.getX() + spielfeld.getWidth() / 80, spielerLinks.getY(), BALL_DIM, BALL_DIM), this);
        ib.neuesRechteck(spielerLinks, "spielerLinks", spielerLinks.getSchlaeger().getX(), spielerLinks.getSchlaeger().getY(), spielerLinks.getSchlaeger().getBreite(), spielerLinks.getSchlaeger().getHoehe());
        ib.neuesRechteck(spielerRechts, "spielerRechts", spielerRechts.getSchlaeger().getX(), spielerRechts.getSchlaeger().getY(), spielerRechts.getSchlaeger().getBreite(), spielerRechts.getSchlaeger().getHoehe());
        ib.neuesRechteck(ball, "Ball", ball.getX(), ball.getY(), ball.getBreite(), ball.getHoehe());
        ib.neuerText(spielfeld.getSpielflaeche().mitteInX() - spielfeld.getMARGIN(), spielfeld.getSpielflaeche().getY() + spielfeld.getMARGIN(), Integer.toString(spielerLinks.getPunkte()));
        spielfeld.darstellen(ib);
        spielerLinks.getSchlaeger().darstellenFuellung(ib);
        spielerRechts.getSchlaeger().darstellenFuellung(ib);
        ball.getForm().darstellenFuellung(ib);
        detektor = new KollisionsDetektion(spielfeld, spielerLinks, spielerRechts);
        ib.textZeigen("PongSpiel");
    }

    public void spielen() {
        new Thread(() -> {
            Thread.currentThread().setPriority(1);
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
                try {
                    ball.bewegen(1);
                    pruefeBallKollision();
                    ball.darstellen(ib);
                } catch (RuntimeException | InterruptedException e) {
                    throw new RuntimeException(e);
                }


                long nacher = System.currentTimeMillis();
                differenz = (nacher - vorher);
                try {
                    if (differenz <= FPMS) {
                        Thread.currentThread().sleep(getFPMS() - differenz);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void pruefeBallKollision() {
        KollisionsDetektion.BallPosition pos = detektor.checkAusserhalbDesSpielfeldes(ball);
        if (pos == KollisionsDetektion.BallPosition.DRINNEN) {
            if (detektor.checkBeruehrungBallMitSchlaeger(ball)) {
                ball.umkehrenDerBewegungInX();
            }
            if (detektor.checkBeruehrungBallSpielfeldGrenzen(ball)) {
                ball.umkehrenDerBewegungInY();
            }
        } else {
            if (pos == KollisionsDetektion.BallPosition.DRAUSSEN_LINKS) {
                spielerRechts.erhoehePunkte();
            }
            if (pos == KollisionsDetektion.BallPosition.DRAUSSEN_RECHTS) {
                spielerLinks.erhoehePunkte();
            }
        }
    }

    public void tasteGedrueckt(String s) {
        if (s.equals("s") && gestartet == false) {
            System.out.println("Spiel gestartet");
            gestartet = true;
            spielen();
        }
        if (s.equals("e")) {
            System.out.println("exit");
            System.exit(1);
        }
        //SpielerLinks
        if (s.equals("a")) {
            spielerLinks.aufwaerts();
        }
        if (s.equals("y")) {
            spielerLinks.abwaerts();
        }
        //SpielerRechts
        if (s.equals("Oben")) {
            spielerRechts.aufwaerts();
        }

        if (s.equals("Unten")) {
            spielerRechts.abwaerts();
        }
    }
}
