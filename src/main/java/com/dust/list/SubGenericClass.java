package com.dust.list;

import java.util.List;

public class SubGenericClass extends MyInterface<String> {
	
	@Override
	public void test(String t) {
		System.out.println("Sub");
	}
	
	public static void main(String[] args) {
		MyInterface<String> i = new SubGenericClass();
		i.test(null);
	}
	
//	public void test2(List<String> list) {
//		
//	}
//	
//	public void test2(List<Integer> list) {
//		
//	}
}
