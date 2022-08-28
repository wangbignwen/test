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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        jsonObject.put("k", "v");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("k1","v1");
        JSONArray jSONArray1 = new JSONArray();
        jSONArray1.add(jsonObject1);
        jsonObject.put("k2",jSONArray1);
        JSONArray jSONArray = new JSONArray();
        jSONArray.add(jsonObject);
        List<Map<String, String>> mapsList = JSON.parseObject(JSON.toJSONString(jSONArray), new TypeReference<List<Map<String, String>>>() {
        });


        //restTemplateText();

        // 调数据库
        //service1.addUser("王炳文");
        //excelTest.exportExcel(response);
        return "success";
    }

    private void restTemplateText() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        // -------------------------------> 解决(响应数据可能)中文乱码 的问题
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1); // 移除原来的转换器
        // 设置字符编码为utf-8
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1, converter); // 添加新的转换器(注:convert顺序错误会导致失败)
        restTemplate.setMessageConverters(converterList);

        // -------------------------------> (选择性设置)请求头信息
        // HttpHeaders实现了MultiValueMap接口
        HttpHeaders httpHeaders = new HttpHeaders();
        // 给请求header中添加一些数据
        httpHeaders.add("JustryDeng", "这是一个大帅哥!");

        // -------------------------------> 注:GET请求 创建HttpEntity时,请求体传入null即可
        // 请求体的类型任选即可;只要保证 请求体 的类型与HttpEntity类的泛型保持一致即可
        String httpBody = null;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpBody, httpHeaders);

        // -------------------------------> URI
        StringBuffer paramsURL = new StringBuffer("http://127.0.0.1:8080/http/doHttpTest");
        // 字符数据最好encoding一下;这样一来，某些特殊字符才能传过去(如:flag的参数值就是“&”,不encoding的话,传不过去)
        paramsURL.append("?flag=" + URLEncoder.encode("&&", "utf-8"));
        URI uri = URI.create(paramsURL.toString());

        //  -------------------------------> 执行请求并返回结果
        // 此处的泛型  对应 响应体数据   类型;即:这里指定响应体的数据装配为String
        ResponseEntity<String> response =
                restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        // -------------------------------> 响应信息
        //响应码,如:401、302、404、500、200等
        System.out.println(response.getStatusCodeValue());
        // 响应头
        System.out.println(JSON.toJSON(response.getHeaders()));
        // 响应体
        if(response.hasBody()) {
            System.out.println(response.getBody());
        }
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
