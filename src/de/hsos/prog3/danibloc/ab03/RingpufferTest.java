package de.hsos.prog3.danibloc.ab03;

public class RingpufferTest {
    public static void main(String[] args) {
        Ringpuffer<Integer> puffer = new Ringpuffer<>(5, false, true);
        puffer.run();
        puffer.offer(1);
        puffer.offer(2);
        puffer.offer(3);
        puffer.offer(4);
        puffer.offer(5);
        puffer.run();
        System.out.println("poll : " + puffer.poll());
        puffer.run();
        puffer.offer(6);
        System.out.println("poll : " + puffer.poll());
        System.out.println("poll : " + puffer.poll());
        System.out.println("poll : " + puffer.poll());
        puffer.offer(7);
        puffer.offer(8);
        puffer.offer(9);
        puffer.run();
        puffer.offer(10);
        puffer.offer(11);
        puffer.offer(12);
        puffer.runArray();



    }
}
