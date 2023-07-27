package usts.pycro.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 10:07 AM
 */
public class Demo05FairLock {
    private Lock lock = new ReentrantLock(true);

    public void showMessage() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始工作");
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " 结束工作");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Demo05FairLock demo = new Demo05FairLock();
        // 创建3个线程
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.showMessage();
            }
        }, "thread-a").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.showMessage();
            }
        }, "thread-b").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.showMessage();
            }
        }, "thread-c").start();
    }
}
