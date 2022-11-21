package de.hsos.prog3.danibloc.ab04.ui;

public class Spieler {
    private Spielfeld spielfeld;
    private Rectangle schlaeger;
    private final int SPEED;

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
        this.SPEED = this.height;
        this.punkte = 0;
        this.schlaeger = new Rectangle(this.x = x, this.y = y, width, height);
    }


    public void aufwaerts() {
        if(schlaeger.getY() - SPEED <= spielfeld.getMARGIN()){
            schlaeger.verschiebeNach(this.getX(), spielfeld.getMARGIN());
        }else{
            schlaeger.verschiebe(0,-SPEED);
        }
    }

    public void abwaerts() {
        if(schlaeger.getY() + schlaeger.getHoehe() + SPEED >= spielfeld.getUnten().getY()){
            schlaeger.verschiebeNach(this.getX(), spielfeld.getUnten().getY() - schlaeger.getHoehe());
        }else{
            schlaeger.verschiebe(0,SPEED);
        }
    }

    public Rectangle getSchlaeger() {
        return this.schlaeger;
    }

    public void erhoehePunkte() {
        this.punkte++;
    }
}
