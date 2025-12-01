package com.tuxgaming.gamelogsystem.parser;

import com.tuxgaming.gamelogsystem.entity.NeverballLog;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NeverballLogParser {
    
    public List<NeverballLog> parseLogFile(String filePath) {
        List<NeverballLog> logs = new ArrayList<>();
        String currentLevel = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                
                // 레벨 정보 추출
                if (line.startsWith("level")) {
                    String[] parts = line.split(" ");
                    if (parts.length >= 4) {
                        currentLevel = parts[3];
                    }
                }
                // 스코어 정보 추출
                else {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        String playerName = parts[2];
                        
                        // Hard, Medium, Easy 제외
                        if (!playerName.equals("Hard") && 
                            !playerName.equals("Medium") && 
                            !playerName.equals("Easy")) {
                            
                            try {
                                int timeMs = Integer.parseInt(parts[0]);
                                int coins = Integer.parseInt(parts[1]);
                                
                                NeverballLog log = NeverballLog.builder()
                                    .playerName(playerName)
                                    .timeMs(timeMs)
                                    .timeSec(timeMs / 1000.0f)
                                    .coins(coins)
                                    .levelName(currentLevel)
                                    .build();
                                
                                logs.add(log);
                            } catch (NumberFormatException e) {
                                // 숫자 변환 실패시 스킵
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Neverball 로그 파일 읽기 실패: " + e.getMessage());
        }
        
        return logs;
    }
}
