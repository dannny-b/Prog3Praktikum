package de.hsos.prog3.danibloc.ab04;

import de.hsos.prog3.danibloc.ab04.logik.KollisionsDetektion;
import de.hsos.prog3.danibloc.ab04.ui.Ball;
import de.hsos.prog3.danibloc.ab04.ui.Rectangle;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

public class PongSpiel {
    private static Ball ball;
    private final int FPMS = 17;
    private final int BALL_DIM = 15;
    private Interaktionsbrett ib;
    private KollisionsDetektion detektor;
    private boolean gestartet;
    private Spielfeld spielfeld;
    private Spieler spielerLinks;
    private Spieler spielerRechts;
    public PongSpiel() {
        ib = new Interaktionsbrett();
        startAufstellung();
        ib.willTasteninfo(this);
        gestartet = false;

    }
    int count = 0;

    public Ball getBall() {
        return ball;
    }

    public int getFPMS() {
        return FPMS;
    }

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

    private void startAufstellung() {
        spielfeld = new Spielfeld();
        spielerLinks = new Spieler(spielfeld, spielfeld.getMARGIN() + 20, (int) (spielfeld.getHeight() * 0.45));
        spielerRechts = new Spieler(spielfeld, spielfeld.getWidth() + spielfeld.getMARGIN() - 20, (int) (spielfeld.getHeight() * 0.45));
        ball = new Ball(new Rectangle(spielerLinks.getX() + spielfeld.getWidth() / 80, spielerLinks.getY(), BALL_DIM, BALL_DIM), this);
        ib.neuesRechteck(spielerLinks, "spielerLinks", spielerLinks.getSchlaeger().getX(), spielerLinks.getSchlaeger().getY(), spielerLinks.getSchlaeger().getBreite(), spielerLinks.getSchlaeger().getHoehe());
        ib.neuesRechteck(spielerRechts, "spielerRechts", spielerRechts.getSchlaeger().getX(), spielerRechts.getSchlaeger().getY(), spielerRechts.getSchlaeger().getBreite(), spielerRechts.getSchlaeger().getHoehe());
        ib.neuesRechteck(ball, "Ball", ball.getX(), ball.getY(), ball.getBreite(), ball.getHoehe());
        spielfeld.darstellen(ib);
        spielerLinks.getSchlaeger().darstellenFuellung(ib);
        spielerRechts.getSchlaeger().darstellenFuellung(ib);
        ball.getForm().darstellenFuellung(ib);
        detektor = new KollisionsDetektion(spielfeld, spielerLinks, spielerRechts);
        ib.textZeigen("PongSpiel");
    }

    public void spielen() {
        ball.setBewegungInXProFrame(3);
        ball.setBewegungInYProFrame(1);
        new Thread(() -> {
            Thread.currentThread().setPriority(1);
            long differenz = 0;
            while (true) {
                count++;
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
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        if (detektor.checkBeruehrungBallMitSchlaeger(ball)) {
            ball.umkehrenDerBewegungInX();
        } else if (detektor.checkBeruehrungBallSpielfeldGrenzen(ball)) {
            ball.umkehrenDerBewegungInY();
        } else if (detektor.checkAusserhalbDesSpielfeldes(ball) != KollisionsDetektion.BallPosition.DRINNEN) {
            ball.umkehrenDerBewegungInX();
            erhöhePunkte();
        }
    }

    public void erhöhePunkte() {
        if (ball.getX() < spielfeld.getSpielflaeche().mitteInX()) {
            if (spielerRechts.getPunkte() <= 14) {
                spielerRechts.erhoehePunkte();
            } else {
                //gewonnen(spielerRechts);
            }

        } else {
            if (spielerLinks.getPunkte() <= 14) {
                spielerLinks.erhoehePunkte();
            } else {
                //gewonnen(spielerLinks);
            }
        }
    }

    public void tasteGedrueckt(String s) throws InterruptedException {
        if (s.equals("s") && gestartet == false) {
            System.out.println("Spiel gestartet");
            gestartet = true;

            spielen();
            Thread.sleep(5000);
            System.out.println(count);


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
