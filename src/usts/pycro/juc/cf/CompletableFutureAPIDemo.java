package usts.pycro.juc.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 1:49 PM
 */
public class CompletableFutureAPIDemo {
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "abc";
        });
        // System.out.println(completableFuture.get());
        // System.out.println(completableFuture.get(2, TimeUnit.SECONDS));
        // System.out.println(completableFuture.join());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 立即获取结果，若未计算完成则使用默认值
        // System.out.println(completableFuture.getNow("xxx"));
        // 主动触发计算，若调用该方法时，未计算完成，则使用complete里面的参数作为计算结果
        System.out.println(completableFuture.complete("completeValue") + "\t" + completableFuture.join());

    }
}
