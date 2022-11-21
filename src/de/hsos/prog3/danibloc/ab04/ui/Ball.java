package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.PongSpiel;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

public class Ball {
    private final int MAX_SPEED = 6;
    private int breite, hoehe;
    private int bewegungInXProFrame, bewegungInYProFrame;
    private Rectangle form;
    private PongSpiel spiel;
    private int x, y;

    public Ball(Rectangle form, PongSpiel spiel) {
        this.form = form;
        this.x = form.getX();
        this.y = form.getY();
        this.breite = form.getBreite();
        this.hoehe = form.getHoehe();
        this.spiel = spiel;
        bewegungInXProFrame = 4;
        bewegungInYProFrame = 1;
    }

    public int getBreite() {
        return breite;
    }

    public int getHoehe() {
        return hoehe;
    }

    public Rectangle getForm() {
        return this.form;
    }

    public void bewegen(int frames) throws InterruptedException {
        for (int i = 0; i < frames; i++) {
            this.form.verschiebe(bewegungInXProFrame, bewegungInYProFrame);
        }

    }

    public void darstellen(Interaktionsbrett ib) throws InterruptedException {
        form.darstellenFuellung(ib);
    }

    public void updateSpeed(int value) {
        switch (value) {
            case -1:
                if (bewegungInXProFrame >= 2) {
                    bewegungInXProFrame--;
                }
                break;
            case 1:
                if (bewegungInXProFrame > 0 && bewegungInXProFrame < MAX_SPEED) {
                    bewegungInXProFrame++;
                }
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void umkehrenDerBewegungInX() {
        this.bewegungInXProFrame *= -1;
    }

    public void umkehrenDerBewegungInY() {
        this.bewegungInYProFrame *= -1;
    }
}
