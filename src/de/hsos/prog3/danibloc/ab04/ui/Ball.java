package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.PongSpiel;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

public class Ball {
    Rectangle form;
    PongSpiel spiel;
    int x, y;

    public int getxRichtung() {
        return xRichtung;
    }

    public void setxRichtung(int xRichtung) {
        this.xRichtung = xRichtung;
    }

    public int getyRichtung() {
        return yRichtung;
    }

    public void setyRichtung(int yRichtung) {
        this.yRichtung = yRichtung;
    }

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
        bewegungInXProFrame = 2;
        bewegungInYProFrame = 0;
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
