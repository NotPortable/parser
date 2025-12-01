package com.tuxgaming.gamelogsystem.parser;

import com.tuxgaming.gamelogsystem.entity.FrozenBubbleLog;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FrozenBubbleLogParser {
    
    public List<FrozenBubbleLog> parseLogFile(String filePath) {
        List<FrozenBubbleLog> logs = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String name = null;
            Integer level = null;
            Integer piclevel = null;
            Float time = null;
            
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                
                // 'name' => 'wjddn',
                if (line.contains("'name'")) {
                    Pattern pattern = Pattern.compile("'name'\\s*=>\\s*'([^']+)'");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        name = matcher.group(1);
                    }
                }
                // 'level' => 1,
                else if (line.contains("'level'")) {
                    Pattern pattern = Pattern.compile("'level'\\s*=>\\s*(\\d+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        level = Integer.parseInt(matcher.group(1));
                    }
                }
                // 'piclevel' => 2,
                else if (line.contains("'piclevel'")) {
                    Pattern pattern = Pattern.compile("'piclevel'\\s*=>\\s*(\\d+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        piclevel = Integer.parseInt(matcher.group(1));
                    }
                }
                // 'time' => '69.039'
                else if (line.contains("'time'")) {
                    Pattern pattern = Pattern.compile("'time'\\s*=>\\s*'([\\d.]+)'");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        time = Float.parseFloat(matcher.group(1));
                        
                        // 모든 필드 수집 완료
                        if (name != null && level != null && piclevel != null) {
                            FrozenBubbleLog log = FrozenBubbleLog.builder()
                                .playerName(name)
                                .level(level)
                                .piclevel(piclevel)
                                .time(time)
                                .build();
                            
                            logs.add(log);
                            
                            // 초기화
                            name = null;
                            level = null;
                            piclevel = null;
                            time = null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Frozen Bubble 로그 파일 읽기 실패: " + e.getMessage());
        }
        
        return logs;
    }
}
