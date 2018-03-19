package com.sucheon.box.server.app.service;

import com.sucheon.box.server.app.dao.DailyLogRepository;
import com.sucheon.box.server.app.model.daily.DailyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 日志service
 */
@Service("DailyLogService")
public class DailyLogService {
    @Autowired
    DailyLogRepository dailyLogRepository;

    public void save(DailyLog dailyLog) {

        dailyLogRepository.save(dailyLog);
    }
}
