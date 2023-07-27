package usts.pycro.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 10:17 AM
 */
public class Demo06Interrupt {
    public static void main(String[] args) throws InterruptedException {
        Object myLock = new Object();
        // A线程启动
        Thread a = new Thread(() -> {
            synchronized (myLock) {
                System.out.println(Thread.currentThread().getName() + " 开始工作...");
                // while (true) {
                // }
                try {
                    TimeUnit.SECONDS.sleep(10);// sleep可以被打断：java.lang.InterruptedException: sleep interrupted
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "thread-a");
        a.start();

        TimeUnit.SECONDS.sleep(1);

        // B线程启动
        Thread b = new Thread(() -> {
            // sync阻塞状态下无法被打断
            synchronized (myLock) {
                System.out.println(Thread.currentThread().getName() + " 开始工作...");
            }
        }, "thread-b");
        b.start();

        // 阻塞5秒后，尝试打断
        TimeUnit.SECONDS.sleep(5);
        System.out.println("调用interrupt()方法前：");
        a.interrupt();
        System.out.println("调用interrupt()方法后：");
    }
}
