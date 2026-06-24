package io.hilal.concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);

        ExecutorService e = Executors.newFixedThreadPool(2);
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
        BlockingQueue<Integer> queue;
        public Producer(BlockingQueue<Integer> queue){
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
    public static class Consumer implements Runnable{
        BlockingQueue<Integer> queue;
        public Consumer(BlockingQueue<Integer> queue){
            this.queue = queue;
        }
        public void run(){
            try{
                System.out.println(queue.take());
                System.out.println(queue.take());
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
