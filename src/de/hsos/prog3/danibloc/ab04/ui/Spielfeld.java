package de.hsos.prog3.danibloc.ab04.ui;


import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;

import java.awt.*;

public class Spielfeld {


    private final int MARGIN = 15;
    private final Dimension IB_DIM = new Dimension(800, 700);
    private final Dimension FELD_DIM = new Dimension(IB_DIM.width - MARGIN * 4, IB_DIM.height - MARGIN * 8);
    Rectangle spielflaeche;
    private Rectangle oben;
    private Rectangle unten;
    private int punkte_links;
    private int punkte_rechts;
    private Rectangle links;
    private Rectangle rechts;

    public Spielfeld() {

        spielflaeche = new Rectangle(MARGIN, MARGIN, FELD_DIM.width, FELD_DIM.height);
        oben = new Rectangle(MARGIN, 0, getWidth(), MARGIN);
        unten = new Rectangle(MARGIN, getHeight() + MARGIN, getWidth(), MARGIN);
        links = new Rectangle(0, MARGIN, MARGIN, getHeight());
        rechts = new Rectangle(getX() + getWidth() + MARGIN, MARGIN, MARGIN, getHeight());
        punkte_links = 0;
        punkte_rechts = 0;

    }

    public Rectangle getOben() {
        return oben;
    }

    public Rectangle getUnten() {
        return unten;
    }

    public Rectangle getLinks() {
        return links;
    }

    public Rectangle getRechts() {
        return rechts;
    }

    public Rectangle getSpielflaeche() {
        return spielflaeche;
    }

    public void darstellen(Interaktionsbrett ib) {
        ib.abwischen();
        ib.neuesRechteck(spielflaeche, "spielfeld", MARGIN, MARGIN, FELD_DIM.width, FELD_DIM.height);
        ib.neueLinie(spielflaeche.mitteInX(), spielflaeche.mitteInY(), spielflaeche.mitteInX(), spielflaeche.mitteInY() + FELD_DIM.height);

        /**
         *
         *  oben.darstellenFuellung(ib);
         *  unten.darstellenFuellung(ib);
         *  links.darstellenFuellung(ib);
         *  rechts.darstellenFuellung(ib);
         *
         */

    }

    public int getWidth() {
        return FELD_DIM.width;
    }

    public int getHeight() {
        return FELD_DIM.height;
    }

    public int getMARGIN() {
        return MARGIN;
    }

    public int getX() {
        return (int) (getWidth() - FELD_DIM.getWidth());
    }
}
