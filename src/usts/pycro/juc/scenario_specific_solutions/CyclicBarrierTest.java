package usts.pycro.juc.scenario_specific_solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 3:02 PM
 */
public class CyclicBarrierTest {
    private static List<List<String>> matrix = new ArrayList<>();

    static {
        matrix.add(Arrays.asList("normal", "special", "end"));
        matrix.add(Arrays.asList("normal", "normal", "special", "end"));
        matrix.add(Arrays.asList("normal", "normal", "normal", "special", "end"));
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        new Thread(() -> {
            try {
                List<String> list = matrix.get(0);
                for (String value : list) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " value: " + value);
                    if ("special".equals(value)) {
                        barrier.await();
                    }
                }
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                List<String> list = matrix.get(1);
                for (String value : list) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " value: " + value);
                    if ("special".equals(value)) {
                        barrier.await();
                    }
                }
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                List<String> list = matrix.get(2);
                for (String value : list) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " value: " + value);
                    if ("special".equals(value)) {
                        barrier.await();
                    }
                }
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
