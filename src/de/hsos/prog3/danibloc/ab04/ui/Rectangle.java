package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;


public class Rectangle {

    private Punkt bottomLeft, bottomRight;
    private Punkt topLeft, topRight;
    private int x, y;
    private int breite;
    private int hoehe;

    public Rectangle(int x, int y, int breite, int hoehe) {
        this.x = x;
        this.y = y;
        this.breite = breite;
        this.hoehe = hoehe;
        this.topLeft = new Punkt(this.x, this.y);
        this.topRight = new Punkt(this.x + this.breite, this.y);
        this.bottomLeft = new Punkt(this.x, this.y + this.hoehe);
        this.bottomRight = new Punkt(this.x + this.breite, this.y + this.hoehe);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBreite() {
        return breite;
    }

    public int getHoehe() {
        return hoehe;
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

    /**
     * src: java.awt.Rectangle
     */
    public boolean intersects(Rectangle r) {
        int tw = this.breite;
        int th = this.hoehe;
        int rw = r.breite;
        int rh = r.hoehe;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = this.x;
        int ty = this.y;
        int rx = r.x;
        int ry = r.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    public void darstellenFuellung(Interaktionsbrett ib) {
        for (int i = 0; i < this.breite; i++) {
            ib.neueLinie(this.x + i, this.y, this.x + i, this.y + this.hoehe);
        }
    }


}
