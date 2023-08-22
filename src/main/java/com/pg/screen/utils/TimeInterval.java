package com.pg.screen.utils;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

/**
 * @Author: liuweipeng
 * @Description:
 * @Date: 2023/7/17 15:34
 */
@Getter
public class TimeInterval {

    private LocalDateTime beginDateTime;

    private LocalDateTime endDateTime;

    private TimeInterval(LocalDateTime beginDateTime, LocalDateTime endDateTime) {
        this.beginDateTime = beginDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * 获取当前时间所在周 周一凌晨到当前时间23:59:59
     * @Return com.pg.screen.utils.TimeInterval
     */
    public static TimeInterval createOnMonday() {
        final LocalDate nowDate = LocalDate.now();
        final LocalDate mondayDate = nowDate.with(WeekFields.ISO.dayOfWeek(), 1L);
        return new TimeInterval(
                mondayDate.atTime(LocalTime.of(0, 0, 0)),
                nowDate.atTime(LocalTime.of(23, 59, 59))
        );
    }

    public String beginDateFormat(String format) {
        return format(beginDateTime, format);
    }

    public String endDateFormat(String format) {
        return format(endDateTime, format);
    }

    private String format(LocalDateTime localDateTime, String formatter) {
        return localDateTime.format(DateTimeFormatter.ofPattern(formatter));
    }
}
