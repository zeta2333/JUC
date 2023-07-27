package usts.pycro.juc.read_write_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 11:21 AM
 */
// public class Situations {
// }

// 读可共享
class Situation01 {
    private ReentrantReadWriteLock.ReadLock readLock = new ReentrantReadWriteLock().readLock();

    public void read() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始执行读操作");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 结束执行读操作");
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        Situation01 situation01 = new Situation01();
        for (int i = 0; i < 10; i++) {
            // 同时开始，同时结束
            new Thread(situation01::read, "thread-" + i).start();
        }
    }
}

// 写互排斥
class Situation02 {
    private ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock().writeLock();

    public void write() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始执行写操作");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 结束执行写操作");
            System.out.println();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        Situation02 situation02 = new Situation02();
        for (int i = 0; i < 10; i++) {
            // 串行执行
            new Thread(situation02::write, "thread-" + i).start();
        }
    }
}

// 读排斥写
class Situation03 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public void read() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始执行读操作");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 结束执行读操作");
        } finally {
            readLock.unlock();
        }
    }

    public void write() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始执行写操作");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 结束执行写操作");
            System.out.println();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        Situation03 situation03 = new Situation03();
        new Thread(situation03::read, "thread-read").start();

        new Thread(situation03::write, "thread-write 01").start();
        new Thread(situation03::write, "thread-write 02").start();
        new Thread(situation03::write, "thread-write 03").start();
    }
}

// 写排斥读
class Situation04 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public void read() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始执行读操作");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 结束执行读操作");
        } finally {
            readLock.unlock();
        }
    }

    public void write() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始执行写操作");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 结束执行写操作");
            System.out.println();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        Situation04 situation04 = new Situation04();
        new Thread(situation04::write, "thread-write").start();

        new Thread(situation04::read, "thread-read 01").start();
        new Thread(situation04::read, "thread-read 02").start();
        new Thread(situation04::read, "thread-read 03").start();
    }
}

// 锁升级不允许
class Situation05 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public void readThenWrite() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 正在读取数据");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 正在写入数据");
        } finally {
            writeLock.unlock();
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        Situation05 situation05 = new Situation05();
        new Thread(situation05::readThenWrite, "thread-0").start();
        // 一直处于读数据状态，写锁会被阻塞
    }
}

// 锁降级
class Situation06 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public void writeThenRead() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 正在写入数据");
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 正在读取数据");
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 写锁释放");
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 读锁释放");
        }
    }

    public static void main(String[] args) {
        Situation06 situation06 = new Situation06();
        new Thread(situation06::writeThenRead, "thread-0").start();
    }

}

// 读锁可重入
class Situation07 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    public void readThenRead() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始第一次读取数据");
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始第二次读取数据");
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 释放读锁");
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 释放读锁");
        }
    }

    public static void main(String[] args) {
        Situation07 situation07 = new Situation07();
        new Thread(situation07::readThenRead, "thread-0").start();
    }
}

// 写锁可重入
class Situation08 {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public void writeThenWrite() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始第一次写入数据");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 开始第二次写入数据");
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
        }
    }

    public static void main(String[] args) {
        Situation08 situation08 = new Situation08();
        new Thread(situation08::writeThenWrite, "thread-0").start();
    }
}