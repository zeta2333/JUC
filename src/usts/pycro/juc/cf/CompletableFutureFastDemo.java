package usts.pycro.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 2:59 PM
 */
public class CompletableFutureFastDemo {
    public static void main(String[] args) {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println("A come in");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "playerA";
        });
        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println("B come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "playerB";
        });

        CompletableFuture<String> result = playA.applyToEither(playB, f -> f + " is winner");
        System.out.println(Thread.currentThread().getName() + " ---\t" + result.join());
    }
}
