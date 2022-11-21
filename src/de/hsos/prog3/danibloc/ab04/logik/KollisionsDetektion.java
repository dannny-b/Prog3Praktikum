package de.hsos.prog3.danibloc.ab04.logik;

import de.hsos.prog3.danibloc.ab04.ui.Ball;
import de.hsos.prog3.danibloc.ab04.ui.Spieler;
import de.hsos.prog3.danibloc.ab04.ui.Spielfeld;

public class KollisionsDetektion {
    private Spielfeld spielfeld;
    private Spieler spielerLinks, spielerRechts;

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
        }

    public boolean checkBeruehrungBallSpielfeldGrenzen(Ball ball) {
        return spielfeld.getOben().intersects(ball.getForm()) || spielfeld.getUnten().intersects(ball.getForm());

    }

    public boolean checkBeruehrungBallMitSchlaeger(Ball ball) {
        return (spielerLinks.getSchlaeger().intersects(ball.getForm())) || (spielerRechts.getSchlaeger().intersects(ball.getForm()));
    }

    public BallPosition checkAusserhalbDesSpielfeldes(Ball ball) {
        if(spielfeld.getLinks().intersects(ball.getForm())){
            return BallPosition.DRAUSSEN_LINKS;
        }
        if(spielfeld.getRechts().intersects(ball.getForm())){
            return BallPosition.DRAUSSEN_RECHTS;
        }
        return BallPosition.DRINNEN;
    }

}
