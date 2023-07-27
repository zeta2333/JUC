package usts.pycro.juc.scenario_specific_solutions;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-18 4:13 PM
 */
public class ForkJoinTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 1.创建Fork Join 任务池
        ForkJoinPool pool = new ForkJoinPool();
        // 2.创建任务对象
        MyTask myTask = new MyTask(1, Integer.MAX_VALUE / 2);
        // 3.将任务对象提交到任务池
        ForkJoinTask<Integer> forkJoinTask = pool.submit(myTask);
        // 4.获取任务执行结果
        Integer finalResult = forkJoinTask.get();
        System.out.println("finalResult = " + finalResult);
        /* Integer sum = 0;
        for (int i = 1; i <= Integer.MAX_VALUE / 2; i++) {
            sum += i;
        }
        System.out.println("sum = " + sum); */
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (double) (end - start) / 1000 + "s");
    }
}

class MyTask extends RecursiveTask<Integer> {

    private int begin; // 区间开始
    private int end; // 区间结束
    private static final int ADJUST_VALUE = Integer.MAX_VALUE / (2 * 128); // 区间调整值
    private int result; // 保存当前结果

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override

    protected Integer compute() {
        // 1.判断当前区间是否是原子任务重可执行的范围
        if (end - begin <= ADJUST_VALUE) {
            for (int i = begin; i <= end; ++i) {
                result += i;
            }
        } else {
            // 2.计算新拆分的任务范围
            int leftBegin = begin;
            int leftEnd = (begin + end) / 2;

            int rightBegin = leftEnd + 1;
            int rightEnd = end;

            // 3.创建两个新的子任务
            MyTask myTaskLeft = new MyTask(leftBegin, leftEnd);
            MyTask myTaskRight = new MyTask(rightBegin, rightEnd);

            // 4.调用fork()进一步拆分任务
            myTaskLeft.fork();
            myTaskRight.fork();

            // 5.调用框架的join()获取子任务计算的结果
            Integer leftResult = myTaskLeft.join();
            Integer rightResult = myTaskRight.join();

            // 6.将子任务的结果合并到一起
            result = leftResult + rightResult;
        }
        return result;
    }
}