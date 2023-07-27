package usts.pycro.juc.scenario_specific_solutions;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 2:38 PM
 */
class LockDoor {
    public static void main(String[] args) throws InterruptedException {
        int stuNum = 6;
        CountDownLatch countDownLatch = new CountDownLatch(stuNum);
        for (int i = 0; i < 6; i++) {
            String num = String.valueOf(i + 1);
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " " + num + "号同学离开教室");
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("班长锁门");
    }
}

class SevenDragonBall {
    public static void main(String[] args) throws InterruptedException {
        int dragonBall = 7;
        CountDownLatch countDownLatch = new CountDownLatch(dragonBall);
        for (int i = 0; i < 7; i++) {
            String index = String.valueOf(i + 1);
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "拿到了第" + index + "颗龙珠");
                countDownLatch.countDown();
            }).start();
        }
        // countDownLatch对象抑制最后一步
        countDownLatch.await();
        System.out.println("集齐7颗龙珠，召唤神龙");
    }
}