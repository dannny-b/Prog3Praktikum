package de.hsos.prog3.danibloc.ab02.test;

import de.hsos.prog3.danibloc.ab02.ui.SpielfeldDarstellung;
import de.hsos.prog3.danibloc.ab02.util.Interaktionsbrett;

public class SpielfeldDarstellungTest {
    public static void main(String[] args) throws InterruptedException {
        SpielfeldDarstellung spielfeldDarstellung = new SpielfeldDarstellung(new Interaktionsbrett());
        spielfeldDarstellung.spielfeldDarstellen(new boolean[20][20]);
        System.out.println((int)Math.pow(50,2.0));

    }
}
