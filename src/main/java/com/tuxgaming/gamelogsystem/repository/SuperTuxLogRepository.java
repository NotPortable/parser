package com.tuxgaming.gamelogsystem.repository;

import com.tuxgaming.gamelogsystem.entity.SuperTuxLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperTuxLogRepository extends JpaRepository<SuperTuxLog, Long> {
    List<SuperTuxLog> findTop10ByOrderByCreatedAtDesc();
}