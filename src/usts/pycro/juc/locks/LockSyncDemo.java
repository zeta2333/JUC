package usts.pycro.juc.locks;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 4:31 PM
 */
public class LockSyncDemo {
    Object object = new Object();

    // public void m1() {
    //     synchronized (object) {
    //         System.out.println("---hello synchronized code block");
    //         throw new RuntimeException("---ex---");
    //     }
    // }

    // public synchronized void m2() {
    //     System.out.println("---hello synchronized method");
    // }
    public static synchronized void m3() {
        System.out.println("---hello static synchronized method");
    }

    public static void main(String[] args) {

    }
}
