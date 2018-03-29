package com.easylinker.proxy.server.app.dao;

import com.easylinker.proxy.server.app.model.daily.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyLogRepository extends JpaRepository<DailyLog,Long> {
}
