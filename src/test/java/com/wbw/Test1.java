package com.wbw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Test
    public void test() throws Exception {

        String date ="2020-01-13T16:08:08.000Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = format.parse(date);
        String result = defaultFormat.format(time);

        String date2 ="2020-04-09T23:00:00+08:00";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date time2 = format2.parse(date2);
        String result2 = defaultFormat.format(time2);

        String date1 ="2020-01-13T16:00:00.000+0000";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'+'SSSS");
        Date time1 = format1.parse(date1);
        String result1 = defaultFormat.format(time1);

//        int[] ints = new int[4];
//        int[] ints2 = {1, 2, 3, 1};
//        int[] nums = new int[]{1, 2, 3, 1};
//        for (int num : nums) {
//
//        }
//        char a = 'a';
//        char b = 97;
//        int c = '1';
//        int d = '2';
//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(c);
//        System.out.println(d);
//        Arrays.sort(nums);
//        Set<Integer> set = new HashSet<>();
//        for (int num : nums) {
//            set.add(num);
//        }

    }
}
