package de.hsos.prog3.danibloc.ab02.test;

import de.hsos.prog3.danibloc.ab02.ui.NutzerEingabe;
import de.hsos.prog3.danibloc.ab02.util.EinUndAusgabe;

public class NutzerEingabeTest {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Test NutzerEingabe");
        NutzerEingabe eingabe = new NutzerEingabe(new EinUndAusgabe());
        try {
            System.out.println("Spielfeld erstellt: Anzahl Felder:  "+eingabe.anzahlZellenDesSpielfelds());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Wahrscheinlichkeit: " + eingabe.wahrscheinlichkeitDerBesiedlung());
        System.out.println("Simulationsschritte: " + eingabe.anzahlDerSimulationsschritte());
    }
}
