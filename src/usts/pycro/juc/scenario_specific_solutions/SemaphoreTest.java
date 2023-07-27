package usts.pycro.juc.scenario_specific_solutions;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 3:46 PM
 */
class NormalUsage {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        // 10个线程争夺3个资源
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // 申请资源
                    semaphore.acquire();
                    // 拿到资源执行操作
                    System.out.println("【" + Thread.currentThread().getName() + "】号车辆【驶入车位】");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("【" + Thread.currentThread().getName() + "】号车辆【驶离车位】");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    // 释放资源
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}

class TimeWaiting {
    public static void main(String[] args) {
        int carPositionNum = 3; // 车位数
        Semaphore semaphore = new Semaphore(carPositionNum); // 信号量
        for (int i = 0; i < 50; i++) { // 50辆车争夺3个车位
            new Thread(() -> {
                boolean acquireResult = false;
                try {
                    // 现成申请资源，申请不到则已知等待
                    // semaphore.acquire();
                    // 尝试申请资源，超时则申请失败
                    acquireResult = semaphore.tryAcquire(3, TimeUnit.SECONDS);
                    if (acquireResult) {
                        System.out.println("【" + Thread.currentThread().getName() + "】 号车辆驶入车位");
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("【" + Thread.currentThread().getName() + "】 号车辆驶离车位");
                    } else {
                        System.out.println("【" + Thread.currentThread().getName() + "】 号车辆放弃等待");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (acquireResult) { // 如果申请到，则释放资源
                        semaphore.release();
                    }
                }
            }, String.valueOf(i)).start();

        }
    }
}