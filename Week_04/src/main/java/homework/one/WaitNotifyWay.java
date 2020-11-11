package homework.one;

import homework.one.entity.FiboCalculate;

/**
 * WaitNotifyWay
 *
 * @author qrXun on 2020/11/9
 */
public class WaitNotifyWay {

    private static Object object = new Object();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        synchronized (object) {
            Thread thread = new Thread(() -> {
                synchronized (object) {
                    fiboCalculate.calculate();
                    object.notifyAll();
                }
            });
            thread.start();
            try {
                object.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("异步计算结果为：" + fiboCalculate.getResult());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

}
