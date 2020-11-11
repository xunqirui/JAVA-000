package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue
 * 使用阻塞队列获取结果
 *
 * @author qrXun on 2020/11/9
 */
public class BlockingQueueWay {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1);
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        Thread thread = new Thread(() -> {
            fiboCalculate.calculate();
            blockingQueue.add(fiboCalculate.getResult());
        });
        thread.start();
        try {
            Integer result = blockingQueue.take();
            System.out.println("异步计算结果为：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        }
    }

}
