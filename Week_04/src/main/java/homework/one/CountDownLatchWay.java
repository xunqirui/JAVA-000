package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLanchWay
 *
 * @author qrXun on 2020/11/9
 */
public class CountDownLatchWay {

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        Thread thread = new Thread(() -> {
            fiboCalculate.calculate();
            countDownLatch.countDown();
        });
        thread.start();
        try {
            countDownLatch.await();
            System.out.println("异步计算结果为：" + fiboCalculate.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        }
    }

}
