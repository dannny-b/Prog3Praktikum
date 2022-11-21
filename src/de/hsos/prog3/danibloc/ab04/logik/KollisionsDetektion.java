package de.hsos.prog3.danibloc.ab04.logik;

import de.hsos.prog3.danibloc.ab02.util.Interaktionsbrett;
import de.hsos.prog3.danibloc.ab04.ui.Ball;
import de.hsos.prog3.danibloc.ab04.ui.Rectangle;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;

public class KollisionsDetektion {
    private Spielfeld spielfeld;
    private Spieler spielerLinks, spielerRechts;

    private Rectangle oben, unten, links, rechts;

    public enum BallPosition {DRINNEN, DRAUSSEN_LINKS, DRAUSSEN_RECHTS}

    public KollisionsDetektion(Spielfeld spielfeld, Spieler spielerLinks, Spieler spielerRechts) {
        if ((this.spielfeld = spielfeld) == null) {
            throw new IllegalArgumentException();
        }


        if ((this.spielerLinks = spielerLinks) == null) {
            throw new IllegalArgumentException();
        }


        if ((this.spielerRechts = spielerRechts) == null) {
            throw new IllegalArgumentException();
        }
        oben = new Rectangle(spielfeld.getMARGIN(),0,spielfeld.getWidth(),spielfeld.getMARGIN());
        unten = new Rectangle(spielfeld.getMARGIN(), spielfeld.getHeight()+spielfeld.getMARGIN(),spielfeld.getWidth(),spielfeld.getMARGIN());

    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
    }

    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

    public Spieler getSpielerLinks() {
        return spielerLinks;
    }

    public void setSpielerLinks(Spieler spielerLinks) {
        this.spielerLinks = spielerLinks;
    }

    public Spieler getSpielerRechts() {
        return spielerRechts;
    }

    public void setSpielerRechts(Spieler spielerRechts) {
        this.spielerRechts = spielerRechts;
    }


    public boolean checkBeruehrungBallSpielfeldGrenzen(Ball ball) {
            // oben
        if(oben.intersects(ball.getForm())|| unten.intersects(ball.getForm())){
            System.out.println("true");
            return true;
        }
        return false;

    }

    public boolean checkBeruehrungBallMitSchlaeger(Ball ball) {
        if ((spielerLinks.getSchlaeger().intersects(ball.getForm())) || (spielerRechts.getSchlaeger().intersects(ball.getForm()))) {

            return true;
        }
        return false;
    }

    public BallPosition checkAusserhalbDesSpielfeldes(Ball ball) {
        return null;
    }


}
