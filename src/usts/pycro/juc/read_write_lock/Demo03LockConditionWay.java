package usts.pycro.juc.read_write_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 1:17 PM
 */
public class Demo03LockConditionWay {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private int data = 0; // 共享数据

    public void doAdd() {
        try {
            // 加锁
            lock.lock();
            // 使用while循环判断，避免虚假唤醒
            while (data == 1) {
                condition.await();
            }
            // 执行增加操作
            System.out.println(Thread.currentThread().getName() + "对data增加，当前data = " + ++data);
            // 任务完成后，唤醒其他线程
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void doSub() {
        try {
            lock.lock();
            while (data == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "对data减少，当前data = " + --data);
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Demo03LockConditionWay demo = new Demo03LockConditionWay();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                demo.doAdd();
            }
        }, "thread-add a").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                demo.doAdd();
            }
        }, "thread-add b").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                demo.doSub();
            }
        }, "thread-sub a").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                demo.doSub();
            }
        }, "thread-sub a").start();
    }
}
