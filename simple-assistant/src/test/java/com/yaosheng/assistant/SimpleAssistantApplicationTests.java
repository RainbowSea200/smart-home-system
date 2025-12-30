package com.yaosheng.assistant;

import com.yaosheng.assistant.mapper.TVMapper;
import com.yaosheng.assistant.mapper.redis.RedisMapper;
import com.yaosheng.assistant.pojo.AirConditioner;
import com.yaosheng.assistant.pojo.Light;
import com.yaosheng.assistant.pojo.TV;
import com.yaosheng.assistant.pojo.WaterHeater;
import com.yaosheng.assistant.service.AirConService;
import com.yaosheng.assistant.service.LightService;
import com.yaosheng.assistant.service.TVService;
import com.yaosheng.assistant.service.WaterHeaterService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SimpleAssistantApplicationTests {

    @Resource
    private AirConService airConService;
    @Resource
    private LightService lightService;
    @Resource
    private WaterHeaterService waterHeaterService;
    @Resource
    private TVService tvService;

    @Test
    void contextLoads() {
    }

    @Test
    void testAirConService(){
        airConService.addAirCon("testName5","testImage5");
        airConService.addAirCon("testName6","testImage6");
        airConService.addAirCon("testName7","testImage7");
    }
    @Test
    void testSelectAirCon(){
//        List<AirConditioner> allAirCon = airConService.getAllAirCon();
//        System.out.println(allAirCon);

        System.out.println(airConService.getAirConById(22));
    }
    @Test
    void updateAirCon(){
        AirConditioner airConditioner = new AirConditioner("testName12","testImage12");
        airConditioner.setId(22);
        airConService.updateAirCon(airConditioner);
    }
    @Test
    void deleteAirCon(){
        airConService.deleteAirConById(22);
    }

    @Test
    void testAdd(){
       lightService.addLight("testName1","testImage1");
       lightService.addLight("testName2","testImage2");
       lightService.addLight("testName3","testImage3");
       tvService.addTV("testName4","testImage4");
       tvService.addTV("testName5","testImage5");
       tvService.addTV("testName6","testImage6");
       waterHeaterService.addWaterHeater("testName7","testImage7");
       waterHeaterService.addWaterHeater("testName8","testImage8");
       waterHeaterService.addWaterHeater("testName9","testImage9");
    }

    @Test
    void testSelect(){
//        System.out.println(lightService.getAllLight());
//        System.out.println(tvService.getAllTV());
//        System.out.println(waterHeaterService.getAllWaterHeater());
        System.out.println(lightService.getLightById(2));
        System.out.println(tvService.getTVById(2));
        System.out.println(waterHeaterService.getWaterHeaterById(2));
    }

    @Test
    void testUpdate(){
        Light light = new Light("testName10","testImage10");
        light.setId(1);
        lightService.updateLight(light);


        TV tv = new TV("testName11","testImage11");
        tv.setId(1);
        tvService.updateTV(tv);

        WaterHeater waterHeater = new WaterHeater("testName12","testImage12");
        waterHeater.setId(1);
        waterHeaterService.updateWaterHeater(waterHeater);
    }

    @Test
    void testDelete(){
        lightService.deleteLightById(1);
        tvService.deleteTVById(1);
        waterHeaterService.deleteWaterHeaterById(1);
    }


}
