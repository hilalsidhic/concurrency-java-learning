package io.hilal.concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> b = new LinkedBlockingQueue();
        try {
            b.put(21);
            b.put(21);
            b.put(21);
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static class MyLinkedBlockingQueue extends LinkedBlockingQueue{
        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public Object[] toArray() {
            return super.toArray();
        }
    }
}
