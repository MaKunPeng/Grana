package com.dust;

import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class FunctionTest {
	static List<List<Integer>> res = new LinkedList<>();
	
	public static void main(String[] args) {
//		Apple.printApple("123");
//		
//		Thread a = new Thread(() -> {System.out.println("Thread run");});
//		a.start();
//		String name = ManagementFactory.getRuntimeMXBean().getName();
//		System.out.println(name);
		int[] nums = {1,2,3};
		permute(nums);
		System.out.println(res);
	}
	
	static void permute(int[] nums) {
		LinkedList<Integer> track = new LinkedList<>();
		backtrack(nums, track);
	}

	private static void backtrack(int[] nums, LinkedList<Integer> track) {
		if (track.size() == nums.length) {
			res.add(new LinkedList(track));
			return;
		}
		for (int i = 0; i < nums.length; i++) {
			if (track.contains(nums[i]))
				continue;
			track.add(nums[i]);
			backtrack(nums, track);
			track.removeLast();
		}
	}
}

class Apple {
	static void printApple(String price) {
		Test a = System.out::println;
		a.print(price);
		
		Supplier<String> function1 = () -> {return "aaa";};
		System.out.println(function1.get());
		
		Test b = Nvidia::add;
		b.print(price);
	}
}

class Nvidia {
	static int add(int a, int b) {
		return a + b;
	}
	
	static int add(String a) {
		System.out.println(a);
		return Integer.parseInt(a);
	}
}

@FunctionalInterface
interface Test {
	void print(String str);
}