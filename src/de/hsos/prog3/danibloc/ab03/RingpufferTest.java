package de.hsos.prog3.danibloc.ab03;

public class RingpufferTest {
    public static void main(String[] args) {
        Ringpuffer<Integer> puffer = new Ringpuffer(2, false, false);
        puffer.add(1);
        puffer.add(2);
        puffer.add(3);
        puffer.add(4);
        puffer.add(5);
        puffer.add(6);
        System.out.println("Size: " + puffer.size());
        System.out.println("Capacity: " + puffer.getCapacity());
        puffer.showPuffer();
    }
}
