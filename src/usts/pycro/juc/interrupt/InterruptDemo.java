package usts.pycro.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-28 11:27 AM
 */
public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " is interrupted,this thread stop");
                    break;
                }
                System.out.println("t1 ---- hello interrupt api");
            }
        }, "t1");
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t1.interrupt();
    }

    private static void m2_atomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean is true,this thread stop");
                    break;
                }
                System.out.println("t1---hello atomicBoolean");
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void m1_volatile() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop is true,this thread stop");
                    break;
                }
                System.out.println("t1---hello volatile");
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            isStop = true;
        }, "t2").start();
    }

}
