package usts.pycro.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-17 5:53 PM
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "外层加锁成功");
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "内层加锁成功");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "内层解锁成功");
            }
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "外层解锁成功");
        }
    }
}
