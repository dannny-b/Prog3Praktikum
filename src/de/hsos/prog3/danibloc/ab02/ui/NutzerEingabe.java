package de.hsos.prog3.danibloc.ab02.ui;

import de.hsos.prog3.danibloc.ab02.util.EinUndAusgabe;

public class NutzerEingabe {
    private final int maxFelder = 50;
    private final int maxSchritte = 25;
    private EinUndAusgabe io;

    public NutzerEingabe(EinUndAusgabe io) {
        this.io = io;
    }

    public int anzahlZellenDesSpielfelds() {
        int eingabe;

        io.ausgeben("Anzahl der Zellen (min: 3");
        eingabe = io.leseInteger();

        return eingabe;

    }

    public int wahrscheinlichkeitDerBesiedlung() {
        int eingabe;
        do {
            io.ausgeben("gültigen Wert zwischen 1 - 100 eingeben: ");
            eingabe = io.leseInteger();
        } while (eingabe < 1 || eingabe > 100);
        return eingabe;
    }

    public int anzahlDerSimulationsschritte() {
        int eingabe;
            io.ausgeben("Wert zwischen 1 - " + maxSchritte + " eingeben: ");
            eingabe = io.leseInteger();
        return eingabe;
    }


}
