package com.pg.screen.utils;

import com.mybatisflex.core.row.Row;
import com.pg.screen.enums.WorkOrderUnitEnum;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 工具共通类
 *
 * @author c.chuang
 */
public class ToolUtils {

    private static final List<String> SORT_LIST = WorkOrderUnitEnum.getFullName();

    public static List<Long> buildList(List<Row> paramList, String key1, String key2) {
        List<Long> aroList = new LinkedList<>();
        for (String s : SORT_LIST) {
            if (CollectionUtils.isEmpty(paramList)) {
                aroList.add(0L);
                continue;
            }
            boolean flag = false;
            for (Row row : paramList) {
                if (s.equals(row.getString(key1))) {
                    aroList.add(row.getLong(key2));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                aroList.add(0L);
            }
        }
        return aroList;
    }

    public static List<Double> buildListForDouble(
            List<Row> paramList, String key1, String key2) {
        List<Double> aroList = new LinkedList<>();
        for (String s : SORT_LIST) {
            if (CollectionUtils.isEmpty(paramList)) {
                aroList.add(0.00);
                continue;
            }
            boolean flag = false;
            for (Row row : paramList) {
                if (s.equals(row.getString(key1))) {
                    aroList.add(row.getDouble(key2));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                aroList.add(0.00);
            }
        }
        return aroList;
    }

    public static String[] getBeforeDates(int amount) {
        if (amount == 0) {
            return null;
        }
        String[] resultStr = new String[amount + 1];
        // 得到日历
        Calendar calendar = Calendar.getInstance();
        // 把当前时间赋给日历
        Date date = new Date();
        calendar.setTime(date);
        int j = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        resultStr[j] = sdf.format(date);
        for (int i = 1; i <= amount; i++) {
            calendar.add(Calendar.MONTH, -1);
            Date time = calendar.getTime();
            j++;
            resultStr[j] = sdf.format(time);
        }
        return resultStr;
    }

    public static Long calculateDiffMonth(String dateStr1, String dateStr2) {
        LocalDate parse = LocalDate.parse(dateStr1);
        LocalDate parse1 = LocalDate.parse(dateStr2);
        return ChronoUnit.MONTHS.between(parse, parse1);
    }

    public static LocalDate findClosestDate(List<LocalDate> dateList, LocalDate targetDate) {
        LocalDate closestDate = null;
        long minDifference = Long.MAX_VALUE;
        for (LocalDate date : dateList) {
            long difference = Math.abs(ChronoUnit.DAYS.between(date, targetDate));
            if (difference < minDifference) {
                minDifference = difference;
                closestDate = date;
            }
        }
        return closestDate;
    }

}
