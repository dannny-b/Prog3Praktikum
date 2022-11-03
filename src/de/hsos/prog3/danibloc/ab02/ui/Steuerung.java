package de.hsos.prog3.danibloc.ab02.ui;

import de.hsos.prog3.danibloc.ab02.logik.BeiAenderung;
import de.hsos.prog3.danibloc.ab02.logik.Simulation;
import de.hsos.prog3.danibloc.ab02.logik.Simulator;
import de.hsos.prog3.danibloc.ab02.util.EinUndAusgabe;
import de.hsos.prog3.danibloc.ab02.util.Interaktionsbrett;

import java.util.Objects;

public class Steuerung implements BeiAenderung {
    private NutzerEingabe nutzerEingabe;
    private Simulator simulation;
    private SpielfeldDarstellung spielfeldDarstellung;

    public Steuerung(Simulation simulation) {
        this.simulation = (Simulator) Objects.requireNonNull(simulation);
        this.initialisierung();
    }

    @Override
    public void aktualisiere(boolean[][] neueGeneration) {
        this.spielfeldDarstellung.spielfeldDarstellen(neueGeneration);
    }

    public void startDesSpiels() {
        int nZellen = this.nutzerEingabe.anzahlZellenDesSpielfelds();
        int pBesiedlung = this.nutzerEingabe.wahrscheinlichkeitDerBesiedlung();
        this.simulation.berechneAnfangsGeneration(nZellen, pBesiedlung);

        int nSchritte;
        do{
            nSchritte = this.nutzerEingabe.anzahlDerSimulationsschritte();
            if(nSchritte < 0 ) {
                break;
            }
            this.simulation.berechneFolgeGeneration(nSchritte);
        } while (nSchritte >= 0);

        System.out.println("---------------------------- und tschuess!");
        this.spielfeldDarstellung.abwischen();
        System.exit(0);
    }

    private void initialisierung() {
        this.spielfeldDarstellung = new SpielfeldDarstellung(new Interaktionsbrett());
        this.nutzerEingabe = new NutzerEingabe(new EinUndAusgabe());
        this.simulation.anmeldenFuerAktualisierungenBeiAenderung(this);
    }

}
