package com.tuxgaming.gamelogsystem.service;

import com.tuxgaming.gamelogsystem.entity.*;
import com.tuxgaming.gamelogsystem.parser.*;
import com.tuxgaming.gamelogsystem.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameLogService {
    
    private final NeverballLogRepository neverballRepo;
    private final ETRLogRepository etrRepo;
    private final FrozenBubbleLogRepository frozenBubbleRepo;
    
    private final NeverballLogParser neverballParser;
    private final ETRLogParser etrParser;
    private final FrozenBubbleLogParser frozenBubbleParser;
    
    @Value("${game.log.neverball}")
    private String neverballLogPath;
    
    @Value("${game.log.etr}")
    private String etrLogPath;
    
    @Value("${game.log.frozenbubble}")
    private String frozenBubbleLogPath;
    
    // 파일별 마지막 수정 시간 저장
    private Map<String, Long> lastModifiedTimes = new HashMap<>();
    
    /**
     * 10초마다 로그 파일 확인 및 파싱
     */
    @Scheduled(fixedDelayString = "${game.log.scan.interval}")
    @Transactional
    public void scanAndParseLogFiles() {
        log.info("로그 파일 스캔 시작");
        
        parseNeverballLog();
        parseETRLog();
        parseFrozenBubbleLog();
        
        log.info("로그 파일 스캔 완료");
    }
    
    private void parseNeverballLog() {
        File file = new File(neverballLogPath);
        if (!file.exists()) {
            log.warn("Neverball 로그 파일 없음: {}", neverballLogPath);
            return;
        }
        
        long lastModified = file.lastModified();
        Long previousModified = lastModifiedTimes.get("neverball");
        
        // 파일이 변경된 경우에만 파싱
        if (previousModified == null || lastModified > previousModified) {
            log.info("Neverball 로그 파싱 시작");
            
            List<NeverballLog> logs = neverballParser.parseLogFile(neverballLogPath);
            
            // 기존 데이터와 비교해서 새로운 것만 저장
            for (NeverballLog log : logs) {
                // 중복 체크 로직 (선택사항)
                neverballRepo.save(log);
            }
            
            lastModifiedTimes.put("neverball", lastModified);
            log.info("Neverball 로그 {} 개 저장 완료", logs.size());
        }
    }
    
    private void parseETRLog() {
        File file = new File(etrLogPath);
        if (!file.exists()) {
            log.warn("ETR 로그 파일 없음: {}", etrLogPath);
            return;
        }
        
        long lastModified = file.lastModified();
        Long previousModified = lastModifiedTimes.get("etr");
        
        if (previousModified == null || lastModified > previousModified) {
            log.info("ETR 로그 파싱 시작");
            
            List<ETRLog> logs = etrParser.parseLogFile(etrLogPath);
            
            for (ETRLog log : logs) {
                etrRepo.save(log);
            }
            
            lastModifiedTimes.put("etr", lastModified);
            log.info("ETR 로그 {} 개 저장 완료", logs.size());
        }
    }
    
    private void parseFrozenBubbleLog() {
        File file = new File(frozenBubbleLogPath);
        if (!file.exists()) {
            log.warn("Frozen Bubble 로그 파일 없음: {}", frozenBubbleLogPath);
            return;
        }
        
        long lastModified = file.lastModified();
        Long previousModified = lastModifiedTimes.get("frozenbubble");
        
        if (previousModified == null || lastModified > previousModified) {
            log.info("Frozen Bubble 로그 파싱 시작");
            
            List<FrozenBubbleLog> logs = frozenBubbleParser.parseLogFile(frozenBubbleLogPath);
            
            for (FrozenBubbleLog log : logs) {
                frozenBubbleRepo.save(log);
            }
            
            lastModifiedTimes.put("frozenbubble", lastModified);
            log.info("Frozen Bubble 로그 {} 개 저장 완료", logs.size());
        }
    }
    
    // 조회 메서드들
    public List<NeverballLog> getRecentNeverballLogs() {
        return neverballRepo.findTop10ByOrderByCreatedAtDesc();
    }
    
    public List<ETRLog> getRecentETRLogs() {
        return etrRepo.findTop10ByOrderByCreatedAtDesc();
    }
    
    public List<FrozenBubbleLog> getRecentFrozenBubbleLogs() {
        return frozenBubbleRepo.findTop10ByOrderByCreatedAtDesc();
    }
}
