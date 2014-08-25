/**
 * 
 */
package com.mcp.scheduler.thread;

import org.apache.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
	
	public static Logger log = Logger.getLogger(ThreadPool.class);

	private final static String poolName = "jcdraw";

	static private ThreadPool threadFixedPool = null;

	public ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(2);

	private ExecutorService executor;

	public static ThreadPool getFixedInstance() {
		if(threadFixedPool == null)
		{
			threadFixedPool = new ThreadPool(40);
		}
		return threadFixedPool;
	}

	private ThreadPool(int num) {
		executor = new ThreadPoolExecutor(4, 6, 60, TimeUnit.SECONDS, queue,
				new DaemonThreadFactory(poolName),
				new ThreadPoolExecutor.AbortPolicy());
	}

	public void execute(Runnable r) {
		executor.execute(r);
	}

	/**
     * Daemon thread factory used by the monitor executors.
     * <P>
     * This factory creates all new threads used by an Executor in
     * the same ThreadGroup. If there is a SecurityManager, it uses
     * the group of System.getSecurityManager(), else the group of
     * the thread instantiating this DaemonThreadFactory. Each new
     * thread is created as a daemon thread with priority
     * Thread.NORM_PRIORITY. New threads have names accessible via
     * Thread.getName() of "JMX Monitor <pool-name> Pool [Thread-M]",
     * where M is the sequence number of the thread created by this
     * factory.
     */
    private static class DaemonThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
        final String nameSuffix = "]";

        public DaemonThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "JMX Monitor " + poolName + " Pool [Thread-";
        }

        /*public DaemonThreadFactory(String poolName, ThreadGroup threadGroup) {
            group = threadGroup;
            namePrefix = "JMX Monitor " + poolName + " Pool [Thread-";
        }

        public ThreadGroup getThreadGroup() {
            return group;
        }*/

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group,
                                  r,
                                  namePrefix +
                                  threadNumber.getAndIncrement() +
                                  nameSuffix,
                                  0);
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
