package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

import java.awt.*;

public class Rectangle {

    private Punkt bottomLeft,bottomRight;
    private Punkt topLeft, topRight;

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
        this.topLeft = new Punkt(this.x,this.y);
        this.topRight = new Punkt(this.x+this.breite,this.y);
        this.bottomLeft = new Punkt(this.x,this.y+this.hoehe);
        this.bottomRight = new Punkt(this.x+this.breite,this.y+this.hoehe);
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

    public boolean ueberschneidet(Rectangle other) {
        System.out.println("uerberpruefe");
     if(this.topRight.getY() < other.bottomLeft.getY()||this.bottomLeft.getY() > other.topRight.getY()){
         System.out.println("false 1");
         return false;
     }
     if(this.topRight.getX() < other.bottomLeft.getX() || this.bottomLeft.getX() > other.topRight.getX()){
         System.out.println("false 2");
         return false;
     }
        System.out.println("true");
     return true;
    }


    public void darstellenFuellung(Interaktionsbrett ib) {
        for (int i = 0; i < this.breite; i++) {
            ib.neueLinie(this.x + i, this.y, this.x + i, this.y + this.hoehe);
        }
    }


}
