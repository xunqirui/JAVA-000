package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.*;

/**
 * CompletionServiceWay
 *
 * @author qrXun on 2020/11/9
 */
public class CompletionServiceWay {

    private static ExecutorService servicePool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(servicePool);
        Future<Integer> future = completionService.submit(() -> {
            fiboCalculate.calculate();
            return fiboCalculate.getResult();
        });
        try {
            Integer result = completionService.take().get();
            future.cancel(true);
            System.out.println("异步计算结果为：" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            servicePool.shutdown();
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        }
    }

}
