package com.wbw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Test
    public void test() {
        int[] ints = new int[4];
        int[] ints2 = {1, 2, 3, 1};
        int[] nums = new int[]{1, 2, 3, 1};
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        System.out.println((set.size() == nums.length));
    }
}
