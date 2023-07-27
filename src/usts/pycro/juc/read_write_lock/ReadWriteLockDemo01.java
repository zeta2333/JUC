package usts.pycro.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 11:10 AM
 */
public class ReadWriteLockDemo01 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    public synchronized void readOperationWithSync() {
        for (int i = 0; i < 5; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " is reading");
        }
    }

    public void readOperationWithReadLock() {
        try {
            readLock.lock();
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " is reading");
            }
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockDemo01 demo = new ReadWriteLockDemo01();
        for (int i = 0; i < 5; i++) {
            // new Thread(demo::readOperationWithSync, "thread-" + (i + 1)).start();//串行，执行效率低
            new Thread(demo::readOperationWithReadLock, "thread-" + (i + 1)).start();//并行，执行效率高
        }
    }
}
