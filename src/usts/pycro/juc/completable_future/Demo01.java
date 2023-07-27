package usts.pycro.juc.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-23 1:26 PM
 */
public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 没有返回值的任务
        // Runnable runnable = () -> {
        //     System.out.println(Thread.currentThread().getName() + "is working");
        // };
        // ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // CompletableFuture.runAsync(runnable, threadPool);

        // 创建Supplier对象，封装任务内容
        // Supplier<String> supplier = () -> {
        //     System.out.println(Thread.currentThread().getName() + " is working");
        //     return "task result";
        // };
        // CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier, threadPool);
        // 调用get方法获取任务结果
        // String taskResult = future.get();
        // System.out.println("taskResult = " + taskResult);
        /* future.whenCompleteAsync((String taskResult, Throwable throwable) -> {
            System.out.println(Thread.currentThread().getName() + " taskResult = " + taskResult);
            System.out.println(Thread.currentThread().getName() + " throwable = " + throwable);
        },threadPool); */
        // CompletableFuture.supplyAsync(() -> "prev task result", threadPool).thenAcceptAsync((String prevTaskResult) -> {
        //     System.out.println(Thread.currentThread().getName() + " prevTaskResult = " + prevTaskResult);
        // },threadPool);
    }
}

class Test02 {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        CompletableFuture.supplyAsync(() -> {
                    return "prev task result";
                }, threadPool)

                // 封装后续任务，并接收前面任务的返回值
                .thenApply((String prevTaskResult) -> {

                    System.out.println(Thread.currentThread().getName() + " prevTaskResult = " + prevTaskResult);

                    // 自己有返回值
                    return "current task result";
                }).whenComplete((String prevTaskResult, Throwable throwable) -> {
                    System.out.println("prevTaskResult = " + prevTaskResult);
                });
    }
}

class Test03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String finalResult = CompletableFuture.supplyAsync(() -> {

                    return "first task result";
                })

                // 将两个任务合并。
                // 第二个任务可以接收上一个任务的结果
                .thenCompose((String prevTaskResult) -> {

                    // 封装任务的方法返回值：要求返回一个新的 CompletableFuture 对象，
                    // 在这个新的对象中封装第二个任务并执行任务结果的合并
                    return CompletableFuture.supplyAsync(() -> {

                        // 当前任务执行的结果
                        String currentTaskResult = "second task result";

                        // 将两个任务的结果合并
                        String composedTaskResult = prevTaskResult + "@" + currentTaskResult;

                        return composedTaskResult;
                    });
                })

                // 如果需要继续合并更多任务，那么就继续调用 thenCompose() 方法
                .thenCompose((String prevTaskResult) -> {
                    return CompletableFuture.supplyAsync(() -> {

                        // 当前任务执行的结果
                        String currentTaskResult = "third task result";

                        // 将两个任务的结果合并
                        String composedTaskResult = prevTaskResult + "@" + currentTaskResult;

                        return composedTaskResult;
                    });
                }).get();

        System.out.println("finalResult = " + finalResult);
    }
}

class Test04 {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
                    System.out.println("第一个任务");
                    return "first task result";
                }).thenApply((String prevTaskResult) -> {
                    System.out.println("第二个任务 " + (10 / 0));

                    return prevTaskResult;
                })
                // 针对前面所有任务提供异常处理的功能
                // 需要传入的对象：Function<Throwable, ? extends T> fn 类型
                .exceptionally((Throwable t) -> {

                    String exceptionFullName = t.getClass().getName();
                    System.out.println("exceptionFullName = " + exceptionFullName);

                    Throwable cause = t.getCause();
                    System.out.println("cause.getClass().getName() = " + cause.getClass().getName());

                    String message = t.getMessage();
                    System.out.println("message = " + message);

                    return "前面任务抛出了异常，由 exceptionally() 方法提供一个备选任务结果。";
                }).whenComplete((String finalResult, Throwable throwable) -> {
                    System.out.println("finalResult = " + finalResult);
                    System.out.println("throwable = " + throwable);
                });
    }
}

class Test05 {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {

                    return "task result 0001";
                }).thenApply((String prevTaskResult) -> {
                    System.out.println("在第二个任务中获取到的上一个任务的结果 = " + prevTaskResult);
                    int a = 10 / 0;
                    return "task result 0002";
                })

                // 相当于在处理整个任务的过程中，在最后的环节做最后的处理。
                // 如果前面的操作中有抛出异常，那么从 Throwable throwable 参数这里可以传进来
                // 如果前面的操作没有抛异常，那么 Throwable throwable 参数传入的就是 null
                .handle((String prevTaskResult, Throwable throwable) -> {

                    System.out.println("handle 方法中获取到的上一个任务的结果 = " + prevTaskResult);

                    System.out.println("handle 方法中获取到的异常：throwable = " + throwable);

                    return "current task result";
                }).whenComplete((String finalResult, Throwable throwable) -> {
                    System.out.println("finalResult = " + finalResult);
                    System.out.println("throwable = " + throwable);
                });
    }
}