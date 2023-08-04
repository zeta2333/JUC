package usts.pycro.juc.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-28 2:56 PM
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" --- come in");
            LockSupport.park();
            LockSupport.unpark(Thread.currentThread());
            LockSupport.park();
            LockSupport.unpark(Thread.currentThread());
            System.out.println(Thread.currentThread().getName()+" --- end");
        }, "t1").start();
    }

    private static void parkAndUnpark() {
        Thread t1 = new Thread(() -> {
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + " --- come in" + System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + " --- 被唤醒" + System.currentTimeMillis());
        }, "t1");
        t1.start();
        // try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " --- 发起通知");
            LockSupport.unpark(t1);
        }, "t2").start();
    }

    private static void lockAwaitSignal() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " --- come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + " --- 被唤醒");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + " --- 发起通知");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void syncWaitNotify() {
        Object objLock = new Object();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objLock) {
                System.out.println(Thread.currentThread().getName() + " ---- come in");
                try {
                    System.out.println(Thread.currentThread().getName() + "开始wait");
                    objLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "被唤醒");
            }

        }, "t1").start();
        // try {
        //     TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        new Thread(() -> {
            synchronized (objLock) {
                objLock.notify();
                System.out.println(Thread.currentThread().getName() + "发出通知");
            }
        }, "t2").start();
    }
}
