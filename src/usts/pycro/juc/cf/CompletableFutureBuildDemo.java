package usts.pycro.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 10:26 AM
 */
public class CompletableFutureBuildDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 使用自定义的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        /* CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName());
            // 程序暂停运行
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
        },threadPool);
        System.out.println("结果："+future.get()); */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            // 程序暂停运行
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {throw new RuntimeException(e);}
            return "hello supplyAsync";
        },threadPool);
        System.out.println("结果："+future.get());
        threadPool.shutdown();
    }
}
