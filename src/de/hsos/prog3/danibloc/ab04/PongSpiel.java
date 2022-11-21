package de.hsos.prog3.danibloc.ab04;

import de.hsos.prog3.danibloc.ab04.logik.KollisionsDetektion;
import de.hsos.prog3.danibloc.ab04.ui.Ball;
import de.hsos.prog3.danibloc.ab04.ui.Rectangle;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

import java.util.Random;

public class PongSpiel {
    private static Ball ball;
    private final int FPMS = 17;
    private final int BALL_DIM = 15;
    private final int MAX_PUNKTE = 5;
    private Interaktionsbrett ib;
    private KollisionsDetektion detektor;
    private boolean gestartet;
    private Spielfeld spielfeld;
    private Spieler spielerLinks;
    private Spieler spielerRechts;
    private boolean gewonnen;
    private boolean firstHit;

    public PongSpiel() {
        ib = new Interaktionsbrett();
        startAufstellung();
        ib.willTasteninfo(this);
        gestartet = false;
    }

    public int getFPMS() {
        return FPMS;
    }

    private void startAufstellung() {
        gewonnen = false;
        firstHit = false;
        spielfeld = new Spielfeld();
        spielerLinks = new Spieler(spielfeld, spielfeld.getMARGIN() + 20, (int) (spielfeld.getHeight() * 0.45));
        spielerRechts = new Spieler(spielfeld, spielfeld.getWidth() + spielfeld.getMARGIN() - 20, (int) (spielfeld.getHeight() * 0.45));
        ball = new Ball(new Rectangle(spielerLinks.getX() + spielfeld.getWidth() / 80, spielerLinks.getY(), BALL_DIM, BALL_DIM), this);
        ib.neuesRechteck(spielerLinks, "spielerLinks", spielerLinks.getSchlaeger().getX(), spielerLinks.getSchlaeger().getY(), spielerLinks.getSchlaeger().getBreite(), spielerLinks.getSchlaeger().getHoehe());
        ib.neuesRechteck(spielerRechts, "spielerRechts", spielerRechts.getSchlaeger().getX(), spielerRechts.getSchlaeger().getY(), spielerRechts.getSchlaeger().getBreite(), spielerRechts.getSchlaeger().getHoehe());
        ib.neuesRechteck(ball, "Ball", ball.getX(), ball.getY(), ball.getBreite(), ball.getHoehe());
        spielfeld.darstellen(ib);
        ib.neuerText(spielfeld.getSpielflaeche().mitteInX() - spielfeld.getMARGIN() * 2, spielfeld.getMARGIN() * 2, Integer.toString(spielerLinks.getPunkte()));
        ib.neuerText(spielfeld.getSpielflaeche().mitteInX() + spielfeld.getMARGIN(), spielfeld.getMARGIN() * 2, Integer.toString(spielerRechts.getPunkte()));
        spielerLinks.getSchlaeger().darstellenFuellung(ib);
        spielerRechts.getSchlaeger().darstellenFuellung(ib);
        ball.getForm().darstellenFuellung(ib);
        detektor = new KollisionsDetektion(spielfeld, spielerLinks, spielerRechts);
        ib.textZeigen("PongSpiel");
    }

    public void spielen() {
        new Thread(() -> {
            Thread.currentThread().setPriority(1);
            long differenz;
            while (gewonnen == false) {
                long vorher = System.currentTimeMillis();
                ib.abwischen();
                spielfeld.darstellen(ib);
                ib.neuerText(spielfeld.getSpielflaeche().mitteInX() - spielfeld.getMARGIN() * 2, spielfeld.getMARGIN() * 2, Integer.toString(spielerLinks.getPunkte()));
                ib.neuerText(spielfeld.getSpielflaeche().mitteInX() + spielfeld.getMARGIN(), spielfeld.getMARGIN() * 2, Integer.toString(spielerRechts.getPunkte()));
                synchronized (spielerLinks) {
                    spielerLinks.getSchlaeger().darstellenFuellung(ib);
                }
                synchronized (spielerRechts) {
                    spielerRechts.getSchlaeger().darstellenFuellung(ib);
                }
                try {
                    ball.darstellen(ib);
                    pruefeBallKollision();
                    long nachKollisionsberechnung = System.currentTimeMillis();
                    long dif_kollision = nachKollisionsberechnung - vorher;
                    int frames = 0;
                    if (dif_kollision >= 17) {
                        frames = (int) (dif_kollision % FPMS);
                    }
                    ball.bewegen(1 + frames);

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

    private void pruefeBallKollision() throws InterruptedException {
        KollisionsDetektion.BallPosition pos = detektor.checkAusserhalbDesSpielfeldes(ball);
        if (detektor.checkBeruehrungBallMitSchlaeger(ball)) {
            if (firstHit) {
                ball.updateSpeed(+1);
            }
            Random r = new Random();
            int rdm = r.nextInt(2);
            if (rdm == 1) {
                ball.umkehrenDerBewegungInY();
            }
            ball.umkehrenDerBewegungInX();
        } else if (detektor.checkBeruehrungBallSpielfeldGrenzen(ball)) {
            ball.umkehrenDerBewegungInY();
        } else if (detektor.checkAusserhalbDesSpielfeldes(ball) != KollisionsDetektion.BallPosition.DRINNEN) {
            erhöhePunkte();
            Thread.sleep(500);
        }
    }

    public void erhöhePunkte() {
        if (ball.getForm().getX() < spielfeld.getSpielflaeche().mitteInX()) {
            if (spielerRechts.getPunkte() + 1 == MAX_PUNKTE) {
                spielerRechts.erhoehePunkte();
                ib.textZeigen("Gewonnen: Spieler Rechts");
                gewonnen = true;
            } else {
                spielerRechts.erhoehePunkte();
                Random r = new Random();
                int y_Spawn = r.nextInt(spielfeld.getSpielflaeche().getHoehe() - ball.getForm().getHoehe() * 2);
                ball.getForm().verschiebeNach(spielfeld.getSpielflaeche().mitteInX() + ball.getBreite(), y_Spawn);
                ball.umkehrenDerBewegungInX();
            }

        } else {
            if (spielerLinks.getPunkte() + 1 == MAX_PUNKTE) {
                spielerLinks.erhoehePunkte();
                ib.textZeigen("Gewonnen: Spieler Links");
                gewonnen = true;
            } else {
                spielerLinks.erhoehePunkte();
                Random r = new Random();
                int y_Spawn = r.nextInt(spielfeld.getSpielflaeche().getHoehe() - ball.getForm().getHoehe() * 2);
                ball.getForm().verschiebeNach(spielfeld.getSpielflaeche().mitteInX() - ball.getBreite(), y_Spawn);
                ball.umkehrenDerBewegungInX();
            }
        }
        firstHit = false;
    }

    public void tasteGedrueckt(String s) {
        if (s.equals("s") && gestartet == false) {
            System.out.println("Spiel gestartet");
            gestartet = true;
            spielen();
        }
        if (s.equals("s") && gewonnen == true) {
            startAufstellung();
            gewonnen = false;
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
