package com.dust.guava;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class StopWatchTest {
	public static void main(String[] args) throws Exception {
	    System.err.println("\u03bcs");
		Stopwatch stopwatch = Stopwatch.createStarted();
		Thread.sleep(1111);
		System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
		stopwatch.resetAndStart();
		Thread.sleep(2222);
		System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
		System.out.println(stopwatch);
		stopwatch.stop();
		System.out.println(stopwatch.elapsedSeconds());
		Thread.sleep(1234);
		System.out.println(stopwatch.elapsedSeconds());
		
		BigDecimal valueOf = BigDecimal.valueOf(0.1d);
	}
}
