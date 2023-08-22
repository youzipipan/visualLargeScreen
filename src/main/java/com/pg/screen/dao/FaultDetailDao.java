package com.pg.screen.dao;

import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 故障明细 dao
 *
 * @author c.chuang
 */
@Repository
public class FaultDetailDao {

    /**
     * 线路故障分析-查询故障数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障数量
     */
    public Long selectTotal(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) AS COUNTS FROM FAULT_DETAIL WHERE  EVENT_TIME BETWEEN ? AND ?";
        Row row = Db.selectOneBySql(sql, beginDate, endDate);
        return row.getLong("COUNTS", 0L);
    }

    /**
     * 线路故障分析-根据故障类型查询线路故障分析
     *
     * @param faultType 故障类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    public List<Row> selectLineFaultAnalysisCount(
            String faultType, LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT COUNTY_CORPORATION, " +
                "            COUNT(1) OVER (PARTITION BY COUNTY_CORPORATION) AS COUNTS " +
                "     FROM FAULT_DETAIL " +
                "     WHERE FAULT_TYPE = ? AND EVENT_TIME BETWEEN ? AND ?";
        return Db.selectListBySql(sql, faultType, beginDate, endDate);
    }

    /**
     * 线路故障分析-查询故障类型的数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障类型的数量
     */
    public List<Row> selectCountByFaultType(
            LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT FAULT_TYPE, " +
                "            COUNT(1) OVER (PARTITION BY FAULT_TYPE) AS COUNTS " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 线路故障修复时长-查询线路故障平均修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 线路故障平均修复时长
     */
    public Row selectLineFaultRepairDurationAvgTotal(
            LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT SUM(REPAIR_TIME) AS SUM " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ?";
        return Db.selectOneBySql(sql, beginDate, endDate);
    }

    /**
     * 线路故障修复时长-查询故障修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障修复时长
     */
    public List<Row> selectLineFaultRepairDurationAvg(
            LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT COUNTY_CORPORATION, " +
                "            ROUND(AVG(REPAIR_TIME) OVER (PARTITION BY COUNTY_CORPORATION), 2) AS AVG " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 线路故障修复时长-查询影响用户数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 影响用户数
     */
    public List<Row> selectAffectUsersNumber(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT COUNTY_CORPORATION," +
                "            SUM(TOTAL_USER_NUMBER) OVER (PARTITION BY COUNTY_CORPORATION) AS COUNTS " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 线路故障修复时长-查询超8小时故障
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 超8小时故障
     */
    public List<Row> select8HourFailure(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT COUNTY_CORPORATION," +
                "            COUNT(1) OVER (PARTITION BY COUNTY_CORPORATION) AS COUNTS " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ? AND REPAIR_TIME > 8";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 频繁故障线路-月
     *
     * @param beginMonth 开始月
     * @param endMonth   结束月
     * @return 频繁故障线路-月
     */
    public List<Row> selectFrequentFailureMonth(String beginMonth, String endMonth) {
        String sql = "SELECT DISTINCT COUNTY_CORPORATION AS unit,POWER_FAILURE_LINE_NAME AS line_name," +
                "            COUNT(1) OVER (PARTITION BY POWER_FAILURE_LINE_NAME) AS counts" +
                "     FROM FAULT_DETAIL " +
                "     WHERE TO_CHAR(EVENT_TIME, 'yyyy-MM') = ? " +
                "           OR TO_CHAR(EVENT_TIME, 'yyyy-MM') = ? " +
                "     ORDER BY COUNTS DESC";
        return Db.selectListBySql(sql, beginMonth, endMonth);
    }

    /**
     * 频繁故障线路-年
     *
     * @param year 年
     * @return 频繁故障线路-年
     */
    public List<Row> selectFrequentFailureYear(String year) {
        String sql = "SELECT DISTINCT COUNTY_CORPORATION AS unit,POWER_FAILURE_LINE_NAME AS line_name, " +
                "            COUNT(1) OVER (PARTITION BY POWER_FAILURE_LINE_NAME) AS counts " +
                "     FROM FAULT_DETAIL " +
                "     WHERE TO_CHAR(EVENT_TIME, 'yyyy') = ? " +
                "     ORDER BY COUNTS DESC";
        return Db.selectListBySql(sql, year);
    }

    /**
     * 查询线路故障率排名
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectLineFailureRateRanking(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT COUNTY_CORPORATION," +
                "            COUNT(1) OVER (PARTITION BY COUNTY_CORPORATION) AS COUNTS " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 查询线路故障成因分析
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return list
     */
    public List<Row> selectLineFailureCauseAnalysis(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT FAULT_CAUSE," +
                "            COUNT(1) OVER (PARTITION BY FAULT_CAUSE) COUNTS " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME between ? and ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 查询线路故障成因分析-根据故障原因
     *
     * @param beginDate  开始日期
     * @param endDate    结束日期
     * @param faultCause 故障原因
     * @return list
     */
    public List<Row> selectLineFailureCauseAnalysisByFaultCause(
            LocalDate beginDate, LocalDate endDate, String faultCause) {
        String sql = "SELECT DISTINCT FAULT_CAUSE_2_CATEGORY, " +
                "            COUNT(1) OVER ( PARTITION BY FAULT_CAUSE_2_CATEGORY) COUNTS " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ? AND FAULT_CAUSE_2_CATEGORY IS NOT NULL ";
        if (StringUtils.isBlank(faultCause)) {
            sql = sql + "ORDER BY COUNTS DESC";
            return Db.selectListBySql(sql, beginDate, endDate);
        }else {
            sql = sql + " AND FAULT_CAUSE = ? ORDER BY COUNTS DESC";
            return Db.selectListBySql(sql, beginDate, endDate, faultCause);
        }

    }

    /**
     * 查询停电信息
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param type      停电类型
     * @return 影响用户数、涉及台区数
     */
    public Row selectOutageInformation(LocalDate beginDate, LocalDate endDate, String type) {
        String sql = "SELECT SUM(AFFECT_USER_QUANTITY) AS USERS, " +
                "            SUM(DISTRICT_QUANTITY)    AS DISTRICTS " +
                "     FROM POWER_CUT_POWER_TRANSMISSION " +
                "     WHERE BLACKOUT_TYPE = ? AND END_TIME BETWEEN ? AND ?";
        return Db.selectOneBySql(sql, type, beginDate, endDate);
    }

    /**
     * 停电类型总数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return total
     */
    public List<Row> selectOutageInformationTotal(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT BLACKOUT_TYPE," +
                "            COUNT(1) OVER (PARTITION BY BLACKOUT_TYPE) COUNTS " +
                "     FROM POWER_CUT_POWER_TRANSMISSION " +
                "     WHERE END_TIME BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 查询重点关注故障线路
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 重点关注故障线路
     */
    public List<Row> selectFocusOnFaultyLines(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT COUNTY_CORPORATION," +
                "            POWER_FAILURE_LINE_NAME," +
                "            TO_CHAR(EVENT_TIME, 'yyyy-MM-dd') EVENT_TIME " +
                "     FROM FAULT_DETAIL " +
                "     WHERE EVENT_TIME BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

}
