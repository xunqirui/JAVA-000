package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.*;

/**
 * FutureTaskWay
 *
 * @author qrXun on 2020/11/9
 */
public class FutureTaskWay {

    private static ExecutorService servicePool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            fiboCalculate.calculate();
            return fiboCalculate.getResult();
        });
        servicePool.submit(futureTask);
        try {
            Integer result = futureTask.get();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
            servicePool.shutdown();
        }
    }

}
