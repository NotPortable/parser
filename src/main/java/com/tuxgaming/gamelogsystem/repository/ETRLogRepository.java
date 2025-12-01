package com.tuxgaming.gamelogsystem.repository;

import com.tuxgaming.gamelogsystem.entity.ETRLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ETRLogRepository extends JpaRepository<ETRLog, Long> {
    List<ETRLog> findTop10ByOrderByCreatedAtDesc();

    List<ETRLog> findByPlayerName(String playerName);
}