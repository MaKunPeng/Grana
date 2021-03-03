package com.dust.datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TrieTree {
    public static void main(String[] args) {
    	Map<String, String> risOrderMap = new HashMap<>();
    	risOrderMap.put("A", "B");
    	
    	String[] array = risOrderMap.keySet().toArray(new String[0]);
    	
    	System.out.println(Arrays.asList(array));
    	
    	StringBuilder sb = new StringBuilder().append("A");
    	System.out.println(sb.substring(1));
    }
}
