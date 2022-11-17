package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

import java.awt.*;

public class Rectangle {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x, y;
    private int breite;
    private int hoehe;

    public Rectangle(int x, int y, int breite, int hoehe) {
        this.x = x;
        this.y = y;
        this.breite = breite;
        this.hoehe = hoehe;
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public int getHoehe() {
        return hoehe;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public int oben() {
        return this.y;
    }

    public int unten() {
        return oben() + hoehe;
    }

    public int links() {
        return this.x;
    }

    public int rechts() {
        return links() + breite;
    }

    public int mitteInY() {
        return y;
    }

    public int mitteInX() {
        return (x + breite) / 2;
    }

    public void verschiebe(int dx, int dy) {
        this.x += dx;
        this.y += dy;

    }

    public void verschiebeNach(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean ueberschneidet(Interaktionsbrett.Rechteck other) {
        if (this.oben() < other.unten() || this.unten() > other.oben()) {
            return false;
        }

        if (this.rechts() < other.links() || this.links() > other.rechts()) {
            return false;
        }
        return true;
    }


    public void darstellenFuellung(Interaktionsbrett ib) {
        for (int i = 0; i < this.breite; i++) {
            ib.neueLinie(this.x + i, this.y, this.x + i, this.y + this.hoehe);
        }
    }


}
