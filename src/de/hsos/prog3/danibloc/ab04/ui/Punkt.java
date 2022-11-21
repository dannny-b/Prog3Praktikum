package de.hsos.prog3.danibloc.ab04.ui;

public class Punkt {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Punkt(int x, int y) {
        setX(x);
        setY(y);
    }
}
