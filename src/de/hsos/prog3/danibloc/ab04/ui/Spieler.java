package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett.*;
public class Spieler {
    private Spielfeld spielfeld;
    private Rectangle schlaeger;
    private final int SPEED = 25;
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


    public void aufwaerts(){
        // ToDO: grenzen akkurat implementieren
        if(schlaeger.getY()-SPEED<0 || schlaeger.getY() - SPEED <= SPEED){
            System.out.println("zu weit");
            schlaeger.verschiebeNach(schlaeger.getX(), spielfeld.getY());
        }
        schlaeger.verschiebe(0,-20);
    }
    public void abwaerts(){schlaeger.verschiebe(0,20);}

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
