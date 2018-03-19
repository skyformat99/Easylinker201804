package com.sucheon.box.server.app.utils;

import com.sucheon.box.server.app.model.daily.DailyLog;
import com.sucheon.box.server.app.service.DailyLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LogPrinter {
    private static final Logger logger = LoggerFactory.getLogger(LogPrinter.class);
    @Autowired
    DailyLogService dailyLogService;

    public void log(String event, String operate, String who) {
        DailyLog dailyLog = new DailyLog();
        dailyLog.setDate(new Date());
        dailyLog.setEvent(event);
        dailyLog.setOperate(operate);
        dailyLog.setWho(who);
        dailyLogService.save(dailyLog);
        logger.info("在时间[" + dailyLog.getDate() + "][" + dailyLog.getWho() + "]在事件[" + dailyLog.getEvent() + "]执行了[" + dailyLog.getOperate() + "]操作.");

    }
}
