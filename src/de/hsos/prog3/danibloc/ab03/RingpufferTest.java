package de.hsos.prog3.danibloc.ab03;

public class RingpufferTest {
    public static void main(String[] args) {
        Ringpuffer puffer = new Ringpuffer(4, false, false);
        puffer.add();
        System.out.println("Size: " + puffer.size());
    }
}
