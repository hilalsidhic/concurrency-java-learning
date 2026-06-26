package io.hilal.concurrency.storage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        ExecutorService e = Executors.newFixedThreadPool(2);
        Producer producer = new Producer(map);
        Consumer consumer = new Consumer(map);
        try{
            e.execute(producer);
            Thread.sleep(3); // Wait for producer to produce some items
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
        ConcurrentHashMap<Integer, String> map;
        public Producer(ConcurrentHashMap<Integer, String> map){
            this.map = map;
        }
        public void run(){
            for(int i=0; i<20; i++){
                map.put(i, "Value " + i);
                System.out.println("Produced: " + i);
            }
        }
    }
    public static class Consumer implements Runnable{
        ConcurrentHashMap<Integer, String> map;
        public Consumer(ConcurrentHashMap<Integer, String> map){
            this.map = map;
        }
        public void run(){
            for(int i=0; i<20; i++){
                String value = map.get(i);
                System.out.println("Consumed: " + value);
            }
        }
    }
}
