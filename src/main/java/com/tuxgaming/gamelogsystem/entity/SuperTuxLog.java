package com.tuxgaming.gamelogsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "supertux_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperTuxLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 200)
    private String levelName;
    
    @Column(nullable = false)
    private Integer coinsCollected;
    
    @Column(nullable = false)
    private Integer secretsFound;
    
    @Column(nullable = false)
    private Float timeNeeded;
    
    @Column(nullable = false)
    private Integer badguysKilled;
    
    @Column(nullable = false)
    private Boolean solved;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
