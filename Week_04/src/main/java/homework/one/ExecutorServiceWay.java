package homework.one;

import homework.one.entity.FiboCalculate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorServiceWay
 * 线程池方式
 *
 * @author qrXun on 2020/11/9
 */
public class ExecutorServiceWay {

    private static ExecutorService servicePool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        FiboCalculate fiboCalculate = new FiboCalculate(43);
        servicePool.execute(fiboCalculate::calculate);
        servicePool.shutdown();
        try {
            while (true){
                if (servicePool.awaitTermination(100, TimeUnit.MILLISECONDS)){
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("异步计算结果为：" + fiboCalculate.getResult());
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        }
    }



}
