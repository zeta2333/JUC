package usts.pycro.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-16 3:43 PM
 */
public class Main {
    public static void main(String[] args) {
        Object lockObj = new Object();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " begin");
            synchronized (lockObj) {
                System.out.println(Thread.currentThread().getName() + " get lock");
                try {
                    // 拿着锁睡觉
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " release lock");
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, "thread-a").start();
        // 主线程睡觉，让a线程先启动，b线程后启动
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " begin");
            synchronized (lockObj) {
                System.out.println(Thread.currentThread().getName() + " get lock");
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, "thread-b").start();
    }
}
