package homework.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test
 *
 * @author qrXun on 2021/1/6
 */
public class Test {

    private static final RedisLock redisLock = new RedisLock();

    //redis分布式锁
    public static void lock(){
        String identifyId = null;
        try {
            identifyId = redisLock.lockWithTimeout("testLock", 5000, 20000);
            System.out.println("线程" + Thread.currentThread().getName() + "获取到分布式锁");
            if (identifyId != null){
                System.out.println("开始执行程序。。。。。。");
                Thread.sleep(1000);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (identifyId != null){
                redisLock.releaseLock("testLock", identifyId);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i<5;i++){
            Thread thread = new Thread(Test::lock);
            thread.start();
        }
    }

}
