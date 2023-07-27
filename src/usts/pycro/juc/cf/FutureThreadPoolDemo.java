package usts.pycro.juc.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-26 4:48 PM
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 3个任务，多个线程异步执行
        FutureTask<String> futureTask1 = new FutureTask<>(()->{
           try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
           return "task1 over";
        });

        FutureTask<String> futureTask2 = new FutureTask<>(()->{
            try {TimeUnit.MILLISECONDS.sleep(400);} catch (InterruptedException e) {throw new RuntimeException(e);}
            return "task2 over";
        });
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();
        threadPool.submit(futureTask1);
        threadPool.submit(futureTask2);

        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + " ms");
        System.out.println(Thread.currentThread().getName() + " ---end--- ");
        threadPool.shutdown();

    }

    private static void m1() {
        // 3个任务，1个线程处理
        long startTime = System.currentTimeMillis();
        try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
        try {TimeUnit.MILLISECONDS.sleep(400);} catch (InterruptedException e) {throw new RuntimeException(e);}
        long endTime = System.currentTimeMillis();
        System.out.println("cost time：" + (endTime - startTime) + " ms");
        System.out.println(Thread.currentThread().getName()+" ---end--- ");
    }
}
