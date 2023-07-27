package usts.pycro.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 9:44 AM
 */
public class Demo04TryLockWithTime {
    private Lock lock = new ReentrantLock();

    public void useLock() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始工作");
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + " 结束工作");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void waitLock() {
        boolean lockResult = false;
        try {
            lockResult = lock.tryLock(3, TimeUnit.SECONDS);
            if (lockResult) {
                System.out.println(Thread.currentThread().getName() + " 得到锁，开始工作");
            } else {
                System.out.println(Thread.currentThread().getName() + " 未得到锁");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lockResult) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Demo04TryLockWithTime demo = new Demo04TryLockWithTime();
        new Thread(() -> {
            demo.useLock();
        }, "thread-a").start();
        new Thread(() -> {
            demo.waitLock();
        }, "thread-b").start();
    }
}
