package com.tuxgaming.gamelogsystem.parser;

import com.tuxgaming.gamelogsystem.entity.ETRLog;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ETRLogParser {
    
    public List<ETRLog> parseLogFile(String filePath) {
        List<ETRLog> logs = new ArrayList<>();
        
        // *[group] default [course] bunny_hill [plyr] gyumin [pts] 443 [herr] 23 [time] 30.7
        Pattern pattern = Pattern.compile(
            "\\*\\[group\\]\\s+\\w+\\s+" +
            "\\[course\\]\\s+(\\w+)\\s+" +
            "\\[plyr\\]\\s+(\\w+)\\s+" +
            "\\[pts\\]\\s+(\\d+)\\s+" +
            "\\[herr\\]\\s+(\\d+)\\s+" +
            "\\[time\\]\\s+([\\d.]+)"
        );
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String course = matcher.group(1);
                    String player = matcher.group(2);
                    int points = Integer.parseInt(matcher.group(3));
                    int herrings = Integer.parseInt(matcher.group(4));
                    float time = Float.parseFloat(matcher.group(5));
                    
                    ETRLog log = ETRLog.builder()
                        .playerName(player)
                        .courseName(course)
                        .points(points)
                        .herrings(herrings)
                        .time(time)
                        .build();
                    
                    logs.add(log);
                }
            }
        } catch (IOException e) {
            System.err.println("ETR 로그 파일 읽기 실패: " + e.getMessage());
        }
        
        return logs;
    }
}
