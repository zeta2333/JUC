package usts.pycro.juc.underlying_principle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-19 10:10 AM
 */
public class SyncPKAtomic {
    private int syncData = 0;
    private AtomicInteger atomicData = new AtomicInteger(0);

    public synchronized void syncAdd() {
        ++syncData;
    }

    public void atomicAdd() {
        atomicData.incrementAndGet();
    }

    public static void main(String[] args) {
        SyncPKAtomic demo = new SyncPKAtomic();


        // 创建两个线程
        new Thread(() -> {
            long start = System.currentTimeMillis();
            int data = 0;
            for (int i = 0; i < 1000_0000; i++) {
                // demo.syncAdd();//300ms
                // demo.atomicAdd();//180ms
                demo.syncAdd();//只有一个线程时:50ms
            }
            long end = System.currentTimeMillis();
            System.out.printf("%s耗时：%dms\n", Thread.currentThread().getName(), (end - start));
        }, "thread-01").start();
        // new Thread(() -> {
        //     long start = System.currentTimeMillis();
        //     for (int i = 0; i < 1000_0000; i++) {
        //         // demo.syncAdd();//300ms
        //         demo.atomicAdd();//180ms
        //     }
        //     long end = System.currentTimeMillis();
        //     System.out.printf("%s耗时：%dms\n", Thread.currentThread().getName(), (end - start));
        // }, "thread-02").start();

    }
}