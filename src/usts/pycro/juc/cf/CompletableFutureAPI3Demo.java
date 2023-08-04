package usts.pycro.juc.cf;

import java.util.concurrent.CompletableFuture;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 2:26 PM
 */
public class CompletableFutureAPI3Demo {
    public static void main(String[] args) {
        // CompletableFuture
        //         .supplyAsync(() -> 1)
        //         .thenApply(f -> f + 2)
        //         .thenApply(f -> f + 3)
        //         .thenAccept(System.out::println);
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {}).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(r -> System.out.println(r)).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r + "resultB").join());
    }
}
