package usts.pycro.juc.locks;

import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-28 10:18 AM
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        Object objA = new Object();
        Object objB = new Object();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "尝试获取objA");
            synchronized (objA) {
                System.out.println(Thread.currentThread().getName() + "拿到objA");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "尝试获取objB");
                synchronized (objB) {
                    System.out.println(Thread.currentThread().getName() + "拿到objB");
                }
            }
        }, "thread-1").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "尝试获取objB");
            synchronized (objB) {
                System.out.println(Thread.currentThread().getName() + "拿到objB");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "尝试获取objA");
                synchronized (objA) {
                    System.out.println(Thread.currentThread().getName() + "拿到objA");
                }
            }
        }, "thread-2").start();

    }
}
