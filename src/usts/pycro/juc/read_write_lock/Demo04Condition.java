package usts.pycro.juc.read_write_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 1:35 PM
 */
public class Demo04Condition {
    // 总体执行步骤
    private int step = 1;
    // 数字
    private int digit = 0;
    // 字母
    private char letter = 'a';

    // 锁
    private ReentrantLock lock = new ReentrantLock();
    // 条件对象：对应打印数字的线程
    private Condition conditionDigital = lock.newCondition();
    // 条件对象：对应打印字母的线程
    private Condition conditionAlphabet = lock.newCondition();
    // 条件对象：对应打印*的线程
    private Condition conditionStar = lock.newCondition();
    // 条件对象：对应打印$的线程
    private Condition conditionDollar = lock.newCondition();

    // 打印数字的方法
    public void printDigit() {
        try {
            lock.lock();
            while (step % 4 != 1) { // 取模不是第1步，则等待
                conditionDigital.await();
            }
            System.out.print(digit);
            digit++;
            // 唤醒指定线程：打印字母
            conditionAlphabet.signal();
            step++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printLetter() {
        try {
            lock.lock();
            while (step % 4 != 2) { // 取模不是第2步，则等待
                conditionAlphabet.await();
            }
            System.out.print(letter);
            letter++;
            // 唤醒指定线程：打印*
            conditionStar.signal();
            step++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printStar() {
        try {
            lock.lock();
            while (step % 4 != 3) { // 取模不是第3步，则等待
                conditionStar.await();
            }
            System.out.print("*");
            // 唤醒指定线程：打印$
            conditionDollar.signal();
            step++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printDollar() {
        try {
            lock.lock();
            while (step % 4 != 0) { // 取模不是第4步，则等待
                conditionDollar.await();
            }
            System.out.println("$");
            // 唤醒指定线程：打印数字
            conditionDigital.signal();
            step++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Demo04Condition demo = new Demo04Condition();
        // 4个线程
        new Thread(() -> {
            System.out.println("第一个线程启动");
            for (int i = 0; i < 10; i++) {
                demo.printDigit();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.printLetter();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.printStar();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                demo.printDollar();
            }
        }).start();
    }

}
