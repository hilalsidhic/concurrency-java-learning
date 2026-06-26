package io.hilal.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {
    public static void main(String[] args) {
        CountDownLatch CL = new CountDownLatch(2);
        Booker booker = new Booker(CL);
        Downer downer = new Downer(CL);

        ExecutorService e = Executors.newFixedThreadPool(2);

        Thread t = new Thread(booker);
        t.start();
        try {
            e.execute(booker);
            e.execute(downer);
            t.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (e != null) {
            e.shutdown();
            try {
                e.awaitTermination(5000, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static class Booker implements Runnable{
        CountDownLatch cl;

        public Booker(CountDownLatch cl) {
            this.cl = cl;
        }

        @Override
        public void run() {
            try {
                cl.await();
                System.out.println("Waiter released");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static class Downer implements Runnable{
        CountDownLatch cl;

        public Downer(CountDownLatch cl) {
            this.cl = cl;
        }

        @Override
        public void run() {
            try{
                for(int i = 0;i<3;i++){
                    Thread.sleep(1000*i);
                    System.out.println(i);
                    cl.countDown();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
