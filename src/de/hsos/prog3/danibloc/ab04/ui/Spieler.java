package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett.*;

public class Spieler {
    private Spielfeld spielfeld;
    private Rectangle schlaeger;
    private final int SPEED = 40;

    public int getPunkte() {
        return punkte;
    }

    private int punkte;
    private final int width, height;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x, y;

    public Spieler(Spielfeld spielfeld, int x, int y) {
        this.spielfeld = spielfeld;
        this.height = spielfeld.getHeight() / 10;
        this.width = spielfeld.getWidth() / 100;
        this.punkte = 0;
        this.schlaeger = new Rectangle(this.x = x, this.y = y, width, height);
    }


    public void aufwaerts() {
        // ToDO: grenzen akkurat implementieren
        if (schlaeger.getY() - SPEED < 0 || schlaeger.getY() - SPEED <= SPEED) {
            System.out.println("zu weit");
            schlaeger.verschiebeNach(schlaeger.getX(), spielfeld.getY());
        }
        schlaeger.verschiebe(0, -SPEED);
    }

    public void abwaerts() {
        if (schlaeger.getY() - SPEED > spielfeld.getHeight()) {
            System.out.println("zu weit");
            schlaeger.verschiebeNach(schlaeger.getX(), spielfeld.getY());
        }
        schlaeger.verschiebe(0, SPEED);
    }

    public Rectangle getSchlaeger() {
        return this.schlaeger;
    }

    public void erhoehePunkte() {
        this.punkte++;
        System.out.println("Punkt Spieler" + this.toString());
    }

    public void setzePunkteZurueck() {
        this.punkte = 0;
    }


}
