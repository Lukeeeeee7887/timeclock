package com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Service.HolidayService;
import com.example.demo.Service.ReminderService;
import com.example.demo.Service.TelegramBotService;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
//    @Bean
//    public CommandLineRunner commandLineRunner(HolidayService holidayService, TelegramBotService telegramBotService) {
//        return args -> {
//            boolean todayIsHoliday = holidayService.isHoliday();
//            String message = todayIsHoliday ? "今天休假" : "今天不是休假日";
//            telegramBotService.sendMessage(message);
//        };
//    }
    

}
