package de.hsos.prog3.danibloc.ab04;



public class Spielfeld extends Interaktionsbrett {

    int breite, hoehe;
    private final int margin = 15;
    Rechteck spielflaeche;
    public Spielfeld(int breite, int hoehe){
        spielflaeche = new Rechteck(margin, margin, breite, hoehe);
        this.breite = breite; this.hoehe = hoehe;
    }

    public void darstellen(Interaktionsbrett ib){
        ib.abwischen();
        ib.neuesRechteck(spielflaeche, "spielfeld", margin, margin, breite, hoehe);
        ib.neueLinie(spielflaeche.mitteInX(), spielflaeche.mitteInY(), spielflaeche.mitteInX()+spielflaeche.getHoehe(), spielflaeche.mitteInY()+ spielflaeche.getHoehe());
    }
}
