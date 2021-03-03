package com.dust.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {
	private CyclicBarrier barrier = new CyclicBarrier(3);
	
	public void test() {
		for (int i = 0; i < 3; i++) {
			Runnable task = () -> {
				try {
					for (int j = 0; j < 5; j++) {
						TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3));
						System.out.println(Thread.currentThread().getId() + ": 完成第" + j + "次循环。");
						barrier.await();						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			Thread t = new Thread(task);
			t.start();
		}
	}
	
	public static void main(String[] args) {
		CyclicBarrierTest test = new CyclicBarrierTest();
		test.test();
	}
}
