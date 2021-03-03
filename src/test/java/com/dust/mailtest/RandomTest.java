package com.dust.mailtest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;;

public class RandomTest {
    private HashMap<String, Integer> awards = new HashMap<>();

    @Before
    public void before() {
        awards.put("谢谢参与", 240);
        awards.put("四等奖", 30);
        awards.put("三等奖", 20);
        awards.put("二等奖", 10);
        awards.put("一等奖", 1);
    }
    
    @Test
    public void test() {
        for (int i = 0; i < 200; i++) {
            System.err.println(i);
            randomGetAward(); 
        }
    }
    
    private void randomGetAward() {
        int sumOfProbability = 0;
        Set<Entry<String, Integer>> entrySet = awards.entrySet();
        Iterator<Entry<String, Integer>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            sumOfProbability += iterator.next().getValue();
        }
        
        Iterator<Entry<String, Integer>> iterator2 = entrySet.iterator();
        // 随机选取范围内数字
        int randomNum = ThreadLocalRandom.current().nextInt(sumOfProbability);
        int range = 0;
        System.out.println("random: " + randomNum + "   sum: " + sumOfProbability);
        
        while (iterator2.hasNext()) {
            Entry<String, Integer> award = iterator2.next();
            range += award.getValue();
            // 如果随机数字落在范围内
            if (randomNum < range) {
                System.out.println(award.getKey());
                break;
            }
        }
    }
}
