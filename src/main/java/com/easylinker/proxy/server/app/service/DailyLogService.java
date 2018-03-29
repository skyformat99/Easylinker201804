package com.easylinker.proxy.server.app.service;

import com.easylinker.proxy.server.app.dao.DailyLogRepository;
import com.easylinker.proxy.server.app.model.daily.DailyLog;
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
