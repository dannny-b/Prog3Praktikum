package de.hsos.prog3.danibloc.ab02.logik;

public interface Simulation {
    void berechneAnfangsGeneration(int anzahlDerZellen, int wahrscheinlichkeitDerBesiedlung);

    void berechneFolgeGeneration(int berechnungsschritte);

    void anmeldenFuerAktualisierungenBeiAenderung(BeiAenderung beiAenderung);

}
