package usts.pycro.juc.read_write_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 1:51 PM
 */
public class ExerPrintDigitAndAlphaBet {
    private boolean flag = false;
    private int digit = 1;
    private char alphabet = 'a';
    private ReentrantLock lock = new ReentrantLock();
    private Condition conditionDigit = lock.newCondition();
    private Condition conditionAlphabet = lock.newCondition();

    public void printDblDigit() {
        try {
            lock.lock();
            // flag为true则等待
            while (flag) {
                conditionDigit.await();
            }
            System.out.print(digit++);
            System.out.print(digit++);
            flag = !flag;
            conditionAlphabet.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printDblAlphaBet() {
        try {
            lock.lock();
            // flag为false则等待
            while (!flag) {
                conditionAlphabet.await();
            }
            System.out.print(alphabet++);
            System.out.print(alphabet++);
            flag = !flag;
            conditionDigit.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ExerPrintDigitAndAlphaBet exer = new ExerPrintDigitAndAlphaBet();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                exer.printDblAlphaBet();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                exer.printDblAlphaBet();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1; i++) {
                exer.printDblDigit();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                exer.printDblDigit();
            }
        }).start();
    }
}
