package com.tuxgaming.gamelogsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "etr_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ETRLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String playerName;
    
    @Column(length = 100)
    private String courseName;
    
    @Column(nullable = false)
    private Integer points;
    
    @Column(nullable = false)
    private Integer herrings;  // 물고기
    
    @Column(nullable = false)
    private Float time;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
