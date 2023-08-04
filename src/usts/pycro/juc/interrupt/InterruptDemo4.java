package usts.pycro.juc.interrupt;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-28 2:30 PM
 */
public class InterruptDemo4 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("---1");
        Thread.currentThread().interrupt();
        System.out.println("---2");
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
    }
}
