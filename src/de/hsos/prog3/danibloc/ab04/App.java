package de.hsos.prog3.danibloc.ab04;

public class App {
    public static void main(String[] args) {
        Interaktionsbrett brett = new Interaktionsbrett();
        Spielfeld spielfeld = new Spielfeld(800,700);
        spielfeld.darstellen(brett);
    }
}
