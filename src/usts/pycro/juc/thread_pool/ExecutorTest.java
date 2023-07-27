package usts.pycro.juc.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-23 11:13 AM
 */
public class ExecutorTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        System.out.println();
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            threadPool.execute(() -> {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("任务进行中：" + Thread.currentThread().getName());
                }
            });
        }
    }
}
