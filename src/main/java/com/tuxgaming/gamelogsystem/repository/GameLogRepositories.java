package com.tuxgaming.gamelogsystem.repository;

import com.tuxgaming.gamelogsystem.entity.SuperTuxLog;
import com.tuxgaming.gamelogsystem.entity.ETRLog;
import com.tuxgaming.gamelogsystem.entity.FrozenBubbleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SuperTuxLogRepository extends JpaRepository<SuperTuxLog, Long> {
    List<SuperTuxLog> findTop10ByOrderByCreatedAtDesc();
}

@Repository
interface ETRLogRepository extends JpaRepository<ETRLog, Long> {
    List<ETRLog> findTop10ByOrderByCreatedAtDesc();
    List<ETRLog> findByPlayerName(String playerName);
}

@Repository
interface FrozenBubbleLogRepository extends JpaRepository<FrozenBubbleLog, Long> {
    List<FrozenBubbleLog> findTop10ByOrderByCreatedAtDesc();
    List<FrozenBubbleLog> findByPlayerName(String playerName);
}
