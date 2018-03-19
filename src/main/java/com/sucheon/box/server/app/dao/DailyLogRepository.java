package com.sucheon.box.server.app.dao;

import com.sucheon.box.server.app.model.daily.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyLogRepository extends JpaRepository<DailyLog,Long> {
}
