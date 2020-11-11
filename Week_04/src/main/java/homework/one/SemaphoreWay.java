package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.Semaphore;

/**
 * SemaphoreWay
 *
 * @author qrXun on 2020/11/9
 */
public class SemaphoreWay {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire();
            Thread thread = new Thread(() -> {
                fiboCalculate.calculate();
                semaphore.release();
            });
            thread.start();
            semaphore.acquire();
            System.out.println("异步计算结果为：" + fiboCalculate.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            semaphore.release();
        }
    }

}
