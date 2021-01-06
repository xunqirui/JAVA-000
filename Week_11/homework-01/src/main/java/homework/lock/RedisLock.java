package homework.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * RedisLock
 *
 * @author qrXun on 2021/1/6
 */
public class RedisLock {

    private final JedisPool jedisPool;

    public RedisLock() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        this.jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 2000, null);
    }

    /**
     * 获取分布式锁
     *
     * @param keyName        锁名称
     * @param acquireTimeOut 获取锁的超时时间（毫秒）
     * @param keyTimeOut     锁超时时间（毫秒）
     * @return
     */
    public String lockWithTimeout(String keyName, long acquireTimeOut, long keyTimeOut) {
        // 超时时间
        String backIdentifyId;
        String identifyId = UUID.randomUUID().toString();
        String lockKey = "lock:" + keyName;
        int lockExpire = (int) (keyTimeOut / 1000);
        try (Jedis connect = jedisPool.getResource()) {
            // 获取锁的超时时间
            long endTime = System.currentTimeMillis() + acquireTimeOut;
            while (System.currentTimeMillis() < endTime) {
                if (connect.setnx(lockKey, identifyId) == 1) {
                    connect.expire(lockKey, lockExpire);
                    backIdentifyId = identifyId;
                    return backIdentifyId;
                }
                // 休息5秒后继续尝试
                Thread.sleep(5);
            }
        } catch (JedisException | InterruptedException e) {
            System.out.println("获取锁异常" + e.getMessage());
        }
        return null;
    }

    /**
     * 释放锁
     * @param lockName
     * @param identifyId
     * @return
     */
    public boolean releaseLock(String lockName, String identifyId) {
        Jedis conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = jedisPool.getResource();
            while (true) {
                // 准备开始事务
                conn.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifyId.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            System.out.println("RedisLock释放锁异常" + e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }
}
