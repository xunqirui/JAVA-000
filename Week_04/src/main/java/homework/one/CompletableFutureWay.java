package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFutureWay
 *
 * @author qrXun on 2020/11/9
 */
public class CompletableFutureWay {

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        Integer completableFuture = CompletableFuture.supplyAsync(() -> {
            fiboCalculate.calculate();
            return fiboCalculate.getResult();
        }).join();
        System.out.println("异步计算结果为：" + completableFuture);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

}
