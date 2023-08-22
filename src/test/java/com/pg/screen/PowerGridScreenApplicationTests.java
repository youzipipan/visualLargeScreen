package com.pg.screen;

import com.pg.screen.dao.DpGdkkxZbDao;
import com.pg.screen.utils.TimeInterval;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

@SpringBootTest
class PowerGridScreenApplicationTests {

    @Resource
    private DpGdkkxZbDao dpGdkkxZbDao;

    @Test
    void test() {
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        System.out.println(timeInterval.getBeginDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }


}
