package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramBotService {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.chat.id}")
    private String chatId;

    private final String TELEGRAM_API_URL = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    @Autowired
    private RestTemplate restTemplate;

    public void sendMessage(String message) {
        String url = String.format(TELEGRAM_API_URL, botToken, chatId, message);
        restTemplate.getForObject(url, String.class);
    }
}
