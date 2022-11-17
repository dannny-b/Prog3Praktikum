package de.hsos.prog3.danibloc.ab04.ui;


import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett;
import de.hsos.prog3.danibloc.ab04.util.Interaktionsbrett.*;

import java.awt.*;

public class Spielfeld {

    private final int MARGIN = 15;
    private final Dimension IB_DIM = new Dimension(800, 700);
    private final Dimension FELD_DIM= new Dimension(IB_DIM.width - MARGIN * 4, IB_DIM.height - MARGIN * 8);

   Rectangle spielflaeche;

    public Spielfeld() {
        spielflaeche = new Rectangle(MARGIN, MARGIN, FELD_DIM.width, FELD_DIM.height);
    }

    public void darstellen(Interaktionsbrett ib) {
        ib.abwischen();
        ib.neuesRechteck(spielflaeche, "spielfeld", MARGIN, MARGIN, FELD_DIM.width, FELD_DIM.height);
        ib.neueLinie(spielflaeche.mitteInX(), spielflaeche.mitteInY(),spielflaeche.mitteInX(), spielflaeche.mitteInY()+ FELD_DIM.height);
    }

    public int getWidth(){
        return FELD_DIM.width;
    }

    public int getHeight(){
        return FELD_DIM.height;
    }

    public int getHeightIB(){
        return IB_DIM.height;
    }

    public int getWidthIB(){
        return IB_DIM.width;
    }

    public int getMARGIN(){
        return MARGIN;
    }
}
