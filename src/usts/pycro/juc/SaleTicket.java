package usts.pycro.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-16 4:23 PM
 */
public class SaleTicket {
    // 票库存数量
    private int stock = 10;

    public synchronized void saleTicket() {
        if (stock > 0) {
            System.out.println(Thread.currentThread().getName() + "号窗口操作，还剩" + --stock + "张票");
        } else {
            System.out.println("票卖完了！");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SaleTicket demo = new SaleTicket();
        new Thread(() -> {
            while (true) {
                demo.saleTicket();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "001").start();
        new Thread(() -> {
            while (true) {
                demo.saleTicket();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "002").start();
        new Thread(() -> {
            while (true) {
                demo.saleTicket();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "003").start();
    }
}
