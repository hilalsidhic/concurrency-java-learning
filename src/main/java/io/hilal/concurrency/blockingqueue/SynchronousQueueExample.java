package io.hilal.concurrency.blockingqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {
    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        ExecutorService e = java.util.concurrent.Executors.newFixedThreadPool(2);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        try{
            e.execute(producer);
            e.execute(consumer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(e != null){
            e.shutdown();
            try{
                e.awaitTermination(5000, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static class Producer implements Runnable{
        SynchronousQueue<Integer> queue;
        public Producer(SynchronousQueue<Integer> queue){
            this.queue = queue;
        }
        public void run(){
            try{
                queue.put(1);
                Thread.sleep(2000);
                queue.put(2);
                Thread.sleep(2000);
                queue.put(3);
            }
            catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
    public static class Consumer implements Runnable {
        SynchronousQueue<Integer> queue;

        public Consumer(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                System.out.println(queue.take());
                System.out.println(queue.take());
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
