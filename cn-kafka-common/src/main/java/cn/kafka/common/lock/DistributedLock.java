package cn.kafka.common.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.ChildReaper;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.Reaper;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hongliang.wang on 15/12/23.
 */
public class DistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    private CuratorFramework client = null;

    private ChildReaper childReaper = null;

    //创建共享可重入锁，此锁可多次加锁，但是需多次解锁
    public static Map<Thread, InterProcessMutex> THREAD_LOCKS = new ConcurrentHashMap<Thread, InterProcessMutex>();

    private final static String BASE_LOCK_PATH = "/lock";

    /**
     * 分布式锁构造方法
     *
     * @param connString zookeeper地址
     * @param nameSpace  锁所在域
     */
    public DistributedLock(String connString, String nameSpace) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client =
                CuratorFrameworkFactory.builder().connectString(connString).namespace(nameSpace).retryPolicy(retryPolicy)
                        .build();
        client.start();

        childReaper = new ChildReaper(client, BASE_LOCK_PATH, Reaper.Mode.REAP_UNTIL_GONE);
        childReaper.start();
    }

    public void destroy() throws Exception {
        for (Thread thread : THREAD_LOCKS.keySet()) {
            try {
                THREAD_LOCKS.get(thread).release();
            } catch (Exception e) {
                throw e;
            }
        }
        if (client != null) {
            client.close();
        }

        if (childReaper != null) {
            childReaper.close();
        }
    }

    /**
     * 获取锁
     *
     * @return true为获取到了锁，false为没有获得锁
     * @throws GetLockFailedException
     */
    public boolean getLock(String lockPath) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, BASE_LOCK_PATH + "/" + lockPath);
        THREAD_LOCKS.put(Thread.currentThread(), lock);
        return lock.acquire(0, TimeUnit.NANOSECONDS);
    }

    /**
     * 释放锁
     *
     * @throws ReleaseLockFailedException
     */
    public void releaseLock() {
        try {
            if (THREAD_LOCKS.get(Thread.currentThread()) != null) {
                THREAD_LOCKS.get(Thread.currentThread()).release();
                THREAD_LOCKS.remove(Thread.currentThread());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
