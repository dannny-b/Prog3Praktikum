package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.PongSpiel;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

public class Ball {
    private Rectangle form;

    public int getSPEED_X() {
        return SPEED_X;
    }

    public void setSPEED_X(int SPEED_X) {
        bewegungInXProFrame = SPEED_X;
    }

    public int getSPEED_Y() {
        return SPEED_Y;
    }

    public void setSPEED_Y(int SPEED_Y) {
        bewegungInYProFrame = SPEED_Y;
    }

    private int SPEED_X = 2;
    private int SPEED_Y = 1;
    private PongSpiel spiel;
    private int x, y;


    int xRichtung, yRichtung;

    public int getBreite() {
        return breite;
    }

    public int getHoehe() {
        return hoehe;
    }

    public Rectangle getForm() {
        return this.form;
    }

    int breite, hoehe;

    int bewegungInXProFrame, bewegungInYProFrame;


    public Ball(Rectangle form, PongSpiel spiel) {
        this.form = form;
        this.x = form.getX();
        this.y = form.getY();
        this.breite = form.getBreite();
        this.hoehe = form.getHoehe();
        this.spiel = spiel;
        bewegungInXProFrame = SPEED_X;
        bewegungInYProFrame = SPEED_Y;
    }

    public void bewegen(int frames) throws InterruptedException {
        for (int i = 0; i < frames; i++) {
            this.form.verschiebe(bewegungInXProFrame, bewegungInYProFrame);
        }

    }

    public void darstellen(Interaktionsbrett ib) throws InterruptedException {
        form.darstellenFuellung(ib);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBewegungInXProFrame() {
        return bewegungInXProFrame;
    }
    public int getBewegungInYProFrame() {
        return bewegungInYProFrame;
    }

    public void umkehrenDerBewegungInX() {
        this.bewegungInXProFrame *= -1;
    }

    public void umkehrenDerBewegungInY() {
        this.bewegungInYProFrame *= -1;
    }
}
