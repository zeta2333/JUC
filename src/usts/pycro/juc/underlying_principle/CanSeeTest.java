package usts.pycro.juc.underlying_principle;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-19 10:29 AM
 */
public class CanSeeTest {
    private volatile int data = 100; // 添加volatile关键字后，线程A能监测到线程B修改了data的值

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public static void main(String[] args) {
        CanSeeTest demo = new CanSeeTest();
        new Thread(() -> {
            while (demo.getData() == 100) {
            }
            System.out.println("AAA线程发现新值：" + demo.getData());

        }, "AAA").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            demo.setData(200);
            System.out.println("BBB线程修改了data的值为200");
        }, "BBB").start();
    }
}

class CASTest {
    public static void main(String[] args) {
        AtomicInteger data = new AtomicInteger(5);
        boolean updateResult = data.compareAndSet(5, 666);
        System.out.println("更新结果：" + updateResult + "，当前值为：" + data);
        updateResult = data.compareAndSet(5, 777);
        System.out.println("更新结果：" + updateResult + "，当前值为：" + data);
        updateResult = data.compareAndSet(666, 777);
        System.out.println("更新结果：" + updateResult + "，当前值为：" + data);
    }
}