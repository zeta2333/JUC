package usts.pycro.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-17 6:00 PM
 */
public class Demo03TryLock {
    private Lock lock = new ReentrantLock();

    public void showMessage() {
        boolean lockResult = false;
        try {
            lockResult = lock.tryLock();
            // 获取到了锁
            if (lockResult) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "获取到锁，正在工作");
            } else {
                System.out.println(Thread.currentThread().getName() + "未获取到锁");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 如果曾经获取过锁，则释放
            if (lockResult) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Demo03TryLock demo = new Demo03TryLock();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                demo.showMessage();
            }
        }, "thread-01").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                demo.showMessage();
            }
        }, "thread-02").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                demo.showMessage();
            }
        }, "thread-03").start();
    }
}
