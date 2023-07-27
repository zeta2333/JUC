package usts.pycro.juc.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Pycro
 * @version 1.0
 * 2023-07-26 5:10 PM
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName()+"\t ---come in");
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {throw new RuntimeException(e);}
            return "task over";
        });
        new Thread(futureTask).start();
        System.out.println(Thread.currentThread().getName()+"\t ---忙其他任务");
        // System.out.println(futureTask.get());// get()会导致程序阻塞
        // System.out.println(futureTask.get(3,TimeUnit.SECONDS));
        while (true){
            if (futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else {
                try {TimeUnit.MILLISECONDS.sleep(1200);} catch (InterruptedException e) {throw new RuntimeException(e);}
                System.out.println("正在处理中...");
            }
        }
    }
}
