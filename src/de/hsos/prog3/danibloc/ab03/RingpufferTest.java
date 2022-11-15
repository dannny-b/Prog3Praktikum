package de.hsos.prog3.danibloc.ab03;


/**
 *
 * In Zusammenarbeit mit Luca S und Sergej S (Gruppe 2)
 *
 **/

public class RingpufferTest {

    public static void main(String[] args) {
        trueTure();
        falseFalse();
    }

    public static void trueTure() {
        Ringpuffer<Integer> Buffer = new Ringpuffer<>(4, true, true);
        Ringpuffer<Integer> Buffer2 = new Ringpuffer<>(4, true, true);
        test(Buffer, Buffer2);
    }

    public static void falseFalse() {
        Ringpuffer<Integer> Buffer = new Ringpuffer<>(4, false, false);
        Ringpuffer<Integer> Buffer2 = new Ringpuffer<>(4, false, false);
        test(Buffer, Buffer2);
    }

    public static void test(Ringpuffer<Integer> Buffer, Ringpuffer<Integer> Buffer2) {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            try {
                Buffer.add(count++);
            } catch (IllegalStateException e) {
                System.out.println(e);
            }
        }
        count = 0;
        for (int i = 10; i < 20; i++) {
            try {
                Buffer2.add(count++);
            } catch (IllegalStateException e) {
                System.out.println(e);
            }
        }

        System.out.println("Iterator Buffer print:");
        Buffer.forEach(System.out::println);

        System.out.println("addAll to Buffer2");
        Buffer2.addAll(Buffer);
        System.out.println("Buffer2 containsAll from Buffer: " + Buffer2.containsAll(Buffer));
        System.out.println("Iterator Buffer2 print:");
        Buffer2.forEach(System.out::println);
        Buffer2.remove();
        System.out.println("Remove");
        System.out.println("Iterator Buffer2 print:");
        Buffer2.forEach(System.out::println);
        System.out.println("Does Buffer2 contain 3: " + Buffer2.contains(3));
        System.out.println("Does Buffer2 contain 0: " + Buffer2.contains(0));
        System.out.println("Does Buffer contain 3: " + Buffer.contains(3));
        System.out.println("Does Buffer contain 0: " + Buffer.contains(0));
        System.out.println("Offer 2");
        Buffer2.offer(2);
        System.out.println("Iterator Buffer2 print:");
        Buffer2.forEach(System.out::println);

        System.out.println("peek: " + Buffer2.peek());
    }
}