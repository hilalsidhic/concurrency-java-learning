# BlockingQueue — Producer/Consumer

A `BlockingQueue` is a thread-safe queue designed for the Producer-Consumer pattern. Unlike standard queues, it blocks producer or consumer threads when the queue is full or empty respectively, removing the need for manual wait/notify logic.

## Behavior
- Producer: if the queue is full, `put(E e)` blocks until space is available.
- Consumer: if the queue is empty, `take()` blocks until an element is available.

## Key methods
- `put(E e)` — Inserts the element, waiting if necessary for space.
- `take()` — Retrieves and removes the head, waiting if necessary until an element is available.

## Example (Java)
    import java.util.concurrent.BlockingQueue;
    import java.util.concurrent.ArrayBlockingQueue;

    BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    // Producer
    queue.put("item"); // blocks if queue is full

    // Consumer
    String item = queue.take(); // blocks if queue is empty

1. ArrayBlockingQueue
   This is the most straightforward implementation to understand.

Under the Hood: It is backed by a standard Java array.

Bounded: It is strictly "bounded." You must specify the capacity when you create it (e.g., new ArrayBlockingQueue<>(10)). Once created, its capacity cannot be changed.

Order: It enforces FIFO (First-In-First-Out). The head of the queue is the oldest element; the tail is the newest.

Memory Efficiency: Because it uses a pre-allocated array, it creates fewer objects during runtime compared to node-based queues (like LinkedBlockingQueue), which means less work for the Garbage Collector.

Locking mechanism: It typically uses a single lock for the entire queue. This means a producer putting an item and a consumer taking an item cannot happen at the exact same millisecond—they must take turns holding the lock.