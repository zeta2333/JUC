package usts.pycro.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 10:30 AM
 */
public class Demo07LockInterruptibly {
    private Lock lock = new ReentrantLock();

    public void useLock() {
        try {
            lock.lock();
            while (true) {
                System.out.println(Thread.currentThread().getName() + " 正在占用锁");
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void waitLock() {
        try {
            // 可打断式加锁
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " 获取到锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName() + " 阻塞状态被打断了");
            while (true) {
                System.out.println(Thread.currentThread().getName() + "...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Demo07LockInterruptibly demo = new Demo07LockInterruptibly();
        new Thread(demo::useLock, "thread-a").start();
        Thread thread = new Thread(demo::waitLock, "thread-b");
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.interrupt();
    }
}
