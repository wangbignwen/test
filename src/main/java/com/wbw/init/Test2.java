package com.wbw.init;

import com.wbw.init.Orders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class Test2 {
    @Resource
    List<Orders> ordersList;

    @Resource
    Map<String, Orders> ordersMap;

    public void test() {
        ordersList.forEach(Orders::doJob);
        ordersMap.keySet().forEach(o->{
            if (o.contains("order1")){
                ordersMap.get(o).doJob();
            }
        });
    }


}
