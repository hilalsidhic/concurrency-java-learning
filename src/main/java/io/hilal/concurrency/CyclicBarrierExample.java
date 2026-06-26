package io.hilal.concurrency;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("r1 is here");
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("r2 is here");
            }
        };
        CyclicBarrier c1 = new CyclicBarrier(2,r1);
        CyclicBarrier c2 = new CyclicBarrier(2,r2);

        BarrierExample br1 = new BarrierExample(c1,c2);
        BarrierExample br2 = new BarrierExample(c1,c2);

        new Thread(br1).start();

        new Thread(br2).start();
    }

    public static class BarrierExample implements Runnable{
        CyclicBarrier barrier1;
        CyclicBarrier barrier2;
        public BarrierExample(CyclicBarrier c1, CyclicBarrier c2) {
            this.barrier1 = c1;
            this.barrier2 = c2;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 1");
                this.barrier1.await();

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 2");
                this.barrier2.await();

                System.out.println(Thread.currentThread().getName() +
                        " done!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
