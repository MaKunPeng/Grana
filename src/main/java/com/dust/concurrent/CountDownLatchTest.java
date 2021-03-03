package com.dust.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
	private CountDownLatch latch = new CountDownLatch(3);
	
	public void test() {
		for (int i = 1; i <= 2; i++) {
			Thread t = new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(3);
					System.out.println("执行完成");
				} catch (Exception e) {
					e.printStackTrace();
				}
				latch.countDown();
			});
			t.start();
		}
		try {
			latch.await();
			System.out.println("解锁");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CountDownLatchTest test = new CountDownLatchTest();
		test.test();
	}
}
