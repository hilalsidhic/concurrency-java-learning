
package io.hilal.concurrency.blockingqueue;

import java.util.concurrent.*;

public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ExecutorService e = Executors.newFixedThreadPool(2);
        e.execute(producer);
        e.execute(consumer);

        if(e != null){
            e.shutdown();
            try{
                e.awaitTermination(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException ex) {
                Thread.currentThread().isInterrupted();
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
