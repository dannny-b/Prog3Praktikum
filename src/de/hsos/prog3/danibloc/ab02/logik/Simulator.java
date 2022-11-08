package de.hsos.prog3.danibloc.ab02.logik;

import javax.lang.model.element.Element;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Simulator implements Simulation {
    BeiAenderung beiAenderung;
    private boolean[][] spielfeld;
    private int anzahlFelder;
    private int countKeineAenderung = 0;
     boolean nicht_mehr_aendern = false;

    /**
     * Die Implementierungen der Methoden
     * berechneAnfangsGeneration(…) und berechneFolgeGeneration(…) rufen sobald
     * eine neue Generation vorliegt, die aktualisiere-Methode der Instanzvariable beiAenderung
     * auf, falls diese nicht null ist.
     */

    @Override
    public void berechneAnfangsGeneration(int anzahlDerZellen, int wahrscheinlichkeitDerBesiedlung) {
        this.anzahlFelder = anzahlDerZellen;
        this.spielfeld = new boolean[anzahlDerZellen][anzahlDerZellen];
        Random rdm = new Random();

        for (int i = 0; i < spielfeld.length; i++) {
            for (int j = 0; j < spielfeld.length; j++) {
                this.spielfeld[i][j] = rdm.nextInt(100) < wahrscheinlichkeitDerBesiedlung;
            }
        }
        this.aktualisieren(this.spielfeld);
    }


    @Override
    public void berechneFolgeGeneration(int berechnungsschritte) {

        if (!nicht_mehr_aendern) {
            if (berechnungsschritte < 1) return;
            if (this.spielfeld == null) throw new IllegalStateException();

            boolean[][] neueGeneration = new boolean[this.anzahlFelder][this.anzahlFelder];

            for (int i = 0; i < this.anzahlFelder; i++) {
                for (int j = 0; j < this.anzahlFelder; j++) {
                    neueGeneration[i][j] = this.aktualisiereZelle(i, j);
                }
            }
            if (Arrays.deepEquals(neueGeneration, this.spielfeld)) {
                System.out.println("Keine Änderung");
                countKeineAenderung++;
                if (countKeineAenderung == 2) {
                    nicht_mehr_aendern = true;
                }
                return;
            }

            this.spielfeld = neueGeneration;
            this.aktualisieren(this.spielfeld);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Berechnugnsschrítte: " + berechnungsschritte);
            this.berechneFolgeGeneration(berechnungsschritte - 1);
        } else {
            System.out.println("Es wir nix mehr geändert!");
            this.aktualisieren(this.spielfeld);
            //this.berechneFolgeGeneration(berechnungsschritte - berechnungsschritte);
        }

    }

    @Override
    public void anmeldenFuerAktualisierungenBeiAenderung(BeiAenderung beiAenderung) {
        this.beiAenderung = beiAenderung;
    }

    private boolean aktualisiereZelle(int x, int y) {
        int nNachbarn = zaehleNachbarn(x, y);
        if (this.spielfeld[x][y]) {
            return nNachbarn == 2 || nNachbarn == 3;
        }
        return nNachbarn == 3;
    }


    private int zaehleNachbarn(int x, int y) {
        int summe = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int xMod = (x + i + spielfeld.length) % spielfeld.length;
                int yMod = (y + j + spielfeld.length) % spielfeld.length;

                if (spielfeld[xMod][yMod]) {
                    summe++;
                }
            }
        }

        if (spielfeld[x][y]) summe--;
        return summe;
    }

    private void aktualisieren(boolean[][] neueGeneration) {
        if (this.beiAenderung != null) {
            beiAenderung.aktualisiere(neueGeneration);
        }
    }
    public boolean getNichtMehrAendern() {
        return nicht_mehr_aendern;
    }
}

