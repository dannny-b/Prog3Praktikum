package de.hsos.prog3.danibloc.ab02.ui;

import de.hsos.prog3.danibloc.ab02.util.Interaktionsbrett;

public class SpielfeldDarstellung {
    private final int length = 1200;
    private int margin = 10;
    private Interaktionsbrett ib;

    public SpielfeldDarstellung(Interaktionsbrett ib) {
        this.ib = ib;
    }

    public void spielfeldDarstellen(boolean[][] spielfeld) {
        this.abwischen();
        int seitenlaenge = this.length / spielfeld.length;
        for (int i = 0; i < spielfeld.length; i++) {
            for (int j = 0; j < spielfeld.length; j++) {
                Quadrat zelle = new Quadrat(this.margin + (j * seitenlaenge), this.margin + (i * seitenlaenge), seitenlaenge);
                if (spielfeld[i][j]) {
                    zelle.darstellenFuellung(this.ib);
                } else {
                    zelle.darstellenRahmen(this.ib);
                }
            }
        }
    }

    public void abwischen() {
        ib.abwischen();
    }
}
