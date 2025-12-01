package com.tuxgaming.gamelogsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "neverball_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NeverballLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String playerName;
    
    @Column(nullable = false)
    private Integer timeMs;  // 밀리초
    
    @Column(nullable = false)
    private Float timeSec;   // 초
    
    @Column(nullable = false)
    private Integer coins;
    
    @Column(length = 200)
    private String levelName;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
