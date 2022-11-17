package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett.*;
public class Spieler {
    private Spielfeld spielfeld;
    private Rectangle schlaeger;
    private int punkte;
    private final int width, height;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private  int x,y;
    public Spieler(Spielfeld spielfeld, int x, int y){
        this.spielfeld = spielfeld;
        this.height = spielfeld.getHeight()/10;
        this.width = spielfeld.getWidth()/100;

        this.schlaeger = new Rectangle(this.x=x,this.y=y,width,height);
    }


    public void aufwaerts(){schlaeger.verschiebe(this.x,this.y-10);}
    public void abwaerts(){schlaeger.verschiebe(this.x,this.y+10);}

    public Rectangle getSchlaeger() {
        return this.schlaeger;
    }

    public void erhoehePunkte(){
        this.punkte++;
    }

    public void setzePunkteZurueck(){
        this.punkte = 0;
    }



}
