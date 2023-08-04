package usts.pycro.juc.locks;

import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-27 3:48 PM
 * 8种锁
 */
class Phone {
    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("------sendSMS");
    }

    public void hello() {
        System.out.println("hello");
    }
}

public class Lock8Demo {
    public static void main(String[] args) {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> phone1.sendEmail()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            // phone.sendSMS();
            // phone1.hello();
            phone2.sendSMS();
            // phone1.sendSMS();
        }).start();

    }
}
