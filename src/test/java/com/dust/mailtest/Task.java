package com.dust.mailtest;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Task implements Delayed {
    private long delayTime;
    private long expireTime;
    private String taskName;
    
    public Task(String name, long delayTime) {
        this.taskName = name;
        this.delayTime = delayTime;
        this.expireTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.SECONDS);
    }
    
    @Override
    public int compareTo(Delayed o) {
        long d = (this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
        return d > 0 ? 1 : d < 0 ? -1 :0;
//        return (int) d;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expireTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }
    
    public static void main(String[] args) {
        final DelayQueue<Task> taskQueue = new DelayQueue<>();
        Thread producer = new Thread(new Runnable() {
            
            @Override
            public void run() {
                int i = 1;
                while (i < 10) {
                    taskQueue.put(new Task("task" + i, i));
                    i++;
                }
            }
        });
        
        producer.start();
        
        Thread consumer = new Thread(new Runnable() {
            
            @Override
            public void run() {
                while (true) {
                    Task task;
                    try {
                        task = taskQueue.take();
                        System.out.println(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
            }
        });
        
        consumer.start();
    }
    
    public void print() {
        System.out.println("task:" + taskName + "expire time: " + expireTime);
    }

    @Override
    public String toString() {
        return "Task:" + taskName + " [delayTime=" + delayTime + ", expireTime=" + expireTime + "]";
    }

    
}
