package usts.pycro.juc.thread_pool;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-23 10:58 AM
 */
public class BlockQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> breadShop = new ArrayBlockingQueue<>(3);
        // 生产者：提供面包
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    String bread = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
                    breadShop.put(bread);
                    System.out.println("面包出炉：" + bread + " 货架情况：" + breadShop);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        // 消费者：卖出面包
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep((int) (Math.random() * 10));
                    String bread = breadShop.take();
                    System.out.println("卖出面包：" + bread + " 货架情况：" + breadShop);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
