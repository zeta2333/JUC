package usts.pycro.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 2:36 PM
 */
public class CompletableFutureWithThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        try {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                // try {
                //     TimeUnit.MILLISECONDS.sleep(20);
                // } catch (InterruptedException e) {
                //     throw new RuntimeException(e);
                // }
                System.out.println("1号任务\t" + Thread.currentThread().getName());
                return "abcd";
            }, threadPool).thenRunAsync(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("2号任务\t" + Thread.currentThread().getName());
            }).thenRunAsync(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("3号任务\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("4号任务\t" + Thread.currentThread().getName());
            });
            System.out.println(future.get(2, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }
}
