package com.example.demo.Controller;

import com.example.demo.Service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping("/test-api")
    public String testApiData() {
        return holidayService.testApiData();
    }
}