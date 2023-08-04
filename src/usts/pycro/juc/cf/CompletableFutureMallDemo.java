package usts.pycro.juc.cf;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 11:20 AM
 */

public class CompletableFutureMallDemo {
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("taobao"),
            new NetMall("pdd"),
            new NetMall("Tmall"),
            new NetMall("dangdang")
    );

    /**
     * 一家家查
     * List<NetMall>----> map ----> List<String>
     *
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(item -> String.format(
                        productName + " in %s price is %.2f",
                        item.getNetMallName(),
                        item.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(netMall -> CompletableFuture.supplyAsync(() -> String.format(
                        productName + " in %s price is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        getPrice(list, "mysql").forEach(System.out::println);
        long endTime = System.currentTimeMillis();
        System.out.println("cost time：" + (endTime - startTime) + " ms");
        System.out.println("-----------------------------");
        startTime = System.currentTimeMillis();
        getPriceByCompletableFuture(list, "mysql").forEach(System.out::println);
        endTime = System.currentTimeMillis();
        System.out.println("cost time：" + (endTime - startTime) + " ms");
    }
}

@AllArgsConstructor
class NetMall {
    @Getter
    private String netMallName;

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble() * 5 + productName.charAt(0);
    }
}