package com.tuxgaming.gamelogsystem.controller;

import com.tuxgaming.gamelogsystem.entity.*;
import com.tuxgaming.gamelogsystem.service.GameLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class GameLogController {
    
    private final GameLogService gameLogService;
    
    /**
     * Neverball 최근 로그 조회
     */
    @GetMapping("/neverball")
    public List<NeverballLog> getNeverballLogs() {
        return gameLogService.getRecentNeverballLogs();
    }
    
    /**
     * ETR 최근 로그 조회
     */
    @GetMapping("/etr")
    public List<ETRLog> getETRLogs() {
        return gameLogService.getRecentETRLogs();
    }
    
    /**
     * Frozen Bubble 최근 로그 조회
     */
    @GetMapping("/frozen-bubble")
    public List<FrozenBubbleLog> getFrozenBubbleLogs() {
        return gameLogService.getRecentFrozenBubbleLogs();
    }
    
    /**
     * 헬스 체크
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
