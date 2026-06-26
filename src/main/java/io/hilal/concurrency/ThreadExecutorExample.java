package io.hilal.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running a 2 thread executable thread");
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running a 2 thread executable thread");
            }
        });
        executor.shutdown();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();

        Future future = executor2.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("This is the end");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get();
                    System.out.println("thread ended executor2 done with runnable");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    executor2.shutdown();
                }
            }
        }).start();


        ExecutorService executor3 = Executors.newFixedThreadPool(2);
        
        Future future1 = executor3.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "Masha Allah";
            }
        });

        try {
            System.out.println(future1.get());
            System.out.println("Reached callable end");
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            executor3.shutdown();
        }
    }
}
