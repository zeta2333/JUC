package usts.pycro.juc.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-26 4:39 PM
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("--------------come in call()--------------");
            return "Hello Callable";
        });
        new Thread(futureTask).start();
        String res = futureTask.get();
        System.out.println(res);

    }
}
