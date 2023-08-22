package com.pg.screen.dao;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.pg.screen.mapper.Gzgd95598Mapper;
import com.pg.screen.utils.TimeInterval;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

import static com.pg.screen.mapper.entity.table.Gzgd95598TableDef.GZGD95598;

/**
 * 报修工单 dao
 *
 * @author c.chuang
 */
@Repository
public class RepairsOrderDao {

    @Resource
    private Gzgd95598Mapper gzgd95598Mapper;

    /**
     * 查询报修工单总数
     *
     * @param workerUnit 工单单位
     * @return 报修工单总数
     */
    public Long selectTotal(String workerUnit) {
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(GZGD95598.SLSJ.between(timeInterval.getBeginDateTime(), timeInterval.getEndDateTime()));
        if (StringUtils.isNotBlank(workerUnit)) {
            queryWrapper.and(GZGD95598.GDDW.eq(workerUnit));
        }
        return gzgd95598Mapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 根据状态查询报修工单数
     *
     * @param orderStatus 工单状态
     * @param appStatus   app状态
     * @param workerUnit  工单单位
     * @return 报修工单数
     */
    public Long selectCountByStatus(
            String orderStatus, String appStatus, Integer type, String workerUnit) {
        QueryWrapper queryWrapper = QueryWrapper.create();
        if (type == 0) {
            queryWrapper
                    .where(GZGD95598.GDZT.eq(orderStatus))
                    .and(GZGD95598.DDXCSCFZ.isNull());
        } else if (type == 1) {
            queryWrapper
                    .where(GZGD95598.GDZT.eq(orderStatus))
                    .and(GZGD95598.APPZT.eq(appStatus));
        } else if (type == 2) {
            queryWrapper
                    .where(GZGD95598.GDZT.eq(orderStatus)
                            .or(GZGD95598.APPZT.eq(appStatus))
                    );
        }
        if (StringUtils.isNotBlank(workerUnit)) {
            queryWrapper = queryWrapper
                    .and(GZGD95598.GDDW.eq(workerUnit));
        }
        final TimeInterval timeInterval = TimeInterval.createOnMonday();
        queryWrapper.and(GZGD95598.SLSJ.between(timeInterval.getBeginDateTime(), timeInterval.getEndDateTime()));
        return gzgd95598Mapper.selectCountByQuery(queryWrapper);
    }

    /**
     * 查询报修工单类型分析一级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return list
     */
    public List<Row> selectRepairWorkOrderTypeAnalysis1(
            LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT GZDL," +
                "            COUNT(1) OVER (PARTITION BY GZDL) COUNTS " +
                "     FROM GZGD95598 " +
                "     WHERE SLSJ BETWEEN ? AND ? AND GZDL IS NOT NULL";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 查询报修工单类型分析二级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param type      故障大类
     * @return list
     */
    public List<Row> selectRepairWorkOrderTypeAnalysis2(
            LocalDate beginDate, LocalDate endDate, String type) {
        String sql = "SELECT DISTINCT GZXLY," +
                "            COUNT(1) OVER (PARTITION BY GZXLY) COUNTS " +
                "     FROM GZGD95598 " +
                "     WHERE SLSJ BETWEEN ? AND ? AND GZDL = ? AND GZXLY IS NOT NULL";
        return Db.selectListBySql(sql, beginDate, endDate, type);
    }

    /**
     * 查询报修工单类型分析三级
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param type      故障大类
     * @return list
     */
    public List<Row> selectRepairWorkOrderTypeAnalysis3(
            LocalDate beginDate, LocalDate endDate, String type) {
        String sql = "SELECT DISTINCT GZXLE," +
                "            COUNT(1) OVER (PARTITION BY GZXLE) COUNTS " +
                "     FROM GZGD95598 " +
                "     WHERE SLSJ BETWEEN ? AND ? AND GZXLY = ? AND GZXLE IS NOT NULL";
        return Db.selectListBySql(sql, beginDate, endDate, type);
    }

    /**
     * 抢修时长分析-查询故障平均修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    public Double selectFaultRepairAvg(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT ROUND(AVG(GZXFSCFZ),2) AVG FROM GZGD95598 WHERE GDSJ BETWEEN ? AND ?";
        Row row = Db.selectOneBySql(sql, beginDate, endDate);
        if (row == null) {
            return 0.00;
        }
        return row.getDouble("AVG", 0.00);
    }

    /**
     * 抢修时长分析-查询到达现场平均时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    public Double selectArriveAtSceneAvg(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT ROUND(AVG(DDXCSCFZ),2) AVG FROM GZGD95598 WHERE GDSJ BETWEEN ? AND ?";
        Row row = Db.selectOneBySql(sql, beginDate, endDate);
        if (row == null) {
            return 0.00;
        }
        return row.getDouble("AVG", 0.00);
    }

    /**
     * 抢修时长分析-查询单位故障平均修复时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    public List<Row> selectFaultRepairAvgForUnit(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT GDDW," +
                "            ROUND(AVG(GZXFSCFZ) OVER ( PARTITION BY GDDW),2) AVG " +
                "     FROM GZGD95598 " +
                "     WHERE GDSJ BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 抢修时长分析-查询单位到达现场平均时长
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 故障平均修复时长
     */
    public List<Row> selectArriveAtSceneAvgForUnit(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT GDDW," +
                "            ROUND(AVG(DDXCSCFZ) OVER ( PARTITION BY GDDW),2) AVG " +
                "     FROM GZGD95598 " +
                "     WHERE GDSJ BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 查询报修工单数量分析-月
     *
     * @return 报修工单数量分析-月
     */
    public Row selectRepairQuantityMonth(LocalDate date) {
        String sql = "SELECT COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '01', 0)) M1,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '02',  0)) M2,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '03',  0)) M3,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '04',  0)) M4,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '05',  0)) M5,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '06',  0)) M6,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '07',  0)) M7,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '08',  0)) M8,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '09',  0)) M9,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '10',  0)) M10,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '11',  0)) M11,\n" +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'MM'), '12',  0)) M12\n" +
                "    FROM GZGD95598 \n" +
                "    WHERE TO_CHAR(SLSJ, 'yyyy') = ?";
        return Db.selectOneBySql(sql, date.getYear());
    }

    /**
     * 查询报修工单数量分析-小时
     *
     * @param date 日期
     * @return 报修工单数量分析-小时
     */
    public Row selectRepairQuantityHour(String date) {
        String sql = "SELECT COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '01', 0)) H1," +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '02',  0)) H2, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '03',  0)) H3, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '04',  0)) H4, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '05',  0)) H5, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '06',  0)) H6, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '07',  0)) H7, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '08',  0)) H8, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '09',  0)) H9, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '10',  0)) H10, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '11',  0)) H11, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '12',  0)) H12, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '13',  0)) H13, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '14',  0)) H14," +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '15',  0)) H15," +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '16',  0)) H16, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '17',  0)) H17, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '18',  0)) H18, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '19',  0)) H19, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '20',  0)) H20, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '21',  0)) H21, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '22',  0)) H22, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '23',  0)) H23, " +
                "            COUNT(DECODE(TO_CHAR(SLSJ, 'HH24'), '24',  0)) H24 " +
                "     FROM GZGD95598 WHERE to_char(SLSJ,'yyyy-MM-dd') = ?";
        return Db.selectOneBySql(sql, date);
    }

    /**
     * 查询配网抢修工单分布
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 配网抢修工单分布
     */
    public List<Row> selectWorkOrderDistribution(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT DISTINCT GDDW, COUNT(1) OVER (PARTITION BY GDDW) COUNTS " +
                "     FROM GZGD95598 " +
                "     WHERE SLSJ BETWEEN ? AND ?";
        return Db.selectListBySql(sql, beginDate, endDate);
    }

    /**
     * 查询配网抢修工单分布-抢修班组
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param unit      工单单位
     * @return 配网抢修工单分布
     */
    public List<Row> selectWorkOrderDistributionByUnit(
            LocalDate beginDate, LocalDate endDate, String unit) {
        String sql = "SELECT * " +
                "     FROM (SELECT DISTINCT QXBZ,COUNT(1) OVER (PARTITION BY QXBZ) COUNTS " +
                "           FROM GZGD95598 WHERE SLSJ BETWEEN ? AND ? AND GDDW = ? AND QXBZ IS NOT NULL " +
                "           ORDER BY COUNTS DESC) " +
                "     WHERE ROWNUM <= 9";
        return Db.selectListBySql(sql, beginDate, endDate, unit);
    }

    /**
     * 查询配网抢修工单分布-抢修人员
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param team      抢修班组
     * @return 配网抢修工单分布
     */
    public List<Row> selectWorkOrderDistributionByUser(
            LocalDate beginDate, LocalDate endDate, String team, String company) {
        String sql = "SELECT *" +
                "     FROM (SELECT DISTINCT QXRY,COUNT(1) OVER (PARTITION BY QXRY) COUNTS " +
                "           FROM GZGD95598 " +
                "           WHERE SLSJ BETWEEN ? AND ? AND QXBZ = ? AND GDDW = ? AND QXRY IS NOT NULL " +
                "           ORDER BY COUNTS DESC) " +
                "     WHERE ROWNUM <= 10";
        return Db.selectListBySql(sql, beginDate, endDate, team, company);
    }

    /**
     * 查询故障工单95598数量
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return count
     */
    public Long selectCount(LocalDate beginDate, LocalDate endDate) {
        String sql = "SELECT COUNT(1) COUNTS " +
                "     FROM GZGD95598 " +
                "     WHERE SLSJ BETWEEN ? AND ?";
        Row row = Db.selectOneBySql(sql, beginDate, endDate);
        return row.getLong("COUNTS", 0L);
    }

}
