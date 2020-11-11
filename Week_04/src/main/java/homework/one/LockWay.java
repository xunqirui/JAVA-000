package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockWay
 *
 * @author qrXun on 2020/11/9
 */
public class LockWay {

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        Lock lock = new ReentrantLock();
        Condition calculateCondition = lock.newCondition();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        lock.lock();
        Thread thread = new Thread(() -> {
            lock.lock();
            fiboCalculate.calculate();
            calculateCondition.signalAll();
            lock.unlock();
        });
        thread.start();
        try {
            calculateCondition.await();
            System.out.println("异步计算结果为：" + fiboCalculate.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        }
    }

}
