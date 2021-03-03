package com.dust.guava;

import static com.google.common.base.Preconditions.checkState;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 计时器
 * 简化了guava中Stopwatch的实现，提供了更方便的API
 * 
 * @author mkp
 *
 */
public final class Stopwatch {
    private boolean isRunning;
    private long elapsedNanos;
    private long startTick;

    private Stopwatch() {

    }

    public static Stopwatch createStarted() {
        return new Stopwatch().start();
    }

    public static Stopwatch creawteUnstarted() {
        return new Stopwatch();
    }
    
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 开始计时
     * 
     * @return
     */
    public Stopwatch start() {
        checkState(!isRunning, "This stopwatch is already running.");
        isRunning = true;
        startTick = System.nanoTime();
        return this;
    }

    /**
     * 停止计时，并记录间隔
     * 
     * @return
     */
    public Stopwatch stop() {
        long tick = System.nanoTime();
        checkState(isRunning, "This stopwatch is already stopped.");
        isRunning = false;
        elapsedNanos += tick - startTick;
        return this;
    }

    /**
     * 重置计时器
     * 
     * @return
     */
    public Stopwatch reset() {
        elapsedNanos = 0;
        isRunning = false;
        return this;
    }

    /**
     * 重置并重新启动计时器
     * 
     * @return
     */
    public Stopwatch resetAndStart() {
        return reset().start();
    }

    private long elapsedNanos() {
        return isRunning ? System.nanoTime() - startTick + elapsedNanos : elapsedNanos;
    }

    /**
     * 获取截止到当前的总计时间隔，不会丢失任何精度
     * 
     * @return
     */
    public Duration elapsed() {
        return Duration.ofNanos(elapsedNanos());
    }

    /**
     * 获取指定单位的时间间隔，可能有精度损失
     * 注意：测量的开销可能超过一微秒，因此在这里指定纳秒精度通常是没用的。
     * 
     * @param timeUnit
     * @return
     */
    public double elapsed(TimeUnit timeUnit) {
        long nanos = elapsedNanos();
        return (double) nanos / NANOSECONDS.convert(1, timeUnit);
    }
    
    public double elapsedMillis() {
        return elapsed(MILLISECONDS);
    }

    public double elapsedSeconds() {
        return elapsed(SECONDS);
    }
    
    /**
     * 返回当前运行时间的字符串表示
     * 如：12.3333 s
     */
    @Override
    public String toString() {
        long nanos = elapsedNanos();
        
        TimeUnit unit = chooseUnit(nanos);
        double value = (double) nanos / NANOSECONDS.convert(1, unit);
        
        return String.format("%.4g %s", value, abbreviate(unit));
    }

    /**
     * 选择最接近的时间单位
     * 
     * @param nanos
     * @return
     */
    private static TimeUnit chooseUnit(long nanos) {
        if (DAYS.convert(nanos, NANOSECONDS) > 0) {
            return DAYS;
        }
        if (HOURS.convert(nanos, NANOSECONDS) > 0) {
            return HOURS;
        }
        if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
            return MINUTES;
        }
        if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
            return SECONDS;
        }
        if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
            return MILLISECONDS;
        }
        if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
            return MICROSECONDS;
        }
        return NANOSECONDS;
    }

    private static String abbreviate(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return "ns";
            case MICROSECONDS:
                return "\u03bcs"; //μs
            case MILLISECONDS:
                return "ms";
            case SECONDS:
                return "s";
            case MINUTES:
                return "min";
            case HOURS:
                return "h";
            case DAYS:
                return "d";
            default:
                throw new AssertionError();
        }
    }
}
