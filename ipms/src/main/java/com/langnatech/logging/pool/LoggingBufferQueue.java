
package com.langnatech.logging.pool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.core.holder.PropertiesHolder;
import com.langnatech.logging.bean.LoggingBean;
import com.langnatech.logging.holder.LoggingHolder;


/**
 * @Title:LoggingBufferQueue
 * @Description: 日志队列
 * @date Jul 30, 2014 10:45:55 AM 
 *  
 */

public enum LoggingBufferQueue
{
    INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(LoggingBufferQueue.class);

    private static final ConcurrentLinkedQueue<LoggingBean> queue = new ConcurrentLinkedQueue<LoggingBean>();

    private static final int maxLogCount = PropertiesHolder.getIntProperty("logging.bufferPool.maxLogCount");

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final HandleLogRunnable logHandler = new HandleLogRunnable();

    public void add(LoggingBean log)
    {
        queue.add(log);
        logger.debug("将日志加入缓冲区，缓冲区日志总条数[{}],日志内容：{}", queue.size(), log.toString());
        //判断缓冲区是否达到限制
        if (queue.size() >= maxLogCount)
        {
            logger.info("缓冲区已达到限制数：[{}],开始进行日志入库！", maxLogCount);
            handleLog();
        }

    }

    public void handleLog()
    {
        if (queue.size() >= maxLogCount)
        {
            executorService.submit(logHandler);
        }
    }

    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    public void close()
    {
        if ( !queue.isEmpty())
        {
            LogPersist.saveLog();
        }
        logger.info("关闭日志处理线程池,保存缓冲区的日志！");
        executorService.shutdown();
    }

    private static class HandleLogRunnable implements Runnable
    {

        public void run()
        {
            LogPersist.saveLog();
        }
    }

    private static class LogPersist
    {
        public static void saveLog()
        {
            int size = queue.size();
            logger.info("缓冲区日志（数量：{}）开始入库", size);
            Multimap<LoggingTypeEnum, LoggingBean> multimap = ArrayListMultimap.create();
            for (int i = 0; i < size; i++ )
            {
                LoggingBean loggingBean = queue.remove();
                multimap.put(loggingBean.getLogType(), loggingBean);
            }

            for (LoggingTypeEnum loggingType : multimap.keySet())
            {
                try
                {
                    LoggingHolder.getLoggingService(loggingType).insertLogBatch(multimap.get(loggingType));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            multimap.clear();
        }
    }
}
