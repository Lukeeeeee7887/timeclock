package com.example.demo.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService {

    private static final String HOLIDAY_API_URL = "https://data.taipei/api/v1/dataset/964e936d-d971-4567-a467-aa67b930f98e?scope=resourceAquire&limit=1000&offset=%d";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final ZoneId TAIPEI_ZONE = ZoneId.of("Asia/Taipei");

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public boolean isHoliday() {
        int offset = 0;
        boolean hasMore = true;

        LocalDate today = LocalDate.now(TAIPEI_ZONE);
        String formattedDate = today.format(DATE_FORMATTER);

        while (hasMore) {
            String url = String.format(HOLIDAY_API_URL, offset);
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response != null && response.has("result") && response.get("result").has("results")) {
                JsonNode results = response.get("result").get("results");

                for (JsonNode result : results) {
                    if (result.get("date").asText().equals(formattedDate)) {
                        return result.get("isholiday").asText().equals("是");
                    }
                }

                if (results.size() < 1000) {
                    hasMore = false;
                } else {
                    offset += 1000;
                }
            } else {
                hasMore = false;
            }
        }
        return false;
    }

    public String testApiData() {
        StringBuilder result = new StringBuilder();
        int offset = 0;
        boolean hasMore = true;
        List<String> fetchedDates = new ArrayList<>();

        try {
            while (hasMore) {
                String url = String.format(HOLIDAY_API_URL, offset);
                String jsonResponse = restTemplate.getForObject(url, String.class);
                
                if (jsonResponse == null || jsonResponse.isEmpty()) {
                    return "從 API 獲取數據失敗";
                }

                result.append("原始 API 響應（前 1000 個字符）：\n");
                result.append(jsonResponse.substring(0, Math.min(jsonResponse.length(), 1000)));
                result.append("\n\n");
                result.append("完整 JSON 響應長度: ").append(jsonResponse.length()).append(" 字符\n\n");

                JsonNode root = objectMapper.readTree(jsonResponse);
                JsonNode results = root.path("result").path("results");

                if (results.isArray()) {
                    for (JsonNode resultNode : results) {
                        fetchedDates.add(resultNode.get("date").asText());
                    }

                    if (results.size() < 1000) {
                        hasMore = false;
                    } else {
                        offset += 1000;
                    }
                } else {
                    hasMore = false;
                }
            }

            result.append("成功解析 ").append(fetchedDates.size()).append(" 個假日。\n");

            result.append("前 5 個假日日期: \n");
            fetchedDates.stream().limit(5).forEach(date -> result.append(date).append(", "));
            result.append("\n最後 5 個假日日期: \n");
            fetchedDates.stream().skip(Math.max(0, fetchedDates.size() - 5)).forEach(date -> result.append(date).append(", "));
            result.append("\n\n");

            LocalDate today = LocalDate.now(TAIPEI_ZONE);
            String formattedToday = today.format(DATE_FORMATTER);

            result.append("檢查今日的假日信息（").append(formattedToday).append("）：\n");

            if (isHoliday()) {
                result.append("今日（").append(formattedToday).append("）是法定假日。\n");
            } else {
                result.append("今日（").append(formattedToday).append("）不是法定假日。\n");
            }

        } catch (Exception e) {
            result.append("處理假日數據時出錯: ").append(e.getMessage()).append("\n");
            result.append("堆疊追蹤: \n");
            for (StackTraceElement element : e.getStackTrace()) {
                result.append(element.toString()).append("\n");
            }
            if (e.getCause() != null) {
                result.append("原因: ").append(e.getCause().getMessage()).append("\n");
            }
        }

        return result.toString();
    }
}
