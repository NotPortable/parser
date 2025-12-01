package com.tuxgaming.gamelogsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "frozen_bubble_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrozenBubbleLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String playerName;
    
    @Column(nullable = false)
    private Integer level;
    
    @Column(nullable = false)
    private Integer piclevel;
    
    @Column(nullable = false)
    private Float time;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
