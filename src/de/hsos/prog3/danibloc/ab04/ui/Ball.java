package de.hsos.prog3.danibloc.ab04.ui;

import de.hsos.prog3.danibloc.ab04.PongSpiel;

public class Ball {
    Rectangle form;
    PongSpiel spiel;
    int x, y;

    public int getxRichtung() {
        return xRichtung;
    }

    public void setxRichtung(int xRichtung) {
        this.xRichtung = xRichtung;
    }

    public int getyRichtung() {
        return yRichtung;
    }

    public void setyRichtung(int yRichtung) {
        this.yRichtung = yRichtung;
    }

    int xRichtung, yRichtung;

    public int getBreite() {
        return breite;
    }

    public int getHoehe() {
        return hoehe;
    }

    public Rectangle getForm() {
        return this.form;
    }

    int breite, hoehe;
    int bewegungInXProFrame, getBewegungInYProFrame;


    public Ball(Rectangle form, PongSpiel spiel) {
        this.form = form;
        this.x = form.getX();
        this.y = form.getY();
        this.breite = form.getBreite();
        this.hoehe = form.getHoehe();
        this.spiel = spiel;
        setxRichtung(+5);
        setyRichtung(0);
    }

    public void bewegen(int frames) throws InterruptedException {
        for (int i = 0; i < frames; i++) {
            long vorher = System.currentTimeMillis();
            ueberlappt();
            if(this.form.ueberschneidet(spiel.getSpielfeld().spielflaeche)){
                System.out.println("Spielfeld true");
            }
            this.form.verschiebe(xRichtung, yRichtung);
            if (form.getX() <= spiel.getSpielfeld().getMARGIN()) {
                setxRichtung(+1);
                spiel.getSpielerRechts().erhoehePunkte();
            }
            if (form.getX() >= spiel.getSpielfeld().getWidth()) {
                setxRichtung(-1);
                spiel.getSpielerLinks().erhoehePunkte();
            }
            if (form.getY() <= spiel.getSpielfeld().getMARGIN()) {
                setyRichtung(+1);
            }
            if (form.getY() >= spiel.getSpielfeld().getHeight()) {
                setyRichtung(+1);
            }
            if (frames != 1) {
                long nacher = System.currentTimeMillis();
                Thread.sleep(spiel.getFPMS() - (nacher - vorher));
            }
        }
    }

    public void ueberlappt() {
        if (spiel.getSpielerLinks().getSchlaeger().ueberschneidet(form)) {
            System.out.println("überlappt");
            setxRichtung(+1);
        }
        if (form.ueberschneidet(spiel.getSpielerRechts().getSchlaeger())) {
            System.out.println("überlappt");
            setxRichtung(-1);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBewegungInXProFrame() {
        return bewegungInXProFrame;
    }

    public int getGetBewegungInYProFrame() {
        return getBewegungInYProFrame;
    }

}
