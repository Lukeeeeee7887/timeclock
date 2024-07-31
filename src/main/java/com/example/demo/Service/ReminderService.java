package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private TelegramBotService telegramBotService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendReminder() {
        boolean todayIsHoliday = holidayService.isHoliday();
        String message = todayIsHoliday ? "今天休假" : "别忘了打卡哦！";
        telegramBotService.sendMessage(message);
    }
}
