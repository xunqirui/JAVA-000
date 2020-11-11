package homework.one;

import homework.one.entity.FiboCalculate;

/**
 * ThreadWay
 * 使用普通线程进行 join() 等待某个线程执行完毕
 *
 * @author qrXun on 2020/11/9
 */
public class ThreadJoinWay {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        Thread thread = new Thread(fiboCalculate::calculate);
        thread.start();
        try {
            thread.join();
            System.out.println("异步计算结果为：" + fiboCalculate.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        }
    }

}
