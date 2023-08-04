package usts.pycro.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 3:12 PM
 */
public class CompletableFutureCombineDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t --- part 1");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t --- part 2");
            return 20;
        }), (x, y) -> {
            System.out.println(Thread.currentThread().getName() + "\t --- combine 1");
            return x + y;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t --- part 3");
            return 30;
        }), (x, y) -> {
            System.out.println(Thread.currentThread().getName() + "\t --- combine 2");
            return x + y;
        });
        System.out.println("结果：" + result.join());
    }

    private static void combine1() {
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        });
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 20;
        });
        CompletableFuture<Integer> result = completableFuture1.thenCombine(completableFuture2, (x, y) -> {
            System.out.println("---开始两个结果的合并");
            return x + y;
        });
        System.out.println(result.join());
    }
}
