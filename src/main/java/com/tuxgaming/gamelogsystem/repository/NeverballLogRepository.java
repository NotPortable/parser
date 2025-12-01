package com.tuxgaming.gamelogsystem.repository;

import com.tuxgaming.gamelogsystem.entity.NeverballLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeverballLogRepository extends JpaRepository<NeverballLog, Long> {
    List<NeverballLog> findTop10ByOrderByCreatedAtDesc();
    List<NeverballLog> findByPlayerName(String playerName);
}
