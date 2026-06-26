package io.hilal.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicUtils {
    private static Integer a = 0;
    private static AtomicInteger b = new AtomicInteger(0);


    public static void main(String[] args) {

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    a++;
                    b.incrementAndGet();
                }
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    a++;
                    b.incrementAndGet();
                }
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Integer value : "+a);
        System.out.println("Atomic Integer value : "+b);

        new Thread(new Runnable() {
            @Override
            public void run() {

                AtomicStampedReference<String> asr = new AtomicStampedReference<>("A", 0);
                String expectedRef = "A";
                int expectedStamp = 0;

                // Simulate ABA: A -> B -> A (stamp updates to 2)
                asr.compareAndSet("A", "B", 0, 1);
                asr.compareAndSet("B", "A", 1, 2);

                // Fails because stamp 0 != 2
                boolean success = asr.compareAndSet(expectedRef, "New", expectedStamp, 3);
                System.out.println("Update successful? " + success); // Output: false
            }
        }).start();
    }
}
