package com.wbw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.wbw.excel.ExcelTest;
import com.wbw.service.Service1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/order")
public class MqController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqController.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private Service1 service1;

    @Resource
    private ExcelTest excelTest;


    @GetMapping("/add")
    public String addOrder(HttpServletResponse response) throws Exception {
        //参数1 指定要发送的交换机的名称..
        //参数2 指定要发送的routingkey
        //参数3 指定要发送的消息的本身数据
        //rabbitTemplate.convertAndSend("wbw_exchange", "wbw.add", "王炳文添加消息");
        //rabbitTemplate.convertAndSend("wbw_exchange", "wbw.delete", "王炳文删除消息");

        // 给过期队列发消息
        //rabbitTemplate.convertAndSend("wbw_exchange", "ttl.add", "王炳文给10秒后过期的队列添加消息");

        // 给死信队列发消息，采用简单模式，消息直接发到队列，routingKey为队列名
        //rabbitTemplate.convertAndSend("dlx_queue", "王炳文给死信队列发的消息");

        // 给发送的消息设置过期时间
        //rabbitTemplate.convertAndSend("wbw_exchange", "ttl.add", "王炳文给10秒后过期的队列添加消息");
//        rabbitTemplate.convertAndSend("dlx_queue", (Object) "王炳文发送了一条有过期时间的消息", new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setExpiration("5000");//设置该消息的过期时间
//                return message;
//            }
//        });

        // 打印日志
        for (int i = 0; i < 10; i++) {
            String str = "输出日志次数" + i;
            outLogToTxt(str);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "123");
        jsonObject.put("name", "wang");
        JSONArray jSONArray = new JSONArray();
        jSONArray.add(jsonObject);
        List<Map<String, String>> mapsList = JSON.parseObject(JSON.toJSONString(jSONArray), new TypeReference<List<Map<String, String>>>() {
        });

        // 调数据库
        //service1.addUser("王炳文");
        //excelTest.exportExcel(response);
        return "success";
    }


    // 输出日志到桌面
    private static void outLogToTxt(String str) throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\log.txt");
        if (!file.exists()) {
            boolean flag = file.createNewFile();
        }
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(str);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
