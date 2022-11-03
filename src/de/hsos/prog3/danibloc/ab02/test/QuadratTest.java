package de.hsos.prog3.danibloc.ab02.test;

import de.hsos.prog3.danibloc.ab02.ui.Quadrat;
import de.hsos.prog3.danibloc.ab02.util.Interaktionsbrett;

public class QuadratTest {
    public static void main(String[] args) throws InterruptedException {
        Quadrat[][] quadrats = new Quadrat[10][10];
        Interaktionsbrett brett = new Interaktionsbrett();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Quadrat q = new Quadrat((5 + i * 20), (5 + j * 20), 20);
                q.darstellenRahmen(brett);
                quadrats[i][j]=q;
                Thread.sleep(20);
            }
        }
        Thread.sleep(200);

        //Fuellen
        for (Quadrat[] x : quadrats){
            for ( Quadrat y : x){
                for (int i=0; i<20;i++){
                    brett.neueLinie(y.getX(),y.getY()+i,y.getX()+20, y.getY()+i);
                    Thread.sleep(5);
                }
            }
        }
    }
}
