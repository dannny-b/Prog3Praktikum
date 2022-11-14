package de.hsos.prog3.danibloc.ab03;

import de.hsos.prog3.danibloc.irrelevant.Ringpuffer;

public class RingpufferTest {
    public static void main(String[] args) {
        CircularBuffer<Integer> puffer = new CircularBuffer<>(5, false, false);
        puffer.showBuffer();
        puffer.offer(1);
        puffer.offer(2);
        puffer.offer(3);
        puffer.offer(4);
        puffer.offer(5);
        puffer.showBuffer();
        System.out.println("poll : " + puffer.poll());
        System.out.println("poll : " + puffer.poll());
        System.out.println("poll : " + puffer.poll());
        System.out.println("poll : " + puffer.poll());
        puffer.showBuffer();
        puffer.offer(6);
        puffer.offer(7);
        puffer.offer(8);
        puffer.offer(9);
        puffer.showBuffer();
        puffer.offer(10);
        puffer.offer(11);
        puffer.offer(12);
        puffer.showBuffer();
    }
}
