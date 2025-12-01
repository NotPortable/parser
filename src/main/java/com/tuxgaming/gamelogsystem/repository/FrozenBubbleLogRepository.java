package com.tuxgaming.gamelogsystem.repository;

import com.tuxgaming.gamelogsystem.entity.FrozenBubbleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrozenBubbleLogRepository extends JpaRepository<FrozenBubbleLog, Long> {
    List<FrozenBubbleLog> findTop10ByOrderByCreatedAtDesc();

    List<FrozenBubbleLog> findByPlayerName(String playerName);
}