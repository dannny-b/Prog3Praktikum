package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.PongSpiel;
import de.hsos.prog3.danibloc.ab04.logik.KonstanteBewegung;

public class Ball implements KonstanteBewegung {
    Rectangle form;
    int x, y;

    public int getBreite() {
        return breite;
    }

    public int getHoehe() {
        return hoehe;
    }
    public Rectangle getForm(){
        return this.form;
    }
    int breite,hoehe;
    int bewegungInXProFrame, getBewegungInYProFrame;


    public Ball(Rectangle form) {
        this.form = form;
        this.x = form.getX();
        this.y = form.getY();
        this.breite = form.getBreite();
        this.hoehe = form.getHoehe();



    }

    public void bewegen() {
        while(true){
            this.getForm().verschiebe(5,0);
        }
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

    public int getGetBewegungInYProFrame() {
        return getBewegungInYProFrame;
    }

}
