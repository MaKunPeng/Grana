package com.dust.datastructure;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 时间轮，存储定时任务的环形队列。底层采用数组实现，每个元素可以存放一个定时任务列表。
 * 
 * 插入、删除操作的时间复杂度都为O(1)
 * 
 * @author Aaron Ma
 *
 */
public class TimingWheel {
    /**
     * 默认缓冲区大小
     */
    private static final int STATIC_RING_SIZE = 64;
    /**
     * 长度必须得是 2∧n
     */
    private int bufferSize;
    /**
     * 环形缓冲区
     */
    private Object[] ringBuffer;
    /**
     * 线程池
     */
    private ExecutorService executorService;
    /**
     * 时间指针步数
     */
    private AtomicInteger tick = new AtomicInteger();
    /**
     * 缓冲区中元素个数
     */
    private AtomicInteger size = new AtomicInteger();
    /**
     * 停止标识
     */
    private volatile boolean stop = false;
    
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    
    private AtomicInteger taskId = new AtomicInteger();
    private AtomicBoolean started = new AtomicBoolean(false);
    private Map<Integer, Task> taskMap = new ConcurrentHashMap<>(16); 
    
    
    public TimingWheel(ExecutorService executorService) {
        this.executorService = executorService;
        this.bufferSize = STATIC_RING_SIZE;
    }
    
    /**
     * 添加任务
     * @param task
     * @return
     */
    public int addTask(Task task) {
        int key = task.getDelay();
        int id = 0;
        
        try {
            lock.lock();
            int index = mod(key, bufferSize);
            task.setIndex(index);
            Set<Task> tasks = get(index); 
            
            int cycleNum = cycleNum(key, bufferSize);
            task.setCycleNum(cycleNum);
            
            if (tasks != null) {
                tasks.add(task);
            } else {
                task.setIndex(index);
                Set<Task> taskSet = new HashSet<>();
                taskSet.add(task);
                ringBuffer[index] = taskSet;
            }
            id = taskId.incrementAndGet();
            task.setTaskId(id);
            taskMap.put(id, task);
            size.incrementAndGet();
        } finally {
            lock.unlock();
        }
        
        start();
        
        return id;
    }
    
    /**
     * 根据任务ID，取消任务
     * @param taskId
     * @return
     */
    public boolean cancel(int taskId) {
       boolean flag = false;
       Set<Task> tempTask = new HashSet<>();
       
       try {
           lock.lock();
           Task task = taskMap.get(taskId);
           if (task == null) {
               return false;
           }
           
           Set<Task> tasks = get(task.getIndex());
           for (Task item : tasks) {
               if (item.getDelay() == task.getDelay() && item.getCycleNum() == task.getCycleNum()) {
                   size.decrementAndGet();
                   flag = true;
                   taskMap.remove(taskId);
               } else {
                   tempTask.add(item);
               }
           }
           ringBuffer[task.getIndex()] = tempTask;
       } finally {
           lock.unlock();
       }
       return flag;
    }
    
    /**
     * 停止工作线程，不再执行任务
     * 如果force=true，直接强行中止
     * 如果force=false，且还有任务未执行，则阻塞一直到所有任务执行完成
     * 
     * @param force
     */
    public void stop(boolean force) {
        if (force) {
            stop = true;
            executorService.shutdownNow();
        } else {
            if (taskSize() > 0) {
                try {
                    lock.lock();
                    condition.await();
                    stop = true;
                } catch (InterruptedException e) {
                    
                } finally {
                    lock.unlock();
                }
            }
            executorService.shutdown();
        }
    }
    
    /**
     * 取出指定缓冲区索引下圈数为0的任务，即当前需要执行的任务
     * 
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    private Set<Task> remove(int index) {
        Set<Task> tempTask = new HashSet<>();
        Set<Task> result = new HashSet<>();
        
        Set<Task> tasks = (Set<Task>) ringBuffer[index];
        if (tasks == null) {
            return result;
        }
        
        for (Task task : tasks) {
            if (task.getCycleNum() == 0) {
                result.add(task);
                size2Notify();
            } else {
                task.setCycleNum(task.getCycleNum() - 1);
                tempTask.add(task);
            }
            
            taskMap.remove(task.getTaskId());
        }
        
        ringBuffer[index] = tempTask;
        
        return result;
    }
    
    /**
     * 当元素个数为0时，唤醒stop阻塞的线程
     */
    private void size2Notify() {
        try {
            lock.lock();
            size.decrementAndGet();
            if (size.get() == 0) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * 启动消费者线程
     */
    private void start() {
        if (!started.get()) {
            if (started.compareAndSet(started.get(), true)) {
                Thread job = new Thread(new TriggerJob());
                job.setName("consumer RingBuffer thread");
                job.start();
                started.set(true);
            }
        }
    }

    /**
     * 计算圈数
     * @param target
     * @param mod
     * @return
     */
    private int cycleNum(int target, int mod) {
        return target >> Integer.bitCount(mod);
    }

    /**
     * 相当于%取余，只针对2的n次方的模有效
     * 
     * @param target
     * @param mod
     * @return
     */
    private int mod(int target, int mod) {
        target += tick.get();
        return target & (mod - 1);
    }
    
    @SuppressWarnings("unchecked")
    private Set<Task> get(int index) {
        return (Set<Task>) ringBuffer[index];
    }
    
    public int taskSize() {
        return size.get();
    }

    /**
     * 任务单元
     * 
     * @author MaKunPeng
     *
     */
    public abstract static class Task extends Thread {
        /**
         * 缓冲区索引
         */
        private int index;
        /**
         * 圈数
         */
        private int cycleNum;
        /**
         * 延迟时间，单位：秒
         */
        private int delay;
        /**
         * 唯一任务标识
         */
        private int taskId;
           
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getCycleNum() {
            return cycleNum;
        }

        public void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            super.run();
        } 
    }
    
    /**
     * 工作线程，每秒前进一步，取当前秒数下所有待执行任务，提交至线程池
     * 
     * @author Aaron Ma
     *
     */
    private class TriggerJob implements Runnable {

        @Override
        public void run() {
            int index = 0;
            while (!stop){
                try {
                    Set<Task> tasks = remove(index);
                    for (Task task : tasks) {
                        executorService.submit(task);
                    }
                    
                    // 重新循环
                    if (++index > bufferSize - 1) {
                        index = 0;
                    }
                    // 步长1秒
                    tick.incrementAndGet();
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    
                }
            }
            
        }
        
    }
}
