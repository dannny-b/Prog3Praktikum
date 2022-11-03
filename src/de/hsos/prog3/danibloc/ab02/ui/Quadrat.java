package de.hsos.prog3.danibloc.ab02.ui;

import de.hsos.prog3.danibloc.ab02.util.Interaktionsbrett;

public class Quadrat {
    private int x;
    private int y;
    private int seitenlaenge;

    public Quadrat(int x, int y, int seitenlaenge) {
        setX(x);
        setY(y);
        setSeitenlaenge(seitenlaenge);
    }

    public void darstellenRahmen(Interaktionsbrett ib) {
        ib.neuesRechteck(this.x, this.y, this.seitenlaenge, this.seitenlaenge);
    }

    public void darstellenFuellung(Interaktionsbrett ib) {
        this.darstellenRahmen(ib);

        for (int i = 2; i < this.seitenlaenge - 1; i++) {
            ib.neueLinie(this.x + i, this.y + 2, this.x + i, this.y + this.seitenlaenge - 2);
        }
    }

    public void setSeitenlaenge(int seitenlaenge) {
        if (seitenlaenge > 0) {
            this.seitenlaenge = seitenlaenge;
        } else setSeitenlaenge(10);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x >= 0) {
            this.x = x;
        } else setX(0);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y >= 0) {
            this.y = y;
        } else setY(0);
    }
}
