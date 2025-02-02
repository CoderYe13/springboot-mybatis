package com.henu.kafka.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.henu.kafka.entity.Area;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class KafkaReceiveService {


    @KafkaListener(topics = {"HelloWorld"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String message = (String) kafkaMessage.get();
            System.out.println("-------------headers: " + record.headers() + ", topic: " + record.topic() + ", offset: " + record.offset() + "----------");

            String uri = "http://localhost:9001/mongo/acceptKafka";
            RestTemplate template = new RestTemplate();
            Area area=template.postForObject(uri, new Gson().fromJson(message,Area.class), Area.class);

            System.out.println("-------------headers: " + record.headers() + "is consumed" + "----------");
//            JsonParser parser = new JsonParser();
//            // 2.获得 根节点元素
//            JsonElement element = parser.parse(message);
//            // 3.根据 文档判断根节点属于 什么类型的 Gson节点对象
//            JsonObject root = element.getAsJsonObject();
//            String resultCode = root.get("id").getAsString();
//            // 4. 取得 节点 下 的某个节点的 value
//            String msg = root.getAsJsonArray("msg").toString();
//            if (msg.indexOf("{") == 2) {
//                msg = msg.substring(1, msg.length() - 1);
//            }
//
//            List<Area> list = new ArrayList<>();
//            Gson gson = new Gson();
//            list = gson.fromJson(parser.parse(msg), new TypeToken<List<Area>>() {
//            }.getType());
//            for (Area area : list) {
//               // areaMapper.updateAreaNameByAreaId(area.getAreaId(), area.getAreaId() + "苑");
//            }
            //  System.out.println("-------------headers: " + resultCode + "is consumed" + "----------");
        }
    }
}
