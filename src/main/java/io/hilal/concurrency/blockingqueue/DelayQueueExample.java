package io.hilal.concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {
    public static void main(String[] args) {
        // Raw types should be avoided; parameterise with <MyDelayed>
        BlockingQueue<MyDelayed> d = new DelayQueue<>();
        try {
            // Put an item with a 2-second delay
            d.put(new MyDelayed(1, 2000));
            System.out.println("Item added to queue. Waiting for expiration...");

            // This will block for exactly 2 seconds and then succeed
            System.out.println("Expired item retrieved: " + d.take());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MyDelayed implements Delayed {
        private final int num;
        private final long startTime; // Timestamp when the item becomes available

        public MyDelayed(int num, long delayInMilliseconds) {
            this.num = num;
            // Calculate exact future expiration timestamp
            this.startTime = System.currentTimeMillis() + delayInMilliseconds;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            // Calculate remaining time
            long diff = startTime - System.currentTimeMillis();
            // Convert and return in the requested TimeUnit
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this == o) return 0;

            // Correct way to compare elements: by their remaining delay
            long diff = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return (diff == 0) ? 0 : ((diff > 0) ? 1 : -1);
        }

        @Override
        public String toString() {
            return "MyDelayed{num=" + num + "}";
        }
    }
}
