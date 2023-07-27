package usts.pycro.juc.thread_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-23 1:06 PM
 */
public class CustomizeThreadPool {
    public static void main(String[] args) {
        // 自定义线程池
        ExecutorService threadPool = new ThreadPoolExecutor(
                2, 5,
                2, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(),
                (r, executor) -> System.out.println("自定义拒绝策略")
        );
        try {
            for (int i = 0; i < 9; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行了业务逻辑");
                });
            }
        } finally {
            threadPool.shutdown();
        }

    }
}
